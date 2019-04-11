package com.diangu.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import lombok.SneakyThrows;

import org.apache.commons.lang3.StringUtils;

public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

	private static SimpleDateFormat FORMAT_YMD;

	/**
	 * 判断日期是否在时间区间内
	 *
	 * @param begin
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @param date
	 *            日期
	 * @return true-是, false-否
	 */
	public static boolean isInclude(Date begin, Date end, Date date) {
		end = addDays(end, 1);
		if (begin.compareTo(date) <= 0 && end.compareTo(date) > 0) {
			return true;
		}

		return false;
	}

	/**
	 * @param source
	 * @param target
	 * @return
	 */
	public static boolean lessThan(Date source, Date target) {
		if (source.compareTo(target) < 0) {
			return true;
		}

		return false;
	}

	public static boolean lessThanOrEqual(Date source, Date target) {
		if (source.compareTo(target) <= 0) {
			return true;
		}

		return false;
	}

	public static boolean greatThan(Date source, Date target) {
		if (source.compareTo(target) > 0) {
			return true;
		}

		return false;
	}

	public static boolean greatThanOrEqual(Date source, Date target) {
		if (source.compareTo(target) >= 0) {
			return true;
		}

		return false;
	}

	/**
	 * 获取当月第一天
	 *
	 * @return
	 */
	public static Date firstDay() {
		Date today = today();
		return truncate(today, Calendar.MONTH);
	}

	/**
	 * 获取当月最后一天
	 *
	 * @return
	 */
	public static Date lastDay() {
		Date firstDay = firstDay();
		Date nextMonth = addMonths(firstDay, 1);
		Date lastDay = addDays(nextMonth, -1);
		return lastDay;
	}

	/**
	 * 获取下个月的第一天
	 *
	 * @return
	 */
	public static Date nextFirstDay() {
		Date firstDay = firstDay();
		Date nextMonth = addMonths(firstDay, 1);
		return nextMonth;
	}

	/**
	 * 获取上个月第一天
	 * @return
	 */
	public static Date beforeFirstDay(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	/**
	 * 获取上个月最后一天
	 * @return
	 */
	public static Date beforeLastDay(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}

	/**
	 * 获取开始和结束日期之间的天数
	 *
	 * @param start
	 *            开始日期
	 * @param end
	 *            结束日期
	 * @return 间隔天数
	 * @author wulinjie
	 */
	public static long daysBetween(Date start, Date end) {
		if (start != null && end != null && end.compareTo(start) > 0) {
			return (end.getTime() - start.getTime()) / MILLIS_PER_DAY;
		}
		return 0;
	}

	/**
	 * 获取开始和结束日期之间的自然日天数
	 *
	 * @param start
	 *            开始日期
	 * @param end
	 *            结束日期
	 * @return 间隔天数
	 * @author wulinjie
	 */
	public static long daysBetweenByNature(Date start, Date end) {
		float days = 0f;
		if (start != null && end != null && end.compareTo(start) > 0) {
			days = ((float) (end.getTime() - start.getTime()) / (float) MILLIS_PER_DAY);
		}
		return new BigDecimal(days).setScale(0, BigDecimal.ROUND_CEILING).intValue();

	}

	/**
	 * 把日期转成yyyyMMddHHmmss字符串
	 *
	 * @param date
	 * @return
	 */
	public static String dateToLongString(Date date) {
		return dateToString(date, "yyyyMMddHHmmss");
	}

	/**
	 * 日期转换为字符
	 *
	 * @param date
	 *            日期
	 * @param pattern
	 *            格式
	 * @return
	 */
	public static String dateToString(Date date, String pattern) {
		if (StringUtils.isNotEmpty(pattern)) {
			FORMAT_YMD = new SimpleDateFormat(pattern);
			return FORMAT_YMD.format(date);
		}

		return "";
	}

	public static String dateToString(Date date) {
		return dateToString(date,DEFAULT_PATTERN);
	}

	private static final String DEFAULT_PATTERN = "yyyy-MM-dd";

	@SneakyThrows
	public static Date stringToDate(String date, String pattern) {
		if (StringUtils.isNotEmpty(pattern)) {
			FORMAT_YMD = new SimpleDateFormat(pattern);
			Date result = FORMAT_YMD.parse(date);
			return result;
		}
		return null;
	}

	public static Date stringToDate(String date) {
		return stringToDate(date, DEFAULT_PATTERN);
	}

	/**
	 * 获取当前日期
	 *
	 * @param field
	 *            日期字段
	 * @return
	 */
	public static Date today(Integer field) {
		Date today;
		if (field == null) {
			today = DateUtils.truncate(new Date(), Calendar.DATE);
		} else {
			today = DateUtils.truncate(new Date(), field);
		}
		return today;
	}

	public static Date today() {
		return today(Calendar.DATE);
	}

	/**
	 * 查询两个日期之间的月份
	 *
	 * @param minDate
	 * @param maxDate
	 * @return
	 * @throws ParseException
	 */
	@SneakyThrows
	public static ArrayList<Date> getMonthBetween(String minDate, String maxDate) {
		ArrayList<Date> result = new ArrayList<Date>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// 格式化为年月

		Calendar min = Calendar.getInstance();
		Calendar max = Calendar.getInstance();

		min.setTime(sdf.parse(minDate));
		min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

		max.setTime(sdf.parse(maxDate));
		max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

		Calendar curr = min;
		while (curr.before(max)) {
			result.add(sdf.parse(sdf.format(curr.getTime())));
			curr.add(Calendar.MONTH, 1);
		}

		return result;
	}

	/**
	 * 获取周期内的tiansh
	 *
	 * @param date
	 * @return
	 */
	public static int getDaysOfMontheriod(Date date, int period) {
		int totalDays = 0;
		Calendar calendar = Calendar.getInstance();
		for (int i = 0; i < period; i++) {
			// Date currDate = addMonths(date, i);
			// calendar.setTime(currDate);
			// int mothDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			// totalDays += mothDays;
			calendar.setTime(date);
			int mothDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			date = addDays(date, mothDays);
			totalDays += mothDays;
		}
		return totalDays;
	}

	/**
	 * 获得该月最后一天
	 *
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getLastDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		// 设置年份
		cal.set(Calendar.YEAR, year);
		// 设置月份
		cal.set(Calendar.MONTH, month - 1);
		// 获取某月最大天数
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		// 设置日历中月份的最大天数
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String lastDayOfMonth = sdf.format(cal.getTime());
		return lastDayOfMonth;
	}

	public static void main(String[] args) {
		Date today = today();
		Date start = DateUtils.stringToDate("2018-03-19 00:00:00","yyyy-MM-dd HH:mm:ss");
		System.out.println(today);
		System.out.println(start);
		System.out.println(today().after(start));
	}

	public static int getAge(Date dateOfBirth) {
		int age = 0;
		Calendar born = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		if (dateOfBirth != null) {
			now.setTime(new Date());
			born.setTime(dateOfBirth);
			if (born.after(now)) {
				System.err.println("年龄不能超过当前日期");
				return 0;
				// throw new IllegalArgumentException("年龄不能超过当前日期");
			}
			age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
			int nowDayOfYear = now.get(Calendar.DAY_OF_YEAR);
			int bornDayOfYear = born.get(Calendar.DAY_OF_YEAR);
			// System.out.println("nowDayOfYear:" + nowDayOfYear + "
			// bornDayOfYear:" + bornDayOfYear);
			if (nowDayOfYear < bornDayOfYear) {
				age -= 1;
			}
		}
		return age;
	}

	/**
	 * 获取当天日期年月日格式
	 *
	 * @return
	 */
	public static String getToadyStr() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String dateNowStr = format.format(new Date());
		return dateNowStr;
	}

	/**
	 * 比较给定时间与现在时间的差值
	 *
	 * @param create
	 * @param longTime
	 * @return
	 * @throws ParseException
	 */
	public static boolean compareLongDate(Date create, long longTime) throws ParseException {
		long createTime = create.getTime();
		long current = System.currentTimeMillis();
		if (current - createTime <= longTime) {
			return true;
		}
		return false;
	}

}
