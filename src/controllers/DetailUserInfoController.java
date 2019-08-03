package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.UserInfo;
import logics.MstGroupLogic;
import logics.MstJapanLogic;
import logics.TblUserLogic;
import logics.impl.MstGroupLogicImpl;
import logics.impl.MstJapanLogicImpl;
import logics.impl.TblUserLogicImpl;
import ultis.Common;
import ultis.Constant;

/**
 * Servlet DetailUserInfoController: hiển thị màn hình ADM005
 */
@WebServlet("/DetailUserInfo.do")
public class DetailUserInfoController extends HttpServlet {

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
			// khởi tạo đối tượng TblUserLogicImpl
			TblUserLogic tblUserLogicImpl = new TblUserLogicImpl();
			MstGroupLogic mstGroupLogicImpl = new MstGroupLogicImpl();
			MstJapanLogic msJapanLogicImpl = new MstJapanLogicImpl();
			// lấy giá trị userID lưu trên url
			int userID = Common.parseInt(request.getParameter("userID"));
			// kiểm tra userId có trong DB hay không
			if (tblUserLogicImpl.checkExistUserById(userID)) {
				// nếu có thì thực hiện truy vấn lấy ra 1 userInfo
				UserInfo userInfo = tblUserLogicImpl.getUserInfoById(userID);
				
				String groupName = mstGroupLogicImpl.getMstGroupName(userInfo.getGroupId());
				String nameLevel = msJapanLogicImpl.getMstJapanName(userInfo.getCodeLevel());
				userInfo.setUserID(userID);
				userInfo.setGroupName(groupName);
				userInfo.setNameLevel(nameLevel);

				// set userInfo lên session
				HttpSession session = request.getSession();
				String keyChange = Common.getKey();
				session.setAttribute(keyChange, userInfo);
				// set key xác nhận từ màn hình ADM005 sang ADM003
				session.setAttribute(Constant.STR_KEY_FLAG, Constant.STR_KEY_FLAG);

				request.setAttribute("key", keyChange); // set key lên url chuyển từ 05 sang 03
				request.setAttribute("userInfo", userInfo); // set giá trị userInfo sang view
				// Khởi tạo đối tượng RequestDispatcher
				RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constant.JSP_ADM005);
				// Chuyển dữ liệu sang jsp để hiển thị
				requestDispatcher.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(Constant.URL_SYSTEM_ERROR);
		}
		;
	}

}
