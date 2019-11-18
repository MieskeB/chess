package nl.michelbijnen.presentation;

import java.sql.*;

public class MySqlConnector {
    private final String host = "michelbijnen.nl";
    private final int port = 3306;
    private final String database = "Chess";
    private final String username = "MieskeB";
    private final String password = "Testtest1";

    private Connection conn;

    protected void open() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?verifyServerCertificate=false&useSSL=false&requireSSL=false";
            this.conn = DriverManager.getConnection(url, this.username, this.password);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected CallableStatement getProcedureStatement(String stmt) {
        try {
            return this.conn.prepareCall(stmt);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected PreparedStatement getStatement(String stmt) {
        try {
            return this.conn.prepareStatement(stmt);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected ResultSet executeQuery(PreparedStatement stmt) {
        try {
            return stmt.executeQuery();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected int executeUpdate(PreparedStatement stmt) {
        try {
            return stmt.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    protected boolean execute(CallableStatement stmt) {
        try {
            return stmt.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    protected void close() {
        try {
            this.conn.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
