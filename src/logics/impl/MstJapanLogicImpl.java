package logics.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.impl.MstJapanDaoImpl;
import entities.MstJapan;
import logics.MstJapanLogic;

public class MstJapanLogicImpl implements MstJapanLogic {

	@Override
	public List<MstJapan> getAllMstJapan() throws SQLException {
		List<MstJapan> listJapan = new ArrayList<>();
		try {
			listJapan = new MstJapanDaoImpl().getAllMstJapan();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listJapan;
	}

	@Override
	public boolean checkExistLevelJapan(String codeLevel) {
		MstJapanDaoImpl mstJapan = new MstJapanDaoImpl();
		boolean flag = false;
		try {
			flag = mstJapan.checkExistLevelJapan(codeLevel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see logics.MstJapanLogic#getMstJapanName(java.lang.String)
	 */
	@Override
	public String getMstJapanName(String codeLevel) throws SQLException , ClassNotFoundException {
		// khởi tạo đối tượng MstJapanDaoImpl
		MstJapanDaoImpl mstJapanDaoImpl = new MstJapanDaoImpl();
		String mstJapanName = mstJapanDaoImpl.getMstJapanName(codeLevel);
		return mstJapanName;
	}

}
