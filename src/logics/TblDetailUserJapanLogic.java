package logics;

public interface TblDetailUserJapanLogic {

	/**
	 * @param userID int
	 * @return true nếu có trình độ tiếng Nhật
	 * @throws Exception 
	 */
	boolean checkHaveLevelJapan(int userID) throws Exception;

}
