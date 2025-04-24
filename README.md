# Pharmacy Management System - Tiệm Thuốc Tây VitalCare

Pharmacy Management System (VitalCare) là một ứng dụng quản lý tiệm thuốc tây, được phát triển bằng Java và JavaFX. Hệ thống này hỗ trợ các quy trình quản lý bán hàng, tồn kho và theo dõi thuốc, giúp các quản lý và dược sĩ dễ dàng vận hành và theo dõi hoạt động kinh doanh của cửa hàng.

## Mục Lục

- [Giới Thiệu](#giới-thiệu)
- [Chức Năng](#chức-năng)
- [Yêu Cầu Hệ Thống](#yêu-cầu-hệ-thống)
- [Cài Đặt](#cài-đặt)
- [Cách Sử Dụng](#cách-sử-dụng)
- [Cấu Trúc Dự Án](#cấu-trúc-dự-án)
- [Góp Ý và Phát Triển](#góp-ý-và-phát-triển)
- [Thông Tin Liên Hệ](#thông-tin-liên-hệ)

## Giới Thiệu

VitalCare là hệ thống quản lý tiệm thuốc tây được thiết kế nhằm mục đích tự động hóa quy trình bán hàng và quản lý thuốc. Ứng dụng giúp các dược sĩ và quản lý tiệm thuốc theo dõi hàng hóa, quản lý nhân viên, theo dõi thông tin khách hàng và xuất báo cáo doanh thu, tồn kho.

## Chức Năng

1. **Quản Lý Bán Hàng**
   - Tạo hóa đơn, cập nhật giao dịch bán hàng.
   - Theo dõi các chương trình khuyến mãi.

2. **Quản Lý Tồn Kho**
   - Thêm, sửa, xóa sản phẩm.
   - Kiểm tra tình trạng tồn kho và thông báo hết hàng.
   - Cảnh báo khi sản phẩm gần hết hạn.

3. **Quản Lý Nhân Viên**
   - Thêm, sửa, xóa thông tin nhân viên.
   - Theo dõi hiệu suất làm việc của từng nhân viên.

4. **Báo Cáo và Thống Kê**
   - Thống kê doanh thu, lợi nhuận theo ngày, tuần, tháng.
   - Báo cáo chi tiết về số lượng hàng tồn và tình trạng sản phẩm.

## Yêu Cầu Hệ Thống

- **Ngôn ngữ lập trình:** Java 8 trở lên
- **Framework giao diện:** JavaFX
- **Cơ sở dữ liệu:** MySQL hoặc SQLite
- **IDE khuyến nghị:** IntelliJ IDEA, Eclipse, hoặc NetBeans

## Cài Đặt

1. Clone dự án về máy:
   ```bash
   git clone https://github.com/your-username/Pharmacy-Management-System.git
   ```
2. **Cấu hình cơ sở dữ liệu**:
   - Mở file cấu hình `db_config.java` trong thư mục `/src/config`.
   - Cập nhật các thông tin kết nối cơ sở dữ liệu như `username`, `password`, và `URL`.
   - Nếu sử dụng MySQL, chạy script SQL trong thư mục `/sql` để tạo các bảng cần thiết.

3. **Cài đặt các thư viện phụ thuộc**:
   - Mở dự án trong IDE và thêm các thư viện như JavaFX, MySQL JDBC Driver, hoặc SQLite JDBC Driver (tuỳ thuộc vào cơ sở dữ liệu bạn sử dụng).
   - Nếu sử dụng Maven hoặc Gradle, thêm dependencies tương ứng trong file `pom.xml` (Maven) hoặc `build.gradle` (Gradle).

4. **Chạy dự án**:
   - Mở file `Main.java` trong IDE.
   - Chạy file `Main.java` để khởi động ứng dụng.

5. **Đăng nhập lần đầu**:
   - Tài khoản quản lý mặc định:
     - **Username**: `admin`
     - **Password**: `admin123`
   - Bạn có thể thay đổi tài khoản và mật khẩu này trong cơ sở dữ liệu sau khi đăng nhập lần đầu.

