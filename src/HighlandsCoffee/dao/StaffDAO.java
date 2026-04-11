package HighlandsCoffee.dao;

import HighlandsCoffee.DBConnection;
import HighlandsCoffee.model.Staff;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffDAO extends DBContext {

    public List<Staff> getAllStaffs() {
    List<Staff> list = new ArrayList<>();
    // Phải là NhanVien (giống trong ảnh SSMS của bạn)
    String sql = "SELECT * FROM NhanVien"; 

    // Dùng đúng file kết nối mà bảng Khách hàng đã dùng thành công
    try (Connection conn = DBConnection.getConnection(); 
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Staff s = new Staff();
            // Tên cột phải GIỐNG HỆT trong SQL (có gạch dưới)
            s.setStaff_id(rs.getString("Staff_id"));
            s.setFullName(rs.getString("Full_name"));
            s.setBase_salary(rs.getDouble("Base_salary"));
            list.add(s);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

    public boolean insertStaff(Staff s) {
        String sql = "INSERT INTO NhanVien VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getStaff_id());
            ps.setString(2, s.getFullName());
            ps.setString(3, "Nhân viên"); // Hoặc s.getPosition().name()
            ps.setDouble(4, s.getBase_salary());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateStaff(Staff s) {
        String sql = "UPDATE NhanVien SET Full_name=?, Base_salary=? WHERE Staff_id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getFullName());
            ps.setDouble(2, s.getBase_salary());
            ps.setString(3, s.getStaff_id());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteStaff(String id) {
        String sql = "DELETE FROM NhanVien WHERE Staff_id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}