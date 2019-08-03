package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.UserInfo;
import logics.TblDetailUserJapanLogic;
import logics.TblUserLogic;
import logics.impl.TblDetailUserJapanLogicImpl;
import logics.impl.TblUserLogicImpl;
import ultis.Constant;
import validates.ValidateUser;

/**
 * Thực hiện chức năng xử lí dữ liệu khi ấn OK tại ADM004 trường hợp edit
 * 
 * @author phanthanhtrung
 *
 */
@WebServlet("/EditUserConfirm.do")
public class EditUserConfirmController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// setDataLogicADM003(request, response);
			UserInfo userInfo = new UserInfo();
			TblUserLogic tblUserLogicImpl = new TblUserLogicImpl();
			TblDetailUserJapanLogic tblDetailUserJapanLogicImpl = new TblDetailUserJapanLogicImpl();

			// lấy session từ request
			HttpSession session = request.getSession();
			// lấy key trên url
			String key = request.getParameter("key");
			// lấy giá trị key flag trên session để xác nhận trường hợp chuyển từ 03 sang 04
			String flag = (String) session.getAttribute(Constant.STR_KEY_FLAG);
			if (flag != null) {
				userInfo = (UserInfo) session.getAttribute(key);
				session.removeAttribute(key);
				session.removeAttribute(Constant.STR_KEY_FLAG);
			}
			// xử lý sau khi vào 1 trong 2 trường hợp EDIT hoặc ADD
			if ("".equals(userInfo.getCodeLevel())) {
				request.setAttribute("display", "none"); // chọn ẩn trình độ tiếng Nhật
			} else {
				request.setAttribute("display", "block"); // hiển thị trình độ tiếng Nhật
			}

			// kiểm tra sự tồn tại của userID
			boolean checkExistUser = tblUserLogicImpl.checkExistUserById(userInfo.getUserID());

			// Nếu có userInfo mang user_id tồn tại trong DB và validate thành công thì thực
			// hiện update dữ liệu
			if (checkExistUser) {
				ValidateUser validateUser = new ValidateUser();
				boolean validateEdit = validateUser.checkValidateUserInfoDB(userInfo);
				if (validateEdit) { // nếu không có lỗi
					// kiểm tra user đó lưu trong DB có trình độ tiếng Nhật hay không
					boolean checkJapanDB = tblDetailUserJapanLogicImpl.checkHaveLevelJapan(userInfo.getUserID());
					boolean checkUpdate = tblUserLogicImpl.updateUserInfo(userInfo, checkJapanDB);
					if (checkUpdate) {
						response.sendRedirect(Constant.SUCCESS + "?type=" + Constant.EDIT_SUCCESS);
					}
				}
			}
			// Ngược lại, có nghĩa userInfo bị xóa bằng tay trong DB
			else {
				response.sendRedirect(Constant.URL_SYSTEM_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(Constant.URL_SYSTEM_ERROR);
		}

	}

}
