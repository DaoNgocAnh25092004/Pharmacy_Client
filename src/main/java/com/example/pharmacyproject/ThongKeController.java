package com.example.pharmacyproject;

import com.example.pharmacyproject.PharmacyApplication;
import com.example.pharmacyproject.utils.RandomGenerator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import service.ChiTietHoaDonService;
import service.HoaDonService;

import java.net.URL;
import java.util.ResourceBundle;

public class ThongKeController implements Initializable {
    private HoaDonService invoiceService;
    private ChiTietHoaDonService detailService;
    public BarChart<String, Number> yearChart;
    public AreaChart<String, Number> monthChart;
    public TextField ngayThamGiaField;
    public Button monthSelect;
    public TextField namField;
    public Button yearSelect;
    public TextField namField1;
    public CategoryAxis xAxis;
    private ObservableList<XYChart.Data<String, Number>> dataMonth;
    private ObservableList<XYChart.Data<String, Number>> dataYear;
    private XYChart.Series<String, Number> seriesYear;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        invoiceService = PharmacyApplication.getInvoiceService();
        detailService = PharmacyApplication.getDetailService();

        setUpviewEvent();
        try {
            initCharts();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void initCharts() throws Exception {
        dataMonth = FXCollections.observableArrayList();
        dataMonth = fetchDataForMonth(0);
        // Create a new series and add data to it
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Data for " + 0);
        series.setData(dataMonth);

        // Add the series to the chart
        monthChart.getData().add(series);

        seriesYear = new XYChart.Series<>();
        seriesYear.setName("Data for " + 0);
        dataYear = FXCollections.observableArrayList();
    }

    private void setUpviewEvent() {
        monthSelect.setOnAction(event -> {
            try {
                onPickDate();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        yearSelect.setOnAction(event -> {
            try {
                onPickYear();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void onPickYear() throws Exception {
        String selectedYear = namField.getText().trim();
        String selectedYear1 = namField1.getText().trim();

        if (!selectedYear.isEmpty() && !selectedYear1.isEmpty()) {
            yearChart.getData().clear();

            int startYear = Integer.parseInt(selectedYear);
            int endYear = Integer.parseInt(selectedYear1);

            ObservableList<String> categories = FXCollections.observableArrayList();
            XYChart.Series<String, Number> series = new XYChart.Series<>();

            for (int year = startYear; year <= endYear; year++) {
                String yearString = String.valueOf(year);
                categories.add(yearString);
                series.getData().add(new XYChart.Data<>(yearString, invoiceService.tinhDoanhThuTheoNam(year)));
            }

            // Cập nhật danh mục cho trục X
            xAxis.setCategories(categories);

            series.setName("Data from " + startYear + " to " + endYear);
            yearChart.getData().add(series);
        }
    }


    private void onPickDate() throws Exception {
        String selectedDate = ngayThamGiaField.getText().trim();
        if (!selectedDate.isEmpty()) {
            // Clear existing data
            monthChart.getData().clear();
            dataMonth = fetchDataForMonth(Integer.parseInt(selectedDate));
            // Create a new series and add data to it
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Data for " + selectedDate);
            series.setData(dataMonth);

            // Add the series to the chart
            monthChart.getData().add(series);
        }
    }

    private ObservableList<XYChart.Data<String, Number>> fetchDataYear(int year1, int year2) throws Exception {
        ObservableList<XYChart.Data<String, Number>> data = FXCollections.observableArrayList();

        if (year1 == 0 && year2 == 0){
            data.add(new XYChart.Data<>("0", 0));
        } else {
            for (int i = year1; i <= year2; i++) {
                data.add(new XYChart.Data<>(String.valueOf(i), invoiceService.tinhDoanhThuTheoNam(i)));
            }
        }
        return data;
    }

    private ObservableList<XYChart.Data<String, Number>> fetchDataForMonth(int year) throws Exception {
        String [] month = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        ObservableList<XYChart.Data<String, Number>> dataMonth = FXCollections.observableArrayList();
        if (year == 0){
            for (int i = 0; i < 12; i++){
                dataMonth.add(new XYChart.Data<>(month[i], 0));
            }
        } else {
            for (int i = 0; i < 12; i++){
                dataMonth.add(new XYChart.Data<>(month[i], invoiceService.tinhDoanhThuTheoThang(year, i + 1)));
                System.out.println(invoiceService.tinhDoanhThuTheoThang(4, year));
            }
        }
        return dataMonth;
    }
}