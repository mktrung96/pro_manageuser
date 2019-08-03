

package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ultis.Constant;

/**
 * Class xử lý lỗi của chương trình
 * 
 * 
 */
@WebServlet(urlPatterns = { "/systemerror" })
public class SystemErrorController extends HttpServlet {

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
		// khởi tạo đối tượng RequestDispatcher
		RequestDispatcher request = req.getRequestDispatcher(Constant.JSP_SYSTEM_ERROR);
		// Chuyển sang JSP_SYSTEM_ERROR
		request.forward(req, resp);
	}
}
