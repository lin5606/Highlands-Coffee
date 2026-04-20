package HighlandsCoffee.form;

import HighlandsCoffee.dao.InvoiceDAO;
import HighlandsCoffee.dao.InvoiceDetailDAO;
import HighlandsCoffee.model.Invoice;
import HighlandsCoffee.model.InvoiceDetail;
import HighlandsCoffee.model.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class HoaDon extends JPanel {

    private JTable tableHoaDon;
    private DefaultTableModel tableModel;
    private JTextField txtMaHoaDon, txtNgay, txtKhachHang, txtTongTien;
    private JTextArea txtChiTiet;
    private JButton btnThemMoi, btnInHoaDon, btnSua, btnXoa, btnLamMoi;

    private InvoiceDAO invoiceDAO = new InvoiceDAO();
    private InvoiceDetailDAO detailDAO = new InvoiceDetailDAO();
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public HoaDon() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        // ==========================================
        // 1. PHẦN NHẬP LIỆU (Top)
        // ==========================================
        JPanel pnlInput = new JPanel(new GridLayout(2, 4, 10, 15));
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin Hóa Đơn"));
        pnlInput.setOpaque(false);

        pnlInput.add(new JLabel("  Mã Hóa Đơn:"));
        txtMaHoaDon = new JTextField();
        txtMaHoaDon.setEditable(false);
        pnlInput.add(txtMaHoaDon);

        pnlInput.add(new JLabel("  Ngày:"));
        txtNgay = new JTextField();
        txtNgay.setEditable(false);
        pnlInput.add(txtNgay);

        pnlInput.add(new JLabel("  Khách Hàng:"));
        txtKhachHang = new JTextField();
        txtKhachHang.setEditable(false);
        pnlInput.add(txtKhachHang);

        pnlInput.add(new JLabel("  Tổng Tiền:"));
        txtTongTien = new JTextField();
        txtTongTien.setEditable(false);
        pnlInput.add(txtTongTien);

        // Chi tiết sản phẩm
        pnlInput.add(new JLabel("  Chi Tiết:"));
        txtChiTiet = new JTextArea(3, 20);
        txtChiTiet.setEditable(false);
        JScrollPane scrollChiTiet = new JScrollPane(txtChiTiet);
        pnlInput.add(scrollChiTiet);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(pnlInput, BorderLayout.NORTH);
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(topPanel, BorderLayout.NORTH);

        // ==========================================
        // 2. PHẦN BẢNG DỮ LIỆU (Center)
        // ==========================================
        String[] columns = {"Mã Hóa Đơn", "Ngày", "Khách Hàng", "Tổng Tiền"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableHoaDon = new JTable(tableModel);
        tableHoaDon.setRowHeight(30);
        tableHoaDon.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tableHoaDon.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(tableHoaDon);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // ==========================================
        // 3. PHẦN NÚT BẤM (Bottom)
        // ==========================================
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        pnlButtons.setOpaque(false);

        btnThemMoi = new JButton("Thêm Mới");
        btnInHoaDon = new JButton("In Hóa Đơn");
        btnSua = new JButton("Cập Nhật");
        btnXoa = new JButton("Xóa");
        btnLamMoi = new JButton("Làm Mới");

        btnThemMoi.setBackground(new Color(46, 204, 113)); btnThemMoi.setForeground(Color.WHITE);
        btnInHoaDon.setBackground(new Color(46, 204, 113)); btnInHoaDon.setForeground(Color.WHITE);
        btnSua.setBackground(new Color(52, 152, 219)); btnSua.setForeground(Color.WHITE);
        btnXoa.setBackground(new Color(231, 76, 60)); btnXoa.setForeground(Color.WHITE);
        btnLamMoi.setBackground(new Color(52, 152, 219)); btnLamMoi.setForeground(Color.WHITE);

        btnThemMoi.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnInHoaDon.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSua.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnXoa.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLamMoi.setFont(new Font("Segoe UI", Font.BOLD, 14));

        btnThemMoi.setFocusPainted(false); btnThemMoi.setBorderPainted(false); btnThemMoi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnInHoaDon.setFocusPainted(false); btnInHoaDon.setBorderPainted(false); btnInHoaDon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSua.setFocusPainted(false); btnSua.setBorderPainted(false); btnSua.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnXoa.setFocusPainted(false); btnXoa.setBorderPainted(false); btnXoa.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLamMoi.setFocusPainted(false); btnLamMoi.setBorderPainted(false); btnLamMoi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        pnlButtons.add(btnThemMoi);
        pnlButtons.add(btnInHoaDon);
        pnlButtons.add(btnSua);
        pnlButtons.add(btnXoa);
        pnlButtons.add(btnLamMoi);
        add(pnlButtons, BorderLayout.SOUTH);

        loadDataToTable();
        setupActions();
        clearForm();
    }

    private void loadDataToTable() {
        tableModel.setRowCount(0);
        List<Invoice> list = invoiceDAO.getAllInvoices();
        for (Invoice inv : list) {
            String customerName = (inv.getCustomer() != null) ? inv.getCustomer().getName() : "Khách vãng lai";
            Object[] row = {
                inv.getInvoiceId(),
                dateFormat.format(inv.getInvoiceDate()),
                customerName,
                currencyFormat.format(inv.getFinalTotal())
            };
            tableModel.addRow(row);
        }
    }

    private void clearForm() {
        txtMaHoaDon.setText("");
        txtNgay.setText("");
        txtKhachHang.setText("");
        txtTongTien.setText("");
        txtChiTiet.setText("");
        tableHoaDon.clearSelection();
    }

    private void setupActions() {
        // CLICK BẢNG
        tableHoaDon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tableHoaDon.getSelectedRow();
                if (row >= 0) {
                    String invoiceId = tableHoaDon.getValueAt(row, 0).toString();
                    loadInvoiceDetails(invoiceId);
                }
            }
        });

        // NÚT THÊM MỚI HÓA ĐƠN
        btnThemMoi.addActionListener(e -> {
            clearForm();
            String nextId = invoiceDAO.generateNextInvoiceID();
            txtMaHoaDon.setText(nextId);
            JOptionPane.showMessageDialog(this, "Để thêm hóa đơn mới, vui lòng sử dụng phần Bán Hàng.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        });

        // NÚT IN HÓA ĐƠN
        btnInHoaDon.addActionListener(e -> {
            int selectedRow = tableHoaDon.getSelectedRow();
            if (selectedRow >= 0) {
                String invoiceId = tableHoaDon.getValueAt(selectedRow, 0).toString();
                printInvoice(invoiceId);
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một hóa đơn để in!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });

        // NÚT SỬA HÓA ĐƠN
        btnSua.addActionListener(e -> {
            int selectedRow = tableHoaDon.getSelectedRow();
            if (selectedRow >= 0) {
                String invoiceId = tableHoaDon.getValueAt(selectedRow, 0).toString();
                Invoice inv = invoiceDAO.getInvoiceById(invoiceId);
                if (inv != null) {
                    // Có thể cho phép sửa ngày hoặc khách hàng, nhưng để đơn giản, chỉ update totals nếu cần
                    // Ở đây, giả sử không sửa gì, chỉ thông báo
                    JOptionPane.showMessageDialog(this, "Chức năng sửa hóa đơn chưa được triển khai đầy đủ.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một hóa đơn để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });

        // NÚT XÓA HÓA ĐƠN
        btnXoa.addActionListener(e -> {
            int selectedRow = tableHoaDon.getSelectedRow();
            if (selectedRow >= 0) {
                String invoiceId = tableHoaDon.getValueAt(selectedRow, 0).toString();
                int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa hóa đơn " + invoiceId + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    if (invoiceDAO.deleteInvoice(invoiceId)) {
                        JOptionPane.showMessageDialog(this, "Xóa hóa đơn thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        loadDataToTable();
                        clearForm();
                    } else {
                        JOptionPane.showMessageDialog(this, "Lỗi khi xóa hóa đơn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một hóa đơn để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });

        // NÚT LÀM MỚI
        btnLamMoi.addActionListener(e -> {
            loadDataToTable();
            clearForm();
        });
    }

    private void loadInvoiceDetails(String invoiceId) {
        Invoice inv = invoiceDAO.getInvoiceById(invoiceId);
        if (inv != null) {
            txtMaHoaDon.setText(inv.getInvoiceId());
            txtNgay.setText(dateFormat.format(inv.getInvoiceDate()));
            String customerName = (inv.getCustomer() != null) ? inv.getCustomer().getName() : "Khách vãng lai";
            txtKhachHang.setText(customerName);
            txtTongTien.setText(currencyFormat.format(inv.getFinalTotal()));

            // Load chi tiết
            List<InvoiceDetail> details = detailDAO.getInvoiceDetailsByInvoiceId(invoiceId);
            StringBuilder sb = new StringBuilder();
            for (InvoiceDetail detail : details) {
                sb.append(detail.getProduct().getName())
                  .append(" x").append(detail.getQuantity())
                  .append(" = ").append(currencyFormat.format(detail.getPrice() * detail.getQuantity()))
                  .append("\n");
            }
            sb.append("\nTổng trước thuế: ").append(currencyFormat.format(inv.getTotalAmount()));
            sb.append("\nThuế: ").append(currencyFormat.format(inv.getTotalTax()));
            sb.append("\nTổng cộng: ").append(currencyFormat.format(inv.getFinalTotal()));
            txtChiTiet.setText(sb.toString());
        }
    }

    private void printInvoice(String invoiceId) {
        Invoice inv = invoiceDAO.getInvoiceById(invoiceId);
        if (inv == null) return;

        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(new InvoicePrintable(inv, detailDAO, currencyFormat, dateFormat));

        if (job.printDialog()) {
            try {
                job.print();
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi in: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

class InvoicePrintable implements java.awt.print.Printable {

    private Invoice invoice;
    private InvoiceDetailDAO detailDAO;
    private NumberFormat currencyFormat;
    private SimpleDateFormat dateFormat;

    public InvoicePrintable(Invoice invoice, InvoiceDetailDAO detailDAO, NumberFormat currencyFormat, SimpleDateFormat dateFormat) {
        this.invoice = invoice;
        this.detailDAO = detailDAO;
        this.currencyFormat = currencyFormat;
        this.dateFormat = dateFormat;
    }

    @Override
    public int print(java.awt.Graphics graphics, java.awt.print.PageFormat pageFormat, int pageIndex) {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

        Font font = new Font("Arial", Font.PLAIN, 12);
        g2d.setFont(font);

        int y = 50;

        // Header
        Font boldFont = new Font("Arial", Font.BOLD, 20);
        g2d.setFont(boldFont);
        g2d.drawString("HÓA ĐƠN THANH TOÁN", 200, y);
        y += 40;

        g2d.setFont(font);
        g2d.drawString("Mã hóa đơn: " + invoice.getInvoiceId(), 50, y);
        y += 20;
        g2d.drawString("Ngày: " + dateFormat.format(invoice.getInvoiceDate()), 50, y);
        y += 20;
        String customer = (invoice.getCustomer() != null) ? invoice.getCustomer().getName() : "Khách vãng lai";
        g2d.drawString("Khách hàng: " + customer, 50, y);
        y += 40;

        // Chi tiết
        g2d.drawString("Chi Tiết Sản Phẩm:", 50, y);
        y += 20;
        List<InvoiceDetail> details = detailDAO.getInvoiceDetailsByInvoiceId(invoice.getInvoiceId());
        for (InvoiceDetail d : details) {
            g2d.drawString(d.getProduct().getName() + " x" + d.getQuantity() + " = " + currencyFormat.format(d.getPrice() * d.getQuantity()), 70, y);
            y += 20;
        }
        y += 20;

        // Tổng kết
        g2d.drawString("Tổng trước thuế: " + currencyFormat.format(invoice.getTotalAmount()), 300, y);
        y += 20;
        g2d.drawString("Thuế: " + currencyFormat.format(invoice.getTotalTax()), 300, y);
        y += 20;
        g2d.drawString("Tổng cộng: " + currencyFormat.format(invoice.getFinalTotal()), 300, y);
        y += 40;

        // Footer
        Font italicFont = new Font("Arial", Font.ITALIC, 14);
        g2d.setFont(italicFont);
        g2d.drawString("Cảm ơn quý khách đã mua hàng!", 200, y);

        return PAGE_EXISTS;
    }
}
