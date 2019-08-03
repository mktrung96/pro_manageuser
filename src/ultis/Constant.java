
package ultis;

/**
 * Class Constant Chứa các constant của dự án
 */
public class Constant {
	// Khai báo giá trị quyền admin
	public static final int ADMIN = 0;
	// Khai báo giá trị quyền user
	public static final int USER = 1;

	// Khai bao kiểu sắp xếp
	public static final String ASC = "ASC";
	public static final String DESC = "DESC";

	// Khai báo paging mặc định
	public static final int CURRENT_PAGE_DEFAULT = 1;

	// Khai báo giá trị key của file config properties
	public static final String LIMIT_PAGING = "limitpaging";
	public static final String LIMIT_USER = "limituser";

	// Khai báo kiểu mã hóa
	public static final String HASH_CODE = "SHA1";

	// Khai báo lỗi

	public static final String ER001_USER_NAME = "ER001_USER_NAME";
	public static final String ER001_PASSWORD = "ER001_PASSWORD";
	public static final String ER001_FULLNAME = "ER001_FULLNAME";
	public static final String ER001_TEL = "ER001_TEL";
	public static final String ER001_EMAIL = "ER001_EMAIL";
	public static final String ER001_TOTAL_SCORE = "ER001_TOTAL_SCORE";
	public static final String ER002_GROUP = "ER002_GROUP";
	public static final String EROO3_USER_NAME = "EROO3_USER_NAME";
	public static final String ER003_EMAIL = "EROO3_EMAIL";
	public static final String ER004_GROUP = "ER004_GROUP";
	public static final String ER005_EMAIL = "ER005_EMAIL";
	public static final String ER005_TEL = "ER005_TEL";
	public static final String ER006_TEL = "ER006_TEL";
	public static final String ER006_FULLNAME = "ER006_FULLNAME";
	public static final String ER006_KATAKANA = "ER006_KATAKANA";
	public static final String ER006_EMAIL = "ER006_EMAIL";
	public static final String ER007_LOGINNAME = "ER007_LOGINNAME";
	public static final String ER007_PASSWORD = "ER007_PASSWORD";
	public static final String ER008 = "ER008";
	public static final String ER008_PASSWORD = "ER008_PASSWORD";
	public static final String ER009 = "ER009";
	public static final String ER010 = "ER0010";
	public static final String ER011_BIRTHDAY = "ER011_BIRTHDAY";
	public static final String ER012 = "ER0012";
	public static final String ER013 = "ER0013";
	public static final String ER014 = "ER0014";
	public static final String ER015 = "ER0015";
	public static final String ER016 = "ER0016";
	public static final String ER017 = "ER0017";
	public static final String ER018_HALF_SIZE = "ER018_HALF_SIZE";
	public static final String ER019 = "ER0019";
	public static final String ER001_COMFIRM_PASSWORD = "ER001_COMFIRM_PASSWORD";
	public static final String ER0017_COMFIRM_PASSWORD = "ER0017_COMFIRM_PASSWORD";
	public static final String ER007_COMFIRM_PASSWORD = "ER007_COMFIRM_PASSWORD";
	public static final String ER008_COMFIRM_PASSWORD = "ER008_COMFIRM_PASSWORD";
	public static final String ER004_CODE_LEVEL = "ER004_CODE_LEVEL";
	public static final String ER0011_START_DATE = "ER0011_START_DATE";
	public static final String ER0011_END_DATE = "ER0011_END_DATE";
	public static final String ER0012 = "ER0012";

	// Khai báo url servlet
	public static final String URL_LIST_USER = "listuser.do";
	public static final String URL_SYSTEM_ERROR = "systemerror";
	public static final String URL_LOGOUT = "logout.do";
	public static final String URL_LOGIN = "login.do";
	public static final String SUCCESS = "success.do";
//	public static final String ADD_USER_INPUT = "adduserinput.do";
	// Khai báo tên của các jsp
	public static final String JSP_ADM001 = "view/jsp/ADM001.jsp";
	public static final String JSP_ADM002 = "view/jsp/ADM002.jsp";
	public static final String JSP_ADM003 = "view/jsp/ADM003.jsp";
	public static final String JSP_ADM004 = "view/jsp/ADM004.jsp";
	public static final String JSP_ADM005 = "view/jsp/ADM005.jsp";
	public static final String JSP_ADM006 = "view/jsp/ADM006.jsp";
	public static final String JSP_SYSTEM_ERROR = "view/jsp/SystemError.jsp";
	public static final String JSP_CHANGE_PASSWORD = "view/jsp/ChangePassword.jsp";

	// Khai báo kiểu chức năng trên màn hình ADM002
	public static final String TYPE_SORT = "sort";
	public static final String TYPE_PAGING = "paging";
	public static final String TYPE_SEARCH = "search";
	public static final String TYPE_BACK = "back";

	// Khai báo id của group để thực hiện chức năng tìm kiếm all
	public static final int ID_GROUP_FIND_ALL = 0;

	// khai báo các điều kiện mặc định của ListUserController
	public static final String DEFAULT_SORT_FULLNAME = "ASC";
	public static final String DEFAULT_SORT_CODE_LEVEL = "ASC";
	public static final String DEFAULT_SORT_END_DATE = "DESC";
	public static final int DEFAULT_OFFSET = 0;
	public static final String DEFAULT_FULLNAME = "";
	public static final int DEFAULT_GROUP_ID = 0;
	public static final String DEFAULT_SORT_TYPE = "sortFullName";
	public static final int DEFAULT_CURRENT_PAGE = 1;

	// Khai báo session cho ListUserController
	public static final String SESSION_LOGIN = "session_login";
	public static final String SESSION_FULLNAME = "session_fullName";
	public static final String SESSION_GROUP_ID = "session_groupID";
	public static final String SESSION_SORT_TYPE = "session_sortType";
	public static final String SESSION_SORT_VALUE = "session_sortValue";
	public static final String SESSION_SORT_FULLNAME = "session_sort_fullName";
	public static final String SESSION_SORT_CODE_LEVEL = "session_sort_codeLevel";
	public static final String SESSION_SORT_END_DATE = "session_sort_endDate";
	public static final String SESSION_CURRENT_PAGE = "session_currentPage";

	// type sort
	public static final String TYPE_SORT_FULLNAME = "sortFullName";
	public static final String TYPE_SORT_CODE_LEVEL = "sortCodeLevel";
	public static final String TYPE_SORT_END_DATE = "sortEndDate";

	// Khai bao thuộc tinh tên các cột trong tbl_user
	public static final String TBL_USER = "tbl_user";
	public static final String TBL_USER_GROUP_ID = "tbl_user.group_id";
	public static final String TBL_USER_LOGIN_NAME = "tbl_user.login_name";
	public static final String TBL_USER_RULE = "tbl_user.rule";
	public static final String TBL_USER_PASS = "tbl_user.pass";
	public static final String TBL_USER_SALT = "tbl_user.salt";
	public static final String TBL_USER_USER_ID = "tbl_user.user_id";
	public static final String TBL_USER_FULLNAME = "tbl_user.full_name";
	public static final String TBL_USER_FULLNAME_KANA = "tbl_user.full_name_kana";

	public static final String TBL_USER_BIRTHDAY = "tbl_user.birthday";
	public static final String TBL_USER_EMAIL = "tbl_user.email";
	public static final String TBL_USER_TEL = "tbl_user.tel";

	// khai báo thuộc tính tên cột trong bảng mst_group
	public static final String MST_GROUP = "mst_group";
	public static final String MST_GROUP_GROUP_ID = "mst_group.group_id";
	public static final String MST_GROUP_GROUP_NAME = "mst_group.group_name";

	// khai báo thuộc tính tên cột trong bảng mst_japan
	public static final String MST_JAPAN = "mst_japan";
	public static final String MST_JAPAN_NAME_LEVEL = "mst_japan.name_level";
	public static final String MST_JAPAN_CODE_LEVEL = "mst_japan.code_level";

	// khai báo thuộc tính tên cột trong bảng tbl_detail_user_japan
	public static final String TBL_DETAIL = "tbl_detail_user_japan";
	public static final String TBL_DETAIL_TOTAL = "tbl_detail_user_japan.total";
	public static final String TBL_DETAIL_START_DATE = "tbl_detail_user_japan.start_date";
	public static final String TBL_DETAIL_END_DATE = "tbl_detail_user_japan.end_date";
	public static final String TBL_DETAIL_CODE_LEVEL = "tbl_detail_user_japan.code_level";
	public static final String TBL_DETAIL_USER_ID = "tbl_detail_user_japan.user_id";

	// Khai báo các câu thông báo trong chương trình
	public static final String MSG001 = "MSG001";
	public static final String MSG002 = "MSG002";
	public static final String MSG003 = "MSG003";
	public static final String MSG004 = "MSG004";
	public static final String MSG005 = "MSG005";
	public static final String MSG006 = "MSG006";
	
	public static final String SYSTEM_ERROR = "error";
	public static final String INSERT_SUCCESS = "insertsuccess";
	public static final String EDIT_SUCCESS = "editsuccess";
	public static final String DELETE_SUCCESS = "deletesuccess";
	public static final String CHANGE_PASSWORD_SUCCESS = "changepasswordsuccess";
	public static final String MESSAGE = "message";
	public static final String STR_KEY_FLAG = "key_flag";

}
