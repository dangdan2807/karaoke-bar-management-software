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
    -- 1. kích hoạt || 0. vô hiệu hóa
    tinhTrangTK BIT NOT NULL DEFAULT(1)
)
GO

CREATE TABLE NhanVien
(
    maNhanVien VARCHAR(10) NOT NULL PRIMARY KEY,
    cmnd VARCHAR(12) NOT NULL,
    hoTen NVARCHAR(100) NOT NULL DEFAULT(N''),
    ngaySinh DATE,
    soDienThoai VARCHAR(10),
    chucVu NVARCHAR(100) NOT NULL,
    mucLuong FLOAT NOT NULL,
    trangThaiNV NVARCHAR(100) NULL,
    -- 0. nam | 1. nữ
    gioiTinh BIT NOT NULL,

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
    maDichVu VARCHAR(5) NOT NULL PRIMARY KEY,
    tenDichVu NVARCHAR(100) NOT NULL DEFAULT(N''),
    giaBan FLOAT NOT NULL DEFAULT(0) CHECK(giaBan >= 0),
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
    giaTien FLOAT NOT NULL DEFAULT(0) CHECK(giaTien >= 0),
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
    maHoaDon INT IDENTITY(1, 1) PRIMARY KEY,
    ngayGioDat DATETIME NOT NULL DEFAULT(getdate()),
    ngayGioTra DATETIME NULL ,
    -- 0. chưa thanh toán | 1. đã thanh toán
    tinhTrangHD INT NOT NULL DEFAULT(0),
    TongTien FLOAT NOT NULL DEFAULT(0) CHECK(TongTien >= 0),
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
    maDichVu VARCHAR(5) NOT NULL,
    maHoaDon INT NOT NULL,
    soLuongDat INT NOT NULL DEFAULT(1) CHECK(soLuongDat > 0),
    tienDichVu FLOAT NOT NULL DEFAULT(0) CHECK(tienDichVu >= 0),
    PRIMARY KEY (maDichVu, maHoaDon),
    FOREIGN KEY (maDichVU) REFERENCES dbo.DichVu (maDichVu),
    FOREIGN KEY (maHoaDon) REFERENCES dbo.HoaDon (maHoaDon)
)
GO