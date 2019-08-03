

package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ultis.Constant;

/**
 * Class Controller để xử lý Logout
 * 
 * 
 */
@WebServlet(urlPatterns = { "/logout.do" })
public class LogoutController extends HttpServlet {
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
		// Lấy đối tượng session
		HttpSession session = req.getSession(false);
		if (session != null) {
		    session.invalidate();
		}
		// chuyển về controller login
		resp.sendRedirect(Constant.URL_LOGIN);
	}
}
