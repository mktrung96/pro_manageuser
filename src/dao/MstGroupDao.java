
package dao;

import java.sql.SQLException;
import java.util.List;

import entities.MstGroup;

/**
 * Interface Thao tác với DB bảng mst_group
 * 
 * 
 */
public interface MstGroupDao {
	/**
	 * Phương thức lấy tất cả group từ data base
	 * 
	 * @return trả về danh sách đối tượng MstGroup
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<MstGroup> getAllMstGroup() throws ClassNotFoundException, SQLException;

	/**
	 * 
	 * @param groupId
	 * @throws Exception
	 */
	public String getMstGroupName(int groupId) throws SQLException, ClassNotFoundException;

}
