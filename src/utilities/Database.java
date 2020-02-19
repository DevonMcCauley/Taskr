package utilities;

import javax.xml.transform.Result;
import java.sql.*;

public class Database {

    private static Connection conn;


    public static void startConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:SqliteDB.db");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Database successfully connected");
    }

    public static void closeConnection() {
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Database successfully closed");

    }

    public static void createTasksTable() {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:SqliteDB.db");
            System.out.println("Database Opened...\n");
            stmt = conn.createStatement();
            String sql = "CREATE TABLE Tasks " + "(task_id INTEGER PRIMARY KEY AUTOINCREMENT," + " task_name TEXT NOT NULL, " + " task_description TEXT NOT NULL) ";
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Table Product Created Successfully!");
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

    public static Boolean checkDatabase() {
        DatabaseMetaData md = null;
        Boolean check = false;
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
        ResultSet result;
        try {
            stmt = conn.createStatement();

            if (query.toLowerCase().startsWith("select")) {
                result = stmt.executeQuery(query);
            } else if (query.toLowerCase().startsWith("delete") || query.toLowerCase().startsWith("insert") || query.toLowerCase().startsWith("update")) {
                stmt.executeUpdate(query);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}