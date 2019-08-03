
package dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

import dao.TblDetailUserJapanDao;
import entities.TblDetailUserJapan;
import ultis.Common;
import ultis.Constant;

/**
 * Class TblDetailUserJapanDaoImpl Thao tác với DB bảng tbl_detail_user_japan
 * 
 */
public class TblDetailUserJapanDaoImpl extends BaseDaoImpl implements TblDetailUserJapanDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * dao.TblDetailUserJapanDao#insertDetailUserJapan(entities.TblDetailUserJapan)
	 */
	@Override
	public boolean insertDetailUserJapan(TblDetailUserJapan tblDetailUserJapan)
			throws SQLException, ClassNotFoundException {
		boolean flag = false;
		try {
			openMySQLConnection();
			if (conn != null) {
				StringBuilder sql = new StringBuilder();
				sql.append("INSERT INTO " + Constant.TBL_DETAIL);
				sql.append("(" + Constant.TBL_DETAIL_USER_ID + "," + Constant.TBL_DETAIL_CODE_LEVEL + ","
						+ Constant.TBL_DETAIL_START_DATE + "," + Constant.TBL_DETAIL_END_DATE + ","
						+ Constant.TBL_DETAIL_TOTAL + ")");
				sql.append(" VALUES (?,?,?,?,?);");
				int i = 0;
				preparedStatement = (PreparedStatement) conn.prepareStatement(sql.toString());
				preparedStatement.setInt(++i, tblDetailUserJapan.getUserId());
				preparedStatement.setString(++i, tblDetailUserJapan.getCodeLevel());
				preparedStatement.setDate(++i, tblDetailUserJapan.getStartDate());
				preparedStatement.setDate(++i, tblDetailUserJapan.getEndDate());
				preparedStatement.setInt(++i, Common.parseInt(tblDetailUserJapan.getTotal()));
				if (preparedStatement.executeUpdate() > 0) {
					flag = true;
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			throw e;
		} finally {
			closeConnection();
		}
		return flag;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.TblDetailUserJapanDao#updateLevelUser()
	 */
	@Override
	public boolean updateLevelUser(TblDetailUserJapan tblDetailUserJapan) throws SQLException, ClassNotFoundException {
		try {
			openMySQLConnection();
			if (conn != null) {
				StringBuffer sql = new StringBuffer();
				sql.append("UPDATE " + Constant.TBL_DETAIL);
				sql.append(" SET  " + Constant.TBL_DETAIL_CODE_LEVEL + " =?, ");
				sql.append(Constant.TBL_DETAIL_START_DATE + " =?, ");
				sql.append(Constant.TBL_DETAIL_END_DATE + " =?, ");
				sql.append(Constant.TBL_DETAIL_TOTAL + " =? ");
				sql.append(" WHERE " + Constant.TBL_DETAIL_USER_ID + " = ?;");
				preparedStatement = (PreparedStatement) conn.prepareStatement(sql.toString());
				int temp = 1;
				preparedStatement.setString(temp++, tblDetailUserJapan.getCodeLevel()); // 1
				preparedStatement.setDate(temp++, tblDetailUserJapan.getStartDate()); // 2
				preparedStatement.setDate(temp++, tblDetailUserJapan.getEndDate()); // 3
				preparedStatement.setString(temp++, tblDetailUserJapan.getTotal()); // 4
				preparedStatement.setInt(temp++, tblDetailUserJapan.getUserId()); // 5

				if (preparedStatement.executeUpdate() > 0) {
					return true;
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.TblDetailUserJapanDao#checkExistLevelJapan(int)
	 */
	@Override
	public boolean checkHaveLevelJapan(int userID) throws SQLException, ClassNotFoundException {
		String codeLevel = "";
		try {
			openMySQLConnection();
			if (conn != null) {
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT " + Constant.TBL_DETAIL_CODE_LEVEL);
				sql.append(" FROM " + Constant.TBL_DETAIL);
				sql.append(" WHERE " + Constant.TBL_DETAIL_USER_ID + " = ? ;");
				preparedStatement = (PreparedStatement) conn.prepareStatement(sql.toString());
				preparedStatement.setInt(1, userID);
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					codeLevel = rs.getString(1);
				}
				if (codeLevel != "") {
					return true;
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeConnection();
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.TblDetailUserJapanDao#deleteLevelJapan(int)
	 */
	@Override
	public boolean deleteLevelJapan(int userID) throws SQLException, ClassNotFoundException {
		try {
			openMySQLConnection();
			if (conn != null) {
				StringBuilder sql = new StringBuilder();
				sql.append("DELETE FROM " + Constant.TBL_DETAIL);
				sql.append(" WHERE " + Constant.TBL_DETAIL_USER_ID + " = ? ;");
				preparedStatement = (PreparedStatement) conn.prepareStatement(sql.toString());
				preparedStatement.setInt(1, userID);
				if (preparedStatement.executeUpdate() > 0) {
					return true;
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		}
		return false;
	}

}
