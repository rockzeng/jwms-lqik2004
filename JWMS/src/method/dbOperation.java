package method;

import java.sql.*;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class dbOperation {

    private String SQL;
    private Connection conn;
    private Statement stmt;
    private ResultSet rs = null;
    private String sqlType;

    public dbOperation() {
        sqlType = propertiesRW.proIDMakeRead("sqltype").toUpperCase();
    }

    public static void DBcleared(String x) {
    }

    public void DBConnect() {
        String url;
        String user;
        String password;
        try {
            if ("MYSQL".equals(sqlType)) {
                url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=gb2312";
                user = "root";
                password = "881010";
                Class.forName("com.mysql.jdbc.Driver");
            } else {
                Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                url = "jdbc:odbc:jwms";
                user = "sa";
                password = "";
            }
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
            Logger.getLogger(dbOperation.class.getName()).log(Level.SEVERE, null, ex);
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
    public void exeIntProcude(int first,int second){
        try {
            CallableStatement c = conn.prepareCall("{call timecache(?,?)}");
            c.setInt(1, first);
            c.setInt(2, second);
            c.execute();
        } catch (SQLException ex) {
            Logger.getLogger(dbOperation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

