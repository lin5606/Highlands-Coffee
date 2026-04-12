package HighlandsCoffee.dao;

import HighlandsCoffee.DBConnection;
import HighlandsCoffee.model.Customer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    // 1. Lấy tất cả khách hàng
    public List<Customer> getAllCustomers() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Customer c = new Customer();
                c.setCustomer_id(rs.getString("Customer_id"));
                c.setPhone_number(rs.getString("Phone_number"));
                c.setFullName(rs.getString("Full_name"));
                c.setReward_points(rs.getInt("Reward_points"));
                
                // Xử lý Enum MembershipTier
                String tier = rs.getString("Membership_tier");
                if (tier != null) {
                    // c.setMembership_tier(MembershipTier.valueOf(tier));
                }
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2. Thêm khách hàng mới
    public boolean addCustomer(Customer customer) {
        String sql = "INSERT INTO KhachHang (Customer_id, Phone_number, Full_name, Reward_points, Membership_tier) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, customer.getCustomer_id());
            ps.setString(2, customer.getPhone_number());
            ps.setString(3, customer.getFullName());
            ps.setInt(4, customer.getReward_points());
            ps.setString(5, customer.getMembership_tier().name());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 3. Cập nhật thông tin khách hàng
    public boolean updateCustomer(Customer customer) {
        String sql = "UPDATE KhachHang SET Full_name = ?, Phone_number = ?, Reward_points = ?, Membership_tier = ? WHERE Customer_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, customer.getFullName());
            ps.setString(2, customer.getPhone_number());
            ps.setInt(3, customer.getReward_points());
            ps.setString(4, customer.getMembership_tier().name());
            ps.setString(5, customer.getCustomer_id());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 4. XÓA KHÁCH HÀNG (Hàm mới bổ sung)
    public boolean deleteCustomer(String customerId) {
        String sql = "DELETE FROM KhachHang WHERE Customer_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, customerId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 5. Tìm khách hàng theo ID (Hàm bổ sung để hỗ trợ việc Sửa/Xóa trên giao diện)
    public Customer getCustomerById(String id) {
        String sql = "SELECT * FROM KhachHang WHERE Customer_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Customer c = new Customer();
                c.setCustomer_id(rs.getString("Customer_id"));
                c.setPhone_number(rs.getString("Phone_number"));
                c.setFullName(rs.getString("Full_name"));
                c.setReward_points(rs.getInt("Reward_points"));
                return c;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}