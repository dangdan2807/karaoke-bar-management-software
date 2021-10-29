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
    maDichVu VARCHAR(5) NOT NULL PRIMARY KEY,
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
    --  0 là là còn trống | 1 là đã cho thuê
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
    maDichVu VARCHAR(5) NOT NULL,
    maHoaDon INT NOT NULL,
    soLuongDat INT NOT NULL DEFAULT(1) CHECK(soLuongDat > 0),
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
    ('LDV001', N'Thức ăn'),
    ('LDV002', N'Đồ uống')
    -- ('LDV003', N'Sinh tố'),
    -- ('LDV004', N'Nước đóng chai'),
    -- ('LDV005', N'Gỏi'),
    -- ('LDV006', N'Soup'),
    -- ('LDV007', N'Salad'),
    -- ('LDV008', N'Hải sản'),
    -- ('LDV009', N'Lẫu'),
    -- ('LDV010', N'Hải sản'),
    -- ('LDV011', N'Mì'),
    -- ('LDV012', N'Thịt heo, bò, gà')
GO

INSERT INTO dbo.DichVu
    (maDichVu, tenDichVu, giaBan, soLuongTon, maLDV)
VALUES
    ('DV001', N'Thạch Dừa Nha Đam', 30000, 10, 'LDV001'),
    ('DV002', N'Thạch Dừa Dâu', 35000, 50, 'LDV001'),
    ('DV003', N'Thạch Dừa Bạc Hà', 35000, 30, 'LDV001'),
    ('DV004', N'Thạch Dừa Cam tươi', 42000, 20, 'LDV001'),
    ('DV005', N'Thạch Dừa Xoài', 30000, 15, 'LDV001'),
    ('DV006', N'Thạch Dừa Yaourt', 42000, 10, 'LDV001'),

    ('DV007', N'Iron man', 32000, 26, 'LDV001'),
    ('DV008', N'Moscow Beer', 32000, 84, 'LDV001'),
    ('DV009', N'Long Island iced Tea', 32000, 2, 'LDV001'),

    ('DV010', N'Heineken (Pháp)', 50000, 84, 'LDV001'),
    ('DV011', N'Heineken (Lon)', 30000, 85, 'LDV001'),
    ('DV012', N'Heineken (Chai)', 27000, 72, 'LDV001'),
    ('DV013', N'Spy', 65000, 83, 'LDV001'),
    ('DV014', N'Budweiser (Lon/Chai)', 33000, 62, 'LDV001'),
    ('DV015', N'Tiger (Chai)', 24000, 82, 'LDV001'),
    ('DV016', N'Tiger Crystal (Chai)', 27000, 75, 'LDV001'),

    ('DV017', N'Sinh tố Yaourt Đá', 35000, 72, 'LDV001'),
    ('DV018', N'Sinh tố Yaourt Lô Hội Cam', 60000, 63, 'LDV001'),

    ('DV019', N'SoDa', 30000, 28, 'LDV001'),
    ('DV020', N'SoDa Xí Muội', 45000, 100, 'LDV001'),
    ('DV021', N'SoDa Chanh Đường', 45000, 100, 'LDV001'),
    ('DV022', N'Pepsi', 30000, 100, 'LDV001'),
    ('DV023', N'7 Up', 30000, 83, 'LDV001'),
    ('DV024', N'Mirinda(Sarsi)', 30000, 83, 'LDV001'),
    ('DV025', N'Sting', 30000, 83, 'LDV001'),
    ('DV026', N'Red Bull', 25000, 83, 'LDV001'),
    ('DV027', N'Red Bull', 25000, 83, 'LDV001'),

    ('DV028', N'Gỏi Bưởi Hải Sản', 130000, 46, 'LDV002'),
    ('DV029', N'Gỏi Miến Thái Lan', 130000, 35, 'LDV002'),
    ('DV030', N'Gỏi Sứa Tôm Thịt', 130000, 28, 'LDV002'),

    ('DV031', N'Soup Bắp Cua Gà Xé', 40000, 28, 'LDV002'),
    ('DV032', N'Soup Hải Sản Thái Lan', 40000, 92, 'LDV002'),

    ('DV033', N'Salad Thập cẩm', 110000, 18, 'LDV002'),
    ('DV034', N'Salad Cá Ngừ', 130000, 29, 'LDV002'),
    ('DV035', N'Salad Trộn Thịt Bò', 130000, 83, 'LDV002'),

    ('DV036', N'Cà Ri Tôm', 260000, 28, 'LDV002'),
    ('DV037', N'Tôm Rang Me', 260000, 28, 'LDV002'),
    ('DV038', N'Tôm Sú Hấp Bia / Nước Dừa', 260000, 88, 'LDV002'),

    ('DV039', N'Cua Hấp Bia / Nước Dừa', 300000, 83, 'LDV002'),
    ('DV040', N'Cua Lột Chiên Giòn', 250000, 15, 'LDV002'),
    ('DV041', N'Cua Rang Me', 310000, 18, 'LDV002'),

    ('DV042', N'Bò Cuộn Phô Mai', 160000, 18, 'LDV002'),
    ('DV043', N'Bò Lúc Lắc', 190000, 93, 'LDV002'),

    ('DV044', N'Heo Rừng Nướng Muối Ớt', 140000, 26, 'LDV002'),

    ('DV045', N'Lẫu Cá Diêu Hồng', 275000, 26, 'LDV002'),
    ('DV046', N'Lẫu Hải Sản', 330000, 26, 'LDV002'),
    ('DV047', N'Lẫu Thái Lan', 330000, 73, 'LDV002'),

    ('DV048', N'Cánh Gà Chiên Nước Mắm', 120000, 24, 'LDV002'),
    ('DV049', N'Gà Hấp Thái Lan', 350000, 47, 'LDV002'),
    ('DV050', N'Gà Quay Tứ Xuyên', 350000, 23, 'LDV002'),

    ('DV051', N'Mì Xào Bò', 150000, 29, 'LDV001'),
    ('DV052', N'Hũ Tiếu Xào Bò', 150000, 38, 'LDV001'),
    ('DV053', N'Miến Xào Tôm', 150000, 34, 'LDV001'),

    ('DV054', N'Mực Nướng Muối Ớt', 160000, 34, 'LDV002'),
    ('DV055', N'Khô Mực Nướng', 130000, 47, 'LDV002'),
    ('DV056', N'Mực Xào Sa Tế', 160000, 8, 'LDV002'),
    ('DV057', N'Mực Chiên Xù', 160000, 6, 'LDV002'),
    ('DV058', N'Khô Chiên Giòn', 160000, 76, 'LDV002'),

    ('DV059', N'Cháo Hải Sản', 250000, 65, 'LDV002'),
    ('DV060', N'Cháo Mực', 250000, 89, 'LDV002'),
    ('DV061', N'Cháo Tôm Tươi', 200000, 67, 'LDV002'),
    ('DV062', N'Cháo Cá', 220000, 8, 'LDV002')
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
    ('KH00000001', '200000001', N'Nguyễn Việt Nam', 0, '0300000001', '1987-09-15'),
    ('KH00000002', '200000002', N'Nguyễn Huỳnh Dũng', 0, '0300000002', '1989-07-24'),
    ('KH00000003', '200000003', N'Ngô Quốc Cường', 0, '0300000003', '1999-07-08'),
    ('KH00000004', '200000004', N'Nguyễn Thị Hảo', 1, '0300000004', '1978-06-20'),
    ('KH00000005', '200000005', N'Nguyễn Thị Hậu', 1, '0300000005', '1968-01-09'),
    ('KH00000006', '200000006', N'Nguyễn Vũ Lâm Đức', 0, '0300000006', '1993-09-06'),
    ('KH00000007', '200000007', N'Phan Thị Thủy', 1, '0300000007', '1987-06-10'),
    ('KH00000008', '200000008', N'Phạm Văn Duy', 0, '0300000008', '1992-12-05'),
    ('KH00000009', '200000009', N'Hoàng Tiến Hào', 0, '0300000009', '1998-03-02'),
    ('KH00000010', '200000010', N'Dương Ngọc Vy', 0, '0300000010', '1993-06-14'),
    ('KH00000011', '200000011', N'Nguyễn Chí Hoàng', 0, '0300000011', '1984-08-05'),
    ('KH00000012', '200000012', N'Đỗ Huy Hữu', 0, '0300000012', '1985-09-18'),
    ('KH00000013', '200000013', N'Nguyễn Anh Khoa', 1, '0300000013', '1995-08-02'),
    ('KH00000014', '200000014', N'Trần Anh Lâm', 1, '0300000014', '1973-08-22'),
    ('KH00000015', '200000015', N'Nguyễn Thái Huấn', 0, '0300000015', '1990-08-10'),
    ('KH00000016', '200000016', N'Nguyễn Dương Lập', 0, '0300000016', '1988-10-28'),
    ('KH00000017', '200000017', N'Nguyễn Văn Nghị', 0, '0300000017', '1994-12-24'),
    ('KH00000018', '200000018', N'Trần Hoài Phương', 0, '0300000018', '1982-05-28'),
    ('KH00000019', '200000019', N'Đinh Diệp Vy', 1, '0300000019', '1983-11-16'),
    ('KH00000020', '200000020', N'Hoàng Thị Hồng', 1, '0300000020', '1985-07-29')
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
    (ngayGioDat, ngayGioTra, tinhTrangHD, TongTien, maNhanVien, maKH, maPhong)
VALUES
    ('2021-10-01 10:00:00', '2021-10-01 12:00:00', 1, 0.0, 'NV00000001', 'KH00000001', 'P0001'),
    ('2021-10-01 15:00:00', '2021-10-01 17:00:00', 1, 0.0, 'NV00000003', 'KH00000003', 'P0002'),
    ('2021-10-01 15:30:00', '2021-10-01 18:10:00', 1, 0.0, 'NV00000004', 'KH00000004', 'P0003'),
    ('2021-10-02 12:00:00', '2021-10-02 13:00:00', 1, 0.0, 'NV00000002', 'KH00000002', 'P0004'),
    ('2021-10-02 12:00:00', '2021-10-02 13:00:00', 1, 0.0, 'NV00000002', 'KH00000002', 'P0005')
GO

INSERT INTO dbo.CTDichVu
    (maDichVu, maHoaDon, soLuongDat, tienDichVu)
VALUES
    ('DV001', 1, 1, 30000),
    ('DV002', 1, 2, 70000),
    ('DV003', 1, 2, 70000),

    ('DV003', 2, 2, 70000),
    ('DV002', 2, 6, 210000),
    ('DV004', 2, 1, 42000),

    ('DV001', 3, 1, 30000),
    ('DV003', 3, 2, 70000),
    ('DV007', 3, 1, 32000),

    ('DV002', 4, 2, 70000),
    ('DV001', 4, 4, 120000),
    ('DV005', 4, 1, 30000),

    ('DV002', 5, 2, 70000),
    ('DV001', 5, 4, 120000),
    ('DV005', 5, 1, 30000)
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

    SELECT @isExitsServiceTypeId = ldv.maLDV
    FROM dbo.LoaiDichVu ldv
    WHERE ldv.maLDV = @servideTypeId

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

CREATE PROC USP_getServiceListByServiceName
    @ServiceName NVARCHAR(100)
AS
BEGIN
    DECLARE @name NVARCHAR(102) = N'%' + @ServiceName + N'%'
    SELECT dv.maDichVu, dv.giaBan, dv.soLuongTon, dv.tenDichVu,
        ldv.tenLDV, ldv.maLDV
    FROM dbo.DichVu dv, dbo.LoaiDichVu ldv
    WHERE dv.maLDV = ldv.maLDV
        AND dbo.fuConvertToUnsign(dv.tenDichVu) LIKE dbo.fuConvertToUnsign(@name)
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
    @servideId VARCHAR(5),
    @serviceName NVARCHAR(100),
    @price MONEY,
    @quantityInStock INT,
    @serviceTypeId VARCHAR(6)
AS
BEGIN
    DECLARE @isExitsServiceId VARCHAR(5)
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
    @servideId VARCHAR(5)
AS
BEGIN
    SELECT dv.tenDichVu
    FROM dbo.DichVu dv
    WHERE dv.maDichVu = @servideId
END
GO

CREATE PROC USP_updateInfoService
    @servideId VARCHAR(5),
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
    SELECT hd.maHoaDon, hd.ngayGioDat, hd.ngayGioTra, hd.tinhTrangHD,
        p.maPhong, p.tinhTrangP, p.viTri,
        lp.giaTien, lp.maLP, lp.sucChua, lp.tenLP,
        kh.cmnd AS cmndKH, kh.gioiTinh AS gioiTinhKH, kh.hoTen AS hoTenKH,
        kh.maKH, kh.ngaySinh AS ngaySinhKH, kh.soDienThoai AS sdtKH,
        nv.chucVu, nv.cmnd AS cmndNV, nv.gioiTinh AS gioiTinhNV,
        nv.hoTen AS hoTenNV, nv.maNhanVien, nv.mucLuong, nv.ngaySinh AS ngaySinhNV,
        nv.soDienThoai AS sdtNV, nv.trangThaiNV,
        tk.tenDangNhap, tk.matKhau, tk.tinhTrangTK
    FROM dbo.HoaDon hd, dbo.Phong p, dbo.LoaiPhong lp,
        dbo.KhachHang kh, dbo.NhanVien nv, dbo.TaiKhoan tk
    WHERE hd.maPhong = p.maPhong
        AND p.maLP = lp.maLP
        AND p.maPhong = @roomId
        AND hd.tinhTrangHD = 0
END
GO

CREATE PROC USP_getBillByBillId
    @billId INT
AS
BEGIN
    SELECT hd.maHoaDon, hd.ngayGioDat, hd.ngayGioTra, hd.tinhTrangHD,
        p.maPhong, p.tinhTrangP, p.viTri,
        lp.giaTien, lp.maLP, lp.sucChua, lp.tenLP,
        kh.cmnd AS cmndKH, kh.gioiTinh AS gioiTinhKH, kh.hoTen AS hoTenKH,
        kh.maKH, kh.ngaySinh AS ngaySinhKH, kh.soDienThoai AS sdtKH,
        nv.chucVu, nv.cmnd AS cmndNV, nv.gioiTinh AS gioiTinhNV,
        nv.hoTen AS hoTenNV, nv.maNhanVien, nv.mucLuong, nv.ngaySinh AS ngaySinhNV,
        nv.soDienThoai AS sdtNV, nv.trangThaiNV,
        tk.tenDangNhap, tk.matKhau, tk.tinhTrangTK
    FROM dbo.HoaDon hd, dbo.Phong p, dbo.LoaiPhong lp,
        dbo.KhachHang kh, dbo.NhanVien nv, dbo.TaiKhoan tk
    WHERE hd.maPhong = p.maPhong
        AND p.maLP = lp.maLP
        AND hd.maHoaDon = @billId
END
GO

CREATE PROC USP_insertBill
    @orderDate DATETIME,
    @staffId VARCHAR(10),
    @customerId VARCHAR(10),
    @roomId VARCHAR(5)
AS
BEGIN
    INSERT INTO dbo.HoaDon
        (ngayGioDat, ngayGioTra, tinhTrangHD, TongTien, maNhanVien, maKH, maPhong)
    VALUES
        (@orderDate, NULL, 0, 0.0, @staffId, @customerId, @roomId)
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
    @billId INT,
    @paymentDate DATETIME,
    @totalPrice MONEY
AS
BEGIN
    UPDATE dbo.HoaDon 
    SET tinhTrangHD = 1 , TongTien = @totalPrice, ngayGioTra = @paymentDate
    WHERE maHoaDon = @billId

    DECLARE @roomId VARCHAR(5)
    SELECT @roomID = hd.maPhong
    FROM dbo.HoaDon hd
    WHERE hd.maHoaDon = @billId

    UPDATE dbo.Phong
    SET tinhTrangP = 0
    WHERE maPhong = @roomID
END
GO


-- chi tiết dịch vụ
CREATE PROC USP_getServiceDetailListByRoomId
    @roomId VARCHAR(5)
AS
BEGIN
    SELECT cthd.soLuongDat,
        dv.maDichVu, dv.giaBan, dv.soLuongTon, dv.tenDichVu,
        hd.maHoaDon, hd.maKH, hd.maNhanVien, hd.ngayGioDat,
        hd.ngayGioTra, hd.tinhTrangHD,
        ldv.maLDV, ldv.tenLDV,
        p.maLP, p.maPhong, p.tinhTrangP, p.viTri
    FROM dbo.CTDichVu cthd,
        dbo.HoaDon hd,
        dbo.DichVu dv,
        dbo.LoaiDichVu ldv,
        dbo.Phong p,
        dbo.LoaiPhong lp
    WHERE cthd.maHoaDon = hd.maHoaDon
        AND hd.maPhong = p.maPhong
        AND cthd.maDichVu = dv.maDichVu
        AND dv.maLDV = ldv.maLDV
        AND p.maPhong = @roomId
        AND p.maLP = lp.maLP
        AND hd.tinhTrangHD = 0
END
GO

CREATE PROC USP_getServiceDetailListByBillId
    @billId INT
AS
BEGIN
    SELECT cthd.soLuongDat, cthd.maHoaDon, cthd.tienDichVu,
        dv.maDichVu, dv.giaBan, dv.soLuongTon, dv.tenDichVu,
        ldv.maLDV, ldv.tenLDV
    FROM dbo.CTDichVu cthd,
        dbo.DichVu dv,
        dbo.LoaiDichVu ldv
    WHERE cthd.maHoaDon = @billId
        AND cthd.maDichVu = dv.maDichVu
        AND dv.maLDV = ldv.maLDV
END
GO

CREATE PROC USP_getServiceDetailByBillIdAndServiceId
    @billId INT,
    @serviceId VARCHAR(5)
AS
BEGIN
    SELECT cthd.soLuongDat,
        dv.maDichVu, dv.giaBan, dv.soLuongTon, dv.tenDichVu,
        hd.maHoaDon, hd.maKH, hd.maNhanVien, hd.ngayGioDat,
        hd.ngayGioTra, hd.tinhTrangHD,
        ldv.maLDV, ldv.tenLDV,
        p.maLP, p.maPhong, p.tinhTrangP, p.viTri
    FROM dbo.CTDichVu cthd,
        dbo.HoaDon hd,
        dbo.DichVu dv,
        dbo.LoaiDichVu ldv,
        dbo.Phong p
    WHERE cthd.maHoaDon = hd.maHoaDon
        AND cthd.maDichVu = dv.maDichVu
        AND dv.maLDV = ldv.maLDV
        AND hd.maPhong = p.maPhong
        AND hd.maHoaDon = @billId
        AND dv.maDichVu = @serviceId
END
GO

CREATE PROC USP_insertServiceDetail
    @serviceId VARCHAR(5),
    @billId VARCHAR(5),
    @quantityOrder INT,
    @price MONEY
AS
BEGIN
    DECLARE @isExitsCTDichVu INT
    DECLARE @soLuongCu INT = 1
    DECLARE @tienDichVu MONEY = 0.0
    DECLARE @soLuongTon INT = 0

    SELECT @soLuongTon = dv.soLuongTon
    FROM dbo.DichVu dv
    WHERE dv.maDichVu = @serviceId

    SELECT @isExitsCTDichVu = ctdv.maHoaDon , @soLuongCu = ctdv.soLuongDat
    FROM dbo.CTDichVu ctdv, dbo.DichVu dv
    WHERE ctdv.maDichVu = dv.maDichVu
        AND ctdv.maHoaDon = @billId
        AND ctdv.maDichVu = @serviceId

    -- hóa đơn tồn tại -> cập nhật
    IF(@isExitsCTDichVu > 0)
        BEGIN
        DECLARE @soLuongMoi INT = @quantityOrder + @soLuongCu
        IF(@soLuongMoi > 0)
            BEGIN
            SET @tienDichVu = @soLuongMoi * @price
            UPDATE dbo.CTDichVu
                SET soLuongDat = @soLuongMoi,
                    tienDichVu = @tienDichVu
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
            SET soLuongTon = @soLuongTon - @quantityOrder
            WHERE maDichVu = @serviceId
    END
    -- hóa đơn không tồn tại -> tạo mới
    ELSE
        BEGIN
        SET @tienDichVu = @quantityOrder * @price
        INSERT INTO dbo.CTDichVu
            (maHoaDon, maDichVu, soLuongDat, tienDichVu)
        VALUES
            (@billId, @serviceId, @quantityOrder, @tienDichVu)

        UPDATE dbo.DichVu
                SET soLuongTon = @soLuongTon - @quantityOrder
                WHERE maDichVu = @serviceId
    END
END
GO


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

CREATE PROC USP_getRoomListByRoomTypeName
    @roomTypeName NVARCHAR(100)
AS
BEGIN
    SELECT p.maPhong, p.tinhTrangP, p.viTri,
        lp.maLP, lp.giaTien, lp.sucChua, lp.tenLP
    FROM dbo.Phong p, dbo.LoaiPhong lp
    WHERE p.maLP = lp.maLP
        AND lp.tenLP =@roomTypeName
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

    DECLARE @isExitsId VARCHAR(6)
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
    @billId INT,
    @oldRoomId VARCHAR(5),
    @newRoomId VARCHAR(5)
AS
BEGIN
    BEGIN TRANSACTION
    EXEC USP_updateRoomStatus 0, @oldRoomId
    EXEC USP_updateRoomStatus 1, @newRoomId

    UPDATE dbo.HoaDon
    SET maPhong = @newRoomId
    WHERE maPhong = @oldRoomId
        AND maHoaDon = @billId
        AND tinhTrangHD = 0

    DECLARE @isExitsId VARCHAR(6)
    SELECT @isExitsId = hd.maHoaDon
    FROM dbo.HoaDon hd
    WHERE hd.maPhong = @newRoomId
        AND hd.maHoaDon = @billId

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
    @maNV VARCHAR(12)
AS
BEGIN
    SELECT nv.chucVu, nv.cmnd AS cmndNV, nv.gioiTinh AS gioiTinhNV,
        nv.hoTen AS hoTenNV, nv.maNhanVien, nv.mucLuong, nv.ngaySinh AS ngaySinhNV,
        nv.soDienThoai AS sdtNV, nv.trangThaiNV
    FROM dbo.TaiKhoan tk, dbo.NhanVien nv
    WHERE tk.tenDangNhap = nv.taiKhoan
        AND nv.maNhanVien = @maNV
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