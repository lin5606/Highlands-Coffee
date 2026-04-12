package HighlandsCoffee.dao;

import HighlandsCoffee.DBConnection;
import HighlandsCoffee.model.Staff;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffDAO { // Đã bỏ extends DBContext để dùng DBConnection cho chuẩn

    // 1. Lấy tất cả nhân viên
    public List<Staff> getAllStaffs() {
        List<Staff> list = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien"; 

        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Staff s = new Staff();
                s.setStaff_id(rs.getString("Staff_id"));
                s.setFullName(rs.getString("Full_name"));
                s.setBase_salary(rs.getDouble("Base_salary"));
                
                // Nếu class Staff của bạn có thuộc tính Position thì mở comment dòng dưới:
                // s.setPosition(rs.getString("Position")); 
                
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2. Thêm nhân viên mới
    public boolean insertStaff(Staff s) {
        // Nên ghi rõ tên cột để tránh lỗi nếu database có nhiều cột hơn
        String sql = "INSERT INTO NhanVien (Staff_id, Full_name, Position, Base_salary) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); // Đã sửa lại cho đồng bộ
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, s.getStaff_id());
            ps.setString(2, s.getFullName());
            ps.setString(3, "Nhân viên"); // Hoặc s.getPosition().name() nếu dùng Enum
            ps.setDouble(4, s.getBase_salary());
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 3. Cập nhật nhân viên
    public boolean updateStaff(Staff s) {
        String sql = "UPDATE NhanVien SET Full_name=?, Base_salary=? WHERE Staff_id=?";
        try (Connection conn = DBConnection.getConnection(); // Đã sửa lại cho đồng bộ
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

    // 4. Xóa nhân viên
    public boolean deleteStaff(String id) {
        String sql = "DELETE FROM NhanVien WHERE Staff_id=?";
        try (Connection conn = DBConnection.getConnection(); // Đã sửa lại cho đồng bộ
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, id);
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 5. Tìm nhân viên theo ID (Bổ sung thêm để sau này làm giao diện cho nhàn)
    public Staff getStaffById(String id) {
        String sql = "SELECT * FROM NhanVien WHERE Staff_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                Staff s = new Staff();
                s.setStaff_id(rs.getString("Staff_id"));
                s.setFullName(rs.getString("Full_name"));
                s.setBase_salary(rs.getDouble("Base_salary"));
                return s;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}