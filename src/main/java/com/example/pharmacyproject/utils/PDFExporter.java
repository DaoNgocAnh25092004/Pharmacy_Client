package com.example.pharmacyproject.utils;

import model.ChiTietHoaDon;
import model.HoaDon;
import model.KhachHang;
import javafx.collections.ObservableList;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class PDFExporter {
    public static boolean exportHoaDonToPdf(HoaDon hoaDon, ObservableList<ChiTietHoaDon> cthdList, KhachHang khachHang, String filePath) {
        try (PDDocument document = new PDDocument()) {
            // Tạo trang PDF mới
            PDPage page = new PDPage();
            document.addPage(page);

            File fontFile = new File("src/main/resources/font/RobotoFont/Roboto-Regular.ttf");
            PDType0Font font = PDType0Font.load(document, fontFile);

            // Viết nội dung vào trang
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(font, 13);

            createHeader(document, page, contentStream, font, hoaDon);
            createCustomerInfo(document, page, contentStream, font, khachHang);
            float yPositionOfTable = createTable(document, page, contentStream, font, cthdList, filePath);
            createCalculateUnderTable(document, page, contentStream, font, hoaDon, yPositionOfTable - 30);

            contentStream.close();

            document.save(filePath);
            System.out.println("File PDF đã được lưu tại: " + filePath);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void createHeader(PDDocument document, PDPage page, PDPageContentStream contentStream, PDType0Font font, HoaDon hoaDon) throws IOException {
        float x = 30;
        float y = 700;
        float width = 69;
        float height = 52;
        PDImageXObject image = PDImageXObject.createFromFile("src/main/resources/icon/Login/logo-login.png", document);
        contentStream.drawImage(image, x, y, width, height);
        contentStream.beginText();
        contentStream.setLeading(14.5f);
        contentStream.newLineAtOffset(130, 720);
        contentStream.setFont(font, 20);
        contentStream.showText("Nhà thuốc VitalCare");
        contentStream.endText();

        contentStream.beginText();
        contentStream.newLineAtOffset(420, 720);
        contentStream.setFont(font, 12);
        contentStream.showText("Số hóa đơn: #" + hoaDon.getMaHoaDon());
        contentStream.newLine();
        contentStream.showText("Ngày lập: " + hoaDon.getNgayLapHD());
        contentStream.endText();
    }

    private static void createCustomerInfo(PDDocument document, PDPage page, PDPageContentStream contentStream, PDType0Font font, KhachHang khachHang) throws IOException {
        float x = 80;
        float y = 650;
        contentStream.beginText();
        contentStream.newLineAtOffset(x, y);
        contentStream.setFont(font, 12);
        contentStream.showText("Thông tin khách hàng:");
        contentStream.newLine();
        contentStream.newLine();
        contentStream.showText("Tên khách hàng: " + khachHang.getHoTen());
        contentStream.newLine();
        contentStream.showText("Số điện thoại: " + khachHang.getSoDienThoai());
        contentStream.newLine();
        contentStream.showText("Số điểm tích lũy: " + khachHang.getDiemTichLuy());
        contentStream.endText();
    }

    private static float createTable(PDDocument document, PDPage page, PDPageContentStream contentStream, PDType0Font font, ObservableList<ChiTietHoaDon> cthdList, String filePath) throws IOException {
        // Set table position
        float margin = 80;
        float yStart = 530; // Start position
        float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
        float yPosition = yStart;
        float rowHeight = 20;
        float cellMargin = 5;

        // Define column widths
        float[] colWidths = {100, 60, 110, 100, 100}; // Width of each column

        // Draw table header
        String[] headers = {"Tên sản phẩm", "Số lượng", "Giá bán", "VAT", "Tổng cộng"};
        drawRow(contentStream, font, headers, yPosition, colWidths, tableWidth, rowHeight, true);
        yPosition -= rowHeight;

        // Draw table content
        for (ChiTietHoaDon detail : cthdList) {
            String[] row = {
                    detail.getSanPham().getTenSP(),
                    String.valueOf(detail.getSoLuong()),
                    String.valueOf(detail.getSanPham().getGiaBan()),
                    String.valueOf(detail.getVAT()),
                    String.valueOf(detail.getTongTien())
            };
            drawRow(contentStream, font, row, yPosition, colWidths, tableWidth, rowHeight, false);
            yPosition -= rowHeight;

            // Add a page if the table exceeds the current page
            if (yPosition < margin) {
                contentStream.close();
                page = new PDPage();
                document.addPage(page);
                contentStream = new PDPageContentStream(document, page);
                contentStream.setFont(font, 12);
                yPosition = yStart;
            }
        }
        return yPosition;
    }

    private static void createCalculateUnderTable(PDDocument document, PDPage page, PDPageContentStream contentStream, PDType0Font font, HoaDon hoaDon, float yPosition) throws IOException {
        float x = 80;
        float y = yPosition;
        contentStream.beginText();
        contentStream.newLineAtOffset(x, y);
        contentStream.setFont(font, 14);
        contentStream.showText("Tổng tiền: " + hoaDon.getTienKhachHangPhaiThanhToan());
        contentStream.endText();
    }

    private static void drawRow(
            PDPageContentStream contentStream, PDType0Font font,
            String[] data, float y, float[] colWidths,
            float tableWidth, float rowHeight, boolean isHeader) throws IOException {

        float x = 80; // Left margin
        float cellMargin = 5;

        // Draw cells
        for (int i = 0; i < colWidths.length; i++) {
            float cellWidth = colWidths[i];
            contentStream.addRect(x, y - rowHeight, cellWidth, rowHeight);
            contentStream.stroke();

            // Add text inside the cell
            contentStream.beginText();
            contentStream.setFont(font, isHeader ? 12 : 10);
            contentStream.newLineAtOffset(x + cellMargin, y - 15);
            contentStream.showText(data[i]);
            contentStream.endText();

            x += cellWidth;
        }
    }
}
