package Controller;

import com.example.pharmacyproject.PharmacyApplication;
import com.example.pharmacyproject.utils.ReadBarcode;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.SanPham;
import service.SanPhamService;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TraCuuThuocController implements Initializable {
    private boolean isScanActivated = false;
    public ImageView arrowIcon;
    public Button activateScanBtn, searchByBarcodeBtn;
    public TextField searchByProductNameField, barcodeTextField;
    public Label notifiTextField;
    public HBox barcodeInputView;
    public Button searchBtn;
    public TableView tableDetail;
    public Label productId;
    public Label productName;
    public Label importDate;
    public Label expiryDate;
    public Label weight;
    public Label importPrice;
    public Label description;
    public Label productStatus;
    public Label profit;
    public Label taxRate;
    public Label type;
    public Label group;
    public CheckBox prescriptionRequired;
    public Label ingredient;
    public Label dosage;
    public Label sideEffect;
    public Label sellingPrice;
    public Label unit;
    public Label origin;
    public Label brand;
    public ListView listView;
    public TableColumn<SanPham, String> maLoCol;
    public TableColumn<SanPham, String> ngayNhapCol;
    public Label hetHanIcon;
    public Label conHanIcon;
    private ObservableList<String> suggestions;
    private Stage primaryStage;
    private ReadBarcode readBarcode = new ReadBarcode();
    private Dashboard dashboard;
    private SanPhamService productService;

    private ObservableList<SanPham> sanPhamObservableList;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setOnCloseRequest(event -> onStopScan());
    }

    public void setDashboard(Dashboard dashboard){
        this.dashboard = dashboard;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productService = PharmacyApplication.getProductService();
        sanPhamObservableList = FXCollections.observableArrayList();
        try {
            sanPhamObservableList.addAll(productService.getAllSanPham());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("SanPham: " + sanPhamObservableList.size());

        setColumn();
        setUpViewEvents();

        conHanIcon.setStyle("-fx-background-color: ebb56d;");
        hetHanIcon.setStyle("-fx-background-color: red;");
    }

    private void setColumn() {
        ngayNhapCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNgaySX().toString()));
    }

    private void setUpViewEvents() {
        searchByBarcodeBtn.setOnAction(event -> onSearchByBarcode());
        //scan event
        activateScanBtn.setOnMouseClicked(event -> onActiveScan());
        //
        searchBtn.setOnAction(event -> onSearchBtnClick());
        //set su kien nhap text field
        searchByProductNameField.setOnKeyReleased(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                onSearchBtnClick();
            } else {
                listView.setVisible(true);
                addSuggestionList(searchByProductNameField.getText());
            }
        });
        tableDetail.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                int index = tableDetail.getSelectionModel().getSelectedIndex();
                System.out.println("index: "+ index);
                SanPham selectedProduct = (SanPham) tableDetail.getSelectionModel().getSelectedItem();
                String productName = searchByProductNameField.getText();

                showProductInfoBySoLo(selectedProduct, productName);
            }
        });
        setUpSuggestionList();
    }

    private void setUpSuggestionList() {
        suggestions = FXCollections.observableArrayList();

        listView.setOnMouseClicked(event -> {
            String selected = listView.getSelectionModel().getSelectedItem().toString();
            searchByProductNameField.setText(selected);
            listView.setVisible(false);
        });
    }

    private void addSuggestionList(String text) {
        suggestions.clear();
        if (text.isEmpty()) {
            listView.setVisible(false);
            return;
        }
        for (SanPham sanPham : sanPhamObservableList) {
            if (sanPham.getTenSP().toLowerCase().contains(text.toLowerCase()) && !suggestions.contains(sanPham.getTenSP())) {
                suggestions.add(sanPham.getTenSP());
            }
        }
        listView.setItems(suggestions);
        //set list view height = 30 * so luong item
        listView.setPrefHeight(24 * suggestions.size());
    }

    private void onActiveScan() {
        TranslateTransition tt = new TranslateTransition();
        tt.setDuration(Duration.millis(500));
        tt.setNode(activateScanBtn);
        if (!isScanActivated){
            //active scan
            try {
                readBarcode.startBarcodeScanner(barcodeData -> {
                    barcodeTextField.setText(barcodeData);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            arrowIcon.rotateProperty().setValue(0);
            activateScanBtn.setStyle("-fx-background-color: #22DD24;");
            activateScanBtn.setText("Đang quét...");

            isScanActivated = true;
            tt.setByX(-194);
            tt.play();
            barcodeInputView.setVisible(true);
            FadeTransition hBoxFade = new FadeTransition(Duration.millis(1000), barcodeInputView);
            hBoxFade.setFromValue(0);
            hBoxFade.setToValue(1);
            hBoxFade.play();
        } else {
            //stop scan
            onStopScan();
            arrowIcon.rotateProperty().setValue(180);
            activateScanBtn.setStyle("-fx-background-color:  #76c8ee;");
            activateScanBtn.setText("Quét mã");

            isScanActivated = false;
            tt.setByX(194);
            tt.play();
            barcodeInputView.setVisible(false);
            FadeTransition hBoxFade = new FadeTransition(Duration.millis(1000), barcodeInputView);
            hBoxFade.setFromValue(1);
            hBoxFade.setToValue(0);
            hBoxFade.play();
        }
    }

    private void onStopScan() {
        try {
            if (readBarcode != null) {
                readBarcode.stopListening();
                System.out.println("Barcode scanner stopped");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onSearchByBarcode(){
        notifiTextField.setText("");
        String productID = barcodeTextField.getText().trim();

        if (productID.isEmpty()) {
            notifiTextField.setText("Vui lòng nhập mã sản phẩm");
        }
        showProductInTableByBarcode(productID);
    }

    private void showProductInTableByBarcode(String productID) {
        ObservableList<SanPham> soLoList = FXCollections.observableArrayList();
        for(SanPham sanPham : sanPhamObservableList){
            if(sanPham.getMaSP().equals(productID)){
                soLoList.add(sanPham);
            }
            tableDetail.setItems(soLoList);
        }
    }

    private void onSearchBtnClick() {
        notifiTextField.setText("");
        String productName = searchByProductNameField.getText().trim();

        if (productName.isEmpty()) {
            notifiTextField.setText("Vui lòng nhập tên sản phẩm");
        }
        showProductsInTable(productName);
    }

    private void showProductsInTable(String productName) {
        ObservableList<SanPham> soLoList = FXCollections.observableArrayList();
        for(SanPham sanPham : sanPhamObservableList){
            if(sanPham.getTenSP().equals(productName)){
                soLoList.add(sanPham);
            }
            tableDetail.setItems(soLoList);
        }
    }

    private void showProductInfoBySoLo(SanPham sanPham, String productName) {
        for (SanPham thuoc : sanPhamObservableList) {
            System.out.println("Thuoc: " + thuoc.getTenSP());
            if (productName.equals(thuoc.getTenSP()) ) {
                displayThuocInfo(thuoc);
                return;
            }
        }
        System.out.println("ko tim thay sp");
    }

    private void displayThuocInfo(SanPham thuoc) {
        productId.setText(thuoc.getMaSP());
        productName.setText(thuoc.getTenSP());
        importDate.setText(thuoc.getNgaySX().toString());
        expiryDate.setText(thuoc.getHanSD().toString());
        setColorExpiryDate(thuoc.getHanSD());
        weight.setText(String.valueOf(thuoc.getKhoiLuong()));
        description.setText(thuoc.getMoTa());
        productStatus.setText(thuoc.getTinhTrangSP().getTinhTrang());
        type.setText(thuoc.getLoaiSP().getLoaiSP());
        group.setText(thuoc.getNhomThuoc().getTenNhomThuoc());
        prescriptionRequired.setSelected(thuoc.isThuocKeDon());
        ingredient.setText(thuoc.getMoTa());
        sideEffect.setText(thuoc.getTacDungPhu());
        sellingPrice.setText(String.valueOf(thuoc.getGiaBan()));
        unit.setText(thuoc.getDonViTinh().getTenDonViTinh());
        origin.setText(thuoc.getNuocSX());
        brand.setText(thuoc.getThuongHieu());
    }

    private void setColorExpiryDate(LocalDate date) {
        if (date.isAfter(LocalDate.now())) {
            expiryDate.setStyle("-fx-text-fill: #ebb56d;"); // Màu vàng-đen (Olive)
        } else {
            expiryDate.setStyle("-fx-text-fill: red;");
        }
    }


    public void cleanUp(){
        onStopScan();
    }
}
