package com.htc.gradeexam.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.htc.gradeexam.model.Classes;
import com.htc.gradeexam.util.DbUtil;

/**
 * 班级管理类
 * 
 * @author 黄调聪
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
	 * 输出班级列表
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
	 * 递归读取班级树形结构
	 * 
	 * @param conn
	 * @param classId
	 *            班级父id
	 * @param level
	 *            层次感
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
				System.out.println(s + rs.getInt("classes_id") + "【" + rs.getString("classes_name") + "】");
				// 非叶子节点
				if (rs.getInt("leaf") == 0) {
					// 调用自身，递归调用
					outClassList(conn, rs.getInt("classes_id"), level + 1);
				}
			}
		} finally {
			DbUtil.close(rs);
			DbUtil.close(pstmt);
			// 不能关闭connection，否则递归会出错，而且资源遵循在哪打开在哪关闭的原则
		}
	}

	/**
	 * 添加班级
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
			// 不再自动提交事务，手动提交
			DbUtil.setAutoCommit(conn, false);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pid);
			pstmt.setString(2, classesName);
			pstmt.executeUpdate();
			// 取得当前节点的父节点
			Classes parentClasses = findClassesById(pid);
			if (parentClasses.getLeaf() == 1) {
				// 如果当前节点为叶子节点，改为非叶子
				modifyLeaf(conn, pid, 0);
			}
			// 提交事务
			DbUtil.commit(conn);
		} catch (Exception e) {
			e.printStackTrace();
			// 回滚事务
			DbUtil.rollBack(conn);
		} finally {
			DbUtil.close(pstmt);
			// 恢复连接的最初状态为自动提交
			DbUtil.setAutoCommit(conn, true);
			DbUtil.close(conn);
		}
	}

	/**
	 * 根据id查询班级
	 * 
	 * @param classesId
	 * @return 如果存在返回Classes对象，否则返回null
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
	 * 根据班级编号修改班级的叶子节点
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
			// 此处不能catch(Exception
			// e){},因为如果此处拦截异常，调用这个函数的上层函数不知道此事发生异常，会继续执行，因此需要把异常抛出，让上层函数拦截处理异常
		} finally {
			DbUtil.close(pstmt);
		}
	}

	/**
	 * 根据班级编号删除班级 1.查询该班级信息，得到其父班级编号， 2.递归删除该班级 3.查询父班级的子节点个数，判断是否有子节点
	 * 4.如果没有子节点，则将该班级的leaf字段设置为 1
	 * 
	 * @param classesId
	 */
	public void delClasses(int classesId) {
		Connection conn = null;
		conn = DbUtil.getConnection();
		try {
			DbUtil.setAutoCommit(conn, false);
			// 1.查询该班级信息，得到该班级的pid，即其父班级的classes_id
			Classes classes = findClassesById(classesId);
			// 2.递归删除班级
			delClasses(conn, classesId);
			// 3.查询该父节点下是否有子节点
			int count = getChildern(conn, classes.getPid());
			// 4.如果没有子节点，则将leaf修改为1
			if (count == 0) {
				// 修改leaf为叶子
				modifyLeaf(conn, classes.getPid(), 1);
			}
			DbUtil.commit(conn);
		} catch (Exception e) {
			e.printStackTrace();
			DbUtil.rollBack(conn);
		} finally {
			// 恢复连接的最初状态为自动提交
			DbUtil.setAutoCommit(conn, true);
			DbUtil.close(conn);
		}
	}

	/**
	 * 得到子节点个数
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
	 * 递归删除班级
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
				// 非叶子
				if (rs.getInt("leaf") == 0) {
					delClasses(conn, rs.getInt("classes_id"));
				}
				// 是叶子
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
	 * 删除具体的班级
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
	 * 修改班级名称
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
