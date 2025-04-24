package com.example.pharmacyproject.enitity.doitra;

public class ChiTietDoiTra {
    private int soLuongDoiTra;
    private double donGia;
    private double thanhTien;

    public ChiTietDoiTra() {
    }

    public ChiTietDoiTra(int soLuongDoiTra, double donGia, double thanhTien) {
        this.soLuongDoiTra = soLuongDoiTra;
        this.donGia = donGia;
        this.thanhTien = thanhTien;
    }

    public int getSoLuongDoiTra() {
        return soLuongDoiTra;
    }

    public void setSoLuongDoiTra(int soLuongDoiTra) {
        this.soLuongDoiTra = soLuongDoiTra;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }
}
