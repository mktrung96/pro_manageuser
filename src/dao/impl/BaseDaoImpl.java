
package dao.impl;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.DatabaseMetaData;
import com.mysql.jdbc.PreparedStatement;

import dao.BaseDao;
import ultis.DatabaseProperties;

/**
 * Class BaseDaoImpl kết nối tới database
 * 
 * @author NamĐH
 */
public class BaseDaoImpl implements BaseDao {
	// Khai báo thuộc tính
	protected Connection conn = null;
	protected PreparedStatement preparedStatement = null;

	/**
	 * Phương thức kết nối với data base
	 * 
	 * @return connection trả về đối tượng connection
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Override
	public void openMySQLConnection() throws SQLException, ClassNotFoundException {
		// Tai lop Driver
		Class.forName(DatabaseProperties.getValue("class"));
		// Tao doi tuong Connection
		conn = (Connection) DriverManager.getConnection(DatabaseProperties.getValue("url"),
				DatabaseProperties.getValue("user"), DatabaseProperties.getValue("pass"));
	}

	/**
	 * Đóng kết nối với database
	 * 
	 * @throws SQLException
	 */
	@Override
	public void closeConnection() {
		// Kiểm tra connention khác null và chưa đóng thì đóng connection
		try {
			if (conn != null || !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Phương thức lấy tên các cột của bảng trong sql
	 * 
	 * @param nameTable bảng cần lấy tên cột
	 * @return ArrayList String mảng tên cột
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Override
	public ArrayList<String> getColumnSort(String nameTable) throws SQLException, ClassNotFoundException {
		ArrayList<String> listNameColumn = new ArrayList<>();
		// nếu Connection đã có thì thực thi dòng câu lệnh phía dưới
		if (conn != null) {
			// khai báo câu lệnh SQL và append lần lượt các giá trị
			// đã đúng điều kiện
			DatabaseMetaData metaData = (DatabaseMetaData) conn.getMetaData();
			ResultSet rs = metaData.getColumns(null, null, nameTable, null);
			while (rs.next()) {
				listNameColumn.add(nameTable + "." + rs.getString("COLUMN_NAME"));
			}
		}
		return listNameColumn;
	}

	@Override
	public Connection getConn() {
		return conn;
	}

	@Override
	public void setConn(Connection conn) {
		this.conn = conn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.BaseDao#setFalseAutoCommit()
	 */
	@Override
	public void setFalseAutoCommit() throws SQLException {
		if (conn != null) {
			conn.setAutoCommit(false);
		}
	}

}
