package com.example.pharmacyproject.enitity.phieunhap;


import java.time.LocalDate;
import java.util.ArrayList;

public class PhieuNhap {
    private String maPhieuNhap;
    private LocalDate ngayNhap;
    private String maNhaCungCap;
    private int soLuong;
    private ArrayList<ChiTietPhieuNhap> chiTietPhieuNhaps;
    private double tong;

    public PhieuNhap() {
    }

    public PhieuNhap(String maPhieuNhap, LocalDate ngayNhap, String maNhaCungCap, int soLuong, ArrayList<ChiTietPhieuNhap> chiTietPhieuNhaps) {
        this.maPhieuNhap = maPhieuNhap;
        this.ngayNhap = ngayNhap;
        this.soLuong = soLuong;
        this.maNhaCungCap = maNhaCungCap;
        this.chiTietPhieuNhaps = chiTietPhieuNhaps;
        setTongTien();
        System.out.println("Chi tiet phieu nhap: " + this.chiTietPhieuNhaps.get(0).getMaHangNhap());
        System.out.println("Chi tiet phieu nhap: " + chiTietPhieuNhaps.get(0).getMaHangNhap());
        System.out.println("Chi tiet phieu nhap: " + chiTietPhieuNhaps.get(0).getThanhTien());
        System.out.println("Chi tiet phieu nhap: " + tong);
    }

    public String getMaPhieuNhap() {
        return maPhieuNhap;
    }

    public void setMaPhieuNhap(String maPhieuNhap) {
        this.maPhieuNhap = maPhieuNhap;
    }

    public LocalDate getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(LocalDate ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public ArrayList<ChiTietPhieuNhap> getChiTietPhieuNhaps() {
        return chiTietPhieuNhaps;
    }

    public void setTongTien() {
        for (ChiTietPhieuNhap chiTietPhieuNhap : this.chiTietPhieuNhaps) {
            this.tong += chiTietPhieuNhap.getThanhTien();
        }
    }

    public double getTongTien() {
        double t = 1;
        for (ChiTietPhieuNhap chiTietPhieuNhap : this.chiTietPhieuNhaps) {
            this.tong += chiTietPhieuNhap.getThanhTien();
            t = this.tong;
        }
        return t;
    }

    public String getMaNhaCungCap() {
        return maNhaCungCap;
    }

    public void setMaNhaCungCap(String maNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
    }
}
