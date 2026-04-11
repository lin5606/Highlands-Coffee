package HighlandsCoffee.dao;

import HighlandsCoffee.model.InvoiceDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDetailDAO extends DBContext {

    // 1. Lấy tất cả chi tiết hóa đơn
    public List<InvoiceDetail> getAllInvoiceDetails() {
        List<InvoiceDetail> list = new ArrayList<>();
        String sql = "SELECT * FROM InvoiceDetail";

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
        String sql = "SELECT * FROM InvoiceDetail WHERE detail_id = ?";

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

    // 3. Lấy chi tiết theo invoice_id
    public List<InvoiceDetail> getInvoiceDetailsByInvoiceId(int invoiceId) {
        List<InvoiceDetail> list = new ArrayList<>();
        String sql = "SELECT * FROM InvoiceDetail WHERE invoice_id = ?";

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
        detail.calculateValues();
        String sql = "INSERT INTO InvoiceDetail (detail_id, invoice_id, product_name, quantity, unit_price, subtotal, tax_rate, tax_amount, total_price) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, detail.getDetail_id());
            ps.setInt(2, detail.getInvoice_id());
            ps.setString(3, detail.getProduct_name());
            ps.setInt(4, detail.getQuantity());
            ps.setDouble(5, detail.getUnit_price());
            ps.setDouble(6, detail.getSubtotal());
            ps.setDouble(7, detail.getTax_rate());
            ps.setDouble(8, detail.getTax_amount());
            ps.setDouble(9, detail.getTotal_price());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 5. Update
    public boolean updateInvoiceDetail(InvoiceDetail detail) {
        detail.calculateValues();
        String sql = "UPDATE InvoiceDetail SET invoice_id = ?, product_name = ?, quantity = ?, unit_price = ?, subtotal = ?, tax_rate = ?, tax_amount = ?, total_price = ? WHERE detail_id = ?";

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
        String sql = "DELETE FROM InvoiceDetail WHERE detail_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, detailId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 7. Delete theo invoice_id
    public boolean deleteInvoiceDetailsByInvoiceId(int invoiceId) {
        String sql = "DELETE FROM InvoiceDetail WHERE invoice_id = ?";

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

