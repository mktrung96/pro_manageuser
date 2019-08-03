package controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.UserInfo;
import logics.TblUserLogic;
import logics.impl.TblUserLogicImpl;
import ultis.Constant;
import validates.ValidateUser;

/**
 * Servlet implementation class AddUserConfirmController
 */
@WebServlet("/AddUserConfirm.do")
public class AddUserConfirmController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Phương thức lấy dữ liệu từ ADM003 và đổ lên ADM004 trường hợp Add
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// khởi tạo đối tượng userInfo
		UserInfo userInfo = new UserInfo();
		// lấy session từ request
		HttpSession session = request.getSession();
		// lấy key trên url
		String keyAdd = request.getParameter("key");

		// lấy giá trị key flag trên session để xác nhận trường hợp chuyển từ 03 sang 04
		String keyConfirm = (String) session.getAttribute(Constant.STR_KEY_FLAG);
		session.removeAttribute(Constant.STR_KEY_FLAG);
		// trường hợp chuyển từ màn hình ADM003 sang ADM004
		if (keyConfirm != null) {
			// lấy userInfo trên session bằng key trên url
			userInfo = (UserInfo) session.getAttribute(keyAdd);

			request.setAttribute("key", keyAdd);

			// set mặc định là ẩn trình độ tiếng Nhật
			request.setAttribute("display", "none");
			// Nếu không có trình độ tiếng Nhật thì ẩn đi
			if ("".equals(userInfo.getCodeLevel())) {
				// độ tiếng Nhật
				request.setAttribute("display", "none");
			} else {
				request.setAttribute("display", "block");
			}
			request.setAttribute("userInfo", userInfo);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constant.JSP_ADM004);
			requestDispatcher.forward(request, response);

		} else {
			response.sendRedirect(Constant.URL_LIST_USER);
		}

	}

	/**
	 * Phương thức lấy xử lí khi ấn nút OK màn hình ADM004 trường hợp Add
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			TblUserLogic tblUserLogicImpl = new TblUserLogicImpl();
			// lấy session từ request
			HttpSession session = request.getSession();
			// lấy key trên url
			String keyOK = request.getParameter("key");
			// lấy userInfo trên session bằng key trên url
			UserInfo userInfo = (UserInfo) session.getAttribute(keyOK);
			// xoá session từ ADM003 sang ADM004 sau khi lấy xong userInfo
			ValidateUser validateUser = new ValidateUser();
			// check validate cho các giá trị userInfo còn
			if (validateUser.checkValidateUserInfoDB(userInfo)) {
				boolean insert = tblUserLogicImpl.createUser(userInfo);
				if (insert) {
					response.sendRedirect(Constant.SUCCESS + "?type=" + Constant.INSERT_SUCCESS);
				}
			}
		} catch (SQLException |

				ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

}
