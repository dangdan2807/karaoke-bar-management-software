USE master
GO

-- Drop DATABASE QuanLyQuanKaraoke
-- GO

CREATE DATABASE QuanLyQuanKaraoke
GO

USE QuanLyQuanKaraoke
GO


-- tạo bảng
CREATE TABLE TaiKhoan
(
    tenDangNhap VARCHAR(100) NOT NULL PRIMARY KEY,
    matKhau VARCHAR(100) NOT NULL DEFAULT(N'123456'),
    -- 0. vô hiệu hóa || 1. kích hoạt
    tinhTrangTK BIT NOT NULL DEFAULT(1)
)
GO

CREATE TABLE NhanVien
(
    maNhanVien VARCHAR(10) NOT NULL PRIMARY KEY,
    hoTen NVARCHAR(100) NOT NULL DEFAULT(N''),
    cmnd VARCHAR(12) NOT NULL,
    -- 0. nam | 1. nữ
    gioiTinh BIT NOT NULL,
    ngaySinh DATE,
    soDienThoai VARCHAR(10),
    chucVu NVARCHAR(100) NOT NULL,
    mucLuong MONEY NOT NULL,
    trangThaiNV NVARCHAR(100) NULL,

    taiKhoan VARCHAR(100) NOT NULL,

    FOREIGN KEY (taiKhoan) REFERENCES dbo.TaiKhoan(tenDangNhap)
)
GO

CREATE TABLE KhachHang
(
    maKH VARCHAR(10) NOT NULL PRIMARY KEY,
    cmnd VARCHAR(12) NOT NULL,
    hoTen NVARCHAR(100) NOT NULL DEFAULT(N''),
    soDienThoai VARCHAR(10),
    ngaySinh DATE,
    -- 0. nam | 1. nữ
    gioiTinh BIT NOT NULL
)
GO

CREATE TABLE LoaiDichVu
(
    maLDV VARCHAR(6) NOT NULL PRIMARY KEY,
    tenLDV NVARCHAR(100) NOT NULL DEFAULT(N''),
)
GO


CREATE TABLE DichVu
(
    maDichVu VARCHAR(6) NOT NULL PRIMARY KEY,
    tenDichVu NVARCHAR(100) NOT NULL DEFAULT(N''),
    giaBan MONEY NOT NULL DEFAULT(0) CHECK(giaBan >= 0),
    soLuongTon INT NOT NULL DEFAULT(0) CHECK(soLuongTon >= 0),
    maLDV VARCHAR(6) NOT NULL,

    FOREIGN KEY (maLDV) REFERENCES dbo.LoaiDichVu (maLDV)
)
GO

CREATE TABLE LoaiPhong
(
    maLP VARCHAR(5) NOT NULL PRIMARY KEY,
    tenLP NVARCHAR(100) NOT NULL DEFAULT(N''),
    sucChua INT NOT NULL DEFAULT(0) CHECK(sucChua > 0),
    giaTien MONEY NOT NULL DEFAULT(0) CHECK(giaTien >= 0),
)
GO

CREATE TABLE Phong
(
    maPhong VARCHAR(5) NOT NULL PRIMARY KEY,
    --  0 là là còn trống | 1 là đang sử dụng
    tinhTrangP INT NOT NULL DEFAULT(0),
    viTri NVARCHAR(100) NOT NULL DEFAULT(N''),
    maLP VARCHAR(5) NOT NULL,

    FOREIGN KEY (maLP) REFERENCES dbo.LoaiPhong (maLP)
)
GO

CREATE TABLE HoaDon
(
    maHoaDon VARCHAR(15) PRIMARY KEY,
    ngayGioDat DATETIME NOT NULL DEFAULT(getdate()),
    ngayGioTra DATETIME,
    -- 0. chưa thanh toán | 1. đã thanh toán
    tinhTrangHD INT NOT NULL DEFAULT(0),
    TongTien MONEY NOT NULL DEFAULT(0) CHECK(TongTien >= 0),
    maNhanVien VARCHAR(10) NOT NULL,
    maKH VARCHAR(10) NOT NULL,
    maPhong VARCHAR(5) NOT NULL,

    FOREIGN KEY (maNhanVien) REFERENCES dbo.NhanVien (maNhanVien),
    FOREIGN KEY (maKH) REFERENCES dbo.KhachHang (maKH),
    FOREIGN KEY (maPhong) REFERENCES dbo.Phong (maPhong)
)
GO

CREATE TABLE CTDichVu
(
    maDichVu VARCHAR(6) NOT NULL,
    maHoaDon VARCHAR(15) NOT NULL,
    soLuongDat INT NOT NULL DEFAULT(1) CHECK(soLuongDat > 0),
    donGia MONEY NOT NULL DEFAULT(0) CHECK(donGia >= 0),
    tienDichVu MONEY NOT NULL DEFAULT(0) CHECK(tienDichVu >= 0),
    PRIMARY KEY (maDichVu, maHoaDon),
    FOREIGN KEY (maDichVU) REFERENCES dbo.DichVu (maDichVu),
    FOREIGN KEY (maHoaDon) REFERENCES dbo.HoaDon (maHoaDon)
)
GO


-- chèn dữ liệu
INSERT INTO dbo.LoaiDichVu
    (maLDV, tenLDV)
VALUES
    -- ('LDV001', N'Thức ăn'),
    -- ('LDV002', N'Đồ uống'),

    ('LDV001', N'Thạch Dừa'),
    ('LDV002', N'Đồ uống có cồn'),
    ('LDV003', N'Sinh tố'),
    ('LDV004', N'Nước đóng chai'),
    ('LDV005', N'Gỏi'),
    ('LDV006', N'Soup'),
    ('LDV007', N'Salad'),
    ('LDV008', N'Hải sản'),
    ('LDV009', N'Lẫu'),
    ('LDV010', N'Mì'),
    ('LDV011', N'Món gà'),
    ('LDV012', N'Món bò'),
    ('LDV013', N'Món heo'),
    ('LDV014', N'Khác')
GO

INSERT INTO dbo.DichVu
    (maDichVu, tenDichVu, giaBan, soLuongTon, maLDV)
VALUES
    -- thạch dừa
    ('DV0001', N'Thạch Dừa Nha Đam', 30000, 10, 'LDV001'),
    ('DV0002', N'Thạch Dừa Dâu', 35000, 50, 'LDV001'),
    ('DV0003', N'Thạch Dừa Bạc Hà', 35000, 30, 'LDV001'),
    ('DV0004', N'Thạch Dừa Cam tươi', 42000, 20, 'LDV001'),
    ('DV0005', N'Thạch Dừa Xoài', 30000, 15, 'LDV001'),
    ('DV0006', N'Thạch Dừa Yaourt', 42000, 10, 'LDV001'),

    -- Đồ uống có cồn
    ('DV0007', N'Iron man', 32000, 26, 'LDV002'),
    ('DV0008', N'Moscow Beer', 32000, 84, 'LDV002'),
    ('DV0009', N'Long Island iced Tea', 32000, 2, 'LDV002'),
    ('DV0010', N'Heineken (Pháp)', 50000, 84, 'LDV002'),
    ('DV0011', N'Heineken (Lon)', 30000, 85, 'LDV002'),
    ('DV0012', N'Heineken (Chai)', 27000, 72, 'LDV002'),
    ('DV0013', N'Spy', 65000, 83, 'LDV002'),
    ('DV0014', N'Budweiser (Lon/Chai)', 33000, 62, 'LDV002'),
    ('DV0015', N'Tiger (Chai)', 24000, 82, 'LDV002'),
    ('DV0016', N'Tiger Crystal (Chai)', 27000, 75, 'LDV002'),
    ('DV0017', N'Tiger Lon', 22000, 24, 'LDV002'),
    ('DV0018', N'Bia 33', 22000, 35, 'LDV002'),

    -- Sinh tố
    ('DV0019', N'Sinh Tố Yaourt Đá', 35000, 72, 'LDV003'),
    ('DV0020', N'Sinh Tố Yaourt Lô Hội Cam', 60000, 63, 'LDV003'),
    ('DV0021', N'Sinh Tố Dâu', 25000, 34, 'LDV003'),
    ('DV0022', N'Sinh Tố Bơ', 270000, 35, 'LDV003'),
    ('DV0023', N'Sinh Tố Dừa', 35000, 12, 'LDV003'),
    ('DV0024', N'Sinh Tố Đào', 35000, 45, 'LDV003'),
    ('DV0025', N'Sinh Tố Cà Rốt', 25000, 49, 'LDV003'),
    ('DV0026', N'Sinh Tố Cà Chua', 23000, 23, 'LDV003'),
    ('DV0027', N'Sinh Tố Thơm', 26000, 34, 'LDV003'),

    -- Nước đóng chai
    ('DV0028', N'SoDa', 30000, 28, 'LDV004'),
    ('DV0029', N'SoDa Xí Muội', 45000, 100, 'LDV004'),
    ('DV0030', N'SoDa Chanh Đường', 45000, 100, 'LDV004'),
    ('DV0031', N'Pepsi', 30000, 100, 'LDV004'),
    ('DV0032', N'7 Up', 20000, 50, 'LDV004'),
    ('DV0033', N'Mirinda(Sarsi)', 30000, 83, 'LDV004'),
    ('DV0034', N'Sting', 30000, 72, 'LDV004'),
    ('DV0035', N'Red Bull', 25000, 16, 'LDV004'),
    ('DV0036', N'Coca Cola', 20000, 30, 'LDV004'),
    ('DV0037', N'Trà Xanh C2', 23000, 30, 'LDV004'),
    ('DV0038', N'Trà Ô Long', 27000, 30, 'LDV004'),

    -- Gỏi
    ('DV0039', N'Gỏi Bưởi Hải Sản', 130000, 46, 'LDV005'),
    ('DV0040', N'Gỏi Miến Thái Lan', 100000, 35, 'LDV005'),
    ('DV0041', N'Gỏi Sứa Tôm Thịt', 127000, 83, 'LDV005'),
    ('DV0042', N'Gỏi Bò Bóp Thấu', 150000, 66, 'LDV005'),
    ('DV0043', N'Gỏi Bò Đề Nhất', 180000, 28, 'LDV005'),
    ('DV0044', N'Gỏi Sen Bào Hải Sản', 110000, 28, 'LDV005'),
    ('DV0045', N'Gỏi Ngó Sen Tôm Thịt', 120000, 23, 'LDV005'),
    ('DV0046', N'Gỏi Tai Heo Dưa Chuột', 140000, 34, 'LDV005'),
    ('DV0047', N'Gỏi Đu Đủ Bò Khô', 193000, 16, 'LDV005'),
    ('DV0048', N'Gỏi Thịt Gà Hoa Chuối', 133000, 43, 'LDV005'),
    ('DV0049', N'Gỏi Xoài Cá Cơm', 100000, 43, 'LDV005'),
    ('DV0050', N'Gỏi Gà Xé Phay', 136000, 43, 'LDV005'),

    -- Soup
    ('DV0051', N'Soup Bắp Cua Gà Xé', 40000, 28, 'LDV006'),
    ('DV0052', N'Soup Hải Sản Thái Lan', 40000, 92, 'LDV006'),
    ('DV0053', N'Cháo Hải Sản', 250000, 65, 'LDV006'),
    ('DV0054', N'Cháo Mực', 250000, 89, 'LDV006'),
    ('DV0055', N'Cháo Tôm Tươi', 200000, 67, 'LDV006'),
    ('DV0056', N'Cháo Cá', 220000, 8, 'LDV006'),

    -- Salad
    ('DV0057', N'Salad Thập cẩm', 110000, 18, 'LDV007'),
    ('DV0058', N'Salad Cá Ngừ', 99000, 29, 'LDV007'),
    ('DV0059', N'Salad Trộn Thịt Bò', 130000, 83, 'LDV007'),
    ('DV0060', N'Salad Cá Thu', 130000, 83, 'LDV007'),

    -- Hải sản
    ('DV0061', N'Cà Ri Tôm', 260000, 28, 'LDV008'),
    ('DV0062', N'Tôm Rang Me', 260000, 28, 'LDV008'),
    ('DV0063', N'Tôm Sú Hấp Bia / Nước Dừa', 260000, 88, 'LDV008'),
    ('DV0064', N'Cua Hấp Bia / Nước Dừa', 300000, 83, 'LDV008'),
    ('DV0065', N'Cua Lột Chiên Giòn', 250000, 15, 'LDV008'),
    ('DV0066', N'Cua Rang Me', 310000, 18, 'LDV008'),
    ('DV0067', N'Lẫu Cá Diêu Hồng', 275000, 26, 'LDV009'),
    ('DV0068', N'Lẫu Hải Sản', 330000, 26, 'LDV009'),
    ('DV0069', N'Lẫu Thái Lan', 330000, 73, 'LDV009'),
    ('DV0070', N'Mực Nướng Muối Ớt', 160000, 34, 'LDV008'),
    ('DV0071', N'Khô Mực Nướng', 130000, 47, 'LDV008'),
    ('DV0072', N'Mực Xào Sa Tế', 160000, 8, 'LDV008'),
    ('DV0073', N'Mực Chiên Xù', 160000, 6, 'LDV008'),
    ('DV0074', N'Khô Chiên Giòn', 160000, 76, 'LDV008'),

    --Mì
    ('DV0075', N'Mì Xào Bò', 79000, 29, 'LDV010'),
    ('DV0076', N'Mì Ý Sốt Bò Bằm', 79000, 29, 'LDV010'),
    ('DV0077', N'Hũ Tiếu Xào Bò', 79000, 38, 'LDV010'),
    ('DV0078', N'Miến Xào Tôm', 69000, 39, 'LDV010'),
    ('DV0079', N'Bún Xào Hải Sản', 79000, 35, 'LDV010'),
    ('DV0080', N'Bún Xào Thập Cẩm', 99000, 93, 'LDV010'),
    ('DV0081', N'Bún Xào Singapo', 89000, 12, 'LDV010'),
    ('DV0082', N'Nui Xào Thập Cẩm', 59000, 35, 'LDV010'),
    ('DV0083', N'Nui Xào Hải Sản', 59000, 23, 'LDV010'),
    ('DV0084', N'Nui Xào Bò', 59000, 44, 'LDV010'),

    -- Món gà
    ('DV0085', N'Cánh Gà Chiên Nước Mắm', 120000, 24, 'LDV011'),
    ('DV0086', N'Gà Hấp Thái Lan', 250000, 47, 'LDV011'),
    ('DV0087', N'Gà Quay Tứ Xuyên', 250000, 47, 'LDV011'),
    ('DV0088', N'Gà Hấp Muối', 180000, 34, 'LDV011'),
    ('DV0089', N'Gà Hấp Cải Xanh', 180000, 23, 'LDV011'),
    ('DV0090', N'Gà Hấp Lá Chanh', 180000, 29, 'LDV011'),
    ('DV0091', N'Cánh Gà Rang Me', 99000, 75, 'LDV011'),
    ('DV0092', N'Cánh Gà Chiên Bơ', 99000, 23, 'LDV011'),
    ('DV0093', N'Cánh Gà Chiên Xù', 99000, 39, 'LDV011'),
    ('DV0094', N'Cánh Gà Sốt Me', 99000, 39, 'LDV011'),
    ('DV0095', N'Cánh Gà Chiên Nước Mắm', 99000, 39, 'LDV011'),

    -- Món Bò
    ('DV0096', N'Bò Cuộn Phô Mai', 160000, 18, 'LDV012'),
    ('DV0097', N'Bò Lúc Lắc', 190000, 93, 'LDV012'),
    ('DV0098', N'Bò Xào Thơm', 169000, 34, 'LDV012'),
    ('DV0099', N'Bò Xào Sa Tế', 169000, 38, 'LDV012'),
    ('DV0100', N'Bò Xào Củ Hàng', 169000, 34, 'LDV012'),
    ('DV0101', N'Bắp Bò Hấp Gừng', 169000, 34, 'LDV012'),
    ('DV0102', N'Bắp Bò Nhúng Dấm ', 169000, 34, 'LDV012'),

    -- Món Heo
    ('DV0103', N'Heo Rừng Nướng Muối Ớt', 140000, 26, 'LDV013'),
    ('DV0104', N'Sườn Chiên Tiêu', 120000, 23, 'LDV013'),
    ('DV0105', N'Sườn Xào Chua Ngọt', 120000, 56, 'LDV013'),
    ('DV0106', N'Sườn Heo Chiên Tỏi', 120000, 34, 'LDV013'),
    ('DV0107', N'Sườn Heo Chiên Nước Mắm', 120000, 34, 'LDV013'),
    ('DV0108', N'Sườn Heo Sốt Chua Ngọt', 120000, 33, 'LDV013'),
    ('DV0109', N'Sườn Heo Chiên Ngũ Vị', 120000, 45, 'LDV013'),
    ('DV0110', N'Bào Tử Xào Cải Chua', 120000, 85, 'LDV013'),
    ('DV0111', N'Bào Tử Xào Sa Tế', 120000, 85, 'LDV013'),
    ('DV0112', N'Bào Tử Hấp Gừng', 120000, 85, 'LDV013'),

    -- Khác
    ('DV0113', N'Khăn Ướt', 5000, 200, 'LDV014')
GO

INSERT INTO dbo.TaiKhoan
    (tenDangNhap, matKhau, tinhTrangTK)
VALUES
    ('phamdangdan', '1234567', 1),
    ('huynhtuananh', '1234567', 1),
    ('langnhapsang', '1234567', 1),
    ('vominhhieu', '1234567', 1),
    ('nhanvien1', '1234567', 1),
    ('nhanvien2', '1234567', 1),
    ('nhanvien3', '1234567', 1),
    ('nhanvien4', '123456', 1),
    ('nhanvien5', '123456', 0)
GO

INSERT INTO dbo.NhanVien
    (maNhanVien, cmnd, hoTen, ngaySinh, gioiTinh, soDienThoai, chucVu, mucLuong, taiKhoan, trangThaiNV)
VALUES
    ('NV00000001', '111111111', N'Phạm Đăng Đan', '2001-01-01', 0, '0312345678', N'Chủ quán', 6000000, 'phamdangdan', N'Đang làm'),
    ('NV00000002', '111111113', N'Huỳnh Tuấn Anh', '2001-01-01', 0, '0312345671', N'Chủ quán', 6000000, 'huynhtuananh', N'Đang làm'),
    ('NV00000003', '111111112', N'Lăng Nhật Sang', '2001-01-01', 0, '0312345679', N'Chủ quán', 6000000, 'langnhapsang', N'Đang làm'),
    ('NV00000004', '111111114', N'Võ Minh Hiếu', '2001-01-01', 0, '0312345672', N'Chủ quán', 6000000, 'vominhhieu', N'Đang làm'),
    ('NV00000005', '111111115', N'Nguyễn Xuân Anh', '1999-06-14', 1, '0312345673', N'Nhân Viên', 3000000, 'nhanvien1', N'Đang làm'),
    ('NV00000006', '111111116', N'Trần Thị Ngọc Vân', '1998-07-17', 1, '0812144673', N'Nhân Viên', 2800000, 'nhanvien2', N'Đang làm'),
    ('NV00000007', '111111117', N'Trần Vinh Can', '1993-08-27', 0, '0715344673', N'Nhân Viên', 3100000, 'nhanvien3', N'Đang làm'),
    ('NV00000008', '111111118', N'Bùi Ngọc Văn', '1996-12-23', 0, '0532344647', N'Nhân Viên', 2700000, 'nhanvien4', N'Đang làm'),
    ('NV00000009', '111111119', N'Bùi Văn Xuân', '1996-06-23', 0, '0532234647', N'Nhân Viên', 2700000, 'nhanvien5', N'Đã nghỉ')
GO

INSERT INTO dbo.KhachHang
    (maKH, cmnd, hoTen, gioiTinh, soDienThoai, ngaySinh)
VALUES
    ('KH00000001', '200000001', N'Nguyễn Việt Nam', 0, '0947536844', '1987-09-15'),
    ('KH00000002', '200000002', N'Nguyễn Huỳnh Dũng', 0, '0338618310', '1989-07-24'),
    ('KH00000003', '200000003', N'Ngô Quốc Cường', 0, '0915362548', '1999-07-08'),
    ('KH00000004', '200000004', N'Nguyễn Thị Hảo', 1, '0343688297', '1978-06-20'),
    ('KH00000005', '200000005', N'Nguyễn Thị Hậu', 1, '0795815322', '1968-01-09'),
    ('KH00000006', '200000006', N'Nguyễn Vũ Lâm Đức', 0, '0967127210', '1993-09-06'),
    ('KH00000007', '200000007', N'Phan Thị Thủy', 1, '0329807464', '1987-06-10'),
    ('KH00000008', '200000008', N'Phạm Văn Duy', 0, '0328038496', '1992-12-05'),
    ('KH00000009', '200000009', N'Hoàng Tiến Hào', 0, '', '1998-03-02'),
    ('KH00000010', '200000010', N'Dương Ngọc Vy', 0, '0948178446', '1993-06-14'),
    ('KH00000011', '200000011', N'Nguyễn Chí Hoàng', 0, '0983609208', '1984-08-05'),
    ('KH00000012', '200000012', N'Đỗ Huy Hữu', 0, '0382614352', '1985-09-18'),
    ('KH00000013', '200000013', N'Nguyễn Anh Khoa', 1, '0867490478', '1995-08-02'),
    ('KH00000014', '200000014', N'Trần Anh Lâm', 1, '0965494140', '1973-08-22'),
    ('KH00000015', '200000015', N'Nguyễn Thái Huấn', 0, '0978477703', '1990-08-10'),
    ('KH00000016', '200000016', N'Nguyễn Dương Lập', 0, '0772446566', '1988-10-28'),
    ('KH00000017', '200000017', N'Nguyễn Văn Nghị', 0, '0942074191', '1994-12-24'),
    ('KH00000018', '200000018', N'Trần Hoài Phương', 0, '0824601980', '1982-05-28'),
    ('KH00000019', '200000019', N'Đinh Diệp Vy', 1, '0819490559', '1983-11-16'),
    ('KH00000020', '200000020', N'Hoàng Thị Hồng', 1, '0978289481', '1985-07-29'),
    ('KH00000021', '200000021', N'Nguyễn Hùng An', 1, '0965236555', '1990-07-09'),
    ('KH00000022', '200000022', N'Trần Thị Minh An', 1, '0364973888', '1984-01-18'),
    ('KH00000023', '200000023', N'Trần Lâm Sang', 0, '0366497800', '1987-06-22'),
    ('KH00000024', '200000024', N'Lê Minh Hiệu', 0, '0387773717', '1974-03-14'),
    ('KH00000025', '200000025', N'Phan Trọng Hoài', 0, '0368783420', '1988-06-21'),
    ('KH00000026', '200000026', N'Đỗ Huy Hiếu', 0, '0394787764', '1994-06-01'),
    ('KH00000027', '200000027', N'Nguyễn Văn Hinh', 0, '0982701798', '1995-11-21'),
    ('KH00000028', '200000028', N'Trần Đông Hoàng', 0, '0978581659', '1995-08-26'),
    ('KH00000029', '200000029', N'Nguyễn Thái Huấn', 0, '0966904885', '1996-11-14'),
    ('KH00000030', '200000030', N'Phan Văn Học', 0, '0368232307', '1984-08-17'),
    ('KH00000031', '200000031', N'Vương Ánh Nga', 1, '0973704864', '1992-03-23'),
    ('KH00000032', '200000032', N'Võ Trần Ngọc Nguyên', 1, '0942221798', '1999-03-16'),
    ('KH00000033', '200000033', N'Nguyễn Thành Nhất', 0, '0907498803', '1998-05-27'),
    ('KH00000034', '200000034', N'Hà Danh Phú', 0, '0342409371', '1997-01-19'),
    ('KH00000035', '200000035', N'Nguyễn Đình Danh', 0, '0943853797', '1996-11-29'),
    ('KH00000036', '200000036', N'Hồ Tuyết Phương', 1, '0708119447', '1993-04-28'),
    ('KH00000037', '200000037', N'Huỳnh Nhật Phúc', 0, '0337857570', '1984-01-14'),
    ('KH00000038', '200000038', N'Trần Vinh Quân', 0, '0395364524', '1983-03-06'),
    ('KH00000039', '200000039', N'Nguyễn Trung Sang', 0, '0339194890', '1989-02-27'),
    ('KH00000040', '200000040', N'Lê Chí Tài', 0, '0945391881', '1970-01-17'),
    ('KH00000041', '200000041', N'Lê Văn Thiện', 0, '0762411115', '1999-06-08'),
    ('KH00000042', '200000042', N'Nguyễn Thiên Châu', 1, '0932067737', '1992-03-10'),
    ('KH00000043', '200000043', N'Nguyễn Võ Thiên Thi', 1, '0973553184', '1971-07-24'),
    ('KH00000044', '200000044', N'Nguyễn Minh Trọng', 0, '0988459265', '1983-05-18'),
    ('KH00000045', '200000045', N'Hồ Công Vinh', 0, '0362797288 ', '1998-05-18'),
    ('KH00000046', '200000046', N'Mã Thanh Việt', 0, '0369306815', '1989-06-27'),
    ('KH00000047', '200000047', N'Nguyễn Hoàng Vương', 0, '0971695983', '1993-03-13'),
    ('KH00000048', '200000048', N'Đinh Diệp Anh', 0, '0965692768', '1992-10-31'),
    ('KH00000049', '200000049', N'Huỳnh Tuấn Bình', 0, '0932669104', '1996-10-31'),
    ('KH00000050', '200000050', N'Nguyễn Tấn Công', 0, '0965667322', '1997-08-26'),
    ('KH00000051', '200000051', N'Đinh Thị Thiên Thư', 1, '0337857260', '2000-03-26'),
    ('KH00000052', '200000052', N'Đoàn Kiều Mai', 1, '0373523384', '2001-12-26'),
    ('KH00000053', '200000053', N'Cao Thị Hồng Ngọc', 1, '0369462754', '2000-02-12'),
    ('KH00000054', '200000054', N'Trần Đông Phùng', 1, '0864965111', '2001-11-03'),
    ('KH00000055', '200000055', N'Mai Nhật Hùng', 0, '0964692660', '2000-08-05'),
    ('KH00000056', '200000056', N'Nguyễn Việt Bảo', 0, '0387383263', '2001-09-03'),
    ('KH00000057', '200000057', N'Nguyễn Thái Anh', 1, '0372253293', '2000-09-15'),
    ('KH00000058', '200000058', N'Nguyễn Minh Cường', 0, '0856624858', '2000-04-19'),
    ('KH00000059', '200000059', N'Hoàng Văn Chính', 0, '0359434872', '2000-02-03'),
    ('KH00000060', '200000060', N'Ngô Quốc Văn', 0, '0933694933', '2000-06-13'),
    ('KH00000061', '200000061', N'Nguyễn Đức Dũng', 0, '0378390583', '2001-12-23'),
    ('KH00000062', '200000062', N'Nguyễn Lâm Đạt', 0, '0845717331', '2000-06-28'),
    ('KH00000063', '200000063', N'Lê Quang Duy', 0, '', '2000-08-12'),
    ('KH00000064', '200000064', N'Lê Văn Duy', 0, '0917482468', '1988-01-26'),
    ('KH00000065', '200000065', N'Nguyễn Chí Hùng', 0, '0819490875', '1986-08-30'),
    ('KH00000066', '200000066', N'Võ Trung Trọng', 0, '0384542530', '1978-09-28'),
    ('KH00000067', '200000067', N'Đỗ Trung Hoàng', 0, '0356058207', '1973-06-01'),
    ('KH00000068', '200000068', N'Đỗ Huy Quang', 0, '', '1985-11-04'),
    ('KH00000069', '200000069', N'Nguyễn Văn Vương', 0, '0352830920', '1989-03-20'),
    ('KH00000070', '200000070', N'Lâm Vương Ánh', 1, '0372224106', '1995-09-23'),
    ('KH00000071', '200000071', N'Nguyễn Vinh Hoàng', 0, '0919602777', '2000-07-28'),
    ('KH00000072', '200000072', N'Thái Anh Vân', 1, '0843766330', '2000-05-20'),
    ('KH00000073', '200000073', N'Nguyễn Vương Hưng', 0, '0846299993', '2000-04-12'),
    ('KH00000074', '200000074', N'Trần Ngọc Mây', 1, '0397530150', '2000-01-19'),
    ('KH00000075', '200000075', N'Bùi Trọng Phùng', 0, '0978964807', '1988-01-02'),
    ('KH00000076', '200000076', N'Trần Minh Nhật', 0, '0368795878', '1999-12-05'),
    ('KH00000077', '200000077', N'Phạm Minh Huy', 0, '0382572701', '1994-09-18'),
    ('KH00000078', '200000078', N'Đào Tấn Kha', 0, '0793464482', '1980-10-28'),
    ('KH00000079', '200000079', N'Lê Duy Khang', 0, '0389324988', '1990-11-16'),
    ('KH00000080', '200000080', N'Huỳnh Nhật Long', 0, '0339686446', '1991-07-19'),
    ('KH00000081', '200000081', N'Vũ Gia Quang', 0, '0911531895', '2000-04-21'),
    ('KH00000082', '200000082', N'Trần Thị Tuyết Băng', 1, '0967806899', '2001-04-28'),
    ('KH00000083', '200000083', N'Nguyễn Bá Luân', 0, '0964406702', '2001-04-21'),
    ('KH00000084', '200000084', N'Huỳnh Công Chiến', 0, '0385142157', '2001-06-09'),
    ('KH00000085', '200000085', N'Huỳnh Công Lý', 0, '0334548945', '2001-05-04'),
    ('KH00000086', '200000086', N'Bùi Thành Công', 0, '0838607346', '1984-07-25'),
    ('KH00000087', '200000087', N'Đỗ Trung Hiếu', 0, '0334715333', '1996-08-21'),
    ('KH00000088', '200000088', N'Trần Thanh Phong', 0, '0979042843', '1985-07-26'),
    ('KH00000089', '200000089', N'Huỳnh Minh Phước', 0, '0869648870', '1997-12-16'),
    ('KH00000090', '200000090', N'Đào Minh Quý', 0, '0377723969', '1992-10-06'),
    ('KH00000091', '200000091', N'Đặng Tấn Duy', 0, '0986439811', '1986-01-22'),
    ('KH00000092', '200000092', N'Lê Tuấn Tín', 0, '0908725223', '1985-10-05'),
    ('KH00000093', '200000093', N'Lê Văn Tú', 0, '0944708156', '1981-09-22'),
    ('KH00000094', '200000094', N'Nguyễn Thị Hồng', 1, '0964505419', '2000-04-25'),
    ('KH00000095', '200000095', N'Đoàn Thị Uyên Linh', 1, '0902642649', '2001-07-26'),
    ('KH00000096', '200000096', N'Cao Thị Vân Mây', 1, '0984218383', '2000-03-17'),
    ('KH00000097', '200000097', N'Trần Thị Hà My', 1, '0833352762', '2000-05-17'),
    ('KH00000098', '200000098', N'Đinh Thị Phương', 1, '0983860448', '1986-06-01'),
    ('KH00000099', '200000099', N'Nguyễn Thị Ngọc Châu', 1, '0978763637', '1986-04-21'),
    ('KH00000100', '200000100', N'Hà Thị Tố Uyên', 1, '0374805996', '2000-05-14'),
    ('KH00000101', '200000101', N'Trần Anh Lâm', 0, '0393575926', '1993-09-20'),
    ('KH00000102', '200000102', N'Võ Hửu Tường', 0, '0917190242', '1994-09-12')
GO

INSERT INTO dbo.LoaiPhong
    (maLP, tenLP, sucChua, giaTien)
VALUES
    ('LP001', N'Phòng 5 người', 5, 80000),
    ('LP002', N'Phòng 10 người', 10, 120000),
    ('LP003', N'Phòng 20 người', 20, 180000)
GO

INSERT INTO dbo.Phong
    (maPhong, tinhTrangP, viTri, maLP)
VALUES
    ('P0001', 0, N'Tầng 1', 'LP001'),
    ('P0002', 0, N'Tầng 1', 'LP001'),
    ('P0003', 0, N'Tầng 1', 'LP001'),
    ('P0004', 0, N'Tầng 1', 'LP001'),
    ('P0005', 0, N'Tầng 1', 'LP001'),
    ('P0006', 0, N'Tầng 2', 'LP002'),
    ('P0007', 0, N'Tầng 2', 'LP002'),
    ('P0008', 0, N'Tầng 2', 'LP002'),
    ('P0009', 0, N'Tầng 2', 'LP002'),
    ('P0010', 0, N'Tầng 2', 'LP002'),
    ('P0011', 0, N'Tầng 3', 'LP003'),
    ('P0012', 0, N'Tầng 3', 'LP003'),
    ('P0013', 0, N'Tầng 3', 'LP003'),
    ('P0014', 0, N'Tầng 3', 'LP003'),
    ('P0015', 0, N'Tầng 3', 'LP003')
GO

INSERT INTO dbo.HoaDon
    (maHoaDon, ngayGioDat, ngayGioTra, tinhTrangHD, TongTien, maNhanVien, maKH, maPhong)
VALUES
    ('HD2021100100001' , '2021-10-01 10:00:00', '2021-10-01 12:00:00', 1, 379500, 'NV00000001', 'KH00000001', 'P0001'),
    ('HD2021100100002' , '2021-10-01 15:00:00', '2021-10-01 17:00:00', 1, 376200, 'NV00000003', 'KH00000003', 'P0002'),
    ('HD2021100100003' , '2021-10-01 15:30:00', '2021-10-01 18:00:00', 1, 453200, 'NV00000004', 'KH00000004', 'P0003'),
    ('HD2021100200001' , '2021-10-02 12:00:00', '2021-10-02 13:00:00', 1, 330000, 'NV00000002', 'KH00000002', 'P0004'),
    ('HD2021100200002' , '2021-10-02 12:00:00', '2021-10-02 13:00:00', 1, 330000, 'NV00000002', 'KH00000002', 'P0005')
GO

INSERT INTO dbo.CTDichVu
    (maDichVu, maHoaDon, soLuongDat, donGia, tienDichVu)
VALUES
    ('DV0001', 'HD2021100100001', 1, 30000, 30000),
    ('DV0002', 'HD2021100100001', 2, 35000, 70000),
    ('DV0003', 'HD2021100100001', 2, 35000, 70000),
    ('DV0113', 'HD2021100100001', 3, 5000, 150000),

    ('DV0003', 'HD2021100100002', 2, 35000, 70000),
    ('DV0002', 'HD2021100100002', 2, 35000, 70000),
    ('DV0004', 'HD2021100100002', 1, 42000, 42000),

    ('DV0001', 'HD2021100100003', 1, 30000, 30000),
    ('DV0003', 'HD2021100100003', 2, 75000, 70000),
    ('DV0007', 'HD2021100100003', 1, 32000, 32000),

    ('DV0002', 'HD2021100200001', 2, 35000, 70000),
    ('DV0001', 'HD2021100200001', 4, 30000, 120000),
    ('DV0005', 'HD2021100200001', 1, 30000, 30000),

    ('DV0002', 'HD2021100200002', 2, 35000, 70000),
    ('DV0001', 'HD2021100200002', 4, 30000, 120000),
    ('DV0005', 'HD2021100200002', 1, 30000, 30000)
GO


-- truy vấn

-- chuyển đổi kí tự có dấu thành không dấu và ngược lại
CREATE FUNCTION [dbo].[fuConvertToUnsign] ( @strInput NVARCHAR(4000) ) 
RETURNS NVARCHAR(4000) 
AS 
BEGIN
    IF @strInput IS NULL RETURN @strInput
    IF @strInput = '' RETURN @strInput
    DECLARE @RT NVARCHAR(4000)
    DECLARE @SIGN_CHARS NCHAR(136)
    DECLARE @UNSIGN_CHARS NCHAR (136)
    SET @SIGN_CHARS = N'ăâđêôơưàảãạáằẳẵặắầẩẫậấèẻẽẹéềểễệế ìỉĩịíòỏõọóồổỗộốờởỡợớùủũụúừửữựứỳỷỹỵý ĂÂĐÊÔƠƯÀẢÃẠÁẰẲẴẶẮẦẨẪẬẤÈẺẼẸÉỀỂỄỆẾÌỈĨỊÍ ÒỎÕỌÓỒỔỖỘỐỜỞỠỢỚÙỦŨỤÚỪỬỮỰỨỲỶỸỴÝ' +NCHAR(272)+ NCHAR(208)
    SET @UNSIGN_CHARS = N'aadeoouaaaaaaaaaaaaaaaeeeeeeeeee iiiiiooooooooooooooouuuuuuuuuuyyyyy AADEOOUAAAAAAAAAAAAAAAEEEEEEEEEEIIIII OOOOOOOOOOOOOOOUUUUUUUUUUYYYYYDD'
    DECLARE @COUNTER INT
    DECLARE @COUNTER1 INT
    SET @COUNTER = 1
    WHILE (@COUNTER <=LEN(@strInput)) BEGIN
        SET @COUNTER1 = 1
        WHILE (@COUNTER1 <=LEN(@SIGN_CHARS)+1) BEGIN
            IF UNICODE(SUBSTRING(@SIGN_CHARS, @COUNTER1,1)) = UNICODE(SUBSTRING(@strInput,@COUNTER ,1) ) BEGIN
                IF @COUNTER=1 SET @strInput = SUBSTRING(@UNSIGN_CHARS, @COUNTER1,1) + SUBSTRING(@strInput, @COUNTER+1,LEN(@strInput)-1) ELSE SET @strInput = SUBSTRING(@strInput, 1, @COUNTER-1) +SUBSTRING(@UNSIGN_CHARS, @COUNTER1,1) + SUBSTRING(@strInput, @COUNTER+1,LEN(@strInput)- @COUNTER)
                BREAK
            END
            SET @COUNTER1 = @COUNTER1 +1
        END
        SET @COUNTER = @COUNTER +1
    END
    SET @strInput = replace(@strInput,' ','-')
    RETURN @strInput
END
GO


-- tài khoản
CREATE PROC USP_Login
    @username NVARCHAR(100),
    @password NVARCHAR(100)
AS
BEGIN
    SELECT tk.matKhau, tk.tenDangNhap, tk.tinhTrangTK
    FROM dbo.TaiKhoan tk
    WHERE tenDangNhap = @username AND matKhau = @password
END
GO

CREATE PROC USP_getAccountByUsername
    @username VARCHAR(100)
AS
BEGIN
    SELECT tk.tenDangNhap, tk.matKhau, tk.tinhTrangTK
    FROM dbo.TaiKhoan tk
    WHERE tk.tenDangNhap = @username
END
GO

CREATE PROC USP_getAccountList
AS
BEGIN
    SELECT tk.tenDangNhap, tk.matKhau, tk.tinhTrangTK
    FROM dbo.TaiKhoan tk
END
GO


-- loại dịch vụ
CREATE PROC USP_getServiceTypeList
AS
BEGIN
    SELECT ldv.tenLDV, ldv.maLDV
    FROM dbo.LoaiDichVu ldv
END
GO

CREATE PROC USP_getServiceTypeByName
    @ServiceTypeName NVARCHAR(100)
AS
BEGIN
    SELECT ldv.tenLDV, ldv.maLDV
    FROM dbo.LoaiDichVu ldv
    WHERE ldv.tenLDV = @ServiceTypeName
END
GO

CREATE PROC USP_getServiceTypeById
    @ServiceTypeId VARCHAR(6)
AS
BEGIN
    SELECT ldv.tenLDV, ldv.maLDV
    FROM dbo.LoaiDichVu ldv
    WHERE ldv.maLDV = @ServiceTypeId
END
GO

CREATE PROC USP_getLastServiceTypeID
AS
BEGIN
    SELECT TOP 1
        ldv.maLDV
    FROM dbo.LoaiDichVu ldv
    ORDER BY ldv.maLDV DESC
END
GO

CREATE PROC USP_getServiceTypeListByName
    @serviceTypeName NVARCHAR(100)
AS
BEGIN
    DECLARE @name NVARCHAR(102) = N'%'+ @serviceTypeName + N'%'
    SELECT ldv.maLDV, ldv.tenLDV
    FROM dbo.LoaiDichVu ldv
    WHERE dbo.fuConvertToUnsign(ldv.tenLDV) LIKE dbo.fuConvertToUnsign(@name)
END
GO


CREATE PROC USP_insertServiceType
    @servideTypeId VARCHAR(6),
    @serviceTypeName NVARCHAR(100)
AS
BEGIN
    DECLARE @isExitsServiceTypeId VARCHAR(5)
    BEGIN TRANSACTION
    INSERT INTO dbo.LoaiDichVu
        (maLDV, tenLDV)
    VALUES
        (@servideTypeId, @serviceTypeName)
    -- tìm mã loại dịch vụ vừa thêm
    SELECT @isExitsServiceTypeId = ldv.maLDV
    FROM dbo.LoaiDichVu ldv
    WHERE ldv.maLDV = @servideTypeId

    -- Nếu có thì print 1 (thành công)
    -- Nếu không có thì print 0 (thất bại)
    IF @isExitsServiceTypeId IS NULL
    BEGIN
        ROLLBACK
        PRINT 0
    END
    ELSE 
    BEGIN
        COMMIT
        PRINT 1
    END
END
GO

CREATE PROC USP_updateInfoServiceType
    @servideTypeId VARCHAR(6),
    @serviceTypeName NVARCHAR(100)
AS
BEGIN
    BEGIN TRANSACTION
    UPDATE dbo.LoaiDichVu
        SET tenLDV = @serviceTypeName
        WHERE maLDV = @servideTypeId

    DECLARE @isExitsId VARCHAR(6)
    SELECT @isExitsId = ldv.maLDV
    FROM dbo.LoaiDichVu ldv
    WHERE ldv.maLDV = @servideTypeId
        AND ldv.tenLDV = @serviceTypeName

    IF @isExitsId IS NULL
    BEGIN
        PRINT 0
        ROLLBACK
    END
    ELSE
    BEGIN
        PRINT 1
        COMMIT
    END
END
GO


-- dịch vụ
CREATE PROC USP_getServiceListByName
    @serviceName NVARCHAR(100)
AS
BEGIN
    DECLARE @keyword NVARCHAR(102) = N'%' + @serviceName + N'%'
    SELECT dv.maDichVu, dv.tenDichVu, dv.giaBan, dv.soLuongTon,
        ldv.maLDV, ldv.tenLDV
    FROM dbo.DichVu dv, dbo.LoaiDichVu ldv
    WHERE dv.maLDV = ldv.maLDV
        AND dbo.fuConvertToUnsign(dv.tenDichVu) LIKE dbo.fuConvertToUnsign(@keyword)
END
GO

CREATE PROC USP_getServiceListByNameAndServiceTypeName
    @serviceName NVARCHAR(100),
    @serviceTypeName NVARCHAR(100)
AS
BEGIN
    DECLARE @keyword NVARCHAR(102) = N'%' + @serviceName + N'%'
    SELECT dv.maDichVu, dv.tenDichVu, dv.giaBan, dv.soLuongTon,
        ldv.maLDV, ldv.tenLDV
    FROM dbo.DichVu dv, dbo.LoaiDichVu ldv
    WHERE dv.maLDV = ldv.maLDV
        AND dbo.fuConvertToUnsign(dv.tenDichVu) LIKE dbo.fuConvertToUnsign(@keyword)
        AND ldv.tenLDV = @serviceTypeName
END
GO

CREATE PROC USP_getServiceList
AS
BEGIN
    SELECT dv.maDichVu, dv.giaBan, dv.soLuongTon, dv.tenDichVu,
        ldv.tenLDV, ldv.maLDV
    FROM dbo.DichVu dv, dbo.LoaiDichVu ldv
    WHERE dv.maLDV = ldv.maLDV;
END
GO

CREATE PROC USP_getServiceListByServiceTypeName
    @ServiceTypeName NVARCHAR(100)
AS
BEGIN
    SELECT dv.maDichVu, dv.giaBan, dv.soLuongTon, dv.tenDichVu,
        ldv.tenLDV, ldv.maLDV
    FROM dbo.DichVu dv, dbo.LoaiDichVu ldv
    WHERE dv.maLDV = ldv.maLDV
        AND ldv.tenLDV = @ServiceTypeName
END
GO

CREATE PROC USP_getServiceByName
    @serviceName NVARCHAR(100)
AS
BEGIN
    SELECT dv.maDichVu, dv.giaBan, dv.soLuongTon, dv.tenDichVu,
        ldv.tenLDV, ldv.maLDV
    FROM dbo.DichVu dv, dbo.LoaiDichVu ldv
    WHERE dv.maLDV = ldv.maLDV
        AND dv.tenDichVu = @serviceName
END
GO

CREATE PROC USP_getQuantityByServiceName
    @serviceName NVARCHAR(100)
AS
BEGIN
    SELECT dv.soLuongTon
    FROM dbo.DichVu dv
    WHERE dv.tenDichVu = @serviceName
END
GO

CREATE PROC USP_getLastServiceId
AS
BEGIN
    SELECT TOP 1
        dv.maDichVu
    FROM dbo.DichVu dv
    ORDER BY dv.maDichVu DESC
END
GO

CREATE PROC USP_getServiceById
    @serviceId VARCHAR(5)
AS
BEGIN
    SELECT dv.maDichVu, dv.giaBan, dv.soLuongTon, dv.tenDichVu,
        ldv.tenLDV, ldv.maLDV
    FROM dbo.DichVu dv, dbo.LoaiDichVu ldv
    WHERE dv.maLDV = ldv.maLDV
        AND dv.maDichVu = @serviceId
END
GO

CREATE PROC USP_insertService
    @servideId VARCHAR(6),
    @serviceName NVARCHAR(100),
    @price MONEY,
    @quantityInStock INT,
    @serviceTypeId VARCHAR(6)
AS
BEGIN
    DECLARE @isExitsServiceId VARCHAR(6)
    BEGIN TRANSACTION
    INSERT INTO dbo.DichVu
        (maDichVu, tenDichVu, giaBan, soLuongTon, maLDV)
    VALUES
        (@servideId, @serviceName, @price, @quantityInStock, @serviceTypeId)

    SELECT @isExitsServiceId = dv.maDichVu
    FROM dbo.DichVu dv
    WHERE dv.maDichVu = @servideId

    IF @isExitsServiceId IS NULL
    BEGIN
        ROLLBACK
        PRINT 0
    END
    ELSE 
    BEGIN
        COMMIT
        PRINT 1
    END

END
GO

CREATE PROC USP_getServiceNameById
    @servideId VARCHAR(6)
AS
BEGIN
    SELECT dv.tenDichVu
    FROM dbo.DichVu dv
    WHERE dv.maDichVu = @servideId
END
GO

CREATE PROC USP_updateInfoService
    @servideId VARCHAR(6),
    @serviceName NVARCHAR(100),
    @price MONEY,
    @quantityInStock INT,
    @serviceTypeId VARCHAR(6)
AS
BEGIN
    BEGIN TRANSACTION
    UPDATE dbo.DichVu
        SET giaBan = @price, 
        tenDichVu = @serviceName, 
        maLDV = @serviceTypeId, 
        soLuongTon = @quantityInStock
        WHERE maDichVu = @servideId

    DECLARE @isExitsId VARCHAR(6)
    SELECT @isExitsId = dv.maDichVu
    FROM dbo.DichVu dv
    WHERE dv.maDichVu = @servideId
        AND dv.tenDichVu = @serviceName
        AND dv.giaBan = @price
        AND dv.soLuongTon = @quantityInStock
        AND dv.maLDV = @serviceTypeId

    IF @isExitsId IS NULL
    BEGIN
        PRINT 0
        ROLLBACK
    END
    ELSE
    BEGIN
        PRINT 1
        COMMIT
    END
END
GO


-- hóa đơn
CREATE PROC USP_getUncheckBillByRoomId
    @roomId VARCHAR(5)
AS
BEGIN
    SELECT hd.maHoaDon, hd.ngayGioDat, hd.ngayGioTra, hd.tinhTrangHD, hd.TongTien
    FROM dbo.HoaDon hd, dbo.Phong p
    WHERE hd.maPhong = p.maPhong
        AND p.maPhong = @roomId
        AND hd.tinhTrangHD = 0
END
GO

CREATE PROC USP_getBillByBillId
    @billId VARCHAR(15)
AS
BEGIN
    SELECT hd.maHoaDon, hd.ngayGioDat, hd.ngayGioTra, hd.tinhTrangHD,
        hd.TongTien
    FROM dbo.HoaDon hd
    WHERE hd.maHoaDon = @billId
END
GO

CREATE PROC USP_insertBill
    @billId VARCHAR(15),
    @orderDate DATETIME,
    @staffId VARCHAR(10),
    @customerId VARCHAR(10),
    @roomId VARCHAR(5)
AS
BEGIN
    DECLARE @isExitsBillId VARCHAR(15)
    BEGIN TRANSACTION
    INSERT INTO dbo.HoaDon
        (maHoaDon, ngayGioDat, ngayGioTra, tinhTrangHD, TongTien, maNhanVien, maKH, maPhong)
    VALUES
        (@billId, @orderDate, NULL, 0, 0.0, @staffId, @customerId, @roomId)

    SELECT @isExitsBillId = hd.maHoaDon
    FROM dbo.HoaDon hd
    WHERE hd.maHoaDon = @billId
        AND hd.ngayGioDat = @orderDate
        AND hd.maNhanVien = @staffId
        AND hd.maKH = @customerId
        AND hd.maPhong = @roomId
        AND hd.tinhTrangHD = 0
        AND hd.TongTien = 0.0
        AND hd.ngayGioTra IS NULL

    IF @isExitsBillId IS NULL
    BEGIN
        PRINT 0
        ROLLBACK
    END
    ELSE
    BEGIN
        PRINT 1
        COMMIT
    END
END
GO

CREATE PROC USP_getLastBillId
AS
BEGIN
    SELECT TOP 1
        hd.maHoaDon
    FROM dbo.HoaDon hd
    ORDER BY hd.maHoaDon DESC
END
GO

CREATE PROC USP_payment
    @billId VARCHAR(15),
    @paymentDate DATETIME,
    @totalPrice MONEY
AS
BEGIN
    DECLARE @roomId VARCHAR(5)
    DECLARE @isExitsBillId VARCHAR(15) = NULL
    BEGIN TRANSACTION
    UPDATE dbo.HoaDon 
        SET tinhTrangHD = 1 , TongTien = @totalPrice, ngayGioTra = @paymentDate
        WHERE maHoaDon = @billId

    SELECT @isExitsBillId = hd.maHoaDon
    FROM dbo.HoaDon hd
    WHERE hd.maHoaDon = @billId
        AND hd.TongTien = @totalPrice
        AND hd.tinhTrangHD = 1
        AND hd.ngayGioTra = @paymentDate

    IF (@isExitsBillId IS NULL)
        BEGIN
        PRINT 0
        ROLLBACK
    END
        ELSE
        BEGIN
        SELECT @roomID = hd.maPhong
        FROM dbo.HoaDon hd
        WHERE hd.maHoaDon = @billId

        UPDATE dbo.Phong
            SET tinhTrangP = 0
            WHERE maPhong = @roomID

        PRINT 1
        COMMIT
    END
END
GO

CREATE PROC USP_getTotalPriceBill
    @billId VARCHAR(15)
AS
BEGIN
    SELECT TOP 1
        hd.TongTien
    FROM dbo.HoaDon hd
    WHERE hd.maHoaDon = @billId
END
GO

CREATE PROC USP_getBillListByDate
    @startDate DATE,
    @endDate DATE
AS
BEGIN
    SELECT hd.maHoaDon, hd.ngayGioDat, hd.ngayGioTra, hd.tinhTrangHD, hd.TongTien
    FROM dbo.HoaDon hd
    WHERE hd.tinhTrangHD = 1
        AND hd.ngayGioDat BETWEEN @startDate AND @endDate
    GROUP BY hd.maHoaDon, hd.ngayGioDat, hd.ngayGioTra, hd.tinhTrangHD, hd.TongTien
END
GO

CREATE PROC USP_getBillListByDateAndCustomerPhoneNumber
    @phoneNumber VARCHAR(10),
    @startDate DATE,
    @endDate DATE
AS
BEGIN
    DECLARE @keyword VARCHAR(12) = N'%' + @phoneNumber + N'%'
    SELECT hd.maHoaDon, hd.ngayGioDat, hd.ngayGioTra, hd.tinhTrangHD, hd.TongTien,
        kh.maKH, kh.hoTen, kh.soDienThoai
    FROM dbo.HoaDon hd, dbo.KhachHang kh
    WHERE hd.tinhTrangHD = 1
        AND hd.maKH = kh.maKH
        AND kh.soDienThoai LIKE @keyword
        AND hd.ngayGioDat BETWEEN @startDate AND @endDate
    GROUP BY hd.maHoaDon, hd.ngayGioDat, hd.ngayGioTra, hd.tinhTrangHD, hd.TongTien,
    kh.maKH, kh.hoTen, kh.soDienThoai
END
GO

CREATE PROC USP_getBillListByDateAndCustomerName
    @customerName NVARCHAR(100),
    @startDate DATE,
    @endDate DATE
AS
BEGIN
    DECLARE @keyword NVARCHAR(102) = N'%' + @customerName + N'%'
    SELECT hd.maHoaDon, hd.ngayGioDat, hd.ngayGioTra, hd.tinhTrangHD, hd.TongTien,
        kh.maKH, kh.hoTen, kh.soDienThoai
    FROM dbo.HoaDon hd, dbo.KhachHang kh
    WHERE hd.tinhTrangHD = 1
        AND hd.maKH = kh.maKH
        AND dbo.fuConvertToUnsign(kh.hoTen) LIKE dbo.fuConvertToUnsign(@keyword)
        AND hd.ngayGioDat BETWEEN @startDate AND @endDate
    GROUP BY hd.maHoaDon, hd.ngayGioDat, hd.ngayGioTra, hd.tinhTrangHD, hd.TongTien,
    kh.maKH, kh.hoTen, kh.soDienThoai
END
GO

CREATE PROC USP_getBillListByDateAndStaffName
    @staffName NVARCHAR(100),
    @startDate DATE,
    @endDate DATE
AS
BEGIN
    DECLARE @keyword NVARCHAR(102) = N'%' + @staffName + N'%'
    SELECT hd.maHoaDon, hd.ngayGioDat, hd.ngayGioTra, hd.tinhTrangHD, hd.TongTien,
        nv.maNhanVien, nv.hoTen, nv.soDienThoai
    FROM dbo.HoaDon hd, dbo.NhanVien nv
    WHERE hd.tinhTrangHD = 1
        AND hd.maNhanVien = nv.maNhanVien
        AND dbo.fuConvertToUnsign(nv.hoTen) LIKE dbo.fuConvertToUnsign(@keyword)
        AND hd.ngayGioDat BETWEEN @startDate AND @endDate
    GROUP BY hd.maHoaDon, hd.ngayGioDat, hd.ngayGioTra, hd.tinhTrangHD, hd.TongTien,
    nv.maNhanVien, nv.hoTen, nv.soDienThoai
END
GO


-- chi tiết dịch vụ
CREATE PROC USP_getServiceDetailListByRoomId
    @roomId VARCHAR(5)
AS
BEGIN
    SELECT ctdv.soLuongDat, ctdv.donGia,
        dv.maDichVu, dv.giaBan, dv.soLuongTon, dv.tenDichVu,
        ldv.maLDV, ldv.tenLDV,
        hd.maPhong
    FROM dbo.CTDichVu ctdv,
        dbo.HoaDon hd,
        dbo.DichVu dv,
        dbo.LoaiDichVu ldv
    WHERE ctdv.maHoaDon = hd.maHoaDon
        AND hd.maPhong = @roomId
        AND ctdv.maDichVu = dv.maDichVu
        AND dv.maLDV = ldv.maLDV
        AND hd.tinhTrangHD = 0
END
GO

CREATE PROC USP_getServiceDetailListByBillId
    @billId VARCHAR(15)
AS
BEGIN
    SELECT ctdv.soLuongDat, ctdv.maHoaDon, ctdv.tienDichVu, ctdv.donGia,
        dv.maDichVu, dv.giaBan, dv.soLuongTon, dv.tenDichVu,
        ldv.maLDV, ldv.tenLDV
    FROM dbo.CTDichVu ctdv,
        dbo.DichVu dv,
        dbo.LoaiDichVu ldv
    WHERE ctdv.maHoaDon = @billId
        AND ctdv.maDichVu = dv.maDichVu
        AND dv.maLDV = ldv.maLDV
END
GO
-- exec USP_getServiceDetailListByBillId 'HD2021100100001'
-- go

ALTER PROC USP_getServiceDetailByBillIdAndServiceId
    @billId VARCHAR(15),
    @serviceId VARCHAR(6)
AS
BEGIN
    SELECT ctdv.soLuongDat, ctdv.donGia,
        dv.maDichVu, dv.giaBan, dv.soLuongTon, dv.tenDichVu,
        ldv.maLDV, ldv.tenLDV,
        hd.maHoaDon, p.maLP
    FROM dbo.CTDichVu ctdv,
        dbo.HoaDon hd,
        dbo.DichVu dv,
        dbo.LoaiDichVu ldv,
        dbo.Phong p
    WHERE ctdv.maHoaDon = hd.maHoaDon
        AND ctdv.maDichVu = dv.maDichVu
        AND dv.maLDV = ldv.maLDV
        AND hd.maPhong = p.maPhong
        AND hd.maHoaDon = @billId
        AND dv.maDichVu = @serviceId
END
GO

-- @quantityOrder có thể là số âm
ALTER PROC USP_insertServiceDetail
    @serviceId VARCHAR(6),
    @billId VARCHAR(15),
    @quantityOrder INT,
    @price MONEY
AS
BEGIN
    DECLARE @isExitsCTDichVu VARCHAR(15)
    DECLARE @oldQuantity INT = 0
    DECLARE @totalPriceService MONEY = 0.0
    DECLARE @quantityInStock INT = 0

    SELECT @quantityInStock = dv.soLuongTon
    FROM dbo.DichVu dv
    WHERE dv.maDichVu = @serviceId

    SELECT @isExitsCTDichVu = ctdv.maHoaDon , @oldQuantity = ctdv.soLuongDat
    FROM dbo.CTDichVu ctdv, dbo.DichVu dv
    WHERE ctdv.maDichVu = dv.maDichVu
        AND ctdv.maHoaDon = @billId
        AND ctdv.maDichVu = @serviceId

    -- hóa đơn tồn tại -> cập nhật
    IF(@isExitsCTDichVu IS NOT NULL)
        BEGIN
        DECLARE @newQuantity INT = @quantityOrder + @oldQuantity
        IF(@newQuantity > 0)
        BEGIN
            SET @totalPriceService = @newQuantity * @price
            UPDATE dbo.CTDichVu
                SET soLuongDat = @newQuantity,
                    tienDichVu = @totalPriceService,
                    donGia = @price
                WHERE maHoaDon = @billId
                AND maDichVu = @serviceId
        END
        ELSE
            BEGIN
            DELETE FROM dbo.CTDichVu
                WHERE maHoaDon = @billId
                AND maDichVu = @serviceId
        END
        UPDATE dbo.DichVu
            SET soLuongTon = @quantityInStock - @quantityOrder
            WHERE maDichVu = @serviceId
    END
    -- hóa đơn không tồn tại -> tạo mới
    ELSE
        BEGIN
        SET @totalPriceService = @quantityOrder * @price
        INSERT INTO dbo.CTDichVu
            (maHoaDon, maDichVu, soLuongDat, donGia, tienDichVu)
        VALUES
            (@billId, @serviceId, @quantityOrder, @price, @totalPriceService)

        UPDATE dbo.DichVu
            SET soLuongTon = @quantityInStock - @quantityOrder
            WHERE maDichVu = @serviceId
    END
    PRINT 1
END
GO

-- select * 
-- from dbo.CTDichVu ctdv, dbo.DichVu dv
-- where maHoaDon = 'HD2021111300006'
--     AND dv.maDichVu = ctdv.maDichVu
-- go


-- khách hàng
CREATE PROC USP_getCustomerList
AS
BEGIN
    SELECT kh.cmnd AS cmndKH, kh.gioiTinh AS gioiTinhKH, kh.hoTen AS hoTenKH,
        kh.maKH, kh.ngaySinh AS ngaySinhKH, kh.soDienThoai AS sdtKH
    FROM dbo.KhachHang kh
END
GO

CREATE PROC USP_getCustomerListById
    @customerId VARCHAR(10)
AS
BEGIN
    DECLARE @keyword NVARCHAR(12) = N'%' + @customerId + N'%'
    SELECT kh.cmnd AS cmndKH, kh.gioiTinh AS gioiTinhKH, kh.hoTen AS hoTenKH,
        kh.maKH, kh.ngaySinh AS ngaySinhKH, kh.soDienThoai AS sdtKH
    FROM dbo.KhachHang kh
    WHERE kh.maKH LIKE @keyword
END
GO

CREATE PROC USP_getCustomerById
    @customerId VARCHAR(10)
AS
BEGIN
    SELECT kh.cmnd AS cmndKH, kh.gioiTinh AS gioiTinhKH, kh.hoTen AS hoTenKH,
        kh.maKH, kh.ngaySinh AS ngaySinhKH, kh.soDienThoai AS sdtKH
    FROM dbo.KhachHang kh
    WHERE kh.maKH = @customerId
END
GO

CREATE PROC USP_getCustomerListByName
    @customerName NVARCHAR(100)
AS
BEGIN
    DECLARE @keyword NVARCHAR(102) = N'%' + @customerName + N'%'
    SELECT kh.cmnd AS cmndKH, kh.gioiTinh AS gioiTinhKH, kh.hoTen AS hoTenKH,
        kh.maKH, kh.ngaySinh AS ngaySinhKH, kh.soDienThoai AS sdtKH
    FROM dbo.KhachHang kh
    WHERE dbo.fuConvertToUnsign(kh.hoTen) LIKE dbo.fuConvertToUnsign(@keyword)
END
GO

CREATE PROC USP_getCustomerListByGender
    @gender BIT
AS
BEGIN
    SELECT kh.cmnd AS cmndKH, kh.gioiTinh AS gioiTinhKH, kh.hoTen AS hoTenKH,
        kh.maKH, kh.ngaySinh AS ngaySinhKH, kh.soDienThoai AS sdtKH
    FROM dbo.KhachHang kh
    WHERE kh.gioiTinh = @gender
END
GO

CREATE PROC USP_getCustomerListByPhoneNumber
    @phoneNumber VARCHAR(10)
AS
BEGIN
    DECLARE @keyword NVARCHAR(12) = N'%' + @phoneNumber + N'%'
    SELECT kh.cmnd AS cmndKH, kh.gioiTinh AS gioiTinhKH, kh.hoTen AS hoTenKH,
        kh.maKH, kh.ngaySinh AS ngaySinhKH, kh.soDienThoai AS sdtKH
    FROM dbo.KhachHang kh
    WHERE kh.soDienThoai LIKE @keyword
END
GO

CREATE PROC USP_getCustomerListUnBooked
AS
BEGIN
    SELECT kh.cmnd AS cmndKH, kh.gioiTinh AS gioiTinhKH, kh.hoTen AS hoTenKH,
        kh.maKH, kh.ngaySinh AS ngaySinhKH, kh.soDienThoai AS sdtKH
    FROM dbo.KhachHang kh, dbo.HoaDon hd
    WHERE kh.maKH NOT IN (
        -- lấy danh sách mã khách hàng chưa thanh toán hóa đơn
        SELECT hd.maKH
    FROM dbo.HoaDon hd, dbo.KhachHang kh1
    WHERE hd.maKH = kh1.maKH
        AND hd.tinhTrangHD = 0
    )
    GROUP BY kh.maKH, kh.hoTen, kh.cmnd, kh.gioiTinh, 
    kh.ngaySinh, kh.soDienThoai
END
GO

CREATE PROC USP_getCustomerListUnBookedByCMND
    @cmnd VARCHAR(12)
AS
BEGIN
    DECLARE @keyword NVARCHAR(14) = N'%' + @cmnd + N'%'
    SELECT kh.cmnd AS cmndKH, kh.gioiTinh AS gioiTinhKH, kh.hoTen AS hoTenKH,
        kh.maKH, kh.ngaySinh AS ngaySinhKH, kh.soDienThoai AS sdtKH
    FROM dbo.KhachHang kh, dbo.HoaDon hd
    WHERE kh.maKH NOT IN (
        -- lấy danh sách mã khách hàng chưa thanh toán hóa đơn
        SELECT hd.maKH
        FROM dbo.HoaDon hd, dbo.KhachHang kh1
        WHERE hd.maKH = kh1.maKH
            AND hd.tinhTrangHD = 0
    )
        AND dbo.fuConvertToUnsign(kh.cmnd) LIKE dbo.fuConvertToUnsign(@keyword)
    GROUP BY kh.maKH, kh.hoTen, kh.cmnd, kh.gioiTinh, 
    kh.ngaySinh, kh.soDienThoai
END
GO

CREATE PROC USP_getCustomerListUnBookedByPhoneNumber
    @phoneNumber VARCHAR(10)
AS
BEGIN
    DECLARE @keyword NVARCHAR(12) = N'%' + @phoneNumber + N'%'
    SELECT kh.cmnd AS cmndKH, kh.gioiTinh AS gioiTinhKH, kh.hoTen AS hoTenKH,
        kh.maKH, kh.ngaySinh AS ngaySinhKH, kh.soDienThoai AS sdtKH
    FROM dbo.KhachHang kh, dbo.HoaDon hd
    WHERE kh.maKH NOT IN (
        -- lấy danh sách mã khách hàng chưa thanh toán hóa đơn
        SELECT hd.maKH
        FROM dbo.HoaDon hd, dbo.KhachHang kh1
        WHERE hd.maKH = kh1.maKH
            AND hd.tinhTrangHD = 0
    )
        AND dbo.fuConvertToUnsign(kh.soDienThoai) LIKE dbo.fuConvertToUnsign(@keyword)
    GROUP BY kh.maKH, kh.hoTen, kh.cmnd, kh.gioiTinh, 
    kh.ngaySinh, kh.soDienThoai
END
GO

CREATE PROC USP_insertCustomer
    @customerId VARCHAR(10),
    @cmnd VARCHAR(12),
    @customerName NVARCHAR(100),
    @gender BIT,
    @phoneNumber VARCHAR(10),
    @birthDay DATE
AS
BEGIN
    BEGIN TRANSACTION
    INSERT INTO dbo.KhachHang
        (maKH, cmnd, hoTen, gioiTinh, soDienThoai, ngaySinh)
    VALUES
        ( @customerId , @cmnd , @customerName , @gender , @phoneNumber , @birthDay )

    DECLARE @isExitsId VARCHAR(10)
    SELECT @isExitsId = kh.maKH
    FROM dbo.KhachHang kh
    WHERE kh.maKH = @customerId

    IF @isExitsId IS NULL
    BEGIN
        PRINT 0
        ROLLBACK
    END
    ELSE
    BEGIN
        PRINT 1
        COMMIT
    END
END
GO

CREATE PROC USP_getLastCustomerId
AS
BEGIN
    SELECT TOP 1
        kh.maKH
    FROM dbo.KhachHang kh
    ORDER BY kh.maKH DESC
END
GO

CREATE PROC USP_updateCustomerInfo
    @customerId VARCHAR(10),
    @cmnd VARCHAR(12),
    @customerName NVARCHAR(100),
    @gender BIT,
    @phoneNumber VARCHAR(10),
    @birthDay DATE
AS
BEGIN
    BEGIN TRANSACTION
    UPDATE dbo.KhachHang 
    SET cmnd = @cmnd, hoTen = @customerName, gioiTinh =  @gender, 
    soDienThoai = @phoneNumber, ngaySinh = @birthDay
    WHERE maKH = @customerId

    DECLARE @isExitsId VARCHAR(10)
    SELECT @isExitsId = kh.maKH
    FROM dbo.KhachHang kh
    WHERE kh.maKH = @customerId
        AND kh.cmnd = @cmnd
        AND kh.hoTen = @customerName
        AND kh.gioiTinh = @gender
        AND kh.soDienThoai = @phoneNumber
        AND kh.ngaySinh = @birthDay

    IF @isExitsId IS NULL
    BEGIN
        PRINT 0
        ROLLBACK
    END
    ELSE
    BEGIN
        PRINT 1
        COMMIT
    END
END
GO

CREATE PROC USP_getCustomerByBillId
    @billId VARCHAR(15)
AS
BEGIN
    SELECT kh.cmnd AS cmndKH, kh.gioiTinh AS gioiTinhKH, kh.hoTen AS hoTenKH,
        kh.maKH, kh.ngaySinh AS ngaySinhKH, kh.soDienThoai AS sdtKH
    FROM dbo.KhachHang kh, dbo.HoaDon hd
    WHERE hd.maHoaDon = @billId
        AND hd.maKH = kh.maKH
END
GO


-- phòng
CREATE PROC USP_getRoomList
AS
BEGIN
    SELECT p.maPhong, p.tinhTrangP, p.viTri,
        lp.maLP, lp.giaTien, lp.sucChua, lp.tenLP
    FROM dbo.Phong p, dbo.LoaiPhong lp
    WHERE p.maLP = lp.maLP
END
GO

CREATE PROC USP_getRoomByRoomId
    @roomId VARCHAR(5)
AS
BEGIN
    SELECT p.maPhong, p.tinhTrangP, p.viTri,
        lp.maLP, lp.giaTien, lp.sucChua, lp.tenLP
    FROM dbo.Phong p, dbo.LoaiPhong lp
    WHERE p.maLP = lp.maLP
        AND p.maPhong = @roomId
END
GO

CREATE PROC USP_getRoomByBillId
    @billId VARCHAR(15)
AS
BEGIN
    SELECT p.maPhong, p.tinhTrangP, p.viTri,
        lp.maLP, lp.giaTien, lp.sucChua, lp.tenLP
    FROM dbo.Phong p, dbo.LoaiPhong lp, dbo.HoaDon hd
    WHERE hd.maHoaDon = @billId
        AND hd.maPhong = p.maPhong
        AND p.maLP = lp.maLP
END
GO

CREATE PROC USP_getRoomListByRoomTypeName
    @roomTypeName NVARCHAR(100)
AS
BEGIN
    SELECT p.maPhong, p.tinhTrangP, p.viTri,
        lp.maLP, lp.giaTien, lp.sucChua, lp.tenLP
    FROM dbo.Phong p, dbo.LoaiPhong lp
    WHERE p.maLP = lp.maLP
        AND lp.tenLP = @roomTypeName
END
GO

CREATE PROC USP_getListAvailableRoom
AS
BEGIN
    SELECT p.maPhong, p.tinhTrangP, p.viTri,
        lp.maLP, lp.giaTien, lp.sucChua, lp.tenLP
    FROM dbo.Phong p, dbo.LoaiPhong lp
    WHERE p.maLP = lp.maLP
        AND p.tinhTrangP = 0
END
GO

CREATE PROC USP_getListAvailableRoomByRoomTypeName
    @roomTypeName NVARCHAR(100)
AS
BEGIN
    SELECT p.maPhong, p.tinhTrangP, p.viTri,
        lp.maLP, lp.giaTien, lp.sucChua, lp.tenLP
    FROM dbo.Phong p, dbo.LoaiPhong lp
    WHERE p.maLP = lp.maLP
        AND lp.tenLP = @roomTypeName
        AND p.tinhTrangP = 0
END
GO

CREATE PROC USP_updateRoomStatus
    @status INT,
    @roomId VARCHAR(5)
AS
BEGIN
    BEGIN TRANSACTION
    UPDATE dbo.Phong 
    SET tinhTrangP = @status 
    WHERE maPhong = @roomId

    DECLARE @isExitsId VARCHAR(5)
    SELECT @isExitsId = p.maPhong
    FROM dbo.Phong p
    WHERE p.maPhong = @roomId
        AND p.tinhTrangP = @status

    IF @isExitsId IS NULL
    BEGIN
        PRINT 0
        ROLLBACK
    END
    ELSE
    BEGIN
        PRINT 1
        COMMIT
    END
END
GO

CREATE PROC USP_switchRoom
    @billId VARCHAR(15),
    @oldRoomId VARCHAR(5),
    @newRoomId VARCHAR(5)
AS
BEGIN
    DECLARE @isExitsOldRoomId VARCHAR(6)
    DECLARE @isExitsNewRoomId VARCHAR(6)
    BEGIN TRANSACTION
    EXEC USP_updateRoomStatus 0, @oldRoomId
    EXEC USP_updateRoomStatus 1, @newRoomId


    SELECT @isExitsOldRoomId = p.maPhong
    FROM dbo.Phong p
    where p.maPhong = @oldRoomId
        AND p.tinhTrangP = 0

    SELECT @isExitsNewRoomId = p.maPhong
    FROM dbo.Phong p
    where p.maPhong = @newRoomId
        AND p.tinhTrangP = 1

    UPDATE dbo.HoaDon
    SET maPhong = @newRoomId
    WHERE maPhong = @oldRoomId
        AND maHoaDon = @billId
        AND tinhTrangHD = 0

    DECLARE @isExitsBillId VARCHAR(15)
    SELECT @isExitsBillId = hd.maHoaDon
    FROM dbo.HoaDon hd
    WHERE hd.maPhong = @newRoomId
        AND hd.maHoaDon = @billId

    IF @isExitsBillId IS NULL 
        OR @isExitsOldRoomId IS NULL 
        OR @isExitsNewRoomId IS NULL
    BEGIN
        PRINT 0
        ROLLBACK
    END
    ELSE
    BEGIN
        PRINT 1
        COMMIT
    END
END
GO

CREATE PROC USP_getRoomListByLocation
    @location NVARCHAR(100)
AS
BEGIN
    DECLARE @keyword NVARCHAR(102) = N'%' + @location + N'%'
    SELECT p.maPhong, p.tinhTrangP, p.viTri,
        lp.maLP, lp.giaTien, lp.sucChua, lp.tenLP
    FROM dbo.Phong p, dbo.LoaiPhong lp
    WHERE p.maLP = lp.maLP
        AND dbo.fuConvertToUnsign(p.viTri) LIKE dbo.fuConvertToUnsign(@keyword)
END
GO

--  0 là là còn trống | 1 là đang sử dụng
CREATE PROC USP_getRoomListByStatus
    @roomStatus INT
AS
BEGIN
    SELECT p.maPhong, p.tinhTrangP, p.viTri,
        lp.maLP, lp.giaTien, lp.sucChua, lp.tenLP
    FROM dbo.Phong p, dbo.LoaiPhong lp
    WHERE p.maLP = lp.maLP
        AND p.tinhTrangP = @roomStatus
END
GO

CREATE PROC USP_updateInfoRoom
    @roomId VARCHAR(5),
    @roomStatus INT,
    @location NVARCHAR(100),
    @roomTypeId VARCHAR(5)
AS
BEGIN
    BEGIN TRANSACTION
    UPDATE dbo.Phong
        SET tinhTrangP = @roomStatus,
            viTri = @location,
            maLP = @roomTypeId
        WHERE maPhong = @roomId

    DECLARE @isExitsId VARCHAR(5)
    SELECT @isExitsId = p.maPhong
    FROM dbo.Phong p
    WHERE p.maPhong = @roomId
        AND p.tinhTrangP = @roomStatus
        AND p.viTri = @location
        AND p.maLP = @roomTypeId

    IF @isExitsId IS NULL
    BEGIN
        PRINT 0
        ROLLBACK
    END
    ELSE
    BEGIN
        PRINT 1
        COMMIT
    END
END
GO

CREATE PROC USP_insertRoom
    @roomId VARCHAR(5),
    @roomStatus INT,
    @location NVARCHAR(100),
    @roomTypeId VARCHAR(5)
AS
BEGIN
    DECLARE @isExitsId VARCHAR(5)
    BEGIN TRANSACTION
    INSERT INTO dbo.Phong
        (maPhong, tinhTrangP, viTri, maLP)
    VALUES
        (@roomId, @roomStatus, @location, @roomTypeId)

    SELECT @isExitsId = p.maLP
    FROM dbo.Phong p
    WHERE p.maPhong = @roomId

    IF @isExitsId IS NULL
    BEGIN
        ROLLBACK
        PRINT 0
    END
    ELSE 
    BEGIN
        COMMIT
        PRINT 1
    END
END
GO

CREATE PROC USP_getLastRoomId
AS
BEGIN
    SELECT TOP 1
        p.maPhong
    FROM dbo.Phong p
    ORDER BY p.maPhong DESC
END
GO


-- loại phòng
CREATE PROC USP_getRoomTypeNameById
    @roomId VARCHAR(5)
AS
BEGIN
    SELECT lp.tenLP
    FROM dbo.Phong p, dbo.LoaiPhong lp
    WHERE p.maLP = lp.maLP
        AND p.maPhong = @roomId
END
GO

CREATE PROC USP_getRoomTypeList
AS
BEGIN
    SELECT lp.tenLP, lp.giaTien, lp.maLP, lp.sucChua
    FROM dbo.LoaiPhong lp
END
GO

CREATE PROC USP_getRoomTypeListByName
    @roomTypeName NVARCHAR(100)
AS
BEGIN
    DECLARE @name NVARCHAR(102) = N'%'+ @roomTypeName + N'%'
    SELECT lp.tenLP, lp.giaTien, lp.maLP, lp.sucChua
    FROM dbo.LoaiPhong lp
    WHERE dbo.fuConvertToUnsign(lp.tenLP) LIKE dbo.fuConvertToUnsign(@name)
END
GO

CREATE PROC USP_getRoomTypeListByPrice
    @price NVARCHAR(100)
AS
BEGIN
    SELECT lp.tenLP, lp.giaTien, lp.maLP, lp.sucChua
    FROM dbo.LoaiPhong lp
    WHERE CONVERT(NVARCHAR, lp.giaTien) LIKE N'%' + @price + N'%'
END
GO

CREATE PROC USP_getLastRoomTypeId
AS
BEGIN
    SELECT TOP 1
        lp.maLP
    FROM dbo.LoaiPhong lp
    ORDER BY lp.maLP DESC
END
GO

CREATE PROC USP_getRoomTypeById
    @roomTypeId VARCHAR(5)
AS
BEGIN
    SELECT lp.maLP, lp.giaTien, lp.sucChua, lp.tenLP
    FROM dbo.LoaiPhong lp
    WHERE lp.maLP = @roomTypeId
END
GO

CREATE PROC USP_getRoomTypeByName
    @roomTypeName NVARCHAR(100)
AS
BEGIN
    SELECT lp.maLP, lp.giaTien, lp.sucChua, lp.tenLP
    FROM dbo.LoaiPhong lp
    WHERE lp.tenLP = @roomTypeName
END
GO

CREATE PROC USP_insertRoomType
    @roomTypeId VARCHAR(5),
    @roomTypeName NVARCHAR(100),
    @capacity INT,
    @price MONEY
AS
BEGIN
    DECLARE @isExitsId VARCHAR(5)
    BEGIN TRANSACTION
    INSERT INTO dbo.LoaiPhong
        (maLP, tenLP, giaTien, sucChua)
    VALUES
        (@roomTypeId, @roomTypeName, @price, @capacity)

    SELECT @isExitsId = lp.maLP
    FROM dbo.LoaiPhong lp
    WHERE lp.maLP = @roomTypeId

    IF @isExitsId IS NULL
    BEGIN
        PRINT 0
        ROLLBACK
    END
    ELSE 
    BEGIN
        PRINT 1
        COMMIT
    END
END
GO

CREATE PROC USP_updateInfoRoomType
    @roomTypeId VARCHAR(5),
    @roomTypeName NVARCHAR(100),
    @capacity INT,
    @price MONEY
AS
BEGIN
    BEGIN TRANSACTION
    UPDATE dbo.LoaiPhong
        SET tenLP = @roomTypeName,
            giaTien = @price,
            sucChua = @capacity
        WHERE maLP = @roomTypeId

    DECLARE @isExitsId VARCHAR(6)
    SELECT @isExitsId = lp.maLP
    FROM dbo.LoaiPhong lp
    WHERE lp.maLP = @roomTypeId
        AND lp.tenLP = @roomTypeName
        AND lp.sucChua = @capacity
        AND lp.giaTien = @price

    IF @isExitsId IS NULL
    BEGIN
        PRINT 0
        ROLLBACK
    END
    ELSE
    BEGIN
        PRINT 1
        COMMIT
    END
END
GO


-- nhân viên
CREATE PROC USP_getStaffByUsername
    @tenDangNhap VARCHAR(100)
AS
BEGIN
    SELECT nv.maNhanVien, nv.cmnd AS cmndNV, nv.hoTen AS hoTenNV, nv.ngaySinh AS ngaySinhNV,
        nv.soDienThoai AS sdtNV, nv.chucVu, nv.mucLuong, nv.gioiTinh AS gioiTinhNV, nv.trangThaiNV,
        tk.tenDangNhap, tk.matKhau, tk.tinhTrangTK
    FROM dbo.TaiKhoan tk, dbo.NhanVien nv
    WHERE tk.tenDangNhap = nv.taiKhoan
        AND tk.tenDangNhap = @tenDangNhap
END
GO

CREATE PROC USP_getStaffByStaffID
    @staffId VARCHAR(12)
AS
BEGIN
    SELECT nv.chucVu, nv.cmnd AS cmndNV, nv.gioiTinh AS gioiTinhNV,
        nv.hoTen AS hoTenNV, nv.maNhanVien, nv.mucLuong, nv.ngaySinh AS ngaySinhNV,
        nv.soDienThoai AS sdtNV, nv.trangThaiNV,
        tk.tenDangNhap, tk.matKhau, tk.tinhTrangTK
    FROM dbo.TaiKhoan tk, dbo.NhanVien nv
    WHERE tk.tenDangNhap = nv.taiKhoan
        AND nv.maNhanVien = @staffId
END
GO

CREATE PROC USP_getStaffByBillId
    @billId VARCHAR(15)
AS
BEGIN
    SELECT nv.chucVu, nv.cmnd AS cmndNV, nv.gioiTinh AS gioiTinhNV,
        nv.hoTen AS hoTenNV, nv.maNhanVien, nv.mucLuong, nv.ngaySinh AS ngaySinhNV,
        nv.soDienThoai AS sdtNV, nv.trangThaiNV,
        tk.tenDangNhap, tk.matKhau, tk.tinhTrangTK
    FROM dbo.TaiKhoan tk, dbo.NhanVien nv, dbo.HoaDon hd
    WHERE tk.tenDangNhap = nv.taiKhoan
        AND nv.maNhanVien = hd.maNhanVien
        AND hd.maHoaDon = @billId
END
GO

CREATE PROC USP_getStaffList
AS
BEGIN
    SELECT nv.chucVu, nv.cmnd AS cmndNV, nv.gioiTinh AS gioiTinhNV,
        nv.hoTen AS hoTenNV, nv.maNhanVien, nv.mucLuong, nv.ngaySinh AS ngaySinhNV,
        nv.soDienThoai AS sdtNV, nv.trangThaiNV,
        tk.tinhTrangTK, tk.tenDangNhap, tk.matKhau
    FROM dbo.NhanVien nv, dbo.TaiKhoan tk
    WHERE nv.taiKhoan = tk.tenDangNhap
END
GO

CREATE PROC USP_getStaffListByWorkingStatus
    @workingStatus NVARCHAR(100)
AS
BEGIN
    SELECT nv.chucVu, nv.cmnd AS cmndNV, nv.gioiTinh AS gioiTinhNV,
        nv.hoTen AS hoTenNV, nv.maNhanVien, nv.mucLuong, nv.ngaySinh AS ngaySinhNV,
        nv.soDienThoai AS sdtNV, nv.trangThaiNV,
        tk.tinhTrangTK, tk.tenDangNhap, tk.matKhau
    FROM dbo.NhanVien nv, dbo.TaiKhoan tk
    WHERE nv.taiKhoan = tk.tenDangNhap
        AND nv.trangThaiNV = @workingStatus
END
GO

CREATE PROC USP_getLastStaffID
AS
BEGIN
    SELECT TOP 1
        nv.maNhanVien
    FROM dbo.NhanVien nv
    ORDER BY nv.maNhanVien DESC
END
GO

CREATE PROC USP_insertStaff
    @staffID VARCHAR(12),
    @cmnd VARCHAR(12),
    @fullName NVARCHAR(100),
    @birthDay DATE,
    @phoneNumber VARCHAR(10),
    @position NVARCHAR(100),
    @salary MONEY,
    @status NVARCHAR(100),
    @gender INT,
    @username VARCHAR(100)
AS
BEGIN
    DECLARE @maNV VARCHAR(12)
    BEGIN TRANSACTION
    INSERT INTO dbo.TaiKhoan
        (tenDangNhap, matKhau, tinhTrangTK)
    VALUES
        (@username, '123456', 1)

    DECLARE @isExitsUsername VARCHAR(100)
    SELECT @isExitsUsername = tk.tenDangNhap
    FROM dbo.TaiKhoan tk
    WHERE tk.tenDangNhap = @username
        AND tk.matKhau = '123456'

    IF @isExitsUsername IS NULL
    BEGIN
        PRINT 0
        ROLLBACK
    END
    ELSE
    BEGIN
        INSERT INTO dbo.NhanVien
            (maNhanVien, cmnd, hoTen, ngaySinh, soDienThoai,
            chucVu, mucLuong, trangThaiNV, gioiTinh, taiKhoan )
        VALUES
            (@staffID, @cmnd, @fullName, @birthDay, @phoneNumber,
                @position, @salary, @status, @gender, @username)

        DECLARE @isExitsStaffId VARCHAR(100)
        SELECT @isExitsStaffId = tk.maNhanVien
        FROM NhanVien tk
        WHERE tk.maNhanVien = @staffID

        IF @isExitsStaffId IS NULL
        BEGIN
            ROLLBACK
            PRINT 0
        END
        ELSE 
        BEGIN
            COMMIT
            PRINT 1
        END
    END
END
GO

CREATE PROC USP_updateInfoStaff
    @staffID VARCHAR(12),
    @cmnd VARCHAR(12),
    @fullName NVARCHAR(100),
    @birthDay DATE,
    @phoneNumber VARCHAR(10),
    @position NVARCHAR(100),
    @salary MONEY,
    @status NVARCHAR(100),
    @gender INT
AS
BEGIN
    BEGIN TRANSACTION
    UPDATE dbo.NhanVien
        SET cmnd = @cmnd, hoTen = @fullName, ngaySinh = @birthDay, 
        soDienThoai = @phoneNumber, chucVu = @position, mucLuong = @salary,
        trangThaiNV = @status, gioiTinh = @gender
        WHERE maNhanVien = @staffID

    DECLARE @isExitsId VARCHAR(6)
    SELECT @isExitsId = nv.maNhanVien
    FROM dbo.NhanVien nv
    WHERE cmnd = @cmnd
        AND hoTen = @fullName
        AND ngaySinh = @birthDay
        AND soDienThoai = @phoneNumber
        AND chucVu = @position
        AND mucLuong = @salary
        AND trangThaiNV = @status
        AND gioiTinh = @gender
        AND maNhanVien = @staffID

    IF @isExitsId IS NULL
    BEGIN
        PRINT 0
        ROLLBACK
    END
    ELSE
    BEGIN
        PRINT 1
        COMMIT
    END
END
GO

CREATE PROC USP_updateInfoStaffAndAccount
    @staffID VARCHAR(12),
    @cmnd VARCHAR(12),
    @fullName NVARCHAR(100),
    @birthDay DATE,
    @phoneNumber VARCHAR(10),
    @position NVARCHAR(100),
    @salary MONEY,
    @status NVARCHAR(100),
    @gender INT,
    @username VARCHAR(100),
    @password NVARCHAR(100)
AS
BEGIN
    BEGIN TRANSACTION
    DECLARE @isExitsUsername VARCHAR(100)

    UPDATE dbo.TaiKhoan 
    SET matKhau = @password
    WHERE tenDangNhap = @username

    SELECT @isExitsUsername = tk.tenDangNhap
    FROM dbo.TaiKhoan tk
    WHERE tk.tenDangNhap = @username
        AND tk.matKhau = @password

    IF @isExitsUsername IS NULL
    BEGIN
        PRINT 0
        ROLLBACK
    END
    ELSE
    BEGIN
        UPDATE dbo.NhanVien
            SET cmnd = @cmnd, hoTen = @fullName, ngaySinh = @birthDay, 
            soDienThoai = @phoneNumber, chucVu = @position, mucLuong = @salary,
            trangThaiNV = @status, gioiTinh = @gender
            WHERE maNhanVien = @staffID

        DECLARE @isExitsStaffId VARCHAR(6)
        SELECT @isExitsStaffId = nv.maNhanVien
        FROM dbo.NhanVien nv
        WHERE cmnd = @cmnd
            AND hoTen = @fullName
            AND ngaySinh = @birthDay
            AND soDienThoai = @phoneNumber
            AND chucVu = @position
            AND mucLuong = @salary
            AND trangThaiNV = @status
            AND gioiTinh = @gender
            AND maNhanVien = @staffID

        IF @isExitsStaffId IS NULL
        BEGIN
            PRINT 0
            ROLLBACK
        END
        ELSE
        BEGIN
            PRINT 1
            COMMIT
        END
    END
END
GO

CREATE PROC USP_getStaffListByPosition
    @position NVARCHAR(100)
AS
BEGIN
    SELECT nv.chucVu, nv.cmnd AS cmndNV, nv.gioiTinh AS gioiTinhNV,
        nv.hoTen AS hoTenNV, nv.maNhanVien, nv.mucLuong, nv.ngaySinh AS ngaySinhNV,
        nv.soDienThoai AS sdtNV, nv.trangThaiNV,
        tk.tenDangNhap, tk.matKhau, tk.tinhTrangTK
    FROM dbo.NhanVien nv, dbo.TaiKhoan tk
    WHERE nv.taiKhoan = tk.tenDangNhap
        AND nv.chucVu = @position
END
GO

CREATE PROC USP_getStaffListByStaffName
    @fullName NVARCHAR(100)
AS
BEGIN
    DECLARE @name NVARCHAR(102) = N'%'+ @fullName + N'%'
    SELECT nv.chucVu, nv.cmnd AS cmndNV, nv.gioiTinh AS gioiTinhNV,
        nv.hoTen AS hoTenNV, nv.maNhanVien, nv.mucLuong, nv.ngaySinh AS ngaySinhNV,
        nv.soDienThoai AS sdtNV, nv.trangThaiNV,
        tk.tenDangNhap, tk.matKhau, tk.tinhTrangTK
    FROM dbo.NhanVien nv, dbo.TaiKhoan tk
    WHERE nv.taiKhoan = tk.tenDangNhap
        AND dbo.fuConvertToUnsign(nv.hoTen) LIKE dbo.fuConvertToUnsign(@name)
END
GO

CREATE PROC USP_getStaffListByPhoneNumber
    @phoneNumber VARCHAR(10)
AS
BEGIN
    DECLARE @rePhoneNumber VARCHAR(12) = '%'+ @phoneNumber + '%'
    SELECT nv.chucVu, nv.cmnd AS cmndNV, nv.gioiTinh AS gioiTinhNV,
        nv.hoTen AS hoTenNV, nv.maNhanVien, nv.mucLuong, nv.ngaySinh AS ngaySinhNV,
        nv.soDienThoai AS sdtNV, nv.trangThaiNV,
        tk.tenDangNhap, tk.matKhau, tk.tinhTrangTK
    FROM dbo.NhanVien nv, dbo.TaiKhoan tk
    WHERE nv.taiKhoan = tk.tenDangNhap
        AND nv.soDienThoai LIKE @rePhoneNumber
END
GO

CREATE PROC USP_getStaffNameById
    @staffID VARCHAR(10)
AS
BEGIN
    SELECT nv.hoTen
    FROM dbo.NhanVien nv
    WHERE nv.maNhanVien = @staffID
END
GO