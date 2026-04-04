package HighlandsCoffee.model;

import HighlandsCoffee.enums.StaffPosition;

public class Staff extends User {
    private String staffId; // Mã nhân viên (Khóa chính) 
    private String fullName; // Họ và tên nhân viên 
    private StaffPosition position; // Chức vụ (Quản lý, Phục vụ, Thu ngân...) 
    private double baseSalary; // Lương cơ bản 

    // Constructor rỗng
    public Staff() {
    }

    // Constructor khởi tạo nhân viên mới (bao gồm cả tài khoản kế thừa từ User)
    public Staff(String username, String password, String staffId, String fullName, StaffPosition position, double baseSalary) {
        super(username, password); // Kế thừa username và password từ lớp cha User
        this.staffId = staffId;
        this.fullName = fullName;
        this.position = position;
        this.baseSalary = baseSalary;
    }

    // --- Getters và Setters ---

    public String getStaff_id() {
        return staffId;
    }

    public void setStaff_id(String staffId) {
        this.staffId = staffId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public StaffPosition getPosition() {
        return position;
    }

    public void setPosition(StaffPosition position) {
        this.position = position;
    }

    public double getBase_salary() {
        return baseSalary;
    }

    public void setBase_salary(double baseSalary) {
        this.baseSalary = baseSalary;
    }

    // --- Phương thức nghiệp vụ ---

    public void displayInfo() {
        System.out.println("Mã NV: " + staffId + " | Tên: " + fullName + 
                           " | Chức vụ: " + position + " | Lương CB: " + baseSalary +
                           " | Tài khoản đăng nhập: " + username);
    }
    
    // Phương thức tính lương cơ bản dựa trên số giờ làm thực tế
    public double calculateSalary(double workedHours) {
        return baseSalary * workedHours;
    }
}

