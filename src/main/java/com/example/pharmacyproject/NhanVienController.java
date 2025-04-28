package com.example.pharmacyproject;


import com.example.pharmacyproject.PharmacyApplication;
import com.example.pharmacyproject.enitity.phieunhap.ETrangThaiNhaCungCap;
import com.example.pharmacyproject.utils.EmployeeIdShortener;
import model.NhanVien;
import model.TaiKhoan;
import model.enums.ETinhTrangNhanVien;
import model.enums.VaiTro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import service.TaiKhoanService;
import service.NhanVienService;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class NhanVienController implements Initializable {
    public TableColumn ngaySinhNVUpdate;
    private NhanVienService employeeService;
    private TaiKhoanService accountService;
    private boolean isDataLoaded = false;
    private static final int CLEAN_ADD_NV_INPUT = 1;
    private static final int CLEAN_UPDATE_INPUT = 2;
    private static final int CLEAN_DEL_NV_INPUT = 3;
    private static final int CLEAN_DEL_TK_INPUT = 4;

    // Table them
    @FXML
    private TableView<NhanVien> addNhanVienTable;
    @FXML
    private TableColumn<NhanVien, String> maNVAdd;
    @FXML
    private TableColumn<NhanVien, String> emailAdd;

    @FXML
    private TableColumn<NhanVien, String> hoTenNVAdd;
    @FXML
    private TableColumn<NhanVien, String> cccdNVAdd;
    @FXML
    private TableColumn<NhanVien, Boolean> gioiTinhNVAdd;
    @FXML
    private TableColumn<NhanVien, String> sdtNVAdd;
    @FXML
    private TableColumn<NhanVien, LocalDate> ngaySinhNVAdd;
    @FXML
    private TableColumn<NhanVien, LocalDate> ngayVaoLamNVAdd;
    @FXML
    private TableColumn<NhanVien, String> ttlvNVAdd;
    @FXML
    private  TableColumn<NhanVien, VaiTro> chucVuNVAdd;
    @FXML
    private TableColumn<NhanVien, String> diaChiNVAdd;

    // Table update
    @FXML
    private TableView<NhanVien> updateNVTable;
    @FXML
    private TableColumn<NhanVien, String> maNVUpdate;
    @FXML
    private TableColumn<NhanVien, String> hoTenNVUpdate;
    @FXML
    private TableColumn<NhanVien, String> cccdNVUpdate;
    @FXML
    private TableColumn<NhanVien, String> emailNVUpdate;
    @FXML
    private TableColumn<NhanVien, String> sdtNVUpdate;
    @FXML
    private TableColumn<NhanVien, LocalDate> ngayVaoLamNVUpdate;
    @FXML
    private TableColumn<NhanVien, String> ttlvNVUpdate;
    @FXML
    private TableColumn<NhanVien, VaiTro> chucVuNVUpdate;
    @FXML
    private TableColumn<NhanVien, String> diaChiNVUpdate;

    // Table xoa
    @FXML
    private TableView<NhanVien> delNhanVienTable;

    @FXML
    private TableColumn<NhanVien, String> maNVDel;
    @FXML
    private TableColumn<NhanVien, String> hoTenNVDel;
    @FXML
    private TableColumn<NhanVien, String> cccdNVDel;
    @FXML
    private TableColumn<NhanVien, String> sdtNVDel;
    @FXML
    private TableColumn<NhanVien, LocalDate> ngayVaoLamNVDel;
    @FXML
    private TableColumn<NhanVien, String> ttlvNVDel;
//    @FXML
//    private TableColumn<NhanVien> caNVDel;
    @FXML
    private TableColumn<NhanVien, String> diaChiNVDel;
    @FXML
    private TableColumn<NhanVien, VaiTro> chucVuNVDel;

    // Table tai khoan
    @FXML
    private TableView<TaiKhoan> taiKhoanNhanVienTable;
    @FXML
    private TableColumn<TaiKhoan, String> maNVTk;
    @FXML
    private TableColumn<TaiKhoan, String> passWordTk;
    @FXML
    private TableColumn<TaiKhoan, VaiTro> chucVuNVTk;

    // Input fields Tai khoan
    @FXML
    private TextField maNVField;
    @FXML
    private ComboBox<String> chucVuCmbBoxTk;
    @FXML
    private Button updateTKBtn, xoaTKBtn;


    // Text file, combobox Them
    @FXML
    private TextField addMaNVField, addHoTenNVField, addSdtNVField, addCccdField, addDiaChiNV;
    @FXML
    private DatePicker addNgayVaoLamPicker;
    @FXML
    private RadioButton addNamNVRadioBtn, adNudNVRadioBtn;
    @FXML
    private Button addNVBtn;
    @FXML
    private ComboBox<String> addChucVuCombobox;
    @FXML
    private Button delONhap1;

    @FXML
    private  Button delONhap2;

    // Text file Cap Nhat
    @FXML
    private TextField updateMaNVField;
    @FXML
    private TextField updateHotenNVField;
    @FXML
    private TextField updateSdtNVField;
    @FXML
    private TextField updateEmail;
    @FXML
    private DatePicker updateNgayVaoLamPicker;
    @FXML
    private TextField updateCccdNVField;
    @FXML
    private TextField updateDiaChiNVField;
    @FXML
    private ComboBox<ETinhTrangNhanVien> updateTinhTrangLamViecCmbBox;
    @FXML
    private Button updateNVBtn;
    @FXML
    private ComboBox<String> chucVuNVUpdateCombobox;

    @FXML
    private TextField addEmailField;
    // xoa
    @FXML
    private TextField delMaNVField;

    @FXML
    private TextField delHoTenNVField;

    @FXML
    private TextField delSdtNVfField;

    @FXML
    private Button delNVBtn;


    private ObservableList<NhanVien> nhanVienList;
    private ObservableList<TaiKhoan> taiKhoanList;
    private FilteredList<NhanVien> updateFilteredData;
    private FilteredList<NhanVien> delFilteredData;
    private FilteredList<TaiKhoan> tkFilteredData;

    // Khoi tao
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Khởi tạo đối tượng EmployeeService   
        employeeService = PharmacyApplication.getEmployeeService();
        accountService = PharmacyApplication.getAccountService();
        // Khai báo danh sách observable
        nhanVienList = FXCollections.observableArrayList();
        taiKhoanList = FXCollections.observableArrayList();

        if (!isDataLoaded) {
            try {
                nhanVienList.addAll(employeeService.getAll());
                taiKhoanList.addAll(accountService.getAll());
                isDataLoaded = true;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        setTableCollums();

        // Khai báo FilteredList
        updateFilteredData = new FilteredList<>(nhanVienList, p -> true);
        delFilteredData = new FilteredList<>(nhanVienList, p -> true);
        tkFilteredData = new FilteredList<>(taiKhoanList, p -> true);

        addNhanVienTable.setItems(nhanVienList);
        updateNVTable.setItems(updateFilteredData);
        delNhanVienTable.setItems(delFilteredData);
        taiKhoanNhanVienTable.setItems(tkFilteredData);



        setEventListeners();
        addChucVuCombobox.getItems().addAll(VaiTro.NHANVIEN.toString(), VaiTro.QUANLY.toString());

        chucVuNVUpdateCombobox.getItems().addAll(VaiTro.NHANVIEN.toString(), VaiTro.QUANLY.toString());
        updateTinhTrangLamViecCmbBox.getItems().addAll(ETinhTrangNhanVien.NGHI_VIEC, ETinhTrangNhanVien.DANG_LAM_VIEC, ETinhTrangNhanVien.NGHI_PHEP);

        chucVuCmbBoxTk.getItems().addAll(VaiTro.NHANVIEN.toString(), VaiTro.QUANLY.toString());
    }


    // Set su kien cho cac nut
    private void setEventListeners() {

        //Them nhan vien
        addNVBtn.setOnMouseClicked(event -> {
            try {
                onAddNhanVienClick();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        delONhap1.setOnMouseClicked(event -> cleanInput(CLEAN_ADD_NV_INPUT));

        // Click table them
        addNhanVienTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                NhanVien nhanVien = (NhanVien) newValue;
                // Populate fields with selected employee data
                addMaNVField.setText(nhanVien.getMaNhanVien());
                addHoTenNVField.setText(nhanVien.getHoTen());
                addSdtNVField.setText(nhanVien.getSdt());
                addEmailField.setText(nhanVien.getEmail());
                addCccdField.setText(nhanVien.getCccd());
                addDiaChiNV.setText(nhanVien.getDiaChi());
                addNgayVaoLamPicker.setValue(nhanVien.getNgayVaoLam());
                addChucVuCombobox.setValue(nhanVien.getTaiKhoan().getVaiTro().toString());
            }
        });



        updateNVBtn.setOnMouseClicked(event -> {
            try {
                onUpdateNhanVienClick();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        delONhap2.setOnMouseClicked(event -> cleanInput(CLEAN_UPDATE_INPUT));

        updateNVTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                NhanVien nhanVien = (NhanVien) newValue;

                updateMaNVField.setText(nhanVien.getMaNhanVien());
                updateHotenNVField.setText(nhanVien.getHoTen());
                updateSdtNVField.setText(nhanVien.getSdt());
                updateEmail.setText(nhanVien.getEmail());
                updateCccdNVField.setText(nhanVien.getCccd());
                updateDiaChiNVField.setText(nhanVien.getDiaChi());
                updateNgayVaoLamPicker.setValue(nhanVien.getNgayVaoLam());
                updateTinhTrangLamViecCmbBox.setValue(nhanVien.getTinhTrangNhanVien());
                chucVuNVUpdateCombobox.setValue(nhanVien.getTaiKhoan().getVaiTro().toString());
            }
        });

        // Thiết lập lập listener cho ô nhập mã nhân viên
        updateMaNVField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateFilteredData.setPredicate(nhanVien -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Hiển thị tất cả khi ô trống
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return nhanVien.getMaNhanVien().toLowerCase().contains(lowerCaseFilter); // Lọc theo mã nhân viên
            });
        });

        updateEmail.textProperty().addListener((observable, oldValue, newValue) -> {
            updateFilteredData.setPredicate(nhanVien -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Hiển thị tất cả khi ô trống
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return nhanVien.getEmail().toLowerCase().contains(lowerCaseFilter); // Lọc theo email
            });
        });

        // Thiết lập listener cho ô nhập họ tên

        updateHotenNVField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateFilteredData.setPredicate(nhanVien -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Hiển thị tất cả khi ô trống
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return nhanVien.getHoTen().toLowerCase().contains(lowerCaseFilter); // Lọc theo họ tên
            });
        });

        // Thiết lập listener cho ô nhập số điện thoại
        updateSdtNVField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateFilteredData.setPredicate(nhanVien -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Hiển thị tất cả khi ô trống
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return nhanVien.getSdt().toLowerCase().contains(lowerCaseFilter); // Lọc theo số điện thoại
            });
        });

        // Chinh sua nhan vien
        updateNVBtn.setOnMouseClicked(event -> {
            try {
                onUpdateNhanVienClick();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        // Xoa nhan vien
        delNVBtn.setOnMouseClicked(event -> {
            try {
                onDeleteNhanVienClick();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        // Xoa tai khoan
        xoaTKBtn.setOnMouseClicked(event -> {
            try {
                onDeleteTaiKhoanClick();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        // Cap nhat tai khoan
        updateTKBtn.setOnMouseClicked(event -> onUpdateTaiKhoanClick());

        // Xoa nhan vien
        delNhanVienTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                NhanVien nhanVien = (NhanVien) newValue;

                delMaNVField.setText(nhanVien.getMaNhanVien());
                delHoTenNVField.setText(nhanVien.getHoTen());
                delSdtNVfField.setText(nhanVien.getSdt());
            }
        });

        // Table tai khoan
        taiKhoanNhanVienTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                maNVField.setText(newValue.getMaTaiKhoan());
                chucVuCmbBoxTk.setValue(newValue.getVaiTro().toString());
            }
        });

        // Thiết lập listener cho ô nhập mã nhân viên
        delMaNVField.textProperty().addListener((observable, oldValue, newValue) -> {
            delFilteredData.setPredicate(nhanVien -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Hiển thị tất cả khi ô trống
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return nhanVien.getMaNhanVien().toLowerCase().contains(lowerCaseFilter); // Lọc theo mã nhân viên
            });
        });

        // Thiết lập listener cho ô nhập họ tên
        delHoTenNVField.textProperty().addListener((observable, oldValue, newValue) -> {
            delFilteredData.setPredicate(nhanVien -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Hiển thị tất cả khi ô trống
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return nhanVien.getHoTen().toLowerCase().contains(lowerCaseFilter); // Lọc theo họ tên
            });
        });



        // Thiết lập listener cho ô nhập số điện thoại
        delSdtNVfField.textProperty().addListener((observable, oldValue, newValue) -> {
            delFilteredData.setPredicate(nhanVien -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Hiển thị tất cả khi ô trống
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return nhanVien.getSdt().toLowerCase().contains(lowerCaseFilter); // Lọc theo số điện thoại
            });
        });

        // Tab tai khoan
        maNVField.textProperty().addListener((observable, oldValue, newValue) -> {
            tkFilteredData.setPredicate(taiKhoan -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return taiKhoan.getMaTaiKhoan().toLowerCase().contains(lowerCaseFilter);
            });
        });



    }

    private void setTableCollums() {
        // Thiết lập CellValueFactory cho các cột
        maNVAdd.setCellValueFactory(new PropertyValueFactory<>("maNhanVien"));
        hoTenNVAdd.setCellValueFactory(new PropertyValueFactory<>("hoTen"));
        emailAdd.setCellValueFactory(new PropertyValueFactory<>("email"));
        cccdNVAdd.setCellValueFactory(new PropertyValueFactory<>("cccd"));
        sdtNVAdd.setCellValueFactory(new PropertyValueFactory<>("sdt"));
        ngayVaoLamNVAdd.setCellValueFactory(new PropertyValueFactory<>("ngayVaoLam"));
        ttlvNVAdd.setCellValueFactory(new PropertyValueFactory<>("tinhTrangNhanVien"));
        diaChiNVAdd.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
        chucVuNVAdd.setCellValueFactory(new PropertyValueFactory<>("vaiTro"));


        chucVuNVAdd.setCellFactory(column -> new TableCell<NhanVien, VaiTro>() {
            @Override
            protected void updateItem(VaiTro item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString()); // Hoặc tùy chỉnh hiển thị
                }
            }
        });

       // Table update
        maNVUpdate.setCellValueFactory(new PropertyValueFactory<>("maNhanVien"));
        hoTenNVUpdate.setCellValueFactory(new PropertyValueFactory<>("hoTen"));
        cccdNVUpdate.setCellValueFactory(new PropertyValueFactory<>("cccd"));
        emailNVUpdate.setCellValueFactory(new PropertyValueFactory<>("email"));
        sdtNVUpdate.setCellValueFactory(new PropertyValueFactory<>("sdt"));
        ngayVaoLamNVUpdate.setCellValueFactory(new PropertyValueFactory<>("ngayVaoLam"));
        ttlvNVUpdate.setCellValueFactory(new PropertyValueFactory<>("tinhTrangNhanVien"));
        diaChiNVUpdate.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
        chucVuNVUpdate.setCellValueFactory(new PropertyValueFactory<>("vaiTro"));


        chucVuNVUpdate.setCellFactory(column -> new TableCell<NhanVien, VaiTro>() {
            @Override
            protected void updateItem(VaiTro item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString());
                }
            }
        });


        // Table xoa
        maNVDel.setCellValueFactory(new PropertyValueFactory<>("maNhanVien"));
        hoTenNVDel.setCellValueFactory(new PropertyValueFactory<>("hoTen"));
        cccdNVDel.setCellValueFactory(new PropertyValueFactory<>("cccd"));
        sdtNVDel.setCellValueFactory(new PropertyValueFactory<>("sdt"));
        ngayVaoLamNVDel.setCellValueFactory(new PropertyValueFactory<>("ngayVaoLam"));
        ttlvNVDel.setCellValueFactory(new PropertyValueFactory<>("tinhTrangNhanVien"));
        diaChiNVDel.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
        chucVuNVDel.setCellValueFactory(new PropertyValueFactory<>("vaiTro"));



        chucVuNVDel.setCellFactory(column -> new TableCell<NhanVien, VaiTro>() {
            @Override
            protected void updateItem(VaiTro item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString()); // Customize display as needed
                }
            }
        });


        // Tai khoan
        // Correct CellValueFactory for maNVTk
        maNVTk.setCellValueFactory(new PropertyValueFactory<>("maTaiKhoan")); // Ensure this matches the getter in TaiKhoan

        chucVuNVTk.setCellValueFactory(new PropertyValueFactory<>("vaiTro")); // Ensure this matches the getter in TaiKhoan

        chucVuNVTk.setCellFactory(column -> new TableCell<TaiKhoan, VaiTro>() {
            @Override
            protected void updateItem(VaiTro item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString()); // Display the enum value as text
                }
            }
        });
    }

    private boolean checkAllInput(String maNV, String hoTen, String email,  String sdt, LocalDate ngayVaoLam, String diaChi, String cccd) {
        // Basic text field validations
        if (maNV.isEmpty() || hoTen.isEmpty() || sdt.isEmpty() || ngayVaoLam == null || diaChi.isEmpty() || cccd.isEmpty()) {
            showAlert("Lỗi!", "Vui lòng điền đầy đủ thông tin");
            return false;
        }

        // Phone number validation: must be 10 digits, all numeric, and start with "0"
        if (sdt.length() != 10 || !sdt.matches("\\d+") || !sdt.startsWith("0")) {
            showAlert("Lỗi!", "Số điện thoại không hợp lệ. Số điện thoại phải có 10 chữ số, bắt đầu bằng '0'.");
            return false;
        }
        if (email.isEmpty()) {
            showAlert("Lỗi!", "Email không được để trống");
            return false;
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            showAlert("Lỗi!", "Email không hợp lệ");
            return false;
        }

        // CCCD validation: must be 12 digits, all numeric, and start with "1"
        if (cccd.length() != 12 || !cccd.matches("\\d+")) {
            showAlert("Lỗi!", "CCCD không hợp lệ. CCCD phải có 12 chữ số.");
            return false;
        }

        if (addChucVuCombobox.getValue() == null || addChucVuCombobox.getValue().isEmpty()) {
            showAlert("Lỗi!", "Vui lòng chọn chức vụ");
            return false;
        }

        return true;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    private void cleanInput(int type) {
        if (type == 1) {
            addMaNVField.clear();
            addHoTenNVField.clear();
            addSdtNVField.clear();
            addNgayVaoLamPicker.setValue(null);
            addDiaChiNV.clear();
            addCccdField.clear();
            addChucVuCombobox.setItems(null);
        }
        if (type == 2) {
            updateMaNVField.clear();
            updateHotenNVField.clear();
            updateSdtNVField.clear();
            updateCccdNVField.clear();
            updateDiaChiNVField.clear();
            updateNgayVaoLamPicker.setValue(null);
            updateTinhTrangLamViecCmbBox.setValue(null);
            chucVuNVUpdateCombobox.setValue(null);
            updateEmail.clear();
        }

        if (type == 3) {
            delMaNVField.clear();
            delHoTenNVField.clear();
            delSdtNVfField.clear();
        }

        if (type == 4) {
            maNVField.clear();
            chucVuCmbBoxTk.setValue(null);
        }
    }

    private void onAddNhanVienClick() throws Exception {
        // Kiểm tra các trường bắt buộc
        if (addChucVuCombobox.getValue() == null || addChucVuCombobox.getValue().isEmpty()) {
            // Thông báo cho người dùng rằng trường chức vụ là bắt buộc
            showAlert("Lỗi!","Chức vụ không được để trống.");
            return;
        }



        String maNV = "NV" + EmployeeIdShortener.stringShortener(String.valueOf(System.currentTimeMillis()));
        System.out.println("Check maNV generation: " + maNV);
        String hoTen = addHoTenNVField.getText().trim();
        String email = addEmailField.getText().trim();
        String sdt = addSdtNVField.getText().trim();
        LocalDate ngayVaoLam = addNgayVaoLamPicker.getValue();
        String diaChi = addDiaChiNV.getText().trim();
        String cccd = addCccdField.getText().trim();
        ETinhTrangNhanVien tinhTrangNv = ETinhTrangNhanVien.DANG_LAM_VIEC;



        // Kiểm tra và thêm nhân viên vào danh sách
        if (checkAllInput(maNV, hoTen,email, sdt, ngayVaoLam, diaChi, cccd)) {

            // Kiểm tra các trường bắt buộc
            if (addChucVuCombobox.getValue() == null || addChucVuCombobox.getValue().isEmpty()) {
                // Thông báo cho người dùng rằng trường chức vụ là bắt buộc
                showAlert("Lỗi!","Chức vụ không được để trống.");
                return;
            }



            VaiTro chucVu = getVaiTro(addChucVuCombobox.getValue());

            //Create Tai Khoan
            TaiKhoan taiKhoan = new TaiKhoan();
            taiKhoan.setMaTaiKhoan(maNV);
            taiKhoan.setVaiTro(chucVu);
            taiKhoan.setPassword("123456");

            // Create employee object
            NhanVien nhanVien = new NhanVien();
            nhanVien.setHoTen(hoTen);
            nhanVien.setEmail(email);
            nhanVien.setSdt(sdt);
            nhanVien.setCccd(cccd);
            nhanVien.setDiaChi(diaChi);
            nhanVien.setNgayVaoLam(ngayVaoLam);
            nhanVien.setTinhTrangNhanVien(tinhTrangNv);


                if (employeeService.themNhanVien(nhanVien)) {
                    // Add the new employee to the list
                    nhanVienList.add(nhanVien);
                    cleanInput(CLEAN_ADD_NV_INPUT);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thành công");
                    alert.setHeaderText("Lưu thông tin nhân viên thành công \n Mã nhân viên là " + maNV + " và mật khẩu mặc định là 1111");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Lỗi");
                    alert.setHeaderText("Lưu thông tin nhân viên không thành công, hãy thử lại");
                    alert.showAndWait();
                }
            }


    }

    private VaiTro getVaiTro(String value) {
        if (value.equalsIgnoreCase(VaiTro.NHANVIEN.toString())) {
            return VaiTro.NHANVIEN;
        } else if (value.equalsIgnoreCase(VaiTro.QUANLY.toString())) {
            return VaiTro.QUANLY;
        }
        return null;
    }

    private NhanVien findNhanVienInTable(String maNV) {
        for (NhanVien nhanVien : nhanVienList) {
            if (nhanVien.getMaNhanVien().equals(maNV)){
                return nhanVien;
            }
        }
        return null;
    }

    private void onUpdateNhanVienClick() throws Exception {
        String maNV = updateMaNVField.getText().trim();
        String sdtNV = updateSdtNVField.getText().trim();

        if (!maNV.isEmpty() || !sdtNV.isEmpty()) {
            NhanVien nhanVien = findNhanVienInTable(maNV);

            if (nhanVien != null) {
                String hoTen = updateHotenNVField.getText().trim();
                LocalDate ngayVaoLam = updateNgayVaoLamPicker.getValue();
                String cccd = updateCccdNVField.getText().trim();
                String email = updateEmail.getText().trim();
                String diaChi = updateDiaChiNVField.getText().trim();
                ETinhTrangNhanVien tinhTrangLamViec = updateTinhTrangLamViecCmbBox.getValue();

                if (!hoTen.isEmpty()) nhanVien.setHoTen(hoTen);
                if (ngayVaoLam != null) nhanVien.setNgayVaoLam(ngayVaoLam);
                if (!cccd.isEmpty()) nhanVien.setCccd(cccd);
                if (!diaChi.isEmpty()) nhanVien.setDiaChi(diaChi);
                if (!email.isEmpty()) nhanVien.setEmail(email);


                if (tinhTrangLamViec != null) {
                    nhanVien.setTinhTrangNhanVien(tinhTrangLamViec);
                }



                // Call the method to update employee info in the database
                if (employeeService.update(nhanVien)) {
                    // Update the list view if needed
                    int index = nhanVienList.indexOf(nhanVien); // Update with employee list if you have it
                    if (index != -1) {
                        nhanVienList.set(index, nhanVien); // Update in the list
                    }

                    // Clear inputs after update
                    cleanInput(CLEAN_UPDATE_INPUT);

                    // Show success alert
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thành công");
                    alert.setHeaderText("Đã cập nhật thông tin nhân viên");
                    alert.showAndWait();
                }else {
                    // Show error if employee is not found
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Không tìm thấy thông tin nhân viên");
                    alert.showAndWait();
                }
            }
        }
        else  {
            // Show warning if no identifier is provided
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText("Vui lòng nhập nội dung để cập nhật");
            alert.showAndWait();
        }
    }

    private void onDeleteNhanVienClick() throws Exception {
        String maNV = delMaNVField.getText().trim();

        NhanVien nhanVienCanXoa = null;
        if (!maNV.isEmpty()) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Xác nhận xóa");
            confirmationAlert.setHeaderText("Bạn có chắc chắn muốn xóa nhân viên này không?");
            confirmationAlert.setContentText("Nhân viên sẽ bị xóa vĩnh viễn khỏi cơ sở dữ liệu.");

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

            for (NhanVien nhanVien : nhanVienList) {
                if (nhanVien.getMaNhanVien().equals(maNV)) {
                    nhanVienCanXoa = nhanVien;
                    break;
                }
            }
            boolean isDeleted = employeeService.xoaNhanVien(nhanVienCanXoa.getMaNhanVien());
            if (isDeleted) {
                cleanInput(CLEAN_DEL_NV_INPUT);
                taiKhoanList.removeIf(taiKhoan -> taiKhoan.getMaTaiKhoan().equals(maNV));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thành công");
                alert.setHeaderText("Đã xóa nhân viên thành công");
                alert.showAndWait();

                nhanVienList.removeIf(nhanVien -> nhanVien.getMaNhanVien().equals(maNV));

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Không tìm thấy thông tin nhân viên");
                alert.showAndWait();
            }
        }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText("Vui lòng nhập mã nhân viên để xóa");
            alert.showAndWait();
        }
    }

    private void onDeleteTaiKhoanClick() throws Exception {
        String tenTaiKhoan = maNVField.getText().trim();

        if (!tenTaiKhoan.isEmpty()) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Xác nhận xóa");
            confirmationAlert.setHeaderText("Bạn có chắc chắn muốn xóa tài khoản này không?");
            confirmationAlert.setContentText("Tài khoản sẽ bị xóa vĩnh viễn khỏi cơ sở dữ liệu.");

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
            // Gọi phương thức để xóa tài khoản
                boolean isDeleted = false;
                try {
                    isDeleted =  accountService.xoaTaiKhoan(tenTaiKhoan);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                if (isDeleted) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thành công");
                alert.setHeaderText("Đã xóa tài khoản thành công");
                alert.showAndWait();

                // Làm sạch input sau khi xóa
                    cleanInput(CLEAN_DEL_TK_INPUT);

                // Cập nhật danh sách tài khoản
                taiKhoanList.removeIf(taiKhoan -> taiKhoan.getMaTaiKhoan().equals(tenTaiKhoan));

                // Làm mới danh sách đã lọc
                tkFilteredData.setPredicate(p -> true);

                // Cập nhật bảng hiển thị tài khoản
                taiKhoanNhanVienTable.setItems(tkFilteredData);
            } else {
                // Thông báo không tìm thấy tài khoản
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Không tìm thấy tài khoản");
                alert.showAndWait();
            }
        }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText("Vui lòng nhập mã tài khoản để xóa");
            alert.showAndWait();
        }
    }

    private TaiKhoan findTaiKhoanInTable(String tenTaiKhoan) {
        for (TaiKhoan taiKhoan : taiKhoanList) {
            if (taiKhoan.getMaTaiKhoan().equalsIgnoreCase(tenTaiKhoan)) {
                return taiKhoan; // Return the account if found
            }
        }
        return null; // Return null if no account matches
    }


    private void onUpdateTaiKhoanClick() {
        String tenTaiKhoan = maNVField.getText().trim();
        String chucVuDisplayName = chucVuCmbBoxTk.getValue();

        // Check if the account username is provided
        if (tenTaiKhoan.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng nhập mã tài khoản để cập nhật");
            return; // Exit the method early if the username is empty
        }

        // Find the account based on the username
        TaiKhoan taiKhoan = findTaiKhoanInTable(tenTaiKhoan);
        taiKhoan.setVaiTro(VaiTro.valueOf(chucVuDisplayName));
        if (taiKhoan == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Không tìm thấy tài khoản");
            return; // Exit if account not found
        }

        try {
            accountService.update(taiKhoan);
            updateTaiKhoanList(taiKhoan);
            cleanInput(CLEAN_DEL_TK_INPUT);
            showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đã cập nhật thông tin tài khoản");
        } catch (Exception e) {
            // Handle any exceptions that may arise during the update
            showAlert(Alert.AlertType.ERROR, "Error", "Không thể cập nhật tài khoản: " + e.getMessage());
        }
    }

    // Helper method to show alerts
    private void showAlert(Alert.AlertType alertType, String title, String headerText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    // Helper method to update the TaiKhoan list
    private void updateTaiKhoanList(TaiKhoan taiKhoan) {
        int index = taiKhoanList.indexOf(taiKhoan);
        if (index != -1) {
            taiKhoanList.set(index, taiKhoan);
        }
    }

}
