package com.htc.gradeexam.model;

import java.util.Date;

/**
 * 学生实体类
 * @author 黄调聪
 *
 */
public class Student {

	@Override
	public String toString() {
		return "Student [学生编号 =" + studentId + ", 学生姓名 =" + studentName + ", 性别 =" + sex + ", 出生日期  ="
				+ birthday + ", 联系电话 =" + contactTel + ", 家庭住址 =" + address + ", 班级名称 =" + classes.getClassName() + "]";
	}

	//学生编号
	private int studentId;
	
	//学生姓名
	private String studentName;
	
	//学生性别
	private String sex;
	
	//出生日期
	private Date birthday;
	
 	//联系方式
	private String contactTel;
	
	//家庭住址
	private String address;
	
	//关联班级对象
	private Classes classes;
	
//	//年龄
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