drop table dbo.CTDichVu
drop table dbo.HoaDon
drop table dbo.KhachHang
drop table dbo.NhanVien
drop table dbo.TaiKhoan
drop table dbo.Phong
drop table dbo.LoaiPhong
drop table dbo.DichVu
drop table dbo.LoaiDichVu

drop PROC dbo.USP_getAccountByUsername
drop PROC dbo.USP_getAccountList
drop PROC dbo.USP_getCTDichVuListByMaPhong
drop PROC dbo.USP_getDSDichVuByTenDichVu
drop PROC dbo.USP_getDSKhachHangByMaKH
drop PROC dbo.USP_getDSKhachHangBySDT
drop PROC dbo.USP_getDSKhachHangByTenKH
drop PROC dbo.USP_getDSKhachHangChuaDatPhong
drop PROC dbo.USP_getHoaDonByMaHD
drop PROC dbo.USP_getLastServiceID
drop PROC dbo.USP_getServiceList
drop PROC dbo.USP_getTableList
drop PROC dbo.USP_getUncheckHoaDonByMaPhong
drop PROC dbo.USP_Login

drop function dbo.fuConvertToUnsign