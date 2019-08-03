package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.MstGroup;
import entities.MstJapan;
import entities.UserInfo;
import logics.MstGroupLogic;
import logics.MstJapanLogic;
import logics.TblUserLogic;
import logics.impl.MstGroupLogicImpl;
import logics.impl.MstJapanLogicImpl;
import logics.impl.TblUserLogicImpl;
import ultis.Common;
import ultis.ConfigProperties;
import ultis.Constant;
import validates.ValidateUser;

@WebServlet("/EditUserInfo.do")
public class EditUserInfoController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			setDataLogicADM003(request, response);
			// khởi tạo đối tượng userInfo
			UserInfo userInfo = new UserInfo();
			// lấy session từ request
			HttpSession session = request.getSession();
			// lấy key trên url
			String keyChange = request.getParameter("key");
			// lấy giá trị key flag trên session để xác nhận trường hợp chuyển từ 03 sang 04
			String flag = (String) session.getAttribute(Constant.STR_KEY_FLAG);
			if (flag != null) {
				userInfo = (UserInfo) session.getAttribute(keyChange);
				String codeLevel = userInfo.getCodeLevel();
				userInfo.setArrBirthDay(Common.convertDateToArr(userInfo.getBirthday()));
				if ("".equals(codeLevel)) { // nếu có trình độ tiếng Nhật
					userInfo.setArrStartDate(Common.convertDateToArr(userInfo.getStartDate()));
					userInfo.setArrEndDate(Common.convertDateToArr(userInfo.getEndDate()));
				}

				session.removeAttribute(keyChange); // remove key khi dùng xong
				session.removeAttribute(Constant.STR_KEY_FLAG); // remove khi dùng
				request.setAttribute("edit", "edit");
				request.setAttribute("userInfo", userInfo);

				String key2 = Common.getKey();
				session.setAttribute(key2, userInfo); // set userInfo lên session
				session.setAttribute(Constant.STR_KEY_FLAG, Constant.STR_KEY_FLAG); // set key xác nhận từ màn hình
																					// ADM003 sang ADM004 trường hợp
																					// edit
			}

			RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constant.JSP_ADM003);
			requestDispatcher.forward(request, response);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Phương thức set khoảng giá trị cho các combobox
	 * 
	 * @param request
	 * @param response
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private void setDataLogicADM003(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException {
		// khởi tạo đối tượng MstGroupLogicImpl
		MstGroupLogic mstGroupLogicImpl = new MstGroupLogicImpl();
		// lấy danh sách group có trong db
		List<MstGroup> listGroup = mstGroupLogicImpl.getAllMstGroup();
		// khởi tạo đối tượng MstJapanLogicImpl
		MstJapanLogic mstJapanLogicImpl = new MstJapanLogicImpl();
		List<MstJapan> listJapan = mstJapanLogicImpl.getAllMstJapan();
		// lây năm bắt đầu trong file config.properties
		int startYear = Common.parseInt(ConfigProperties.getValue("startYear"));
		List<Integer> listYear = Common.getListYear(startYear, Common.getYearNow());
		List<Integer> listMonth = Common.getListMonth();
		List<Integer> listDay = Common.getListDay();

		// set các giá trị lên view
		request.setAttribute("listGroup", listGroup);
		request.setAttribute("listJapan", listJapan);
		request.setAttribute("listYear", listYear);
		request.setAttribute("listMonth", listMonth);
		request.setAttribute("listDay", listDay);
	}

	/**
	 * @param request
	 * @param response
	 */
	private UserInfo getDefaultValueADM003(HttpServletRequest request, HttpServletResponse response) {
		UserInfo userInfo = new UserInfo();// UserInfo để truyền lên ADM004
		String codeLevel = "";
		List<Integer> arrBirthday = new ArrayList<Integer>();
		List<Integer> arrStartDate = new ArrayList<Integer>();
		List<Integer> arrEndDate = new ArrayList<Integer>();
		String loginName = "";
		String fullName = "";
		String kanaName = "";
		String email = "";
		String tel = "";
		String total = "";
		int groupId = 0;

		// chọn nút OK tại ADM003
		// lấy các dữ liệu trên form ADM003 để kiểm tra
		loginName = request.getParameter("loginName");
		groupId = Common.parseInt(request.getParameter("groupId"));
		fullName = request.getParameter("fullName");
		kanaName = request.getParameter("kanaName");
		email = request.getParameter("email");
		tel = request.getParameter("tel");
		codeLevel = request.getParameter("codeLevel");
		total = request.getParameter("total");
		// set các tham số Year,Month,Day vào mảng Integer
		arrBirthday = Common.setArrayDate(request, "yearBirthDay", "monthBirthDay", "dayBirthDay");

		// set dữ liệu vào đối tượng userInfo
		userInfo.setLoginName(loginName);
		userInfo.setGroupId(groupId);
		userInfo.setFullName(fullName);
		userInfo.setKanaName(kanaName);
		userInfo.setEmail(email);
		userInfo.setTel(tel);
		userInfo.setTotal(total);
		userInfo.setArrBirthDay(arrBirthday);
		userInfo.setCodeLevel(codeLevel);
		// nếu có trình độ tiếng Nhật
		if (!"".equals(codeLevel)) {
			arrStartDate = Common.setArrayDate(request, "yearStartDate", "monthStartDate", "dayStartDate");
			arrEndDate = Common.setArrayDate(request, "yearEndDate", "monthEndDate", "dayEndDate");
			userInfo.setArrStartDate(arrStartDate);
			userInfo.setArrEndDate(arrEndDate);
		}
		return userInfo;
	}

	/**
	 * Phương thức xử lí sự kiện ấn Ok tại ADM003 trường hợp edit
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// khởi tạo các dữ liệu mặc định
			setDataLogicADM003(request, response);
			UserInfo userInfo = new UserInfo();
			List<String> listError = new ArrayList<String>();
			TblUserLogic tblUserLogicImpl = new TblUserLogicImpl();
			int userID = Common.parseInt(request.getParameter("userID"));
			userInfo = getDefaultValueADM003(request, response);
			// kiểm tra sự tồn tại của userID
			boolean checkUserID = tblUserLogicImpl.checkExistUserById(userID);
			if (checkUserID) {
				userInfo.setUserID(userID);
			} else {
				// nếu không tồn tại userID thì trả về UserInfo có userID = 0
				userInfo.setUserID(0);
			}
			// validate cho userInfo vừa nhập vào
			ValidateUser validateUser = new ValidateUser();
			listError = validateUser.validateUserInfor(userInfo);

			// xử lý sau khi vào 1 trong 2 trường hợp EDIT hoặc ADD
			if ("".equals(userInfo.getCodeLevel())) {
				request.setAttribute("display", "none"); // chọn ẩn trình độ tiếng Nhật
			} else {
				request.setAttribute("display", "block"); // hiển thị trình độ tiếng Nhật
			}
			request.setAttribute("edit", "edit");
			request.setAttribute("userInfo", userInfo); // request dữ liệu đã nhập lên form
			if (listError.size() == 0) {
				userInfo = Common.convertType(userInfo);
				HttpSession session = request.getSession();
				String keyEdit = Common.getKey();
				session.setAttribute(keyEdit, userInfo); // set userInfo lên session
				// set key xác nhận từ màn hình ADM003 sang ADM004
				session.setAttribute(Constant.STR_KEY_FLAG, Constant.STR_KEY_FLAG);
				request.setAttribute("key", keyEdit);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constant.JSP_ADM004);
				requestDispatcher.forward(request, response);
			} else {
				request.setAttribute("listError", listError); // request câu thông báo lỗi lên ADM003
				setDataLogicADM003(request, response);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constant.JSP_ADM003);
				requestDispatcher.forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(Constant.URL_SYSTEM_ERROR);
		}

	}

}
