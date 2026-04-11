package HighlandsCoffee.dao;

import HighlandsCoffee.model.Shift;
import HighlandsCoffee.model.Staff;
import HighlandsCoffee.DBConnection;
import HighlandsCoffee.enums.ShiftName;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShiftDAO extends DBContext {

   public List<Shift> getAllShifts() {
        List<Shift> list = new ArrayList<>();
        String sql = "SELECT * FROM CaLamViec";

        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Shift s = new Shift();
                s.setShiftId(rs.getInt("Shift_id"));

                // 1. Xử lý Nhân viên (Staff)
                Staff st = new Staff();
                st.setStaff_id(rs.getString("Staff_id"));
                s.setStaff(st);

                // 2. Xử lý Enum ShiftName (Ép kiểu an toàn từ "Ca Sáng" sang Enum)
                String sqlShiftName = rs.getString("Shift_name");
                if (sqlShiftName != null) {
                    if (sqlShiftName.equals("Ca Sáng")) s.setShiftName(ShiftName.CASANG);
                    else if (sqlShiftName.equals("Ca Chiều")) s.setShiftName(ShiftName.CACHIEU);
                    else if (sqlShiftName.equals("Ca Tối")) s.setShiftName(ShiftName.CATOI);
                    else s.setShiftName(ShiftName.HANHCHINH); // Cho trường hợp "Hành chính"
                }

                // 3. Xử lý Ngày tháng
                s.setWorkDate(rs.getDate("Work_date").toLocalDate());

                // 4. Xử lý Giờ giấc (LocalDateTime)
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
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, shift.getStaff().getStaff_id());
            
            // SỬA LỖI 2: Thêm .name() để chuyển từ Enum sang String cho SQL
            ps.setString(2, shift.getShiftName().name());
            
            ps.setDate(3, java.sql.Date.valueOf(shift.getWorkDate()));

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}