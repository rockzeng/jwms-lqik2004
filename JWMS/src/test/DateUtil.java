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

import java.util.Calendar;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 提供了一些关于时间的工具，包括了获取当前日期，获取一个包含了选择时间的控件，以panel
 * 形式返回
 * @author res0w
 * @since 2009-12-1
 * @version 0.1
 */
public class DateUtil implements DateUI{

    static private Calendar cal = Calendar.getInstance();
    static int getDateYear = cal.get(Calendar.YEAR);
    static int getDateMonth = cal.get(Calendar.MONTH);
    static int getDateDay = cal.get(Calendar.DATE);
    private Object[] Objyear = {
        "2009", "2010", "2011", "2012"
    };
    private Object[] Objmonth = {
        "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"
    };
    private Object[] Objday = {
        "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"
    };
    private JComboBox JYear = new JComboBox(Objyear);//增加了自动选择时间功能
    private JComboBox JMonth = new JComboBox(Objmonth);
    private JComboBox JDay = new JComboBox(Objday);

    public JPanel DateSelectionUI() {
        JPanel panel = new JPanel();

        JLabel label1 = new JLabel("日期：");
        JYear.setSelectedItem(getYear());
        JLabel label2 = new JLabel("年");
        JMonth.setSelectedItem(getMonth());
        JLabel label3 = new JLabel("月");
        JDay.setSelectedItem(getDay());
        JLabel label4 = new JLabel("日");
        JYear.setMaximumSize(JYear.getPreferredSize());
        JMonth.setMaximumSize(JMonth.getPreferredSize());
        JDay.setMaximumSize(JDay.getPreferredSize());
        Box hbox1 = Box.createHorizontalBox();
        hbox1.add(label1);
        hbox1.add(JYear);
        hbox1.add(label2);
        hbox1.add(JMonth);
        hbox1.add(label3);
        hbox1.add(JDay);
        hbox1.add(label4);
        panel.add(hbox1);
        panel.setMaximumSize(panel.getPreferredSize());//保证生成panel大小和组件一致
        return panel;
    }

    /**
     * 使用它就可以简单的制作出时间组合选单
     * @param isLabel 如果isLabel为真，即设置标签，则进行判断选择
     * @return 返回一个包含了基本选择时间日期控件的panel
     */
    public JPanel DateSelectionUI(boolean isLabel) {
        JPanel panel = new JPanel();

        JLabel label1 = new JLabel("日期：");
        JYear.setSelectedItem(getYear());
        JLabel label2 = new JLabel("年");
        JMonth.setSelectedItem(getMonth());
        JLabel label3 = new JLabel("月");
        JDay.setSelectedItem(getDay());
        JLabel label4 = new JLabel("日");
        JYear.setMaximumSize(JYear.getPreferredSize());
        JMonth.setMaximumSize(JMonth.getPreferredSize());
        JDay.setMaximumSize(JDay.getPreferredSize());
        Box hbox1 = Box.createHorizontalBox();
        hbox1.add(Box.createHorizontalStrut(5));
        /**
         * 如果isLabel，即设置标签，则进行判断选择
         */
        if (isLabel) {
            hbox1.add(label1);
        }
        hbox1.add(JYear);
        hbox1.add(label2);
        hbox1.add(JMonth);
        hbox1.add(label3);
        hbox1.add(JDay);
        hbox1.add(label4);
        hbox1.add(Box.createHorizontalGlue());
        panel.add(hbox1);
        return panel;
    }

    /**
     * 获得所选择的年份
     * @return 返回选择的年份
     */
    public String getSelectionYear() {
        return JYear.getSelectedItem().toString().trim();
    }

    /**
     * 获得所选择的月份
     * @return 返回选择的月份
     */
    public String getSelectionMonth() {
        return JMonth.getSelectedItem().toString().trim();
    }

    /**
     * 获得所选择的日期
     * @return 返回选择的日期
     */
    public String getSelectionDay() {
        return JDay.getSelectedItem().toString().trim();
    }

     public String getSelectionDate() {
        return getSelectionYear()+getSelectionMonth()+getSelectionDay();
    }

    /**
     * 静态方法，可直接调用。用来返回当前年份。并固定格式为四位
     * @return 返回当前年份
     * @issue 在3000年时会出现BUG
     */
    static public String getYear() {
        int y = getDateYear;
        String sy = String.valueOf(y);//switch int to string
        String[] lib = {"200", "20", "2", ""};  //自动补零算法
        sy = lib[sy.length() - 1] + sy;
        return sy;
    }

    /**
     * 静态方法，可直接调用。用来返回当前月份。并固定格式为两位
     * @return 返回当前月份
     */
    static public String getMonth() {
        int m = getDateMonth + 1;
        String sm = String.valueOf(m);
        String[] lib = {"0", ""};
        sm = lib[sm.length() - 1] + sm;
        return sm;
    }

    /**
     * 静态方法，可直接调用。用来返回当前日期。并固定格式为两位
     * @return 返回当前日期
     */
    static public String getDay() {
        int d = getDateDay;
        String sd = String.valueOf(d);
        String[] lib = {"0", ""};
        sd = lib[sd.length() - 1] + sd;
        return sd;
    }

    /**
     * 可以把年份转换成四位标准格式
     * @param t 输入待修复的年份
     * @return 返回标准格式年份
     * @issue 由到3000年出现的BUG
     */
    public static String fixYear(String t) {
        String sy = String.valueOf(t);//switch int to string
        String[] lib = {"200", "20", "2", ""};  //自动补零算法
        sy = lib[sy.length() - 1] + sy;
        return sy;
    }

    /**
     * 可以把月份转换成两位标准格式
     * @param m 输入待修复的月份
     * @return 返回标准格式月份
     */
    static public String fixMonth(String m) {

        String sm = String.valueOf(m);
        String[] lib = {"0", ""};
        sm = lib[sm.length() - 1] + sm;
        return sm;
    }

    /**
     * 可以把日期转换成两位标准格式
     * @param d 输入待修复的日期
     * @return 返回标准格式日期
     */
    static public String fixDay(String d) {
        String sd = String.valueOf(d);
        String[] lib = {"0", ""};
        sd = lib[sd.length() - 1] + sd;
        return sd;
    }

    /**
     *
     * @deprecated 即将删除
     */
    static public int yearIndex() {
        int year = getDateYear;
        int index = year - 2009;
        return index;
    }

    /**
     *
     * @deprecated 即将删除
     */
    static public int monthIndex() {
        return getDateMonth;
    }

    /**
     *
     * @deprecated 即将删除
     */
    static public int dayIndex() {
        return getDateDay - 1;
    }


}
