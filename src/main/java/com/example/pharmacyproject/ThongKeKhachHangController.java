package com.example.pharmacyproject;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import service.HoaDonService;

import java.net.URL;
import java.util.*;

public class ThongKeKhachHangController implements Initializable {
    private HoaDonService invoiceService;
    public BarChart<String, Number> barChart;
    public TextField ngayThamGiaField;
    public Button btnSelect;
    public RadioButton luotMuaSort;
    public RadioButton soTienSort;
    public CategoryAxis xAxis;
    public Button inc5;
    public Button dec5;
    public LineChart lineChart;
    public CategoryAxis lineXAxis;
    public NumberAxis yAxis;
    //    private ObservableList<XYChart.Data<String, Integer>> data;
    private XYChart.Series<String, Number> series;
    private int showQuantity = 5;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpviewEvent();
        initCharts();
    }

    private void initCharts() {
        invoiceService = PharmacyApplication.getInvoiceService();
//        data = FXCollections.observableArrayList();
        series = new XYChart.Series<>();
        series.setName("Data for " + 0);
    }

    private void setUpviewEvent() {
        btnSelect.setOnAction(event -> onPressSelect());
        luotMuaSort.setOnAction(event -> {
            if (luotMuaSort.isSelected()){
                soTienSort.setSelected(false);
                onPressSelect();
            }
        });

        soTienSort.setOnAction(event -> {
            if (soTienSort.isSelected()){
                luotMuaSort.setSelected(false);
                onPressSelect();
            }
        });

        dec5.setOnAction(e -> {
            onDec5();
        });

        inc5.setOnAction(e -> {
            onInc5();
        });
    }

    private void onDec5() {
        if (showQuantity - 5 <= 0) {
            showQuantity = 1;
        } else {
            showQuantity -= 5;
        }
        loadData();
    }

    private void onInc5() {
        showQuantity += 5;
        loadData();
    }

    private void onPressSelect() {
        showQuantity = 5;
        loadData();
    }

    private void loadData() {
    yAxis.setTickUnit(1);

    yAxis.forceZeroInRangeProperty().setValue(true);

    // Barchart
    barChart.getData().clear();
    String year = ngayThamGiaField.getText().trim();
    if (!year.isEmpty()) {
        series = new XYChart.Series<>();
        series.setName("Data for " + year);

        Map<String, Long> luotMuaData = null;
        Map<String, Double> soTienData = null;
        try {
            luotMuaData = invoiceService.topKhachHangCoNhieuHoaDonNhat(Integer.parseInt(year), showQuantity);
            soTienData = invoiceService.topKhachHangChiTieuNhieuNhat(Integer.parseInt(year), showQuantity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (luotMuaSort.isSelected()) {
            xAxis.getCategories().clear();
            xAxis.setCategories(FXCollections.observableArrayList(luotMuaData.keySet()));
            for (String key : luotMuaData.keySet()) {
                series.getData().add(new XYChart.Data<>(key, luotMuaData.get(key)));
            }
        } else if (soTienSort.isSelected()) {
            xAxis.getCategories().clear();
            xAxis.setCategories(FXCollections.observableArrayList(soTienData.keySet()));
            for (String key : soTienData.keySet()) {
                series.getData().add(new XYChart.Data<>(key, soTienData.get(key)));
            }
        }

        barChart.getData().add(series);
    } else {
        barChart.getData().clear();
    }

    // Linechart
    lineChart.getData().clear();
        int[] data = null;
        try {
            data = invoiceService.thongKeSoLuongKhachHangTheoThang(Integer.parseInt(year));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        List<String> months = Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
    lineXAxis.setCategories(FXCollections.observableArrayList(months));
    XYChart.Series<String, Number> lineSeries = new XYChart.Series<>();
    for (int i = 0; i < 12; i++) {
        lineSeries.getData().add(new XYChart.Data<>(months.get(i), data[i]));
    }
    lineSeries.setName("Data for " + year);
    lineChart.getData().add(lineSeries);
}
}
