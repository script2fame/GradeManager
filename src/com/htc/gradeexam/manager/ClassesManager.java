package com.htc.gradeexam.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.htc.gradeexam.model.Classes;
import com.htc.gradeexam.util.DbUtil;

/**
 * �༶������
 * 
 * @author �Ƶ���
 *
 */

public class ClassesManager {

	private static ClassesManager instance = new ClassesManager();

	private ClassesManager() {
	}

	public static ClassesManager getInstance() {
		return instance;
	}

	/**
	 * ����༶�б�
	 */
	public void outClassList() {
		Connection conn = null;
		try {
			conn = DbUtil.getConnection();
			outClassList(conn, 0, 0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(conn);
		}
	}

	/**
	 * �ݹ��ȡ�༶���νṹ
	 * 
	 * @param conn
	 * @param classId
	 *            �༶��id
	 * @param level
	 *            ��θ�
	 * @throws SQLException
	 */
	private void outClassList(Connection conn, int classId, int level) throws SQLException {
		String sql = "select * from t_classes where pid=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String s = "";
			for (int i = 0; i < level; i++) {
				s += "  ";
			}
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, classId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				System.out.println(s + rs.getInt("classes_id") + "��" + rs.getString("classes_name") + "��");
				// ��Ҷ�ӽڵ�
				if (rs.getInt("leaf") == 0) {
					// ���������ݹ����
					outClassList(conn, rs.getInt("classes_id"), level + 1);
				}
			}
		} finally {
			DbUtil.close(rs);
			DbUtil.close(pstmt);
			// ���ܹر�connection������ݹ�����������Դ��ѭ���Ĵ����Ĺرյ�ԭ��
		}
	}

	/**
	 * ��Ӱ༶
	 * 
	 * @param pid
	 * @param classesName
	 */
	public void addClasses(int pid, String classesName) {
		String sql = "insert into t_classes(pid,classes_name) values (?,?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DbUtil.getConnection();
			// �����Զ��ύ�����ֶ��ύ
			DbUtil.setAutoCommit(conn, false);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pid);
			pstmt.setString(2, classesName);
			pstmt.executeUpdate();
			// ȡ�õ�ǰ�ڵ�ĸ��ڵ�
			Classes parentClasses = findClassesById(pid);
			if (parentClasses.getLeaf() == 1) {
				// �����ǰ�ڵ�ΪҶ�ӽڵ㣬��Ϊ��Ҷ��
				modifyLeaf(conn, pid, 0);
			}
			// �ύ����
			DbUtil.commit(conn);
		} catch (Exception e) {
			e.printStackTrace();
			// �ع�����
			DbUtil.rollBack(conn);
		} finally {
			DbUtil.close(pstmt);
			// �ָ����ӵ����״̬Ϊ�Զ��ύ
			DbUtil.setAutoCommit(conn, true);
			DbUtil.close(conn);
		}
	}

	/**
	 * ����id��ѯ�༶
	 * 
	 * @param classesId
	 * @return ������ڷ���Classes���󣬷��򷵻�null
	 */
	public Classes findClassesById(int classesId) {
		String sql = "select * from t_classes where classes_id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Classes classes = null;
		try {
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, classesId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				classes = new Classes();
				classes.setClassesId(classesId);
				classes.setClassName(rs.getString("classes_name"));
				classes.setPid(rs.getInt("pid"));
				classes.setLeaf(rs.getInt("leaf"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(rs);
			DbUtil.close(pstmt);
			DbUtil.close(conn);
		}
		return classes;
	}

	/**
	 * ���ݰ༶����޸İ༶��Ҷ�ӽڵ�
	 * 
	 * @param conn
	 * @param classesId
	 * @param leaf
	 * @throws SQLException
	 */
	private void modifyLeaf(Connection conn, int classesId, int leaf) throws SQLException {
		String sql = "update t_classes set leaf = ? where classes_id = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, leaf);
			pstmt.setInt(2, classesId);
			pstmt.executeUpdate();
			// �˴�����catch(Exception
			// e){},��Ϊ����˴������쳣����������������ϲ㺯����֪�����·����쳣�������ִ�У������Ҫ���쳣�׳������ϲ㺯�����ش����쳣
		} finally {
			DbUtil.close(pstmt);
		}
	}

	/**
	 * ���ݰ༶���ɾ���༶ 1.��ѯ�ð༶��Ϣ���õ��丸�༶��ţ� 2.�ݹ�ɾ���ð༶ 3.��ѯ���༶���ӽڵ�������ж��Ƿ����ӽڵ�
	 * 4.���û���ӽڵ㣬�򽫸ð༶��leaf�ֶ�����Ϊ 1
	 * 
	 * @param classesId
	 */
	public void delClasses(int classesId) {
		Connection conn = null;
		conn = DbUtil.getConnection();
		try {
			DbUtil.setAutoCommit(conn, false);
			// 1.��ѯ�ð༶��Ϣ���õ��ð༶��pid�����丸�༶��classes_id
			Classes classes = findClassesById(classesId);
			// 2.�ݹ�ɾ���༶
			delClasses(conn, classesId);
			// 3.��ѯ�ø��ڵ����Ƿ����ӽڵ�
			int count = getChildern(conn, classes.getPid());
			// 4.���û���ӽڵ㣬��leaf�޸�Ϊ1
			if (count == 0) {
				// �޸�leafΪҶ��
				modifyLeaf(conn, classes.getPid(), 1);
			}
			DbUtil.commit(conn);
		} catch (Exception e) {
			e.printStackTrace();
			DbUtil.rollBack(conn);
		} finally {
			// �ָ����ӵ����״̬Ϊ�Զ��ύ
			DbUtil.setAutoCommit(conn, true);
			DbUtil.close(conn);
		}
	}

	/**
	 * �õ��ӽڵ����
	 * 
	 * @param conn
	 * @param classesId
	 * @return
	 * @throws SQLException
	 */
	private int getChildern(Connection conn, int classesId) throws SQLException {
		String sql = "select count(*) from t_classes where pid = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, classesId);
			rs = pstmt.executeQuery();
			rs.next();
			// count = rs.getInt("count(*)");
			count = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			DbUtil.close(rs);
			DbUtil.close(pstmt);
		}

		return count;
	}

	/**
	 * �ݹ�ɾ���༶
	 * 
	 * @param conn
	 * @param classesId
	 * @throws Exception
	 */
	private void delClasses(Connection conn, int classesId) throws Exception {
		String sql = "select * from t_classes where pid = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, classesId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// ��Ҷ��
				if (rs.getInt("leaf") == 0) {
					delClasses(conn, rs.getInt("classes_id"));
				}
				// ��Ҷ��
				delClassesById(conn, rs.getInt("classes_id"));
			}
			delClassesById(conn, classesId);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DbUtil.close(rs);
			DbUtil.close(pstmt);
		}
	}

	/**
	 * ɾ������İ༶
	 * 
	 * @param conn
	 * @param classesId
	 * @throws SQLException
	 */
	private void delClassesById(Connection conn, int classesId) throws SQLException {
		String sql = "delete from t_classes where classes_id = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, classesId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			DbUtil.close(pstmt);
		}
	}

	/**
	 * �޸İ༶����
	 * 
	 * @param classesId
	 * @param classesName
	 */
	public void ModifyById(int classesId, String newClassesName) {
		String sql = "update t_classes set classes_name = ? where classes_id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newClassesName);
			pstmt.setInt(2, classesId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(pstmt);
			DbUtil.close(conn);
		}

	}

	public static void main(String[] args) {
		ClassesManager.getInstance().outClassList();
	}
}
