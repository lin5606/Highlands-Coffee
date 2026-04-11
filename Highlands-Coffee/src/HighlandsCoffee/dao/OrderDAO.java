package HighlandsCoffee.dao;

import HighlandsCoffee.model.*;
import HighlandsCoffee.enums.OrderStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends DBContext {

    // 1. Lấy tất cả Order
    public List<Order> getAllOrders() {
        List<Order> list = new ArrayList<>();

        String sql = "SELECT * FROM Orders";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Order o = new Order();

                o.setOrder_id(rs.getString("Order_id"));
                o.setOrder_date(rs.getDate("Order_date"));
                o.setTotal_amount(rs.getFloat("Total_amount"));

                // enum
                o.setOrder_status(OrderStatus.valueOf(rs.getString("Order_status")));

                // Staff (chỉ set ID)
                Staff s = new Staff();
                s.setStaff_id(rs.getString("Staff_id"));
                o.setStaff(s);

                // Customer
                Customer c = new Customer();
                c.setCustomer_id(rs.getString("Customer_id"));
                o.setCustomer(c);

                list.add(o);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 2. Lấy theo ID
    public Order getOrderById(String id) {

        String sql = "SELECT * FROM Orders WHERE Order_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Order o = new Order();

                o.setOrder_id(rs.getString("Order_id"));
                o.setOrder_date(rs.getDate("Order_date"));
                o.setTotal_amount(rs.getFloat("Total_amount"));
                o.setOrder_status(OrderStatus.valueOf(rs.getString("Order_status")));

                Staff s = new Staff();
                s.setStaff_id(rs.getString("Staff_id"));
                o.setStaff(s);

                Customer c = new Customer();
                c.setCustomer_id(rs.getString("Customer_id"));
                o.setCustomer(c);

                return o;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // 3. Insert
    public boolean insertOrder(Order o) {

        String sql = "INSERT INTO Orders VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, o.getOrder_id());
            ps.setDate(2, new java.sql.Date(o.getOrder_date().getTime()));
            ps.setString(3, o.getStaff().getStaff_id());
            ps.setString(4, o.getCustomer().getCustomer_id());
            ps.setFloat(5, o.getTotal_amount());
            ps.setString(6, o.getOrder_status().name());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 4. Update
    public boolean updateOrder(Order o) {

        String sql = "UPDATE Orders SET Order_date=?, Staff_id=?, Customer_id=?, Total_amount=?, Order_status=? WHERE Order_id=?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, new java.sql.Date(o.getOrder_date().getTime()));
            ps.setString(2, o.getStaff().getStaff_id());
            ps.setString(3, o.getCustomer().getCustomer_id());
            ps.setFloat(4, o.getTotal_amount());
            ps.setString(5, o.getOrder_status().name());
            ps.setString(6, o.getOrder_id());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 5. Delete
    public boolean deleteOrder(String id) {

        String sql = "DELETE FROM Orders WHERE Order_id=?";

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