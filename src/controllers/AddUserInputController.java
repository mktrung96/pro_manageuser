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
import logics.impl.MstGroupLogicImpl;
import logics.impl.MstJapanLogicImpl;
import ultis.Common;
import ultis.ConfigProperties;
import ultis.Constant;
import validates.ValidateUser;

/**
 * Servlet implementation class AddUserInputController
 */
@WebServlet("/AddUserInput.do")
public class AddUserInputController extends HttpServlet {

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
			setDataLogicADM003(request, response); // set dữ liệu cho các combobox(list group, time) lên view
			UserInfo userInfo = getDefaultValueADM003(request, response);
			request.setAttribute("display", "none"); // none để ấn trình độ tiếng Nhật
			request.setAttribute("userInfo", userInfo); // đưa userInfo hiển thị ra view
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constant.JSP_ADM003);
			requestDispatcher.forward(request, response);
		} catch (ClassNotFoundException | SQLException e) {
			
			e.printStackTrace();
		}

	}

	/**
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
		// set các giá trị cho lên view
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
		// khởi tạo các giá trị mặc định
		UserInfo userInfo = new UserInfo();
		String codeLevel = "";
		List<Integer> arrBirthday = new ArrayList<Integer>();
		List<Integer> arrStartDate = new ArrayList<Integer>();
		List<Integer> arrEndDate = new ArrayList<Integer>();
		String type = request.getParameter("type");
		String edit = request.getParameter("edit");
		String loginName = "";
		String fullName = "";
		String kanaName = "";
		String email = "";
		String tel = "";
		String passWord = "";
		String confirmPassWord = "";
		String total = "";
		int groupId = 0;

		if ("ADM003".equals(type)) { // chọn chức năng ADD từ màn hình ADM002
			arrBirthday = Common.getArrYearMonthDayNow(); // mặc định giá trị cho time tại Birthday
			arrStartDate = Common.getArrYearMonthDayNow(); // mặc định giá trị cho time tại StartDate
			arrEndDate = Common.getArrYearMonthDayNow(); // mặc định giá trị cho time tại EndDate
		}
		// trường hợp validate khi ấn button OK ở ADM003
		else if ("validateUser".equals(type)) {
			if (!"edit".equals(edit)) {
				// lấy các dữ liệu trên form ADM003 để kiểm tra
				loginName = request.getParameter("loginName");
				groupId = Common.parseInt(request.getParameter("groupId"));
				fullName = request.getParameter("fullName");
				kanaName = request.getParameter("kanaName");
				email = request.getParameter("email");
				tel = request.getParameter("tel");
				passWord = request.getParameter("passWord");
				confirmPassWord = request.getParameter("confirmPassWord");
				codeLevel = request.getParameter("codeLevel");

				// set các tham số Year,Month,Day vào mảng Integer
				arrBirthday = Common.setArrayDate(request, "yearBirthDay", "monthBirthDay", "dayBirthDay");
				if (!"".equals(codeLevel)) {
					total = request.getParameter("total");
					arrStartDate = Common.setArrayDate(request, "yearStartDate", "monthStartDate", "dayStartDate");
					arrEndDate = Common.setArrayDate(request, "yearEndDate", "monthEndDate", "dayEndDate");
				}

			}

		}
		// trường hợp back từ màn hình ADM004 về ADM003
		else if ("back".equals(type)) {
			// lấy session từ request
			HttpSession session = request.getSession();
			// lấy key trên url
			String keyAdd = request.getParameter("key");
			// lấy userInfo trên session bằng key trên url
			userInfo = (UserInfo) session.getAttribute(keyAdd);
			// xoá session từ ADM004 về ADM003 sau khi lấy xong userInfo
			session.removeAttribute(keyAdd);
			return userInfo;

		}
		// set các giá trị cho userInfo
		userInfo.setLoginName(loginName);
		userInfo.setGroupId(groupId);
		userInfo.setFullName(fullName);
		userInfo.setKanaName(kanaName);
		userInfo.setEmail(email);
		userInfo.setTel(tel);
		userInfo.setPassWord(passWord);
		userInfo.setConfirmPassWord(confirmPassWord);
		userInfo.setArrBirthDay(arrBirthday);
		userInfo.setCodeLevel(codeLevel);

		// nếu có codeLevel
		if (!"".equals(codeLevel)) {
			userInfo.setArrStartDate(arrStartDate);
			userInfo.setArrEndDate(arrEndDate);
			userInfo.setTotal(total);
		}
		return userInfo;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// khởi tạo các giá trị mặc định
			UserInfo userInfo = new UserInfo();
			List<String> listError = new ArrayList<String>();
			ValidateUser validateUser = new ValidateUser();

			// userInfo là các giá trị được nhập vào từ màn hình
			userInfo = getDefaultValueADM003(request, response);
			// validate cho các giá trị được nhập vào
			listError = validateUser.validateUserInfor(userInfo);

			// chọn ẩn trình độ tiếng Nhật
			if ("".equals(userInfo.getCodeLevel())) {
				request.setAttribute("display", "none");
			}
			// hiển thị trình độ tiếng Nhật
			else {
				request.setAttribute("display", "block");
			}

			// nếu không có lỗi khi nhập liệu
			if (listError.size() == 0) {
				// chuyển các giá trị được nhập sang các giá trị đúng với trong DB
				userInfo = Common.convertType(userInfo);
				// đưa userInfo(các giá trị được nhập từ màn hình) lên session
				HttpSession session = request.getSession();
				String keyAdd = Common.getKey();
				session.setAttribute(keyAdd, userInfo);
				// set key xác nhận từ màn hình ADM003 sang ADM004
				session.setAttribute(Constant.STR_KEY_FLAG, Constant.STR_KEY_FLAG); 
				// chuyển sang màn ADM004
				response.sendRedirect(request.getContextPath() + "/AddUserConfirm.do?key=" + keyAdd);
			} 
			// nếu có lỗi khi nhập liệu
			else { 
				// hiển thị lỗi lên view và giữ lại các giá trị vừa nhập
				request.setAttribute("userInfo", userInfo); 
				request.setAttribute("listError", listError); 
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
