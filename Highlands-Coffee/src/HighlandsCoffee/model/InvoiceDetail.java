package HighlandsCoffee.model;

public class InvoiceDetail {
    private int detail_id;      // Primary key
    private int invoice_id;     // Foreign key
    private String product_name;
    private int quantity;
    private double unit_price;
    private double subtotal;
    private double tax_rate;
    private double tax_amount;
    private double total_price;

    // Constructor mặc định
    public InvoiceDetail() {
    }

    // Constructor đầy đủ
    public InvoiceDetail(int detail_id, int invoice_id, String product_name,
                         int quantity, double unit_price,
                         double tax_rate) {
        this.detail_id = detail_id;
        this.invoice_id = invoice_id;
        this.product_name = product_name;
        setQuantity(quantity);
        setUnit_price(unit_price);
        setTax_rate(tax_rate);
        calculateValues();
    }

    // ================== Getter ==================
    public int getDetail_id() {
        return detail_id;
    }

    public int getInvoice_id() {
        return invoice_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnit_price() {
        return unit_price;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getTax_rate() {
        return tax_rate;
    }

    public double getTax_amount() {
        return tax_amount;
    }

    public double getTotal_price() {
        return total_price;
    }

    // ================== Setter ==================
    public void setDetail_id(int detail_id) {
        this.detail_id = detail_id;
    }

    public void setInvoice_id(int invoice_id) {
        this.invoice_id = invoice_id;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setQuantity(int quantity) {
        if (quantity > 0) {
            this.quantity = quantity;
        } else {
            throw new IllegalArgumentException("Quantity phải > 0");
        }
    }

    public void setUnit_price(double unit_price) {
        if (unit_price >= 0) {
            this.unit_price = unit_price;
        } else {
            throw new IllegalArgumentException("Unit price phải >= 0");
        }
    }

    public void setTax_rate(double tax_rate) {
        if (tax_rate >= 0) {
            this.tax_rate = tax_rate;
        } else {
            throw new IllegalArgumentException("Tax rate phải >= 0");
        }
    }

    // ================== Tính toán ==================
    public void calculateValues() {
        this.subtotal = quantity * unit_price;
        this.tax_amount = subtotal * tax_rate / 100;
        this.total_price = subtotal + tax_amount;
    }

    // ================== Hiển thị ==================
    public void displayDetail() {
        System.out.printf("- SP: %-20s | SL: %2d | Đơn giá: %,10.0f | Thuế: %,8.0f | Tổng: %,10.0f%n", 
            product_name, 
            quantity, 
            unit_price, 
            tax_amount, 
            total_price);
    }
}