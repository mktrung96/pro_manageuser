
package dao;

import java.sql.SQLException;
import java.util.List;

import entities.MstJapan;

/**
 * Interface Thao tác với DB bảng mst_japan
 * 
 * @author NamĐH
 */
public interface MstJapanDao {

	/**
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	List<MstJapan> getAllMstJapan() throws SQLException, ClassNotFoundException;

}
