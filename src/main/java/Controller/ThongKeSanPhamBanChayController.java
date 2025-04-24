package Controller;

import com.example.pharmacyproject.PharmacyApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.SanPham;
import service.ChiTietHoaDonService;
import service.HoaDonService;
import service.SanPhamService;

import java.net.URL;
import java.util.ResourceBundle;

public class ThongKeSanPhamBanChayController implements Initializable {
    private HoaDonService invoiceService;
    private SanPhamService productService;
    private ChiTietHoaDonService detailService;
    public TextField searchByProductNameField;
    public Button selectBtn;
    public ListView listView;
    public AreaChart <String, Number> areaChart;
    public CategoryAxis xAxis;
    private ObservableList<XYChart.Data<String, Number>> data;
    private ObservableList<SanPham> sanPhamObservableList;
    private ObservableList<String> suggestions;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        invoiceService = PharmacyApplication.getInvoiceService();
        productService = PharmacyApplication.getProductService();
        detailService = PharmacyApplication.getDetailService();

        sanPhamObservableList = FXCollections.observableArrayList();

        data = FXCollections.observableArrayList();
        try {
            sanPhamObservableList.addAll(productService.getAllSanPham());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        setUpviewEvent();
        xAxis.setCategories(FXCollections.observableArrayList(
                "2020", "2021", "2022", "2023", "2024"
        ));
    }

    private void setUpSuggestionList() {
        suggestions = FXCollections.observableArrayList();

        listView.setOnMouseClicked(event -> {
            String selected = listView.getSelectionModel().getSelectedItem().toString();
            searchByProductNameField.setText(selected);
            listView.setVisible(false);
        });
    }

    private void setUpviewEvent() {
        selectBtn.setOnAction(event -> {
            try {
                onSelectPress();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        searchByProductNameField.setOnKeyReleased(event -> {
            listView.setVisible(true);
            addSuggestionList(searchByProductNameField.getText());
        });
        setUpSuggestionList();
    }


    private void onSelectPress() throws Exception {
        String productName = searchByProductNameField.getText().trim();
        if (!productName.isEmpty()) {
            // Clear existing data
            areaChart.getData().clear();
            data = fetchDataForDate(productName);
            // Create a new series and add data to it
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Data for " + productName);
            series.setData(data);

            // Add the series to the chart
            areaChart.getData().add(series);
        }
    }


    private ObservableList<XYChart.Data<String, Number>> fetchDataForDate(String productName) throws Exception {
        ObservableList<XYChart.Data<String, Number>> dataMonth = FXCollections.observableArrayList();

        dataMonth.add(new XYChart.Data<>("2020", invoiceService.thongKeSoLuongSanPhamBanDuocTheoNam(productName, 2021)));
        dataMonth.add(new XYChart.Data<>("2021", invoiceService.thongKeSoLuongSanPhamBanDuocTheoNam(productName, 2022)));
        dataMonth.add(new XYChart.Data<>("2022", invoiceService.thongKeSoLuongSanPhamBanDuocTheoNam(productName, 2023)));
        dataMonth.add(new XYChart.Data<>("2023", invoiceService.thongKeSoLuongSanPhamBanDuocTheoNam(productName, 2024)));
        dataMonth.add(new XYChart.Data<>("2024", invoiceService.thongKeSoLuongSanPhamBanDuocTheoNam(productName, 2025)));

        return dataMonth;
    }

    private void addSuggestionList(String text) {
        suggestions.clear();
        if (text.isEmpty()) {
            listView.setVisible(false);
            return;
        }
        for (SanPham sanPham : sanPhamObservableList) {
            if (sanPham.getTenSP().toLowerCase().contains(text.toLowerCase()) && !suggestions.contains(sanPham.getTenSP())) {
                suggestions.add(sanPham.getTenSP());
            }
        }
        listView.setItems(suggestions);
        //set list view height = 30 * so luong item
        listView.setPrefHeight(24 * suggestions.size());
    }
}
