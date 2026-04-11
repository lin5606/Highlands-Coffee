package HighlandsCoffee.dao;

import HighlandsCoffee.model.Customer;
import HighlandsCoffee.model.Invoice;
import HighlandsCoffee.model.InvoiceDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDao extends DBContext {

    // 1. Lấy tất cả hóa đơn
    public List<Invoice> getAllInvoices() {
        List<Invoice> list = new ArrayList<>();
        String sql = "SELECT * FROM Invoice";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToInvoice(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 2. Lấy hóa đơn theo ID
    public Invoice getInvoiceById(int invoiceId) {
        String sql = "SELECT * FROM Invoice WHERE invoice_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, invoiceId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToInvoice(rs);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // 3. Lấy hóa đơn kèm chi tiết theo ID
    public Invoice getInvoiceByIdWithDetails(int invoiceId) {
        Invoice invoice = getInvoiceById(invoiceId);
        if (invoice != null) {
            InvoiceDetailDao detailDao = new InvoiceDetailDao();
            List<InvoiceDetail> details = detailDao.getInvoiceDetailsByInvoiceId(invoiceId);
            for (InvoiceDetail d : details) {
                invoice.addDetail(d);
            }
            invoice.recalculateTotals();
        }
        return invoice;
    }

    // 4. Insert
    public boolean insertInvoice(Invoice invoice) {
        String sql = "INSERT INTO Invoice (invoice_id, invoice_date, customer_id, total_amount, total_tax, final_total) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, invoice.getInvoiceId());
            ps.setDate(2, new java.sql.Date(invoice.getInvoiceDate().getTime()));
            ps.setString(3, invoice.getCustomer().getCustomer_id());
            ps.setDouble(4, invoice.getTotalAmount());
            ps.setDouble(5, invoice.getTotalTax());
            ps.setDouble(6, invoice.getFinalTotal());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 5. Update
    public boolean updateInvoice(Invoice invoice) {
        String sql = "UPDATE Invoice SET invoice_date = ?, customer_id = ?, total_amount = ?, total_tax = ?, final_total = ? WHERE invoice_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, new java.sql.Date(invoice.getInvoiceDate().getTime()));
            ps.setString(2, invoice.getCustomer().getCustomer_id());
            ps.setDouble(3, invoice.getTotalAmount());
            ps.setDouble(4, invoice.getTotalTax());
            ps.setDouble(5, invoice.getFinalTotal());
            ps.setInt(6, invoice.getInvoiceId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 6. Delete
    public boolean deleteInvoice(int invoiceId) {
        String sql = "DELETE FROM Invoice WHERE invoice_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, invoiceId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private Invoice mapResultSetToInvoice(ResultSet rs) throws Exception {
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(rs.getInt("invoice_id"));
        invoice.setInvoiceDate(rs.getDate("invoice_date"));

        Customer customer = new Customer();
        customer.setCustomer_id(rs.getString("customer_id"));
        invoice.setCustomer(customer);

        invoice.setTotalAmount(rs.getDouble("total_amount"));
        invoice.setTotalTax(rs.getDouble("total_tax"));
        invoice.setFinalTotal(rs.getDouble("final_total"));

        return invoice;
    }
}

