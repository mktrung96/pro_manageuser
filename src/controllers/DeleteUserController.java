package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logics.TblUserLogic;
import logics.impl.TblUserLogicImpl;
import ultis.Common;
import ultis.Constant;

/**
 * Servlet DeleteUserController thực hiện chức năng xóa user
 */
@WebServlet("/DeleteUser.do")
public class DeleteUserController extends HttpServlet {

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
			// lấy giá trị userID từ url
			int userID = Common.parseInt(request.getParameter("userID"));
			if (userID > 0) {
				TblUserLogic tblUserLogicImpl = new TblUserLogicImpl();
				if (!tblUserLogicImpl.checkExistIdUser(userID)) { // nếu tồn tại User
					// thực hiện delete user nếu thành công trả về true, ngược lại trả về false
					if (tblUserLogicImpl.deleteUser(userID)) {
						// nếu delete thành công sẽ chuyển sang màn hình success để thông báo
						response.sendRedirect(Constant.SUCCESS + "?type=" + Constant.DELETE_SUCCESS);
					} else { // nếu delete User thất bại
						response.sendRedirect(Constant.URL_SYSTEM_ERROR);
					}
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

}
