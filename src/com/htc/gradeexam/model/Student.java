package com.htc.gradeexam.model;

import java.util.Date;

/**
 * ѧ��ʵ����
 * @author �Ƶ���
 *
 */
public class Student {

	@Override
	public String toString() {
		return "Student [ѧ����� =" + studentId + ", ѧ������ =" + studentName + ", �Ա� =" + sex + ", ��������  ="
				+ birthday + ", ��ϵ�绰 =" + contactTel + ", ��ͥסַ =" + address + ", �༶���� =" + classes.getClassName() + "]";
	}

	//ѧ�����
	private int studentId;
	
	//ѧ������
	private String studentName;
	
	//ѧ���Ա�
	private String sex;
	
	//��������
	private Date birthday;
	
 	//��ϵ��ʽ
	private String contactTel;
	
	//��ͥסַ
	private String address;
	
	//�����༶����
	private Classes classes;
	
//	//����
//	private int age;
//	
//	public int getAge() {
//		if(getBirthday()!=null){
//			long b=1000L*60L*60L*24L*365L;
//			long a=System.currentTimeMillis() - getBirthday().getTime();
//			return (int)(a/b);
//		}else {
//			return -1;
//		}
//		
//	}
//
//	public void setAge(int age) {
//		this.age = age;
//	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public Classes getClasses() {
		return classes;
	}

	public void setClasses(Classes classes) {
		this.classes = classes;
	}
}