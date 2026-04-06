package HighlandsCoffee.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Invoice {

    private int invoiceId;
    private Date invoiceDate;

    private double totalAmount; // Tổng trước thuế
    private double totalTax;    // Tổng thuế
    private double finalTotal;  // Tổng sau thuế

    private Customer customer;
    private List<InvoiceDetail> details;

    // Constructor rỗng
    public Invoice() {
        this.details = new ArrayList<>();
    }

    // Constructor đầy đủ
    public Invoice(int invoiceId, Date invoiceDate, Customer customer) {
        this.invoiceId = invoiceId;
        this.invoiceDate = invoiceDate;
        this.customer = customer;
        this.details = new ArrayList<>();
    }

    // ================= GETTER & SETTER =================

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getTotalTax() {
        return totalTax;
    }

    public double getFinalTotal() {
        return finalTotal;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<InvoiceDetail> getDetails() {
        return details;
    }

    // ================= NGHIỆP VỤ =================

    // Thêm chi tiết hóa đơn
    public void addDetail(InvoiceDetail detail) {
        if (detail != null) {
            details.add(detail);
            recalculateTotals(); // Cập nhật ngay
        }
    }

    // Tính lại toàn bộ tiền
    public void recalculateTotals() {
        double amount = 0;
        double tax = 0;

        for (InvoiceDetail d : details) {
            amount += d.getSubtotal();
            tax += d.getTax_amount();
        }

        this.totalAmount = amount;
        this.totalTax = tax;
        this.finalTotal = amount + tax;
    }

    // Hiển thị
    public void displayInvoice() {
        System.out.println("===== HÓA ĐƠN =====");
        System.out.println("Mã HĐ: " + invoiceId);
        System.out.println("Ngày: " + invoiceDate);

        if (customer != null) {
            customer.displayInfo();
        }

        System.out.println("----- Chi tiết -----");
        for (InvoiceDetail d : details) {
            d.displayDetail();
        }

        System.out.println("-------------------");
        System.out.println("Tổng trước thuế: " + totalAmount);
        System.out.println("Tổng thuế: " + totalTax);
        System.out.println("Thành tiền: " + finalTotal);
    }
}
