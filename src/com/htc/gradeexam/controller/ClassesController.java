package com.htc.gradeexam.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.htc.gradeexam.manager.ClassesManager;

/**
 * �༶���������
 * 
 * @author �Ƶ���
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
		System.out.println("1-��Ӱ༶");
		System.out.println("2-ɾ���༶");
		System.out.println("3-�޸İ༶");
		System.out.println("4-��ѯ�༶");
		System.out.println("q-�˳�");
		System.out.println("=======================");
		// ������Ļ��������ȡ������ַ�
		BufferedReader br = null;
		br = new BufferedReader(new InputStreamReader(System.in));
		String s = null;
		try {
			while ((s = br.readLine()) != null) {
				if (ADD.equals(s)) {
					System.out.println("������Ҫ��ӵİ༶����ʽ��pid=#,course_name=#");
					state = ADD;
				} else if (DELETE.equals(s)) {
					System.out.println("������Ҫɾ���İ༶��ţ���ʽclasses_id=#");
					state = DELETE;
				} else if (MODIFY.equals(s)) {
					System.out.println("������Ҫ�޸ĵİ༶��ź����ƣ���ʽ��classes_id=#,classes_name=#");
					state = MODIFY;
				} else if (QUERY.equals(s)) {
					System.out.println("������س���ѯ���а༶");
					state = QUERY;
				} else if (QUIT.equalsIgnoreCase(s)) {
					System.out.println("�Ƿ�ȷ���˳���Y|N");
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
					System.out.println("��Ӱ༶�ɹ���");
				} else if (DELETE.equals(state)) {
					int classesId = Integer.parseInt(s.substring(s.indexOf("=") + 1, s.length()));
					ClassesManager.getInstance().delClasses(classesId);
					System.out.println("ɾ�����Ϊ��" + classesId + "�İ༶�ɹ���");
				} else if (MODIFY.equals(state)) {
					int classesId = Integer.parseInt(s.substring(s.indexOf("=") + 1, s.indexOf(",")));
					String newClassesName = s.substring(s.lastIndexOf("=") + 1, s.length());
					ClassesManager.getInstance().ModifyById(classesId, newClassesName);
					System.out.println("�޸ı��Ϊ" + classesId + "�༶�ɹ���");
				} else if (QUERY.equals(state)) {
					ClassesManager.getInstance().outClassList();
				} else if (QUIT.equals(state)) {
					if ("Y".equalsIgnoreCase(s)) {
						System.err.println("�ɹ��˳���");
						break;
					} else {
						System.out.println("����ϵͳ�����������:");
						System.out.println("=======================");
						System.out.println("1-��Ӱ༶");
						System.out.println("2-ɾ���༶");
						System.out.println("3-�޸İ༶");
						System.out.println("4-��ѯ�༶");
						System.out.println("q-�˳�");
						System.out.println("=======================");
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
