package method;


import java.util.Calendar;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Administrator
 */
public class getDate {

    static public String getYear() {
        int y = getDateYear;
        String sy = String.valueOf(y);//switch int to string 
        return sy;
    }

    static public String getMonth() {
        int m = getDateMonth + 1;
        String sm = String.valueOf(m);
        return sm;
    }

    static public String getDay() {
        int d = getDateDay;
        String sd = String.valueOf(d);
        return sd;
    }
    static public int yearIndex(){
        int year=getDateYear;
        int index=year-2009;
        return index;
    }
    static public int monthIndex(){
       return getDateMonth;
    }
    static public int dayIndex(){
        return getDateDay-1;
    }
    static private Calendar cal = Calendar.getInstance();
    static private int getDateYear = cal.get(Calendar.YEAR);
    static private int getDateMonth = cal.get(Calendar.MONTH);
    static private int getDateDay = cal.get(Calendar.DATE);
}
