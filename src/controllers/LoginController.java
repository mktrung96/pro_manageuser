
package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ultis.Constant;
import validates.ValidateUser;

@WebServlet(urlPatterns = { "/login.do" })

/**
 * Class để xử lý cho màn hình ADM001 
 * 
 */

public class LoginController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			RequestDispatcher request = req.getRequestDispatcher(Constant.JSP_ADM001);
			request.forward(req, resp);
			// Nếu xảy ra lỗi thì sang màn hình system error
		} catch (Exception e) {
			RequestDispatcher requestDispatcher = req.getRequestDispatcher(Constant.URL_SYSTEM_ERROR);
			// Chuyển dữ liệu lỗi và user vừa set sang cho servlet systemerror
			requestDispatcher.forward(req, resp);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// Lấy đôi tượng session từ request
			HttpSession session = req.getSession();
			ValidateUser validateUser = new ValidateUser();
			// set character utf8
			req.setCharacterEncoding("UTF-8");
			// lấy giá trị trên textbox user
			String userName = req.getParameter("username");
			// lấy giá trị trên textbox pass
			String password = req.getParameter("password");
			// kiểm tra nhập liệu của user và pass
			List<String> listError = validateUser.checkValidateLogin(userName, password);
			// Nếu không có lỗi thì chuyển sang màn hình adm002
			if (listError.isEmpty()) {
				// Gán user cho session
				session.setAttribute(Constant.SESSION_LOGIN, userName);
				// Chuyển sang ListUserController để xử lý màn hình ADM002
				resp.sendRedirect(Constant.URL_LIST_USER);
				

			} else {
				// Nếu có lỗi thì set giá trị lỗi lên vị trí error trên ADM001
				req.setAttribute("listError", listError);
				// Gửi lại giá trị user vừa mới cài đặt
				req.setAttribute("username", userName);
				// tạo RequestDispatcher để chuyển tiếp dữ liệu lỗi cho ADM001
				RequestDispatcher request = req.getRequestDispatcher(Constant.JSP_ADM001);
				// Chuyển dữ liệu lỗi và user vừa set sang cho ADM001 để hiển thị
				request.forward(req, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect(Constant.URL_SYSTEM_ERROR);
		}
	}
}
