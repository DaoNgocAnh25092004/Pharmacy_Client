package com.example.pharmacyproject.enitity.hang;

public class Hang {
    private String maHang;
    private String tenHang;
    private String moTa;
    private int diemCanDat;

    public Hang() {
    }

    public Hang(String maHang, String tenHang, String moTa, int diemCanDat) {
        this.maHang = maHang;
        this.tenHang = tenHang;
        this.moTa = moTa;
        this.diemCanDat = diemCanDat;
    }

    public String getMaHang() {
        return maHang;
    }

    public void setMaHang(String maHang) {
        this.maHang = maHang;
    }

    public String getTenHang() {
        return tenHang;
    }

    public void setTenHang(String tenHang) {
        this.tenHang = tenHang;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String tenHang) {
        this.moTa = moTa;
    }

    public int getDiemCanDat() {
        return diemCanDat;
    }

    public void setDiemCanDat(int diemCanDat) {
        this.diemCanDat = diemCanDat;
    }
}
