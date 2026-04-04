package HighlandsCoffee.model;

import java.time.LocalDate;
import HighlandsCoffee.enums.ShiftName;

public class Shift {
    private int shiftId; // Mã ca làm [cite: 240, 305]
    private Staff staff; // Đối tượng nhân viên được phân công 
    private ShiftName shiftName; // Tên ca (Ca Sáng, Ca Chiều, Ca Tối) [cite: 240, 307]
    private LocalDate workDate; // Ngày làm việc dự kiến [cite: 240, 308]

    // Constructor rỗng
    public Shift() {
    }

    // Constructor khởi tạo đầy đủ thông tin phân công
    public Shift(int shiftId, Staff staff, ShiftName shiftName, LocalDate workDate) {
        this.shiftId = shiftId;
        this.staff = staff;
        this.shiftName = shiftName;
        this.workDate = workDate;
    }

    // --- Getters và Setters ---

    public int getShiftId() {
        return shiftId;
    }

    public void setShiftId(int shiftId) {
        this.shiftId = shiftId;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public ShiftName getShiftName() {
        return shiftName;
    }

    public void setShiftName(ShiftName shiftName) {
        this.shiftName = shiftName;
    }

    public LocalDate getWorkDate() {
        return workDate;
    }

    public void setWorkDate(LocalDate workDate) {
        this.workDate = workDate;
    }

    // --- Phương thức nghiệp vụ ---

    public void displaySchedule() {
        // Lấy tên nhân viên từ đối tượng staff để hiển thị cho trực quan
        String staffName = (staff != null) ? staff.getFullName() : "Chưa xác định";
        String staffCode = (staff != null) ? staff.getStaffId() : "N/A";
        
        System.out.println("Mã ca: " + shiftId + " | Ngày: " + workDate + 
                           " | Ca: " + shiftName + " | Nhân viên: " + staffName + " (" + staffCode + ")");
    }
}

