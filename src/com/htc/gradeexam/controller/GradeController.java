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
 * �ɼ����������
 * 
 * @author �Ƶ���
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
		System.out.println("1-��ӳɼ�");
		System.out.println("2-ɾ���ɼ�");
		System.out.println("3-�޸ĳɼ�");
		System.out.println("4-����ѧ����Ų�ѯ�ɼ�");
		System.out.println("5-��ѯÿ����߷�");
		System.out.println("6-��ѯ�ܷ�ǰ����");
		System.out.println("7-��ҳ��ѯѧ��");
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
					System.out.println("������Ҫ��ӵĳɼ�����ʽ��student_id=#,course_id=#,grade=#");
					state = ADD;
				} else if (DELETE.equals(s)) {
					System.out.println("������ɾ����ѧ����źͿγ̱�ţ���ʽ��student_id=#,course_id=#");
					state = DELETE;
				} else if (MODIFY.equals(s)) {
					System.out.println("������Ҫ�޸ĵĳɼ���Ϣ����ʽ��student_id=#,course_id=#,grade=#");
					state = MODIFY;
				} else if (QUERY_TOP3.equals(s)) {
					System.out.println("������س���ѯ�ܷ�ǰ������");
					state = QUERY_TOP3;
				} else if (QUERY_STUDENT_ID.equals(s)) {
					System.out.println("������id��ѯѧ���ɼ�����ʽ��student_id=#");
					state = QUERY_STUDENT_ID;
				} else if (QUERY_HIGHTEST_GRADE.equals(s)) {
					System.out.println("������س���ѯÿ����߷֣�");
					state = QUERY_HIGHTEST_GRADE;
				} else if (QUERY.equals(s)) {
					System.out.println("��ҳ��ѯ�ɼ���Ϣ����ʽ��pageNo=#,pageSize=#");
					state = QUERY;
				} else if (QUIT.equalsIgnoreCase(s)) {
					state = QUIT;
					System.out.println("�Ƿ�ȷ���˳���Y|N");
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
						System.err.println("�ɹ��˳���");
						break;
					} else {
						System.out.println("����ϵͳ�����������:");
						System.out.println("1-��ӳɼ�");
						System.out.println("2-ɾ���ɼ�");
						System.out.println("3-�޸ĳɼ�");
						System.out.println("4-����ѧ����Ų�ѯ�ɼ�");
						System.out.println("5-��ѯÿ����߷�");
						System.out.println("6-��ѯ�ܷ�ǰ����");
						System.out.println("7-��ҳ��ѯѧ��");
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
		System.out.println("��ӳɼ��ɹ�������");
	}

	@SuppressWarnings("rawtypes")
	private static void modifyGrade(String s) {
		Map paramMap = parseParam(s);
		int studentId = (int) (paramMap.get("student_id"));
		int courseId = (int) (paramMap.get("course_id"));
		float grade = (float) (paramMap.get("grade"));
		gradeManager.modifyGrade(studentId, courseId, grade);
		System.out.println("�޸ĳɼ��ɹ�������");
	}

	@SuppressWarnings("rawtypes")
	private static void delGrade(String s) {
		Map paramMap = parseParam(s);
		int studentId = (int) (paramMap.get("student_id"));
		int courseId = (int) (paramMap.get("course_id"));
		gradeManager.delGrade(studentId, courseId);
		System.out.println("ɾ�����Ϊ��" + studentId + "ѧ���ĳɼ��ɹ�������");
	}

	@SuppressWarnings("rawtypes")
	private static void findGradeByStudentId(String s) {
		Map paramMap = parseParam(s);
		int studentId = (int) (paramMap.get("student_id"));
		List<Grade> gradeList = gradeManager.findGradeListByStudentId(studentId);
		for (Iterator<Grade> iterator = gradeList.iterator(); iterator.hasNext();) {
			Grade grade = iterator.next();
			System.out.print("ѧ�����: " + grade.getStudent().getStudentId());
			System.out.print(" ѧ������ : " + grade.getStudent().getStudentName());
			System.out.print(" �༶����: " + grade.getStudent().getClasses().getClassName());
			System.out.print(" �γ�����: " + grade.getCourse().getCourseName());
			System.out.println(" ����ɼ�: " + new DecimalFormat("####.00").format(grade.getGrade()));
		}
	}

	/**
	 * ��ҳ��ѯѧ����Ϣ
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
			System.out.print("ѧ�����: " + grade.getStudent().getStudentId());
			System.out.print(" ѧ������ : " + grade.getStudent().getStudentName());
			System.out.print(" �༶����: " + grade.getStudent().getClasses().getClassName());
			System.out.print(" �γ�����: " + grade.getCourse().getCourseName());
			System.out.println(" ����ɼ�: " + new DecimalFormat("####.00").format(grade.getGrade()));
		}
	}
	
	/**
	 * ��ѯ�ܷ�ǰ����
	 * @param s
	 */
	private static void findGradeListTop3() {
		List<Grade> gradeList= gradeManager.findGradeListTop3();
		for (Iterator<Grade> iterator = gradeList.iterator(); iterator.hasNext();) {
			Grade grade = iterator.next();
			System.out.print("ѧ�����: " + grade.getStudent().getStudentId());
			System.out.print(" ѧ������ : " + grade.getStudent().getStudentName());
			System.out.print(" �༶����: " + grade.getStudent().getClasses().getClassName());
			System.out.println("�ܷ�: " + new DecimalFormat("####.00").format(grade.getGrade()));
		}
	}
	
	/**
	 * ��ѯÿ����߷�
	 */
	private static void findHighestGradeListOfPerCourse() {
		List<Grade> gradeList = gradeManager.findHighestGradeListOfPerCourse();
		for (Iterator<Grade> iterator = gradeList.iterator(); iterator.hasNext();) {
			Grade grade = iterator.next();
			System.out.print("ѧ�����: " + grade.getStudent().getStudentId());
			System.out.print(" ѧ������ : " + grade.getStudent().getStudentName());
			System.out.print(" �༶����: " + grade.getStudent().getClasses().getClassName());
			System.out.print(" �γ�����: " + grade.getCourse().getCourseName());
			System.out.println("�ܷ�: " + new DecimalFormat("####.00").format(grade.getGrade()));
		}
	}
}
