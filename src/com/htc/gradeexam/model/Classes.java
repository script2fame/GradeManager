package com.htc.gradeexam.model;
/**
 * �༶ʵ����
 * @author �Ƶ���
 *
 */
public class Classes {

	//�༶���
	private int classesId;
	
	//�༶����
	private String className;
	
	//�Ƿ�ΪҶ�� 1:��Ҷ�� 0������Ҷ��
	private int leaf;
	
	//private Classes parent;
	//private set<Classes> children;
	
	//��id
	private int pid;

	public int getClassesId() {
		return classesId;
	}

	public void setClassesId(int classesId) {
		this.classesId = classesId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getLeaf() {
		return leaf;
	}

	public void setLeaf(int leaf) {
		this.leaf = leaf;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}
	
}
