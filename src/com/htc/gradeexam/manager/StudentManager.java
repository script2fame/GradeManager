package com.htc.gradeexam.manager;

import java.util.List;

import com.htc.gradeexam.model.Student;

/**
 * ѧ������ӿ�
 * 
 * @author �Ƶ���
 *
 */
public interface StudentManager {

	/**
	 * ���ѧ��
	 * 
	 * @param student
	 */
	public void addStudent(Student student);

	/**
	 * ����ѧ�����ɾ��ѧ��
	 * 
	 * @param studentId
	 */
	public void delStudent(int studentId);

	/**
	 * �޸�ѧ����Ϣ
	 * 
	 * @param student
	 */
	public void modifyStudent(Student student);

	/**
	 * ��ҳ��ѯ
	 * @param pageNo �ڼ�ҳ
	 * @param pageSize ÿҳ������
	 * @return
	 */
	public List<Student> findStudentList(int pageNo, int pageSize);
	
	/**
	 * ��ѯ����ѧ��
	 * @return
	 */
	public List<Student> findStudentList();
}
