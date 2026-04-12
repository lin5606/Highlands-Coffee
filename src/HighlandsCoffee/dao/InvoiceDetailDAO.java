package HighlandsCoffee.dao;

import HighlandsCoffee.model.InvoiceDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDetailDAO extends DBContext {

    // 1. Lấy tất cả chi tiết hóa đơn 
    public List<InvoiceDetail> getAllInvoiceDetails() {
        List<InvoiceDetail> list = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietHoaDon";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToInvoiceDetail(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 2. Lấy chi tiết theo ID
    public InvoiceDetail getInvoiceDetailById(int detailId) {
        String sql = "SELECT * FROM ChiTietHoaDon WHERE detail_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, detailId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToInvoiceDetail(rs);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // 3. Lấy chi tiết theo 
    public List<InvoiceDetail> getInvoiceDetailsByInvoiceId(int invoiceId) {
        List<InvoiceDetail> list = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietHoaDon WHERE invoice_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, invoiceId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToInvoiceDetail(rs));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 4. Insert 
    public boolean insertInvoiceDetail(InvoiceDetail detail) {
        
        if (detail.getSubtotal() == 0) {
            detail.calculateValues();
        }

       
        String sql = "INSERT INTO ChiTietHoaDon (invoice_id, product_name, quantity, unit_price, subtotal, tax_rate, tax_amount, total_price) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, detail.getInvoice_id());
            ps.setString(2, detail.getProduct_name());
            ps.setInt(3, detail.getQuantity());
            ps.setDouble(4, detail.getUnit_price());
            ps.setDouble(5, detail.getSubtotal());
            ps.setDouble(6, detail.getTax_rate());
            ps.setDouble(7, detail.getTax_amount());
            ps.setDouble(8, detail.getTotal_price());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        detail.setDetail_id(rs.getInt(1));
                    }
                }
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 5. Update
    public boolean updateInvoiceDetail(InvoiceDetail detail) {
        detail.calculateValues();
        String sql = "UPDATE ChiTietHoaDon SET invoice_id = ?, product_name = ?, quantity = ?, unit_price = ?, "
                   + "subtotal = ?, tax_rate = ?, tax_amount = ?, total_price = ? WHERE detail_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, detail.getInvoice_id());
            ps.setString(2, detail.getProduct_name());
            ps.setInt(3, detail.getQuantity());
            ps.setDouble(4, detail.getUnit_price());
            ps.setDouble(5, detail.getSubtotal());
            ps.setDouble(6, detail.getTax_rate());
            ps.setDouble(7, detail.getTax_amount());
            ps.setDouble(8, detail.getTotal_price());
            ps.setInt(9, detail.getDetail_id());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 6. Delete 
    public boolean deleteInvoiceDetail(int detailId) {
        String sql = "DELETE FROM ChiTietHoaDon WHERE detail_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, detailId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 7. Delete
    public boolean deleteInvoiceDetailsByInvoiceId(int invoiceId) {
        String sql = "DELETE FROM ChiTietHoaDon WHERE invoice_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, invoiceId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private InvoiceDetail mapResultSetToInvoiceDetail(ResultSet rs) throws Exception {
        InvoiceDetail detail = new InvoiceDetail();
        detail.setDetail_id(rs.getInt("detail_id"));
        detail.setInvoice_id(rs.getInt("invoice_id"));
        detail.setProduct_name(rs.getString("product_name"));
        detail.setQuantity(rs.getInt("quantity"));
        detail.setUnit_price(rs.getDouble("unit_price"));
        detail.setTax_rate(rs.getDouble("tax_rate"));
        detail.setSubtotal(rs.getDouble("subtotal"));
        detail.setTax_amount(rs.getDouble("tax_amount"));
        detail.setTotal_price(rs.getDouble("total_price"));
        return detail;
    }
}