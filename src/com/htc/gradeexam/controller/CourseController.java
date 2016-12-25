package com.htc.gradeexam.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import com.htc.gradeexam.manager.CourseManager;
import com.htc.gradeexam.model.Course;

/**
 * 课程控制器
 * 
 * @author 黄调聪
 *
 */
public class CourseController {

	private static final String ADD = "1";

	private static final String DELETE = "2";

	private static final String MODIFY = "3";

	private static final String QUERY = "4";

	private static final String QUIT = "q";

	private static String state = null;

	public static void main(String[] args) {
		System.out.println("=======================");
		System.out.println("1-添加课程");
		System.out.println("2-删除课程");
		System.out.println("3-修改课程");
		System.out.println("4-查询课程");
		System.out.println("q-退出");
		System.out.println("=======================");
		// 利用屏幕输入流获取输入的字符
		BufferedReader br = null;
		String s = null;
		try {
			// Scanner s=new Scanner(System.in);
			// 字节流转化为字符流
			br = new BufferedReader(new InputStreamReader(System.in));
			while ((s = br.readLine()) != null) {
				if (ADD.equals(s)) {
					System.out.println("请输入要添加的课程：格式：course_name=#");
					state = ADD;
				} else if (DELETE.equals(s)) {
					System.out.println("请输入要删除的课程编号：格式：course_id=#");
					state = DELETE;
				} else if (MODIFY.equals(s)) {
					System.out.println("请输入要修改的课程：格式：course_id=#,course_name=#");
					state = MODIFY;
				} else if (QUERY.equals(s)) {
					System.out.println("请输入回车查询所有课程");
					state = QUERY;
				} else if (QUIT.equalsIgnoreCase(s)) {
					state = QUIT;
					System.out.println("是否确定退出？Y|N");
				} else if (ADD.equals(state)) {
					String[] courseArray = s.split("=");
					String courseName = courseArray[1];
					CourseManager.getInstance().addCourse(courseName);
					System.out.println("添加课程成功!");
				} else if (DELETE.equals(state)) {
					String[]courseArray = s.split("=");
					int courseId =Integer.parseInt(courseArray[1]);
					CourseManager.getInstance().delCourse(courseId);
					System.out.println("删除成功！");
				} else if (MODIFY.equals(state)) {
					String[] courseArray = s.split(",");
					int courseId = 0;
					String courseName = null;
					for (int i = 0; i < courseArray.length; i++) {
						String[] courseAttr = courseArray[i].split("=");
						if ("course_id".equals(courseAttr[0])) {
							courseId = Integer.parseInt(courseAttr[1]);
						} else if ("course_name".equals(courseAttr[0])) {
							courseName = courseAttr[1];
						}
					}
					CourseManager.getInstance().modifyCourse(courseId, courseName);
					System.out.println("成功修改编号为" + courseId + "的课程！");
				} else if (QUERY.equals(state)) {
					List<Course> courseList = CourseManager.getInstance().findCourseList();
					System.out.println("========课程列表========");
					for (Iterator<Course> iterator = courseList.iterator(); iterator.hasNext();) {
						Course course = iterator.next();
						System.out.println(course.getCourseId() + "、" + course.getCourseName());
					}
				} else if (QUIT.equals(state)) {
					if ("Y".equalsIgnoreCase(s)) {
						System.err.println("成功退出！");
						break;
					} else {
						System.out.println("返回系统，请继续操作:");
						System.out.println("=======================");
						System.out.println("1-添加课程");
						System.out.println("2-删除课程");
						System.out.println("3-修改课程");
						System.out.println("4-查询课程");
						System.out.println("q-退出");
						System.out.println("=======================");
						continue;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
