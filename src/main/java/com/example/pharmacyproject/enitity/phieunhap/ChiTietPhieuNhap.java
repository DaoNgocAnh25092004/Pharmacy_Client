package com.example.pharmacyproject.enitity.phieunhap;

public class ChiTietPhieuNhap {
    private String maHangNhap;
    private String maPhieuNhap;
    private String maSP;
    private double thanhTien;
    private double giaNhap;
    private int soLuong;

    public ChiTietPhieuNhap() {
    }

    public ChiTietPhieuNhap(String maHangNhap, String maPhieuNhap, String maSP, double giaNhap, int soLuong) {
        this.maHangNhap = maHangNhap;
        this.maPhieuNhap = maPhieuNhap;
        this.maSP = maSP;
        this.giaNhap = giaNhap;
        this.soLuong = soLuong;
        setThanhTien();
    }

    public String getMaHangNhap() {
        return maHangNhap;
    }

    public void setMaHangNhap(String maHangNhap) {
        this.maHangNhap = maHangNhap;
    }

    public String getMaPhieuNhap() {
        return maPhieuNhap;
    }

    public void setMaPhieuNhap(String maPhieuNhap) {
        this.maPhieuNhap = maPhieuNhap;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien() {
        this.thanhTien = soLuong * giaNhap;
    }

    public double getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(double giaNhap) {
        this.giaNhap = giaNhap;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
