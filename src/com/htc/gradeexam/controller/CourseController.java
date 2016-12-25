package com.htc.gradeexam.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import com.htc.gradeexam.manager.CourseManager;
import com.htc.gradeexam.model.Course;

/**
 * �γ̿�����
 * 
 * @author �Ƶ���
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
		System.out.println("1-��ӿγ�");
		System.out.println("2-ɾ���γ�");
		System.out.println("3-�޸Ŀγ�");
		System.out.println("4-��ѯ�γ�");
		System.out.println("q-�˳�");
		System.out.println("=======================");
		// ������Ļ��������ȡ������ַ�
		BufferedReader br = null;
		String s = null;
		try {
			// Scanner s=new Scanner(System.in);
			// �ֽ���ת��Ϊ�ַ���
			br = new BufferedReader(new InputStreamReader(System.in));
			while ((s = br.readLine()) != null) {
				if (ADD.equals(s)) {
					System.out.println("������Ҫ��ӵĿγ̣���ʽ��course_name=#");
					state = ADD;
				} else if (DELETE.equals(s)) {
					System.out.println("������Ҫɾ���Ŀγ̱�ţ���ʽ��course_id=#");
					state = DELETE;
				} else if (MODIFY.equals(s)) {
					System.out.println("������Ҫ�޸ĵĿγ̣���ʽ��course_id=#,course_name=#");
					state = MODIFY;
				} else if (QUERY.equals(s)) {
					System.out.println("������س���ѯ���пγ�");
					state = QUERY;
				} else if (QUIT.equalsIgnoreCase(s)) {
					state = QUIT;
					System.out.println("�Ƿ�ȷ���˳���Y|N");
				} else if (ADD.equals(state)) {
					String[] courseArray = s.split("=");
					String courseName = courseArray[1];
					CourseManager.getInstance().addCourse(courseName);
					System.out.println("��ӿγ̳ɹ�!");
				} else if (DELETE.equals(state)) {
					String[]courseArray = s.split("=");
					int courseId =Integer.parseInt(courseArray[1]);
					CourseManager.getInstance().delCourse(courseId);
					System.out.println("ɾ���ɹ���");
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
					System.out.println("�ɹ��޸ı��Ϊ" + courseId + "�Ŀγ̣�");
				} else if (QUERY.equals(state)) {
					List<Course> courseList = CourseManager.getInstance().findCourseList();
					System.out.println("========�γ��б�========");
					for (Iterator<Course> iterator = courseList.iterator(); iterator.hasNext();) {
						Course course = iterator.next();
						System.out.println(course.getCourseId() + "��" + course.getCourseName());
					}
				} else if (QUIT.equals(state)) {
					if ("Y".equalsIgnoreCase(s)) {
						System.err.println("�ɹ��˳���");
						break;
					} else {
						System.out.println("����ϵͳ�����������:");
						System.out.println("=======================");
						System.out.println("1-��ӿγ�");
						System.out.println("2-ɾ���γ�");
						System.out.println("3-�޸Ŀγ�");
						System.out.println("4-��ѯ�γ�");
						System.out.println("q-�˳�");
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
