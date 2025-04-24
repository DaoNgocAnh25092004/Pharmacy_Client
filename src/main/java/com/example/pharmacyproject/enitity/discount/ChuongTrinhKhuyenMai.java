package com.example.pharmacyproject.enitity.discount;

import java.time.LocalDate;

public class ChuongTrinhKhuyenMai {
    private String maCTKM;
    private String tenCTKM;
    private LocalDate ngayBatDau;
    private LocalDate ngayHetHan;
    private String hangApDung;
    private String loaiUuDai;
    private double giaTriUuDai;
    private String hinhThucUuDai;
    private String tinhTrang;

    public ChuongTrinhKhuyenMai() {

    }

    public ChuongTrinhKhuyenMai(String maCTKM, String tenCTKM, LocalDate ngayBatDau, LocalDate ngayHetHan, String hangApDung,
                                String loaiUuDai, double giaTriUuDai, String hinhThucUuDai) {
        this.maCTKM = maCTKM;
        this.tenCTKM = tenCTKM;
        this.ngayBatDau = ngayBatDau;
        this.ngayHetHan = ngayHetHan;
        this.hangApDung = hangApDung;
        this.loaiUuDai = loaiUuDai;
        this.giaTriUuDai = giaTriUuDai;
        this.hinhThucUuDai = hinhThucUuDai;
        setTinhTrang();
    }

    private void setTinhTrang() {
        if (LocalDate.now().isBefore(ngayBatDau)) {
            this.tinhTrang = "chuaKichHoat";
        } else if (LocalDate.now().isAfter(ngayHetHan)) {
            this.tinhTrang = "daKetThuc";
        } else {
            this.tinhTrang = "dangApDung";
        }
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public String getMaCTKM() {
        return maCTKM;
    }

    public void setMaCTKM(String maCTKM) {
        this.maCTKM = maCTKM;
    }

    public String getTenCTKM() {
        return tenCTKM;
    }

    public void setTenCTKM(String tenCTKM) {
        this.tenCTKM = tenCTKM;
    }

    public LocalDate getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(LocalDate ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public LocalDate getNgayHetHan() {
        return ngayHetHan;
    }

    public void setNgayHetHan(LocalDate ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
    }

    public String getHangApDung() {
        return hangApDung;
    }

    public void setHangApDung(String hangApDung) {
        this.hangApDung = hangApDung;
    }

    public String getLoaiUuDai() {
        return loaiUuDai;
    }

    public void setLoaiUuDai(String loaiUuDai) {
        this.loaiUuDai = loaiUuDai;
    }

    public double getGiaTriUuDai() {
        return giaTriUuDai;
    }

    public void setGiaTriUuDai(double giaTriUuDai) {
        this.giaTriUuDai = giaTriUuDai;
    }

    public String getHinhThucUuDai() {
        return hinhThucUuDai;
    }

    public void setHinhThucUuDai(String hinhThucUuDai) {
        this.hinhThucUuDai = hinhThucUuDai;
    }
}
