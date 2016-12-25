package com.htc.gradeexam.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import com.htc.gradeexam.manager.StudentManager;
import com.htc.gradeexam.model.Classes;
import com.htc.gradeexam.model.Student;
import com.htc.gradeexam.util.ExamConfigReader;
import com.htc.gradeexam.util.Exportutil;

/**
 * 学生管理控制器
 * 
 * @author 黄调聪
 *
 */
public class StudentController {

	private static final String ADD = "1";

	private static final String DELETE = "2";

	private static final String MODIFY = "3";

	private static final String QUERY = "4";

	private static final String EXPORT = "5";

	private static final String EXPORTEXCEL = "6";
	
	private static final String QUIT = "q";

	private static String state = null;

	private static StudentManager studentManager = null;

	static {
		try {
			studentManager = (StudentManager) Class
					.forName(ExamConfigReader.getInstance().getPropertiesValue("student-manager-impl")).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.out.println("=======================");
		System.out.println("1-添加学生");
		System.out.println("2-删除学生");
		System.out.println("3-修改学生");
		System.out.println("4-查询学生");
		System.out.println("5-导出学生信息");
		System.out.println("6-导出生成excel文件");
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
					System.out.println("请输入要添加的学生：格式：" + "student_name=#," + "sex=#," + "birthday=#," + "contact_tel=#,"
							+ "address=#," + "classes_id=#");
					state = ADD;
				} else if (DELETE.equals(s)) {
					System.out.println("请输入要删除的学生编号：格式：student_id=#");
					state = DELETE;
				} else if (MODIFY.equals(s)) {
					System.out.println("请输入要修改的学生：格式：student_id=#，" + "student_name=#," + "sex=#," + "birthday=#,"
							+ "contact_tel=#," + "address=#," + "classes_id=#");
					state = MODIFY;
				} else if (QUERY.equals(s)) {
					System.out.println("分页查询学生，pageNo=#,pageSize=#");
					state = QUERY;
				} else if (EXPORT.equals(s)) {
					state = EXPORT;
					System.out.println("输入回车导出学生信息");
				}else if(EXPORTEXCEL.equals(s)){
					state = EXPORTEXCEL;
					System.out.println("输入回车导出学生信息生成excel");
				}else if (QUIT.equalsIgnoreCase(s)) {
					state = QUIT;
					System.out.println("是否确定退出？Y|N");
				} else if (ADD.equals(state)) {
					addStudent(s);
				} else if (DELETE.equals(state)) {
					delStudent(s);
				} else if (MODIFY.equals(state)) {
					modifyStudent(s);
				} else if (QUERY.equals(state)) {
					outListStudent(s);
				} else if(EXPORT.equals(state)){
					Exportutil.export();
				} else if(EXPORTEXCEL.equals(state)){
					Exportutil.ExportToExcel();
				}else if (QUIT.equals(state)) {
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

	

	/**
	 * 构造Student对象
	 * 
	 * @param s
	 * @return
	 */
	private static Student makeStudent(String s) {
		Student student = new Student();
		String[] studentArray = s.split(",");
		for (int i = 0; i < studentArray.length; i++) {
			String[] studentAttr = studentArray[i].split("=");
			if ("student_name".equals(studentAttr[0])) {
				student.setStudentName(studentAttr[1]);
			} else if ("student_id".equals(studentAttr[0])) {
				student.setStudentId(Integer.parseInt(studentAttr[1]));
			} else if ("sex".equals(studentAttr[0])) {
				student.setSex(studentAttr[1]);
			} else if ("birthday".equals(studentAttr[0])) {
				try {
					student.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(studentAttr[1]));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} else if ("contact_tel".equals(studentAttr[0])) {
				student.setContactTel(studentAttr[1]);
			} else if ("address".equals(studentAttr[0])) {
				student.setAddress(studentAttr[1]);
			} else if ("classes_id".equals(studentAttr[0])) {
				Classes classes = new Classes();
				classes.setClassesId(Integer.parseInt(studentAttr[1]));
				// 建立学生和班级之间的关联
				student.setClasses(classes);
			}
		}
		return student;
	}

	/**
	 * 添加学生
	 * 
	 * @param s
	 */
	private static void addStudent(String s) {
		Student student = makeStudent(s);
		studentManager.addStudent(student);
		System.out.println("添加学生成功!");
	}

	/**
	 * 修改学生
	 * 
	 * @param s
	 */
	private static void modifyStudent(String s) {
		Student student = makeStudent(s);
		studentManager.modifyStudent(student);
		System.out.println("修改学生成功!");
	}

	private static void outListStudent(String s) {
		String[] studentArray = s.split(",");
		int pageNo = Integer.parseInt(studentArray[0].split("=")[1]);
		int pageSize = Integer.parseInt(studentArray[1].split("=")[1]);
		// StudentManager studentManager = new StudentManagerImpl();
		// 静态代码块加载StudentManagerImpl类
		List<Student> studentList = studentManager.findStudentList(pageNo, pageSize);
		for (Iterator<Student> iterator = studentList.iterator(); iterator.hasNext();) {
			Student student = iterator.next();
			System.out.print(student.toString());
			// System.out.println("年龄："+ (new
			// Date().getTime()-student.getBirthday().getTime())/1000/60/60/24/365);
			System.out.println(
					"年龄：" + (System.currentTimeMillis() - student.getBirthday().getTime()) / 1000 / 60 / 60 / 24 / 365);
		}
	}

	/**
	 * 删除学生
	 * 
	 * @param s
	 */
	private static void delStudent(String s) {
		String[] studentArray = s.split("=");
		int studentId = Integer.parseInt(studentArray[1]);
		studentManager.delStudent(studentId);
		System.out.println("删除编号为：" + studentId + "的学生成功!");
	}
}
