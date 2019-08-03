
package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import entities.TblUser;
import entities.UserInfo;

/**
 * Interface Thao tác với DB của các chức năng login + list/search user
 * 
 * 
 */
public interface TblUserDao extends BaseDao {
	/**
	 * 
	 * <b>Phương thức interface lấy đối tượng admin trong data base</b><br>
	 * 
	 * @param user loginName của đối tượng user
	 * @return Trả về đối tượng tblUser
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public TblUser getAdmin(String user) throws SQLException, ClassNotFoundException;

	/**
	 * Phương thức lấy số lượng user theo điều kiện tìm kiếm
	 *
	 * @param groupID  int GroupID
	 * @param fullName String tên tìm kiếm
	 * @return int số lương tìm kiếm
	 */
	public int getTotalUser(int groupID, String fullName) throws SQLException, ClassNotFoundException;

	/**
	 * Phương thức lấy danh sách user từ database theo điều kiện tìm kiếm
	 * 
	 * @param offset          <b>: int</b> vị trí data cần lấy ra
	 * @param limit           <b>: int</b> số lượng lấy
	 * @param groupId         <b>: int</b> mã nhóm tìm kiếm
	 * @param fullName        <b>: String</b> Tên tìm kiếm
	 * @param sortType        <b>: String</b> Nhận biết xem cột nào được ưu tiên sắp
	 *                        xếp
	 * @param sortByFullName  <b>: String</b> Giá trị sắp xếp của cột Tên(ASC or
	 *                        DESC)
	 * @param sortByCodeLevel <b>: String</b> Giá trị sắp xếp của cột Trình độ tiếng
	 *                        nhật(ASC or DESC)
	 * @param sortByEndDate   <b>: String</b> Giá trị sắp xếp của cột Ngày kết
	 *                        hạn(ASC or DESC)
	 * @return ArrayList<UserInfo> trả về 1 danh sách UserInfo kết quả
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public ArrayList<UserInfo> getListUser(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate)
			throws SQLException, ClassNotFoundException;

	/**
	 * @param userId
	 * @param loginName
	 * @return
	 * @throws Exception
	 */
	boolean getUserByLoginName(Integer userId, String loginName) throws SQLException, ClassNotFoundException;

	/**
	 * @param email
	 * @return trả về true nếu đã tồn tại
	 * @throws Exception
	 */
	public boolean getUserByEmail(String email) throws SQLException, ClassNotFoundException;

	/**
	 * @param groupId
	 * @return true nếu DB tồn tại group_id trong mstGroup
	 * @throws Exception
	 */
	public boolean checkExistedGroupId(int groupId) throws SQLException, ClassNotFoundException;

	/**
	 * @param loginName
	 * @return true nếu đã tồn tại
	 * @throws Exception
	 */
	public boolean checkExistedLoginName(String loginName) throws SQLException, ClassNotFoundException;

	/**
	 * @param tblUser
	 * @return
	 * @throws Exception
	 */
	public int insertUser(TblUser tblUser) throws SQLException, ClassNotFoundException;

	/**
	 * @param userID
	 * @return
	 * @throws Exception
	 */
	public boolean checkExistUserById(int userID) throws SQLException, ClassNotFoundException;

	/**
	 * thực hiện truy vấn để lấy 1 userInfo
	 * 
	 * @param userID
	 * @return UserInfo
	 * @throws Exception
	 */
	public UserInfo getUserInfoById(int userID) throws SQLException, ClassNotFoundException;

	/**
	 * @param userID
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws Exception
	 */
	public boolean checkExistIdUser(int userID) throws SQLException, ClassNotFoundException;

	/**
	 * @param tblUser
	 * @return
	 * @throws Exception
	 */
	public boolean updateTblUser(TblUser tblUser) throws SQLException, ClassNotFoundException;

	/**
	 * @param email
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int getUserIDByEmail(String email) throws SQLException, ClassNotFoundException;

	/**
	 * @param userID
	 * @return
	 */
	public boolean deleteTblUser(int userID) throws SQLException, ClassNotFoundException;

	/**
	 * @param userID
	 * @param salt
	 * @param passWord
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public boolean updatePasswordUser(int userID, String salt, String passWordEncrypted) throws SQLException, ClassNotFoundException;
}
