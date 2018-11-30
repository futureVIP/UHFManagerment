package org.lqz.framework.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class SystemTime {
	/**
	 * @显示当前时间
	 * 
	 */
	public static String currentTime() {
		// 获得系统的时间，单位为毫秒,转换为妙
		long totalMilliSeconds = System.currentTimeMillis();
		// 格式化输出
		DateFormat dateFormatterChina = DateFormat.getDateTimeInstance(	DateFormat.MEDIUM, DateFormat.MEDIUM);
		// 获取时区 ,这句加上，很关键。
		TimeZone timeZoneChina = TimeZone.getTimeZone("Asia/Shanghai");
		dateFormatterChina.setTimeZone(timeZoneChina);// 设置系统时区
		long totalSeconds = totalMilliSeconds / 1000;
		// 求出现在的秒
		long currentSecond = totalSeconds % 60;
		// 求出现在的分
		long totalMinutes = totalSeconds / 60;
		long currentMinute = totalMinutes % 60;
		// 求出现在的小时
		long totalHour = totalMinutes / 60;
		long currentHour = totalHour % 24;
		// 显示时间
		//System.out.println("总毫秒为： " + totalMilliSeconds);
		//System.out.println(currentHour + ":" + currentMinute + ":"	+ currentSecond + " GMT");
		Date nowTime = new Date(System.currentTimeMillis());
		//System.out.println(System.currentTimeMillis());
		SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd ");
		String retStrFormatNowDate = sdFormatter.format(nowTime);
		return retStrFormatNowDate.toString();
	}
}
