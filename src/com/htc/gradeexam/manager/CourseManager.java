package com.htc.gradeexam.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.htc.gradeexam.model.Course;
import com.htc.gradeexam.util.DbUtil;

/**
 * �γ̹�����
 * 
 * @author �Ƶ���
 *
 */
public class CourseManager {

	private static CourseManager instance = new CourseManager();

	private CourseManager() {
	}

	public static CourseManager getInstance() {
		return instance;
	}

	/**
	 * ��ӿγ�
	 * 
	 * @param courseName
	 *            �γ�����
	 */
	public void addCourse(String courseName) {
		String sql = "insert into t_course(course_name) values(?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, courseName);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(pstmt);
			DbUtil.close(conn);
		}
	}

	/**
	 * ��ѯ���пγ�
	 * 
	 * @return
	 */
	public List<Course> findCourseList() {
		String sql = "select * from t_course order by course_id";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Course> courseList = new ArrayList<Course>();
		try {
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int courseId = rs.getInt("course_id");
				String courseName = rs.getString("course_name");
				Course course = new Course();
				course.setCourseId(courseId);
				course.setCourseName(courseName);
				courseList.add(course);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(pstmt);
			DbUtil.close(conn);
		}
		return courseList;
	}

	/**
	 * ���ݿγ̱��ɾ���γ�
	 * 
	 * @param courseId
	 *            �γ̱��
	 */
	public void delCourse(int courseId) {
		String sql = "delete from t_course where course_id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, courseId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(pstmt);
			DbUtil.close(conn);
		}
	}

	/**
	 * �޸Ŀγ�
	 * 
	 * @param courseid
	 *            �γ̱��
	 * @param courseName
	 *            �γ�����
	 */
	public void modifyCourse(int courseId, String courseName) {
		String sql = "update t_course set course_name = ? where course_id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, courseName);
			pstmt.setInt(2, courseId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(pstmt);
			DbUtil.close(conn);
		}
	}
}
