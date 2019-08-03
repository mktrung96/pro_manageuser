
package ultis;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dao.TblUserDao;
import dao.impl.TblUserDaoImpl;
import entities.TblDetailUserJapan;
import entities.TblUser;
import entities.UserInfo;
import logics.MstGroupLogic;
import logics.MstJapanLogic;
import logics.impl.MstGroupLogicImpl;
import logics.impl.MstJapanLogicImpl;

/**
 * Class Common Chứa các hàm common của dự án
 * 
 * 
 */
public class Common {
	/**
	 * 
	 * Phương thức mã hóa password
	 * 
	 * @param salt
	 * @param password
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String encryptPassword(String salt, String password) throws NoSuchAlgorithmException {
		// khởi tạo kết quả
		StringBuilder encrypt = new StringBuilder();
		// cộng 2 chuỗi
		String saltPassword = salt + "" + password;
		try {
			// thực hiện mã hóa theo chuẩn SHA-1
			MessageDigest messageDigest = MessageDigest.getInstance(Constant.HASH_CODE);
			byte byteArray[] = messageDigest.digest(saltPassword.getBytes());
			for (int i = 0; i < byteArray.length; i++) {
				encrypt.append(Integer.toString((byteArray[i] & 0xff) + 0x100, 16).substring(1));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw e;
		}
		return encrypt.toString();
	}

	/**
	 * Phương thức kiểm tra column truyền vào có trong danh sách comlumn không
	 * 
	 * @param column
	 * @param listColum
	 * @return Boolean<br>
	 *         true có trong danh sách<br>
	 *         false không có trong danh sách
	 */
	public static boolean checkColumnSort(String column, ArrayList<String> listColumn) {
		// kiểm tra cột có trong list cột không
		return listColumn.contains(column);
	}

	/**
	 * <b>Phương thức thay thế kí tự '%' và '_'<br>
	 * tránh kí tự wilcard không để xảy ra lỗi Sql injection</b>
	 * 
	 * @param value
	 * @return
	 */
	public static String replaceWilcard(String value) {
		value = value.replace("%", "\\%");
		value = value.replace("_", "\\_");
		return value;
	}

	/**
	 * Phương thức kiểm tra tài khoản đã kết nối hay chưa
	 *
	 * @param session giá trị session
	 * @return boolean <br>
	 *         true đã được kết nối<br>
	 *         false chưa kết nối tới server
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static boolean checkLogin(HttpSession session) throws ClassNotFoundException, SQLException {
		if (session != null) {
			// Lấy giá trị đã gửi trên session
			String valueSession = (String) session.getAttribute(Constant.SESSION_LOGIN);
			// Nếu giá trị lấy về khác null thì
			if (valueSession != null) {
				// kiểm tra xem giá trị trên session có phải là tài khoản admin k
				TblUserDao tblUserDao = new TblUserDaoImpl();
				// trả về thông tin tài khoản
				TblUser tblUser = tblUserDao.getAdmin(valueSession);
				// nếu khác null thì trả về true
				if (tblUser != null) {
					return true;
				}
			}
		}
		// nếu k thì trả về false
		return false;
	}

	/**
	 * Phương thức tạo chuỗi paging
	 *
	 * @param totalUser
	 * @param limitUser
	 * @param currentPage
	 * @return
	 */
	public static ArrayList<Integer> getListPaging(int totalUser, int limitUser, int currentPage) {
		// Khởi tạo danh sách cần trả về
		ArrayList<Integer> listPaging = new ArrayList<>();
		// tính tổng số trang dựa trên thương của totalUser và limitUser
		// ta làm tròn cận trên
		int totalPaging = getTotalPaging(totalUser, limitUser);
		// Lấy số paging giới hạn được hiển thị trên 1 trang trong properties
		// và chuyển sang giá trị int
		int limitPaging = getLimitPaging();
		// Kiểm tra số lượng Paging max > 0
		if (limitPaging > 0) {
			// Kiểm tra trang hiện tại có đúng điều kiện hay k
			if (totalPaging >= currentPage) {
				// Tìm paging đầu tiên dựa vào limitPaging và currentPage (trang hiện tại)
				int firstPaging = findFirstPaging(currentPage, limitPaging);
				// để xác định vị trí tiệm cận cuối cùng của Paging
				int asymLatePaging = 0;
				// tính paging max
				int maxPaging = firstPaging + limitPaging - 1;
				// nếu maxPaging < totalPaging
				if (maxPaging < totalPaging) {
					asymLatePaging = maxPaging;
					// ngược lại nếu maxPaging >= totalPaging
				} else {
					asymLatePaging = totalPaging;
				}
				// add vào listPaging và trả về
				for (int i = firstPaging; i <= asymLatePaging; i++) {
					listPaging.add(i);
				}
				// trả về kết quả tìm được
				return listPaging;
			}
		}
		// Nếu không tìm được trả về null
		return null;
	}

	/**
	 * Phương thức lấy giới hạn trang hiển thị
	 *
	 * @return
	 */
	public static int getLimitPaging() {
		// lấy dữ liệu từ file config
		String limitPage = ConfigProperties.getValue(Constant.LIMIT_PAGING);
		// chuyển sang kiểu dữ liệu int nếu bị lỗi thì đặt mặc định là 3
		return convertInterger(limitPage, 3);
	}

	/**
	 * Phương thức tìm tổng số paging
	 *
	 * @param totalUser
	 * @param limitUser
	 * @return
	 */
	public static int getTotalPaging(int totalUser, int limitUser) {
		// chia 2 số làm tròn cận trên và trả về kết quả
		return roundDivision(totalUser, limitUser);
	}

	/**
	 * Phương thức tìm trang đầu của Paging
	 *
	 * @param currentPage
	 * @param limitPaging
	 * @return trả về trang đầu luôn > 0
	 */
	public static int findFirstPaging(int currentPage, int limitPaging) {
		// khai bao trang đầu tiên
		int firstPaging = 0;
		// tìm vị trí phân ô của paging
		// qua phép làm tròn cận trên thương của 2 số currentPage và limitPaging
		int localPaging = roundDivision(currentPage, limitPaging);
		firstPaging = (localPaging - 1) * limitPaging + 1;
		if (firstPaging == 0) {
			firstPaging++;
		}
		// trả về kết quả luôn > 0
		return firstPaging;
	}

	/**
	 * Phương thức thực hiện phép chia và làm tròn cận trên
	 *
	 * @param divisor  <b>: int </b> số bị chia
	 * @param dividend <b>: int </b> số chia
	 * @return <b>: int </b> kết quả
	 */
	private static int roundDivision(int divisor, int dividend) {
		// Khai báo kết quả
		int result = 0;
		if (dividend > 0) {
			// tinh tông số trang dựa trên totalUser và limit
			// Kiểu dữ liệu int 4 byte float 4 byte
			result = (int) Math.ceil((float) divisor / dividend);
		}
		// Nếu dividend <= 0 thì return 0
		// Trả về kết quả
		return result;
	}

	/**
	 * 
	 * Phương thức chuyển đối kiểu dữ liệu từ String sang kiểu dữ liệu int
	 * 
	 * @param value  <b>: String</> giá trị cần chuyển đổi
	 * @param desire <b> int </b> Mong muốn của người dùng trả về kết quả<br>
	 *               khi value = null
	 * 
	 * @return <b> int </b> trả về kiểu dữ liệu int
	 * @throws NumberFormatException
	 */
	public static int convertInterger(String value, int desire) {
		// Khai báo kết quả trả về
		int result = desire;
		// Kiểm tra chuỗi có phải là số hay không
		if (checkNumber(value)) {
			// chuyển sang kiểu dữ liệu int
			result = Integer.parseInt(value);
		}
		// trả về kết quả
		return result;
	}

	/**
	 * Phương thức validate kiểm tra id có phải là số nguyên dương hay không
	 * 
	 * @param value giá trị id
	 * @return Boolean trả về true nếu chuỗi là số, false chuỗi đó không phải là số
	 */
	private static boolean checkNumber(String value) {
		// Biểu thức chính quy kiểm tra số nguyên dương
		String regex = "[0-9]+";
		// trả về kết quả kiểm tra
		return value.matches(regex);
	}

	/**
	 * Phương thức đổi kiểu sắp xếp
	 *
	 * @param sortValue kiểu sắp xếp truyền vào
	 * @return String kiểu sắp xếp đã được đổi
	 */
	public static String convertSortValue(String sortValue) {
		// đổi ngược lại kiểu sắp xếp so với kiểu sắp dếp truyền vào
		return (Constant.ASC.equals(sortValue)) ? Constant.DESC : Constant.ASC;
	}

	/**
	 * Phương thức đổi kiểu sắp xếp từ request sang tên cột trong sql
	 *
	 * @param sortType
	 * @return String tên cột trong data base
	 */
	public static String convertSortType(String sortType) {
		String sortTypeSQL = "";
		// trả về tên cột trong sql với tham số truyền vào tương ứng
		switch (sortType) {
		case Constant.TYPE_SORT_FULLNAME:
			sortTypeSQL = Constant.TBL_USER_FULLNAME;
			break;
		case Constant.TYPE_SORT_CODE_LEVEL:
			sortTypeSQL = Constant.MST_JAPAN_CODE_LEVEL;
			break;
		case Constant.TYPE_SORT_END_DATE:
			sortTypeSQL = Constant.TBL_DETAIL_END_DATE;
			break;
		default:
			break;
		}
		// trả về tên cột trong data base
		return sortTypeSQL;
	}

	/**
	 * Lấy giá trị từ session nếu giá trị bằng null thì lấy theo mặc định
	 * 
	 * @param session      Object cần chuyển đổi
	 * @param key          String Key của session cần lấy giá trị
	 * @param defaultValue Giá trị mặc định
	 * @return Object Giá trị cần lấy
	 */
	public static Object getValueSession(HttpSession session, String key, Object defaultValue) {
		// Lấy dữ liệu từ sesion
		Object result = session.getAttribute(key);
		// kiểm tra object truyền vào có phải là request thì thực hiện lấy dữ liệu
		return (result != null) ? result : defaultValue;
	}

	/**
	 * Lấy giá trị từ request nếu giá trị bằng null thì lấy theo mặc định
	 *
	 * @param request
	 * @param key
	 * @param defaultValue
	 * @return kết quả cần tìm
	 */
	public static Object getValueRequest(HttpServletRequest request, String key, Object defaultValue) {
		// lấy dữ liệu từ request
		String param = request.getParameter(key);
		// nếu param khác rỗng thì
		if (param != null) {
			// kiểm tra defaultValue truyền vào là kiểu dữ liệu Integer thì
			if (defaultValue instanceof Integer) {
				// kiểm tra param có phải là số thì thực hiện chuyển đổi sang int
				if (checkNumber(param)) {
					// trả về kết quả sau khi đã chuyển sang kiểu dữ liệu int
					return Integer.parseInt(param);
				}
				// nếu kiểu truyền vào defaultValue là kiểu dữ liệu string thì
			} else if (defaultValue instanceof String) {
				// trả về kết quả đã lấy
				return param;
			}
		}
		// trả về kết quả mặc định
		return defaultValue;
	}

	/**
	 * Hàm parseInt từ String sang Integer
	 * 
	 * @param str chuỗi string cần parserInt
	 * @return Trả về số nếu chuỗi là số trả về -1 nếu chuỗi ko phải là số
	 */

	public static int parseInt(String str) {
		int temp = 0;
		try {
			temp = Integer.parseInt(str);
		} catch (Exception e) {
			temp = 0;
		}
		return temp;
	}

	/**
	 * Lấy năm hiện tại
	 * 
	 * @return
	 */
	public static int getYearNow() {
		Calendar calendar = Calendar.getInstance();
		int nowYear = calendar.get(Calendar.YEAR);
		return nowYear;
	}

	/**
	 * Lấy danh sách các năm từ năm 1900 -> năm hiện tại + 1
	 */
	public static List<Integer> getListYear(int fromYear, int toYear) {
		List<Integer> listYear = new ArrayList<>();
		if (fromYear > 1900 && toYear < Common.getYearNow() + 1) {
			for (int i = fromYear; i <= toYear; i++) {
				listYear.add(i);
			}
		}
		return listYear;

	}

	/**
	 * Lấy danh sách các tháng
	 * 
	 * @return List<Integer> list các tháng trong năm
	 */

	public static List<Integer> getListMonth() {
		List<Integer> listMonth = new ArrayList<>();
		int month = 1;
		while (month <= 12) {
			listMonth.add(month);
			month++;
		}
		return listMonth;
	}

	/**
	 * Lấy danh sách các ngày từ 1->31
	 * 
	 * @return List<Integer> list các ngày trong tháng
	 */

	public static List<Integer> getListDay() {
		List<Integer> listDay = new ArrayList<>();
		int day = 1;
		while (day <= 31) {
			listDay.add(day);
			day++;
		}
		return listDay;
	}

	/**
	 * Convert các số năm. Tháng ngày thành 1 chuỗi ngày tháng có format yyyy/mm/dd
	 * 
	 * @return
	 */
	public static String convertToString(int year, int month, int day) {
		String str = year + "/" + month + "/" + day;
		return str;

	}

	/**
	 * Hàm ép chuỗi sang dạng date của sql: YYYY-MM-DD
	 * 
	 * @param list
	 * @return chuỗi string theo định dạng YYYY-MM-DD
	 */
	public static String convertArrToStringDate(List<Integer> list) {
		String str = list.get(0) + "-" + list.get(1) + "-" + list.get(2);
		return str;
	}

	/**
	 * Hàm ép chuỗi sang dạng date của sql: YYYY-MM-DD
	 * 
	 * @param list
	 * @return chuỗi string theo định dạng YYYY-MM-DD
	 */
	public static Date convertArrToDate(List<Integer> list) {
		String str = list.get(0) + "-" + list.get(1) + "-" + list.get(2);
		return Date.valueOf(str);
	}

	/**
	 * Hàm ép dạng date của sql: YYYY-MM-DD chuỗi sang dạng List<Integer>
	 * 
	 * @param date
	 * @return List<Integer> chứa các giá trị ngày tháng năm của date
	 */
	public static List<Integer> convertDateToArr(Date date) {
		List<Integer> arrDate = new ArrayList<>();
		// khởi tạo đối tượng Calendar
		Calendar call = Calendar.getInstance();
		call.setTime(date);
		int year = call.get(Calendar.YEAR);
		int month = call.get(Calendar.MONTH);
		int day = call.get(Calendar.DAY_OF_MONTH);
		arrDate.add(year);
		arrDate.add(month);
		arrDate.add(day);
		return arrDate;
	}

	/**
	 * Lấy năm bắt đầu từ file config.properties
	 * 
	 * @return
	 */

	public static int getStartYear() {
		int startYear = Common.parseInt(ConfigProperties.getValue("startYear"));
		return startYear;
	}

	public static boolean checkFormat(String type, String string) {
		String regex = "";
		switch (type) {
		case "EMAIL":
			regex = "^(.+)@(.+)$";
			break;
		case "TEL":
			regex = "^\\d{1,4}-\\d{1,4}-\\d{1,4}$";
			break;
		case "USERNAME":
			regex = "^[a-zA-Z][a-zA-Z0-9_]{1,}$";
			break;
		case "KATAKANA":
			regex = "[ァ-ヶ]*";
			break;
		case "HALFSIZE":
			regex = "[1-9][0-9]{1,2}$";
			break;
//		case "PASS":
//			regex = "^[a-zA-Z0-9._-]{1,}$";
//			break;
		default:
			break;
		}
		Pattern valid = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = valid.matcher(string);
		return matcher.matches();
	}

	/**
	 * Kiểm tra ngày có hợp lệ
	 * 
	 * @param arrDate
	 * @return true nếu hợp lệ
	 */
	public static boolean checkFormatDate(List<Integer> arrDate) {
		int year = arrDate.get(0);
		int month = arrDate.get(1);
		int day = arrDate.get(2);
		boolean check = true;
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			if (day < 1 || day > 31) {
				check = false;
			}
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			if (day < 1 || day > 30) {
				check = false;
			}
			break;
		case 2:
			// Nếu là năm nhuận
			if (((year % 4 == 0) && !(year % 100 == 0)) || (year % 400 == 0)) {
				if (day < 1 || day > 29) {
					check = false;
				}
			}
			// nếu không phải là năm nhuận
			else {
				if (day < 1 || day > 28) {
					check = false;
				}
			}
			break;
		default:
			check = false;
		}
		return check;
	}

	/**
	 * Kiểm tra độ dài chuỗi có nằm trong khoảng từ i -> j hay không
	 * 
	 * @param data
	 * @param i
	 * @param j
	 * @return
	 */
	public static boolean checkLength(String data, int i, int j) {
		int leng = data.length();
		if (leng >= i && leng <= j) {
			return true;
		}
		return false;
	}

	/**
	 * Kiểm tra kí tự 1 byte của password
	 * 
	 * @param passWord
	 * @return true nếu là kí tự 1 byte
	 */
	public static boolean checkBytePassWord(String passWord) {
		for (int i = 0; i < passWord.length(); i++) {
			int pos = passWord.charAt(i);
			if (pos < 0 || pos > 255) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Kiểm tra confirmPassWord có giống với password hay không
	 * 
	 * @param confirmPassWord
	 * @return
	 */
	public static boolean checkConfirmPassWord(String passWord, String confirmPassWord) {
		boolean check = passWord.equals(confirmPassWord);
		return check;
	}

	/**
	 * Kiểm tra ngày hết hạn có sau ngày cấp hay không
	 * 
	 * @param arrStartDate
	 * @param arrEndDate
	 * @return true nếu ngày hết hạn sau ngày cấp
	 */
	public static boolean compareDate(List<Integer> arrStartDate, List<Integer> arrEndDate) {
		boolean check = true;
		// nếu năm hết hạn lớn hơn năm cấp
		if (arrEndDate.get(0) < arrStartDate.get(0)) {
			check = false;
		}
		// nếu năm hết hạn bằng năm cấp
		else if (arrEndDate.get(0).equals(arrStartDate.get(0))) {
			// nếu tháng hết hạn lớn hơn tháng cấp
			if (arrEndDate.get(1) < arrStartDate.get(1)) {
				check = false;
			}
			// tháng hết hạn = tháng cấp
			else if (arrEndDate.get(1).equals(arrStartDate.get(1))) {
				// ngày hết hạn nhỏ hơn ngày cấp || ngày hết hạn = ngày cấp
				if (arrEndDate.get(2) < arrStartDate.get(2) || arrEndDate.get(2).equals(arrStartDate.get(2))) {
					check = false;
				}
			}
		}
		return check;

	}

	/**
	 * Hàm lấy dữ liệu year, month, day từ request cho vào mảng Integer
	 * 
	 * @param request   request từ client
	 * @param yearDate  dữ liệu year trên view
	 * @param monthDate dữ liệu month trên view
	 * @param dayDate   dữ liệu day trên view
	 * @return mảng Array Integer
	 */
	public static ArrayList<Integer> setArrayDate(HttpServletRequest request, String yearDate, String monthDate,
			String dayDate) {
		int year = Common.parseInt(request.getParameter(yearDate));
		int month = Common.parseInt(request.getParameter(monthDate));
		int day = Common.parseInt(request.getParameter(dayDate));
		ArrayList<Integer> array = new ArrayList<>();
		array.add(year);
		array.add(month);
		array.add(day);
		return array;
	}

	/**
	 * Phương thức lấy giá trị ngày tháng năm hiện tại
	 * 
	 * @return List<Integer> arrDate
	 */
	public static List<Integer> getArrYearMonthDayNow() {
		List<Integer> arrDate = new ArrayList<Integer>();
		Calendar calendar = Calendar.getInstance();
		int nowYear = calendar.get(Calendar.YEAR);
		int nowMonth = calendar.get(Calendar.MONTH);
		int nowDay = calendar.get(Calendar.DAY_OF_MONTH);
		arrDate.add(nowYear);
		arrDate.add(nowMonth);
		arrDate.add(nowDay);
		return arrDate;
	}

	/**
	 * Hàm lấy key ngẫu nhiên theo thời gian hiện tại
	 * 
	 * @return keySession
	 */
	public static String getKey() {
		String keySession = System.currentTimeMillis() + "";
		return keySession;
	}

	/**
	 * Phương thức chuyển các thuộc tính birthday, startDate, endDate, groupId của
	 * userInfo sang kiểu dữ liệu của DB
	 * 
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	public static UserInfo convertType(UserInfo userInfo) throws SQLException, ClassNotFoundException {
		MstGroupLogic mstGroupLogicImpl = new MstGroupLogicImpl();
		MstJapanLogic mstJapanLogicImpl = new MstJapanLogicImpl();
		String codeLevel = userInfo.getCodeLevel();
		// khởi tạo đối tượng MstGroupLogicImpl
		String groupName = mstGroupLogicImpl.getMstGroupName(userInfo.getGroupId());
		// set giá trị groupName cho userInfo
		userInfo.setGroupName(groupName);
		if (userInfo.getArrBirthDay() != null) {
			// set giá trị Birthday có kiểu dữ liệu sql.Date
			userInfo.setBirthday(Date.valueOf(Common.convertArrToStringDate(userInfo.getArrBirthDay())));
		}

		if (codeLevel != "") { // nếu tồn tại trình độ tiếng Nhật
			// lấy giá trị nameLevel khi biết codeLevel
			String nameLevel = mstJapanLogicImpl.getMstJapanName(codeLevel);
			// set giá trị nameLevel cho userInfo
			userInfo.setNameLevel(nameLevel);
			// set giá trị StartDate có kiểu dữ liệu sql.Date
			userInfo.setStartDate(Date.valueOf(Common.convertArrToStringDate(userInfo.getArrStartDate())));
			// set giá trị EndDate có kiểu dữ liệu sql.Date
			userInfo.setEndDate(Date.valueOf(Common.convertArrToStringDate(userInfo.getArrEndDate())));
		}
		return userInfo;
	}

	/**
	 * <b>Phương thức lấy các giá trị của userInfo để tạo 1 đối tượng con tblUser
	 * dùng để thêm vào DB</b>
	 * 
	 * @param userInfo
	 * @return tblUser
	 * @throws NoSuchAlgorithmException
	 */
	public static TblUser convertUserInfoToTblUser(UserInfo userInfo) throws NoSuchAlgorithmException {
		TblUser tblUser = new TblUser();
		tblUser.setBirthDay(userInfo.getBirthday());
		tblUser.setEmail(userInfo.getEmail());
		tblUser.setFullName(userInfo.getFullName());
		tblUser.setFullNameKana(userInfo.getKanaName());
		tblUser.setGroupId(userInfo.getGroupId());
		tblUser.setLoginName(userInfo.getLoginName());
		tblUser.setPass(userInfo.getPassWord());
		tblUser.setTel(userInfo.getTel());
		tblUser.setUserId(userInfo.getUserID());
		tblUser.setRule(Constant.ADMIN);
		String salt = createSalt();
		tblUser.setSalt(salt);
		tblUser.setPass(Common.encryptPassword(salt, userInfo.getPassWord()));
		return tblUser;
	}

	/**
	 * <b>Phương thức lấy các giá trị của userInfo để tạo 1 đối tượng con
	 * TblDetailUserJapan</b>
	 * 
	 * @param userInfo
	 * @return tblDetailUserJapan
	 */
	public static TblDetailUserJapan convertUserInfoToDetailUserJapan(UserInfo userInfo) {
		TblDetailUserJapan tblDetailUserJapan = new TblDetailUserJapan();
		tblDetailUserJapan.setUserId(userInfo.getUserID());
		tblDetailUserJapan.setEndDate(userInfo.getEndDate());
		tblDetailUserJapan.setStartDate(userInfo.getStartDate());
		tblDetailUserJapan.setCodeLevel(userInfo.getCodeLevel());
		tblDetailUserJapan.setTotal(userInfo.getTotal());
		return tblDetailUserJapan;
	}

	/**
	 * <b>phương thức tạo salt: chuỗi các kí tự được sinh ngẫu nhiên dùng cho việc
	 * mã hóa password</b>
	 * 
	 * @return String
	 */
	public static String createSalt() {
		// độ dài của salt
		int len = 10;
		// các kí tự trong salt được lấy ngẫu nhiên từ chuỗi sau
		String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrs";
		// khởi tạo hàm random
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			// các kí tự của salt được lấy ngẫu nhiên từ chuỗi AB
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

}
