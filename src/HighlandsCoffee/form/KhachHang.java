package HighlandsCoffee.form;

import HighlandsCoffee.dao.CustomerDAO;
import HighlandsCoffee.model.Customer;
import HighlandsCoffee.enums.MembershipTier;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class KhachHang extends JPanel {

    private JTable tableKhachHang;
    private DefaultTableModel tableModel;
    private JTextField txtMaKH, txtTenKH, txtSDT, txtDiemThuong;
    private JComboBox<String> cbHangThanhVien;
    private JButton btnThemMoi, btnCapNhat, btnXoa, btnLamMoi;

    private CustomerDAO dao = new CustomerDAO();

    public KhachHang() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        // ==========================================
        // 1. PHẦN NHẬP LIỆU (Top)
        // ==========================================
        JPanel pnlInput = new JPanel(new GridLayout(3, 4, 10, 15));
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin Khách Hàng"));
        pnlInput.setOpaque(false);

        pnlInput.add(new JLabel("  Mã KH:"));
        txtMaKH = new JTextField();
        pnlInput.add(txtMaKH);

        pnlInput.add(new JLabel("  Tên KH:"));
        txtTenKH = new JTextField();
        pnlInput.add(txtTenKH);

        pnlInput.add(new JLabel("  SĐT:"));
        txtSDT = new JTextField();
        pnlInput.add(txtSDT);

        pnlInput.add(new JLabel("  Điểm Thưởng:"));
        txtDiemThuong = new JTextField();
        pnlInput.add(txtDiemThuong);

        pnlInput.add(new JLabel("  Hạng Thành Viên:"));
        String[] hang = {"DONG", "BAC", "VANG"};
        cbHangThanhVien = new JComboBox<>(hang);
        pnlInput.add(cbHangThanhVien);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(pnlInput, BorderLayout.NORTH);
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(topPanel, BorderLayout.NORTH);

        // ==========================================
        // 2. PHẦN BẢNG DỮ LIỆU (Center)
        // ==========================================
        String[] columns = {"Mã KH", "Tên KH", "SĐT", "Điểm Thưởng", "Hạng"};
        tableModel = new DefaultTableModel(columns, 0);
        tableKhachHang = new JTable(tableModel);
        tableKhachHang.setRowHeight(30);
        tableKhachHang.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(tableKhachHang);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // ==========================================
        // 3. PHẦN NÚT BẤM (Bottom)
        // ==========================================
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        pnlButtons.setOpaque(false);

        btnThemMoi = new JButton("Thêm Mới");
        btnCapNhat = new JButton("Cập Nhật");
        btnXoa = new JButton("Xóa");
        btnLamMoi = new JButton("Làm Mới");

        btnThemMoi.setBackground(new Color(46, 204, 113)); btnThemMoi.setForeground(Color.WHITE);
        btnCapNhat.setBackground(new Color(52, 152, 219)); btnCapNhat.setForeground(Color.WHITE);
        btnXoa.setBackground(new Color(231, 76, 60)); btnXoa.setForeground(Color.WHITE);

        btnThemMoi.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCapNhat.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnXoa.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLamMoi.setFont(new Font("Segoe UI", Font.BOLD, 14));

        btnThemMoi.setFocusPainted(false); btnThemMoi.setBorderPainted(false); btnThemMoi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCapNhat.setFocusPainted(false); btnCapNhat.setBorderPainted(false); btnCapNhat.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnXoa.setFocusPainted(false); btnXoa.setBorderPainted(false); btnXoa.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLamMoi.setFocusPainted(false); btnLamMoi.setBorderPainted(false); btnLamMoi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        pnlButtons.add(btnThemMoi);
        pnlButtons.add(btnCapNhat);
        pnlButtons.add(btnXoa);
        pnlButtons.add(btnLamMoi);
        add(pnlButtons, BorderLayout.SOUTH);

        loadDataToTable();
        setupActions();
        clearForm();
    }

    private void loadDataToTable() {
        tableModel.setRowCount(0);
        List<Customer> list = dao.getAllCustomers();
        for (Customer c : list) {
            String hang = (c.getMembership_tier() != null) ? c.getMembership_tier().name() : "DONG";
            Object[] row = {
                c.getCustomer_id(),
                c.getFullName(),
                c.getPhone_number(),
                c.getReward_points(),
                hang
            };
            tableModel.addRow(row);
        }
    }

    private void clearForm() {
        String nextId = dao.generateCustomerId();
        txtMaKH.setText(nextId);
        txtMaKH.setEditable(false);

        txtTenKH.setText("");
        txtSDT.setText("");
        txtDiemThuong.setText("0");
        cbHangThanhVien.setSelectedIndex(0);
        tableKhachHang.clearSelection();
    }

    private Customer getCustomerFromForm() throws NumberFormatException {
        Customer c = new Customer();
        c.setCustomer_id(txtMaKH.getText().trim());
        c.setFullName(txtTenKH.getText().trim());
        c.setPhone_number(txtSDT.getText().trim());
        c.setReward_points(Integer.parseInt(txtDiemThuong.getText().trim()));

        String hang = (String) cbHangThanhVien.getSelectedItem();
        c.setMembership_tier(MembershipTier.valueOf(hang));
        return c;
    }

    private void setupActions() {
        // CLICK BẢNG
        tableKhachHang.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tableKhachHang.getSelectedRow();
                if (row >= 0) {
                    txtMaKH.setText(tableKhachHang.getValueAt(row, 0).toString());
                    txtTenKH.setText(tableKhachHang.getValueAt(row, 1).toString());
                    txtSDT.setText(tableKhachHang.getValueAt(row, 2).toString());
                    txtDiemThuong.setText(tableKhachHang.getValueAt(row, 3).toString());
                    String hang = tableKhachHang.getValueAt(row, 4).toString();
                    cbHangThanhVien.setSelectedItem(hang);
                    txtMaKH.setEditable(false);
                }
            }
        });

        // NÚT THÊM MỚI
        btnThemMoi.addActionListener(e -> {
            try {
                Customer c = getCustomerFromForm();
                if (dao.addCustomer(c)) {
                    JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    loadDataToTable();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi khi thêm khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // NÚT CẬP NHẬT
        btnCapNhat.addActionListener(e -> {
            try {
                Customer c = getCustomerFromForm();
                if (dao.updateCustomer(c)) {
                    JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    loadDataToTable();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // NÚT XÓA
        btnXoa.addActionListener(e -> {
            int selectedRow = tableKhachHang.getSelectedRow();
            if (selectedRow >= 0) {
                String id = tableKhachHang.getValueAt(selectedRow, 0).toString();
                int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa khách hàng " + id + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    if (dao.deleteCustomer(id)) {
                        JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        loadDataToTable();
                        clearForm();
                    } else {
                        JOptionPane.showMessageDialog(this, "Lỗi khi xóa khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });

        // NÚT LÀM MỚI
        btnLamMoi.addActionListener(e -> {
            loadDataToTable();
            clearForm();
        });
    }
}
