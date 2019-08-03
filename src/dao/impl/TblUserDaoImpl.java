
package dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

import dao.TblUserDao;
import entities.TblUser;
import entities.UserInfo;
import ultis.Common;
import ultis.Constant;

public class TblUserDaoImpl extends BaseDaoImpl implements TblUserDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblUserDao#getListUser()
	 */
	@Override
	public TblUser getAdmin(String user) throws SQLException, ClassNotFoundException {
		try {
			// Khởi tạo đối tượng tblUser để trả về
			TblUser userAdmin = new TblUser();
			// mở kết nối tới database
			openMySQLConnection();
			// nếu Connection đã có thì thực thi dòng câu lệnh phía dưới
			if (conn != null) {
				// khai báo câu lệnh SQL và append lần lượt các giá trị
				// đã đúng điều kiện
				StringBuilder sqlB = new StringBuilder();
				sqlB.append("SELECT " + Constant.TBL_USER_PASS + ", " + Constant.TBL_USER_SALT);
				sqlB.append(" FROM " + Constant.TBL_USER);
				sqlB.append(" where ");
				sqlB.append(Constant.TBL_USER_LOGIN_NAME + "  = ?");
				sqlB.append(" AND ");
				sqlB.append(Constant.TBL_USER_RULE + "  = ?");
				sqlB.append(";");
				// Khai báo vị tri parameter
				int i = 0;
				// chuyển stringbuilder sang string
				String sql = sqlB.toString();
				preparedStatement = (PreparedStatement) conn.prepareStatement(sql);
				// Gán các giá trị lên preparedStatement
				preparedStatement.setString(++i, user);
				preparedStatement.setInt(++i, Constant.ADMIN);
				// get data theo câu lệnh truy vấn sql ở trên.
				// lấy danh sách kết quả đã tìm kiếm được
				ResultSet rs = preparedStatement.executeQuery();
				// lấy từng kết quả gán vào đối tượng tblUser để trả về
				while (rs.next()) {
					userAdmin.setPass(rs.getString(Constant.TBL_USER_PASS));
					userAdmin.setSalt(rs.getString(Constant.TBL_USER_SALT));
				}
				// trả về đối tương
				return userAdmin;
			}
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
			throw e;

		} finally {
			closeConnection();
		}
		// không tìm thấy trả về null
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblUserDao#getListUser()
	 */
	@Override
	public int getTotalUser(int groupID, String fullName) throws SQLException, ClassNotFoundException {
		int result = -1;
		try {
			// mở kết nối tới database
			openMySQLConnection();
			// nếu Connection đã có thì thực thi dòng câu lệnh phía dưới
			if (conn != null) {
				// khai báo câu lệnh SQL và append lần lượt các giá trị
				// đã đúng điều kiện
				StringBuilder sqlB = new StringBuilder();
				sqlB.append("SELECT COUNT(" + Constant.TBL_USER_USER_ID);
				sqlB.append(") FROM " + Constant.TBL_USER);
				sqlB.append(" where 1=1");
				// Kiểm tra nếu groupID != 0 thì thêm vào điều kiện groupID
				if (groupID != 0) {
					sqlB.append(" AND ");
					sqlB.append(Constant.TBL_USER_GROUP_ID + " = ? ");
				}
				if (fullName != null) {
					sqlB.append(" AND ");
					sqlB.append(Constant.TBL_USER_FULLNAME + " like CONVERT(?, BINARY) ");
				}
				sqlB.append(";");
				// Khai báo vị tri parameter
				int i = 0;
				// chuyển stringbuilder sang string
				String sql = sqlB.toString();
				preparedStatement = (PreparedStatement) conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				if (groupID != 0) {
					preparedStatement.setInt(++i, groupID);
				}
				if (fullName != null) {
					// Gán các giá trị lên preparedStatement
					preparedStatement.setString(++i, "%" + fullName + "%");
				}
				// get data theo câu lệnh truy vấn sql ở trên.
				// lấy danh sách kết quả đã tìm kiếm được
				ResultSet rs = preparedStatement.executeQuery();
				// lấy kết quả trả về
				while (rs.next()) {
					result = rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw e;

		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
			throw e;
		} finally {
			closeConnection();
		}
		// Nếu bị lỗi thấy trả về -1
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblUserDao#getListUser()
	 */
	@Override
	public ArrayList<UserInfo> getListUser(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate)
			throws SQLException, ClassNotFoundException {
		try {
			// khởi tạo danh sách trả về
			ArrayList<UserInfo> listUserInfo = new ArrayList<>();
			// mở kết nối tới database
			openMySQLConnection();
			// nếu Connection đã có thì thực thi dòng câu lệnh phía dưới
			if (conn != null) {
				// khai báo câu lệnh SQL và append lần lượt các giá trị
				// đã đúng điều kiện
				StringBuilder sqlB = new StringBuilder();
				sqlB.append("SELECT " + Constant.TBL_USER_USER_ID + ", ");
				sqlB.append(Constant.TBL_USER_FULLNAME + ", " + Constant.TBL_USER_BIRTHDAY + ", ");
				sqlB.append(Constant.MST_GROUP_GROUP_NAME + ", " + Constant.TBL_USER_EMAIL + ", ");
				sqlB.append(Constant.TBL_USER_TEL + ", " + Constant.MST_JAPAN_NAME_LEVEL + ", ");
				sqlB.append(Constant.TBL_DETAIL_END_DATE + ", " + Constant.TBL_DETAIL_TOTAL);
				sqlB.append(" FROM ");
				sqlB.append(Constant.TBL_USER + " INNER JOIN " + Constant.MST_GROUP);
				sqlB.append(" ON ");
				sqlB.append(Constant.TBL_USER_GROUP_ID + " = " + Constant.MST_GROUP_GROUP_ID);
				sqlB.append(" LEFT JOIN ");
				sqlB.append(Constant.TBL_DETAIL + " INNER JOIN " + Constant.MST_JAPAN);
				sqlB.append(" ON ");
				sqlB.append(Constant.TBL_DETAIL_CODE_LEVEL + " = " + Constant.MST_JAPAN_CODE_LEVEL);
				sqlB.append(" ON ");
				sqlB.append(Constant.TBL_USER_USER_ID + " = " + Constant.TBL_DETAIL_USER_ID);
				sqlB.append(" where 1=1");
				// Kiểm tra xem tìm kiesm group id kiểu tất cả hay theo group id
				if (groupId != 0) {
					sqlB.append(" AND ");
					sqlB.append(Constant.TBL_USER_GROUP_ID + " = ?");
				}
				if (fullName != null) {
					sqlB.append(" AND ");
					sqlB.append(Constant.TBL_USER_FULLNAME + " like CONVERT(?, BINARY) ");

				}
				// kiểm tra hàm check
				ArrayList<String> listNameColumn = getColumnSort(Constant.TBL_USER);
				listNameColumn.addAll(getColumnSort(Constant.TBL_DETAIL));
				listNameColumn.addAll(getColumnSort(Constant.MST_JAPAN));
				// Không có trong list thì không sắp xếp
				if (Common.checkColumnSort(sortType, listNameColumn)) {
					sqlB.append(" ORDER BY ");
					switch (sortType) {
					case Constant.TBL_USER_FULLNAME:
						sqlB.append(Constant.TBL_USER_FULLNAME + " " + sortByFullName);
						sqlB.append(" , ");
						sqlB.append(" (case when " + Constant.TBL_DETAIL_CODE_LEVEL + " is null then 'N6' else "
								+ Constant.TBL_DETAIL_CODE_LEVEL + " end) " + sortByCodeLevel);
						sqlB.append(" , ");
						sqlB.append(Constant.TBL_DETAIL_END_DATE + " " + sortByEndDate);
						break;
					case Constant.MST_JAPAN_CODE_LEVEL:
						sqlB.append(" (case when " + Constant.TBL_DETAIL_CODE_LEVEL + " is null then 'N6' else "
								+ Constant.TBL_DETAIL_CODE_LEVEL + " end) " + sortByCodeLevel);
						sqlB.append(" , ");
						sqlB.append(Constant.TBL_USER_FULLNAME + " " + sortByFullName);
						sqlB.append(" , ");
						sqlB.append(Constant.TBL_DETAIL_END_DATE + " " + sortByEndDate);
						break;
					case Constant.TBL_DETAIL_END_DATE:
						sqlB.append(Constant.TBL_DETAIL_END_DATE + " " + sortByEndDate);
						sqlB.append(" , ");
						sqlB.append(Constant.TBL_USER_FULLNAME + " " + sortByFullName);
						sqlB.append(" , ");
						sqlB.append(" (case when " + Constant.TBL_DETAIL_CODE_LEVEL + " is null then 'N6' else "
								+ Constant.TBL_DETAIL_CODE_LEVEL + " end) " + sortByCodeLevel);
						break;
					default:
						break;
					}
				}
				// thêm điều kiện lấy ra bao nhiêu bản ghi
				sqlB.append(" LIMIT " + limit);
				// thêm điều kiện lấy từ bản ghi thứ bao nhiêu
				sqlB.append(" OFFSET " + offset);
				sqlB.append(";");
				// chuyển stringbuilder sang string
				String sql = sqlB.toString();
				// Khai báo vị tri parameter
				int i = 0;
				preparedStatement = (PreparedStatement) conn.prepareStatement(sql);
				// Chuyển đối dữ liệu fullname truyền vào tránh kí tự wilcard
				if (groupId != 0) {
					preparedStatement.setInt(++i, groupId);
				}
				// Gán các giá trị lên preparedStatement
				if (fullName != null) {
					preparedStatement.setString(++i, "%" + fullName + "%");
				}
				ResultSet rs = preparedStatement.executeQuery();
				// lây tất cả bản ghi tìm được và add vào danh sách
				while (rs.next()) {
					UserInfo userInfo = new UserInfo();
					userInfo.setUserID(rs.getInt(Constant.TBL_USER_USER_ID));
					userInfo.setFullName(rs.getString(Constant.TBL_USER_FULLNAME));
					userInfo.setBirthday(rs.getDate(Constant.TBL_USER_BIRTHDAY));
					userInfo.setGroupName(rs.getString(Constant.MST_GROUP_GROUP_NAME));
					userInfo.setEmail(rs.getString(Constant.TBL_USER_EMAIL));
					userInfo.setTel(rs.getString(Constant.TBL_USER_TEL));
					userInfo.setNameLevel(rs.getString(Constant.MST_JAPAN_NAME_LEVEL));
					userInfo.setEndDate(rs.getDate(Constant.TBL_DETAIL_END_DATE));
					userInfo.setTotal(String.valueOf(rs.getInt(Constant.TBL_DETAIL_TOTAL)));
					listUserInfo.add(userInfo);
				}
				// trả về danh sách đã tìm kiếm được
				return listUserInfo;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw e;
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
			throw e;
		} finally {
			closeConnection();
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.TblUserDao#getUserByLoginName(java.lang.String)
	 */
	@Override
	public boolean getUserByLoginName(Integer userId, String loginName) throws SQLException, ClassNotFoundException {
		boolean flag = false;
		try {
			openMySQLConnection();
			if (conn != null) {
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT * FROM tbl_user WHERE login_name = ? ");
				if (userId > 0) {
					sql.append("and user_id = ?");
				}
				int i = 0;
				preparedStatement = (PreparedStatement) conn.prepareStatement(sql.toString());
				preparedStatement.setString(++i, loginName);
				if (userId > 0) {
					preparedStatement.setInt(++i, userId);
				}
				ResultSet rs = preparedStatement.executeQuery();
				if (rs.next()) {
					flag = true;

				}
			}

		} catch (Exception e) {
			throw e;
		} finally {
			closeConnection();
		}
		return flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.TblUserDao#getUserByEmail(java.lang.String)
	 */
	@Override
	public boolean getUserByEmail(String email) throws SQLException, ClassNotFoundException {
		boolean flag = false;
		try {
			openMySQLConnection();
			if (conn != null) {
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT * FROM tbl_user WHERE email = ?");
				int i = 0;
				preparedStatement = (PreparedStatement) conn.prepareStatement(sql.toString());
				preparedStatement.setString(++i, email);
				ResultSet rs = preparedStatement.executeQuery();
				if (rs.next()) {
					flag = true;
				}
			}

		} catch (Exception e) {
			throw e;
		} finally {
			closeConnection();
		}
		return flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.TblUserDao#getUserByGroupId(int)
	 */
	@Override
	public boolean checkExistedGroupId(int groupId) throws SQLException, ClassNotFoundException {
		int temp = 0;
		try {
			openMySQLConnection();
			if (conn != null) {
				StringBuilder sql = new StringBuilder();

				sql.append("SELECT count(" + Constant.MST_GROUP_GROUP_ID + ") ");
				sql.append(" FROM " + Constant.MST_GROUP);
				sql.append(" WHERE " + Constant.MST_GROUP_GROUP_ID + " = ?;");
				int i = 0;
				preparedStatement = (PreparedStatement) conn.prepareStatement(sql.toString());
				preparedStatement.setInt(++i, groupId);
				ResultSet rs = preparedStatement.executeQuery();

				while (rs.next()) {
					temp = rs.getInt(1);
				}
				if (temp != 0) { // tìm thấy thì return true
					return true;
				}

			}

		} catch (SQLException | ClassNotFoundException e) {
			throw e;
		} finally {
			closeConnection();
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.TblUserDao#checkExistedLoginName(java.lang.String)
	 */
	@Override
	public boolean checkExistedLoginName(String loginName) throws SQLException, ClassNotFoundException {
		boolean flag = false;
		try {
			openMySQLConnection();
			if (conn != null) {
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT * FROM tbl_user WHERE login_name = ?");
				int i = 0;
				preparedStatement = (PreparedStatement) conn.prepareStatement(sql.toString());
				preparedStatement.setString(++i, loginName);
				ResultSet rs = preparedStatement.executeQuery();
				if (rs.next()) {
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
	 * @see dao.TblUserDao#insertUser(entities.TblDetailUserJapan)
	 */
	@Override
	public int insertUser(TblUser tblUser) throws SQLException, ClassNotFoundException {
		int generatedKey = 0;
		try {
			openMySQLConnection();
			if (conn != null) {
				StringBuilder sql = new StringBuilder();
				sql.append("INSERT INTO " + Constant.TBL_USER);
				sql.append("(" + Constant.TBL_USER_GROUP_ID + "," + Constant.TBL_USER_LOGIN_NAME + ","
						+ Constant.TBL_USER_PASS);
				sql.append("," + Constant.TBL_USER_FULLNAME + "," + Constant.TBL_USER_FULLNAME_KANA + ","
						+ Constant.TBL_USER_EMAIL);
				sql.append("," + Constant.TBL_USER_TEL + "," + Constant.TBL_USER_BIRTHDAY + "," + Constant.TBL_USER_RULE
						+ "," + Constant.TBL_USER_SALT + ")");
				sql.append(" VALUES (?,?,?,?,?,?,?,?,?,?);");
				int i = 0;
				preparedStatement = (PreparedStatement) conn.prepareStatement(sql.toString(),
						PreparedStatement.RETURN_GENERATED_KEYS);
				preparedStatement.setInt(++i, tblUser.getGroupId());
				preparedStatement.setString(++i, tblUser.getLoginName());
				preparedStatement.setString(++i, tblUser.getPass());
				preparedStatement.setString(++i, tblUser.getFullName());
				preparedStatement.setString(++i, tblUser.getFullNameKana());
				preparedStatement.setString(++i, tblUser.getEmail());
				preparedStatement.setString(++i, tblUser.getTel());
				preparedStatement.setDate(++i, tblUser.getBirthDay());
				preparedStatement.setInt(++i, tblUser.getRule());
				preparedStatement.setString(++i, tblUser.getSalt());
				preparedStatement.execute();
				ResultSet rs = preparedStatement.getGeneratedKeys();
				if (rs.next()) {
					generatedKey = rs.getInt(1);
					System.out.println(generatedKey);
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			throw e;
		} finally {
			closeConnection();
		}
		return generatedKey;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.TblUserDao#checkExistUserById(int)
	 */
	@Override
	public boolean checkExistUserById(int userID) throws SQLException, ClassNotFoundException {
		boolean flag = false;
		try {
			openMySQLConnection();
			if (conn != null) {
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT * FROM " + Constant.TBL_USER);
				sql.append(" WHERE " + Constant.TBL_USER_USER_ID + " = ?;");
				int i = 0;
				preparedStatement = (PreparedStatement) conn.prepareStatement(sql.toString());
				preparedStatement.setInt(++i, userID);
				ResultSet rs = preparedStatement.executeQuery();
				if (rs.next()) {
					flag = true;
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeConnection();
		}
		return flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.TblUserDao#getUserInfoById(int)
	 */
	@Override
	public UserInfo getUserInfoById(int userID) throws SQLException, ClassNotFoundException {
		try {
			openMySQLConnection();
			if (conn != null) {
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT * FROM " + Constant.TBL_USER);
				sql.append(" LEFT JOIN " + Constant.TBL_DETAIL);
				sql.append(" ON " + Constant.TBL_USER_USER_ID + " = " + Constant.TBL_DETAIL_USER_ID);
				sql.append(" WHERE " + Constant.TBL_USER_USER_ID + " = ?;");
				int i = 0;
				preparedStatement = (PreparedStatement) conn.prepareStatement(sql.toString());
				preparedStatement.setInt(++i, userID);
				ResultSet rs = preparedStatement.executeQuery();
				UserInfo userInfo = new UserInfo();
				if (rs.next()) {
					userInfo.setLoginName(rs.getString(Constant.TBL_USER_LOGIN_NAME));
					userInfo.setGroupId(rs.getInt(Constant.TBL_USER_GROUP_ID));
					userInfo.setFullName(rs.getString(Constant.TBL_USER_FULLNAME));
					userInfo.setKanaName(rs.getString(Constant.TBL_USER_FULLNAME_KANA));
					userInfo.setBirthday(rs.getDate(Constant.TBL_USER_BIRTHDAY));
					userInfo.setEmail(rs.getString(Constant.TBL_USER_EMAIL));
					userInfo.setTel(rs.getString(Constant.TBL_USER_TEL));
					String codeLevel = rs.getString(Constant.TBL_DETAIL_CODE_LEVEL);
					userInfo.setCodeLevel(codeLevel);
					if (codeLevel != "") {
						userInfo.setStartDate(rs.getDate(Constant.TBL_DETAIL_START_DATE));
						userInfo.setEndDate(rs.getDate(Constant.TBL_DETAIL_END_DATE));
						userInfo.setTotal(String.valueOf(rs.getInt(Constant.TBL_DETAIL_TOTAL)));
					}
				}
				return userInfo;
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeConnection();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.TblUserDao#checkExistIdUser(int)
	 */
	@Override
	public boolean checkExistIdUser(int userID) throws SQLException, ClassNotFoundException {
		int temp = 0;
		try {
			openMySQLConnection();
			if (conn != null) {
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT count(user_id) ");
				sql.append(" FROM " + Constant.TBL_USER);
				sql.append(" WHERE " + Constant.TBL_USER_USER_ID + " = ? and " + Constant.TBL_USER_RULE + " = ? ;");
				preparedStatement = (PreparedStatement) conn.prepareStatement(sql.toString());
				preparedStatement.setInt(1, userID);
				preparedStatement.setInt(2, Constant.ADMIN);
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					temp = rs.getInt(1);
				}
				if (temp != 0) { // không tìm thấy thì return true
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
	 * @see dao.TblUserDao#updateTblUser(entities.TblUser)
	 */
	@Override
	public boolean updateTblUser(TblUser tblUser) throws SQLException, ClassNotFoundException {
		try {
			openMySQLConnection();
			if (conn != null) {
				StringBuffer sql = new StringBuffer();
				sql.append("UPDATE " + Constant.TBL_USER);
				sql.append(" SET  " + Constant.TBL_USER_GROUP_ID + " =?, ");
				sql.append(Constant.TBL_USER_LOGIN_NAME + " = ?, ");
				sql.append(Constant.TBL_USER_FULLNAME + " = ?, ");
				sql.append(Constant.TBL_USER_FULLNAME_KANA + " = ?, ");
				sql.append(Constant.TBL_USER_EMAIL + " = ?, ");
				sql.append(Constant.TBL_USER_TEL + " = ?, ");
				sql.append(Constant.TBL_USER_BIRTHDAY + " = ? ");
				sql.append(" WHERE " + Constant.TBL_USER_USER_ID + " = ?;");
				preparedStatement = (PreparedStatement) conn.prepareStatement(sql.toString());
				int temp = 1;
				preparedStatement.setInt(temp++, tblUser.getGroupId()); // 1
				preparedStatement.setString(temp++, tblUser.getLoginName()); // 2
				preparedStatement.setString(temp++, tblUser.getFullName()); // 3
				preparedStatement.setString(temp++, tblUser.getFullNameKana()); // 4
				preparedStatement.setString(temp++, tblUser.getEmail());// 5
				preparedStatement.setString(temp++, tblUser.getTel()); // 6
				preparedStatement.setDate(temp++, tblUser.getBirthDay()); // 7
				preparedStatement.setInt(temp++, tblUser.getUserId()); // 8
				if (preparedStatement.executeUpdate() > 0) {
					return true;
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			// TODO: handle exception
			throw e;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.TblUserDao#getUserByEmailAndID(java.lang.String, int)
	 */
	@Override
	public int getUserIDByEmail(String email) throws SQLException, ClassNotFoundException {
		int userID = 0;
		try {
			openMySQLConnection();
			if (conn != null) {
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT " + Constant.TBL_USER_USER_ID);
				sql.append(" FROM " + Constant.TBL_USER);
				sql.append(" WHERE " + Constant.TBL_USER_EMAIL + " = ?;");
				preparedStatement = (PreparedStatement) conn.prepareStatement(sql.toString());
				preparedStatement.setString(1, email);
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					userID = rs.getInt("user_id");
				}

			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeConnection();
		}
		return userID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.TblUserDao#deleteTblUser(int)
	 */
	@Override
	public boolean deleteTblUser(int userID) throws SQLException, ClassNotFoundException {
		try {
			openMySQLConnection();
			if (conn != null) {
				StringBuilder sql = new StringBuilder();
				sql.append("DELETE FROM " + Constant.TBL_USER);
				sql.append(" WHERE " + Constant.TBL_USER_USER_ID + " = ? ;");
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.TblUserDao#updatePasswordUser(int, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean updatePasswordUser(int userID, String salt, String passWordEncrypted)
			throws SQLException, ClassNotFoundException {
		try {
			openMySQLConnection();
			if (conn != null) {
				StringBuffer sql = new StringBuffer();
				sql.append("UPDATE " + Constant.TBL_USER);
				sql.append(" SET  " + Constant.TBL_USER_PASS + " = ?, ");
				sql.append(Constant.TBL_USER_SALT + " = ? ");

				sql.append(" WHERE " + Constant.TBL_USER_USER_ID + " = ?;");
				preparedStatement = (PreparedStatement) conn.prepareStatement(sql.toString());
				int temp = 1;
				preparedStatement.setString(temp++, passWordEncrypted); // 1
				preparedStatement.setString(temp++, salt); // 2
				preparedStatement.setInt(temp++, userID); // 2
				if (preparedStatement.executeUpdate() > 0) {
					return true;
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			// TODO: handle exception
			throw e;
		}
		return false;
	}

}
