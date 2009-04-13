/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jwms;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import method.dbOperation;

/**
 *
 * @author Administrator
 */
public class inputIDMake {

    private String year;
    private String month;
    private String day;
    private String date;
    private String dateTag;

    public void getYear(String x) {
        year = x;
    }

    public void getMonth(String x) {
        month = x;
    }

    public void getDay(String x) {
        day = x;
    }

    private void collectDate() {
        date = year + month + day;
    }

    public String inputMake() {
        collectDate();
        searchDateAndTag();
        return dateTag;
    }

    private void searchDateAndTag() {
        String[] taglib = {"00", "0", ""};//自动补零算法
        ResultSet rs = null;
        String sql = null;
        dbOperation s = new dbOperation();
        s.DBConnect();
        sql = "select distinct tag from timeLine  where date='" + date + "'";
        try {
            try {
                rs = s.DBSqlQuery(sql);
            } catch (SQLException ex) {
                Logger.getLogger(inputIDMake.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (rs.next()) {
                int tagInt = Integer.parseInt(rs.getString(1).trim())+1;
                tag=String.valueOf(tagInt);
                tag = taglib[tag.length()] + tag;
                dbOperation sx = new dbOperation();
                sx.DBConnect();
                sql = "update timeLine set tag='"+tag+"' where date='"+date+"'";
                sx.DBSqlExe(sql);
                sx.DBClosed();
            } else {
                tag = "00";
                dbOperation sx = new dbOperation();
                sx.DBConnect();
                sql = "insert into timeLine(date,tag) values ('" + date + "','" + tag + "')";
                sx.DBSqlExe(sql);
                sx.DBClosed();
            }
            dateTag=date+tag;
            s.DBClosed();
        } catch (SQLException ex) {
            Logger.getLogger(inputIDMake.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public  String tag;
    public  String judge;
}
