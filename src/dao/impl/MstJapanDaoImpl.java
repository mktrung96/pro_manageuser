
package dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import dao.MstJapanDao;
import entities.MstJapan;
import ultis.Constant;

/**
 * Class MstJapanDaoImpl Thao tác với DB bảng mst_japan
 * 
 */
public class MstJapanDaoImpl extends BaseDaoImpl implements MstJapanDao {

	/**
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Override
	public List<MstJapan> getAllMstJapan() throws SQLException, ClassNotFoundException {
		try {
			// Khởi tạo listMstGroup để trả về
			List<MstJapan> listMstJapan = new ArrayList<>();
			openMySQLConnection();
			// nếu Connection đã có thì thực thi dòng câu lệnh phía dưới
			if (conn != null) {
				// khai báo câu lệnh SQL và append lần lượt các giá trị
				// đã đúng điều kiện
				StringBuilder sqlB = new StringBuilder();
				sqlB.append("SELECT " + Constant.MST_JAPAN_CODE_LEVEL + ", " + Constant.MST_JAPAN_NAME_LEVEL);
				sqlB.append(" FROM ");
				sqlB.append(Constant.MST_JAPAN);
				sqlB.append(" where 1 = 1;");
				// chuyển stringbuilder sang string
				String sql = sqlB.toString();
				preparedStatement = (PreparedStatement) conn.prepareStatement(sql);
				// get data theo câu lệnh truy vấn sql ở trên.
				// lấy danh sách kết quả đã tìm kiếm được
				ResultSet rs = preparedStatement.executeQuery();
				// lấy từng kết quả gán vào đối tượng MstGroup để trả về
				while (rs.next()) {
					MstJapan mstJapan = new MstJapan();
					mstJapan.setCodeLevel(rs.getString(Constant.MST_JAPAN_CODE_LEVEL));
					mstJapan.setNameLevel(rs.getString(Constant.MST_JAPAN_NAME_LEVEL));
					listMstJapan.add(mstJapan);
				}
				// trả về kết quả
				return listMstJapan;
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

	/**
	 * @param codeLevel
	 * @return
	 * @throws Exception
	 */
	public boolean checkExistLevelJapan(String codeLevel) throws SQLException , ClassNotFoundException {
		boolean flag = false;
		try {
			// mở kết nối đến DB
			openMySQLConnection();
			MstJapan mstJapan = new MstJapan();
			// nếu Connection đã có thì thực thi dòng câu lệnh phía dưới
			if (conn != null) {
				// khai báo câu lệnh SQL và append lần lượt các giá trị
				// đã đúng điều kiện
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT " + Constant.MST_JAPAN_CODE_LEVEL);
				sql.append(" FROM ");
				sql.append(Constant.MST_JAPAN);
				sql.append(" WHERE code_level = ?;");
				int i = 0;
				//
				preparedStatement = (PreparedStatement) conn.prepareStatement(sql.toString());
				preparedStatement.setString(++i, codeLevel);
				// get data theo câu lệnh truy vấn sql ở trên.
				// lấy danh sách kết quả đã tìm kiếm được
				ResultSet rs = preparedStatement.executeQuery();
				// lấy từng kết quả gán vào đối tượng MstGroup để trả về
				while (rs.next()) {
					mstJapan.setCodeLevel(rs.getString(Constant.MST_JAPAN_CODE_LEVEL));
				}
				if (mstJapan != null) {
					flag = true;
				}
				// trả về kết quả
				return flag;
			}
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
			throw e;

		} finally {
			closeConnection();
		}
		// Nếu không tìm thây trả về kết quả null
		return flag;
	}

	/**
	 * lấy nameLevel trong DB
	 * 
	 * @param codeLevel
	 * @return nameLevel tương ứng với codeLevel truyền vào
	 * @throws Exception
	 */
	public String getMstJapanName(String codeLevel) throws SQLException , ClassNotFoundException {
		String nameLevel = null;
		try {
			// mở kết nối đến DB
			openMySQLConnection();
			// nếu Connection đã có thì thực thi dòng câu lệnh phía dưới
			if (conn != null) {
				// khai báo câu lệnh SQL và append lần lượt các giá trị
				// đã đúng điều kiện
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT " + Constant.MST_JAPAN_NAME_LEVEL);
				sql.append(" FROM ");
				sql.append(Constant.MST_JAPAN);
				sql.append(" WHERE code_level = ?;");
				int i = 0;
				preparedStatement = (PreparedStatement) conn.prepareStatement(sql.toString());
				preparedStatement.setString(++i, codeLevel);
				// get data theo câu lệnh truy vấn sql ở trên.
				// lấy danh sách kết quả đã tìm kiếm được
				ResultSet rs = preparedStatement.executeQuery();
				// lấy từng kết quả gán vào đối tượng MstGroup để trả về
				while (rs.next()) {
					nameLevel = rs.getString(Constant.MST_JAPAN_NAME_LEVEL);
				}

			}
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
			throw e;

		} finally {
			closeConnection();
		}
		// Nếu không tìm thây trả về kết quả null
		return nameLevel;
	}

}
