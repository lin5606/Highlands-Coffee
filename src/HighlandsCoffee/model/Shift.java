package HighlandsCoffee.model;

import java.time.LocalDate;
import java.time.LocalDateTime; // Thêm thư viện này
import HighlandsCoffee.enums.ShiftName;

public class Shift {
    private int shiftId; 
    private Staff staff; 
    private ShiftName shiftName; 
    private LocalDate workDate; 
    // BỔ SUNG 2 DÒNG NÀY ĐỂ HẾT LỖI Ở DAO
    private LocalDateTime actualCheckIn; 
    private LocalDateTime actualCheckOut;

    // Constructor rỗng
    public Shift() {
    }

    // Constructor khởi tạo đầy đủ (Cập nhật thêm 2 trường mới)
    public Shift(int shiftId, Staff staff, ShiftName shiftName, LocalDate workDate, LocalDateTime actualCheckIn, LocalDateTime actualCheckOut) {
        this.shiftId = shiftId;
        this.staff = staff;
        this.shiftName = shiftName;
        this.workDate = workDate;
        this.actualCheckIn = actualCheckIn;
        this.actualCheckOut = actualCheckOut;
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

    // CÁC HÀM GETTER/SETTER MỚI THÊM VÀO
    public LocalDateTime getActualCheckIn() {
        return actualCheckIn;
    }

    public void setActualCheckIn(LocalDateTime actualCheckIn) {
        this.actualCheckIn = actualCheckIn;
    }

    public LocalDateTime getActualCheckOut() {
        return actualCheckOut;
    }

    public void setActualCheckOut(LocalDateTime actualCheckOut) {
        this.actualCheckOut = actualCheckOut;
    }

    // --- Phương thức nghiệp vụ ---

    public void displaySchedule() {
        String staffName = (staff != null) ? staff.getFullName() : "Chưa xác định";
        String staffCode = (staff != null) ? staff.getStaff_id() : "N/A";
        
        System.out.println("Mã ca: " + shiftId + " | Ngày: " + workDate + 
                           " | Ca: " + shiftName + " | Nhân viên: " + staffName + " (" + staffCode + ")");
    }
}