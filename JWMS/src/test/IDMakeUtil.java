/*
# JWMS2 is based on JWMS.JWMS is short for JeanWest store-sell Management System
# Copyright (C) 2009,res0w
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package test;

import method.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author res0w
 * @since 2009-12-1
 */
public class IDMakeUtil implements IdUI {

    public String tag;
    public String judge;
    private String year;
    private String month;
    private String day;
    public String date;
    public String dateTag;
    private JPanel panel = new JPanel();
    private JTextField ID = new JTextField(12);

    public JPanel IdUI(String tag) {
        JLabel labelID = new JLabel("编号：");
        ID.setEditable(false);//不可修改
        ID.setText(showID(tag, getDate.getYear(), getDate.getMonth(), getDate.getDay()));
        ID.setMaximumSize(ID.getPreferredSize());
        Box hbox0 = Box.createHorizontalBox();
        hbox0.add(labelID);
        hbox0.add(ID);
        panel.add(hbox0);
        panel.setMaximumSize(panel.getPreferredSize());
        return panel;
    }

    /**
     * 此方法可以同时返回String格式的完整ID编号，同时也会写入数据库，但不会修改UI显示，谨慎使用。
     * @param tag 写入单据tag，如销售单据为'S'
     * @param year 年份
     * @param month 月份
     * @param day 日
     * @return 返回完整ID
     * @deprecated 请谨慎使用，下个版本可能删除
     */
    public String setGetID(String tag, String year, String month, String day) {
        this.tag = tag;
        this.year = year;
        this.month = month;
        this.day = day;
        return alterID(tag);
    }
    /**
     * 修改IDUI显示
     * @param tag 写入单据tag，如销售单据为'S'
     * @param year 年份
     * @param month 月份
     * @param day 日
     */
    public void setUIId(String tag, String year, String month, String day) {
        ID.setText(showID(tag, year, month, day));
    }

    public void setYear(String x) {
        year = x;
    }

    public void setMonth(String x) {
        month = x;
    }

    public void setDay(String x) {
        day = x;
    }
    //生成日期方法

    private void collectDate() {
        date = year + month + day;
    }
    //最有用的一个公用类

    private String showID(String x, String initYear, String initMonth, String initDay) {
        year = initYear;
        month = initMonth;
        day = initDay;
        collectDate();
        searchDateAndTag();
        return x + dateTag;
    }

    public String alterID(String x) {

        collectDate();
        alterDateTag(date, tag);
        return x + dateTag;
    }

    private void searchDateAndTag() {
        String[] taglib = {"00", "0", ""};//自动补零算法
        ResultSet rs = null;
        String sql = null;
        dbOperation s = new dbOperation();
        s.DBConnect();
        sql = "select distinct tag from timeLine  where date='" + date + "'";   //根据时间进行检索
        try {
            try {
                rs = s.DBSqlQuery(sql);
            } catch (SQLException ex) {
                Logger.getLogger(IDMakeUtil.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(IDMakeUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void alterDateTag(String alterDate, String alterTag) {
        String[] taglib = {"00", "0", ""};//自动补零算法
        ResultSet rs = null;
        String sql = null;
        dbOperation s = new dbOperation();
        s.DBConnect();
        sql = "select distinct tag from timeLine  where date='" + alterDate + "'";   //根据时间进行检索
        try {
            try {
                rs = s.DBSqlQuery(sql);
            } catch (SQLException ex) {
                Logger.getLogger(IDMakeUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (rs.next()) {
                int tagInt = Integer.parseInt(rs.getString(1).trim()) + 1;      //取得tag值，并生成新的TAG
                alterTag = String.valueOf(tagInt);
                alterTag = taglib[alterTag.length()] + alterTag;   //生成标准格式的TAG
                dbOperation sx = new dbOperation();
                sx.DBConnect();
                sql = "update timeLine set tag='" + alterTag + "' where date='" + alterDate + "'";
                sx.DBSqlExe(sql);
                sx.DBClosed();
            } else {
                alterTag = "00";
                dbOperation sx = new dbOperation();
                sx.DBConnect();
                sql = "insert into timeLine(date,tag) values ('" + alterDate + "','" + alterTag + "')";
                sx.DBSqlExe(sql);
                sx.DBClosed();
            }
            dateTag = date + alterTag;
            s.DBClosed();
        } catch (SQLException ex) {
            Logger.getLogger(IDMakeUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
