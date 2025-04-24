package com.example.pharmacyproject.enitity.discount;

public class LoaiKhuyenMai {
    private String maLoaiKm;
    private String moTa;

    public LoaiKhuyenMai() {
    }

    public LoaiKhuyenMai(String maLoaiKm, String moTa) {
        this.maLoaiKm = maLoaiKm;
        this.moTa = moTa;
    }

    public String getMaLoaiKm() {
        return maLoaiKm;
    }

    public void setMaLoaiKm(String maLoaiKm) {
        this.maLoaiKm = maLoaiKm;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}
