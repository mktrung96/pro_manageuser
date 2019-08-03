
package logics;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import entities.UserInfo;

/**
 * Interface Xử lý logic cho các chức năng login + list/search user
 * 
 */
public interface TblUserLogic {
	/**
	 * 
	 * 
	 * @param userName String userName cần kiểm tra
	 * @param password String password cần kiểm tra
	 * @return boolean kết quả kiểm tra<br>
	 *         Nếu là true thì có tồn tại<br>
	 *         là false thì không tồn tại
	 * @throws NoSuchAlgorithmException
	 * @throws SQLException-
	 * @throws ClassNotFoundException
	 */
	public boolean checkExistUser(String userName, String password)
			throws NoSuchAlgorithmException, ClassNotFoundException, SQLException;

	/**
	 * Phương thức lấy tổng số user theo điều kiện tìm kiếm
	 *
	 * @param groupID  int điều kiện tìm kiếm theo id group
	 * @param fullName String điều kiện tìm kiếm theo tên
	 * @return int số lượng kết quả user trả về
	 */
	public int getTotalUser(int groupID, String fullName) throws ClassNotFoundException, SQLException;

	/**
	 * Phương thức lấy danh sách theo điều kiện tìm kiếm
	 * 
	 * @return ArrayList<UserInfo>
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<UserInfo> getListUser(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate)
			throws SQLException, ClassNotFoundException;

	/**
	 * Kiểm tra tồn tại khi add loginNames
	 * 
	 * @param loginName
	 * @return true nếu đã tồn tại
	 * @throws Exception
	 */
	public boolean checkExistedLoginName(String loginName) throws Exception;

	/**
	 * Phương thức kiểm tra tồn tại group khi update user
	 * 
	 * @param groupIds
	 * @return true không tồn tại groupId trong DB
	 * @throws Exception
	 */
	public boolean checkExistedGroupId(int groupId) throws Exception;

	/**
	 * Kiểm tra sự tồn tại email
	 * 
	 * @param email
	 * @return trả về true nếu tồn tại email trong DB trùng với email ngoài view với cùng userID
	 * @throws Exception
	 */
	public boolean checkExistedEmail(String email,int userID) throws Exception;

	/**
	 * @param userInfo
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public boolean createUser(UserInfo userInfo) throws SQLException, ClassNotFoundException;

	/**
	 * Phương thức kiểm sự tồn tại của User bằng userID
	 * 
	 * @param userID
	 * @return true nếu tồn tại User có userID
	 * @throws Exception
	 */
	public boolean checkExistUserById(int userID) throws Exception;

	/**
	 * phương thức lấy UserInfo từ DB
	 * 
	 * @param userID
	 * @return 1 đối tượng userInfo
	 * @throws Exception
	 */
	public UserInfo getUserInfoById(int userID) throws Exception;

	/**
	 * @param userInfo UserInfo
	 * @param checkJapan boolean
	 * @return true  nếu update thành công
	 * @throws Exception
	 */
	public boolean updateUserInfo(UserInfo userInfo, boolean checkJapan) throws Exception;

	/**
	 * @param userID int
	 * @return true nếu tồn tại userID đó trong bảng tbl_user
	 * @throws Exception
	 */
	public boolean checkExistIdUser(int userID) throws Exception;

	/**
	 * @param userID
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public boolean deleteUser(int userID) throws SQLException, ClassNotFoundException;

	/**
	 * @param userID
	 * @param passWordConfirm
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public boolean updatePasswordUser(int userID, String passWord) throws NoSuchAlgorithmException, ClassNotFoundException, SQLException;
}
