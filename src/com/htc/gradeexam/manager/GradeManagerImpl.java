package com.htc.gradeexam.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.htc.gradeexam.model.Classes;
import com.htc.gradeexam.model.Course;
import com.htc.gradeexam.model.Grade;
import com.htc.gradeexam.model.Student;
import com.htc.gradeexam.util.DbUtil;

public class GradeManagerImpl implements GradeManager {

	@Override
	public void addGrade(int studentId, int courseId, float grade) {
		String sql = "insert into t_grade(student_id,course_id,grade) values (?,?,?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, studentId);
			pstmt.setInt(2, courseId);
			pstmt.setFloat(3, grade);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(pstmt);
			DbUtil.close(conn);
		}
	}

	@Override
	public void delGrade(int studentId, int courseId) {
		String sql = "delete from t_grade where student_id = ? and course_id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, studentId);
			pstmt.setInt(2, courseId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(pstmt);
			DbUtil.close(conn);
		}

	}

	@Override
	public void modifyGrade(int studentId, int courseId, float grade) {
		String sql = "update t_grade set grade = ? where student_id = ? and course_id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setFloat(1, grade);
			pstmt.setInt(2, studentId);
			pstmt.setInt(3, courseId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(pstmt);
			DbUtil.close(conn);
		}

	}

	@Override
	public List<Grade> findGradeListByStudentId(int studentId) {
		// sql92语法
		/*
		 * select
		 * g.student_id,s.student_name,cls.classes_name,c.course_name,g.grade
		 * from t_grade g,t_student s,t_classes cls,t_course c where
		 * g.student_id=s.student_id and s.classes_id=cls.classes_id and
		 * g.course_id=c.course_id and g.student_id=13201501
		 */
		// sql99语法

		StringBuffer sb = new StringBuffer();
		sb.append("select g.student_id,s.student_name,cls.classes_name,c.course_name,g.grade ")
				.append("from t_grade g join t_student s on g.student_id=s.student_id join t_classes cls on s.classes_id=cls.classes_id join t_course c on g.course_id = c.course_id ")
				.append("where g.student_id = ?");
		String sql = sb.toString();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Grade> gradeList = null;
		try {
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, studentId);
			rs = pstmt.executeQuery();
			gradeList = makeGradeList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(pstmt);
			DbUtil.close(conn);
		}
		return gradeList;
	}

	@Override
	public List<Grade> findHighestGradeListOfPerCourse() {
		StringBuffer sb = new StringBuffer();
		sb.append("select s.student_id,s.student_name,c.course_name,cls.classes_name,g.grade ");
		sb.append("from t_grade g join t_student s on g.student_id = s.student_id ");
		sb.append("join t_classes cls on s.classes_id = cls.classes_id ");
		sb.append("join t_course c on g.course_id = c.course_id ");
		sb.append("where g.grade in (select max(gg.grade) from t_grade gg where gg.course_id = c.course_id) ");
		String sql = sb.toString();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Grade> gradeList = null;
		try {
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			gradeList = makeGradeList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(pstmt);
			DbUtil.close(conn);
		}
		return gradeList;
	}

	@Override
	public List<Grade> findGradeListTop3() {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from ");
		sb.append("( ");
		sb.append("select g.student_id,s.student_name,cls.classes_name,sum(g.grade) total_grade ");
		sb.append("from  ");
		sb.append("t_grade g join t_student s on g.student_id = s.student_id join t_classes cls on s.classes_id = cls.classes_id  ");
		sb.append("group by g.student_id,s.student_name,cls.classes_name ");
		sb.append("order by total_grade desc ");
		sb.append(") ");
		sb.append("where rownum <=3 ");
		String sql = sb.toString();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Grade> gradeList = new ArrayList<>();
		try {
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Grade grade = new Grade();
				Student student = new Student();
				Classes classes = new Classes();
				student.setStudentId(rs.getInt("student_id"));
				student.setStudentName(rs.getString("student_name"));
				classes.setClassName(rs.getString("classes_name"));
				// 设置学生与班级之间的关联
				student.setClasses(classes);
				// 设置成绩与学生之间的关系
				grade.setStudent(student);
				// 设置具体成绩
				grade.setGrade(rs.getFloat("total_grade"));
				gradeList.add(grade);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(pstmt);
			DbUtil.close(conn);
		}
		return gradeList;
	}

	@Override
	public List<Grade> findGradeList(int pageNo, int pageSize) {
		StringBuffer sb = new StringBuffer();
		sb.append("select s.* from ").append("( ").append("select rownum as rn, t.* from ").append("( ")
				.append("select g.student_id,s.student_name,cls.classes_name,c.course_name,g.grade ")
				.append("from t_grade g join t_student s on g.student_id=s.student_id join t_classes cls on s.classes_id=cls.classes_id join t_course c on g.course_id = c.course_id ")
				.append(") ").append("t where rownum <=? ").append(") ").append("s where s.rn > ? ");
		String sql = sb.toString();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Grade> gradeList = null;
		try {
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pageNo * pageSize);
			pstmt.setInt(2, (pageNo - 1) * pageSize);
			rs = pstmt.executeQuery();
			gradeList = makeGradeList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(pstmt);
			DbUtil.close(conn);
		}
		return gradeList;
	}

	/**
	 * 构造成绩列表对象
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private List<Grade> makeGradeList(ResultSet rs) throws SQLException {
		List<Grade> gradeList = new ArrayList<Grade>();
		while (rs.next()) {
			Grade grade = new Grade();
			Student student = new Student();
			Classes classes = new Classes();
			Course course = new Course();
			student.setStudentId(rs.getInt("student_id"));
			student.setStudentName(rs.getString("student_name"));
			classes.setClassName(rs.getString("classes_name"));
			// 设置学生与班级之间的关联
			student.setClasses(classes);
			course.setCourseName(rs.getString("course_name"));
			// 设置成绩与学生之间的关系
			grade.setStudent(student);
			// 设置成绩与课程之间的关系
			grade.setCourse(course);
			// 设置具体成绩
			grade.setGrade(rs.getFloat("grade"));
			gradeList.add(grade);
		}
		return gradeList;
	}
}
