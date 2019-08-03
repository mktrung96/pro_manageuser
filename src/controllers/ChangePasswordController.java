package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logics.TblUserLogic;
import logics.impl.TblUserLogicImpl;
import ultis.Common;
import ultis.Constant;
import validates.ValidateUser;

/**
 * Servlet implementation class ChangePasswordController
 */
@WebServlet("/ChangePassword.do")
public class ChangePasswordController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @throws IOException
	 * @throws ServletException
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// lấy giá trị userID từ url
			int userID = Common.parseInt(request.getParameter("userID"));
			request.setAttribute("userID", userID);

			if (userID > 0) {
				TblUserLogic tblUserLogicImpl = new TblUserLogicImpl();
				if (tblUserLogicImpl.checkExistIdUser(userID)) { // nếu tồn tại User
					RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constant.JSP_CHANGE_PASSWORD);
					requestDispatcher.forward(request, response);
				} else { // nếu User không tồn tại
					response.sendRedirect(Constant.URL_SYSTEM_ERROR);
				}
			} else { // không có IdUser
				response.sendRedirect(Constant.URL_SYSTEM_ERROR);
			}
		}

		catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// lấy giá trị userID từ url
		int userID = Common.parseInt(request.getParameter("userID"));
		// pass được nhập
		String passWord = request.getParameter("passWord");
		// pass confirm được nhập
		String passWordConfirm = request.getParameter("confirmPassWord");
		TblUserLogic tblUserLogicImpl = new TblUserLogicImpl();
		try {
			ValidateUser validateUser = new ValidateUser();
			List<String> err = new ArrayList<>();
			// kiểm tra sự tồn tại của userInfo
			if (tblUserLogicImpl.checkExistIdUser(userID)) {
				// validate pass và confirmpass được nhập vào
				err = validateUser.validatePassUserInfo(passWord, passWordConfirm);
				if (err.size() == 0) { // nếu validate không có lỗi
					if (tblUserLogicImpl.updatePasswordUser(userID, passWord)) { // nếu update Pass thành công
						// chuyển sang màn hình hiển thị thông báo thành công
						response.sendRedirect(Constant.SUCCESS + "?type=" + Constant.CHANGE_PASSWORD_SUCCESS);
					} else {
						response.sendRedirect(Constant.URL_SYSTEM_ERROR);
					}
				}
				// validate có pass có lỗi
				else {
					// hiển thị lỗi lên màn hình change pass
					request.setAttribute("err", err);
					RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constant.JSP_CHANGE_PASSWORD);
					requestDispatcher.forward(request, response);
				}
			} else { // nếu User không tồn tại
				response.sendRedirect(Constant.URL_SYSTEM_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(Constant.URL_SYSTEM_ERROR);
		}
	}

}
