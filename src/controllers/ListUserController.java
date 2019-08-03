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

import entities.MstGroup;
import entities.UserInfo;
import logics.MstGroupLogic;
import logics.TblUserLogic;
import logics.impl.MstGroupLogicImpl;
import logics.impl.TblUserLogicImpl;
import ultis.Common;
import ultis.ConfigProperties;
import ultis.Constant;
import ultis.MessageProperties;

@WebServlet(urlPatterns = { "/listuser.do" })
/**
 * Class Controller xử lý cho màn hình danh sách user ADM002
 * 
 * 
 */
public class ListUserController extends HttpServlet {

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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Lấy giá trị từ trên session
			HttpSession session = request.getSession();
			// Khởi tạo đối tượng MstGroupLogic và TblUserLogic
			MstGroupLogic mstGroupLogic = new MstGroupLogicImpl();
			TblUserLogic tblUserLogic = new TblUserLogicImpl();
			// lấy tất cả tên MstGroup
			List<MstGroup> listGroup = mstGroupLogic.getAllMstGroup();
			// Chuyển danh sách tên Group sang file jsp để hiển thị
			request.setAttribute("listMstGroup", listGroup);
			// Set giá trị tìm kiếm theo tên mặc định là rỗng
			String fullName = "";
			// Set giá trị măc định của khung tìm kiếm theo group
			int groupID = 0;
			// Set tiêu chí sắp xếp ưu tiên mặc định
			String sortType = "sortFullName";
			// Khai bao kiểu sắp xếp mặc định của từng cột
			String sortByFullName = "ASC";
			String sortByCodeLevel = "ASC";
			String sortByEndDate = "DESC";

			// Xác định số lượng user đc hiển thị trong 1 trang trong properties
			// nếu đọc file bị lỗi mặc định sẽ là 5
			int limit = Common.convertInterger(ConfigProperties.getValue(Constant.LIMIT_USER), 5);
			// Khai báo vị trí offset mặc định
			int offSet = Constant.DEFAULT_OFFSET;
			// Khai báo vị trí trang mặc định
			int currentPage = Constant.DEFAULT_CURRENT_PAGE;
			// Lấy kiểu chức năng là tìm kiếm hay sắp xếp hay paging
			String type = request.getParameter("type");

			// Các trường hợp
			// 1.chức năng tìm kiếm
			// 2.chức năng sort
			// 3.chức năng paging
			// 4.chức năng back

			// trường hợp chức năng tìm kiếm
			if (Constant.TYPE_SEARCH.equals(type)) {
				// lấy giá trị tìm kiếm từ request
				// Thực hiện chuẩn hóa giá trị fullName nếu giá trị lấy từ
				// request mà bằng null thì trả về giá trị mặc định
				fullName = (String) Common.getValueRequest(request, "fullNameText", Constant.DEFAULT_FULLNAME);
				// Thực hiện chuẩn hóa giá trị groupID nếu giá trị lấy từ
				// request mà bằng null thì trả về giá trị mặc định
				groupID = (int) Common.getValueRequest(request, "groupID", Constant.DEFAULT_GROUP_ID);
				// Đưa điều kiện tìm kiếm lên request
				session.setAttribute(Constant.SESSION_FULLNAME, fullName);
				session.setAttribute(Constant.SESSION_GROUP_ID, groupID);
				// đưa về trang đầu
				currentPage = Constant.DEFAULT_CURRENT_PAGE;
				// Trường hợp chức năng sort hoặc paging
			} else if (Constant.TYPE_SORT.equals(type) | Constant.TYPE_PAGING.equals(type)
					| Constant.TYPE_BACK.equals(type)) {
				// Lấy giá trị tìm kiếm trên session nếu không có giá trị để là mặc định
				fullName = (String) Common.getValueSession(session, Constant.SESSION_FULLNAME,
						Constant.DEFAULT_FULLNAME);
				groupID = (int) Common.getValueSession(session, Constant.SESSION_GROUP_ID, Constant.DEFAULT_GROUP_ID);
				// Lấy giá trị sắp xếp từ session
				sortByFullName = (String) Common.getValueSession(session, Constant.SESSION_SORT_FULLNAME,
						Constant.DEFAULT_SORT_FULLNAME);
				sortByCodeLevel = (String) Common.getValueSession(session, Constant.SESSION_SORT_CODE_LEVEL,
						Constant.DEFAULT_SORT_CODE_LEVEL);
				sortByEndDate = (String) Common.getValueSession(session, Constant.SESSION_SORT_END_DATE,
						Constant.DEFAULT_SORT_END_DATE);
				
				// Nếu là chức năng sort
				if (Constant.TYPE_SORT.equals(type)) {
					// Lấy điều kiện sắp xếp trên request
					sortType = request.getParameter("sortType");
					// giá trị sắp xêp là DESC hay ASC
					String sortValue = request.getParameter("sortValue");
					if (sortType != null) {
						// kiểm tra kiểu sắp xếp có đúng chuẩn không
						if (Constant.ASC.equals(sortValue) || Constant.DESC.equals(sortValue)) {
							// thay thế kiểu sắp xếp theo sortValue trên request
							switch (sortType) {
							case Constant.TYPE_SORT_FULLNAME:
								sortByFullName = sortValue;
								break;
							case Constant.TYPE_SORT_CODE_LEVEL:
								sortByCodeLevel = sortValue;
								break;
							case Constant.TYPE_SORT_END_DATE:
								sortByEndDate = sortValue;
								break;
							default:
								break;
							}
							// Chuyển điều kiện sắp xếp lên session
							session.setAttribute(Constant.SESSION_SORT_TYPE, sortType);
							session.setAttribute(Constant.SESSION_SORT_FULLNAME, sortByFullName);
							session.setAttribute(Constant.SESSION_SORT_CODE_LEVEL, sortByCodeLevel);
							session.setAttribute(Constant.SESSION_SORT_END_DATE, sortByEndDate);
							// đưa về trang đầu
							currentPage = Constant.DEFAULT_CURRENT_PAGE;
							// đưa giá trị sortValue lên request
							request.setAttribute("sortValue", sortValue);
							// set các giá trị tìm được lên request
							request.setAttribute("sortType", sortType);
						}
					}
					
				} 
				// trường hợp paging và back
				else {
					// Lấy các điều kiện sắp xếp lên session
					sortType = (String) Common.getValueSession(session, Constant.SESSION_SORT_TYPE,
							Constant.DEFAULT_SORT_TYPE);
					if (Constant.TYPE_PAGING.equals(type)) {
						// lấy trang hiện tại trên request
						currentPage = (int) Common.getValueRequest(request, "currentPage",
								Constant.DEFAULT_CURRENT_PAGE);
						// set trang hiện tại lên sesison
						session.setAttribute(Constant.SESSION_CURRENT_PAGE, currentPage);
						// Nếu là back
					} else {
						// Lấy trang hiện tại từ session nếu bằng null thì đặt là mặc định
						currentPage = (int) Common.getValueSession(session, Constant.SESSION_CURRENT_PAGE,
								Constant.DEFAULT_CURRENT_PAGE);
					}
					// Tìm vị trí offset để thực hiện việc lấy dữ liệu từ database
					offSet = limit * (currentPage - 1);
				}
			}
			// lấy tông số user theo kết quả tìm được
			int totalUser = tblUserLogic.getTotalUser(groupID, fullName);
			// Hiển thị lại yêu cầu lên box
			// Chuyển giá tri selectbox đã select
			request.setAttribute("typeCheckGroupID", groupID);
			// set lại giá trị tìm kiếm
			request.setAttribute("textboxSearch", fullName);
			// gửi tổng số user tìm kiếm được lên request
			request.setAttribute("totalUser", totalUser);
			// Nếu chương trình tìm kiếm được user thì sẽ
			// hiện danh sách và paging
			if (totalUser > 0) {
				// Lấy list user dựa theo điều kiện tìm kiếm
				List<UserInfo> listUserInfo = tblUserLogic.getListUser(offSet, limit, groupID, fullName, sortType,
						sortByFullName, sortByCodeLevel, sortByEndDate);
				// Tìm list Paging
				List<Integer> listPaging = Common.getListPaging(totalUser, limit, currentPage);
				// lấy giới hạn phân trang trong 1 trang
				int limitPaging = Common.getLimitPaging();
				// tìm trang đầu khi bấm back. Lấy giá trị đầu tiên trong listPaging
				int pageFirstBack = listPaging.get(0) - limitPaging;
				// tìm trang đầu khi bấm next
				int pageFirstNext = listPaging.get(0) + limitPaging;
				// Lấy tổng số page và chuyển sang jsp
				int totalPage = Common.getTotalPaging(totalUser, limit);
				// Đổi kiểu sort và chuyển qua cho jsp để hiển thị
				request.setAttribute("sortByFullName", Common.convertSortValue(sortByFullName));
				request.setAttribute("sortByCodeLevel", Common.convertSortValue(sortByCodeLevel));
				request.setAttribute("sortByEndDate", Common.convertSortValue(sortByEndDate));
				request.setAttribute("listUserInfo", listUserInfo);
				request.setAttribute("totalPage", totalPage);
				request.setAttribute("listPaging", listPaging);
				request.setAttribute("pageFirstNext", pageFirstNext);
				request.setAttribute("pageFirstBack", pageFirstBack);
				// Nếu không tìm thấy user nào thì sẽ hiện câu thông báo không tìm thấy
			} else {
				request.setAttribute("userNotFound", MessageProperties.getValue(Constant.MSG005));
			}
			// Khởi tạo đối tượng RequestDispatcher
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constant.JSP_ADM002);
			// Chuyển dữ liệu sang jsp để hiển thị
			requestDispatcher.forward(request, response);
		} catch (Exception e) {
			// Nếu bị lỗi thì chuyển sang màn hình lỗi
			e.printStackTrace();
			response.sendRedirect(Constant.URL_SYSTEM_ERROR);
		}
	}

}
