package com.doanjava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class AppTest 
{
    private static final int PAGE_SIZE = 5;
    public static void main(String[] args) {
        String databaseUrl = "jdbc:sqlite:C:\\Users\\shinanaoki\\Desktop\\DoAnJava\\csdl.db";

        try {
            Connection connection = DriverManager.getConnection(databaseUrl);

            Scanner scanner = new Scanner(System.in);

            System.out.println("Chao mung den voi phan mem Quan ly Thu vien!");

            System.out.println("1. Sinh vien");
            System.out.println("2. Quan ly");
            System.out.print("Chon loai tai khoan (1 hoac 2): ");
            int userType = scanner.nextInt();

            if (userType == 1) {
                System.out.print("Nhap ma sinh vien: ");
                String maSinhVien = scanner.next();
                System.out.print("Nhap mat khau: ");
                String matKhau = scanner.next();

                if (kiemTraDangNhapSinhVien(connection, maSinhVien, matKhau)) {
                    System.out.println("Dang nhap thanh cong!");
                    hienThiThongTinSach(connection);
                    muonSach(connection, maSinhVien);
                } else {
                    System.out.println("Dang nhap that bai!");
                }
            } else if (userType == 2) {
                System.out.print("Nhap ID Quan ly: ");
                String idThuThu = scanner.next();
                System.out.print("Nhap Mat khau: ");
                String matKhau = scanner.next();

                if (kiemTraDangNhapThuThu(connection, idThuThu, matKhau)) {
                    hienThiMenuThuThu();

                    int luaChon = scanner.nextInt();
                    switch (luaChon) {
                        case 1:
                            hienThiThongTinSinhVien(connection);
                            break;
                        case 2:
                            hienThiThongTinSachMaster(connection);
                            break;
                        case 3:
                            hienThiThongTinSachMuon(connection);
                            break;
                        case 4:
                            hienThiThongTinQuanTri(connection);
                            break;
                        default:
                            System.out.println("Lua chon khong hop le!");
                    System.out.println("Dang nhap thanh cong!");
                    
                }
             } else {
                    System.out.println("Dang nhap that bai!");
                }
            } else {
                System.out.println("Lua chon khong hop le!");
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean kiemTraDangNhapSinhVien(Connection connection, String maSinhVien, String matKhau) {
        try {
            String query = "SELECT * FROM ThongTinSinhVien WHERE MaSinhVien = ? AND MatKhauSV = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, maSinhVien);
            preparedStatement.setString(2, matKhau);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean kiemTraDangNhapThuThu(Connection connection, String idThuThu, String matKhau) {
        try {
            String query = "SELECT * FROM ThongTinThuThu WHERE IDThuThu = ? AND MatKhauTT = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, idThuThu);
            preparedStatement.setString(2, matKhau);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    private static void hienThiThongTinSach(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ThongTinSach LIMIT " + PAGE_SIZE);

            System.out.printf("%-20s%-10s%-10s\n", "Ten Sach", "ID Sach", "So Luong");
            System.out.println("-------------------------------------------");
            while (resultSet.next()) {
                String tenSach = resultSet.getString("TenSach");
                int idSach = resultSet.getInt("IDSach");
                int soLuong = resultSet.getInt("SoLuong");

                System.out.printf("%-20s%-10d%-10d\n", tenSach, idSach, soLuong);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void hienThiThongTinSachMaster(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ThongTinSach LIMIT " + PAGE_SIZE);

            System.out.printf("%-20s%-10s%-10s\n", "TenSach    ", "IDSach", "SoLuong");
            System.out.println("-------------------------------------------");
            while (resultSet.next()) {
                String TenSach = resultSet.getString("TenSach");
                int IDSach = resultSet.getInt("IDSach");
                int SoLuong = resultSet.getInt("SoLuong");

                System.out.printf("%-20s%-10d%-10d\n", TenSach, IDSach, SoLuong);
            }
			
			System.out.println();

            Scanner scanner = new Scanner(System.in);
            System.out.println("Chon chuc nang:");
            System.out.println("1. Them thong tin");
            System.out.println("2. Xoa thong tin");
            int luaChon = scanner.nextInt();

            switch (luaChon) {
                case 1:
                    themThongTinSach(connection);
                    break;
                case 2:
                    xoaThongTinSach(connection);
                    break;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
			
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void hienThiThongTinSachMuon(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM DanhSachMuon LIMIT " + PAGE_SIZE);

            System.out.printf("%-20s%-10s%-10s\n", "Ma Sinh Vien    ", "ID Sach", "So Luong");
            System.out.println("-------------------------------------------");
            while (resultSet.next()) {
                String MaSinhVien = resultSet.getString("MaSinhVien");
                int idSach = resultSet.getInt("IDSach");
                int soLuong = resultSet.getInt("SoLuong");

                System.out.printf("%-20s%-10d%-10d\n", MaSinhVien, idSach, soLuong);
            }

            System.out.println();

            Scanner scanner = new Scanner(System.in);
            System.out.println("Chon chuc nang:");
            System.out.println("1. Them thong tin");
            System.out.println("2. Xoa thong tin");
            int luaChon = scanner.nextInt();

            switch (luaChon) {
                case 1:
                    themThongTinSachMuon(connection);
                    break;
                case 2:
                    xoaThongTinSachMuon(connection);
                    break;
                default:
                    System.out.println("Lua chon khong hop le!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void hienThiThongTinQuanTri(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ThongTinThuThu LIMIT " + PAGE_SIZE);

            System.out.printf("%-20s%-10s%-10s\n", "IDThuThu", "TenThuThu    ", "Mat Khau");
            System.out.println("-------------------------------------------");
            while (resultSet.next()) {
                String IDThuThu = resultSet.getString("IDThuThu");
                int TenThuThu = resultSet.getInt("TenThuThu");
                int MatKhauTT = resultSet.getInt("MatKhauTT");

                System.out.printf("%-20s%-10d%-10d\n", IDThuThu, TenThuThu, MatKhauTT);
            }

            System.out.println();

            Scanner scanner = new Scanner(System.in);
            System.out.println("Chon chuc nang:");
            System.out.println("1. Them thong tin");
            System.out.println("2. Xoa thong tin");
            int luaChon = scanner.nextInt();

            switch (luaChon) {
                case 1:
                    themThongTinQuanTri(connection);
                    break;
                case 2:
                    xoaThongTinQuanTri(connection);
                    break;
                default:
                    System.out.println("Lua chon khong hop le!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void muonSach(Connection connection, String maSinhVien) {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Nhap ID Sach muon muon: ");
            int idSachMuon = scanner.nextInt();

            if (kiemTraSachTrongKho(connection, idSachMuon)) {
                capNhatSoLuongSach(connection, idSachMuon);

                themMuonSach(connection, maSinhVien, idSachMuon);

                System.out.println("Mượn sách thành công!");
            } else {
                System.out.println("Sách không có sẵn hoặc hết sách để mượn.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static boolean kiemTraSachTrongKho(Connection connection, int idSach) {
        try {
            String query = "SELECT * FROM ThongTinSach WHERE IDSach = ? AND SoLuong > 0";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idSach);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void capNhatSoLuongSach(Connection connection, int idSach) {
        try {
            String updateQuery = "UPDATE ThongTinSach SET SoLuong = SoLuong - 1 WHERE IDSach = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setInt(1, idSach);
            updateStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void themMuonSach(Connection connection, String maSinhVien, int idSach) {
        try {
            String insertQuery = "INSERT INTO DanhSachMuon (MaSinhVien, IDSach, SoLuong) VALUES (?, ?, 1)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setString(1, maSinhVien);
            insertStatement.setInt(2, idSach);
            insertStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void hienThiMenuThuThu() {
        System.out.println("Menu Quan Ly:");
        System.out.println("1. Quan ly sinh vien");
        System.out.println("2. Quan ly sach");
        System.out.println("3. Quan ly sach muon");
        System.out.println("4. Quan ly quan tri");
        System.out.print("Vui long chon chuc nang (1-4): ");
    }

    private static void hienThiThongTinSinhVien(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ThongTinSinhVien LIMIT 5");

            System.out.printf("%-15s %-20s %-15s%n", "Ma Sinh Vien", "Ten Sinh Vien", "Mat Khau");

            while (resultSet.next()) {
                String maSinhVien = resultSet.getString("MaSinhVien");
                String tenSinhVien = resultSet.getString("TenSinhVien");
                String matKhauSV = resultSet.getString("MatKhauSV");

                System.out.printf("%-15s %-20s %-15s%n", maSinhVien, tenSinhVien, matKhauSV);
            }

            System.out.println();

            Scanner scanner = new Scanner(System.in);
            System.out.println("Chon chuc nang:");
            System.out.println("1. Them thong tin");
            System.out.println("2. Xoa thong tin");
            int luaChon = scanner.nextInt();

            switch (luaChon) {
                case 1:
                    themThongTinSinhVien(connection);
                    break;
                case 2:
                    xoaThongTinSinhVien(connection);
                    break;
                default:
                    System.out.println("Lua chon khong hop le!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void themThongTinSinhVien(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhap ma sinh vien: ");
        String maSinhVien = scanner.next();
        System.out.print("Nhap ten sinh vien: ");
        String tenSinhVien = scanner.next();
        System.out.print("Nhap mat khau: ");
        String matKhau = scanner.next();

        try {
            String query = "INSERT INTO ThongTinSinhVien (MaSinhVien, TenSinhVien, MatKhauSV) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, maSinhVien);
            preparedStatement.setString(2, tenSinhVien);
            preparedStatement.setString(3, matKhau);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Them thanh cong!");
            } else {
                System.out.println("Them that bai!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void xoaThongTinSinhVien(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhap ma sinh vien muon xoa: ");
        String maSinhVien = scanner.next();

        try {
            String query = "DELETE FROM ThongTinSinhVien WHERE MaSinhVien = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, maSinhVien);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Xoa thong tin thanh cong!");
            } else {
                System.out.println("Xoa thong tin that bai!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void themThongTinQuanTri(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhap ma quan ly: ");
        String maQuanTri = scanner.next();
        System.out.print("Nhap ten quan ly: ");
        String tenQuanTri = scanner.next();
        System.out.print("Nhap mat khau: ");
        String matKhau = scanner.next();

        try {
            String query = "INSERT INTO ThongTinQuanTri (IDThuThu, TenThuThu, MatKhauTT) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, maQuanTri);
            preparedStatement.setString(2, tenQuanTri);
            preparedStatement.setString(3, matKhau);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Them thanh cong!");
            } else {
                System.out.println("Them that bai!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void xoaThongTinQuanTri(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhap ma quan ly muon xoa: ");
        String maQuanTri = scanner.next();

        try {
            String query = "DELETE FROM ThongTinQuanTri WHERE IDThuThu = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, maQuanTri);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Xoa thong tin thanh cong!");
            } else {
                System.out.println("Xoa thong tin that bai!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void themThongTinSachMuon(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhap ma sinh vien: ");
        String maSinhVien = scanner.next();
        System.out.print("Nhap ID Sach: ");
        String IDSach = scanner.next();
        System.out.print("Nhap so luong: ");
        String SoLuong = scanner.next();

        try {
            String query = "INSERT INTO DanhSachMuon (MaSinhVien, IDSach, SoLuong) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, maSinhVien);
            preparedStatement.setString(2, IDSach);
            preparedStatement.setString(3, SoLuong);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Them thanh cong!");
            } else {
                System.out.println("Them that bai!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void xoaThongTinSachMuon(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhap ma quan ly muon xoa: ");
        String maSinhVien = scanner.next();

        try {
            String query = "DELETE FROM DanhSachMuon WHERE MaSinhVien = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, maSinhVien);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Xoa thong tin thanh cong!");
            } else {
                System.out.println("Xoa thong tin that bai!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void themThongTinSach(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhap Ten Sach: ");
        String TenSach = scanner.next();
        System.out.print("Nhap ID Sach: ");
        String IDSach = scanner.next();
        System.out.print("Nhap So Luong: ");
        String SoLuong = scanner.next();

        try {
            String query = "INSERT INTO ThongTinSach (TenSach, IDSach, SoLuong) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, TenSach);
            preparedStatement.setString(2, IDSach);
            preparedStatement.setString(3, SoLuong);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Them thanh cong!");
            } else {
                System.out.println("Them that bai!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void xoaThongTinSach(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhap IDSach muon xoa: ");
        String IDSach = scanner.next();

        try {
            String query = "DELETE FROM ThongTinSach WHERE IDSach = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, IDSach);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Xoa thong tin thanh cong!");
            } else {
                System.out.println("Xoa thong tin that bai!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
