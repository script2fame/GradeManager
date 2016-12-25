package com.htc.gradeexam.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 采用单例读取exam-config.properties配置文件
 * 
 * @author 黄调聪
 *
 */
public class ExamConfigReader {
	private static ExamConfigReader instance = new ExamConfigReader();

	private Properties properties = new Properties();

	private ExamConfigReader() {
		InputStream inputStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("exam-config.properties");
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ExamConfigReader getInstance() {
		return instance;
	}

	public String getPropertiesValue(String key) {
		return properties.getProperty(key);
	}

	public static void main(String[] args) {
		String value = ExamConfigReader.getInstance().getPropertiesValue("student-manager-impl");
		System.out.println(value);
	}
}
