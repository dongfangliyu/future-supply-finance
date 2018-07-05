package cn.fintecher.sms.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * Classname 时间处理 Version 1.2
 * 
 * @author panye 2014-7-28 Copyright notice
 */
public class Time {

	public final static String STYLE_1 = "yyyy-MM-dd HH:mm:ss";
	public final static String STYLE_2 = "yyyy-MM-dd";
	public final static String STYLE_3 = "yyyyMMdd";
	public final static String STYLE_4 = "yyyyMMddhh";
	public final static String STYLE_5 = "yyyyMMddhhmm";
	public final static String STYLE_6 = "yyyy年MM月dd日HH时mm分ss秒";
	public final static String STYLE_7 = "yyyy年MM月dd日HH时mm分";
	public final static String STYLE_8 = "yyyy年MM月dd日";
	public final static String STYLE_9 = "hhmmss";
	
	
	
	/**
	 * 
	 *  函数功能说明  得到指定格式的当前时间  panye  2014-7-17   
	 *  修改内容   @param   @return   
	 *  @throws 
	 */
	public static String getCurrentTime(String style) {
		SimpleDateFormat sdf = new SimpleDateFormat(style);
		String str = sdf.format(new Date());
		return str;
	}

	public static String formatTimeNew(String time,String fmtStyle,String wantStyle) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(STYLE_1);
		Date date = sdf.parse(time);
		sdf = new SimpleDateFormat(STYLE_3);
		
		return sdf.format(date);
	}
	
	
	/**
	 * 
	 *  函数功能说明  得到指定格式的当前时间  panye  2014-7-17   修改内容   @param   @return   
	 *  @throws 
	 */
	public static String getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat(STYLE_1);
		String str = sdf.format(new Date());
		return str;
	}

	/**
	 * 
	 *  根据生日求年龄 周岁  panye  2014-6-26   修改内容   @param birthDay  @return  @throws
	 * ParseException       @return int      @throws ParseException
	 */
	public static int getAgeByBirthDay(String birthDay) throws ParseException {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(STYLE_2);
		Date date = sdf.parse(birthDay);
		if (cal.before(date)) {
			return 0;
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(date);

		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				age--;
			}
		} else {
			return 0;
		}

		return age;
	}

	/**
	 * 
	 *  格式化日期函数  panye   2014-6-25   
	 * 修改内容   
	 * @param times 时间  
	 * @param style 样式
	 * @return String      
	 * @throws ParseException
	 */
	public static String formartTimes(String times, String style)
			throws ParseException {

		if (style == null || "".equals(style)) {
			style = STYLE_1;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(style);
		return sdf.format(sdf.parse(times));
	}

	
	public static Date parseDate(String times, String style) throws ParseException{
		if (style == null || "".equals(style)) {
			style = STYLE_1;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(style);
		return sdf.parse(times);
	}
	
	public static String formartTimes(Date date,String style){
		if (style == null || "".equals(style)) {
			style = STYLE_1;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(style);
		return sdf.format(date);
	}
	

	public static int compare_date(String DATE1, String DATE2) {
		int i = 0;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
				i = -1;
			} else if (dt1.getTime() < dt2.getTime()) {
				i = 0;
			} else if (dt1.getTime() == dt2.getTime()) {
				i = 1;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return i;
	}


	// 获取当前月最后一天
	public static Date lastDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.roll(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	public static int[] convertStr(String str) {
		str = str.substring(0, 10);
		String[] strArr = str.split("-");
		int[] result = new int[strArr.length];
		for (int i = 0; i < strArr.length; i++) {
			result[i] = Integer.parseInt(strArr[i]);
		}
		return result;
	}

	public static boolean isWeekOfSaturday(String bDate) throws ParseException {

		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		Date bdate = format1.parse(bDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(bdate);
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
			return true;
		return false;
	}

	public static boolean isWeekOfSunday(String bDate) throws ParseException {

		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		Date bdate = format1.parse(bDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(bdate);
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
			return true;
		return false;
	}

	/**
	 * 获得day天前的时间
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static String[] getBefore(Date date, int day) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		sdf.format(date);
		String[] dates = new String[2];
		dates[0] = sdf.format(now.getTime());
		dates[1] = sdf.format(date);
		return dates;
	}

	/** 获取当前月末日期 */
	public static String getLastDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
		int endday = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		return calendar.get(Calendar.YEAR) + "-"
				+ (calendar.get(Calendar.MONTH) + 1) + "-" + endday;
	}

	public static String formatData(String time) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(time);
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	/**
	 * 
	 *  函数功能说明  得到月初  panye  . 2014-7-31    修改内容   
	 * 
	 * @param   
	 * @return     
	 * @throws 
	 */
	public static String thisMonthBegin() {
		Calendar localTime = Calendar.getInstance();
		String strY = null;
		int x = localTime.get(Calendar.YEAR);
		int y = localTime.get(Calendar.MONTH) + 1;
		strY = y >= 10 ? String.valueOf(y) : ("0" + y);
		return x + "-" + strY + "-01";
	}

	/**
	 * 
	 *  函数功能说明  得到月底日期  panye  2014-7-31   修改内容   @param   @return     @throws 
	 */
	public static String thisMonthEnd() {
		Calendar localTime = Calendar.getInstance();
		String strY = null;
		String strZ = null;
		boolean leap = false;
		int x = localTime.get(Calendar.YEAR);
		int y = localTime.get(Calendar.MONTH) + 1;
		if (y == 1 || y == 3 || y == 5 || y == 7 || y == 8 || y == 10
				|| y == 12) {
			strZ = "31";
		}
		if (y == 4 || y == 6 || y == 9 || y == 11) {
			strZ = "30";
		}
		if (y == 2) {
			leap = leapYear(x);
			if (leap) {
				strZ = "29";
			} else {
				strZ = "28";
			}
		}
		strY = y >= 10 ? String.valueOf(y) : ("0" + y);
		return x + "-" + strY + "-" + strZ;
	}

	public static boolean leapYear(int year) {
		boolean leap;
		if (year % 4 == 0) {
			if (year % 100 == 0) {
				if (year % 400 == 0)
					leap = true;
				else
					leap = false;
			} else
				leap = true;
		} else
			leap = false;
		return leap;
	}

	/**
	 * 得到当前时间的前几天
	 */
	public static String getBeforeTime(Date date, int day) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long date2 = date.getTime() - (day * 1000 * 60 * 60 * 24);
		date.setTime(date2);
		return sdf.format(date);
	}

	/**
	 * 根据当前日期计算出后面几个月的日期 或者 几天的日期
	 * 
	 * @param nowTime
	 * @param type
	 *            区分 日 月 2代表月
	 * @return
	 * @throws ParseException
	 */
	public static String getLastTime(String nowTime, int type, int increment,
			String style) throws ParseException {
		if (style == null || "".equals(style)) {
			style = STYLE_1;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(style);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sdf.parse(nowTime));
		if (type == 2)
			calendar.add(Calendar.MONTH, increment);
		else
			calendar.add(Calendar.DAY_OF_MONTH, increment);

		String sdate = sdf.format(calendar.getTime());
		calendar.setTime(sdf.parse(sdate));
		return sdate;
	}

	/**
	 * 根据当前日期计算出后面几个月的日期 或者 几天的日期
	 * 
	 * @param nowTime
	 * @param type
	 *            区分 日 月 2代表月
	 * @return
	 * @throws ParseException
	 */
	public static String getLastTime1(String nowTime, int type, int increment)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sdf.parse(nowTime));
		if (type == 2)
			calendar.add(Calendar.MONTH, increment);
		else
			calendar.add(Calendar.DAY_OF_MONTH, increment);

		String sdate = sdf.format(calendar.getTime());
		calendar.setTime(sdf.parse(sdate));
		return sdate;
	}

	/**
	 * 
	 *  获取指定格式的日期  panye  2014-6-19   修改内容   @param  style  @param  data
	 *  @return String      @throws Exception
	 */
	public static String formatData(String style, String data) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(style);
		Date date = sdf.parse(data);
		return sdf.format(date);
	}

	/**
	 * 
	 *  函数功能说明  求两个日期之间相隔天数  panye  2014-7-28   修改内容   @param   @return   
	 *  @throws 
	 */
	public static long getBetweenDays(String time1, String time2) {
		long quot = 0;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			quot = date1.getTime() - date2.getTime();
			quot = quot / 1000 / 60 / 60 / 24;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return Math.abs(quot);
	}

	/**
	 * 
	 *  比较两个时间段 的时间差 根据类型 还有 差值   panye  2014-6-30   修改内容   @return     @throws 
	 */
	public static boolean getQuot2(String currentTime, String time2,
			int limitTime) {
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(ft.parse(time2));
			cal.add(Calendar.DAY_OF_MONTH, limitTime);

			long t1 = ft.parse(currentTime).getTime();
			long t2 = cal.getTime().getTime();
			if (t1 - t2 >= 0) {
				return true;
			}

			return false;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 获取i天后的日期
	 * 
	 * @param date
	 * @param i
	 * @return
	 * @throws ParseException
	 */
	public static String getday(String date, int i) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date sDate = sdf.parse(date);
		String time = "";
		sDate.setDate(sDate.getDate() + i);
		time = sdf.format(sDate);
		return time;
	}

	/**
	 * 统计连个日期见的天数
	 * 
	 * @param smdate
	 *            日期1
	 * @param bdate
	 *            日期2
	 * @return int 相隔的天数
	 * */
	public static int daysBetween(String smdate, String bdate)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 比较日期先后顺序
	 * 
	 * @param DATE1
	 *            yyyy-MM-dd
	 * @param DATE2
	 *            yyyy-MM-dd
	 * @return
	 */
	public static int compare_dateymd(String DATE1, String DATE2) {
		int i = 0;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
				i = -1;
			} else if (dt1.getTime() < dt2.getTime()) {
				i = 0;
			} else if (dt1.getTime() == dt2.getTime()) {
				i = 1;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return i;
	}

	/**
	 * 获取i天后的日期 ymd
	 * 
	 * @param date
	 * @param i
	 * @return
	 * @throws ParseException
	 */
	public static String getdayymd(String date, int i) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date sDate = sdf.parse(date);
		String time = "";
		sDate.setDate(sDate.getDate() + i);
		time = sdf.format(sDate);
		return time;
	}

	// 计算两个时间相差的秒数
	public static long getRemainTime(String psorderDeadline, Date date2)
			throws ParseException, java.text.ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date1 = (Date) sdf.parse(psorderDeadline);
		Calendar cal = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal.setTime(date1);
		cal2.setTime(date2);
		long date3 = 0L;
		date3 = (cal.getTimeInMillis() - cal2.getTimeInMillis()) / 1000;
		if (date3 <= 0) {
			date3 = 0L;
		}
		return date3;
	}

	/**
	 * 
	 *  函数功能说明  两个时间是否相差三十分钟，小于三十位false，否则为true  yanchangwei  2014-7-26   修改内容 
	 *  @param currentTime 当前时间  @param errorTime 错误时间  @return  @throws
	 * ParseException       @return boolean      @throws 
	 */
	public static boolean differTime(String currentTime, String errorTime)
			throws ParseException {
		Date date = new SimpleDateFormat(Time.STYLE_1).parse(errorTime);
		long differTime = Time.getRemainTime(
				formartTimes(currentTime, Time.STYLE_1), date);
		long minus = differTime / 60;
		if (minus < 30) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 *  函数功能说明  求两个日期相差天数  panye  2014-7-26   修改内容   @param   @return   
	 *  @throws 
	 */
	public static int diffDays(String smdate, String bdate)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 计算前指定日期的前n天时间
	 * 
	 * @param daytime
	 *            时间，例：2013-01-03
	 * @param temp
	 *            提前的天数
	 * @return string 日期:2013-01-02
	 * */
	public static String getBeforeday(String daytime, int temp) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd HH:mm:ss").parse(daytime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - temp);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c
				.getTime());
		return dayBefore;
	}

	// 由“yyyy-MM-dd HH:mm:ss”时间格式转换为"yyyyMMddHHmmss"格式
	public static String getDateNumber(String aa) throws ParseException {

		Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(aa);
		String dateNumber = new SimpleDateFormat("yyyyMMddHHmmss").format(date);
		return dateNumber;
	}
	
	public static long getSeconds(String date1,String date2)throws ParseException{
		SimpleDateFormat s = new SimpleDateFormat(STYLE_1);
		long t1 = s.parse(date1).getTime();
		long t2 = s.parse(date2).getTime();
		return t2 - t1;
	}
	

	/**
	 * 
	 *  函数功能说明  得到月初  yanchangwei  
	 * 2014-7-28   修改内容          
	 *  @return String    
	 *  @throws 
	 */
	public static String getMonthStart() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		Date strDateFrom = calendar.getTime();
		strDateFrom.setHours(0);
		strDateFrom.setMinutes(0);
		strDateFrom.setSeconds(0);
		return strDateFrom.toLocaleString();
	}

	/**
	 * 
	 *  函数功能说明  得到月末  yanchangwei  2014-7-28   修改内容          @return String    
	 *  @throws 
	 */
	public static String getMonthEnd() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date strDateTo = calendar.getTime();
		strDateTo.setHours(23);
		strDateTo.setMinutes(59);
		strDateTo.setSeconds(59);
		return strDateTo.toLocaleString();
	}

	/**
	 * 
	 * 函数功能说明 				求当前时间的几分钟前的时间	
	 * panye  2014-11-29
	 * 修改内容 
	 * @param 
	 * @return   
	 * @throws 
	 */
	public static String getBeforMinData(int min){
		String currentTime = getCurrentTime();
		Calendar car = Calendar.getInstance();
		Date date = new Date();
		date.setMinutes(date.getMinutes() - min);
		car.setTime(date);
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(car.getTime());
	}
	
	public static void main(String[] args) {
		System.out.println(getBeforMinData(5));
	}

	/**
	 * 
	 *  函数功能说明  两个时间相差距离多少天多少小时多少分多少秒   yanchangwei  2014-8-6 
	 * 
	 * @param str1
	 *            时间参数 1 格式：1990-01-01 12:00:00
	 * @param str2
	 *            时间参数 2 格式：2009-01-01 12:00:00
	 * @return String 返回值为：xx天xx小时xx分  @throws 
	 */
	public static String getDistanceTime(String str1, String str2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date one;
		Date two;
		long day = 0;
		long hour = 0;
		long min = 0;
		try {
			one = df.parse(str1);
			two = df.parse(str2);
			long time1 = one.getTime();
			long time2 = two.getTime();
			if (time2 <= time1)
				return 0 + "天" + 0 + "小时" + 0 + "分";

			long diff = time2 - time1;
			day = diff / (24 * 60 * 60 * 1000);
			hour = (diff / (60 * 60 * 1000) - day * 24);
			min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return day + "天" + hour + "小时" + min + "分";
	}
	public static long getTime(String str) throws ParseException {
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
		Date date = sdf.parse(str);
		return date.getTime();
	}
	
	public static Long getDaysBetween(Date startDate, Date endDate) {  
        Calendar fromCalendar = Calendar.getInstance();  
        fromCalendar.setTime(startDate);  
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);  
        fromCalendar.set(Calendar.MINUTE, 0);  
        fromCalendar.set(Calendar.SECOND, 0);  
        fromCalendar.set(Calendar.MILLISECOND, 0);  
  
        Calendar toCalendar = Calendar.getInstance();  
        toCalendar.setTime(endDate);  
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);  
        toCalendar.set(Calendar.MINUTE, 0);  
        toCalendar.set(Calendar.SECOND, 0);  
        toCalendar.set(Calendar.MILLISECOND, 0);  
  
        return (toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24);  
    } 
	
	
	/**
	 * 
	 * 函数功能说明 			得到当前时间所处的时间段
	 * panye  2014-11-14 
	 * 修改内容 
	 * @param 
	 * @return   
	 * @throws 
	 */
	public static int PeriodOfTime(){
		Calendar calendar =  Calendar.getInstance(); 
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int result = 0;
		//早上
		if(hours >=0 && hours <= 5){
			//凌晨
			result = 1;
		}else if(hours >=6 && hours <= 10){
			//早上
			result = 2;
		}else if(hours >=11 && hours <= 13){
			//中午
			result = 3;
		}else if(hours >=14 && hours <= 18){
			//下午
			result = 4;
		}else if(hours >=19 && hours <= 24){
			//晚上
			result = 5;
		}
		return result;
	}
	
	/**
	 * 
	 * 函数功能说明 			求第二天特定时间（早上七点）
	 * panye  2014-11-29
	 * 修改内容 
	 * @param 
	 * @return   
	 * @throws 
	 */
	@SuppressWarnings("static-access")
	public static String getSpeTomorrow(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(calendar.DATE,1);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(calendar.getTime());
		return dateString + " 07:00:00";
	}
	
	/**
	 * 函数功能说明 				得到几分名后的时间
	 * panye  2014-11-29
	 * 修改内容 
	 * @param 
	 * @return   
	 * @throws 
	 */
	public static String getAfterTime(int minutes){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, minutes);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return formatter.format(calendar.getTime());
	}
	/**
	 * 
	 * 函数功能说明 			返回年份	
	 * panye  2014-11-29
	 * 修改内容 
	 * @param 
	 * @return   
	 * @throws 
	 */
	public static int getCurrentYear(){
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.YEAR);
	}
	
	/**
	 * 
	 * 函数功能说明 			返回月份	
	 * panye  2014-11-29
	 * 修改内容 
	 * @param 
	 * @return   
	 * @throws 
	 */
	public static int getCurrentMonth(){
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MONTH) + 1;
	}
	
	/**
	 * 
	 * 函数功能说明 			返回日	
	 * panye  2014-11-29
	 * 修改内容 
	 * @param 
	 * @return   
	 * @throws 
	 */
	public static int getCurrentDay(){
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DATE);
	}
}
