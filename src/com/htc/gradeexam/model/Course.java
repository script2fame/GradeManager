package com.htc.gradeexam.model;
/**
 * 课程实体类
 * @author 黄调聪
 *
 */
public class Course {
	
	//课程编号
	private int courseId;
	//课程名字
	private String courseName;
	
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
}
