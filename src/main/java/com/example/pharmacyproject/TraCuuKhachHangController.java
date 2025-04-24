package com.example.pharmacyproject;

import model.KhachHang;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import service.KhachHangService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TraCuuKhachHangController implements Initializable {

    public TextField idField;
    public TextField nameField;
    public TextField birthdayField;
    public TextField phoneNumberField;
    public TextField genderField;
    public TextField joinedDateField;
    public TextField pointField;
    public TextField searchByPhoneNumberField;
    public Button searchBtn;
    public GridPane gridPane;
    public Label notifiTextField;
    public TextField rankField;
    public ListView sugguestListView;
    private ObservableList<String> sugguestList;
    private List<KhachHang> khachHangArrayList;
    private KhachHangService customerService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //gridPane.setVisible(false);
        customerService = PharmacyApplication.getCustomerService();
        searchBtn.setOnAction(event -> onSearchBtnClick());
        try {
            khachHangArrayList = customerService.getAllKhachHang();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("So luong khach hang" + khachHangArrayList.size());

        setUpViewEvent();
    }

    private void setUpViewEvent() {

        searchByPhoneNumberField.setOnKeyReleased(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                onSearchBtnClick();
            } else {
                sugguestListView.setVisible(true);
                addSuggestionList(searchByPhoneNumberField.getText());
            }
        });

        setUpSuggestionList();
    }

    void onSearchBtnClick() {
        String phoneNumber = searchByPhoneNumberField.getText().trim();

        if (phoneNumber.trim().isEmpty()){
            notifiTextField.setText("Vui lòng nhập số điện thoại");
        } else {
            KhachHang khachHang;
            try {
                khachHang= customerService.findKhachHangBySDT(phoneNumber);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (khachHang == null) {
                notifiTextField.setText("Không tìm thấy khách hàng");
            } else {
                //gridPane.setVisible(true);
                idField.setText(khachHang.getMaKhachHang());
                nameField.setText(khachHang.getHoTen());
                birthdayField.setText(khachHang.getNgaySinh().toString());
                phoneNumberField.setText(khachHang.getSoDienThoai());
                genderField.setText(khachHang.isGioiTinh()? "Nam" : "Nữ");
                joinedDateField.setText(khachHang.getNgayThamGia().toString());
                pointField.setText(khachHang.getSoDienThoai());
            }
        }
    }

    private void setUpSuggestionList() {
        sugguestList = FXCollections.observableArrayList();

        sugguestListView.setOnMouseClicked(event -> {
            String selected = sugguestListView.getSelectionModel().getSelectedItem().toString();
//            searchByPhoneNumberField.setText(removeName(selected));
            sugguestListView.setVisible(false);
        });
    }

    private void addSuggestionList(String text) {
        sugguestList.clear();
        if (text.isEmpty()) {
            sugguestListView.setVisible(false);
            return;
        }
        for (KhachHang khachHang : khachHangArrayList) {
            if (khachHang.getHoTen().toLowerCase().contains(text.toLowerCase()) || khachHang.getSoDienThoai().contains(text)) {
                sugguestList.add(khachHang.getHoTen() + "\n" + khachHang.getSoDienThoai());
            }
        }
        sugguestListView.setItems(sugguestList);
        //set list view height = 30 * so luong item
        sugguestListView.setPrefHeight(48 * sugguestList.size());
    }
}
