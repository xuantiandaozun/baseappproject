package com.system.baseapplibrary.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

    private static Calendar mCalendar = Calendar.getInstance(); // 当前时间
    private static SimpleDateFormat mFormatDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat mFormatDateTimeShort = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 获取当前日期
     *
     * @return
     */
    public static Date getDate() {
        mCalendar = Calendar.getInstance(); // 当前时间
        Date mNowDate = mCalendar.getTime();
        return mNowDate;
    }
    /**
     * 获取时间是上午还是下午
     * @return
     */
    public static String getTimeStatue(){
        Date d = new Date();
        String text=null;
        if(d.getHours()<11){
            text="上午";
        }else if(d.getHours()<13){
            text="中午";
        }else if(d.getHours()<18){
            text="下午";
        }else if(d.getHours()<24){
            text="晚上";
        }
        return text;

    }

    /**
     * 获取当前日期String类型
     *
     * @return
     */
    public static String getDateString() {
        mCalendar = Calendar.getInstance(); // 当前时间
        Date mNowDate = mCalendar.getTime();
        mFormatDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return mFormatDateTime.format(mNowDate);
    }

    public static String getDateStringByCalendar(Calendar cal) {
        Date mNowDate = cal.getTime();
        mFormatDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return mFormatDateTime.format(mNowDate);
    }

    public static String getDateStringByCalendar(Calendar cal, String format) {
        mFormatDateTime = new SimpleDateFormat(format);
        Date mNowDate = cal.getTime();
        return mFormatDateTime.format(mNowDate);
    }

    /**
     * 获取当前日期String类型
     *
     * @param format
     * @return
     */
    public static String getDateString(String format) {
        mFormatDateTime = new SimpleDateFormat(format);
        mCalendar = Calendar.getInstance(); // 当前时间
        Date mNowDate = mCalendar.getTime();
        return mFormatDateTime.format(mNowDate);
    }

    public static String getDateString(Date date, String format) {
        mFormatDateTime = new SimpleDateFormat(format);
        mCalendar = Calendar.getInstance(); // 当前时间
        return mFormatDateTime.format(date);
    }
    public static String getDay(String time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date=null;
        try {
             date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DATE);
        return String.valueOf(day);
    }
    public static int getMonth(String time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date=null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.MONTH);
        return day;
    }
    public static int getYear(String time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date=null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.YEAR);
        return day;
    }
    public static int getCurrentYear(){
        Date date = getDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int yeay = calendar.get(Calendar.YEAR);
        return yeay;
    }
    public static int getCurrentMonth(){
        Date date = getDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);
        return month;
    }
    public static int getCurrentDay(){
        Date date = getDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DATE);
        return day;
    }
    /**
     * 判断是否是闰年
     */
    public static boolean isLeapYear(int year){
        if (year % 100 == 0 && year % 400 == 0){
            return true;
        }else if (year % 100 != 0 && year % 4==0){
            return true;
        }
        return false;
    }
    public static int getDaysOfMonth(boolean isLeapYear,int month){
        int days=0;
        switch (month){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                days=31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                days=30;
                break;
            case 2:
                if (isLeapYear){
                    days=29;
                }else{
                    days=28;
                }
        }
        return days;
    }
    public static String getFristData(){
        SimpleDateFormat dateFormater = new SimpleDateFormat(
                "yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.getTime();
       return dateFormater.format(cal.getTime()) + "";
    }

    /**
     * 获取当前日期String类型
     *
     * @return
     */
    public static String getDateStringShort() {
        mCalendar = Calendar.getInstance(); // 当前时间
        Date mNowDate = mCalendar.getTime();
        return mFormatDateTimeShort.format(mNowDate);
    }

    /**
     * 格式化MySql日期
     *
     * @param newString
     * @return
     */
    public static String getDateFromMySql(String newString) {
        Date mNowDate = new Date(Long.parseLong(newString) * 1000);
        return mFormatDateTime.format(mNowDate);

    }

    public static String getDateStringByAddDays(int days) {
        mCalendar = Calendar.getInstance(); // 当前时间
        mCalendar.add(Calendar.DAY_OF_MONTH, days);
        Date mNowDate = mCalendar.getTime();
        return mFormatDateTimeShort.format(mNowDate);
    }

    public static String getDateStringByAddDays(Date sDate, int days) {

        // 将开始时间赋给日历实例
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTime(sDate);
        // 计算结束时间
        mCalendar.add(Calendar.DATE, days);
        // 返回Date类型结束时间
        Date mNowDate = mCalendar.getTime();
        return mFormatDateTimeShort.format(mNowDate);

    }

    /**
     * 格式化Json日期【待修改】
     *
     * @param json
     * @return
     */
    public static String getDateFromJson(String json) {

        String newString = json.replace("/Date(", "").replace(")/", "");
        long now = Long.parseLong(newString);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(now);
        Date mNowDate = calendar.getTime();
        return mFormatDateTime.format(mNowDate);

    }

    /**
     * 格式化日期字符串
     *
     * @param format
     * @param time
     * @return
     */
    public static String formatDateString(String format, String time) {
        String str = time;
        SimpleDateFormat formatDate = new SimpleDateFormat(format); // "MM-dd HH:mm"
        Date date = null;
        try {
            date = formatDate.parse(time);
            str = formatDate.format(date);
        } catch (ParseException e) {

        }

        return str;

    }

    public static String formatDateString(String format, String fromat2, String time) {
        String str = time;
        SimpleDateFormat formatDate = new SimpleDateFormat(format); // "MM-dd HH:mm"
        SimpleDateFormat formatDate2 = new SimpleDateFormat(fromat2); // "MM-dd HH:mm"
        Date date = null;
        try {
            str = formatDate2.format(formatDate.parse(time));
        } catch (ParseException e) {
            // e.printStackTrace();
        }

        return str;

    }

    /**
     * 取得两个时间段的时间间隔 return t2 与t1的间隔分钟 throws ParseException
     * 如果输入的日期格式不是0000-00-00 格式抛出异常
     */
    public static int getBetweenMinutes(String t1, String t2) {
        // DateFormat format1 = new SimpleDateFormat("yyyy/MM/dd H:m:s");
        int iBetweenMinutes = 0;
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m:s");
            Date d1 = format.parse(t1);
            Date d2 = format.parse(t2);

            long iBetween = d2.getTime() - d1.getTime();

            iBetweenMinutes = (int) (iBetween / 1000 / 60);

        } catch (ParseException ex) {
            iBetweenMinutes = 0;
        }
        return iBetweenMinutes;
    }

    public static int getBetweenMinutesByFormat(String t1, String t2, String format) {
        // DateFormat format1 = new SimpleDateFormat("yyyy/MM/dd H:m:s");
        int iBetweenMinutes = 0;
        try {
            DateFormat f = new SimpleDateFormat(format);
            Date d1 = f.parse(t1);
            Date d2 = f.parse(t2);

            long iBetween = d2.getTime() - d1.getTime();

            iBetweenMinutes = (int) (iBetween / 1000 / 60);

        } catch (ParseException ex) {
            iBetweenMinutes = 0;
        }
        return iBetweenMinutes;
    }

    public static int getBetweenMill(String t1, String t2) {
        // DateFormat format1 = new SimpleDateFormat("yyyy/MM/dd H:m:s");
        int iBetweenMinutes = 0;
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m:s");
            Date d1 = format.parse(t1);
            Date d2 = format.parse(t2);

            long iBetween = d2.getTime() - d1.getTime();

            iBetweenMinutes = (int) (iBetween);

        } catch (ParseException ex) {
            iBetweenMinutes = 0;
        }
        return iBetweenMinutes;
    }

    /**
     * 格式化日期
     *
     * @param minutes
     * @return
     */
    public static String GetDayHourMinutes(String minutes) {
        String result = "";
        long nowMinutes = Long.parseLong(minutes);

        // 按照传入的格式生成一个simpledateformate对象
        long nd = 24 * 60;// 一天的毫秒数
        long nh = 60;// 一小时的毫秒数

        try {
            // 获得两个时间的毫秒时间差异<br />
            long day = 0;
            long hour = 0;
            long min = 0;
            if ((nowMinutes / nd) != 0) {
                day = nowMinutes / nd;
                nowMinutes = nowMinutes - day * nd;
            }

            if (nowMinutes != 0 && (nowMinutes / nh) != 0) {
                hour = nowMinutes / nh;

                nowMinutes = nowMinutes - hour * nh;
            }

            if (nowMinutes != 0) {
                min = nowMinutes;
            }

            if (day != 0) {
                result = String.valueOf(day) + "天";
            }

            if (day != 0) {
                result += String.valueOf(hour) + "小时";
            } else {
                result = String.valueOf(hour) + "小时";
            }
            result += String.valueOf(min) + "分钟";

        } catch (Exception e) {
            e.printStackTrace();
            result = "";
        }

        return result;

    }

    /**
     * 字符串转日期
     *
     * @param date
     * @return
     */
    public static Date setStringToDate(String date) {
        Date formatDate = getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            formatDate = sdf.parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return formatDate;

    }

    public static Date setStringToDate(String date, String format) {
        Date formatDate = getDate();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            formatDate = sdf.parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return formatDate;

    }

    public static boolean isBeforeDate(String date1, String date2) {
        boolean flag = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date mDate1, mDate2;
        try {
            mDate1 = sdf.parse(date1);
            mDate2 = sdf.parse(date2);
            flag = mDate1.before(mDate2);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return flag;

    }

    private static final long ONE_MINUTE = 60000L;

    private static final long ONE_HOUR = 3600000L;

    private static final long ONE_DAY = 86400000L;

    private static final long ONE_WEEK = 604800000L;

    private static final String ONE_SECOND_AGO = "秒前";

    private static final String ONE_MINUTE_AGO = "分钟前";

    private static final String ONE_HOUR_AGO = "小时前";

    private static final String ONE_DAY_AGO = "天前";

    private static final String ONE_MONTH_AGO = "月前";

    private static final String ONE_YEAR_AGO = "年前";


    public static String getDateAgo(String value) {
        Date date = setStringToDate(value, "yyyy-MM-dd HH:mm");
        long delta = getDate().getTime() - date.getTime();
        if (delta < 1L * ONE_MINUTE) {
            long seconds = toSeconds(delta);
            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
        }
        if (delta < 45L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
        }
        if (delta < 48L * ONE_HOUR) {
            return String.format("%s", formatDateString("yyyy-MM-dd HH:mm", "MM月dd日 HH时mm分", value));
        }
        return value;
    }

    public static String getToadayOrDate(String value) {
        Date date = setStringToDate(value, "yyyy-MM-dd HH:mm");
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

        long delta = getDate().getTime() - date.getTime();

        if (delta < 24L * ONE_HOUR) {
            return "今天 " + formatter.format(date);
        }
        if (delta < 48L * ONE_HOUR) {
            return "昨天 " + formatter.format(date);
        }
        return value;
    }

    public static String getMeeingDateAgo(String value) {
        Date date = setStringToDate(value, "yyyy-MM-dd HH:mm");
        long delta = date.getTime() - getDate().getTime();

        if (delta < 0) {
            return "0天";
        }
        if (delta < 1L * ONE_MINUTE) {
            long seconds = toSeconds(delta);
            return (seconds <= 0 ? 1 : seconds) + "秒";
        }
        if (delta < 45L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + "分钟";
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + "小时";
        }
        return (delta / (24L * ONE_HOUR)) + "天";
    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }

    private static long toYears(long date) {
        return toMonths(date) / 365L;
    }


}
