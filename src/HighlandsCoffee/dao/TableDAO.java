package HighlandsCoffee.dao;

import HighlandsCoffee.model.Table;
import HighlandsCoffee.enums.TableStatus;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TableDAO extends DBContext {

    // 1. Lấy tất cả bàn
    public List<Table> getAllTables() {
        List<Table> list = new ArrayList<>();
        String sql = "SELECT * FROM TableCoffee";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Table t = new Table();

                t.setTable_id(rs.getString("Table_id"));
                t.setTable_number(rs.getString("Table_number"));
                t.setSeat(rs.getInt("Seat"));
                t.setStatus(TableStatus.valueOf(rs.getString("Table_status")));

                list.add(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 2. Lấy theo ID
    public Table getTableById(String id) {
        String sql = "SELECT * FROM TableCoffee WHERE Table_id=?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Table t = new Table();

                t.setTable_id(rs.getString("Table_id"));
                t.setTable_number(rs.getString("Table_number"));
                t.setSeat(rs.getInt("Seat"));
                t.setStatus(TableStatus.valueOf(rs.getString("Table_status")));

                return t;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // 3. Insert
    public boolean insertTable(Table t) {
        String sql = "INSERT INTO TableCoffee VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, t.getTable_id());
            ps.setString(2, t.getTable_number());
            ps.setInt(3, t.getSeat());
            ps.setString(4, t.getStatus().name());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 4. Update
    public boolean updateTable(Table t) {
        String sql = "UPDATE TableCoffee SET Table_number=?, Seat=?, Table_status=? WHERE Table_id=?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, t.getTable_number());
            ps.setInt(2, t.getSeat());
            ps.setString(3, t.getStatus().name());
            ps.setString(4, t.getTable_id());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 5. Delete
    public boolean deleteTable(String id) {
        String sql = "DELETE FROM TableCoffee WHERE Table_id=?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}