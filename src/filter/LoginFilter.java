
package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ultis.Common;
import ultis.Constant;

/**
 * Class LoginFilter là class filter để check login của chương trình
 * 
 */
@WebFilter(urlPatterns = { "*.do" })
public class LoginFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		try {
			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse) resp;
			HttpSession session = request.getSession(false);
			// lấy url của servlet login
			String servletPath = request.getServletPath();
			// url serlvet login
			String loginURI = "/" + Constant.URL_LOGIN;
			// kiểm tra login
			boolean loggedIn = Common.checkLogin(session);
			// kiểm tra url login có giống url servlet login không
			boolean loginRequest = loginURI.equals(servletPath);
			// Nếu đã login và đang trong url login thì chuyển hướng sang url servlet
			// listuser
			if (loggedIn && loginRequest) {
				response.sendRedirect(Constant.URL_LIST_USER);
				// Nếu login thành công hoặc là đường dẫn login thì cho đi tiếp
			} else if (loggedIn || loginRequest) {
				chain.doFilter(request, response);
				// Nếu chưa đăng nhập, url không trùng với tên url login thì chuyển hướng đến
				// màn hình đăng nhập
			} else {
				response.sendRedirect(Constant.URL_LOGIN);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// chuyển tới url báo lỗi hệ thống
			((HttpServletResponse) resp).sendRedirect(Constant.URL_SYSTEM_ERROR);
		}

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

}
