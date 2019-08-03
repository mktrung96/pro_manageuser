<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="view/css/style.css" rel="stylesheet" type="text/css" />
<title>ユーザ管理</title>
</head>
<body>
	<!-- Begin vung header -->
	<jsp:include page="Header.jsp" />
	<!-- End vung header -->

	<!-- Begin vung dieu kien tim kiem -->
	<form action="listuser.do" method="get" name="mainform">
		<input class="display_hidden" name="type" value="search" />
		<table class="tbl_input" border="0" width="90%" cellpadding="0"
			cellspacing="0">
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>会員名称で会員を検索します。検索条件無しの場合は全て表示されます。</td>
			</tr>
			<tr>
				<td width="100%">
					<table class="tbl_input" cellpadding="4" cellspacing="0">
						<tr>
							<td class="lbl_left">氏名:</td>
							<td align="left"><input class="txBox" type="text"
								name="fullNameText"
								value="<c:out value="${textboxSearch}" escapeXml="true"/>"
								size="20" onfocus="this.style.borderColor='#0066ff';"
								onblur="this.style.borderColor='#aaaaaa';" /></td>
						</tr>
						<tr>
							<td class="lbl_left">グループ:</td>
							<td align="left" width="80px"><select name="groupID">
									<c:forEach var="mstGroup" items="${listMstGroup}">
										<option
											value="<c:out value="${mstGroup.groupID}" escapeXml="true"/>"
											<c:if test="${typeCheckGroupID eq mstGroup.groupID}">selected</c:if>>
											<c:out value="${mstGroup.groupName}" escapeXml="true" />
										</option>
									</c:forEach>
							</select></td>

							<td align="left"><input class="btn" type="submit" value="検索" />
								<input class="btn" type="button" value="新規追加"
								onclick="javascript:window.location.href='AddUserInput.do?type=ADM003'" /></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<!-- End vung dieu kien tim kiem -->
	</form>
	<!-- Begin vung hien thi danh sach user -->
	<c:choose>
		<c:when test="${totalUser > 0}">
			<table class="tbl_list" border="1" cellpadding="4" cellspacing="0"
				width="80%">

				<tr class="tr2">
					<th align="center" width="20px">ID</th>
					<th align="left">氏名 <a
						href="listuser.do?type=sort&sortType=sortFullName&sortValue=${sortByFullName}">
							<c:if test="${sortByFullName.equals('DESC')}">▲▽</c:if> <c:if
								test="${sortByFullName.equals('ASC')}">△▼</c:if>
					</a>
					</th>
					<th align="left">生年月日</th>
					<th align="left">グループ</th>
					<th align="left">メールアドレス</th>
					<th align="left" width="70px">電話番号</th>
					<th align="left">日本語能力 <a
						href="listuser.do?type=sort&sortType=sortCodeLevel&sortValue=${sortByCodeLevel}">
							<c:if test="${sortByCodeLevel.equals('DESC')}">▲▽</c:if> <c:if
								test="${sortByCodeLevel.equals('ASC')}">△▼</c:if>
					</a>
					</th>
					<th align="left">失効日 <a
						href="listuser.do?type=sort&sortType=sortEndDate&sortValue=${sortByEndDate}">
							<c:if test="${sortByEndDate.equals('DESC')}">▲▽</c:if> <c:if
								test="${sortByEndDate.equals('ASC')}">△▼</c:if>
					</a>
					</th>
					<th align="left">点数</th>
				</tr>
				<c:forEach var="userinfo" items="${listUserInfo}">
					<tr>
						<td align="right"><a
							href="DetailUserInfo.do?userID=${userinfo.userID}"><c:out
									value="${userinfo.userID}" escapeXml="true" /></a></td>
						<td><c:out value="${userinfo.fullName}" escapeXml="true" /></td>
						<td align="center"><c:out value="${userinfo.birthday}"
								escapeXml="true" /></td>
						<td><c:out value="${userinfo.groupName}" escapeXml="true" /></td>
						<td><c:out value="${userinfo.email}" escapeXml="true" /></td>
						<td><c:out value="${userinfo.tel}" escapeXml="true" /></td>
						<td><c:out value="${userinfo.nameLevel}" escapeXml="true" /></td>
						<td align="center"><c:out value="${userinfo.endDate}"
								escapeXml="true" /></td>
						<td align="right"><c:out value="${userinfo.total}"
								escapeXml="true" /></td>
					</tr>
				</c:forEach>
			</table>
			<!-- End vung hien thi danh sach user -->

			<!-- Begin vung paging -->
			<c:if test="${totalPage > 1 }">
				<table>
					<tr>
						<td class="lbl_paging"><c:if test="${pageFirstBack >= 0}">
								<a href="listuser.do?type=paging&currentPage=${pageFirstBack }">
									&lt;&lt;</a> 
						</c:if> <c:forEach items="${listPaging}" var="page">
								<a href="listuser.do?type=paging&currentPage=${page}">${page}</a>
							</c:forEach> <c:if test="${pageFirstNext <= totalPage}">
								<a href="listuser.do?type=paging&currentPage=${pageFirstNext }">&gt;&gt;</a>
							</c:if></td>
					</tr>
				</table>
			</c:if>
		</c:when>
		<c:otherwise>
			<br />
			<c:out value="${userNotFound}"></c:out>
		</c:otherwise>
	</c:choose>
	<!-- End vung paging -->

	<!-- Begin vung footer -->
	<jsp:include page="Footer.jsp" />
	<!-- End vung footer -->

</body>

</html>