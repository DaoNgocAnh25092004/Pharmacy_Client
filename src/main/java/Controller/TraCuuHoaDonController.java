package Controller;

import com.example.pharmacyproject.PharmacyApplication;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.ChiTietHoaDon;
import model.HoaDon;
import model.KhachHang;
import service.ChiTietHoaDonService;
import service.HoaDonService;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TraCuuHoaDonController implements Initializable {
    public Label maHDField;
    public MenuButton menu;
    public MenuItem sdt;
    public MenuItem mahd;
    public MenuItem ngay;
    public ComboBox comboBoxMenu;
    private KhachHang khachHangMain;
    ObservableList<HoaDon> data;
    ObservableList<ChiTietHoaDon> dataDetail;
    public TextField searchInputField;
    public Button searchBtn;
    public TableView<HoaDon> tableView;
    public GridPane table;
    public Label idField;
    public Label idCustomField;
    public Label nameField;
    public Label idStaffField;
    public Label createdDateField;
    public Label discountField;
    public Label totalField;
    public Label sumField;
    public Label excessCashField;
    @FXML
    public TableView<ChiTietHoaDon> tableDetail;
    @FXML
    public TableColumn<HoaDon, String> maHDCol;
    @FXML
    public TableColumn<HoaDon, String> ngayTaoCol;
    @FXML
    public TableColumn<ChiTietHoaDon, String> maThuocCol;
    @FXML
    public TableColumn<ChiTietHoaDon, String> tenThuocCol;
    @FXML
    public TableColumn<ChiTietHoaDon, String> soLuongCol;
    @FXML
    public TableColumn<ChiTietHoaDon, String> donGiaCol;
    @FXML
    public TableColumn<ChiTietHoaDon, String> thanhTienCol;
    private List<HoaDon> hoaDonList;

    private HoaDonService invoiceService;
    private ChiTietHoaDonService detailService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        invoiceService = PharmacyApplication.getInvoiceService();
        detailService = PharmacyApplication.getDetailService();

        data = FXCollections.observableArrayList();
        hoaDonList = new ArrayList<>();
        setColumns();
        setUpviewEvent();
        comboBoxMenu.getItems().addAll("Số điện thoại", "Ngày", "Mã hóa đơn");
        comboBoxMenu.getSelectionModel().select(0);

    }

    private void setColumns() {
        //HoaDon
        maHDCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMaHoaDon()));
        ngayTaoCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNgayLapHD().toString()));

        //ChiTietHoaDon
        maThuocCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSanPham().getMaSP()));
        tenThuocCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSanPham().getTenSP()));
        soLuongCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getSoLuong()).asString());
        donGiaCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getSanPham().getGiaBan()).asString());
        thanhTienCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getTongTien()).asString());
    }

    private void setUpviewEvent() {
        searchBtn.setOnAction(event -> onSearchBtnClick());
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                int index = tableView.getSelectionModel().getSelectedIndex();
                try {
                    displayBillInfo(hoaDonList.get(index));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                tableDetail.setItems(dataDetail);
            }
        });

        comboBoxMenu.setOnAction(event -> {
            String selectedItem = comboBoxMenu.getSelectionModel().getSelectedItem().toString();
            if (selectedItem.equals("Số điện thoại")) {
                searchInputField.setPromptText("Nhập số điện thoại");
            } else if (selectedItem.equals("Ngày")) {
                searchInputField.setPromptText("Nhập ngày tạo hóa đơn(VD: 01/02/2021)");
            } else if (selectedItem.equals("Mã hóa đơn")) {
                searchInputField.setPromptText("Nhập mã hóa đơn");
            }
        });
    }

    private void onSearchBtnClick() {
        String input = searchInputField.getText().trim();
        String selectedItem = comboBoxMenu.getSelectionModel().getSelectedItem().toString();

        if (selectedItem.equals("Số điện thoại")) {
            addDataSearchByPhoneNumberToTable(input);
        } else if (selectedItem.equals("Mã hóa đơn")) {
            addDataSearchByMaHDToTable(input);
        } else if (selectedItem.equals("Ngày")) {
            addDataSearchByDateToTable(input);
        }
    }

    private void addDataSearchByDateToTable(String date) {
        try {

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        data = FXCollections.observableArrayList(hoaDonList);
        tableView.setItems(data);
    }

    private void addDataSearchByMaHDToTable(String mahd) {
        HoaDon hoaDon;
        try {
            hoaDon = invoiceService.find(mahd);
            if (hoaDon != null) {
                hoaDonList = List.of(hoaDon);  // Create a list with single invoice
                data = FXCollections.observableArrayList(hoaDonList);
                tableView.setItems(data);
            } else {
                hoaDonList.clear();
                data.clear();
                tableView.setItems(data);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void addDataSearchByPhoneNumberToTable(String phoneNumber) {
        try {
            hoaDonList = invoiceService.timHoaDonTheoSoDienThoaiVaTenNhanVien(phoneNumber, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        System.out.println(hoaDonList.getFirst().getMaHD());
        if (!hoaDonList.isEmpty()) {
            data = FXCollections.observableArrayList(hoaDonList);
            tableView.setItems(data);
        }
    }

    private void displayBillInfo(HoaDon hoaDon) throws Exception {
        khachHangMain = hoaDon.getKhachHang();
        idField.setText(hoaDon.getMaHoaDon());
        idCustomField.setText(hoaDon.getKhachHang().getMaKhachHang());
        nameField.setText(khachHangMain.getHoTen());
        idStaffField.setText(hoaDon.getNhanVien().getMaNhanVien()+"");
        createdDateField.setText(hoaDon.getNgayLapHD().toString()+"");
        sumField.setText(hoaDon.getTienKhachHangPhaiThanhToan() + "");


        System.out.println(detailService.timHoaDonTheoId(hoaDon.getMaHoaDon()));
        dataDetail = FXCollections.observableArrayList(detailService.timHoaDonTheoId(hoaDon.getMaHoaDon()));
    }

    boolean checkPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^[0-9]{10}$");
    }
}