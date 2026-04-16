package HighlandsCoffee.form;

import HighlandsCoffee.dao.InvoiceDAO;
import HighlandsCoffee.dao.InvoiceDetailDAO;
import HighlandsCoffee.model.Customer;
import HighlandsCoffee.model.Invoice;
import HighlandsCoffee.model.InvoiceDetail;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HoaDon extends JPanel {

    private final InvoiceDAO invoiceDAO = new InvoiceDAO();
    private final InvoiceDetailDAO detailDAO = new InvoiceDetailDAO();
    private final DefaultTableModel invoiceTableModel = new DefaultTableModel();
    private final DefaultTableModel detailTableModel = new DefaultTableModel();
    private final TableRowSorter<DefaultTableModel> invoiceSorter;

    private JTextField txtInvoiceId;
    private JTextField txtInvoiceDate;
    private JTextField txtCustomerId;
    private JTextField txtTotalAmount;
    private JTextField txtTotalTax;
    private JTextField txtFinalTotal;
    private JTextField txtSearch;
    private JTable tblInvoices;
    private JTable tblDetails;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public HoaDon() {
        setLayout(new BorderLayout());
        setBackground(new Color(248, 249, 250));
        initComponents();
        invoiceSorter = new TableRowSorter<>(invoiceTableModel);
        tblInvoices.setRowSorter(invoiceSorter);
        loadInvoices();
    }

    private void initComponents() {
        add(createHeaderPanel(), BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout(16, 16));
        content.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        content.setBackground(new Color(248, 249, 250));

        content.add(createFilterPanel(), BorderLayout.NORTH);
        content.add(createInvoiceFormPanel(), BorderLayout.CENTER);
        content.add(createDetailPanel(), BorderLayout.SOUTH);

        add(content, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(238, 238, 238));
        header.setPreferredSize(new Dimension(100, 70));

        JLabel title = new JLabel("QUẢN LÝ HÓA ĐƠN", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.add(title, BorderLayout.CENTER);

        return header;
    }

    private JPanel createFilterPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));
        panel.setBackground(new Color(248, 249, 250));

        JLabel lblSearch = new JLabel("Tìm kiếm hóa đơn:");
        lblSearch.setFont(new Font("Segoe UI", Font.BOLD, 18));
        txtSearch = createTextField();
        txtSearch.setPreferredSize(new Dimension(280, 32));
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { filterInvoices(); }
            public void removeUpdate(DocumentEvent e) { filterInvoices(); }
            public void changedUpdate(DocumentEvent e) { filterInvoices(); }
        });

        panel.add(lblSearch);
        panel.add(txtSearch);
        return panel;
    }

    private JPanel createInvoiceFormPanel() {
        JPanel panel = new JPanel(new BorderLayout(12, 12));
        panel.setBackground(new Color(248, 249, 250));

        panel.add(createInvoiceInputPanel(), BorderLayout.NORTH);
        panel.add(createInvoiceTable(), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createInvoiceInputPanel() {
        JPanel panel = new JPanel(new BorderLayout(12, 12));
        panel.setBackground(new Color(248, 249, 250));

        JPanel fields = new JPanel(new GridBagLayout());
        fields.setBackground(new Color(248, 249, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblInvoiceId = new JLabel("Mã hóa đơn:");
        JLabel lblDate = new JLabel("Ngày hóa đơn (yyyy-MM-dd):");
        JLabel lblCustomerId = new JLabel("Mã khách hàng:");
        JLabel lblTotalAmount = new JLabel("Tổng trước thuế:");
        JLabel lblTotalTax = new JLabel("Tổng thuế:");
        JLabel lblFinalTotal = new JLabel("Thành tiền:");

        txtInvoiceId = createTextField();
        txtInvoiceDate = createTextField();
        txtCustomerId = createTextField();
        txtTotalAmount = createTextField();
        txtTotalTax = createTextField();
        txtFinalTotal = createTextField();

        txtTotalAmount.setEditable(false);
        txtTotalTax.setEditable(false);
        txtFinalTotal.setEditable(false);

        gbc.gridx = 0;
        gbc.gridy = 0;
        fields.add(lblInvoiceId, gbc);
        gbc.gridx = 1;
        fields.add(txtInvoiceId, gbc);

        gbc.gridx = 2;
        fields.add(lblDate, gbc);
        gbc.gridx = 3;
        fields.add(txtInvoiceDate, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        fields.add(lblCustomerId, gbc);
        gbc.gridx = 1;
        fields.add(txtCustomerId, gbc);

        gbc.gridx = 2;
        fields.add(lblTotalAmount, gbc);
        gbc.gridx = 3;
        fields.add(txtTotalAmount, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        fields.add(lblTotalTax, gbc);
        gbc.gridx = 1;
        fields.add(txtTotalTax, gbc);

        gbc.gridx = 2;
        fields.add(lblFinalTotal, gbc);
        gbc.gridx = 3;
        fields.add(txtFinalTotal, gbc);

        panel.add(fields, BorderLayout.CENTER);
        panel.add(createInvoiceButtonPanel(), BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createInvoiceButtonPanel() {
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 0));
        buttons.setBackground(new Color(248, 249, 250));

        JButton btnAdd = createActionButton("Thêm hóa đơn");
        JButton btnUpdate = createActionButton("Sửa hóa đơn");
        JButton btnDelete = createActionButton("Xóa hóa đơn");
        JButton btnReset = createActionButton("Làm mới");

        btnAdd.addActionListener(e -> addInvoice());
        btnUpdate.addActionListener(e -> updateInvoice());
        btnDelete.addActionListener(e -> deleteInvoice());
        btnReset.addActionListener(e -> resetForm());

        buttons.add(btnAdd);
        buttons.add(btnUpdate);
        buttons.add(btnDelete);
        buttons.add(btnReset);

        return buttons;
    }

    private JScrollPane createInvoiceTable() {
        invoiceTableModel.setColumnIdentifiers(new Object[]{"Mã hóa đơn", "Ngày", "Mã KH", "Tổng trước thuế", "Tổng thuế", "Thành tiền"});
        tblInvoices = new JTable(invoiceTableModel);
        tblInvoices.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        tblInvoices.setRowHeight(26);
        tblInvoices.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblInvoices.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (tblInvoices.getSelectedRow() >= 0) {
                    fillInvoiceForm();
                }
            }
        });

        return new JScrollPane(tblInvoices);
    }

    private JPanel createDetailPanel() {
        JPanel panel = new JPanel(new BorderLayout(12, 12));
        panel.setBackground(new Color(248, 249, 250));
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)), "Chi tiết hóa đơn"));

        detailTableModel.setColumnIdentifiers(new Object[]{"Mã chi tiết", "Mã hóa đơn", "Sản phẩm", "Số lượng", "Đơn giá", "Thuế", "Thành tiền"});
        tblDetails = new JTable(detailTableModel);
        tblDetails.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        tblDetails.setRowHeight(24);

        panel.add(new JScrollPane(tblDetails), BorderLayout.CENTER);
        return panel;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209)));
        field.setPreferredSize(new Dimension(200, 32));
        return field;
    }

    private JButton createActionButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        button.setBackground(new Color(76, 175, 80));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 36));
        return button;
    }

    private void loadInvoices() {
        invoiceTableModel.setRowCount(0);
        List<Invoice> invoices = invoiceDAO.getAllInvoices();
        for (Invoice invoice : invoices) {
            invoiceTableModel.addRow(new Object[]{
                    invoice.getInvoiceId(),
                    invoice.getInvoiceDate() != null ? dateFormat.format(invoice.getInvoiceDate()) : "",
                    invoice.getCustomer() != null ? invoice.getCustomer().getCustomer_id() : "",
                    invoice.getTotalAmount(),
                    invoice.getTotalTax(),
                    invoice.getFinalTotal()
            });
        }
    }

    private void fillInvoiceForm() {
        int viewRow = tblInvoices.getSelectedRow();
        if (viewRow < 0) {
            return;
        }
        int modelRow = tblInvoices.convertRowIndexToModel(viewRow);
        txtInvoiceId.setText(invoiceTableModel.getValueAt(modelRow, 0).toString());
        txtInvoiceDate.setText(invoiceTableModel.getValueAt(modelRow, 1).toString());
        txtCustomerId.setText(invoiceTableModel.getValueAt(modelRow, 2).toString());
        txtTotalAmount.setText(invoiceTableModel.getValueAt(modelRow, 3).toString());
        txtTotalTax.setText(invoiceTableModel.getValueAt(modelRow, 4).toString());
        txtFinalTotal.setText(invoiceTableModel.getValueAt(modelRow, 5).toString());

        loadInvoiceDetails(txtInvoiceId.getText().trim());
    }

    private void loadInvoiceDetails(String invoiceId) {
        detailTableModel.setRowCount(0);
        if (invoiceId.isEmpty()) {
            return;
        }
        List<InvoiceDetail> details = detailDAO.getInvoiceDetailsByInvoiceId(invoiceId);
        for (InvoiceDetail detail : details) {
            detailTableModel.addRow(new Object[]{
                    detail.getDetail_id(),
                    detail.getInvoice_id(),
                    detail.getProduct_name(),
                    detail.getQuantity(),
                    detail.getUnit_price(),
                    detail.getTax_rate(),
                    detail.getTotal_price()
            });
        }
    }

    private void addInvoice() {
        if (!validateInvoiceForm()) {
            return;
        }

        Invoice invoice = buildInvoiceFromForm();
        boolean success = invoiceDAO.insertInvoice(invoice);
        if (success) {
            JOptionPane.showMessageDialog(this, "Thêm hóa đơn thành công.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadInvoices();
            resetForm();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm hóa đơn thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateInvoice() {
        if (!validateInvoiceForm()) {
            return;
        }

        Invoice invoice = buildInvoiceFromForm();
        boolean success = invoiceDAO.updateInvoice(invoice);
        if (success) {
            JOptionPane.showMessageDialog(this, "Cập nhật hóa đơn thành công.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadInvoices();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật hóa đơn thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteInvoice() {
        int viewRow = tblInvoices.getSelectedRow();
        if (viewRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn để xóa.", "Chú ý", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = tblInvoices.convertRowIndexToModel(viewRow);
        String invoiceId = invoiceTableModel.getValueAt(modelRow, 0).toString();
        int confirmed = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa hóa đơn này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirmed != JOptionPane.YES_OPTION) {
            return;
        }

        boolean success = invoiceDAO.deleteInvoice(invoiceId);
        if (success) {
            JOptionPane.showMessageDialog(this, "Xóa hóa đơn thành công.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadInvoices();
            resetForm();
            detailTableModel.setRowCount(0);
        } else {
            JOptionPane.showMessageDialog(this, "Xóa hóa đơn thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validateInvoiceForm() {
        if (txtInvoiceId.getText().trim().isEmpty() || txtInvoiceDate.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã hóa đơn và ngày hóa đơn không được để trống.", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try {
            dateFormat.parse(txtInvoiceDate.getText().trim());
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Ngày hóa đơn không hợp lệ. Định dạng phải là yyyy-MM-dd.", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private Invoice buildInvoiceFromForm() {
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(txtInvoiceId.getText().trim());
        try {
            Date date = dateFormat.parse(txtInvoiceDate.getText().trim());
            invoice.setInvoiceDate(date);
        } catch (ParseException e) {
            invoice.setInvoiceDate(new Date());
        }

        Customer customer = new Customer();
        customer.setCustomer_id(txtCustomerId.getText().trim());
        invoice.setCustomer(customer);

        invoice.setTotalAmount(parseDouble(txtTotalAmount.getText().trim()));
        invoice.setTotalTax(parseDouble(txtTotalTax.getText().trim()));
        invoice.setFinalTotal(parseDouble(txtFinalTotal.getText().trim()));

        return invoice;
    }

    private double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    private void filterInvoices() {
        String text = txtSearch.getText().trim();
        if (text.isEmpty()) {
            invoiceSorter.setRowFilter(null);
        } else {
            invoiceSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
    }

    private void resetForm() {
        txtInvoiceId.setText("");
        txtInvoiceDate.setText("");
        txtCustomerId.setText("");
        txtTotalAmount.setText("");
        txtTotalTax.setText("");
        txtFinalTotal.setText("");
        tblInvoices.clearSelection();
    }
}
