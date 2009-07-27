package com.res0w.jwms.method;

import java.util.Calendar;
/**
 *
 * @author lqik2004
 */
public class GetDate {

    static public String getYear() {
        int y = getDateYear;
        String sy = String.valueOf(y);//switch int to string 
        String[] lib = {"200", "20", "2", ""};  //自动补零算法
        sy = lib[sy.length() - 1] + sy;
        return sy;
    }

    static public String getMonth() {
        int m = getDateMonth + 1;
        String sm = String.valueOf(m);
        String[] lib = {"0", ""};
        sm = lib[sm.length() - 1] + sm;
        return sm;
    }

    static public String getDay() {
        int d = getDateDay;
        String sd = String.valueOf(d);
        String[] lib = {"0", ""};
        sd = lib[sd.length() - 1] + sd;
        return sd;
    }

    public static String fixYear(String t) {
        String sy = String.valueOf(t);//switch int to string
        String[] lib = {"200", "20", "2", ""};  //自动补零算法
        sy = lib[sy.length() - 1] + sy;
        return sy;
    }

    static public String fixMonth(String m) {

        String sm = String.valueOf(m);
        String[] lib = {"0", ""};
        sm = lib[sm.length() - 1] + sm;
        return sm;
    }

    static public String fixDay(String d) {
        String sd = String.valueOf(d);
        String[] lib = {"0", ""};
        sd = lib[sd.length() - 1] + sd;
        return sd;
    }

    static public int yearIndex() {
        int year = getDateYear;
        int index = year - 2009;
        return index;
    }

    static public int monthIndex() {
        return getDateMonth;
    }

    static public int dayIndex() {
        return getDateDay - 1;
    }
    static private Calendar cal = Calendar.getInstance();
    static int getDateYear = cal.get(Calendar.YEAR);
    static int getDateMonth = cal.get(Calendar.MONTH);
    static int getDateDay = cal.get(Calendar.DATE);
}
