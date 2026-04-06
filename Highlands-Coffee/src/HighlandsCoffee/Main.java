package HighlandsCoffee;

import java.sql.Connection;
import HighlandsCoffee.form.DangNhap;
public class Main {
    public static void main(String[] args) {
        Connection conn = DBConnection.getConnection();

        if (conn != null) {
            System.out.println("Ket noi thanh cong");
            java.awt.EventQueue.invokeLater(()->{
                DangNhap login=new DangNhap();
                login.setVisible(true);
                login.setLocationRelativeTo(null);
            });
        } else {
            System.out.println("Ket noi that bai");
        }
    }
}
