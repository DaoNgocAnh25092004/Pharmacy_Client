package com.example.pharmacyproject;

import com.example.pharmacyproject.ControllerCallback.ProductExpireCallback;
import com.example.pharmacyproject.utils.PDFExporter;
import com.example.pharmacyproject.utils.PriceFormatter;
import com.example.pharmacyproject.utils.ReadBarcode;
import model.*;
import model.enums.ETinhTrangSP;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.*;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BanHangController implements Initializable {
    private ReadBarcode readBarcode = new ReadBarcode();
    private Map<SanPham, Integer> textFieldPairListRoot;
    private Stage primaryStage;
    private NhanVien nhanVien;
    private Dashboard dashboard;
    private static final double VAT = 0.2;
    //quan ly ban hang
    private SanPhamService productService;
    private HoaDonService invoiceService;
    private ChiTietHoaDonService detailService;
    private KhachHangService customerService;
    private NhanVienService employeeService;

    private Map<Tab, ObservableList<ChiTietHoaDon>> tabSanPhamMap = new HashMap<>();
    private Map<Tab, HoaDon> tabHoaDonMap = new HashMap<>();
    private Map<Tab, KhachHang> tabKhachHangMapMap = new HashMap<>();

    private int tabCount = 1;

    @FXML
    private TabPane hoaDonTabPane;

    @FXML
    private AnchorPane rootScene;

    @FXML
    private Button addDigitBtn, addSanPhamBtn, initHoaDonUI, checkKHBtn, removeItem, thanhToanBtn;

    //barcode scanner
    @FXML
    private Button scanBarcodeBtn;
    @FXML
    private Button suggestPrice1, suggestPrice2, suggestPrice3;
    @FXML
    private TextField scanBarcodeResult, soLuongScan;

    @FXML
    private TextField customerMoneyField, tenSpInputField, soLuongField, checkUpSdtField, hoTenKHField, hangKHField;

    @FXML
    private TextField maHoaDonField, chietKhauField, khachPhaiTraField;
    @FXML
    private DatePicker ngayTaoPicker;
    @FXML
    private ComboBox<String> rankComboBox;
    @FXML
    private ComboBox<String> donThuocMauComboBox;
    @FXML
    private ComboBox<String> chuongTrinhKMCB;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //quan ly ban hang
        productService = PharmacyApplication.getProductService();
        customerService = PharmacyApplication.getCustomerService();
        invoiceService = PharmacyApplication.getInvoiceService();
        detailService = PharmacyApplication.getDetailService();
        employeeService = PharmacyApplication.getEmployeeService();

        //comboBox data
        donThuocMauComboBox.getItems().addAll("Đơn thuốc hạ sốt (người lớn)", "Thuốc hạ sốt (trẻ em)");
        addDigitBtn.setOnMouseClicked(e -> {
            if (customerMoneyField.getText().equals("") || customerMoneyField.getText() == null){
                showConfirmAlert("Cảnh báo", "Chưa nhập số tiền của khách hàng");
            } else {
                String money = customerMoneyField.getText();
                money = money.concat(".000");
                customerMoneyField.setText(money);
            }
        });
        hoaDonTabPane.getTabs().addListener((ListChangeListener<Tab>) change -> {
            if (hoaDonTabPane.getTabs().isEmpty()) {
                initHoaDonUI.setVisible(true);
                initHoaDonUI.setDisable(false);
                removeItem.setVisible(false);
                tabCount = 1;
            } else {
                initHoaDonUI.setVisible(false);
                initHoaDonUI.setDisable(true);
                removeItem.setVisible(true);
            }
        });

        rootScene.sceneProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Scene scene = (Scene) newValue;
                scene.setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.F1) {
                        System.out.println("F1 pressed");
                    }
                });
            }
        });
        hoaDonTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                transferHoaDonForDisplay();
            }
        });

        eventListener();
    }

    @FXML
    protected void handleAddTab(){
        HoaDon hoaDon = new HoaDon();
        hoaDon.setNgayLapHD(LocalDate.now());
        hoaDon.setNhanVien(nhanVien);
        hoaDon.setMaHoaDon(UUID.randomUUID().toString());

        ObservableList<ChiTietHoaDon> chiTietHoaDonList = FXCollections.observableArrayList();
        Tab newTab = new Tab("Hóa đơn " + tabCount++);
        tabHoaDonMap.put(newTab, hoaDon);

        Button closeBtn = new Button("x");
        closeBtn.setStyle("-fx-background-color: transparent; -fx-padding: 0; -fx-font-size: 10;");
        closeBtn.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Chưa thanh toán");
            alert.setHeaderText(null);
            alert.setContentText("Có chắc bạn muốn hủy hóa đơn không");
            alert.showAndWait().ifPresent(respone ->{
                if (respone == ButtonType.OK){
                    hoaDonTabPane.getTabs().remove(newTab);
                }
            });
        });

        newTab.setGraphic(closeBtn);
        TableView<ChiTietHoaDon> tableView = new TableView<>();
        tableView.setItems(chiTietHoaDonList);

//        String soLuongText = soLuongField.getText().trim();

        //1
        TableColumn<ChiTietHoaDon, String> maSpColumn = new TableColumn<>("Mã sản phẩm");
        maSpColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSanPham().getMaSP()));
        //2
        TableColumn<ChiTietHoaDon, String> tenColumn = new TableColumn<>("Tên sản phẩm");
        tenColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSanPham().getTenSP()));
        //3
        TableColumn<ChiTietHoaDon, Integer> soLuongColumn = new TableColumn<>("Số lượng");
        soLuongColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getSoLuong()).asObject());

        //donvitinh
        TableColumn<ChiTietHoaDon, DonViTinh> donViTinhColumn = new TableColumn<>("Đơn vị tính");
        donViTinhColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getSanPham().getDonViTinh()));
        donViTinhColumn.setCellFactory(column -> new TableCell<ChiTietHoaDon, DonViTinh>() {
            @Override
            protected void updateItem(DonViTinh item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getTenDonViTinh()); // Display only the name of DonViTinh
                }
            }
        });

        //4
        TableColumn<ChiTietHoaDon, Double> giaBanColumn = new TableColumn<>("Giá bán");
        giaBanColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getSanPham().getGiaBan()).asObject());

        //5
        TableColumn<ChiTietHoaDon, Double> VATColumn = new TableColumn<>("VAT");
        VATColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(VAT).asObject());

        //8
        TableColumn<ChiTietHoaDon, Double> thanhTienColumn = new TableColumn<>("Thành tiền");
        thanhTienColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty((cellData.getValue().getTongTien())
        ).asObject());

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableView.getColumns().addAll(
                maSpColumn, tenColumn, soLuongColumn, donViTinhColumn, giaBanColumn, VATColumn, thanhTienColumn
        );


        newTab.setContent(tableView);
        hoaDonTabPane.getTabs().add(newTab);
        hoaDonTabPane.getSelectionModel().select(newTab);

        tabSanPhamMap.put(newTab, chiTietHoaDonList);
        System.out.println(hoaDon);
    }

    @FXML
    protected void handleAddSanPham() {
//        if (hoaDonTabPane.getTabs().isEmpty()) {
//            showWarningAlert("Chưa tạo hóa đơn");
//            return;
//        }
//        int soLuong;
//        String soLuongText = soLuongField.getText().trim();
//        if (!soLuongText.isEmpty()) {
//            soLuong = Integer.parseInt(soLuongText);
//        } else {
//            soLuong = 1;
//        }
//
//        String tenSP = tenSpInputField.getText().trim();
//        Tab selectedTab = hoaDonTabPane.getSelectionModel().getSelectedItem();
//
//        if (!tenSP.isEmpty()) {
//            ObservableList<ChiTietHoaDon> chiTietHoaDonList = tabSanPhamMap.get(selectedTab);
//            String maHoaDon = maHoaDonMap.get(selectedTab);
//
//            quanLyBanHang = QuanLyBanHang.getInstance();
//            quanLyBanHang.retrieveSPByName(tenSP, new TranferSPToGUICallback() {
//                @Override
//                public void transferThuoc(Thuoc thuoc) {
//                    if (thuoc != null && selectedTab != null && selectedTab.getContent() instanceof TableView) {
//                        if (thuoc.isThuocKeDon()){
//                            showWarningAlert("Thuốc kê đơn, không thể bán lẻ");
//                        }
//                        ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(soLuong, VAT, thuoc, maHoaDon);
//                        chiTietHoaDonList.add(chiTietHoaDon);
//                    }
//                }
//
//                @Override
//                public void transferTPCN(ThucPhamChucNang tpcn) {
//                    if (tpcn != null && selectedTab != null && selectedTab.getContent() instanceof TableView) {
//                        ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(soLuong, VAT, tpcn, maHoaDon);
//                        chiTietHoaDonList.add(chiTietHoaDon);
//                    }
//                }
//
//                @Override
//                public void onSPNotFound() {
//                    showWarningAlert("Không tìm thấy sản phẩm");
//                }
//            });
//        }
    }


    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setOnCloseRequest(event -> {
            // Handle the close event here
            System.out.println("Application is closing");
            try {
                if (readBarcode != null) {
                    readBarcode.stopListening();
                    System.out.println("Barcode scanner stopped");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void setNhanVienInfo(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
        System.out.println("Nhan vien: " + nhanVien.getHoTen());
    }

    public void returnSPList(Map<SanPham, Integer> textFieldPairList) {
        Tab selectedTab = hoaDonTabPane.getSelectionModel().getSelectedItem();
        HoaDon hoaDon = tabHoaDonMap.get(selectedTab);
        if (selectedTab != null) {
            this.textFieldPairListRoot = textFieldPairList;
            for (Map.Entry<SanPham, Integer> entry : textFieldPairList.entrySet()) {
                SanPham sanPham = entry.getKey();
                ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
                chiTietHoaDon.setHoaDon(hoaDon);
                tabSanPhamMap.get(selectedTab).add(chiTietHoaDon);
            }
        }
    }

    private boolean showExpireAlert(String title, String message){
        boolean[] isAccepted = new boolean[]{false};
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                isAccepted[0] = true;
            }
        });
        return isAccepted[0];
    }

    private void showWarningAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showConfirmAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String extractStringInBrackets(String str) {
        if (str == null) {
            return null;
        }
        Pattern pattern = Pattern.compile("\\[(.*?)\\]");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private void clearComboBox(){
        Platform.runLater(() -> {
            if (!donThuocMauComboBox.getItems().isEmpty()) {
                donThuocMauComboBox.getSelectionModel().clearSelection();
            }
        });
    }

    private void eventListener() {
//        donThuocMauComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue != null) {
//                //check invoice is initialized
//                if (hoaDonTabPane.getTabs().isEmpty()) {
//                    showWarningAlert("Chưa tạo hóa đơn", "Chưa tạo hóa đơn, nhấn nút tạo hóa đơn bên dưới");
//                    clearComboBox();
//                    return;
//                }
//                clearComboBox();
//            }
//        });

        customerMoneyField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()){
                suggestPrice1.setText(PriceFormatter.formatPrice(Double.parseDouble(newValue) * 1000.0));
                suggestPrice2.setText(PriceFormatter.formatPrice(Double.parseDouble(newValue) * 10000.0));
                suggestPrice3.setText(PriceFormatter.formatPrice(Double.parseDouble(newValue) * 100000.0));
            } else {
                suggestPrice1.setText("10,000đ");
                suggestPrice2.setText("20,000đ");
                suggestPrice3.setText("50,000đ");
            }
        });

        try {
            readBarcode.startBarcodeScanner(barcodeData -> {
                scanBarcodeResult.setText(barcodeData);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        suggestPrice1.setOnMouseClicked(event -> {
            customerMoneyField.setText(revereseToSimpleString(suggestPrice1.getText()));
        });

        suggestPrice2.setOnMouseClicked(event -> {
            customerMoneyField.setText(revereseToSimpleString(suggestPrice2.getText()));
        });

        suggestPrice3.setOnMouseClicked(event -> {
            customerMoneyField.setText(revereseToSimpleString(suggestPrice3.getText()));
        });

        initHoaDonUI.setOnMouseClicked(event -> {
            handleAddTab();
        });

        checkKHBtn.setOnMouseClicked(event -> {
            try {
                KhachHang khachHang = customerService.findKhachHangBySDT(checkUpSdtField.getText().trim());
                //Todo: may be need to check diemTichLuy
                if (khachHang != null) {
                    tabKhachHangMapMap.put(hoaDonTabPane.getSelectionModel().getSelectedItem(), khachHang);
                    hoTenKHField.setText(khachHang.getHoTen());
//                    hangKHField.setText(khachHang.getTenHang());
                    fillHoaDonInfo(khachHang);
                } else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Khách hàng mới");
                    alert.setHeaderText(null);
                    alert.setContentText("Khách hàng chưa tồn tại, bạn có muốn thêm mới?");
                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            dashboard.loadUI("khach_hang.fxml");
                        }
                    });
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });


        //tableView remove item listener
        hoaDonTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Tab selectedTab = hoaDonTabPane.getSelectionModel().getSelectedItem();
            if (selectedTab != null && selectedTab.getContent() instanceof TableView) {
                TableView<ChiTietHoaDon> tableView = (TableView<ChiTietHoaDon>) selectedTab.getContent();
                tableView.getSelectionModel().selectedItemProperty().addListener((observable1, oldValue1, newValue1) -> {
                    if (newValue1 != null) {
                        ChiTietHoaDon selectedChiTietHoaDon = tableView.getSelectionModel().getSelectedItem();
                        removeItem.setOnMouseClicked(event -> {
                            tableView.getItems().remove(selectedChiTietHoaDon);
                        });
                    }
                });
            }
        });

        thanhToanBtn.setOnMouseClicked(event -> {
            try {
                if(hoaDonTabPane.getTabs().isEmpty()){
                    showWarningAlert("Chưa tạo hóa đơn", "Chưa tạo hóa đơn, nhấn nút tạo hóa đơn bên dưới");
                    return;
                }
                if (tabSanPhamMap.get(hoaDonTabPane.getSelectionModel().getSelectedItem()).isEmpty()){
                    showWarningAlert("Lỗi thanh toán", "Chưa thêm sản phẩm");
                    return;
                }
                handleThanhToan();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });

        //declare barcode scanner


        scanBarcodeBtn.setOnMouseClicked(event -> {
            int soLuong = soLuongScan.getText().isEmpty() ? 1 : Integer.parseInt(soLuongScan.getText());
            if (hoaDonTabPane.getTabs().isEmpty()) {
                showWarningAlert("Cảnh báo", "Chưa tạo hóa đơn");
                return;
            }

            SanPham sanPham;
            String maSp = scanBarcodeResult.getText().trim();
            Tab selectedTab = hoaDonTabPane.getSelectionModel().getSelectedItem();

            if (!maSp.isEmpty()) {
                ObservableList<ChiTietHoaDon> chiTietHoaDonList = tabSanPhamMap.get(selectedTab);
                HoaDon currentHoaDon = tabHoaDonMap.get(selectedTab);

                try {
                    sanPham = productService.find(maSp);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if (sanPham != null && selectedTab != null && selectedTab.getContent() instanceof TableView) {
                    if (sanPham.getHanSD().isBefore(LocalDate.now())){
                        showWarningAlert("Cảnh báo", "Thực phẩm chức năng đã hết hạn sử dụng, không thể thêm vào hóa đơn");
                    } else if (sanPham.getTinhTrangSP().equals(ETinhTrangSP.HET_HANG)) {
                        showWarningAlert("Cảnh báo", "Thực phẩm chức năng đã hết hàng");
                    } else {
                        checkExpireWithinAMonth(sanPham, new ProductExpireCallback() {
                            @Override
                            public void onProductExpireWithinAMonth(boolean isAddToInvoice) {
                                if (isAddToInvoice){
                                    ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
                                    chiTietHoaDon.setSoLuong(soLuong);
                                    chiTietHoaDon.setVAT(20);
                                    chiTietHoaDon.setSanPham(sanPham);
                                    chiTietHoaDon.setHoaDon(currentHoaDon);
                                    chiTietHoaDonList.add(chiTietHoaDon);
                                }
                            }
                            @Override
                            public void onProductStillGood() {
                                ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
                                chiTietHoaDon.setSoLuong(soLuong);
                                chiTietHoaDon.setVAT(20);
                                chiTietHoaDon.setSanPham(sanPham);
                                chiTietHoaDon.setHoaDon(currentHoaDon);
                                chiTietHoaDonList.add(chiTietHoaDon);
                            }
                        });
                    }
                } else {
                    showWarningAlert("Cảnh báo", "Sản phẩm không tồn tại");
                }
                soLuongScan.clear();
                scanBarcodeResult.clear();
            }
        });
    }



    private void checkExpireWithinAMonth(SanPham sanPham, ProductExpireCallback callback){
        if (sanPham.getHanSD().isEqual(LocalDate.now().plusMonths(1)) ||
                sanPham.getHanSD().isBefore(LocalDate.now().plusMonths(1))) {
            boolean result = showExpireAlert("Nhắc nhở", "Thực phẩm chức năng chỉ còn " + getExpireDayLeft(sanPham.getHanSD()) + " sẽ hết hạn sử dụng, bạn có muốn tiếp tục thêm vào hóa đơn?");
            callback.onProductExpireWithinAMonth(result);
        } else {
            callback.onProductStillGood();
        }
    }

    private String getExpireDayLeft(LocalDate hanSuDung){
        return String.valueOf(LocalDate.now().until(hanSuDung).getDays());
    }

    private String revereseToSimpleString(String text) {
        return PriceFormatter.formatPriceToRegularString(text);
    }

    private String formatNumber(String number) {
        if (number.isEmpty()) {
            return "";
        }
        long parsedNumber = Long.parseLong(number.replaceAll(",", ""));
        return String.format("%,d", parsedNumber);
    }

    private void handleThanhToan() throws ParseException {
        if (customerMoneyField.getText().trim().isEmpty()){
            showWarningAlert("Cảnh báo", "Chưa nhập số tiền của khách hàng");
        }
        else{
            if (checkSoTienThanhToan()){
                Tab selectedTab = hoaDonTabPane.getSelectionModel().getSelectedItem();
                HoaDon hoaDon = tabHoaDonMap.get(selectedTab);
                ObservableList<ChiTietHoaDon> chiTietHoaDons = tabSanPhamMap.get(selectedTab);
                if (hoaDon != null) {
                    //fix wrong in HoaDon
                    hoaDon.setTienKhachHangPhaiThanhToan(PriceFormatter.parsePrice(khachPhaiTraField.getText().trim()));
                    // update diemTichLuy
                    KhachHang khachHang = tabKhachHangMapMap.get(selectedTab);
                    int diemCongThem = (int) hoaDon.getTienKhachHangPhaiThanhToan() / 10000;
                    if (Objects.equals(extractStringInBrackets(chuongTrinhKMCB.getSelectionModel().getSelectedItem()), "tangDiem")){
                        diemCongThem = diemCongThem * 2;
                    }
                    if (diemCongThem <= 0){
                        diemCongThem = 1;
                    }
                    khachHang.setDiemTichLuy(khachHang.getDiemTichLuy() + diemCongThem);
                    //fill the rest into HoaDon

                    hoaDon.setSoDiemTichLuyDuocSuDung(0);
                    hoaDon.setKhachHang(khachHang);
                    hoaDon.setNhanVien(nhanVien);
                    hoaDon.setChiTietHoaDons(new HashSet<>(chiTietHoaDons));

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Xuất hóa đơn");
                    alert.setHeaderText(null);
                    alert.setContentText("Có muốn xuất hóa đơn không");
                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            try {
                                if (invoiceService.saveInvoice(hoaDon)) {
                                    System.out.println("Lưu hóa đơn thành công");
                                    for (ChiTietHoaDon chiTiet : chiTietHoaDons) {
                                        chiTiet.setHoaDon(hoaDon);
                                        if (detailService.save(chiTiet)) {
                                            System.out.println("Lưu chi tiết hóa đơn thành công");
                                        } else {
                                            System.out.println("Lưu chi tiết hóa đơn thất bại");
                                        }
                                    }
                                    if (exportHoaDonToPDF(
                                            tabHoaDonMap.get(selectedTab),
                                            tabSanPhamMap.get(selectedTab),
                                            tabKhachHangMapMap.get(selectedTab))
                                    ) {
                                        showConfirmAlert("Thành công", "Thanh toán thành công, hóa đơn đã được xuất ra file PDF");
                                        closeCurrentTab();
                                    } else {
                                        showWarningAlert("Lỗi xuất file", "Thanh toán thành công, xuất file thất bại");
                                        closeCurrentTab();
                                    }
                                } else showWarningAlert("Lỗi thanh toán", "Thanh toán thất bại");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                if (invoiceService.saveInvoice(hoaDon)){
                                    System.out.println("Lưu hóa đơn thành công");
                                    for (ChiTietHoaDon chiTiet : chiTietHoaDons) {
                                        chiTiet.setHoaDon(hoaDon);
                                        if (detailService.save(chiTiet)) {
                                            System.out.println("Lưu chi tiết hóa đơn thành công");
                                        } else {
                                            System.out.println("Lưu chi tiết hóa đơn thất bại");
                                        }
                                    }
                                    showConfirmAlert("Thành công", "Thanh toán thành công");
                                    closeCurrentTab();
                                } else {
                                    showWarningAlert("Lỗi thanh toán", "Thanh toán thất bại");
                                }
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                }
            }
        }
        System.out.println("Thanh toan");
    }

    private boolean exportHoaDonToPDF(HoaDon hoaDon, ObservableList<ChiTietHoaDon> chiTietHoaDons, KhachHang khachHang) {
        File directory = new File("src/main/java/com/example/pharmacyproject/FilePDF");
        if (!directory.exists()) {
            directory.mkdir();
        }
        // Lưu file PDF vào thư mục FilePDF
        File file = new File(directory, "hoadon.pdf");
        return PDFExporter.exportHoaDonToPdf(hoaDon, chiTietHoaDons, khachHang, file.getPath());
    }

    private void closeCurrentTab() {
        openFilePDF();
        checkUpSdtField.clear();
        khachPhaiTraField.clear();
        customerMoneyField.clear();
        Tab selectedTab = hoaDonTabPane.getSelectionModel().getSelectedItem();
        hoaDonTabPane.getTabs().remove(selectedTab);
    }

    private void openFilePDF() {
        File file = new File("src/main/java/com/example/pharmacyproject/FilePDF/hoadon.pdf");
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkSoTienThanhToan() {
        try {
            String tienKhachTra = customerMoneyField.getText().trim();
            String tienPhaiTra = khachPhaiTraField.getText().trim();
            double tienKhachTraDouble = Double.parseDouble(tienKhachTra);
            double tienPhaiTraDouble = PriceFormatter.parsePrice(tienPhaiTra);

            if (tienKhachTraDouble < tienPhaiTraDouble){
                showWarningAlert("Cảnh báo","Số tiền không đủ");
                return false;
            } else {
                return true;
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillHoaDonInfo(KhachHang khachHang) {
        Tab selectedTab = hoaDonTabPane.getSelectionModel().getSelectedItem();

        double tongTien = 0;
        for (ChiTietHoaDon chiTietHoaDon : tabSanPhamMap.get(selectedTab)) {
            tongTien += chiTietHoaDon.getTongTien();
        }
        HoaDon hoaDon = tabHoaDonMap.get(selectedTab);
        if (hoaDon != null) {
            hoaDon.setTienKhachHangPhaiThanhToan(tongTien);
            hoaDon.setKhachHang(khachHang); // Set the customer
            tabHoaDonMap.put(selectedTab, hoaDon); // Update the map
        }

        transferHoaDonForDisplay();
    }


    private void transferHoaDonForDisplay() {
        Tab selectedTab = hoaDonTabPane.getSelectionModel().getSelectedItem();
        HoaDon hoaDon = tabHoaDonMap.get(selectedTab);
        if (hoaDon != null) {
            System.out.println("alo");
            maHoaDonField.setText(hoaDon.getMaHoaDon());
            ngayTaoPicker.setValue(hoaDon.getNgayLapHD());
            khachPhaiTraField.setText(PriceFormatter.formatPrice(hoaDon.getTienKhachHangPhaiThanhToan()));
        } else {
            System.out.println("alo loõi");
            clearHoaDonInfo();
        }

    }

    private void clearHoaDonInfo() {
        maHoaDonField.clear();
        ngayTaoPicker.setValue(null);
        donThuocMauComboBox.getSelectionModel().clearSelection();
        chietKhauField.clear();
        khachPhaiTraField.clear();
    }

    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
        try {
            if (this.dashboard != null) {
//                this.dashboard.getReadBarcode().startBarcodeScanner(barcodeData -> {
//                    scanBarcodeResult.setText(barcodeData);
//                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
