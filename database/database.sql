USE master
GO

CREATE DATABASE QuanLyKhachSan
GO

-- Drop DATABASE QuanLyKhachSan
-- GO

USE QuanLyKhachSan 
GO

CREATE TABLE TaiKhoan
(
    taiKhoan VARCHAR(50) NOT NULL PRIMARY KEY,
    matKhau VARCHAR(100) NOT NULL DEFAULT(N'123456'),
    -- 1. kích hoạt || 0. vô hiệu hóa
    tinhTrang BIT NOT NULL DEFAULT(1)
)
GO

CREATE TABLE NhanVien
(
    maNhanVien VARCHAR(10) NOT NULL PRIMARY KEY,
    cmnd VARCHAR(12) NOT NULL,
    hoTen NVARCHAR(100) NOT NULL DEFAULT(N''),
    ngaySinh DATETIME,
    -- 1. nam | 0. nữ
    gioiTinh BIT NOT NULL,
    soDienThoai VARCHAR(10),
    chucVu NVARCHAR(100) NOT NULL,
    mucLuong FLOAT NOT NULL,
    taiKhoan VARCHAR(50) NOT NULL,

    FOREIGN KEY (taiKhoan) REFERENCES dbo.TaiKhoan(taiKhoan)
)
GO

CREATE TABLE KhachHang
(
    maKH VARCHAR(10) NOT NULL PRIMARY KEY,
    cmnd VARCHAR(12) NOT NULL,
    hoTen NVARCHAR(100) NOT NULL DEFAULT(N''),
    -- 1. nam | 0. nữ
    gioiTinh BIT NOT NULL,
    soDienThoai VARCHAR(10),
    ngaySinh DATETIME
)
GO

CREATE TABLE HoaDon
(
    maHoaDon INT IDENTITY PRIMARY KEY,
    ngayGioDat DATETIME NOT NULL DEFAULT(getdate()),
    -- 0. chưa thanh toán | 1. đã thanh toán | 2. đặt phòng
    tinhTrang INT NOT NULL DEFAULT(0),

    maNhanVien VARCHAR(10) NOT NULL,
    maKH VARCHAR(10) NOT NULL,

    FOREIGN KEY (maNhanVien) REFERENCES dbo.NhanVien (maNhanVien),
    FOREIGN KEY (maKH) REFERENCES dbo.KhachHang (maKH)
)
GO

CREATE TABLE DichVu
(
    maDichVu VARCHAR(5) NOT NULL PRIMARY KEY,
    tenDichVu NVARCHAR(100) NOT NULL DEFAULT(N''),
    giaBan FLOAT NOT NULL DEFAULT(0),
    soLuongTon INT NOT NULL DEFAULT(0)
)
GO

CREATE TABLE CTDichVu
(
    maCTDichVu INT IDENTITY PRIMARY KEY,
    soLuongDat INT NOT NULL DEFAULT(1),
    ngayGioDat DATETIME NOT NULL DEFAULT(getdate()),
    maDichVu VARCHAR(5) NOT NULL,
    maHoaDon INT NOT NULL,

    FOREIGN KEY (maDichVU) REFERENCES dbo.DichVu (maDichVu),
    FOREIGN KEY (maHoaDon) REFERENCES dbo.HoaDon (maHoaDon)
)
GO

CREATE TABLE LoaiPhong
(
    maLP VARCHAR(5) NOT NULL PRIMARY KEY,
    tenLP NVARCHAR(100) NOT NULL DEFAULT(N''),
    sucChua INT NOT NULL DEFAULT(0),
    giaTien FLOAT NOT NULL DEFAULT(0),
)
GO

CREATE TABLE Phong
(
    maPhong VARCHAR(5) NOT NULL PRIMARY KEY,
    -- 0 là đã cho thuê | 1 là là còn trống | 2 là phòng tạm không dùng
    tinhTrang INT NOT NULL DEFAULT(0),
    viTri NVARCHAR(100) NOT NULL DEFAULT(N''),
    maLP VARCHAR(5) NOT NULL,

    FOREIGN KEY (maLP) REFERENCES dbo.LoaiPhong (maLP)

)
GO

CREATE TABLE CTPhong
(
    maCTPhong INT IDENTITY PRIMARY KEY,
    ngayGioNhan DATETIME NOT NULL,
    ngayGioTra DATETIME NOT NULL,
    maPhong VARCHAR(5) NOT NULL,
    maHoaDon INT NOT NULL,

    FOREIGN KEY (maPhong) REFERENCES dbo.Phong (maPhong),
    FOREIGN KEY (maHoaDon) REFERENCES dbo.HoaDon (maHoaDon)
)
GO

INSERT INTO dbo.DichVu
    (maDichVu, tenDichVu, giaBan, soLuongTon)
VALUES
    ('DV001', N'Thạch Dừa', 30000, 10),
    ('DV002', N'Thạch Dừa Dâu', 35000, 50),
    ('DV003', N'Thạch Dừa Bạc Hà', 35000, 30),
    ('DV004', N'Thạch Dừa Cam tươi', 42000, 20),
    ('DV005', N'Lô Hội Nha Đam', 30000, 15),
    ('DV006', N'Yaourt Lô Hội', 42000, 10),

    ('DV007', N'Iron man', 32000, 26),
    ('DV008', N'Moscow Beer', 32000, 84),
    ('DV009', N'Long Island iced Tea', 32000, 2),

    ('DV010', N'Heineken (Pháp)', 50000, 84),
    ('DV011', N'Heineken (Lon)', 30000, 85),
    ('DV012', N'Heineken (Chai)', 27000, 72),
    ('DV013', N'Spy', 65000, 83),
    ('DV014', N'Budweiser (Lon/Chai)', 33000, 62),
    ('DV015', N'Tiger (Chai)', 24000, 82),
    ('DV016', N'Tiger Crystal (Chai)', 27000, 75),

    ('DV017', N'Sinh tố Yaourt Đá', 35000, 72),
    ('DV018', N'Sinh tố Yaourt Lô Hội Cam', 60000, 63),
    ('DV019', N'SoDa', 30000, 28),
    ('DV020', N'SoDa Xí Muội', 45000, 100),
    ('DV021', N'SoDa Chanh Đường', 45000, 100),

    ('DV022', N'Pepsi', 30000, 100),
    ('DV023', N'7 Up', 30000, 83),
    ('DV024', N'Mirinda(Sarsi)', 30000, 83),
    ('DV025', N'Sting', 30000, 83),
    ('DV026', N'Red Bull', 25000, 83),
    ('DV027', N'Red Bull', 25000, 83),

    ('DV028', N'Gỏi Bưởi Hải Sản', 130000, 46),
    ('DV029', N'Gỏi Miến Thái Lan', 130000, 35),
    ('DV030', N'Gỏi Sứa Tôm Thịt', 130000, 28),

    ('DV031', N'Soup Bắp Cua Gà Xé', 40000, 28),
    ('DV032', N'Soup Hải Sản Thái Lan', 40000, 92),

    ('DV033', N'Salad Thập cẩm', 110000, 18),
    ('DV034', N'Salad Cá Ngừ', 130000, 29),
    ('DV035', N'Salad Trộn Thịt Bò', 130000, 83),

    ('DV036', N'Cà Ri Tôm', 260000, 28),
    ('DV037', N'Tôm Rang Me', 260000, 28),
    ('DV038', N'Tôm Sú Hấp Bia / Nước Dừa', 260000, 88),

    ('DV039', N'Cua Hấp Bia / Nước Dừa', 300000, 83),
    ('DV040', N'Cua Lột Chiên Giòn', 250000, 15),
    ('DV041', N'Cua Rang Me', 310000, 18),

    ('DV042', N'Bò Cuộn Phô Mai', 160000, 18),
    ('DV043', N'Bò Lúc Lắc', 190000, 93),

    ('DV044', N'Heo Rừng Nướng Muối Ớt', 140000, 26),

    ('DV045', N'Lẫu Cá Diêu Hồng', 275000, 26),
    ('DV046', N'Lẫu Hải Sản', 330000, 26),
    ('DV047', N'Lẫu Thái Lan', 330000, 73),

    ('DV048', N'Cánh Gà Chiên Nước Mắm', 120000, 24),
    ('DV049', N'Gà Hấp Thái Lan', 350000, 47),
    ('DV050', N'Gà Quay Tứ Xuyên', 350000, 23),

    ('DV051', N'Mì Xào Bò', 150000, 29),
    ('DV052', N'Hũ Tiếu Xào Bò', 150000, 38),
    ('DV053', N'Miến Xào Tôm', 150000, 34),

    ('DV054', N'Mực Nướng Muối Ớt', 160000, 34),
    ('DV055', N'Khô Mực Nướng', 130000, 47),
    ('DV056', N'Mực Xào Sa Tế', 160000, 8),
    ('DV057', N'Mực Chiên Xù', 160000, 6),
    ('DV058', N'Khô Chiên Giòn', 160000, 76),

    ('DV059', N'Cháo Hải Sản', 250000, 65),
    ('DV060', N'Cháo Mực', 250000, 89),
    ('DV061', N'Cháo Tôm Tươi', 200000, 67),
    ('DV062', N'Cháo Cá', 220000, 8)
GO

INSERT INTO dbo.TaiKhoan
    (taiKhoan, matKhau, tinhTrang)
VALUES
    ('phamdangdan', '123456', 1),
    ('huynhtuananh', '123456', 1),
    ('langnhapsang', '123456', 1),
    ('vominhhieu', '123456', 1),
    ('nhanvien1', '123456', 1),
    ('nhanvien2', '123456', 1),
    ('nhanvien3', '123456', 1),
    ('nhanvien4', '123456', 1)
GO

INSERT INTO dbo.NhanVien
    (maNhanVien, cmnd, hoTen, ngaySinh, gioiTinh, soDienThoai, chucVu, mucLuong, taiKhoan)
VALUES
    ('NV00000001', '111111111', N'Phạm Đăng Đan', '2001-01-01', 1, '0312345678', N'Quản lý', 6000000, 'phamdangdan'),
    ('NV00000002', '111111113', N'Huỳnh Tuấn Anh', '2001-01-01', 1, '0312345671', N'Quản lý', 6000000, 'huynhtuananh'),
    ('NV00000003', '111111112', N'Lăng Nhật Sang', '2001-01-01', 1, '0312345679', N'Quản lý', 6000000, 'langnhapsang'),
    ('NV00000004', '111111114', N'Võ Minh Hiếu', '2001-01-01', 1, '0312345672', N'Quản lý', 6000000, 'vominhhieu'),
    ('NV00000005', '111111115', N'Nguyễn Xuân Anh', '1999-06-14', 0, '0312345673', N'Nhân Viên', 3000000, 'nhanvien1'),
    ('NV00000006', '111111116', N'Trần Thị Ngọc Vân', '1998-07-17', 0, '0812144673', N'Nhân Viên', 2800000, 'nhanvien2'),
    ('NV00000007', '111111117', N'Trần Vinh Can', '1993-08-27', 1, '0715344673', N'Nhân Viên', 3100000, 'nhanvien3'),
    ('NV00000008', '111111118', N'Bùi Ngọc Văn', '1996-12-23', 1, '0532344647', N'Nhân Viên', 2700000, 'nhanvien4')
GO

INSERT INTO dbo.KhachHang
    (maKH, cmnd, hoTen, gioiTinh, soDienThoai, ngaySinh)
VALUES
    ('KH00000001', '211111111', N'Nguyễn Việt Nam', 1, '0653684411', '1987-09-15'),
    ('KH00000002', '211111112', N'Nguyễn Huỳnh Dũng', 1, '0468265445', '1989-07-24'),
    ('KH00000003', '211111113', N'Ngô Quốc Cường', 1, '0663473239', '1999-07-08'),
    ('KH00000004', '211111114', N'Nguyễn Thị Hảo', 0, '0662426330', '1978-06-20'),
    ('KH00000005', '211111115', N'Nguyễn Thị Hậu', 0, '0606411930', '1968-01-09'),
    ('KH00000006', '211111116', N'Nguyễn Vũ Lâm Đức', 1, '0624819104', '1993-09-06'),
    ('KH00000007', '211111117', N'Phan Thị Thủy', 0, '0632127202', '1987-06-10'),
    ('KH00000008', '211111118', N'Phạm Văn Duy', 1, '0682290550', '1992-12-05'),
    ('KH00000009', '211111119', N'Hoàng Tiến Hào', 1, '0646311277', '1998-03-02'),
    ('KH00000010', '211111120', N'Dương Ngọc Vy', 1, '0646221277', '1993-14-06'),
    ('KH00000011', '211111121', N'Nguyễn Chí Hoàng', 1, '0679042843', '1984-08-05'),
    ('KH00000012', '211111122', N'Đỗ Huy Hữu', 1, '0669648870', '1985-09-18'),
    ('KH00000013', '211111123', N'Nguyễn Anh Khoa', 0, '0677723969', '1995-08-02'),
    ('KH00000014', '211111124', N'Trần Anh Lâm', 0, '0686439811', '1973-08-22'),
    ('KH00000015', '211111125', N'Nguyễn Thái Huấn', 1, '0644708156', '1990-08-10'),
    ('KH00000016', '211111126', N'Nguyễn Dương Lập', 1, '0617190242', '1988-10-28'),
    ('KH00000017', '211111127', N'Nguyễn Văn Nghị', 1, '0664505419', '1994-12-24'),
    ('KH00000018', '211111128', N'Trần Hoài Phương', 1, '0602642649', '1982-05-28'),
    ('KH00000019', '211111129', N'Đinh Diệp Vy', 0, '0684218383', '1983-11-16'),
    ('KH00000020', '211111130', N'Hoàng Thị Hồng', 0, '0678763637', '1985-07-29')
GO

INSERT INTO dbo.LoaiPhong
    (maLP, tenLP, sucChua, giaTien)
VALUES
    ('LP001', N'Phòng 5', 5, 80000),
    ('LP002', N'Phòng 10', 10, 100000),
    ('LP003', N'Phòng 15', 15, 150000)
GO

INSERT INTO dbo.Phong
    (maPhong, tinhTrang, viTri, maLP)
VALUES
    ('P0001', 1, N'Tầng 1', 'LP001'),
    ('P0002', 1, N'Tầng 1', 'LP001'),
    ('P0003', 1, N'Tầng 1', 'LP001'),
    ('P0004', 1, N'Tầng 1', 'LP001'),
    ('P0005', 1, N'Tầng 1', 'LP001'),
    ('P0006', 1, N'Tầng 2', 'LP002'),
    ('P0007', 1, N'Tầng 2', 'LP002'),
    ('P0008', 1, N'Tầng 2', 'LP002'),
    ('P0009', 1, N'Tầng 2', 'LP002'),
    ('P0010', 1, N'Tầng 2', 'LP002'),
    ('P0011', 1, N'Tầng 3', 'LP003'),
    ('P0012', 1, N'Tầng 3', 'LP003'),
    ('P0013', 1, N'Tầng 3', 'LP003'),
    ('P0014', 1, N'Tầng 3', 'LP003'),
    ('P0015', 1, N'Tầng 3', 'LP003')
GO

INSERT INTO dbo.HoaDon
    (ngayGioDat, tinhTrang, maNhanVien, maKH)
VALUES
    ('2021-10-01 10:00:00', 1, 'NV00000001', 'KH00000001'),
    ('2021-10-02 12:00:00', 1, 'NV00000002', 'KH00000002'),
    ('2021-10-01 15:00:00', 1, 'NV00000003', 'KH00000003'),
    ('2021-10-01 15:30:00', 1, 'NV00000004', 'KH00000004')
GO

INSERT INTO dbo.CTPhong
    (ngayGioNhan, ngayGioTra, maPhong, maHoaDon)
VALUES
    ('2021-10-01 10:00:00', '2021-10-01 12:00:00', 'P0001', 1),
    ('2021-10-02 12:00:00', '2021-10-02 13:00:00', 'P0002', 2),
    ('2021-10-01 15:00:00', '2021-10-01 17:00:00', 'P0003', 3),
    ('2021-10-01 15:30:00', '2021-10-01 18:10:00', 'P0004', 4)
GO

INSERT INTO dbo.CTDichVu
    (soLuongDat, ngayGioDat, maDichVu, maHoaDon)
VALUES
    (1, '2021-10-01 10:05:00', 'DV001', 1),
    (2, '2021-10-01 10:05:00', 'DV002', 1),
    (2, '2021-10-01 11:00:00', 'DV003', 1),
    
    (1, '2021-10-02 12:10:00', 'DV005', 2),
    (2, '2021-10-02 12:10:00', 'DV002', 2),
    (4, '2021-10-02 12:10:00', 'DV001', 2),
    
    (2, '2021-10-01 15:01:00', 'DV003', 3),
    (6, '2021-10-01 15:30:00', 'DV002', 3),
    (1, '2021-10-01 16:02:00', 'DV003', 3),

    (3, '2021-10-01 15:32:00', 'DV001', 4),
    (2, '2021-10-01 16:30:00', 'DV001', 4),
    (1, '2021-10-01 17:15:00', 'DV001', 4)
GO

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