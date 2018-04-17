package com.edu.wf.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Session;
/**
 * 时间格式转化工具类
 * @author wangfei
 *
 */
public abstract class TimeUtils {

	public final static int YEAR = Calendar.YEAR;
	public final static int MONTH = Calendar.MONTH;
	public final static int DAY = Calendar.DATE;
	public final static int HOUR = Calendar.HOUR;
	public final static int HOUR_OF_DAY = Calendar.HOUR_OF_DAY;
	public final static int MINUTE = Calendar.MINUTE;
	public final static int SECOND = Calendar.SECOND;

	public final static String DATE_TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
	public final static String TIME_FORMAT_3 = "HH:mm:ss";
	public final static String TIME_FORMAT_2 = "HH:mm";
	/** 系统数据库的时间，统一存储本格式，可对精度进行调整 yyyy-MM-dd HH:mm:ss */
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_TIME_FORMAT_14 = "yyyyMMddHHmmss";
	public static final String DATE_TIME_FORMAT_17 = "yyyyMMddHHmmssSSS";
	public static final String DATE_FORMAT_4 = "yyyy";
	public static final String DATE_FORMAT_7 = "yyyy-MM";
	public static final String DATE_FORMAT_8 = "yyyyMMdd";
	public final static String DATE_FORMAT_10 = "yyyy-MM-dd";

	public static final String DATE_TIME_CHINA_FORMATE = "yyyy年M月d日 H:mm";
	public static final String DATE_TIME_CHINA_FORMATE_9 = "yyyy年M月d日";
	public static final String DATE_TIME_CHINA_FORMATE_11 = "yyyy年MM月dd日";
	
	/**
	 * 获得上个月日期
	 * 格式为:yyyy-MM
	 * @param date
	 * @return
	 */
	public static String getLastMonth() {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_7);
		Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -1);
        return sdf.format(cal.getTime());
    }
	
	/**
	 * 获得指定月份的上个月日期
	 * 格式为:yyyy-MM
	 * @param date
	 * @return
	 */
	public static String getLastMonth(String month) {
		DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = dateFormat1.parse(month + "-01");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("月份：" + month + ",格式转化异常");
		}
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_7);
//		Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -1);
        return sdf.format(cal.getTime());
    }
	
	/**
	 * 指定实体，判断人指定【日期】范围是否有重复
 	 * @param startDate 开始日期 格式为：DATE_FORMAT_10 = "yyyy-MM-dd"
	 * @param endDate 结束日期 格式为：DATE_FORMAT_10 = "yyyy-MM-dd"
	 * @param creatorId
	 * @param fieldNameOfStartDate 实体对应的字段名 开始日期
	 * @param fieldNameOfEndDate 实体对应的字段名 结束日期
	 * @param fieldNameOfCreatorId 实体对应的字段名 创建人id
	 * @param entityName 实体名称(全名，包括包路径)
	 * @param hibernateSession
	 * @param id 如果有id，会去掉id的数据。适用在对要修改的数据的判断
	 * @return
	 */
	public static boolean hasDateRangeInDbByCreatorId(String startDate, String endDate, String creatorId, String fieldNameOfStartDate, 
			String fieldNameOfEndDate, String fieldNameOfCreatorId, String entityName, Session hibernateSession, String id){
		String hsql = "select count(e) from " + entityName + " as e "
				+ " where "
				+ " e."+fieldNameOfCreatorId+"= '"+creatorId+"' and "
			    + " ("
			            + " (e."+fieldNameOfStartDate+"<='"+startDate+"' and e."+fieldNameOfEndDate+">='"+startDate+"') "
			    		+ " or "
			    		+ " (e."+fieldNameOfStartDate+"<='"+endDate+"' and e."+fieldNameOfEndDate+">='"+endDate+"') "
			    + " ) ";
		if (!StringUtils.isEmpty(id)) {
			hsql = hsql + " and e.id != '"+id+"'";
		}
		System.out.println("------------hsql = " + hsql);
		int count = Integer.parseInt(hibernateSession.createQuery(hsql).uniqueResult() + "");
		if(count > 0){
			return true;
		}
		return false;
	}
	
	/**
	 * 指定实体，判断人指定【时间】范围是否有重复
	 * 只支持时间范围为一天内的判断
 	 * @param date 日期 格式为：DATE_FORMAT_10 = "yyyy-MM-dd"
	 * @param startTime 开始时间 格式为：TIME_FORMAT_2 = "HH:mm";
	 * @param endTime 结束时间 格式为：TIME_FORMAT_2 = "HH:mm";
	 * @param creatorId 数据创建人id
	 * @param fieldNameOfDate 实体对应的字段名 日期
	 * @param fieldNameOfStartTime 实体对应的字段名 开始时间
	 * @param fieldNameOfEndTime 实体对应的字段名 结束时间
	 * @param fieldNameOfCreatorId 实体对应的字段名 创建人id
	 * @param entityName 实体名称(全名，包括包路径)
	 * @param hibernateSession
	 * @param id 如果有id，会去掉id的数据。适用在对要修改的数据的判断
	 * @return
	 */
	public static boolean hasTimeRangeInDbByCreatorId(String date, String startTime, String endTime, String creatorId, String fieldNameOfDate, 
			String fieldNameOfStartTime, String fieldNameOfEndTime, String fieldNameOfCreatorId, String entityName, Session hibernateSession, 
			String id){
		String hsql = "select count(e) from " + entityName + " as e "
				+ " where "
				+ " e."+fieldNameOfCreatorId+"= '"+creatorId+"' and "
				+ " e."+fieldNameOfDate+"= '"+date+"' and "
			    + " ("
			            + " (e."+fieldNameOfStartTime+"<='"+startTime+"' and e."+fieldNameOfEndTime+">='"+startTime+"') "
			    		+ " or "
			    		+ " (e."+fieldNameOfStartTime+"<='"+endTime+"' and e."+fieldNameOfEndTime+">='"+endTime+"') "
			    + " ) ";
		if (!StringUtils.isEmpty(id)) {
			hsql = hsql + " and e.id != '"+id+"'";
		}
		System.out.println("------------hsql = " + hsql);
		int count = Integer.parseInt(hibernateSession.createQuery(hsql).uniqueResult() + "");
		if(count > 0){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断日期是否是周末
	 * @param dateStr
	 * @param formate
	 * @return
	 */
	public static boolean isWeekend(String dateStr, String formate) {
		DateFormat format1 = new SimpleDateFormat(formate);
		Date bdate = null;
		try {
			bdate = format1.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("判断日期是否是周末时异常");
		} 
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(bdate);
	    if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY||cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
	    	return true;
	    }else{
	    	return false;
	    }
	}
	
	/**
	 * 根据年 月 获取对应的月份 天数
	 * monthStr的格式必须是：yyyy-MM
	 * */
	public static int getDaysByYearMonth(String monthStr) {
		String[] ss = monthStr.split("-");
		int year = Integer.valueOf(ss[0]);
		int month = Integer.valueOf(ss[1]);
		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
		
	}

	/** 在date日期基础上，增加时间
	 * @param date yyyy-MM-dd HH:mm:ss格式的日期
	 * @param addTime HH:mm:ss或HH:mm格式的时间
	 * @return */
	public static Date addTime(String date, String addTime) {
		Date d = convert(date, DATE_TIME_FORMAT);

		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.HOUR_OF_DAY, getHour(addTime));// 24小时制
		cal.add(Calendar.MINUTE, getMinute(addTime));
		cal.add(Calendar.SECOND, getSecond(addTime));
		return cal.getTime();
	}

	/** 获得time的小时
	 * @param time 格式为：HH:mm:ss或HH:mm
	 * @return */
	public static int getHour(String time) {
		String[] arr = time.split(":");
		return Integer.valueOf(arr[0]);
	}

	/** 获得time的分钟
	 * @param time 格式为：HH:mm:ss或HH:mm
	 * @return */
	public static int getMinute(String time) {
		String[] arr = time.split(":");
		return Integer.valueOf(arr[1]);
	}

	/** 获得time的秒
	 * @param time 格式为：HH:mm:ss
	 * @return */
	public static int getSecond(String time) {
		String[] arr = time.split(":");
		if (arr.length >= 3) {
			return Integer.valueOf(arr[2]);
		} else {
			return 0;
		}
	}

	/** 
	 * 判断是否是时间格式
	 * @param str
	 * @param timeFormate 如：HH:mm 或 yyyy-MM-dd HH:mm:ss
	 * @return */
	public static boolean isValidTime(String str, String timeFormate) {
		if (StringUtils.isEmpty(str) || str.length() != timeFormate.length()) {
			//如果不这样判断。2014-12-01 == 2014-12-1。所以先判断是否与格式的长度一致
			return false;
		}
		boolean convertSuccess = true;
		SimpleDateFormat format = new SimpleDateFormat(timeFormate);
		try {
			format.setLenient(false);
			format.parse(str);
		} catch (Exception e) {
			// 如果throw java.text.Exception或者NullPointerRuntimeException，就说明格式不对
			convertSuccess = false;
		}
		return convertSuccess;
	}
	
	/**
	 * 判断日期格式是否是：2014.1.23，或则2014-1-23，或则2014/1/23
	 * @param str
	 * @param s 分割符合
	 * @return
	 */
	public static boolean isValidTimeOfyyyyMdd(String str, String s){
		Pattern p = Pattern.compile("\\d{4}" + s + "([1-9]|1[0-2])" + s + "([1-9]|[1-2][0-9]|3[0-1])");
		Matcher m = p.matcher(str);
        return m.matches();
	}
	
	/** 判断2个日期，第一个是否早于第二个。 方法认为传入的时间参数格式要相同 方式只支持格式：yyyy-MM-dd HH:mm:ss
	 * @param oneDate
	 * @param twoDate
	 * @param equalIsTrue true=如果两个日期相等也返回true
	 * @return */
	public static boolean isEarlyDateTime(String oneDate, String twoDate, boolean equalIsTrue) {
		boolean isEarly = false;
		DateFormat df = null;
		df = new SimpleDateFormat(DATE_TIME_FORMAT);

		try {
			Date dt1 = df.parse(oneDate);
			Date dt2 = df.parse(twoDate);
			if (equalIsTrue) {
				if (dt1.getTime() <= dt2.getTime()) {
					isEarly = true;
				}
			} else {
				if (dt1.getTime() < dt2.getTime()) {
					isEarly = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("时间比较出现异常");
		}
		System.out.println("=========isEarly=" + isEarly);
		return isEarly;
	}

	/** 判断2个日期，第一个是否早于第二个。 方法认为传入的时间参数格式要相同 方式只支持格式：yyyy-MM-dd
	 * @param oneDate
	 * @param twoDate
	 * @param equalIsTrue true=如果两个日期相等也返回true
	 * @return */
	public static boolean isEarlyDate(String oneDate, String twoDate, boolean equalIsTrue) {
		boolean isEarly = false;
		DateFormat df = null;
		df = new SimpleDateFormat(DATE_FORMAT_10);

		try {
			Date dt1 = df.parse(oneDate);
			Date dt2 = df.parse(twoDate);
			if (equalIsTrue) {
				if (dt1.getTime() <= dt2.getTime()) {
					isEarly = true;
				}
			} else {
				if (dt1.getTime() < dt2.getTime()) {
					isEarly = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("时间比较出现异常");
		}
		System.out.println("=========isEarly=" + isEarly);
		return isEarly;
	}

	/** 判断2个时间，第一个是否早于第二个。 方法认为传入的时间参数格式要相同 方式只至此一下格式：HH:mm:ss 或 HH:mm
	 * @param oneTime
	 * @param twoTime
	 * @param equalIsTrue true=如果两个时间相等也返回true
	 * @return */
	public static boolean isEarlyTime(String oneTime, String twoTime, boolean equalIsTrue) {
		boolean isEarly = false;
		String date = "1800-12-12 ";
		DateFormat df = null;
		if (isValidTime(oneTime, TIME_FORMAT_2) && isValidTime(twoTime, TIME_FORMAT_2)) {
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		} else if (isValidTime(oneTime, TIME_FORMAT_3) && isValidTime(twoTime, TIME_FORMAT_3)) {
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} else {
			throw new RuntimeException("时间参数格式不正确,不能进行时间比较。oneTime="+oneTime+";twoTime="+twoTime);
		}
		try {
			Date dt1 = df.parse(date + oneTime);
			Date dt2 = df.parse(date + twoTime);
			if (equalIsTrue) {
				if (dt1.getTime() <= dt2.getTime()) {
					isEarly = true;
				}
			} else {
				if (dt1.getTime() < dt2.getTime()) {
					isEarly = true;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("时间比较出现异常");
		}
		return isEarly;
	}

	/** 获取当前日期 yyyy-MM-dd HH:mm:ss
	 * @return */
	public static String getCurrentTime() {
		return format(new Date(), DATE_TIME_FORMAT);
	}

	/** 获取当前日期 yyyy-MM-dd */
	public static String getCurrentDate() {
		return format(new Date(), DATE_FORMAT_10);
	}
	
	/** 获取当前日期 格式为dateFormate */
	public static String getCurrentDate(String dateFormate) {
		return format(new Date(), dateFormate);
	}

	/** 明天的日期，yyyy-MM-dd */
	public static String getNextDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		DateFormat fmt = new SimpleDateFormat(DATE_FORMAT_10);
		Date d = cal.getTime();
		return fmt.format(d);
	}
	
	/**
	 * 获得指定日期的下一天
	 * @param dateStr
	 * @return
	 * @throws Exception 
	 */
	public static String getNextDate(String dateStr) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new SimpleDateFormat(TimeUtils.DATE_FORMAT_10).parse(dateStr));
		cal.add(Calendar.DATE, 1);
		DateFormat fmt = new SimpleDateFormat(DATE_FORMAT_10);
		Date d = cal.getTime();
		return fmt.format(d);
	}

	/** 检查非空且严格按照yyyy-MM-dd格式，否则抛异常 <br/>zhp20150804 */
	public static void assertDate10(String s) throws RuntimeException{
		if (StringUtils.isEmpty(s)) {
			throw new RuntimeException("日期不能为空！");
		}
		DateFormat fmt = new SimpleDateFormat(TimeUtils.DATE_FORMAT_10);
		try {
			Date d = fmt.parse(s);
			//parse方法会把"1998-2-322"成功转换成"1998-12-19"，但我们应该报错
			if (!fmt.format(d).equals(s)) {
				throw new RuntimeException("请使用2015-06-01这样的日期格式！");
			}
		} catch (Exception e) {
//			e.printStackTrace();
			throw new RuntimeException("不符合要求的日期:‘" + s + "’。请使用2015-06-01这样的日期格式！");
		}
	}

	/** 获取当前日期
	 * @return */
	public static String getCurrentTime(String format) {
		return format(new Date(), format);
	}

	/** 获取当天开始时间
	 * @return */
	public static String getDayStartTime(String timeStr) {
		Date date = convert(timeStr, DATE_TIME_FORMAT);
		Calendar calendar = setHourMinToZero(date);
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);// 可以方便地修改期格式
		return dateFormat.format(calendar.getTime());
	}

	/** 获取该日的第二天的开始时间
	 * @param date
	 * @return */
	public static String getTomorrowStartTime(String timeStr) {
		Date date = convert(timeStr, DATE_TIME_FORMAT);
		Calendar calendar = setHourMinToZero(date);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);// 可以方便地修改期格式
		return dateFormat.format(calendar.getTime());
	}

	private static Calendar setHourMinToZero(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR, 0);
		return calendar;
	}

	/** 获得本周一的日期
	 * @param timeStr
	 * @return */
	public static String getMondayOfWeek(String timeStr) {
		Date date = convert(timeStr, DATE_TIME_FORMAT);
		Calendar calendar = setHourMinToZero(date);
		int mondayPlus = getMondayPlus(calendar);
		calendar.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = calendar.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
		return dateFormat.format(monday);
	}

	/* 获得本周星期日的日期 */
	public static String getMondyOfNextWeek(String timeStr) {
		Date date = convert(timeStr, DATE_TIME_FORMAT);
		Calendar calendar = setHourMinToZero(date);
		int mondayPlus = getMondayPlus(calendar);
		calendar.add(GregorianCalendar.DATE, mondayPlus + 7);
		Date monday = calendar.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
		return dateFormat.format(monday);
	}

	// 计算当月最后一天,返回字符串
	public String getDefaultDay() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
		lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/** 获取当月第一天
	 * @return */
	public static String getFirstDayOfMonth(String timeStr, String format, String returnFormat) {
		Date date = convert(timeStr, format);
		Calendar calendar = setHourMinToZero(date);
		calendar.set(Calendar.DATE, 1);// 设为当前月的1号
		SimpleDateFormat dateFormat = new SimpleDateFormat(returnFormat);
		return dateFormat.format(calendar.getTime());
	}

	/** 获取下个月第一天
	 * @return */
	public static String getFirstDayOfNextMonth(String timeStr, String format, String returnFormat) {
		Date date = convert(timeStr, format);
		Calendar calendar = setHourMinToZero(date);
		calendar.set(Calendar.DATE, 1);// 设为当前月的1号
		calendar.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
		SimpleDateFormat dateFormat = new SimpleDateFormat(returnFormat);
		return dateFormat.format(calendar.getTime());
	}
	
	/**
	 * 获取指定日期的上一周日期
	 * @param timeStr
	 * @return
	 */
	public static String getLastWeek(String timeStr){
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(new SimpleDateFormat(TimeUtils.DATE_FORMAT_10).parse(timeStr));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("参数格式异常。正确格式为：yyyy-MM-dd");
		}
		//过去七天
		cal.add(Calendar.DATE, - 7);
        Date d = cal.getTime();
        String day = new SimpleDateFormat(TimeUtils.DATE_FORMAT_10).format(d);
//        System.out.println("七天前："+day);
        return day;
	}
	
	/**
	 * 获取指定日期的下一周日期
	 * @param timeStr
	 * @return
	 */
	public static String getNextWeek(String timeStr){
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(new SimpleDateFormat(TimeUtils.DATE_FORMAT_10).parse(timeStr));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("参数格式异常。正确格式为：yyyy-MM-dd");
		}
		//过去七天
		cal.add(Calendar.DATE, 7);
        Date d = cal.getTime();
        String day = new SimpleDateFormat(TimeUtils.DATE_FORMAT_10).format(d);
//        System.out.println("七天后："+day);
        return day;
	}
	
	/**
	 * 获取指定日期的上一年
	 * @param timeStr 格式 2017-01-01
	 * @return
	 */
	public static String getLastYear(String timeStr){
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(new SimpleDateFormat(TimeUtils.DATE_FORMAT_10).parse(timeStr));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("参数格式异常。正确格式为：yyyy-MM-dd");
		}
		//过去七天
//		cal.setTime(new Date());
		cal.add(Calendar.YEAR, -1);
        Date d = cal.getTime();
        String day = new SimpleDateFormat(TimeUtils.DATE_FORMAT_10).format(d);
//        System.out.println("过去七天："+day);
        return day;
	}
	
	/**
	 * 获取指定日期的下一年
	 * @param timeStr 格式 2017-01-01
	 * @return
	 */
	public static String getNextYear(String timeStr){
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(new SimpleDateFormat(TimeUtils.DATE_FORMAT_10).parse(timeStr));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("参数格式异常。正确格式为：yyyy-MM-dd");
		}
		//过去七天
//		cal.setTime(new Date());
		cal.add(Calendar.YEAR, 1);
        Date d = cal.getTime();
        String day = new SimpleDateFormat(TimeUtils.DATE_FORMAT_10).format(d);
//        System.out.println("过去七天："+day);
        return day;
	}
	
	/** 
	 * 获取当周7天
	 * 基于中国习惯，周一为第一天
	 * @return */
	public static String[] getFirstToLastDayOfWeek(String timeStr) {
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(new SimpleDateFormat(TimeUtils.DATE_FORMAT_10).parse(timeStr));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("参数格式异常。正确格式为：yyyy-MM-dd");
		}
		int d = 0;
		if(cal.get(Calendar.DAY_OF_WEEK)==1){
			d = -6;
		}else{
			d = 2-cal.get(Calendar.DAY_OF_WEEK);
		}
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
		String[] result = new String[7];
		for (int i = 0; i < result.length; i++) {
			if(i == 0){
				cal.add(Calendar.DAY_OF_WEEK, d);
				result[i] = sdFormat.format(cal.getTime());
			}else{
				cal.add(Calendar.DAY_OF_WEEK, 1);
				result[i] = sdFormat.format(cal.getTime());
			}
		}
		return result;
//		cal.add(Calendar.DAY_OF_WEEK, d);
//		String first = sdFormat.format(cal.getTime());
//		cal.add(Calendar.DAY_OF_WEEK, 5);
//		String last = sdFormat.format(cal.getTime());
//		cal.add(Calendar.DAY_OF_WEEK, 6);
//		String last = sdFormat.format(cal.getTime());
//		//所在周开始日期
//		return new String[]{first, last};
	}
	
	/** 获得指定日期是星期几
	 * @param dt
	 * @param type 0=全名；1=简写；2=数字
	 * @return */
	public static Object getWeekOfDate(Date dt, int type) {
		Object[] weekDays = null;
		if (type == 0) {
			weekDays = new String[] { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		} else if (type == 1) {
			weekDays = new String[] { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
		} else if (type ==2) {
			weekDays = new Integer[]{7, 1, 2, 3, 4, 5, 6};
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	/** 根据日期，获得当前日期与本周日相差的天数
	 * @param date
	 * @return */
	private static int getMondayPlus(Calendar calendar) {
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以里减1
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1 - dayOfWeek;
		}
	}

	/** 获得上月最后一天的日期
	 * @return */
	public String getPreviousMonthEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, -1);// 减一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	public static String format(String startTime) {
		if (StringUtils.isEmpty(startTime)) {
			return startTime;
		}
		return format(startTime, DATE_FORMAT_10, DATE_TIME_FORMAT);
	}

	public static String formatYYMMDD(String startTime) {
		if (StringUtils.isEmpty(startTime)) {
			return startTime;
		}
		return format(startTime, DATE_TIME_FORMAT, DATE_FORMAT_10);
	}

	public static String formatMax(String startTime) {
		if (StringUtils.isEmpty(startTime))
			return startTime;
		try {
			Date date = parseDate(startTime);
			date = addDays(date, 1);
			return format(date, DATE_TIME_FORMAT);
		} catch (RuntimeException e) {
			throw new RuntimeException("日期型参数格式不正确");
		}
	}

	/** 获得用户展示的当前日期
	 * @return */
	public static String getShowCurrentDateTime() {
		String time = "";
		DateFormat fmt = new SimpleDateFormat(DATE_TIME_CHINA_FORMATE);
		time = fmt.format(new Date());
		return time;
	}

	/** 将yyyyMMddHHmmss格式转为用于展示的时间字符串
	 * @param dateTime
	 * @return */
	public static String formateDateTime(String dateTime) {
		try {
			return format(dateTime, DATE_TIME_FORMAT, DATE_TIME_CHINA_FORMATE);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return dateTime;
	}

	/** 计算两个日期的时间差
	 * @param startTime
	 * @param endTime
	 * @return */
	public static int[] getTimeDifference(String startTime, String endTime) {
		SimpleDateFormat timeformat = new SimpleDateFormat(DATE_TIME_FORMAT);
		long t1 = 0L;
		long t2 = 0L;
		try {
			t1 = timeformat.parse(startTime).getTime();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			t2 = timeformat.parse(endTime).getTime();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long mss = t2 - t1;
		int days = (int) (mss / (1000 * 60 * 60 * 24));
		int hours = (int) (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		int minutes = (int) (mss % (1000 * 60 * 60)) / (1000 * 60);
		int seconds = (int) (mss % (1000 * 60)) / 1000;
		int[] t = new int[] { days, hours, minutes, seconds };
		return t;
	}

	/** 最优的展示方式 展示指定的时间
	 * @param days
	 * @param hours
	 * @param minutes
	 * @param secounds
	 * @return */
	public static String getGoodShowTimeDif(int days, int hours, int minutes, int secounds) {
		String showStr = "";
		if (days > 0) {
			showStr = days + "天";
		}
		if (hours > 0) {
			showStr = showStr + hours + "小时";
		}
		if (minutes > 0) {
			showStr = showStr + minutes + "分";
		}
		if (secounds > 0) {
			showStr = showStr + secounds + "秒";
		}
		return StringUtils.isEmpty(showStr) ? "0秒" : showStr;
	}

	/** 得到在指定日期/时间基础上增加或减少指定年数的新日期/时间。
	 * @param date 原日期/时间。
	 * @param amount 日期间隔年数。可以为负数。
	 * @return 修改后的新日期。 */
	public static Date addYears(Date date, int amount) {
		return DateUtils.addYears(date, amount);
	}

	/** 得到在指定日期/时间基础上增加或减少指定月数的新日期/时间。
	 * @param date 原日期/时间。
	 * @param amount 日期间隔月数。可以为负数。
	 * @return 修改后的新日期。 */
	public static Date addMonths(Date date, int amount) {
		return DateUtils.addMonths(date, amount);
	}

	/** 得到在指定日期/时间基础上增加或减少指定周数的新日期/时间。
	 * @param date 原日期/时间。
	 * @param amount 日期间隔周数。可以为负数。
	 * @return 修改后的新日期。 */
	public static Date addWeeks(Date date, int amount) {
		return DateUtils.addWeeks(date, amount);
	}

	/** 得到在指定日期/时间基础上增加或减少指定天数的新日期/时间。
	 * @param date 原日期/时间。
	 * @param amount 日期间隔天数。可以为负数。
	 * @return 修改后的新日期。 */
	public static Date addDays(Date date, int amount) {
		return DateUtils.addDays(date, amount);
	}

	/** 得到在指定日期/时间基础上增加或减少指定小时的新日期/时间。
	 * @param date 原日期/时间。
	 * @param amount 时间间隔小时数。可以为负数。
	 * @return 修改后的新日期/时间。 */
	public static Date addHours(Date date, int amount) {
		return DateUtils.addHours(date, amount);
	}

	/** 得到在指定日期/时间基础上增加或减少指定分钟数的新日期/时间。
	 * @param date 原日期/时间。
	 * @param amount 日期间隔分钟数。可以为负数。
	 * @return 修改后的新日期。 */
	public static Date addMinutes(Date date, int amount) {
		return DateUtils.addMinutes(date, amount);
	}

	/** 得到在指定日期/时间基础上增加或减少指定秒数的新日期/时间。
	 * @param date 原日期/时间。
	 * @param amount 日期间隔秒数。可以为负数。
	 * @return 修改后的新日期。 */
	public static Date addSeconds(Date date, int amount) {
		return DateUtils.addSeconds(date, amount);
	}

	/** 得到在指定日期/时间基础上增加或减少指定毫秒数的新日期/时间。
	 * @param date 原日期/时间。
	 * @param amount 日期间隔毫秒数。可以为负数。
	 * @return 修改后的新日期。 */
	public static Date addMilliseconds(Date date, int amount) {
		return DateUtils.addMilliseconds(date, amount);
	}

	/** 将指定位置后的日期/时间设为起始值，指定位置的日期/时间变为大于当前值的最小整数值。 如： 传入时间为28 Dec 2002
	 * 13:45:01.231，传入的位置为HOUR，那么返回的结果为28 Dec 2002
	 * 14:00:00.000.传入的位置为MONTH，那么返回结果为1 Jan 2003
	 * 0:00:00.000。（时分秒的起始值为0，月日为均为1）。
	 * @param date 指定的日期/时间。
	 * @param field 指定的位置。
	 * @return 返回设定后的日期/时间。 */
	public static Calendar ceiling(Calendar date, int field) {
		return DateUtils.ceiling(date, field);
	}

	/** 将指定位置后的日期/时间设为起始值，指定位置的日期/时间变为大于当前值的最小整数值。 如： 传入时间为28 Dec 2002
	 * 13:45:01.231，传入的位置为HOUR，那么返回的结果为28 Dec 2002
	 * 14:00:00.000.传入的位置为MONTH，那么返回结果为1 Jan 2003
	 * 0:00:00.000。（时分秒的起始值为0，月日为均为1）。
	 * @param date 指定的日期/时间。
	 * @param field 指定的位置。
	 * @return 返回设定后的日期/时间。 */
	public static Date ceiling(Date date, int field) {
		return DateUtils.ceiling(date, field);
	}

	/** 截取传入的日期/时间，保留指定位置前的日期/时间，指定位置后的日期/时间，设置为起始值。如： 传入时间为28 Mar 2002
	 * 13:45:01.231，传入的位置为HOUR，那么返回的结果为28 Mar 2002
	 * 13:00:00.000.传入的位置为MONTH，那么返回结果为1 Mar 2002
	 * 0:00:00.000。（时分秒的起始值为0，月日为均为1）。
	 * @param date 指定的日期/时间。
	 * @param field 指定的位置。
	 * @return 返回设定后的日期/时间。 */
	public static Date truncate(Date date, int field) {
		return DateUtils.truncate(date, field);
	}

	/** 得到传入的两个日期之间相差的年数。
	 * @param beginDate 开始日期。
	 * @param endDate 结束日期。
	 * @return 两个日期之间相差的年数。 */
	public static int intervalYears(Date beginDate, Date endDate) {
		// 判断时间的先后
		if (beginDate.after(endDate)) {
			Date tmp = endDate;
			endDate = beginDate;
			beginDate = tmp;
		}

		// 得到两个时间各自的年份
		int endDateYear = getYear(endDate);
		int beginDateYear = getYear(beginDate);

		// 获取两个时间相差的年数
		int interval = endDateYear - beginDateYear;

		return interval;
	}

	/** 得到传入的两个日期之间相差的月数。
	 * @param beginDate 开始日期。
	 * @param endDate 结束日期。
	 * @return 两个日期之间相差的月数。 */
	public static int intervalMonths(Date beginDate, Date endDate) {
		// 判断时间的先后
		if (beginDate.after(endDate)) {
			Date tmp = endDate;
			endDate = beginDate;
			beginDate = tmp;
		}

		// 得到两个时间各自的年份
		int endDateYear = getYear(endDate);
		int beginDateYear = getYear(beginDate);

		// 获取两个时间相差的年数
		int intervalYear = endDateYear - beginDateYear;

		// 得到两个时间各自的月份
		int endDateMonth = getMonth(endDate);
		int beginDateMonth = getMonth(beginDate);

		// 获取两个时间相差的年数
		int intervalMonth = 0;
		if (endDateMonth < beginDateMonth) {
			intervalYear--;
			intervalMonth = endDateMonth + 12 - beginDateMonth;
		} else {
			intervalMonth = endDateMonth - beginDateMonth;
		}

		return intervalYear * 12 + intervalMonth;
	}

	/** 得到传入的两个日期之间相差的天数。
	 * @param beginDate 开始日期。
	 * @param endDate 结束日期。
	 * @return 两个日期之间相差的天数。 */
	public static int intervalDays(Date beginDate, Date endDate) {
		// 判断时间的先后
		if (beginDate.after(endDate)) {
			Date tmp = endDate;
			endDate = beginDate;
			beginDate = tmp;
		}

		// 将日期后的时分秒都截取掉
		beginDate = truncate(beginDate, DAY);
		endDate = truncate(endDate, DAY);

		// 获取两个时间相差的毫秒数
		long interval = endDate.getTime() - beginDate.getTime();

		return (int) (interval / (1000 * 60 * 60 * 24));
	}

	/** 得到传入的两个日期之间相差的天数。
	 * @param beginDate 开始日期。格式为yyyy-MM-dd
	 * @param endDate 结束日期。格式为yyyy-MM-dd
	 * @return 两个日期之间相差的天数。 */
	public static int intervalDays(String beginDate, String endDate) {
		Date begin=format(beginDate,"yyyy-MM-dd");
		Date end=format(endDate,"yyyy-MM-dd");
		int result=intervalDays(begin,end);
		if (isEarlyDate(beginDate,endDate,false)){
			return result;
		}else {
			return -result;
		}
	}

	/** 得到传入的两个日期之间相差的小时数。
	 * @param beginDate 开始日期。
	 * @param endDate 结束日期。
	 * @return 两个日期之间相差的小时数。 */
	public static int intervalHours(Date beginDate, Date endDate) {
		// 判断时间的先后
		if (beginDate.after(endDate)) {
			Date tmp = endDate;
			endDate = beginDate;
			beginDate = tmp;
		}

		// 将日期后的分秒都截取掉
		beginDate = truncate(beginDate, HOUR);
		endDate = truncate(endDate, HOUR);

		// 获取两个时间相差的毫秒数
		long interval = endDate.getTime() - beginDate.getTime();

		return (int) (interval / (1000 * 60 * 60));
	}

	/** 得到传入的两个日期之间相差的分钟数。
	 * @param beginDate 开始日期。
	 * @param endDate 结束日期。
	 * @return 两个日期之间相差的分钟数。 */
	public static int intervalMinutes(Date beginDate, Date endDate) {
		// 判断时间的先后
		if (beginDate.after(endDate)) {
			Date tmp = endDate;
			endDate = beginDate;
			beginDate = tmp;
		}
		return getIntervalMinutes(beginDate, endDate);
	}

	/** 得到传入的两个日期之间分钟数的相差。
	 * @param beginDate 开始日期。
	 * @param endDate 结束日期。
	 * @return 两个日期之间相差的分钟数。 */
	public static int getIntervalMinutes(Date beginDate, Date endDate) {
		// 将日期后的秒都截取掉
		beginDate = truncate(beginDate, MINUTE);
		endDate = truncate(endDate, MINUTE);

		// 获取两个时间相差的毫秒数
		long interval = endDate.getTime() - beginDate.getTime();

		return (int) (interval / (1000 * 60));
	}

	/** 得到传入的两个日期之间相差的秒数。
	 * @param beginDate 开始日期。
	 * @param endDate 结束日期。
	 * @return 两个日期之间相差的秒数。 */
	public static long intervalSeconds(Date beginDate, Date endDate) {
		// 判断时间的先后
		if (beginDate.after(endDate)) {
			Date tmp = endDate;
			endDate = beginDate;
			beginDate = tmp;
		}

		// 获取两个时间相差的毫秒数
		long interval = endDate.getTime() - beginDate.getTime();

		return (int) (interval / 1000);
	}
	
	/** 得到传入的两个日期之间相差的毫秒数。
	 * @param beginDate 开始日期。
	 * @param endDate 结束日期。
	 * @return 两个日期之间相差的秒数/1000。 */
	public static long intervalLong(Date beginDate, Date endDate) {
		// 判断时间的先后
		if (beginDate.after(endDate)) {
			Date tmp = endDate;
			endDate = beginDate;
			beginDate = tmp;
		}

		// 获取两个时间相差的毫秒数
		long interval = endDate.getTime() - beginDate.getTime();

		return (int) interval;
	}

	/** 得到传入的日期的年份信息。
	 * @param date 传入的日期。
	 * @return 日期的年份信息。 */
	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar.get(YEAR);
	}

	/** 得到传入的日期的月份信息。
	 * @param date 传入的日期。
	 * @return 日期的月份信息。 */
	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar.get(MONTH) + 1;
	}

	/** 得到传入的日期的日子信息。
	 * @param date 传入的日期。
	 * @return 日期的日子信息。 */
	public static int getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar.get(DAY);
	}

	/** 得到传入的时间的小时信息。 <pre> 12小时制 </pre>
	 * @param date 传入的日期。
	 * @return 日期的小时信息。 */
	public static int getHour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar.get(HOUR);
	}

	/** 得到传入的时间的小时信息。 <pre> 24小时制 </pre>
	 * @param date 传入的日期。
	 * @return 日期的小时信息。 */
	public static int getHourOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(HOUR_OF_DAY);
		return day;
	}

	/** 设置时间的小时信息。 <pre> 24小时制 </pre>
	 * @param date 传入的日期。
	 * @return 日期的小时信息。 */
	public static Date setHourOfDay(Date date, int amount) {
		return DateUtils.setHours(date, amount);
	}

	/** 得到传入的时间的分钟信息。
	 * @param date 传入的日期。
	 * @return 日期的分钟信息。 */
	public static int getMinute(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(MINUTE);
	}

	/** 设置时间的分钟信息。
	 * @param date 传入的日期。
	 * @return 日期的分钟信息。 */
	public static Date setMinute(Date date, int amount) {
		return DateUtils.setMinutes(date, amount);
	}

	/** 得到传入的时间的秒信息。
	 * @param date 传入的日期。
	 * @return 日期的秒信息。 */
	public static int getSecond(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar.get(SECOND);
	}

	/** 设置时间的秒信息
	 * @param date
	 * @param amount */
	public static Date setSecond(Date date, int amount) {
		return DateUtils.setSeconds(date, amount);
	}
	
	/**
	 * 根据给定的日期格式，把String转成Date对象
	 * @param str
	 * @param pattern
	 * @return
	 */
	public static Date format(String str, String pattern){
		Date date = null;
		try {  
		    SimpleDateFormat sdf = new SimpleDateFormat(pattern);  
		    date = sdf.parse(str);  
		}  
		catch (Exception e) {  
		    System.out.println(e.getMessage());  
		}
		return date;
	}

	/** 按照指定的格式对传入的日期或时间进行格式化处理。格式例如：yyyyMMdd
	 * HH:mm:ss，或者yyMd等。yyyy是完整的公元年，MM是月份，dd是日期，HH是24小时制，hh是12小时制。
	 * @param date 要处理的日期或时间。
	 * @param pattern 指定的格式。
	 * @return 格式化后的日期或时间字符串。 */
	public static String format(Date date, String pattern) {
		if (StringUtils.isEmpty(pattern)) {
			pattern = DATE_TIME_FORMAT;
		}
		return DateFormatUtils.format(date, pattern);
	}

	/** 按照指定的格式对传入的日期或时间字符串进行格式化处理后，返回处理后的字符串。
	 * @param dateString 要处理的日期或时间字符串。
	 * @param pattern 传入时间或日期字符串的指定的格式。
	 * @param desPattern 希望处理后的格式。
	 * @return 格式化后的日期或时间字符串。
	 * @throws Exception */
	public static String format(String dateString, String pattern, String desPattern) {
		if (StringUtils.isEmpty(pattern)) {
			pattern = DATE_TIME_FORMAT;
		}
		Date date;
		try {
			date = DateUtils.parseDate(dateString, new String[] { pattern });
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("时间格式转换异常");
		}

		return format(date, desPattern);
	}

	/** 将传入的字符串，按照指定格式转换成日期对象。
	 * @param date 日期字符串。
	 * @param pattern 指定的格式。
	 * @return 格式化后的日期或时间对象。
	 * @throws Exception */
	public static Date convert(String date, String pattern) {
		if (StringUtils.isEmpty(pattern)) {
			pattern = DATE_TIME_FORMAT;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(date);
		} catch (Exception e) {
			throw new IllegalArgumentException("日期字符串转换异常!");
		}
	}

	/** 将标准格式的日期字符串解析为日期
	 * @param dateString 日期的字符串表示
	 * @return 日期
	 * @throws Exception */
	public static Date parseDate(String dateString) {
		try {
			return DateUtils.parseDate(dateString, new String[] { DATE_TIME_FORMAT, DATE_FORMAT_10, TIME_FORMAT_3,
					DATE_TIMESTAMP_FORMAT });
		} catch (Exception e) {
			throw new RuntimeException(dateString + "不是一个正确格式的日期。");
		}
	}

	/** 时间是否在某个时间段之间
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param dateTime 判定时间
	 * @return */
	public static boolean isTimeBetween(Date startTime, Date endTime, Date dateTime) {
		if ((dateTime.equals(startTime) || dateTime.after(startTime)) && dateTime.before(endTime)) {
			return true;
		}
		return false;
	}

	/** 根据毫秒数表示的结束时间与毫秒数表示的开始时间的时间差,计算出差额<pre> ，并以XXX天XX小时XX分XX秒XXX毫秒格式显示
	 * @param end 结束时间
	 * @param start 开始时间
	 * @return XXX天XX小时XX分XX秒XXX毫秒格式字符串 */
	public static String printTimeInterval(long end, long start) {
		long ms = end - start;
		int ss = 1000;
		int mi = ss * 60;
		int hh = mi * 60;
		int dd = hh * 24;

		long day = ms / dd;
		long hour = (ms - day * dd) / hh;
		long minute = (ms - day * dd - hour * hh) / mi;
		long second = (ms - day * dd - hour * hh - minute * mi) / ss;
		long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

		StringBuilder sb = new StringBuilder();
		if (day != 0) {
			sb.append(day + "天");
		}
		if (hour != 0) {
			sb.append(hour + "小时");
		}
		if (minute != 0) {
			sb.append(minute + "分");
		}
		if (second != 0) {
			sb.append(second + "秒");
		}
		if (milliSecond != 0) {
			sb.append(milliSecond + "毫秒");
		}
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		
		
		
		String a[] = getFirstToLastDayOfWeek(TimeUtils.getCurrentDate());
		System.out.println(a[0]);
		System.out.println(a[1]);
		System.out.println(a[2]);
		System.out.println(a[3]);
		System.out.println(a[4]);
		System.out.println(a[5]);
		System.out.println(a[6]);
        
//		String a = TimeUtils.getLastDate();
//		System.out.println(a);
//		String nextMonth = TimeUtils.getFirstDayOfNextMonth("2014-01", TimeUtils.DATE_FORMAT_7, TimeUtils.DATE_FORMAT_7);
//		System.out.println(nextMonth);

		/** String startTime = "20150303121212"; String endTime =
		 * "20150303121912"; SimpleDateFormat timeformat = new
		 * SimpleDateFormat(DATE_TIME_FORMAT); long t1 = 0L; long t2 = 0L; try {
		 * t1 = timeformat.parse(startTime).getTime(); } catch (Exception
		 * e) { // TODO Auto-generated catch block e.printStackTrace(); } try {
		 * t2 = timeformat.parse(endTime).getTime(); } catch (Exception e)
		 * { // TODO Auto-generated catch block e.printStackTrace(); } long mss
		 * = t2-t1; long hours = (long) (mss / 3600000);
		 * System.out.println(hours); **/
	}
}
