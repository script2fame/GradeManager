package com.htc.gradeexam.manager;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.htc.gradeexam.model.Classes;
import com.htc.gradeexam.model.Student;
import com.htc.gradeexam.util.DbUtil;

public class StudentManagerImpl implements StudentManager {

	@Override
	public void addStudent(Student student) {
		String sql = "insert into t_student(classes_id,student_name,sex,birthday,contact_tel,address) values (?,?,?,?,?,?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, student.getClasses().getClassesId());
			pstmt.setString(2, student.getStudentName());
			pstmt.setString(3, student.getSex());
			pstmt.setDate(4, new Date(student.getBirthday().getTime()));
			pstmt.setString(5, student.getContactTel());
			pstmt.setString(6, student.getAddress());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(pstmt);
			DbUtil.close(conn);
		}
	}

	/**
	 * 
	 */
	public void delStudent(int studentId) {
		String sql = "delete from t_student where student_id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, studentId);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(pstmt);
			DbUtil.close(conn);
		}
	}

	/**
	 * �޸�ѧ����Ϣ
	 */
	public void modifyStudent(Student student) {
		String sql = "update t_student set classes_id=?,student_name=? ,sex=?,birthday=?,contact_tel=?,address=? where student_id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, student.getClasses().getClassesId());
			pstmt.setString(2, student.getStudentName());
			pstmt.setString(3, student.getSex());
			pstmt.setDate(4, new Date(student.getBirthday().getTime()));
			pstmt.setString(5, student.getContactTel());
			pstmt.setString(6, student.getAddress());
			pstmt.setInt(7, student.getStudentId());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(pstmt);
			DbUtil.close(conn);
		}
	}

	@Override
	public List<Student> findStudentList(int pageNo, int pageSize) {
		// select * from( select rownum as rn ,t.* from (select
		// s.*,c.classes_name from t_student s join t_classes c on
		// s.classes_id=c.classes_id order by s.student_id) t where rownum <=4)
		// where rn>2
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("select * from").append("(").append("select rownum as rn ,t.* from ").append("(")
				.append("select s.*,c.classes_name from t_student s join t_classes c on s.classes_id=c.classes_id order by s.student_id")
				.append(")").append("t where rownum <=?").append(")").append("where rn>?");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Student> studentList = new ArrayList<Student>();
		try {
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sbSql.toString());
			pstmt.setInt(1, pageSize * pageNo);
			pstmt.setInt(2, (pageNo - 1) * pageSize);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Student student = new Student();
				student.setStudentId(rs.getInt("student_id"));
				student.setStudentName(rs.getString("student_name"));
				student.setSex(rs.getString("sex"));
				student.setBirthday(rs.getDate("birthday"));
				student.setContactTel(rs.getString("contact_tel"));
				student.setAddress(rs.getString("address"));
				// �����༶����
				Classes classes = new Classes();
				classes.setClassName(rs.getString("classes_name"));
				student.setClasses(classes);

				studentList.add(student);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(rs);
			DbUtil.close(pstmt);
			DbUtil.close(conn);
		}
		return studentList;
	}

	public List<Student> findStudentList() {
		return findStudentList(1, Integer.MAX_VALUE);
	}
}
