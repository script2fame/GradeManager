package com.htc.gradeexam.model;

/**
 * �ɼ�ʵ����
 * @author �Ƶ���
 *
 */

public class Grade {

	//����ѧ������
	private Student student;
	
	//�����γ̶���
	private Course course;
	
	//����ɼ�
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
