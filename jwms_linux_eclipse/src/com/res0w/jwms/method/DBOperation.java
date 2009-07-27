package com.res0w.jwms.method;

import java.sql.*;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class DBOperation {

    private String SQL;
    private Connection conn;
    private Statement stmt;
    private ResultSet rs = null;

    public static void DBcleared(String x){
        
    }

    public void DBConnect() {

        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String url = "jdbc:odbc:jwms";
            String user = "sa";
            String password = "";
            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void DBClosed() {
        try {
            //rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBOperation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet DBSqlQuery(String sql) throws SQLException {
        SQL = sql;
        rs = stmt.executeQuery(sql);
        return rs;
    }

    public void DBSqlExe(String sql) throws SQLException {
        SQL = sql;
        boolean r;
        r = stmt.execute(sql);

    }

    public ResultSet DBReturnRS() {
        return rs;
    }
}

