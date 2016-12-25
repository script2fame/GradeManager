package com.htc.gradeexam.manager;

import java.util.List;

import com.htc.gradeexam.model.Grade;

/**
 * �ɼ�����ҵ���߼��ӿ�
 * 
 * @author �Ƶ���
 *
 */

public interface GradeManager {

	/**
	 * ��ӳɼ�
	 * 
	 * @param studentId
	 * @param courseId
	 * @param grade
	 */
	public void addGrade(int studentId, int courseId, float grade);

	/**
	 * ɾ���ɼ�
	 * 
	 * @param studentId
	 * @param courseId
	 */
	public void delGrade(int studentId, int courseId);

	/**
	 * �޸ĳɼ�
	 * 
	 * @param studentId
	 * @param courseId
	 * @param Grade
	 */
	public void modifyGrade(int studentId, int courseId, float grade);

	/**
	 * ����ѧ����Ų�ѯ�ɼ�
	 * 
	 * @param studentId
	 * @return
	 */
	public List<Grade> findGradeListByStudentId(int studentId);

	/**
	 * ��ѯÿ����߷�
	 * 
	 * @return
	 */
	public List<Grade> findHighestGradeListOfPerCourse();

	/**
	 * ��ѯ�ܷ�ǰ����
	 * 
	 * @return
	 */
	public List<Grade> findGradeListTop3();

	/**
	 * ��ҳ��ѯ�ɼ�
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<Grade> findGradeList(int pageNo, int pageSize);
}
