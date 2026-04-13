package HighlandsCoffee;

import HighlandsCoffee.dao.*;
import HighlandsCoffee.model.*;
import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("========== HỆ THỐNG QUẢN LÝ HIGHLANDS COFFEE ==========");
        System.out.println("            BÁO CÁO KIỂM TRA DỮ LIỆU TOÀN DIỆN         \n");

        // ---------------------------------------------------------
        // 1. KIỂM TRA BẢNG NHÂN VIÊN
        // ---------------------------------------------------------
        System.out.println("--- [1] DANH SÁCH NHÂN VIÊN ---");
        try {
            StaffDAO staffDAO = new StaffDAO();
            List<Staff> staffList = staffDAO.getAllStaffs();
            if (staffList.isEmpty()) {
                System.out.println("   ⚠️ Chưa có dữ liệu nhân viên trong SQL.");
            } else {
                System.out.println("   ✅ Tìm thấy " + staffList.size() + " nhân viên:");
                for (Staff s : staffList) {
                    System.out.printf("   - Mã NV: %-5s | Tên: %-20s | Lương: %,.0f VNĐ\n",
                            s.getStaff_id(), s.getFullName(), s.getBase_salary());
                }
            }
        } catch (Exception e) {
            System.out.println("   ❌ LỖI BẢNG NHÂN VIÊN: " + e.getMessage());
        }
        System.out.println();

        // ---------------------------------------------------------
        // 2. KIỂM TRA BẢNG KHÁCH HÀNG
        // ---------------------------------------------------------
        System.out.println("--- [2] DANH SÁCH KHÁCH HÀNG ---");
        try {
            CustomerDAO customerDAO = new CustomerDAO();
            List<Customer> customerList = customerDAO.getAllCustomers();
            if (customerList.isEmpty()) {
                System.out.println("   ⚠️ Chưa có dữ liệu khách hàng trong SQL.");
            } else {
                System.out.println("   ✅ Tìm thấy " + customerList.size() + " khách hàng:");
                for (Customer c : customerList) {
                    System.out.printf("   - Mã KH: %-5s | Tên: %-20s | SĐT: %-12s | Điểm: %d\n",
                            c.getCustomer_id(), c.getFullName(), c.getPhone_number(), c.getReward_points());
                }
            }
        } catch (Exception e) {
            System.out.println("   ❌ LỖI BẢNG KHÁCH HÀNG: " + e.getMessage());
        }
        System.out.println();

        // ---------------------------------------------------------
        // 3. KIỂM TRA BẢNG CA LÀM VIỆC
        // ---------------------------------------------------------
        System.out.println("--- [3] LỊCH PHÂN CA LÀM VIỆC ---");
        try {
            ShiftDAO shiftDAO = new ShiftDAO();
            List<Shift> shiftList = shiftDAO.getAllShifts();
            if (shiftList.isEmpty()) {
                System.out.println("   ⚠️ Chưa có dữ liệu ca trực trong SQL.");
            } else {
                System.out.println("   ✅ Tìm thấy " + shiftList.size() + " ca trực:");
                for (Shift s : shiftList) {
                    String staffId = (s.getStaff() != null) ? s.getStaff().getStaff_id() : "Trống";
                    System.out.printf("   - Ngày: %s | Ca: %-12s | Mã NV trực: %s\n",
                            s.getWorkDate(), s.getShiftName(), staffId);
                }
            }
        } catch (Exception e) {
            System.out.println("   ❌ LỖI BẢNG CA LÀM VIỆC: " + e.getMessage());
        }

        System.out.println("\n================ KẾT THÚC BÁO CÁO ====================");
        System.out.println("Hà ơi, nếu tất cả đều có dấu ✅ thì bạn đã hoàn thành xuất sắc nhiệm vụ rồi! 🏆");
    }
}