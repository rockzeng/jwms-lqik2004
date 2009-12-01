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

    public JPanel IdUI(String tag) {
        JPanel panel = new JPanel();
        JTextField ID = new JTextField(12);
        JLabel labelID = new JLabel("编号：");//设置文字
        ID.setEditable(false);//不可修改
        ID.setText(new IDMakeUtil().showID(tag, getDate.getYear(), getDate.getMonth(), getDate.getDay()));   //设置编号，销售单以S开头，这里可能有问题
        ID.setMaximumSize(ID.getPreferredSize());   //使在箱式布局下不会默认取得最大值，保持预定义大小
        Box hbox0 = Box.createHorizontalBox();
        hbox0.add(labelID);
        hbox0.add(ID);
        panel.add(hbox0);
        panel.setMaximumSize(panel.getPreferredSize());
        return panel;
    }

    public String setGetID(String tag, String year, String month, String day) {
        this.tag=tag;
        this.year=year;
        this.month=month;
        this.day=day;
        return alterID(tag);
    }

    public IDMakeUtil() {
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


    public String showID(String x, String initYear, String initMonth, String initDay) {
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
