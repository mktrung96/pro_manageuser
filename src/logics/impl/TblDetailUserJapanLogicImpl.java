package logics.impl;

import java.sql.SQLException;

import dao.TblDetailUserJapanDao;
import dao.impl.TblDetailUserJapanDaoImpl;
import logics.TblDetailUserJapanLogic;

public class TblDetailUserJapanLogicImpl implements TblDetailUserJapanLogic {

	/*
	 * (non-Javadoc)
	 * 
	 * @see logics.TblDetailUserJapanLogic#checkExistLevelJapan(entities.UserInfo)
	 */
	@Override
	public boolean checkHaveLevelJapan(int userID) throws SQLException, ClassNotFoundException {
		TblDetailUserJapanDao tblDetailUserJapanDaoImpl = new TblDetailUserJapanDaoImpl();
		return tblDetailUserJapanDaoImpl.checkHaveLevelJapan(userID);
	}

}
