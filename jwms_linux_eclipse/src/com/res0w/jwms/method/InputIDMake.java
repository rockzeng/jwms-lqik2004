package com.res0w.jwms.method;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lqik2004
 */

public class InputIDMake {

    public String tag;
    public String judge;
    private  String year;
    private  String month;
    private  String day;
    public String date;
    public String dateTag;


    public void getYear(String x) {
        year = x;
    }

    public void getMonth(String x) {
        month = x;
    }

    public void getDay(String x) {
        day = x;
    }
    //生成日期方法

    private void collectDate() {
        date = year + month + day;
    }
    //最有用的一个公用类

    public String showID(String x,String initYear,String initMonth,String initDay) {
        year=initYear;
        month=initMonth;
        day=initDay;
        collectDate();
        searchDateAndTag();
        return x+dateTag;
    }
     public String alterID(String x) {

        collectDate();
        alterDateTag(date,tag);
        return x+dateTag;
    }

    private void searchDateAndTag() {
        String[] taglib = {"00", "0", ""};//自动补零算法
        ResultSet rs = null;
        String sql = null;
        DBOperation s = new DBOperation();
        s.DBConnect();
        sql = "select distinct tag from timeLine  where date='" + date + "'";   //根据时间进行检索
        try {
            try {
                rs = s.DBSqlQuery(sql);
            } catch (SQLException ex) {
                Logger.getLogger(InputIDMake.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (rs.next()) {
                int tagInt = Integer.parseInt(rs.getString(1).trim()) + 1;      //取得tag值，并生成新的TAG
                tag = String.valueOf(tagInt);
                tag = taglib[tag.length()] + tag;   //生成标准格式的TAG
            } else {
                tag = "00";
            }
            dateTag = date + tag;
            s.DBClosed();
        } catch (SQLException ex) {
            Logger.getLogger(InputIDMake.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     private void alterDateTag(String alterDate,String alterTag) {
        String[] taglib = {"00", "0", ""};//自动补零算法
        ResultSet rs = null;
        String sql = null;
        DBOperation s = new DBOperation();
        s.DBConnect();
        sql = "select distinct tag from timeLine  where date='" + alterDate + "'";   //根据时间进行检索
        try {
            try {
                rs = s.DBSqlQuery(sql);
            } catch (SQLException ex) {
                Logger.getLogger(InputIDMake.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (rs.next()) {
                int tagInt = Integer.parseInt(rs.getString(1).trim()) + 1;      //取得tag值，并生成新的TAG
                alterTag = String.valueOf(tagInt);
                alterTag = taglib[alterTag.length()] + alterTag;   //生成标准格式的TAG
                DBOperation sx = new DBOperation();
                sx.DBConnect();
                sql = "update timeLine set tag='" + alterTag + "' where date='" + alterDate + "'";
                sx.DBSqlExe(sql);
                sx.DBClosed();
            } else {
                alterTag = "00";
                DBOperation sx = new DBOperation();
                sx.DBConnect();
                sql = "insert into timeLine(date,tag) values ('" + alterDate + "','" + alterTag + "')";
                sx.DBSqlExe(sql);
                sx.DBClosed();
            }
            //dateTag = date + tag;
            s.DBClosed();
        } catch (SQLException ex) {
            Logger.getLogger(InputIDMake.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}