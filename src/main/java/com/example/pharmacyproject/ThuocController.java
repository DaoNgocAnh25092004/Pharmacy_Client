package com.example.pharmacyproject;

import com.example.pharmacyproject.PharmacyApplication;
import com.example.pharmacyproject.utils.RandomGenerator;
import model.DonViTinh;
import model.NhomThuoc;
import model.SanPham;
import model.enums.ELoaiSanPham;
import model.enums.ETinhTrangSP;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import service.DonViTinhService;
import service.NhomThuocService;
import service.SanPhamService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.*;


public class ThuocController implements Initializable {
    private SanPhamService productService;
    private DonViTinhService donViTinhService;
    private boolean isDataLoaded = false;
    private static final int CLEAN_ADD_Thuoc_INPUT = 1;
    private static final int CLEAN_UPDATE_Thuoc_INPUT = 2;
    private static final int CLEAN_DEL_Thuoc_INPUT = 3;

    // Du lieu them
    @FXML
    private TableView<SanPham> thuocTableAdd;
    @FXML
    private TableColumn<SanPham, String> maThuocAdd;
    @FXML
    private TableColumn<SanPham, String> tenThuocAdd;
    @FXML
    private TableColumn<SanPham, ETinhTrangSP> ttThuocAdd;
    @FXML
    private TableColumn<SanPham, LocalDate> ngayNhapThuocAdd;
    @FXML
    private TableColumn<SanPham, LocalDate> hanSuDungThuocAdd;
    @FXML
    private TableColumn<SanPham, Double> khoiLuongThuocAdd;
    @FXML
    private TableColumn<SanPham, String> thuocKeDonThuocAdd;
    @FXML
    private TableColumn<SanPham, String> dvtThuocAdd;
    @FXML
    private TableColumn<SanPham, String> thuongHieuThuocAdd;
    @FXML
    private TableColumn<SanPham, String> lieuLuongThuocAdd;
    @FXML
    private TableColumn<SanPham, String> nsxThuocAdd;
    @FXML
    private TableColumn<SanPham, String> moTaThuocAdd;
    @FXML
    private TableColumn<SanPham, String> tdpThuocAdd;
    @FXML
    private TableColumn<SanPham, Double> giaBanThuocAdd;
    @FXML
    private ComboBox<NhomThuoc> nhomThuocField;
    @FXML
    private TextField maThuocAddField;
    @FXML
    private TextField tenThuocAddField;
    @FXML
    private DatePicker ngayNhapThuocAddField;
    @FXML
    private DatePicker hanSuDungThuocAddField;
    @FXML
    private TextField khoiLuongThuocAddField;
    @FXML
    private ComboBox<ETinhTrangSP> ccbttThuocAdd;
    @FXML
    private ComboBox<DonViTinh> dvtThuocAddField;
    @FXML
    private TextField nsxThuocAddField;
    @FXML
    private TextField thuongHieuThuocAddField;
    @FXML
    private TextField moTaThuocAddField;
    @FXML
    private ComboBox<ELoaiSanPham> loaiSPComboBox;
    @FXML
    private ComboBox<ELoaiSanPham> loaiSPCBUpdate;
    @FXML
    private TextField tdpThuocAddField;
    @FXML
    private CheckBox tkdAddField;
    @FXML
    private TextField giaBanAddField;

    @FXML
    private Button btnAddThuoc;
    @FXML
    private Button xoaRongAdd;
    @FXML
    private Button xacNhanButton;
    @FXML
    private ComboBox<String> comboBoxNhaCC;

    // Du lieu sua
    @FXML
    private TableView<SanPham> thuocTableUpdate;
    @FXML
    private TableColumn<SanPham, String> maThuocUpdate;
    @FXML
    private TableColumn<SanPham, String> tenThuocUpdate;
    @FXML
    private TableColumn<SanPham, ETinhTrangSP> ttThuocUpdate;
    @FXML
    private TableColumn<SanPham, LocalDate> ngayNhapThuocUpdate;
    @FXML
    private TableColumn<SanPham, LocalDate> hanSuDungThuocUpdate;
    @FXML
    private TableColumn<SanPham, Double> khoiLuongThuocUpdate;
    @FXML
    private TableColumn<SanPham, String> thuocKeDonThuocUpdate;
    @FXML
    private TableColumn<SanPham, String> dvtThuocUpdate;
    @FXML
    private TableColumn<SanPham, String> thuongHieuThuocUpdate;
    @FXML
    private TableColumn<SanPham, String> lieuLuongThuocUpdate;
    @FXML
    private TableColumn<SanPham, String> nsxThuocUpdate;
    @FXML
    private TableColumn<SanPham, String> moTaThuocUpdate;
    @FXML
    private TableColumn<SanPham, String> tdpThuocUpdate;
    @FXML
    private TableColumn<SanPham, String> thanhPhanThuocUpdate;
    @FXML
    private TableColumn<SanPham, Double> thueSuatThuocUpdate;
    @FXML
    private TableColumn<SanPham, Double> giaNhapThuocUpdate;
    @FXML
    private TableColumn<SanPham, Double> loiNhuanThuocUpdate;
    @FXML
    private TableColumn<SanPham, Double> giaBanThuocUpdate;

    @FXML
    private TextField maThuocUpdateField;
    @FXML
    private TextField tenThuocUpdateField;
    @FXML
    private DatePicker ngayNhapThuocUpdateField;
    @FXML
    private DatePicker hanSuDungThuocUpdateField;
    @FXML
    private TextField khoiLuongThuocUpdateField;
    @FXML
    private ComboBox<ETinhTrangSP> ccbttThuocUpdate;
    @FXML
    private ComboBox<DonViTinh> dvtThuocUpdateField;
    @FXML
    private TextField nsxThuocUpdateField;
    @FXML
    private TextField thuongHieuThuocUpdateField;
    @FXML
    private TextField moTaThuocUpdateField;
    @FXML
    private TextField giaNhapThuocUpdateField;
    @FXML
    private TextField thueSuatThuocUpdateField;
    @FXML
    private TextField loiNhuanThuocUpdateField;
    @FXML
    private TextField thanhPhanThuocUpdateField;
    @FXML
    private ComboBox<NhomThuoc> nhomThuocUpdateField;
    @FXML
    private TextField tdpThuocUpdateField;
    @FXML
    private TextField giaBanThuocUpdateField;
    @FXML
    private CheckBox tkdUpdateField;

    @FXML
    private Button btnUpdateThuoc;
    @FXML
    private Button xoaRongUpdate;

    // Du lieu xoa
    @FXML
    private TableView<SanPham> thuocTableDel;
    @FXML
    private TableColumn<SanPham, String> maThuocDel;
    @FXML
    private TableColumn<SanPham, String> tenThuocDel;
    @FXML
    private TableColumn<SanPham, ETinhTrangSP> ttThuocDel;
    @FXML
    private TableColumn<SanPham, LocalDate> hanSuDungThuocDel;
    @FXML
    private TableColumn<SanPham, Double> khoiLuongThuocDel;
    @FXML
    private TableColumn<SanPham, String> thuocKeDonThuocDel;
    @FXML
    private TableColumn<SanPham, String> dvtThuocDel;
    @FXML
    private TableColumn<SanPham, String> thuongHieuThuocDel;
    @FXML
    private TableColumn<SanPham, String> nsxThuocDel;
    @FXML
    private TableColumn<SanPham, String> moTaThuocDel;
    @FXML
    private TableColumn<SanPham, String> tdpThuocDel;
    @FXML
    private TableColumn<SanPham, Double> giaBanThuocDel;
    @FXML
    private TextField soLoAddField;

    @FXML
    private TextField maThuocDelField;
    @FXML
    private TextField tenThuocDelField;
    @FXML
    private DatePicker ngayNhapThuocDelField;

    @FXML
    private Button btnDelThuoc;

    private ObservableList<SanPham> thuocList;
    private FilteredList<SanPham> addFilteredData;
    private FilteredList<SanPham> updateFilteredData;
    private FilteredList<SanPham> delFilteredData;
    private List<DonViTinh> donViTinhs;
    private List<NhomThuoc> nhomThuocs;
    private NhomThuocService nhomThuocService;

    private ObservableList<SanPham> thuocObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productService = PharmacyApplication.getProductService();
        donViTinhService = PharmacyApplication.getDonViTinhService();
        nhomThuocService = PharmacyApplication.getNhomThuocService();

        try {
            donViTinhs = donViTinhService.getAll();
            nhomThuocs = nhomThuocService.getAll();
            nhomThuocs.forEach(nhom -> {
                System.out.println(nhom.getTenNhomThuoc());
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("nhom thuoc null");
        }
        // Khai báo danh sách observable
        thuocList = FXCollections.observableArrayList();

        if (!isDataLoaded) {
            try {
                productService.getAllSanPham();
                isDataLoaded = true;
                thuocList.addAll(productService.getAllSanPham());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        
        setTableCollums();

        addFilteredData = new FilteredList<>(thuocList, p -> true);
        updateFilteredData = new FilteredList<>(thuocList, p -> true);
        delFilteredData = new FilteredList<>(thuocList, p -> true);

//        thuocTableAdd.setItems(addFilteredData);
        thuocTableAdd.setItems(addFilteredData);
        thuocTableUpdate.setItems(updateFilteredData);
        thuocTableDel.setItems(delFilteredData);

        dvtThuocAddField.getItems().addAll(donViTinhs);
        dvtThuocUpdateField.getItems().addAll(donViTinhs);
        nhomThuocField.getItems().addAll(nhomThuocs);
        nhomThuocUpdateField.getItems().addAll(nhomThuocs);

        ccbttThuocAdd.getItems().addAll(ETinhTrangSP.CON_HANG, ETinhTrangSP.SAP_CO_HANG, ETinhTrangSP.HET_HANG);
        ccbttThuocUpdate.getItems().addAll(ETinhTrangSP.CON_HANG, ETinhTrangSP.SAP_CO_HANG, ETinhTrangSP.HET_HANG);

        loaiSPComboBox.getItems().addAll(ELoaiSanPham.THUOC, ELoaiSanPham.THUC_PHAM_CHUC_NANG);
        loaiSPCBUpdate.getItems().addAll(ELoaiSanPham.THUOC, ELoaiSanPham.THUC_PHAM_CHUC_NANG);
        setEventListeners();
    }

    private void setEventListeners() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat decimalFormat = new DecimalFormat("#.####", symbols);

        thuocTableAdd.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

                maThuocAddField.setText(newValue.getMaSP());
                tenThuocAddField.setText(newValue.getTenSP());
                hanSuDungThuocAddField.setValue(newValue.getHanSD());

                // Format khoiLuong
                String khoiLuongFormatted = decimalFormat.format(newValue.getKhoiLuong());
                khoiLuongThuocAddField.setText(khoiLuongFormatted);

                tkdAddField.setSelected(newValue.isThuocKeDon());
                nsxThuocAddField.setText(newValue.getNuocSX());
                thuongHieuThuocAddField.setText(newValue.getThuongHieu());
                moTaThuocAddField.setText(newValue.getMoTa());

                // Format giaNhap
                String giaNhapFormatted = decimalFormat.format(newValue.getGiaBan());
                tdpThuocAddField.setText(newValue.getTacDungPhu());

                ccbttThuocAdd.setValue(newValue.getTinhTrangSP());
            }
        });

        btnAddThuoc.setOnAction(event -> onAddThuocClick());
        btnUpdateThuoc.setOnAction(event -> onUpdateThuocClick());
        btnDelThuoc.setOnAction(event -> onDeleteThuocClick());
        xoaRongAdd.setOnAction(event -> cleanInput(CLEAN_ADD_Thuoc_INPUT));
        xoaRongUpdate.setOnAction(event -> cleanInput(CLEAN_UPDATE_Thuoc_INPUT));
        xacNhanButton.setOnAction(event -> onXacNhan());


        // Tương tự cho các phần khác như update và delete...

        thuocTableUpdate.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                SanPham thuoc = (SanPham) newValue;

                maThuocUpdateField.setText(thuoc.getMaSP());
                tenThuocUpdateField.setText(thuoc.getTenSP());
                hanSuDungThuocUpdateField.setValue(thuoc.getHanSD());

                // Format khoiLuong
                String khoiLuongFormatted = decimalFormat.format(thuoc.getKhoiLuong());
                khoiLuongThuocUpdateField.setText(khoiLuongFormatted);

                nsxThuocUpdateField.setText(thuoc.getNuocSX());
                thuongHieuThuocUpdateField.setText(thuoc.getThuongHieu());
                moTaThuocUpdateField.setText(thuoc.getMoTa());

                // Format giaNhap
                String giaNhapFormatted = decimalFormat.format(thuoc.getGiaBan());
                giaNhapThuocUpdateField.setText(giaNhapFormatted);


                thanhPhanThuocUpdateField.setText(thuoc.getMoTa());
                tdpThuocUpdateField.setText(thuoc.getTacDungPhu());

                // Format giaBan
                String giaBanFormatted = decimalFormat.format(thuoc.getGiaBan());
                giaBanThuocUpdateField.setText(giaBanFormatted);

                ccbttThuocUpdate.setValue(thuoc.getTinhTrangSP());
            }
        });


        tenThuocUpdateField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateFilteredData.setPredicate(nhaCungCap -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return nhaCungCap.getTenSP().toLowerCase().contains(lowerCaseFilter);
            });
        });

        thuocTableDel.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                SanPham thuoc = (SanPham) newValue;

                maThuocDelField.setText(thuoc.getMaSP());
                tenThuocDelField.setText(thuoc.getTenSP());
            }
        });

        maThuocDelField.textProperty().addListener((observable, oldValue, newValue) -> {
            delFilteredData.setPredicate(sanPham -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return sanPham.getMaSP().toLowerCase().contains(lowerCaseFilter);
            });
        });

        tenThuocDelField.textProperty().addListener((observable, oldValue, newValue) -> {
            delFilteredData.setPredicate(sanPham -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return sanPham.getTenSP().toLowerCase().contains(lowerCaseFilter);
            });
        });
    }

    // Xoa rong
    private void cleanInput(int type) {
        if(type == 1) {
            maThuocAddField.clear();
            tenThuocAddField.clear();
            ngayNhapThuocAddField.setValue(null);
            hanSuDungThuocAddField.setValue(null);
            khoiLuongThuocAddField.clear();
            ccbttThuocAdd.getSelectionModel().clearSelection();
            dvtThuocAddField.getSelectionModel().clearSelection();
            nsxThuocAddField.clear();
            thuongHieuThuocAddField.clear();
            moTaThuocAddField.clear();
            loaiSPComboBox.getSelectionModel().clearSelection();
            loaiSPCBUpdate.getSelectionModel().clearSelection();
            soLoAddField.clear();
            giaBanAddField.clear();
            tdpThuocAddField.clear();
            tkdAddField.setSelected(false);
            nhomThuocField.getSelectionModel().clearSelection();
            comboBoxNhaCC.getSelectionModel().clearSelection();

        }

        if(type == 2) {
            maThuocUpdateField.clear();
            tenThuocUpdateField.clear();
            ngayNhapThuocUpdateField.setValue(null);
            hanSuDungThuocUpdateField.setValue(null);
            khoiLuongThuocUpdateField.clear();
            ccbttThuocUpdate.getSelectionModel().clearSelection();
            dvtThuocUpdateField.getSelectionModel().clearSelection();
            nsxThuocUpdateField.clear();
            thuongHieuThuocUpdateField.clear();
            moTaThuocUpdateField.clear();
            giaNhapThuocUpdateField.clear();
            thueSuatThuocUpdateField.clear();
            loiNhuanThuocUpdateField.clear();
            thanhPhanThuocUpdateField.clear();
            nhomThuocUpdateField.getSelectionModel().clearSelection();
            tdpThuocUpdateField.clear();
            giaBanThuocUpdateField.clear();
            tkdUpdateField.setSelected(false);
            nhomThuocField.getSelectionModel().clearSelection();
        }

        if(type == 3) {
            maThuocDelField.clear();
            tenThuocDelField.clear();
            ngayNhapThuocDelField.setValue(null);
        }
    }

    private void onXacNhan() {
        if (comboBoxNhaCC.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Vui lòng chọn nhà cung cấp!");
            alert.showAndWait();
            return;
        }
        for (SanPham thuoc : thuocObservableList) {
            // Thêm thuốc vào danh sách và cơ sở dữ liệu
            try {
                if (productService.addSanPham(thuoc)) {
                    thuocList.add(thuoc);
                    cleanInput(CLEAN_ADD_Thuoc_INPUT);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thành công");
                    alert.setHeaderText("Lưu thông tin thuốc thành công.");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Lỗi");
                    alert.setHeaderText("Thêm thông tin thuốc không thành công!");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        // Xoa rong
        thuocObservableList.clear();
    }


    private void onAddThuocClick() {
        // Lấy giá trị từ các trường nhập liệu
        String maThuoc = "T" + System.currentTimeMillis();
        String tenThuoc = tenThuocAddField.getText().trim();
        LocalDate hanSuDung = hanSuDungThuocAddField.getValue();
        String khoiLuongString = khoiLuongThuocAddField.getText().trim();
        DonViTinh dvt = dvtThuocAddField.getValue();
        ELoaiSanPham loaiSP = loaiSPComboBox.getValue();
        NhomThuoc nhomThuoc = nhomThuocField.getValue();
        String tdp = tdpThuocAddField.getText().trim();
        String nsx = nsxThuocAddField.getText().trim();
        String thuongHieu = thuongHieuThuocAddField.getText().trim();
        String moTa = moTaThuocAddField.getText().trim();
        ETinhTrangSP tinhTrangSanPham = ccbttThuocAdd.getValue();
        String giaBan = giaBanAddField.getText().trim();

        // Kiểm tra các trường không được để trống
        if (tenThuoc.isEmpty() || hanSuDung == null || khoiLuongString.isEmpty() || nhomThuoc == null ||
                dvt == null || giaBan.isEmpty() || tdp.isEmpty() || nsx.isEmpty() || thuongHieu.isEmpty() || tinhTrangSanPham == null) {
            showAlert("Lỗi!", "Vui lòng điền đầy đủ thông tin.");
            return;
        }

        // Kiểm tra khối lượng phải là số dương
        double khoiLuong;
        try {
            khoiLuong = Double.parseDouble(khoiLuongString);
            if (khoiLuong <= 0) {
                showAlert("Lỗi!", "Khối lượng phải là số dương.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Lỗi!", "Khối lượng phải là một số hợp lệ.");
            return;
        }
        SanPham sanPham = new SanPham();
        sanPham.setTenSP(tenThuoc);
        sanPham.setCongDung("");
        sanPham.setGiaBan(Double.parseDouble(giaBan));
        sanPham.setHanSD(hanSuDung);
        sanPham.setKhoiLuong(khoiLuong);
        sanPham.setLoaiSP(loaiSP);
        sanPham.setLoiKhuyen("");
        sanPham.setMoTa(moTa);
        sanPham.setNuocSX(nsx);
        sanPham.setTacDungPhu(tdp);
        sanPham.setThuocKeDon(true);
        sanPham.setThuongHieu(thuongHieu);
        sanPham.setTinhTrangSP(ETinhTrangSP.CON_HANG);
        sanPham.setDonViTinh(dvt);
        sanPham.setNgaySX(LocalDate.parse("2024-01-01"));
        sanPham.setNhomThuoc(nhomThuoc);
        System.out.println(dvt);
        System.out.println(nhomThuoc);
        System.out.println(sanPham);

        try {
            productService.addSanPham(sanPham);
            thuocList.add(sanPham); // Thêm thuốc vào danh sách
            cleanInput(CLEAN_ADD_Thuoc_INPUT); // Dọn dẹp input sau khi thêm
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thành công");
            alert.setHeaderText("Đã thêm thuốc thành công");
            alert.showAndWait();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    // Hàm hiển thị thông báo lỗi
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    private void onUpdateThuocClick() {
        // Lấy giá trị từ các trường nhập liệu
        String maThuoc = maThuocUpdateField.getText().trim();
        String tenThuoc = tenThuocUpdateField.getText().trim();
        LocalDate ngayNhapThuoc = ngayNhapThuocUpdateField.getValue();
        LocalDate hanSuDung = hanSuDungThuocUpdateField.getValue();
        String khoiLuongString = khoiLuongThuocUpdateField.getText().trim();
        DonViTinh dvt = dvtThuocUpdateField.getValue();
        String giaNhapString = giaNhapThuocUpdateField.getText().trim();
        String thueSuatString = thueSuatThuocUpdateField.getText().trim();
        String loiNhuanString = loiNhuanThuocUpdateField.getText().trim();
        String thanhPhan = thanhPhanThuocUpdateField.getText().trim();
        NhomThuoc nhomThuoc = nhomThuocUpdateField.getValue();
        String tdp = tdpThuocUpdateField.getText().trim();
        String nsx = nsxThuocUpdateField.getText().trim();
        String thuongHieu = thuongHieuThuocUpdateField.getText().trim();
        boolean thuocKeDon = tkdUpdateField.isSelected();
        ETinhTrangSP tinhTrangSanPham = ccbttThuocUpdate.getValue();
        String moTa = moTaThuocUpdateField.getText().trim();

        // Kiểm tra ít nhất một thông tin được cung cấp
        if (!maThuoc.isEmpty()) {
            SanPham thuoc = findThuocInTable(maThuoc);  // Tìm thuốc theo mã thuốc

            if (thuoc != null) {
                // Cập nhật thông tin thuốc
                if (!tenThuoc.isEmpty()) thuoc.setTenSP(tenThuoc);
                if (hanSuDung != null) thuoc.setHanSD(hanSuDung);
                if (!khoiLuongString.isEmpty()) {
                    double khoiLuong = Double.parseDouble(khoiLuongString);
                    thuoc.setKhoiLuong(khoiLuong);
                }
                if (dvt != null) thuoc.setDonViTinh(dvt);
                if (!giaNhapString.isEmpty()) {
                    double giaNhap = Double.parseDouble(giaNhapString);
                    thuoc.setGiaBan(giaNhap);

                    // Tính lại giá bán khi giá nhập thay đổi
                    double loiNhuan = !loiNhuanString.isEmpty() ? Double.parseDouble(loiNhuanString) : 0;
                    double thueSuat = !thueSuatString.isEmpty() ? Double.parseDouble(thueSuatString) : 0;

                    // Công thức tính giá bán
                    double phanTramLoiNhuan = (loiNhuan / giaNhap) * 100;
                    double giaBan = giaNhap * (1 + phanTramLoiNhuan / 100) * (1 + thueSuat / 100);

                    thuoc.setGiaBan(giaBan);  // Cập nhật giá bán
                }
                if (!thanhPhan.isEmpty()) thuoc.setMoTa(thanhPhan);
                if (!tdp.isEmpty()) thuoc.setTacDungPhu(tdp);
                if (!nsx.isEmpty()) thuoc.setNuocSX(nsx);
                if (!thuongHieu.isEmpty()) thuoc.setThuongHieu(thuongHieu);
                thuoc.setThuocKeDon(thuocKeDon);
                if (tinhTrangSanPham != null) thuoc.setTinhTrangSP(tinhTrangSanPham);
                if (!moTa.isEmpty()) thuoc.setMoTa(moTa);
                thuoc.setNhomThuoc(nhomThuoc);
                thuoc.setDonViTinh(dvt);
                thuoc.setNgaySX(hanSuDungThuocUpdateField.getValue());

                // Cập nhật thông tin vào cơ sở dữ liệu
                try {
                    if (productService.updateSanPham(thuoc)) {
                        // Cập nhật trong danh sách nếu cần
                        int index = thuocList.indexOf(thuoc);
                        if (index != -1) {
                            thuocList.set(index, thuoc); // Cập nhật trong danh sách
                        }

                        // Dọn dẹp input sau khi cập nhật
                        cleanInput(CLEAN_UPDATE_Thuoc_INPUT);

                        // Hiển thị thông báo thành công
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Thành công");
                        alert.setHeaderText("Đã cập nhật thông tin thuốc");
                        alert.showAndWait();
                    } else {
                        // Thông báo lỗi nếu không cập nhật thành công
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Lỗi");
                        alert.setHeaderText("Không thể cập nhật thông tin thuốc");
                        alert.showAndWait();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                // Thông báo lỗi nếu không tìm thấy thuốc
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText("Không tìm thấy thuốc với mã đã nhập");
                alert.showAndWait();
            }
        }
        else {
            // Thông báo lỗi nếu mã thuốc rỗng
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText("Vui lòng thông tin để cập nhật");
            alert.showAndWait();
        }
    }

    // Hàm tìm thuốc trong danh sách theo mã thuốc
    private SanPham findThuocInTable(String maThuoc) {
        for (SanPham thuoc : thuocList) {
            if (thuoc.getMaSP().equals(maThuoc)) {
                return thuoc;
            }
        }
        return null;
    }

    // Xóa thuốc
    private void onDeleteThuocClick() {
        // Lấy giá trị mã thuốc từ trường nhập liệu
        String maThuoc = maThuocDelField.getText().trim();

        // Kiểm tra xem mã thuốc có rỗng không
        if (!maThuoc.isEmpty()) {
            // Tìm thuốc trong cơ sở dữ liệu theo mã thuốc
            SanPham thuoc = findThuocInTable(maThuoc);  // Tìm thuốc theo mã

            if (thuoc != null) {
                // Hiển thị hộp thoại xác nhận trước khi xóa
                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Xác nhận xóa");
                confirmAlert.setHeaderText("Bạn có chắc chắn muốn xóa thuốc này?");
                confirmAlert.setContentText("Tên thuốc: " + thuoc.getTenSP());  // Hiển thị tên thuốc trong hộp thoại

                // Chờ người dùng chọn Yes hoặc No
                Optional<ButtonType> result = confirmAlert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // Nếu người dùng chọn "Yes", gọi phương thức xóa
                    boolean isDeleted = false;  // Gọi phương thức xóa từ ThuocDAO
                    try {
//                        isDeleted = productService.removeSanPham(maThuoc);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    if (isDeleted) {
                        // Cập nhật danh sách thuốc sau khi xóa
                        thuocList.remove(thuoc);

                        // Dọn dẹp input sau khi xóa
                        cleanInput(CLEAN_DEL_Thuoc_INPUT);

                        // Thông báo thành công
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Thành công");
                        alert.setHeaderText("Đã xóa thuốc");
                        alert.showAndWait();
                    } else {
                        // Thông báo lỗi nếu xóa không thành công
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Lỗi");
                        alert.setHeaderText("Không thể xóa thuốc");
                        alert.showAndWait();
                    }
                } else {
                    // Nếu người dùng chọn "No" hoặc đóng hộp thoại, không thực hiện hành động xóa
                    System.out.println("Người dùng đã hủy xóa thuốc.");
                }
            } else {
                // Thông báo lỗi nếu không tìm thấy thuốc
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText("Không tìm thấy thuốc với mã đã nhập");
                alert.showAndWait();
            }
        } else {
            // Thông báo lỗi nếu mã thuốc rỗng
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText("Vui lòng nhập it nhất mã thuốc để xóa");
            alert.showAndWait();
        }
    }


    private void setTableCollums() {
        // Add
        maThuocAdd.setCellValueFactory(new PropertyValueFactory<>("maSP"));
        tenThuocAdd.setCellValueFactory(new PropertyValueFactory<>("tenSP"));
        ttThuocAdd.setCellValueFactory(new PropertyValueFactory<>("tinhTrangSP"));
        hanSuDungThuocAdd.setCellValueFactory(new PropertyValueFactory<>("hanSD"));
        khoiLuongThuocAdd.setCellValueFactory(new PropertyValueFactory<>("khoiLuong"));
        dvtThuocAdd.setCellValueFactory(new PropertyValueFactory<>("donViTinh"));
        thuongHieuThuocAdd.setCellValueFactory(new PropertyValueFactory<>("thuongHieu"));
        nsxThuocAdd.setCellValueFactory(new PropertyValueFactory<>("nuocSX"));
        moTaThuocAdd.setCellValueFactory(new PropertyValueFactory<>("moTa"));
        tdpThuocAdd.setCellValueFactory(new PropertyValueFactory<>("tacDungPhu"));
        giaBanThuocAdd.setCellValueFactory(new PropertyValueFactory<>("giaBan"));

        thuocKeDonThuocAdd.setCellValueFactory(cellData -> {
            boolean thuocKeDon = cellData.getValue().isThuocKeDon();
            return new SimpleStringProperty(thuocKeDon ? "Thuốc kê đơn" : "Không phải thuốc kê đơn");
        });

        khoiLuongThuocAdd.setCellFactory(column -> {
            return new TableCell<SanPham, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        // Dùng BigDecimal để làm tròn
                        BigDecimal bd = new BigDecimal(item).setScale(1, RoundingMode.HALF_UP);
                        setText(bd.toString());
                    }
                }
            };
        });

        giaBanThuocAdd.setCellFactory(column -> {
            return new TableCell<SanPham, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        // Dùng BigDecimal để làm tròn
                        BigDecimal bd = new BigDecimal(item).setScale(1, RoundingMode.HALF_UP);
                        setText(bd.toString());
                    }
                }
            };
        });



        // Update
        maThuocUpdate.setCellValueFactory(new PropertyValueFactory<>("maSP"));
        tenThuocUpdate.setCellValueFactory(new PropertyValueFactory<>("tenSP"));
        ttThuocUpdate.setCellValueFactory(new PropertyValueFactory<>("tinhTrangSP"));
        hanSuDungThuocUpdate.setCellValueFactory(new PropertyValueFactory<>("hanSD"));
        khoiLuongThuocUpdate.setCellValueFactory(new PropertyValueFactory<>("khoiLuong"));
        dvtThuocUpdate.setCellValueFactory(new PropertyValueFactory<>("donViTinh"));
        thuongHieuThuocUpdate.setCellValueFactory(new PropertyValueFactory<>("thuongHieu"));
        nsxThuocUpdate.setCellValueFactory(new PropertyValueFactory<>("nuocSX"));
        moTaThuocUpdate.setCellValueFactory(new PropertyValueFactory<>("moTa"));
        tdpThuocUpdate.setCellValueFactory(new PropertyValueFactory<>("tacDungPhu"));
        giaBanThuocUpdate.setCellValueFactory(new PropertyValueFactory<>("giaBan"));

        thuocKeDonThuocUpdate.setCellValueFactory(cellData -> {
            boolean thuocKeDon = cellData.getValue().isThuocKeDon();
            return new SimpleStringProperty(thuocKeDon ? "Thuốc kê đơn" : "Không phải thuốc kê đơn");
        });

        khoiLuongThuocUpdate.setCellFactory(column -> {
            return new TableCell<SanPham, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        // Dùng BigDecimal để làm tròn
                        BigDecimal bd = new BigDecimal(item).setScale(1, RoundingMode.HALF_UP);
                        setText(bd.toString());
                    }
                }
            };
        });



        giaBanThuocUpdate.setCellFactory(column -> {
            return new TableCell<SanPham, Double>() {
                private final DecimalFormat decimalFormat;

                {
                    DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
                    decimalFormat = new DecimalFormat("0.0", symbols);
                }

                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(decimalFormat.format(item));
                    }
                }
            };
        });


        // Del
        maThuocDel.setCellValueFactory(new PropertyValueFactory<>("maSP"));
        tenThuocDel.setCellValueFactory(new PropertyValueFactory<>("tenSP"));
        ttThuocDel.setCellValueFactory(new PropertyValueFactory<>("tinhTrangSP"));
        hanSuDungThuocDel.setCellValueFactory(new PropertyValueFactory<>("hanSD"));
        khoiLuongThuocDel.setCellValueFactory(new PropertyValueFactory<>("khoiLuong"));
        dvtThuocDel.setCellValueFactory(new PropertyValueFactory<>("donViTinh"));
        thuongHieuThuocDel.setCellValueFactory(new PropertyValueFactory<>("thuongHieu"));
        nsxThuocDel.setCellValueFactory(new PropertyValueFactory<>("nuocSX"));
        moTaThuocDel.setCellValueFactory(new PropertyValueFactory<>("moTa"));
        tdpThuocDel.setCellValueFactory(new PropertyValueFactory<>("tacDungPhu"));
        giaBanThuocDel.setCellValueFactory(new PropertyValueFactory<>("giaBan"));

        thuocKeDonThuocDel.setCellValueFactory(cellData -> {
            boolean thuocKeDon = cellData.getValue().isThuocKeDon();
            return new SimpleStringProperty(thuocKeDon ? "Thuốc kê đơn" : "Không phải thuốc kê đơn");
        });

        khoiLuongThuocDel.setCellFactory(column -> {
            return new TableCell<SanPham, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        // Dùng BigDecimal để làm tròn
                        BigDecimal bd = new BigDecimal(item).setScale(1, RoundingMode.HALF_UP);
                        setText(bd.toString());
                    }
                }
            };
        });

        giaBanThuocDel.setCellFactory(column -> {
            return new TableCell<SanPham, Double>() {
                private final DecimalFormat decimalFormat;

                {
                    DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
                    decimalFormat = new DecimalFormat("0.0", symbols);
                }

                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(decimalFormat.format(item));
                    }
                }
            };
        });
    }
}

