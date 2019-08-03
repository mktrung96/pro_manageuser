package logics;

import java.sql.SQLException;
import java.util.List;

import entities.MstJapan;

public interface MstJapanLogic {
	/**
	 * Lấy tất cả giá trị trong bảng MstJapan
	 * 
	 * @return listMstJapan
	 * @throws SQLException
	 */
	public List<MstJapan> getAllMstJapan() throws SQLException;

	/**
	 * Kiểm tra sự tồn tại của codeLevel trong DB
	 * 
	 * @param codeLevel
	 * @return true nếu tồn tại
	 */
	public boolean checkExistLevelJapan(String codeLevel);

	/**
	 * @param codeLevel
	 * @return
	 * @throws Exception
	 */
	public String getMstJapanName(String codeLevel) throws SQLException , ClassNotFoundException;
}
