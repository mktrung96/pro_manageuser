
package validates;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.UserInfo;
import logics.MstJapanLogic;
import logics.TblUserLogic;
import logics.impl.MstJapanLogicImpl;
import logics.impl.TblUserLogicImpl;
import ultis.Common;
import ultis.Constant;
import ultis.MessageErrorProperties;

/**
 * Class ValidateUser Xử lý validate thông tin nhập vào từ màn hình
 * 
 */
public class ValidateUser {
	/**
	 * 
	 * <b>Phương thức kiểm tra nhập liệu user và pass</b><br>
	 * 
	 * @param user     String user cần kiểm tra
	 * @param password String password cần kiểm tra
	 * @return ArrayList<String> danh sách lỗi trong quá trình kiểm tra.<br>
	 *         Nếu danh sách rỗng thì user và password nhập vào là đúng
	 * @throws NoSuchAlgorithmException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<String> checkValidateLogin(String userName, String password)
			throws NoSuchAlgorithmException, ClassNotFoundException, SQLException {

		TblUserLogic tblUserLogic = new TblUserLogicImpl(); // khai báo đối tượng TblUserLogicImpl
		// Khai báo arraylist lỗi
		List<String> listError = new ArrayList<>();
		// nếu chưa nhập userName
		if (userName == null || userName.isEmpty()) {
			// add lỗi chưa nhập username
			listError.add(MessageErrorProperties.getValue(Constant.ER001_USER_NAME));
		}
		// nếu chưa nhập password
		if (password == null || password.isEmpty()) {
			// add lỗi chưa nhập password
			listError.add(MessageErrorProperties.getValue(Constant.ER001_PASSWORD));
		}
		// nếu không có lỗi
		if (listError.isEmpty()) {
			// kiểm tra sự tồn tại của username và password trong DB
			boolean checkExist = tblUserLogic.checkExistUser(userName, password);
			// nếu không trùng khớp trong DB thì add lỗi
			if (!checkExist) {
				listError.add(MessageErrorProperties.getValue(Constant.ER016));
			}
		}
		return listError;
	}

	/**
	 * <b>Phương thức kiểm tra nhập liệu userInfo</b>
	 * 
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	public List<String> validateUserInfor(UserInfo userInfo) throws Exception {
		List<String> listError = new ArrayList<>();
		int userID = userInfo.getUserID();
		String loginName = userInfo.getLoginName();
		int groupId = userInfo.getGroupId();
		String fullName = userInfo.getFullName();
		String kanaName = userInfo.getKanaName();
		List<Integer> arrBirthDay = userInfo.getArrBirthDay();
		String email = userInfo.getEmail();
		String tel = userInfo.getTel();
		String passWord = userInfo.getPassWord();
		String confirmPassWord = userInfo.getConfirmPassWord();

		String codeLevel = userInfo.getCodeLevel();
		List<Integer> arrStartDate = userInfo.getArrStartDate();
		List<Integer> arrEndDate = userInfo.getArrEndDate();
		String total = userInfo.getTotal();
//		TblUserLogic tblUserLogicImpl = new TblUserLogicImpl();
		// trường hợp add, userID = 0
		if (userID == 0) {
			listError.addAll(checkLoginName(loginName));
			listError.addAll(checkGroupId(groupId, userID));
			listError.addAll(checkFullName(fullName));
			if (kanaName != null) {
				listError.addAll(checkFullNameKana(kanaName));
			}
			listError.addAll(checkBirthDay(arrBirthDay));
			listError.addAll(checkEmail(email, userID));
			listError.addAll(checkTel(tel));
			listError.addAll(checkPassWord(passWord));
			listError.addAll(checkConfirmPassWord(passWord, confirmPassWord));

			// có chọn trình độ tiếng Nhật
			if (!"".equals(codeLevel)) {
				listError.addAll(checkJapan(codeLevel, arrStartDate, arrEndDate, total));

			}
		}
		// trường hợp edit, userID != 0
		else {
			listError.addAll(checkGroupId(groupId, userID));
			listError.addAll(checkFullName(fullName));
			if (kanaName != null) {
				listError.addAll(checkFullNameKana(kanaName));

			}
			listError.addAll(checkBirthDay(arrBirthDay));
			listError.addAll(checkTel(tel));
			listError.addAll(checkEmail(email, userID));

			// có chọn trình độ tiếng Nhật
			if (!"".equals(codeLevel)) {
				listError.addAll(checkJapan(codeLevel, arrStartDate, arrEndDate, total));

			}
			// kiêm tra email có max length 255
			if (email.length() > 255) {
				listError.add(MessageErrorProperties.getValue(Constant.ER006_EMAIL));
			}
		}

		return listError;

	}

	/**
	 * @param codeLevel
	 * @param arrStartDate
	 * @param arrEndDate
	 * @param total
	 * @return
	 */
	private List<String> checkJapan(String codeLevel, List<Integer> arrStartDate, List<Integer> arrEndDate,
			String total) {
		List<String> listError = new ArrayList<>();
//		TblUserLogic tblUserLogicImpl = new TblUserLogicImpl();
		MstJapanLogic mstJapanLogicImpl = new MstJapanLogicImpl();

		// kiểm tra sự tồn tại của trình độ tiếng Nhật trong DB
		if (!mstJapanLogicImpl.checkExistLevelJapan(codeLevel)) {
			listError.add(MessageErrorProperties.getValue(Constant.ER004_CODE_LEVEL));
		}

		// check hợp lệ cho ngày cấp
		if (!Common.checkFormatDate(arrStartDate)) {
			listError.add(MessageErrorProperties.getValue(Constant.ER0011_START_DATE));
		}
		// kiểm tra ngày hết hạn không hợp lệ
		if (!Common.checkFormatDate(arrEndDate)) {
			listError.add(MessageErrorProperties.getValue(Constant.ER0011_END_DATE));
		}
		// kiểm tra ngày hết hạn có sau ngày cấp hay không
		if (!Common.compareDate(arrStartDate, arrEndDate)) {
			listError.add(MessageErrorProperties.getValue(Constant.ER0012));
		}
		// Không nhập total score
		if ("".equals(total)) {
			listError.add(MessageErrorProperties.getValue(Constant.ER001_TOTAL_SCORE));
		}
		// check half size
		if (!Common.checkFormat("HALFSIZE", total) | (Common.parseInt(total) < 0)) {
			listError.add(MessageErrorProperties.getValue(Constant.ER018_HALF_SIZE));
		}

		return listError;
	}

	/**
	 * @param confirmPassWord
	 * @return
	 */
	private List<String> checkConfirmPassWord(String passWord, String confirmPassWord) {
		List<String> listError = new ArrayList<>();
		// kiểm tra không nhập confirmPassWord
		if (confirmPassWord.isEmpty()) {
			listError.add(MessageErrorProperties.getValue(Constant.ER001_COMFIRM_PASSWORD));
		}
		// kiểm tra độ dài confirmPassWord trong khoảng 8->15
		if (!Common.checkLength(confirmPassWord, 8, 15)) {
			listError.add(MessageErrorProperties.getValue(Constant.ER007_COMFIRM_PASSWORD));
		}
		// kiểm tra kí tự 1 byte của của confirmPassWord
		if (!Common.checkBytePassWord(confirmPassWord)) {
			listError.add(MessageErrorProperties.getValue(Constant.ER008_COMFIRM_PASSWORD));
		}
		// kiểm tra confirmPassWord có giống password hay không
		if (!Common.checkConfirmPassWord(passWord, confirmPassWord)) {
			listError.add(MessageErrorProperties.getValue(Constant.ER0017_COMFIRM_PASSWORD));
		}
		return listError;
	}

	/**
	 * @param passWord
	 * @return
	 */
	private List<String> checkPassWord(String passWord) {
		List<String> listError = new ArrayList<>();

		// kiểm tra không nhập password
		if (passWord.isEmpty()) {
			listError.add(MessageErrorProperties.getValue(Constant.ER001_PASSWORD));
		}
		// kiểm tra độ dài password trong khoảng 8->15
		if (!Common.checkLength(passWord, 8, 15)) {
			listError.add(MessageErrorProperties.getValue(Constant.ER007_PASSWORD));
		}
		// kiểm tra kí tự 1 byte của của password
		if (!Common.checkBytePassWord(passWord)) {
			listError.add(MessageErrorProperties.getValue(Constant.ER008_PASSWORD));
		}
		return listError;
	}

	/**
	 * @param tel
	 * @return
	 */
	private List<String> checkTel(String tel) {
		List<String> listError = new ArrayList<>();

		// không nhập tel
		if (tel.isEmpty()) {
			listError.add(MessageErrorProperties.getValue(Constant.ER001_TEL));
		}
		// check format tel (xxxx-xxxx-xxxx)
		if (!Common.checkFormat("TEL", tel)) {
			listError.add(MessageErrorProperties.getValue(Constant.ER005_TEL));
		}
		// check max length = 14
		if (tel.length() > 14) {
			listError.add(MessageErrorProperties.getValue(Constant.ER006_TEL));
		}
		return listError;
	}

	/**
	 * @param email
	 * @return
	 */
	private List<String> checkEmail(String email, int userID) {
		List<String> listError = new ArrayList<>();
		try {
			TblUserLogic tblUserLogicImpl = new TblUserLogicImpl();
//			MstJapanLogic mstJapanLogicImpl = new MstJapanLogicImpl();

			// kiểm tra không nhập email
			if (email.isEmpty()) {
				listError.add(MessageErrorProperties.getValue(Constant.ER001_EMAIL));
			}
			// kiêm tra format email
			if (!Common.checkFormat("EMAIL", email)) {
				listError.add(MessageErrorProperties.getValue(Constant.ER005_EMAIL));
			}
			// kiểm tra email tồn tại trong DB
			if (tblUserLogicImpl.checkExistedEmail(email, userID)) {
				listError.add(MessageErrorProperties.getValue(Constant.ER003_EMAIL));
			}
			// kiêm tra email có max length 255
			if (email.length() > 255) {
				listError.add(MessageErrorProperties.getValue(Constant.ER006_EMAIL));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listError;
	}

	/**
	 * @param arrBirthDay
	 * @return
	 */
	private List<String> checkBirthDay(List<Integer> arrBirthDay) {
		List<String> listError = new ArrayList<>();

		// kiểm tra ngày sinh
		if (!Common.checkFormatDate(arrBirthDay)) {
			listError.add(MessageErrorProperties.getValue(Constant.ER011_BIRTHDAY));
		}
		return listError;
	}

	/**
	 * @param kanaName
	 * @return
	 */
	private List<String> checkFullNameKana(String kanaName) {
		List<String> listError = new ArrayList<>();

		// kiểm tra giá trị nhập vào là kí tự kana
		if (!Common.checkFormat("KATAKANA", kanaName)) {
			listError.add(MessageErrorProperties.getValue(Constant.ER009));
		}
		// kiểm tra kana có max lengh 255
		if (kanaName.length() > 255) {
			listError.add(MessageErrorProperties.getValue(Constant.ER006_KATAKANA));
		}
		return listError;
	}

	/**
	 * @param fullName
	 * @return
	 */
	private List<String> checkFullName(String fullName) {
		List<String> listError = new ArrayList<>();

		// kiểm tra không nhập fullName
		if (fullName.isEmpty() || fullName == null) {
			listError.add(MessageErrorProperties.getValue(Constant.ER001_FULLNAME));
		}
		// kiểm tra fullName có max lengh 255
		if (fullName.length() > 255) {
			listError.add(MessageErrorProperties.getValue(Constant.ER006_FULLNAME));
		}
		return listError;

	}

	/**
	 * @param groupId
	 * @return
	 */
	private List<String> checkGroupId(int groupId, int userID) {
		List<String> listError = new ArrayList<>();
		TblUserLogic tblUserLogicImpl = new TblUserLogicImpl();
		try {

			// kiểm tra không chọn nhóm
			if (groupId == 0) {
				listError.add(MessageErrorProperties.getValue(Constant.ER002_GROUP));
			}
			if (userID != 0) {
				// kiểm tra nhóm tồn tại trong DB
				if (!tblUserLogicImpl.checkExistedGroupId(groupId)) {
					listError.add(MessageErrorProperties.getValue(Constant.ER004_GROUP));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return listError;
	}

	/**
	 * @param loginName
	 * @return listError
	 */
	private List<String> checkLoginName(String loginName) {
		List<String> listError = new ArrayList<>();
		TblUserLogic tblUserLogicImpl = new TblUserLogicImpl();

		try {
			// kiểm tra có nhập loginName hay không
			if (loginName.isEmpty()) {
				listError.add(MessageErrorProperties.getValue(Constant.ER001_USER_NAME));
			}
			// kiểm tra độ dài của loginName
			if (!Common.checkLength(loginName, 6, 15)) {
				listError.add(MessageErrorProperties.getValue(Constant.ER007_LOGINNAME));
			}
			// kiểm tra các ký tự (a-z, A-Z, 0-9 và _). Ký tự đầu tiên không phải là số.
			if (!Common.checkFormat("USERNAME", loginName)) {
				listError.add(MessageErrorProperties.getValue(Constant.ER019));
			}
			// kiểm tra loginName tồn tại trong DB
			if (tblUserLogicImpl.checkExistedLoginName(loginName)) {
				listError.add(MessageErrorProperties.getValue(Constant.EROO3_USER_NAME));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listError;

	}

	/**
	 * <b>Phương thức validate tồn tại trong DB cho loginname , nhóm, email, codeLevel sử dụng ADM004 trường hợp add và edit </b>
	 * 
	 * @param userInfo
	 * @return true nếu không lỗi
	 */
	public boolean checkValidateUserInfoDB(UserInfo userInfo) {
		boolean flag = true;
		TblUserLogic tblUserLogicImpl = new TblUserLogicImpl();
		MstJapanLogic mstJapanLogicImpl = new MstJapanLogicImpl();
		try {
			if (userInfo.getUserID() == 0) {
				// kiểm tra loginName tồn tại trong DB
				if (tblUserLogicImpl.checkExistedLoginName(userInfo.getLoginName())) {
					System.out.println("login đã tồn tại");
					flag = false;
				}
			}
			// kiểm tra nhóm tồn tại trong DB
			if (!tblUserLogicImpl.checkExistedGroupId(userInfo.getGroupId())) {
				System.out.println("nhóm không tồn tại");
				flag = false;
			}
			// kiểm tra email tồn tại trong DB
			if (tblUserLogicImpl.checkExistedEmail(userInfo.getEmail(), userInfo.getUserID())) {
				System.out.println("mail đã tồn tại");
				flag = false;
			}
			// kiểm tra sự tồn tại của trình độ tiếng Nhật trong DB
			if (!mstJapanLogicImpl.checkExistLevelJapan(userInfo.getCodeLevel())) {
				System.out.println("codelevel không tồn tại");
				flag = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * kiểm tra passWord trong trường hợp changePassword
	 * @param passWord
	 * @param passWordConfirm
	 * @return danh sách lỗi khi xóa password
	 */
	public List<String> validatePassUserInfo(String passWord, String passWordConfirm) {
		List<String> listError = new ArrayList<>();
		listError.addAll(checkPassWord(passWord));
		listError.addAll(checkConfirmPassWord(passWord, passWordConfirm));
		return listError;
	}

}
