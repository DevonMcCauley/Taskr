package utilities;

import java.sql.*;
import java.time.LocalDate;

public class Database {

    private static Connection conn;


    public static void startConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:SqliteDB.db");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeConnection() {
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createTasksTable() {
        Connection conn;
        Statement stmt;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:SqliteDB.db");
            stmt = conn.createStatement();
            String sql = "CREATE TABLE Tasks " + "(task_id INTEGER PRIMARY KEY AUTOINCREMENT,  task_description TEXT NOT NULL,  task_date DATE)";
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ResultSet selectTasksQuery() {
        Statement stmt;
        ResultSet result = null;

        try {
            stmt = conn.createStatement();
            result = stmt.executeQuery("SELECT * FROM Tasks");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ResultSet checkDates() {
        Statement stmt;
        ResultSet result = null;
        LocalDate now = LocalDate.now();
        try {
            stmt = conn.createStatement();
            result = stmt.executeQuery("SELECT * FROM Tasks where task_date <= '" + now +"';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Boolean checkDatabase() {
        DatabaseMetaData md;
        boolean check = false;
        try {
             md = conn.getMetaData();
            ResultSet rs = md.getTables(null, null, "Tasks", null);
            if(rs.next())
            {
                check = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    public static void submitQuery(String query) {
        Statement stmt;
        try {
            stmt = conn.createStatement();

            if (query.toLowerCase().startsWith("select")) {
                stmt.executeQuery(query);
            } else if (query.toLowerCase().startsWith("delete") || query.toLowerCase().startsWith("insert") || query.toLowerCase().startsWith("update")) {
                stmt.executeUpdate(query);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}