package HighlandsCoffee.form;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import HighlandsCoffee.DBConnection; 
public class DangNhap extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DangNhap.class.getName());
    public DangNhap() {
        initComponents();
        setLocationRelativeTo(null);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtTen = new javax.swing.JTextField();
        btnDangNhap = new javax.swing.JButton();
        btnThoat = new javax.swing.JButton();
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Tên đăng nhập");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Mật khẩu");

        txtTen.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        btnDangNhap.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnDangNhap.setText("Đăng nhập");
        btnDangNhap.addActionListener(this::btnDangNhapActionPerformed);

        btnThoat.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnThoat.setText("Thoát");
        btnThoat.addActionListener(this::btnThoatActionPerformed);

        jPasswordField1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HighlandsCoffee/images/Login.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(btnDangNhap)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnThoat)
                .addGap(46, 46, 46))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel4)
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDangNhap)
                    .addComponent(btnThoat))
                .addContainerGap(105, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>                        

    private void btnDangNhapActionPerformed(java.awt.event.ActionEvent evt) {                                            
        String username=txtTen.getText().trim();
        String password = new String(jPasswordField1.getPassword()); 
        if(username.isEmpty()||password.isEmpty()){
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin");
            return;
        }
        try {
        Connection conn = DBConnection.getConnection();

        if(conn == null){
            JOptionPane.showMessageDialog(this, "Không kết nối được database!");
            return;
        }

        String sql = "SELECT u.Role, u.Staff_id, n.Full_name " +
                     "FROM Users u " +
                     "JOIN NhanVien n ON u.Staff_id = n.Staff_id " +
                     "WHERE u.Username=? AND u.Password=?";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, username);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            String role = rs.getString("Role");
            String staffId = rs.getString("Staff_id");
            String fullname=rs.getString("Full_name");

            JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");

            TrangChu frm = new TrangChu(role, staffId);
            frm.setLoginContext(fullname, role);
            frm.setVisible(true);
            frm.setLocationRelativeTo(null);

            this.dispose(); // đóng login
        } else {
            JOptionPane.showMessageDialog(this, "Sai tài khoản hoặc mật khẩu!");
        }

        rs.close();
        ps.close();
        conn.close();

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Lỗi kết nối!");
    }
    }                                           

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {                                         
  
        this.dispose();
    }                                        

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new DangNhap().setVisible(true));
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton btnDangNhap;
    private javax.swing.JButton btnThoat;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField txtTen;
    // End of variables declaration                   
}
