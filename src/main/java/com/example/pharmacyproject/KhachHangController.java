package com.example.pharmacyproject;

import com.example.pharmacyproject.PharmacyApplication;
import com.example.pharmacyproject.enitity.hang.Hang;
import model.KhachHang;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import service.KhachHangService;

import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class KhachHangController implements Initializable {
    private boolean isDataLoaded = false;
    private KhachHangService customerService;
    private static final int CLEAN_ADD_KH_INPUT = 1;
    private static final int CLEAN_UPDATE_INFO_INPUT = 2;
    private static final int CLEAN_DEL_KH_INPUT = 3;
    @FXML
    private TableColumn<KhachHang, String> maKHColumn;
    @FXML
    private TableColumn<KhachHang, String> hoTenColumn;
    @FXML
    private TableColumn<KhachHang, LocalDate> ngaySinhColumn;
    @FXML
    private TableColumn<KhachHang, Boolean> gioiTinhColumn;
    @FXML
    private TableColumn<KhachHang, String> sdtColumn;
    @FXML
    private TableColumn<KhachHang, LocalDate> ngayThamGiaColumn;
    @FXML
    private TableColumn<KhachHang, Double> diemTichLuyColumn;
    @FXML
    private TableColumn<KhachHang, LocalDate> hangColumn;
    @FXML
    private TableColumn<KhachHang, String> maHangKHColumn;

    @FXML
    private TableColumn<KhachHang, String> maKHColumnUpdate;
    @FXML
    private TableColumn<KhachHang, String> hoTenColumnUpdate;
    @FXML
    private TableColumn<KhachHang, LocalDate> ngaySinhColumnUpdate;
    @FXML
    private TableColumn<KhachHang, Boolean> gioiTinhColumnUpdate;
    @FXML
    private TableColumn<KhachHang, String> sdtColumnUpdate;
    @FXML
    private TableColumn<KhachHang, LocalDate> ngayThamGiaColumnUpdate;
    @FXML
    private TableColumn<KhachHang, Double> diemTichLuyColumnUpdate;
    @FXML
    private TableColumn<KhachHang, LocalDate> hangColumnUpdate;

    @FXML
    private TableColumn<KhachHang, String> maKHColumnDel;
    @FXML
    private TableColumn<KhachHang, String> hoTenColumnDel;
    @FXML
    private TableColumn<KhachHang, LocalDate> ngaySinhColumnDel;
    @FXML
    private TableColumn<KhachHang, Boolean> gioiTinhColumnDel;
    @FXML
    private TableColumn<KhachHang, String> sdtColumnDel;
    @FXML
    private TableColumn<KhachHang, LocalDate> ngayThamGiaColumnDel;
    @FXML
    private TableColumn<KhachHang, Double> diemTichLuyColumnDel;
    @FXML
    private TableColumn<KhachHang, LocalDate> hangColumnDel;




    @FXML
    private TextField maKHField;
    @FXML
    private TextField hoTenField;
    @FXML
    private TextField sdtField;
    @FXML
    private DatePicker ngaySinhPicker;
    @FXML
    private DatePicker ngayThamGiaPicker;
    @FXML
    private RadioButton namRadioBtn;
    @FXML
    private RadioButton nuRadioBtn;
    @FXML
    private Button addKHBtn;
    @FXML
    private Button clearAddInputBtn;

    @FXML
    private TextField updateMaKHField;
    @FXML
    private TextField updateHoTenField;
    @FXML
    private TextField updateSdtField;
    @FXML
    private DatePicker updateNgaySinhPicker;
    @FXML
    private DatePicker updateNgayThamGiaPicker;
    @FXML
    private RadioButton updateNamRadio;
    @FXML
    private RadioButton updateNuRadio;
    @FXML
    private Button updateInfoBtn;
    @FXML
    private Button clearUpdateInfoInputBtn;



    @FXML
    private Button delKHBtn;
    @FXML
    private TextField delMaKHField;
    @FXML
    private TextField delSdtField;
    @FXML
    private TextField delHoTenKHField;

    @FXML
    private TableView<KhachHang> khachHangTable;
    @FXML
    private TableView<KhachHang> updateInfoTable;
    @FXML
    private TableView<KhachHang> delKHTable;

    private ObservableList<KhachHang> khachHangList = FXCollections.observableArrayList();
    private ObservableList<Hang> hangList = FXCollections.observableArrayList();
    private FilteredList<KhachHang> updateFilteredData;
    private FilteredList<KhachHang> delFilteredData;


    private boolean checkAllInput(KhachHang khachHang) {
        if (khachHang.getHoTen().isEmpty()
                || khachHang.getSoDienThoai().isEmpty()
                || khachHang.getNgaySinh() == null
                || khachHang.getNgayThamGia() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi!");
            alert.setHeaderText("Vui lòng điền đầy đủ thông tin");
            alert.showAndWait();
            return false;
        } else if (khachHang.getSoDienThoai().length() != 10){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi!");
            alert.setHeaderText("Số điện thoại không hợp lệ");
            alert.showAndWait();
            return false;
        } else {
            return true;
        }

    }

    private void cleanInput(int type) {
        if (type == 1){
            khachHangTable.getSelectionModel().clearSelection();
            maKHField.clear();
            hoTenField.clear();
            sdtField.clear();
            ngaySinhPicker.setValue(null);
            ngayThamGiaPicker.setValue(null);
            namRadioBtn.setSelected(false);
            nuRadioBtn.setSelected(false);
        }
        if (type == 2) {
            updateInfoTable.getSelectionModel().clearSelection();
            updateMaKHField.clear();
            updateHoTenField.clear();
            updateSdtField.clear();
            updateNgaySinhPicker.setValue(null);
            updateNgayThamGiaPicker.setValue(null);
            updateNamRadio.setSelected(false);
            updateNuRadio.setSelected(false);
        }
//        if (type == 3) {
//            delKHTable.getSelectionModel().clearSelection();
//            delMaKHField.clear();
//            delSdtField.clear();
//            delHoTenKHField.clear();
//        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customerService = PharmacyApplication.getCustomerService();
        //declare observable list
        khachHangList.clear();
        hangList.clear();

        try {
            khachHangList.addAll(customerService.getAllKhachHang());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        setTableColumn();
        if (khachHangList == null){
            khachHangList = FXCollections.observableArrayList();
        }

        //declare FilteredList
        updateFilteredData = new FilteredList<>(khachHangList, p -> true);
        delFilteredData = new FilteredList<>(khachHangList, p -> true);

        khachHangTable.setItems(khachHangList);
        updateInfoTable.setItems(updateFilteredData);
//        delKHTable.setItems(delFilteredData);

        setEventListeners();
    }

    private void setEventListeners() {
        //radio button
        namRadioBtn.setOnMouseClicked(event -> nuRadioBtn.setSelected(false));
        nuRadioBtn.setOnMouseClicked(event -> namRadioBtn.setSelected(false));
        updateNamRadio.setOnMouseClicked(event -> updateNuRadio.setSelected(false));
        updateNuRadio.setOnMouseClicked(event -> updateNamRadio.setSelected(false));
        //btn
        addKHBtn.setOnMouseClicked(event -> onAddKhachHangClick());
        updateInfoBtn.setOnMouseClicked(event -> onUpdateKhachHangClick());
        clearAddInputBtn.setOnMouseClicked(event -> cleanInput(CLEAN_ADD_KH_INPUT));
        clearUpdateInfoInputBtn.setOnMouseClicked(event -> cleanInput(CLEAN_UPDATE_INFO_INPUT));
        // delKHTable row item click
//        delKHTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue != null){
//                KhachHang khachHang = (KhachHang) newValue;
//                delMaKHField.setText(khachHang.getMaKhachHang());
//                delSdtField.setText(khachHang.getSoDienThoai());
//                delHoTenKHField.setText(khachHang.getHoTen());
//            }
//        });

        // khachHangTable row item click
        khachHangTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                maKHField.setText(newValue.getMaKhachHang());
                hoTenField.setText(newValue.getHoTen());
                namRadioBtn.setSelected(newValue.isGioiTinh());
                nuRadioBtn.setSelected(!newValue.isGioiTinh());
                sdtField.setText(newValue.getSoDienThoai());
                ngaySinhPicker.setValue(newValue.getNgaySinh());
                ngayThamGiaPicker.setValue(newValue.getNgayThamGia());
            }
        });

        // delKHTable row item click
        updateInfoTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                KhachHang khachHang = (KhachHang) newValue;
                updateMaKHField.setText(khachHang.getMaKhachHang());
                updateHoTenField.setText(khachHang.getHoTen());
                updateNamRadio.setSelected(khachHang.isGioiTinh());
                updateNuRadio.setSelected(!khachHang.isGioiTinh());
                updateSdtField.setText(khachHang.getSoDienThoai());
                updateNgaySinhPicker.setValue(khachHang.getNgaySinh());
                updateNgayThamGiaPicker.setValue(khachHang.getNgayThamGia());
            }
        });



//        delKHBtn.setOnMouseClicked(event -> {
//            String maKH = delMaKHField.getText().trim();
//            String sdt = delSdtField.getText().trim();
//
//            // Kiểm tra nếu cả mã khách hàng và số điện thoại đều trống
//            if (maKH.isEmpty() && sdt.isEmpty()) {
//                Alert alert = new Alert(Alert.AlertType.WARNING);
//                alert.setTitle("Lỗi");
//                alert.setHeaderText("Vui lòng nhập mã khách hàng hoặc số điện thoại để thực hiện xóa.");
//                alert.showAndWait();
//                return;
//            }
//
//            // Tìm khách hàng theo mã khách hàng và số điện thoại
//            KhachHang khachHang = findKhachHangInTable(maKH, sdt);
//            if (khachHang == null) {
//                Alert alert = new Alert(Alert.AlertType.WARNING);
//                alert.setTitle("Lỗi");
//                alert.setHeaderText("Không tìm thấy thông tin khách hàng.");
//                alert.setContentText("Vui lòng kiểm tra lại mã khách hàng hoặc số điện thoại.");
//                alert.showAndWait();
//            } else {
//                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
//                confirmationAlert.setTitle("Xác nhận xóa");
//                confirmationAlert.setHeaderText("Bạn có chắc chắn muốn xóa khách hàng này không?");
//
//                Optional<ButtonType> result = confirmationAlert.showAndWait();
//                if (result.isPresent() && result.get() == ButtonType.OK) {
//                    try {
//                        if (customerService.removeCustomer(khachHang)) {
//                            // Xóa khách hàng khỏi danh sách và làm sạch dữ liệu đầu vào
//                            khachHangList.remove(khachHang);
//                            cleanInput(CLEAN_DEL_KH_INPUT);
//
//                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                            alert.setTitle("Thành công");
//                            alert.setHeaderText("Đã xóa thông tin khách hàng");
//                            alert.showAndWait();
//                        } else {
//                            Alert alert = new Alert(Alert.AlertType.ERROR);
//                            alert.setTitle("Lỗi");
//                            alert.setHeaderText("Xóa thông tin không thành công, hãy thử lại.");
//                            alert.showAndWait();
//                        }
//                    } catch (Exception e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            }
//        });


        //typing event
        updateSdtField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateInfoTable.getSelectionModel().clearSelection();
            updateFilteredData.setPredicate(khachHang -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return khachHang.getSoDienThoai().toLowerCase().contains(lowerCaseFilter);
            });
        });
//        delSdtField.textProperty().addListener((observable, oldValue, newValue) -> {
//            delKHTable.getSelectionModel().clearSelection();
//            delFilteredData.setPredicate(khachHang -> {
//                if (newValue == null || newValue.isEmpty()) {
//                    return true;
//                }
//                String lowerCaseFilter = newValue.toLowerCase();
//                return khachHang.getSoDienThoai().toLowerCase().contains(lowerCaseFilter);
//            });
//        });
    }

    private void onAddKhachHangClick() {
//        String maKH = "KH" + System.currentTimeMillis();
        KhachHang khachHang = new KhachHang();
        khachHang.setHoTen(hoTenField.getText().trim());
        khachHang.setSoDienThoai(sdtField.getText().trim());
        khachHang.setGioiTinh(namRadioBtn.isSelected());
        khachHang.setNgaySinh(ngaySinhPicker.getValue());
        khachHang.setNgayThamGia(ngayThamGiaPicker.getValue());

        if (checkAllInput(khachHang)) {
            try {
                if (customerService.checkDuplicateSDT(sdtField.getText().trim())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Số điện thoại khách hàng đã tồn tại");
                    alert.showAndWait();
                } else {
                    try {
                        if (customerService.addKhachHang(khachHang)) {
                            khachHangList.add(khachHang);
                            cleanInput(CLEAN_ADD_KH_INPUT);
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Thành công");
                            alert.setHeaderText("Lưu thông tin thành công");
                            alert.showAndWait();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Lưu thông tin không thành công, hãy thử lại");
                            alert.showAndWait();
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void onUpdateKhachHangClick() {
        String maKH = updateMaKHField.getText().trim();
        String sdtKH = updateSdtField.getText().trim();

        // Kiểm tra nếu mã khách hàng hoặc số điện thoại trống
        if (maKH.isEmpty() || sdtKH.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText("Vui lòng điền đầy đủ thông tin cần thiết");
            alert.showAndWait();
            return; // Kết thúc nếu dữ liệu chưa hợp lệ
        }

        // Tìm khách hàng theo mã khách hàng và số điện thoại
        KhachHang khachHang = findKhachHangInTable(maKH, sdtKH);
        if (khachHang != null) {
            String hoTen = updateHoTenField.getText().trim();
            boolean gioiTinhNam = updateNamRadio.isSelected();
            boolean gioiTinhNu = updateNuRadio.isSelected();
            LocalDate ngaySinh = updateNgaySinhPicker.getValue();
            LocalDate ngayThamGia = updateNgayThamGiaPicker.getValue();

            // Cập nhật thông tin nếu các trường không rỗng
            if (!hoTen.isEmpty()) khachHang.setHoTen(hoTen);
            if (gioiTinhNam) khachHang.setGioiTinh(true);
            if (gioiTinhNu) khachHang.setGioiTinh(false);
            if (ngaySinh != null) khachHang.setNgaySinh(ngaySinh);
            if (ngayThamGia != null) khachHang.setNgayThamGia(ngayThamGia);
            khachHang.setSoDienThoai(sdtKH);

            // Cập nhật thông tin trong danh sách khách hàng
            try {
                customerService.updateKhachHang(khachHang);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            khachHangList.set(khachHangList.indexOf(khachHang), khachHang);
            cleanInput(CLEAN_UPDATE_INFO_INPUT);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thành công");
            alert.setHeaderText("Đã cập nhật thông tin khách hàng");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Không tìm thấy thông tin khách hàng");
            alert.showAndWait();
        }
    }


    private KhachHang findKhachHangInTable(String maKH, String sdt) {
        for (KhachHang khachHang : khachHangList){
            if (khachHang.getMaKhachHang().equals(maKH) && khachHang.getSoDienThoai().equals(sdt)){
                return khachHang;
            }
        }
        return null;
    }

    private void setTableColumn() {
        // khachHangTable
        maKHColumn.setCellValueFactory(new PropertyValueFactory<>("maKhachHang"));
        hoTenColumn.setCellValueFactory(new PropertyValueFactory<>("hoTen"));
        ngaySinhColumn.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
        gioiTinhColumn.setCellValueFactory(new PropertyValueFactory<>("gioiTinh"));
        sdtColumn.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));
        ngayThamGiaColumn.setCellValueFactory(new PropertyValueFactory<>("ngayThamGia"));
        diemTichLuyColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getDiemTichLuy()).asObject());
        hangColumn.setCellValueFactory(new PropertyValueFactory<>("tenHang"));
        maHangKHColumn.setCellValueFactory(new PropertyValueFactory<>("maHang"));
        gioiTinhColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item ? "Nam" : "Nữ");
                }
            }
        });

        // updateInfoTable
        maKHColumnUpdate.setCellValueFactory(new PropertyValueFactory<>("maKhachHang"));
        hoTenColumnUpdate.setCellValueFactory(new PropertyValueFactory<>("hoTen"));
        ngaySinhColumnUpdate.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
        gioiTinhColumnUpdate.setCellValueFactory(new PropertyValueFactory<>("gioiTinh"));
        sdtColumnUpdate.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));
        ngayThamGiaColumnUpdate.setCellValueFactory(new PropertyValueFactory<>("ngayThamGia"));
        diemTichLuyColumnUpdate.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getDiemTichLuy()).asObject());
        hangColumnUpdate.setCellValueFactory(new PropertyValueFactory<>("tenHang"));
        gioiTinhColumnUpdate.setCellFactory(column -> new TableCell<KhachHang, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item ? "Nam" : "Nữ");
                }
            }
        });

//        // delKHTable
//        maKHColumnDel.setCellValueFactory(new PropertyValueFactory<>("maKhachHang"));
//        hoTenColumnDel.setCellValueFactory(new PropertyValueFactory<>("hoTen"));
//        ngaySinhColumnDel.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
//        gioiTinhColumnDel.setCellValueFactory(new PropertyValueFactory<>("gioiTinh"));
//        sdtColumnDel.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));
//        ngayThamGiaColumnDel.setCellValueFactory(new PropertyValueFactory<>("ngayThamGia"));
//        diemTichLuyColumnDel.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getDiemTichLuy()).asObject());
//        hangColumnDel.setCellValueFactory(new PropertyValueFactory<>("tenHang"));
//        gioiTinhColumnDel.setCellFactory(column -> new TableCell<KhachHang, Boolean>() {
//            @Override
//            protected void updateItem(Boolean item, boolean empty) {
//                super.updateItem(item, empty);
//                if (empty || item == null) {
//                    setText(null);
//                } else {
//                    setText(item ? "Nam" : "Nữ");
//                }
//            }
//        });

    }

}
