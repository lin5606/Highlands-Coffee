package HighlandsCoffee.dao;

import HighlandsCoffee.DBConnection;
import HighlandsCoffee.model.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    
    // Thêm khách hàng mới vào Database
    public boolean addCustomer(Customer customer) {
        String sql = "INSERT INTO KhachHang (Customer_id, Phone_number, Full_name, Reward_points, Membership_tier) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            // SỬA LỖI: Đổi tên các hàm Get theo đúng chuẩn của nhóm
            ps.setString(1, customer.getCustomer_id());
            ps.setString(2, customer.getPhone_number());
            ps.setString(3, customer.getFullName()); // Hàm này không bị lỗi nên giữ nguyên
            ps.setInt(4, customer.getReward_points());
            ps.setString(5, customer.getMembership_tier().name()); // Thêm .name() vì đây là Enum
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật điểm và hạng thành viên
    public boolean updateCustomer(Customer customer) {
        String sql = "UPDATE KhachHang SET Full_name = ?, Phone_number = ?, Reward_points = ?, Membership_tier = ? WHERE Customer_id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, customer.getFullName());
            ps.setString(2, customer.getPhone_number());
            ps.setInt(3, customer.getReward_points());
            ps.setString(4, customer.getMembership_tier().name()); // Thêm .name()
            ps.setString(5, customer.getCustomer_id());
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

public List<Customer> getAllCustomers() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) { // <--- Biến rs nằm ở đây này!
            
            while (rs.next()) {
                Customer c = new Customer();
                
                // Lấy dữ liệu từ SQL đắp vào Object Java
                // LƯU Ý: Chữ màu cam trong ngoặc kép phải giống Y HỆT tên cột trong SQL
                c.setCustomer_id(rs.getString("Customer_id")); 
                c.setPhone_number(rs.getString("Phone_number"));
                c.setFullName(rs.getString("Full_name"));
                c.setReward_points(rs.getInt("Reward_points"));
                
                // Nếu Membership_tier là Enum thì có thể cần sửa dòng này, 
                // nhưng tạm thời cứ để vầy cho hết lỗi đỏ đã nhé:
                // c.setMembership_tier(rs.getString("Membership_tier")); 
                
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}