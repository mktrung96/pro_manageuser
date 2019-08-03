
package entities;

import java.sql.Date;
import java.util.List;

/**
 * Class TblDetailUserJapan là Java Bean chứa các thuộc tính của bảng
 * tbl_detail_user_japan trong db
 * 
 */
public class TblDetailUserJapan {
	private int detailUserJapanId;
	private int userId;
	private String codeLevel;
	private List<Integer> arrStartDate;
	private List<Integer> arrEndDate;
	private Date endDate;
	private Date startDate;
	private String total;

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public int getDetailUserJapanId() {
		return detailUserJapanId;
	}

	public void setDetailUserJapanId(int detailUserJapanId) {
		this.detailUserJapanId = detailUserJapanId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getCodeLevel() {
		return codeLevel;
	}

	public void setCodeLevel(String codeLevel) {
		this.codeLevel = codeLevel;
	}

	public List<Integer> getArrStartDate() {
		return arrStartDate;
	}

	public void setArrStartDate(List<Integer> arrStartDate) {
		this.arrStartDate = arrStartDate;
	}

	public List<Integer> getArrEndDate() {
		return arrEndDate;
	}

	public void setArrEndDate(List<Integer> arrEndDate) {
		this.arrEndDate = arrEndDate;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

}
