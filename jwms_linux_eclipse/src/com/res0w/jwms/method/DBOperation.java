package com.res0w.jwms.method;

import java.sql.*;
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
	private String sqlType;

	public DBOperation() {
		sqlType = PropertiesRW.proIDMakeRead("sqltype").toUpperCase();
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
				password = "";
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
			System.err.println("Connection the database is wrong  !!");
		}
	}

	public void DBClosed() {
		try {
			// rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException ex) {
			Logger.getLogger(DBOperation.class.getName()).log(Level.SEVERE,
					null, ex);
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
