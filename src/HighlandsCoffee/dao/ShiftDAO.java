package HighlandsCoffee.dao;

import HighlandsCoffee.DBConnection;
import HighlandsCoffee.model.Shift;
import HighlandsCoffee.model.Staff;
import HighlandsCoffee.enums.ShiftName;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShiftDAO { // ĐÃ XÓA extends DBContext cho an toàn

    // 1. Lấy tất cả ca làm việc
    public List<Shift> getAllShifts() {
        List<Shift> list = new ArrayList<>();
        String sql = "SELECT * FROM CaLamViec";

        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Shift s = new Shift();
                s.setShiftId(rs.getInt("Shift_id"));

                Staff st = new Staff();
                st.setStaff_id(rs.getString("Staff_id"));
                s.setStaff(st);

                String sqlShiftName = rs.getString("Shift_name");
                if (sqlShiftName != null) {
                    if (sqlShiftName.equals("Ca Sáng")) s.setShiftName(ShiftName.CASANG);
                    else if (sqlShiftName.equals("Ca Chiều")) s.setShiftName(ShiftName.CACHIEU);
                    else if (sqlShiftName.equals("Ca Tối")) s.setShiftName(ShiftName.CATOI);
                    else s.setShiftName(ShiftName.HANHCHINH); 
                }

                s.setWorkDate(rs.getDate("Work_date").toLocalDate());

                Timestamp checkIn = rs.getTimestamp("Actual_checkIn");
                if (checkIn != null) s.setActualCheckIn(checkIn.toLocalDateTime());

                list.add(s);
            }
        } catch (Exception e) {
            System.err.println("Lỗi tại ShiftDAO: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // 2. Thêm ca làm việc mới
    public boolean insertShift(Shift shift) {
        String sql = "INSERT INTO CaLamViec (Staff_id, Shift_name, Work_date) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); // ĐÃ ĐỒNG BỘ
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, shift.getStaff().getStaff_id());
            
            // ĐÃ SỬA: Chuyển Enum thành chuỗi tiếng Việt y như SQL
            String nameForSQL = "";
            if (shift.getShiftName() == ShiftName.CASANG) nameForSQL = "Ca Sáng";
            else if (shift.getShiftName() == ShiftName.CACHIEU) nameForSQL = "Ca Chiều";
            else if (shift.getShiftName() == ShiftName.CATOI) nameForSQL = "Ca Tối";
            else nameForSQL = "Hành chính";
            
            ps.setString(2, nameForSQL);
            ps.setDate(3, java.sql.Date.valueOf(shift.getWorkDate()));

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 3. Cập nhật ca làm việc
    public boolean updateShift(Shift shift) {
        String sql = "UPDATE CaLamViec SET Staff_id = ?, Shift_name = ?, Work_date = ? WHERE Shift_id = ?";
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, shift.getStaff().getStaff_id());
            
            String nameForSQL = "";
            if (shift.getShiftName() == ShiftName.CASANG) nameForSQL = "Ca Sáng";
            else if (shift.getShiftName() == ShiftName.CACHIEU) nameForSQL = "Ca Chiều";
            else if (shift.getShiftName() == ShiftName.CATOI) nameForSQL = "Ca Tối";
            else nameForSQL = "Hành chính";
            
            ps.setString(2, nameForSQL);
            ps.setDate(3, java.sql.Date.valueOf(shift.getWorkDate()));
            ps.setInt(4, shift.getShiftId());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 4. Xóa ca làm việc
    public boolean deleteShift(int shiftId) {
        String sql = "DELETE FROM CaLamViec WHERE Shift_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, shiftId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 5. Tìm ca làm việc theo ID (Bổ sung thêm)
    public Shift getShiftById(int shiftId) {
        String sql = "SELECT * FROM CaLamViec WHERE Shift_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, shiftId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                Shift s = new Shift();
                s.setShiftId(rs.getInt("Shift_id"));

                Staff st = new Staff();
                st.setStaff_id(rs.getString("Staff_id"));
                s.setStaff(st);

                String sqlShiftName = rs.getString("Shift_name");
                if (sqlShiftName != null) {
                    if (sqlShiftName.equals("Ca Sáng")) s.setShiftName(ShiftName.CASANG);
                    else if (sqlShiftName.equals("Ca Chiều")) s.setShiftName(ShiftName.CACHIEU);
                    else if (sqlShiftName.equals("Ca Tối")) s.setShiftName(ShiftName.CATOI);
                    else s.setShiftName(ShiftName.HANHCHINH); 
                }

                s.setWorkDate(rs.getDate("Work_date").toLocalDate());

                Timestamp checkIn = rs.getTimestamp("Actual_checkIn");
                if (checkIn != null) s.setActualCheckIn(checkIn.toLocalDateTime());

                return s;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}