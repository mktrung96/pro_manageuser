package logics.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.MstGroupDao;
import dao.impl.MstGroupDaoImpl;
import entities.MstGroup;
import logics.MstGroupLogic;
import ultis.ConfigProperties;
import ultis.Constant;

/**
 * Class MstGroupLogicImpl để Xử lý logic cho các chức năng search list/search
 * 
 */
public class MstGroupLogicImpl implements MstGroupLogic {

	/**
	 * Phương thức lấy tất cả group từ đối tượng MstGroupDao
	 *
	 * @return arraylist MstGroup
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Override
	public ArrayList<MstGroup> getAllMstGroup() throws ClassNotFoundException, SQLException {

		// khởi tạo danh sách MstGroup để trả về
		ArrayList<MstGroup> listGroup = new ArrayList<>();
		// Khởi tạo đối tương MstGroupDao
		MstGroupDao mstGroupDao = new MstGroupDaoImpl();
		// Khởi tạo đối tượng MstGroup để chọn tất cả
		MstGroup mstGroupAll = new MstGroup();
		// set giá trị mặc định là 0
		mstGroupAll.setGroupID(Constant.ID_GROUP_FIND_ALL);
		// set giá trị tiếng nhật ở trong file properties config
		mstGroupAll.setGroupName(ConfigProperties.getValue("groupall"));
		// gán vào listGroup
		listGroup.add(mstGroupAll);
		// Lấy danh sách group trong data base
		List<MstGroup> listGroupInDB = mstGroupDao.getAllMstGroup();
		// Gán tất cả group có trong data base vừa tìm được vào danh sách trả về
		listGroup.addAll(listGroupInDB);
		// Trả về danh sách MstGroup
		return listGroup;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see logics.MstGroupLogic#getMstGroupName(int)
	 */
	@Override
	public String getMstGroupName(int groupId) throws SQLException, ClassNotFoundException {
		// Khởi tạo đối tương MstGroupDao
		MstGroupDao mstGroupDao = new MstGroupDaoImpl();
		return mstGroupDao.getMstGroupName(groupId);

	}

}
