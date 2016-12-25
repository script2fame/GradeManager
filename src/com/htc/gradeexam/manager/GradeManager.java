package com.htc.gradeexam.manager;

import java.util.List;

import com.htc.gradeexam.model.Grade;

/**
 * 成绩管理业务逻辑接口
 * 
 * @author 黄调聪
 *
 */

public interface GradeManager {

	/**
	 * 添加成绩
	 * 
	 * @param studentId
	 * @param courseId
	 * @param grade
	 */
	public void addGrade(int studentId, int courseId, float grade);

	/**
	 * 删除成绩
	 * 
	 * @param studentId
	 * @param courseId
	 */
	public void delGrade(int studentId, int courseId);

	/**
	 * 修改成绩
	 * 
	 * @param studentId
	 * @param courseId
	 * @param Grade
	 */
	public void modifyGrade(int studentId, int courseId, float grade);

	/**
	 * 根据学生编号查询成绩
	 * 
	 * @param studentId
	 * @return
	 */
	public List<Grade> findGradeListByStudentId(int studentId);

	/**
	 * 查询每科最高分
	 * 
	 * @return
	 */
	public List<Grade> findHighestGradeListOfPerCourse();

	/**
	 * 查询总分前三名
	 * 
	 * @return
	 */
	public List<Grade> findGradeListTop3();

	/**
	 * 分页查询成绩
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<Grade> findGradeList(int pageNo, int pageSize);
}
