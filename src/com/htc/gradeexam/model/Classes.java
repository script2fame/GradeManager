package com.htc.gradeexam.model;
/**
 * 班级实体类
 * @author 黄调聪
 *
 */
public class Classes {

	//班级编号
	private int classesId;
	
	//班级名称
	private String className;
	
	//是否为叶子 1:是叶子 0：不是叶子
	private int leaf;
	
	//private Classes parent;
	//private set<Classes> children;
	
	//父id
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
