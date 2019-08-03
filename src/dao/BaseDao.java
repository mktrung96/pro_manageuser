
package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;

/**
 * interface kết nối database
 * 
 *
 */
public interface BaseDao {

	/**
	 * Phương thức kết nối với data base
	 * 
	 * @return connection trả về đối tượng connection
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void openMySQLConnection() throws SQLException, ClassNotFoundException;

	/**
	 * Đóng kết nối với database
	 * 
	 * @throws SQLException
	 */
	public void closeConnection();

	public void setConn(Connection conn);

	public Connection getConn() throws ClassNotFoundException, SQLException;

	public void setFalseAutoCommit() throws SQLException;

	/**
	 * @param nameTable
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	ArrayList<String> getColumnSort(String nameTable) throws SQLException, ClassNotFoundException;
}
