
package logics;

import java.sql.SQLException;
import java.util.ArrayList;

import entities.MstGroup;

public interface MstGroupLogic {
	/**
	 * Phương thức lấy tất cả group từ đối tượng MstGroupDao
	 *
	 * @return arraylist MstGroup
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ArrayList<MstGroup> getAllMstGroup() throws ClassNotFoundException, SQLException;

	/**
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public String getMstGroupName(int groupId) throws SQLException , ClassNotFoundException;

}
