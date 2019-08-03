
package dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import dao.MstGroupDao;
import entities.MstGroup;
import ultis.Constant;

/**
 * Class MstGroupDaoImpl Thao tác với DB bảng mst_group user
 * 
 * 
 */
public class MstGroupDaoImpl extends BaseDaoImpl implements MstGroupDao {

	/**
	 * Phương thức lấy tất cả group từ data base
	 * 
	 * @return trả về danh sách đối tượng MstGroup
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Override
	public List<MstGroup> getAllMstGroup() throws ClassNotFoundException, SQLException {
		try {
			// Khởi tạo listMstGroup để trả về
			ArrayList<MstGroup> listMstGroup = new ArrayList<>();
			openMySQLConnection();
			// nếu Connection đã có thì thực thi dòng câu lệnh phía dưới
			if (conn != null) {
				// khai báo câu lệnh SQL và append lần lượt các giá trị
				// đã đúng điều kiện
				StringBuilder sqlB = new StringBuilder();
				sqlB.append("SELECT " + Constant.MST_GROUP_GROUP_ID + ", " + Constant.MST_GROUP_GROUP_NAME);
				sqlB.append(" FROM ");
				sqlB.append(Constant.MST_GROUP);
				sqlB.append(" where 1 = 1;");
				// chuyển stringbuilder sang string
				String sql = sqlB.toString();
				preparedStatement = (PreparedStatement) conn.prepareStatement(sql);
				// get data theo câu lệnh truy vấn sql ở trên.
				// lấy danh sách kết quả đã tìm kiếm được
				ResultSet rs = preparedStatement.executeQuery();
				// lấy từng kết quả gán vào đối tượng MstGroup để trả về
				while (rs.next()) {
					MstGroup mstGroup = new MstGroup();
					mstGroup.setGroupID(rs.getInt(Constant.MST_GROUP_GROUP_ID));
					mstGroup.setGroupName(rs.getString(Constant.MST_GROUP_GROUP_NAME));
					listMstGroup.add(mstGroup);
				}
				// trả về kết quả
				return listMstGroup;
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
		// Nếu không tìm thây trả về kết quả null
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.MstGroupDao#getMstGroupName(int)
	 */
	@Override
	public String getMstGroupName(int groupId) throws SQLException, ClassNotFoundException {
		try {
			String groupName = null;
			openMySQLConnection();
			// nếu Connection đã có thì thực thi dòng câu lệnh phía dưới
			if (conn != null) {
				// khai báo câu lệnh SQL và append lần lượt các giá trị
				// đã đúng điều kiện
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT " + Constant.MST_GROUP_GROUP_NAME);
				sql.append(" FROM ");
				sql.append(Constant.MST_GROUP);
				sql.append(" where group_id = ?;");
				int i = 0;
				preparedStatement = (PreparedStatement) conn.prepareStatement(sql.toString());
				preparedStatement.setInt(++i, groupId);
				// get data theo câu lệnh truy vấn sql ở trên.
				// lấy danh sách kết quả đã tìm kiếm được
				ResultSet rs = preparedStatement.executeQuery();
				// lấy từng kết quả gán vào đối tượng MstGroup để trả về
				while (rs.next()) {
					groupName = rs.getString(Constant.MST_GROUP_GROUP_NAME);
				}
				// trả về kết quả
				return groupName;
			}
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
			throw e;

		} finally {
			closeConnection();
		}
		// Nếu không tìm thây trả về kết quả null
		return null;

	}

}
