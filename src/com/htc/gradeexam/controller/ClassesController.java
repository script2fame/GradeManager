package com.htc.gradeexam.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.htc.gradeexam.manager.ClassesManager;

/**
 * 班级管理控制器
 * 
 * @author 黄调聪
 *
 */
public class ClassesController {

	private static final String ADD = "1";

	private static final String DELETE = "2";

	private static final String MODIFY = "3";

	private static final String QUERY = "4";

	private static final String QUIT = "q";

	private static String state = null;

	public static void main(String[] args) {
		System.out.println("=======================");
		System.out.println("1-添加班级");
		System.out.println("2-删除班级");
		System.out.println("3-修改班级");
		System.out.println("4-查询班级");
		System.out.println("q-退出");
		System.out.println("=======================");
		// 利用屏幕输入流获取输入的字符
		BufferedReader br = null;
		br = new BufferedReader(new InputStreamReader(System.in));
		String s = null;
		try {
			while ((s = br.readLine()) != null) {
				if (ADD.equals(s)) {
					System.out.println("请输入要添加的班级：格式：pid=#,course_name=#");
					state = ADD;
				} else if (DELETE.equals(s)) {
					System.out.println("请输入要删除的班级编号，格式classes_id=#");
					state = DELETE;
				} else if (MODIFY.equals(s)) {
					System.out.println("请输入要修改的班级编号和名称，格式：classes_id=#,classes_name=#");
					state = MODIFY;
				} else if (QUERY.equals(s)) {
					System.out.println("请输入回车查询所有班级");
					state = QUERY;
				} else if (QUIT.equalsIgnoreCase(s)) {
					System.out.println("是否确定退出？Y|N");
					state = QUIT;
				} else if (ADD.equals(state)) {
					// String[] classesArray = s.split(",");
					int pid = 0;
					String classesName = "";
					// for (int i = 0; i < classesArray.length; i++) {
					// String[] classesAttr = classesArray[i].split("=");
					// if ("pid".equals(classesAttr[0])) {
					// pid = Integer.parseInt(classesAttr[1]);
					// } else if ("classes_name".equals(classesAttr[0])) {
					// classesName = classesAttr[1];
					// }
					// }
					pid = Integer.parseInt(s.substring(s.indexOf("=") + 1, s.indexOf(",")));
					classesName = s.substring(s.lastIndexOf("=") + 1, s.length());
					ClassesManager.getInstance().addClasses(pid, classesName);
					System.out.println("添加班级成功！");
				} else if (DELETE.equals(state)) {
					int classesId = Integer.parseInt(s.substring(s.indexOf("=") + 1, s.length()));
					ClassesManager.getInstance().delClasses(classesId);
					System.out.println("删除编号为：" + classesId + "的班级成功！");
				} else if (MODIFY.equals(state)) {
					int classesId = Integer.parseInt(s.substring(s.indexOf("=") + 1, s.indexOf(",")));
					String newClassesName = s.substring(s.lastIndexOf("=") + 1, s.length());
					ClassesManager.getInstance().ModifyById(classesId, newClassesName);
					System.out.println("修改编号为" + classesId + "班级成功！");
				} else if (QUERY.equals(state)) {
					ClassesManager.getInstance().outClassList();
				} else if (QUIT.equals(state)) {
					if ("Y".equalsIgnoreCase(s)) {
						System.err.println("成功退出！");
						break;
					} else {
						System.out.println("返回系统，请继续操作:");
						System.out.println("=======================");
						System.out.println("1-添加班级");
						System.out.println("2-删除班级");
						System.out.println("3-修改班级");
						System.out.println("4-查询班级");
						System.out.println("q-退出");
						System.out.println("=======================");
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
