
package logics.impl;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import com.mysql.jdbc.Connection;

import dao.TblDetailUserJapanDao;
import dao.TblUserDao;
import dao.impl.TblDetailUserJapanDaoImpl;
import dao.impl.TblUserDaoImpl;
import entities.TblDetailUserJapan;
import entities.TblUser;
import entities.UserInfo;
import logics.TblUserLogic;
import ultis.Common;

/**
 * Class TblUserLogicImpl để xử lý logic cho các chức năng login + list/search
 * user
 * 
 * 
 */
public class TblUserLogicImpl implements TblUserLogic {

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblUserDao#getListUser()
	 */
	@Override
	public boolean checkExistUser(String userName, String password)
			throws NoSuchAlgorithmException, ClassNotFoundException, SQLException {
		// Khai báo giá trị trả về mặc định là false
		boolean result = false;
		// Khởi tạo đối tượng tblUserDao
		TblUserDao tblUserDao = new TblUserDaoImpl();
		// lấy đối tượng tblUser
		TblUser tblUser = tblUserDao.getAdmin(userName);
		// Nếu đối tượng khác null thì
		if (tblUser != null) {
			// lấy giá trị password của đối tương trong database
			String passDB = tblUser.getPass();
			// lấy giá trị mã hóa salt của đối tương trong database
			String salt = tblUser.getSalt();
			// Mã hóa password truyền vào từ người dùng với giá trị salt vừa lấy
			String passEncrypt = Common.encryptPassword(salt, password);
			// Kiếm tra password vừa mã hóa với password trong database
			// Nếu trùng khớp thì gán kết quả true
			if (passEncrypt.equals(passDB)) {
				result = true;
			}
		}
		// trả về kết quả
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblUserDao#getListUser()
	 */
	@Override
	public int getTotalUser(int groupID, String fullName) throws ClassNotFoundException, SQLException {
		// Khởi tạo tham số trả về số lượng tìm kiếm mặc định là -1
		int result = -1;
		// Khởi tạo đối tượng tblUserDao
		TblUserDao tblUserDao = new TblUserDaoImpl();
		// loại bỏ dấu cách mà người dùng nhỡ nhập
		fullName = fullName.trim();
		// Xử lý fullName để tránh lỗi Sql injection
		fullName = Common.replaceWilcard(fullName);
		// Lấy danh sách user dựa theo điều kiện tìm kiếm
		result = tblUserDao.getTotalUser(groupID, fullName);
		// Trả về số lượng tìm kiếm
		// result = 0 là k tìm thấy
		// result = -1 là có lỗi xảy ra
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblUserDao#getListUser()
	 */
	@Override
	public List<UserInfo> getListUser(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate)
			throws SQLException, ClassNotFoundException {
		// Khởi tạo đối tượng tblUserDao kết nối tới database
		TblUserDao tblUserDao = new TblUserDaoImpl();
		// loại bỏ dấu cách mà người dùng nhỡ nhập
		fullName = fullName.trim();
		// Xử lý fullName để tránh lỗi Sql injection
		fullName = Common.replaceWilcard(fullName);
		// Chuyển đổi sort type từ requets sang tên cột trong bảng để thực hiện câu lệnh
		// sql
		sortType = Common.convertSortType(sortType);
		// Khởi tạo danh sách trả về
		List<UserInfo> listUserInfo = tblUserDao.getListUser(offset, limit, groupId, fullName, sortType, sortByFullName,
				sortByCodeLevel, sortByEndDate);
		// trả về danh sách tìm kiếm
		return listUserInfo;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @throws Exception
	 * 
	 * @see manageuser.dao.TblUserDao#getListUser()
	 */
	@Override
	public boolean checkExistedLoginName(String loginName) throws Exception {
		// Khai báo giá trị trả về mặc định là false
		boolean result = false;
		try {
			// Khởi tạo đối tượng tblUserDao
			TblUserDao tblUserDao = new TblUserDaoImpl();
			// lấy đối tượng tblUser
			result = tblUserDao.checkExistedLoginName(loginName);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean checkExistedGroupId(int groupId) throws Exception {
		// Khai báo giá trị trả về mặc định là false
		boolean result = false;
		try {
			// Khởi tạo đối tượng tblUserDao
			TblUserDao tblUserDao = new TblUserDaoImpl();
			// trả về true nếu DB tồn tại groupId trong mstGroup
			result = tblUserDao.checkExistedGroupId(groupId);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean checkExistedEmail(String email, int userID) throws Exception {
		// Khai báo giá trị trả về mặc định là false
		boolean result = false;
		try {
			// Khởi tạo đối tượng tblUserDao
			TblUserDao tblUserDao = new TblUserDaoImpl();
			if (userID == 0) {
				// lấy đối tượng tblUser
				result = tblUserDao.getUserByEmail(email);
			} else {
				int id = tblUserDao.getUserIDByEmail(email);
				if (id == userID || id == 0) { // email và userID là cùng 1 đối tượng
					return false;
				} else {
					return true;
				}
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @param userInfo
	 * @return true nếu insert thành công
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Override
	public boolean createUser(UserInfo userInfo) throws SQLException {
		boolean flag = false;
		TblUserDao tblUserDaoImpl = new TblUserDaoImpl();
		TblDetailUserJapanDao tblDetailUserJapanDaoImpl = new TblDetailUserJapanDaoImpl();
		Connection connection = null;
		try {
			// khởi tạo và set các giá trị cho đối tượng tblUser
			TblUser tblUser = new TblUser();
			tblUser = Common.convertUserInfoToTblUser(userInfo);
			// lấy kết nối tới DB
			tblUserDaoImpl.openMySQLConnection();
			connection = tblUserDaoImpl.getConn();
			// chưa commit ngay
			tblUserDaoImpl.setFalseAutoCommit();

			// insert tblUser vào DB và trả về giá trị userID được tạo ra trong DB
			// nếu insert không thành công thì userID = 0
			// add giá trị userID cho userInfo
			userInfo.setUserID(tblUserDaoImpl.insertUser(tblUser));

			// nếu insert tblUser thành công
			if (userInfo.getUserID() != 0) {
				// tồn tại trình độ tiếng Nhật
				if (!"".equals(userInfo.getCodeLevel())) {
					// khởi tạo và set các giá trị cho đối tượng tblDetailUserJapan
					TblDetailUserJapan tblDetailUserJapan = new TblDetailUserJapan();
					tblDetailUserJapan = Common.convertUserInfoToDetailUserJapan(userInfo);
					// insert tblDetailUserJapan vào DB
					tblDetailUserJapanDaoImpl.setConn(connection);
					flag = tblDetailUserJapanDaoImpl.insertDetailUserJapan(tblDetailUserJapan);
					if (flag) {

						connection.commit();
					}
				}
				// không tồn tại trình độ tiếng Nhật
				else {
					flag = true;
					connection.commit();
				}
			} else {
				connection.rollback();
			}

		} catch (Exception e) {
			flag = false;
			connection.rollback();
			e.printStackTrace();
		}
		return flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see logics.TblUserLogic#checkExistUserById(int)
	 */
	@Override
	public boolean checkExistUserById(int userID) throws Exception {
		TblUserDao tblUserDaoImpl = new TblUserDaoImpl();
		// nếu tồn tại thì trả về true
		return tblUserDaoImpl.checkExistUserById(userID);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see logics.TblUserLogic#getUserInfoById(int)
	 */
	@Override
	public UserInfo getUserInfoById(int userID) throws Exception {
		TblUserDao tblUserDaoImpl = new TblUserDaoImpl();
		// truy vấn để trả về 1 đối tượng userIno
		return tblUserDaoImpl.getUserInfoById(userID);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see logics.TblUserLogic#updateTblUser(entities.TblUser)
	 */
	@Override
	public boolean updateUserInfo(UserInfo userInfo, boolean checkJapanDB) throws SQLException, ClassNotFoundException {
		// khởi tạo mặc định trả về là false
		boolean flag = false;
		Connection connection = null;

		try {
			// khởi tạo 1 đối tượng tblUser khi biết 1 đối tượng userInfo
			TblUser tblUser = Common.convertUserInfoToTblUser(userInfo);
			System.out.println("logic: " + tblUser.toString());
			// khởi tạo đối tượng TblUserDaoImpl
			TblUserDao tblUserDaoImpl = new TblUserDaoImpl();

			// update tblUser
			flag = tblUserDaoImpl.updateTblUser(tblUser);
			// set chưa commit ngay
			tblUserDaoImpl.setFalseAutoCommit();
			// lấy ra connection của tblUserDaoImpl
			connection = tblUserDaoImpl.getConn();
			// nếu có trình độ tiếng Nhật trong DB

			// khởi tạo đối tượng làm việc với DB của bảng tbl_detail_user_japan
			TblDetailUserJapanDao tblDetailUserJapanDaoImpl = new TblDetailUserJapanDaoImpl();
			tblDetailUserJapanDaoImpl.setConn(connection);
			// lấy ra userID của userInfo
			int userID = userInfo.getUserID();

			if (!"".equals(userInfo.getCodeLevel())) {
				// khởi tạo entities tblDetailUserJapan khi biết 1 đối tượng userInfo
				TblDetailUserJapan tblDetailUserJapan = Common.convertUserInfoToDetailUserJapan(userInfo);
				System.out.println(tblDetailUserJapan.toString());
				// DB có codeLevel và edit -> có trình độ tiếng Nhật khác
				if (checkJapanDB) {
					System.out.println("có thành có");
					flag = tblDetailUserJapanDaoImpl.updateLevelUser(tblDetailUserJapan);
				}
				// DB không có codeLevel và edit -> có trình độ tiếng Nhật
				else {
					System.out.println("không thành có");
					flag = tblDetailUserJapanDaoImpl.insertDetailUserJapan(tblDetailUserJapan);
				}
			}
			// DB có codeLevel và edit -> không có trình độ tiếng Nhật
			else {
				if (checkJapanDB) {
					System.out.println("có thành không");
					flag = tblDetailUserJapanDaoImpl.deleteLevelJapan(userID);
				}

			}

			if (flag == true) {

				connection.commit();
			}
		} catch (SQLException | ClassNotFoundException | NoSuchAlgorithmException e) {
			connection.rollback();
			e.printStackTrace();
		}
		return flag;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see logics.TblUserLogic#checkExistIdUser(int)
	 */
	@Override
	public boolean checkExistIdUser(int userID) throws SQLException , ClassNotFoundException {
		TblUserDao tblUserDaoImpl = new TblUserDaoImpl();
		// truy vấn để kiểm tra user_id
		return tblUserDaoImpl.checkExistIdUser(userID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see logics.TblUserLogic#deleteUser(int)
	 */
	@Override
	public boolean deleteUser(int userID) throws SQLException, ClassNotFoundException {
		// khởi tạo mặc định trả về là false
		boolean flag = false;
		Connection connection = null;

		try {
			// khởi tạo đối tượng làm việc với DB của bảng tbl_detail_user_japan
			TblDetailUserJapanDao tblDetailUserJapanDaoImpl = new TblDetailUserJapanDaoImpl();

			flag = tblDetailUserJapanDaoImpl.deleteLevelJapan(userID);
			// set chưa commit ngay
			tblDetailUserJapanDaoImpl.setFalseAutoCommit();
			// lấy ra connection của tblUserDaoImpl
			connection = tblDetailUserJapanDaoImpl.getConn();
			// khởi tạo đối tượng TblUserDaoImpl
			TblUserDao tblUserDaoImpl = new TblUserDaoImpl();
			tblUserDaoImpl.setConn(connection);
			// delete tblUser
			flag = tblUserDaoImpl.deleteTblUser(userID);
			if (flag == true) {
				connection.commit();
			}
		} catch (SQLException | ClassNotFoundException e) {
			connection.rollback();
			e.printStackTrace();
		}
		return flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see logics.TblUserLogic#updatetPasswordUser(int, java.lang.String)
	 */
	@Override
	public boolean updatePasswordUser(int userID, String passWord)
			throws NoSuchAlgorithmException, ClassNotFoundException, SQLException {
		// phương thức tạo salt
		String salt = Common.createSalt();
		String passWordEncrypted = Common.encryptPassword(salt, passWord);
		TblUserDao tblUserDaoImpl = new TblUserDaoImpl();
		boolean check = tblUserDaoImpl.updatePasswordUser(userID, salt, passWordEncrypted);
		return check;
	}

}
