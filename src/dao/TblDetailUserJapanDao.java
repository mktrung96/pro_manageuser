
package dao;

import java.sql.SQLException;

import entities.TblDetailUserJapan;

/**
 * Interface Thao tác với DB bảng tbl_detail_user_japan
 * 
 */
public interface TblDetailUserJapanDao extends BaseDao {

	/**
	 * @param tblDetailUserJapan
	 * @throws Exception
	 */
	boolean insertDetailUserJapan(TblDetailUserJapan tblDetailUserJapan) throws SQLException, ClassNotFoundException;

	/**
	 * @param userID
	 * @return true nếu user đó có trình độ tiếng Nhật
	 * @throws SQLException | ClassNotFoundException
	 */
	boolean checkHaveLevelJapan(int userID) throws SQLException, ClassNotFoundException;

	/**
	 * @param userID
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	boolean deleteLevelJapan(int userID) throws SQLException, ClassNotFoundException;

	/**
	 * @param tblDetailUserJapan
	 * @return
	 * @throws Exception
	 */
	boolean updateLevelUser(TblDetailUserJapan tblDetailUserJapan) throws SQLException, ClassNotFoundException;

}
