package com.htc.gradeexam.model;

/**
 * 成绩实体类
 * @author 黄调聪
 *
 */

public class Grade {

	//关联学生对象
	private Student student;
	
	//关联课程对象
	private Course course;
	
	//具体成绩
	private float grade;

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public float getGrade() {
		return grade;
	}

	public void setGrade(float grade) {
		this.grade = grade;
	}
	
}
