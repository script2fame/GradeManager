package com.htc.gradeexam.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 格式化日期
 * 
 * @author 黄调聪
 *
 */
public class DateUtil {

	public static String format(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

	public static Date parse(String source) {
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}
