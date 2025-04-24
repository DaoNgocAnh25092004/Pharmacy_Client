package com.example.pharmacyproject;

import com.example.pharmacyproject.BanHangController;
import com.example.pharmacyproject.NhanVienController;
import com.example.pharmacyproject.PharmacyApplication;
import com.example.pharmacyproject.ThongTinCaNhanController;
import model.NhanVien;
import model.TaiKhoan;
import model.enums.VaiTro;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Dashboard implements Initializable {
    private NhanVien nhanVienInfo;
    private TaiKhoan taiKhoanInfo;
    private Stage primaryStage;
    private Map<String, AnchorPane> loadLayoutList = new HashMap<>();
    //TrangChu
    TreeItem<String> trangChuItem;
    //quanLyMenuBtn
    TreeItem<String> quanlyItem, banHang, sanPham, nhanVien, nhaCungCap, khachHang, hang, ctKhuyenMai;
    //thongKeMenuBtn
    TreeItem<String> thongKeItem, sanPhamBanChay, xepHangKhachHang, doanhThu;
    //traCuu
    TreeItem<String> traCuuItem, traCuuThuoc, traCuuHoaDon, traCuuKhachHang;
    //banHang
    TreeItem<String> lapHoaDonItem, danhSachHoaDonItem;
    //sanPham
    TreeItem<String> thuocItem, thucPhamCNItem;
    @FXML
    private Label userNameLabel;

    @FXML
    private Label userRoleLabel;

    @FXML
    private ImageView signOutImgView;

    @FXML
    private Button signOutBtn;

    @FXML
    private TreeView trangChuTreeView;

    @FXML
    private TreeView treeViewQuanLy;

    @FXML
    private TreeView thongKeBtn;

    @FXML
    private TreeView traCuuTreeView;

    @FXML
    private AnchorPane contentArea;


    @FXML
    protected void onHomeClicked(){
        /*loadUI("trang_chu.fxml");*/
    }

    @FXML
    protected void clickTreeViewQuanLy(){
        TreeItem<String> selectedItem = (TreeItem<String>) treeViewQuanLy.getSelectionModel().getSelectedItem();
        if (selectedItem.getValue().equals("Khách hàng")){
            loadUI("khach_hang.fxml");
        }
        if (selectedItem.getValue().equals("Lập hóa đơn")){
            loadUI("ban_hang.fxml");
        }
        if (selectedItem.getValue().equals("Nhân viên")){
            loadUI("nhan_vien.fxml");
        }
        if (selectedItem.getValue().equals("Nhà cung cấp")){
            loadUI("nha_cung_cap.fxml");
        }
//        if (selectedItem.getValue().equals("Danh sách hóa đơn")){
//            loadUI("danh_sach_hoa_don.fxml");
//        }
        if (selectedItem.getValue().equals("Chương trình khuyến mãi")){
            loadUI("khuyen_mai.fxml");
        }
        if (selectedItem.getValue().equals("Thuốc")){
            loadUI("thuoc.fxml");
        }
        if (selectedItem.getValue().equals("Thực phẩm chức năng")){
            loadUI("thuc_pham_chuc_nang.fxml");
        }
        if (selectedItem.getValue().equals("Hạng")){
            loadUI("hang.fxml");
        }
    }

    @FXML
    protected void click_thong_ke_btn(){
        TreeItem<String> selectedItem = (TreeItem<String>) thongKeBtn.getSelectionModel().getSelectedItem();
        if (selectedItem.getValue().equals("Doanh thu")){
            loadUI("thong_ke.fxml");
        } else if (selectedItem.getValue().equals("Sản phẩm bán chạy")){
            loadUI("thong_ke_san_pham_ban_chay.fxml");
        } else if (selectedItem.getValue().equals("Xếp hạng khách hàng")){
            loadUI("thong_ke_khach_hang.fxml");
        }
    }

    public void setNhanVienInfo(NhanVien nhanVienInfo, TaiKhoan taiKhoan) {
        this.nhanVienInfo = nhanVienInfo;
        this.taiKhoanInfo = taiKhoan;
        userNameLabel.setText(nhanVienInfo.getHoTen());
        userRoleLabel.setText(taiKhoan.getVaiTro().toString());

        quanlyItem.getChildren().addAll(banHang, sanPham, khachHang);
        if(taiKhoanInfo.getVaiTro() == VaiTro.QUANLY || taiKhoanInfo.getVaiTro() == VaiTro.ADMIN){
            quanlyItem.getChildren().addAll(nhanVien, nhaCungCap);
        }
        treeViewQuanLy.setRoot(quanlyItem);
    }

    protected void loadUI(String fileName) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fileName));
        AnchorPane pane = loadLayoutList.get(fileName);
        try {
            if (pane == null){
                pane = loader.load();
                contentArea.getChildren().setAll(pane);
                if (!fileName.equals("khach_hang.fxml")){
                    loadLayoutList.put(fileName, pane);
                }
                if (fileName.equals("ban_hang.fxml")) {
                    BanHangController banHangController = loader.getController();
                    banHangController.setPrimaryStage(primaryStage);
                    banHangController.setNhanVienInfo(nhanVienInfo);
                    banHangController.setDashboard(this);
                }

                if (fileName.equals("nhan_vien.fxml")){
                    NhanVienController nhanVienController = loader.getController();
                }
                if (fileName.equals("thong_tin_ca_nhan.fxml")){
                    ThongTinCaNhanController thongTinCaNhanController = loader.getController();
                    thongTinCaNhanController.setPersonalInfo(nhanVienInfo);
                    thongTinCaNhanController.setTaiKhoan(taiKhoanInfo);
                }
//                if (fileName.equals("tra_cuu_thuoc.fxml")){
//                    TraCuuThuocController traCuuThuocController = loader.getController();
//                    traCuuThuocController.setPrimaryStage(primaryStage);
//                    traCuuThuocController.setDashboard(this);
//                }

            }
            else{
                contentArea.getChildren().setAll(pane);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void click_tra_cuu_btn(){
        TreeItem<String> selectedItem = (TreeItem<String>) traCuuTreeView.getSelectionModel().getSelectedItem();
        if (selectedItem.getValue().equals("Thuốc")){
            loadUI("tra_cuu_thuoc.fxml");
        }
        if (selectedItem.getValue().equals("Khách hàng")){
            loadUI("tra_cuu_khach_hang.fxml");
        }

        if (selectedItem.getValue().equals("Hóa đơn")){
            loadUI("tra_cuu_hoa_don.fxml");
        }
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setOnCloseRequest(event -> {
            System.out.println("Application closed");
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadUI("trang_chu.fxml");
//        trangChuItem = new TreeItem<>("Trang chủ", new ImageView(new Image(getClass().getResourceAsStream("/icon/home_vector.png"))));
        quanlyItem = new TreeItem<>("Quản lý", new ImageView(new Image(getClass().getResourceAsStream("/icon/bag_icon.png"))));
        thongKeItem = new TreeItem<>("Thống kê", new ImageView(new Image(getClass().getResourceAsStream("/icon/statistic_icon.png"))));
        traCuuItem = new TreeItem<>("Tra cứu", new ImageView(new Image(getClass().getResourceAsStream("/icon/search_icon.png"))));

        //for quanLyMenuBtn
        banHang = new TreeItem<>("Bán hàng");
        sanPham = new TreeItem<>("Sản phẩm");
        nhanVien = new TreeItem<>("Nhân viên");
        khachHang = new TreeItem<>("Khách hàng");

        //for nhanVien
        TreeItem<String> nhanVienItem = new TreeItem<>("Nhân viên");

        //for thongKeMenuBtn
        sanPhamBanChay = new TreeItem<>("Sản phẩm bán chạy");
        xepHangKhachHang = new TreeItem<>("Xếp hạng khách hàng");
        doanhThu = new TreeItem<>("Doanh thu");

        //for traCuuMenuBtn
        traCuuThuoc = new TreeItem<>("Thuốc");
        traCuuKhachHang = new TreeItem<>("Khách hàng");
        traCuuHoaDon = new TreeItem<>("Hóa đơn");

        //for banHang
        lapHoaDonItem = new TreeItem<>("Lập hóa đơn");
//        danhSachHoaDonItem = new TreeItem<>("Danh sách hóa đơn");

        //for sanPham
        thuocItem = new TreeItem<>("Thuốc");
        thucPhamCNItem = new TreeItem<>("Thực phẩm chức năng");

        trangChuTreeView.setRoot(trangChuItem);



        thongKeItem.getChildren().addAll(sanPhamBanChay, xepHangKhachHang, doanhThu);
        thongKeBtn.setRoot(thongKeItem);

        traCuuItem.getChildren().addAll(traCuuThuoc, traCuuKhachHang, traCuuHoaDon);
        traCuuTreeView.setRoot(traCuuItem);

        banHang.getChildren().addAll(lapHoaDonItem, danhSachHoaDonItem);
        sanPham.getChildren().addAll(thuocItem);

        eventListener();
    }

    private void eventListener() {
        //userNameLabel click handler
        userNameLabel.setOnMouseClicked(event -> {
            loadUI("thong_tin_ca_nhan.fxml");
        });

        //button handler
        signOutBtn.setOnAction(event -> {
            onSignOutBtnClick();
        });
        signOutImgView.setOnMouseClicked(event -> {
            onSignOutBtnClick();
        });

        //treeView handler
        quanlyItem.expandedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue){
                    treeViewQuanLy.setPrefHeight(270);
                    collapseOtherMenu(quanlyItem);
                } else treeViewQuanLy.setPrefHeight(60);
            }
        });

        thongKeItem.expandedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue){
                    thongKeBtn.setPrefHeight(234);
                    collapseOtherMenu(thongKeItem);
                } else thongKeBtn.setPrefHeight(60);
            }
        });

        traCuuItem.expandedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue){
                    traCuuTreeView.setPrefHeight(234);
                    collapseOtherMenu(traCuuItem);
                } else traCuuTreeView.setPrefHeight(60);
            }
        });
    }

    private void collapseOtherMenu(TreeItem<String> item) {
        if (item != quanlyItem){
            resizeItem(treeViewQuanLy, quanlyItem, 60);
        }
        if (item != thongKeItem){
            resizeItem(thongKeBtn, thongKeItem, 60);
        }
        if (item != traCuuItem){
            resizeItem(traCuuTreeView, traCuuItem, 60);
        }
    }

    private void resizeItem(TreeView root, TreeItem<String> item, int i) {
        item.setExpanded(false);
        root.setPrefHeight(i);
    }


    private void onSignOutBtnClick() {
        //show dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Đăng xuất");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc muốn đăng xuất?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
//                    FXMLLoader loader = new FXMLLoader(PharmacyApplication.class.getResource("tra_cuu_thuoc.fxml"));
//                    loader.load();
//                    TraCuuThuocController traCuuThuocController = loader.getController();
//                    if (readBarcode != null){
//                        readBarcode.stopListening();
//                    }

                    Stage currentStage = (Stage) signOutBtn.getScene().getWindow();
                    currentStage.close();

                    FXMLLoader fxmlLoader = new FXMLLoader(PharmacyApplication.class.getResource("log_in.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 700, 379);
                    Stage stage = new Stage();
                    stage.setTitle("Vital Care");
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
