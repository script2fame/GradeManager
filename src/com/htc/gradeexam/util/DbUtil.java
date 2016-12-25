package com.htc.gradeexam.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 封装数据库相关操作
 * 
 * @author Administrator
 *
 */
public class DbUtil {

	/**
	 * 取得数据库连接
	 * 
	 * @return 一个数据库连接connection
	 */
	public static Connection getConnection() {
		/*
		 * Connection conn = null; try {
		 * Class.forName("oracle.jdbc.driver.OracleDriver"); String dbUrl =
		 * "jdbc:oracle:thin:127.0.0.1:1521:ORCL"; String userName = "htc1";
		 * String password = "htc1"; conn = DriverManager.getConnection(dbUrl,
		 * userName, password);
		 * 
		 * } catch (ClassNotFoundException e) { e.printStackTrace(); } catch
		 * (SQLException e) { e.printStackTrace(); } return conn;
		 */
		Connection conn = null;
		try {
			String driverName = ExamConfigReader.getInstance().getPropertiesValue("driver-name");
			String dbUrl = ExamConfigReader.getInstance().getPropertiesValue("url");
			String userName = ExamConfigReader.getInstance().getPropertiesValue("username");
			String password = ExamConfigReader.getInstance().getPropertiesValue("password");
			Class.forName(driverName);
			conn = DriverManager.getConnection(dbUrl, userName, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 关闭 preparedStatement 对象
	 * 
	 * @param pstmt
	 *            需要关闭的preparedStatement 对象
	 */
	public static void close(PreparedStatement pstmt) {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 关闭Connection 对象
	 * 
	 * @param conn
	 *            需要关闭的Connection对象
	 */
	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 关闭rs对象
	 * 
	 * @param rs
	 */
	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 设置是否自动提交事务
	 * 
	 * @param conn
	 * @param autoCommit
	 */
	public static void setAutoCommit(Connection conn, boolean autoCommit) {
		if (conn != null) {
			try {
				conn.setAutoCommit(autoCommit);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 提交事务
	 * 
	 * @param conn
	 */
	public static void commit(Connection conn) {
		if (conn != null) {
			try {
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 回滚事务
	 * 
	 * @param conn
	 */
	public static void rollBack(Connection conn) {
		if (conn != null) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				// TODOautoCommit Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		Connection conn = DbUtil.getConnection();
		System.out.println(conn);
		DbUtil.close(conn);
	}
}
