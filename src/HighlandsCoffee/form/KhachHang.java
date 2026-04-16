package HighlandsCoffee.form;

import HighlandsCoffee.dao.CustomerDAO;
import HighlandsCoffee.enums.MembershipTier;
import HighlandsCoffee.model.Customer;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class KhachHang extends JPanel {

    private final CustomerDAO customerDAO = new CustomerDAO();
    private final DefaultTableModel tableModel = new DefaultTableModel();
    private final TableRowSorter<DefaultTableModel> rowSorter;

    private JTextField txtCustomerId;
    private JTextField txtFullName;
    private JTextField txtPhoneNumber;
    private JTextField txtRewardPoints;
    private JComboBox<MembershipTier> cmbMembershipTier;
    private JTextField txtSearch;
    private JTable tblCustomers;

    public KhachHang() {
        setLayout(new BorderLayout());
        setBackground(new Color(248, 249, 250));
        initComponents();
        rowSorter = new TableRowSorter<>(tableModel);
        tblCustomers.setRowSorter(rowSorter);
        loadCustomers();
    }

    private void initComponents() {
        add(createHeaderPanel(), BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        mainPanel.add(createFormPanel(), BorderLayout.NORTH);
        mainPanel.add(createTablePanel(), BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(238, 238, 238));
        header.setPreferredSize(new Dimension(100, 60));

        JLabel title = new JLabel("QUẢN LÝ KHÁCH HÀNG", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.add(title, BorderLayout.CENTER);

        return header;
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(12, 12));

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(new Color(248, 249, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblCustomerId = new JLabel("Mã khách hàng:");
        JLabel lblFullName = new JLabel("Họ và tên:");
        JLabel lblPhone = new JLabel("Số điện thoại:");
        JLabel lblPoints = new JLabel("Điểm thưởng:");
        JLabel lblTier = new JLabel("Hạng thành viên:");

        txtCustomerId = createTextField();
        txtFullName = createTextField();
        txtPhoneNumber = createTextField();
        txtRewardPoints = createTextField();
        cmbMembershipTier = new JComboBox<>(MembershipTier.values());
        cmbMembershipTier.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(lblCustomerId, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtCustomerId, gbc);

        gbc.gridx = 2;
        inputPanel.add(lblFullName, gbc);
        gbc.gridx = 3;
        inputPanel.add(txtFullName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(lblPhone, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtPhoneNumber, gbc);

        gbc.gridx = 2;
        inputPanel.add(lblPoints, gbc);
        gbc.gridx = 3;
        inputPanel.add(txtRewardPoints, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(lblTier, gbc);
        gbc.gridx = 1;
        inputPanel.add(cmbMembershipTier, gbc);

        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(createButtonPanel(), BorderLayout.SOUTH);
        panel.add(createSearchPanel(), BorderLayout.NORTH);

        return panel;
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        panel.setBackground(new Color(248, 249, 250));

        JLabel lblSearch = new JLabel("Tìm kiếm:");
        lblSearch.setFont(new Font("Segoe UI", Font.BOLD, 18));
        txtSearch = createTextField();
        txtSearch.setPreferredSize(new Dimension(260, 32));
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { filterCustomers(); }
            public void removeUpdate(DocumentEvent e) { filterCustomers(); }
            public void changedUpdate(DocumentEvent e) { filterCustomers(); }
        });

        panel.add(lblSearch);
        panel.add(txtSearch);
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 0));
        panel.setBackground(new Color(248, 249, 250));

        JButton btnAdd = createActionButton("Thêm");
        JButton btnEdit = createActionButton("Sửa");
        JButton btnDelete = createActionButton("Xóa");
        JButton btnReset = createActionButton("Làm mới");

        btnAdd.addActionListener(e -> addCustomer());
        btnEdit.addActionListener(e -> updateCustomer());
        btnDelete.addActionListener(e -> deleteCustomer());
        btnReset.addActionListener(e -> resetForm());

        panel.add(btnAdd);
        panel.add(btnEdit);
        panel.add(btnDelete);
        panel.add(btnReset);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(248, 249, 250));

        tableModel.setColumnIdentifiers(new Object[]{"Mã KH", "Họ tên", "SĐT", "Điểm", "Hạng"});
        tblCustomers = new JTable(tableModel);
        tblCustomers.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        tblCustomers.setRowHeight(28);
        tblCustomers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblCustomers.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    fillFormFromTable();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tblCustomers);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209)));
        field.setPreferredSize(new Dimension(220, 32));
        return field;
    }

    private JButton createActionButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        button.setBackground(new Color(76, 175, 80));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(110, 36));
        return button;
    }

    private void loadCustomers() {
        tableModel.setRowCount(0);
        List<Customer> customers = customerDAO.getAllCustomers();
        for (Customer customer : customers) {
            tableModel.addRow(new Object[]{
                    customer.getCustomer_id(),
                    customer.getFullName(),
                    customer.getPhone_number(),
                    customer.getReward_points(),
                    customer.getMembership_tier() != null ? customer.getMembership_tier().name() : ""
            });
        }
    }

    private void addCustomer() {
        if (txtCustomerId.getText().trim().isEmpty() || txtFullName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã khách hàng và họ tên không được để trống.", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Customer customer = buildCustomerFromForm();
        boolean success = customerDAO.addCustomer(customer);
        if (success) {
            JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadCustomers();
            resetForm();
        } else {
            JOptionPane.showMessageDialog(this, "Không thể thêm khách hàng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateCustomer() {
        if (tblCustomers.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để sửa.", "Chú ý", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Customer customer = buildCustomerFromForm();
        boolean success = customerDAO.updateCustomer(customer);
        if (success) {
            JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thành công.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadCustomers();
        } else {
            JOptionPane.showMessageDialog(this, "Không thể cập nhật khách hàng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteCustomer() {
        int selectedRow = tblCustomers.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để xóa.", "Chú ý", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String customerId = tblCustomers.getValueAt(tblCustomers.convertRowIndexToModel(selectedRow), 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa khách hàng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        boolean success = customerDAO.deleteCustomer(customerId);
        if (success) {
            JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadCustomers();
            resetForm();
        } else {
            JOptionPane.showMessageDialog(this, "Không thể xóa khách hàng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetForm() {
        txtCustomerId.setText("");
        txtFullName.setText("");
        txtPhoneNumber.setText("");
        txtRewardPoints.setText("0");
        cmbMembershipTier.setSelectedItem(MembershipTier.DONG);
        tblCustomers.clearSelection();
    }

    private void fillFormFromTable() {
        int selectedRow = tblCustomers.getSelectedRow();
        if (selectedRow < 0) {
            return;
        }

        int modelRow = tblCustomers.convertRowIndexToModel(selectedRow);
        txtCustomerId.setText(tableModel.getValueAt(modelRow, 0).toString());
        txtFullName.setText(tableModel.getValueAt(modelRow, 1).toString());
        txtPhoneNumber.setText(tableModel.getValueAt(modelRow, 2).toString());
        txtRewardPoints.setText(tableModel.getValueAt(modelRow, 3).toString());
        String tier = tableModel.getValueAt(modelRow, 4).toString();
        cmbMembershipTier.setSelectedItem(MembershipTier.valueOf(tier));
    }

    private Customer buildCustomerFromForm() {
        Customer customer = new Customer();
        customer.setCustomer_id(txtCustomerId.getText().trim());
        customer.setFullName(txtFullName.getText().trim());
        customer.setPhone_number(txtPhoneNumber.getText().trim());

        try {
            customer.setReward_points(Integer.parseInt(txtRewardPoints.getText().trim()));
        } catch (NumberFormatException ex) {
            customer.setReward_points(0);
        }

        customer.setMembership_tier((MembershipTier) cmbMembershipTier.getSelectedItem());
        return customer;
    }

    private void filterCustomers() {
        String searchTerm = txtSearch.getText().trim();
        if (searchTerm.isEmpty()) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchTerm));
        }
    }
}
