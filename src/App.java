package HighlandsCoffee;

import HighlandsCoffee.dao.OrderDAO;
import model.*;
import enums.OrderStatus;

import java.util.Date;
import java.util.List;

public class App {
    public static void main(String[] args) {

        OrderDAO dao = new OrderDAO();

        // ================== 1. INSERT ==================
        System.out.println("=== INSERT ===");

        Order o = new Order();
        o.setOrder_id("O1001");
        o.setOrder_date(new Date());
        o.setTotal_amount(50000);
        o.setOrder_status(OrderStatus.PENDING);

        Staff s = new Staff();
        s.setStaff_id("1");
        o.setStaTableDAO dao = new TableDAO();

        // INSERT
        Table t = new Table("T01", "Bàn 1", 4, TableStatus.TRONG);
        dao.insertTable(t);

        // GET ALL
        for (Table tb : dao.getAllTables()) {
            System.out.println(tb.getTable_id() + " - " + tb.getStatus());
        }

    }
}