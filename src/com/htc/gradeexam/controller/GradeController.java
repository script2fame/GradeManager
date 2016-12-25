package com.htc.gradeexam.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.htc.gradeexam.manager.GradeManager;
import com.htc.gradeexam.model.Grade;
import com.htc.gradeexam.util.ExamConfigReader;

/**
 * 成绩管理控制器
 * 
 * @author 黄调聪
 *
 */

public class GradeController {

	private static final String ADD = "1";

	private static final String DELETE = "2";

	private static final String MODIFY = "3";

	private static final String QUERY_STUDENT_ID = "4";

	private static final String QUERY_HIGHTEST_GRADE ="5";
	
	private static final String QUERY_TOP3 = "6";
	
	private static final String QUERY = "7";

	private static final String QUIT = "q";

	private static String state = null;

	private static GradeManager gradeManager = null;

	static {
		String className = ExamConfigReader.getInstance().getPropertiesValue("grade-manager-impl");
		try {
			Class<?> classNmae = Class.forName(className);
			gradeManager = (GradeManager) classNmae.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.out.println("=======================");
		System.out.println("1-添加成绩");
		System.out.println("2-删除成绩");
		System.out.println("3-修改成绩");
		System.out.println("4-根据学生编号查询成绩");
		System.out.println("5-查询每科最高分");
		System.out.println("6-查询总分前三名");
		System.out.println("7-分页查询学生");
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
					System.out.println("请输入要添加的成绩，格式：student_id=#,course_id=#,grade=#");
					state = ADD;
				} else if (DELETE.equals(s)) {
					System.out.println("请输入删除的学生编号和课程编号，格式；student_id=#,course_id=#");
					state = DELETE;
				} else if (MODIFY.equals(s)) {
					System.out.println("请输入要修改的成绩信息，格式：student_id=#,course_id=#,grade=#");
					state = MODIFY;
				} else if (QUERY_TOP3.equals(s)) {
					System.out.println("请输入回车查询总分前三名：");
					state = QUERY_TOP3;
				} else if (QUERY_STUDENT_ID.equals(s)) {
					System.out.println("请输入id查询学生成绩，格式：student_id=#");
					state = QUERY_STUDENT_ID;
				} else if (QUERY_HIGHTEST_GRADE.equals(s)) {
					System.out.println("请输入回车查询每科最高分：");
					state = QUERY_HIGHTEST_GRADE;
				} else if (QUERY.equals(s)) {
					System.out.println("分页查询成绩信息，格式：pageNo=#,pageSize=#");
					state = QUERY;
				} else if (QUIT.equalsIgnoreCase(s)) {
					state = QUIT;
					System.out.println("是否确定退出？Y|N");
				} else if (ADD.equals(state)) {
					addGrade(s);
				} else if (DELETE.equals(state)) {
					delGrade(s);
				} else if (MODIFY.equals(state)) {
					modifyGrade(s);
				} else if (QUERY_STUDENT_ID.equals(state)) {
					findGradeByStudentId(s);
				} else if(QUERY_HIGHTEST_GRADE.equals(state)){ 
					findHighestGradeListOfPerCourse();
				} else if (QUERY_TOP3.equals(state)) {
					findGradeListTop3();
				} else if (QUERY.equals(state)) {
					findGradeList(s);
				} else if (QUIT.equals(state)) {
					if ("Y".equalsIgnoreCase(s)) {
						System.err.println("成功退出！");
						break;
					} else {
						System.out.println("返回系统，请继续操作:");
						System.out.println("1-添加成绩");
						System.out.println("2-删除成绩");
						System.out.println("3-修改成绩");
						System.out.println("4-根据学生编号查询成绩");
						System.out.println("5-查询每科最高分");
						System.out.println("6-查询总分前三名");
						System.out.println("7-分页查询学生");
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


	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Map parseParam(String s) {
		Map paramMap = new HashMap();
		StringTokenizer st1 = new StringTokenizer(s, ",");
		while (st1.hasMoreTokens()) {
			String t1 = st1.nextToken();
			StringTokenizer st2 = new StringTokenizer(t1, "=");
			if (st2.hasMoreTokens()) {
				String leftStr = st2.nextToken();
				if ("student_id".equals(leftStr)) {
					if (st2.hasMoreTokens()) {
						paramMap.put("student_id", Integer.parseInt(st2.nextToken()));
					}
				} else if ("course_id".equals(leftStr)) {
					if (st2.hasMoreTokens()) {
						paramMap.put("course_id", Integer.parseInt(st2.nextToken()));
					}
				} else if ("grade".equals(leftStr)) {
					if (st2.hasMoreTokens()) {
						paramMap.put("grade", Float.parseFloat(st2.nextToken()));
					}
				} else if ("pageNo".equals(leftStr)) {
					if (st2.hasMoreTokens()) {
						paramMap.put("pageNo", Integer.parseInt(st2.nextToken()));
					}
				} else if ("pageSize".equals(leftStr)) {
					if (st2.hasMoreTokens()) {
						paramMap.put("pageSize", Integer.parseInt(st2.nextToken()));
					}
				}
			}
		}
		return paramMap;

	}

	@SuppressWarnings("rawtypes")
	private static void addGrade(String s) {
		Map paramMap = parseParam(s);
		int studentId = (int) (paramMap.get("student_id"));
		int courseId = (int) (paramMap.get("course_id"));
		float grade = (float) (paramMap.get("grade"));
		gradeManager.addGrade(studentId, courseId, grade);
		System.out.println("添加成绩成功！！！");
	}

	@SuppressWarnings("rawtypes")
	private static void modifyGrade(String s) {
		Map paramMap = parseParam(s);
		int studentId = (int) (paramMap.get("student_id"));
		int courseId = (int) (paramMap.get("course_id"));
		float grade = (float) (paramMap.get("grade"));
		gradeManager.modifyGrade(studentId, courseId, grade);
		System.out.println("修改成绩成功！！！");
	}

	@SuppressWarnings("rawtypes")
	private static void delGrade(String s) {
		Map paramMap = parseParam(s);
		int studentId = (int) (paramMap.get("student_id"));
		int courseId = (int) (paramMap.get("course_id"));
		gradeManager.delGrade(studentId, courseId);
		System.out.println("删除编号为：" + studentId + "学生的成绩成功！！！");
	}

	@SuppressWarnings("rawtypes")
	private static void findGradeByStudentId(String s) {
		Map paramMap = parseParam(s);
		int studentId = (int) (paramMap.get("student_id"));
		List<Grade> gradeList = gradeManager.findGradeListByStudentId(studentId);
		for (Iterator<Grade> iterator = gradeList.iterator(); iterator.hasNext();) {
			Grade grade = iterator.next();
			System.out.print("学生编号: " + grade.getStudent().getStudentId());
			System.out.print(" 学生姓名 : " + grade.getStudent().getStudentName());
			System.out.print(" 班级名称: " + grade.getStudent().getClasses().getClassName());
			System.out.print(" 课程名称: " + grade.getCourse().getCourseName());
			System.out.println(" 具体成绩: " + new DecimalFormat("####.00").format(grade.getGrade()));
		}
	}

	/**
	 * 分页查询学生信息
	 * 
	 * @param s
	 */
	@SuppressWarnings("rawtypes")
	private static void findGradeList(String s) {
		Map paramMap = parseParam(s);
		int pageNo = (int) (paramMap.get("pageNo"));
		int pageSize = (int) (paramMap.get("pageSize"));
		List<Grade> gradeList = gradeManager.findGradeList(pageNo, pageSize);
		for (Iterator<Grade> iterator = gradeList.iterator(); iterator.hasNext();) {
			Grade grade = iterator.next();
			System.out.print("学生编号: " + grade.getStudent().getStudentId());
			System.out.print(" 学生姓名 : " + grade.getStudent().getStudentName());
			System.out.print(" 班级名称: " + grade.getStudent().getClasses().getClassName());
			System.out.print(" 课程名称: " + grade.getCourse().getCourseName());
			System.out.println(" 具体成绩: " + new DecimalFormat("####.00").format(grade.getGrade()));
		}
	}
	
	/**
	 * 查询总分前三名
	 * @param s
	 */
	private static void findGradeListTop3() {
		List<Grade> gradeList= gradeManager.findGradeListTop3();
		for (Iterator<Grade> iterator = gradeList.iterator(); iterator.hasNext();) {
			Grade grade = iterator.next();
			System.out.print("学生编号: " + grade.getStudent().getStudentId());
			System.out.print(" 学生姓名 : " + grade.getStudent().getStudentName());
			System.out.print(" 班级名称: " + grade.getStudent().getClasses().getClassName());
			System.out.println("总分: " + new DecimalFormat("####.00").format(grade.getGrade()));
		}
	}
	
	/**
	 * 查询每科最高分
	 */
	private static void findHighestGradeListOfPerCourse() {
		List<Grade> gradeList = gradeManager.findHighestGradeListOfPerCourse();
		for (Iterator<Grade> iterator = gradeList.iterator(); iterator.hasNext();) {
			Grade grade = iterator.next();
			System.out.print("学生编号: " + grade.getStudent().getStudentId());
			System.out.print(" 学生姓名 : " + grade.getStudent().getStudentName());
			System.out.print(" 班级名称: " + grade.getStudent().getClasses().getClassName());
			System.out.print(" 课程名称: " + grade.getCourse().getCourseName());
			System.out.println("总分: " + new DecimalFormat("####.00").format(grade.getGrade()));
		}
	}
}
