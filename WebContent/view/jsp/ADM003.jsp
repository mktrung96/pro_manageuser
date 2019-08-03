
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="java.util.ArrayList"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="${pageContext.request.contextPath }/view/css/style.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript" src="view/js/user.js"></script>
<title>ユーザ管理</title>
</head>
<body>
	<!-- Begin vung header -->
	<%@ include file="Header.jsp"%>
	<!-- End vung header -->

	<!-- Begin vung input-->
	<form
		<c:if test="${!edit.equals(edit)}">
		action="AddUserInput.do?type=validateUser&edit=${edit}&userId=${userInfo.userID}"
		</c:if>
		<c:if test="${edit.equals(edit)}">
		action="EditUserInfo.do?edit=${edit}&key=${keys}&userID=${userInfo.userID}"
		</c:if>
		method="post" name="inputform">
		<table class="tbl_input" border="0" width="75%" cellpadding="0"
			cellspacing="0">
			<tr>
				<th align="left">
					<div style="padding-left: 100px;">会員情報編集</div>
				</th>
			</tr>


			<c:forEach items="${listError}" var="error">
				<tr>
					<td class="errMsg">
						<div style="padding-left: 120px">
							<font color="#FF0000">${error}</font>
						</div>
					</td>
				</tr>
			</c:forEach>
			<tr>
				<td class="errMsg">
					<div style="padding-left: 120px">&nbsp;</div>
				</td>
			</tr>
			<tr>
				<td align="left">
					<div style="padding-left: 100px;">
						<table border="0" width="100%" class="tbl_input" cellpadding="4"
							cellspacing="0">
							<tr>
								<td class="lbl_left" width="30%"><font color="red">*</font>
									アカウント名:</td>

								<td align="left"><input class="txBox" type="text"
									name="loginName" value="${fn:escapeXml(userInfo.loginName)}"
									size="15" onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';"
									${userInfo.userID > 0 ? 'readonly' : ''} /></td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font> グループ:</td>
								<td align="left"><select name="groupId">
										<option value="0" id="0">選択してください</option>
										<c:forEach items="${listGroup}" var="list">
											<option
												${list.groupID == userInfo.groupId ? 'selected = "selected"' : ''}
												value="${list.groupID}">${list.groupName}</option>
										</c:forEach>
								</select> <span>&nbsp;&nbsp;&nbsp;</span></td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font> 氏名:</td>
								<td align="left"><input class="txBox" type="text"
									name="fullName" value="${fn:escapeXml(userInfo.fullName)}"
									size="30" onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
							<tr>
								<td class="lbl_left">カタカナ氏名:</td>
								<td align="left"><input class="txBox" type="text"
									name="kanaName" value="${fn:escapeXml(userInfo.kanaName)}"
									size="30" onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font> 生年月日:</td>
								<td align="left"><select name="yearBirthDay">
										<c:forEach items="${listYear}" var="year">
											<option
												${ year == userInfo.arrBirthDay[0]  ? 'selected = "selected"' : ''}
												value="${year}">${year}</option>
										</c:forEach>
								</select>年 <select name="monthBirthDay">
										<c:forEach items="${listMonth}" var="month">
											<option
												${month == userInfo.arrBirthDay[1] ? 'selected = "selected"' : ''}
												value="${month}">${month}</option>
										</c:forEach>
								</select>月 <select name="dayBirthDay">
										<c:forEach items="${listDay}" var="day">
											<option
												${day == userInfo.arrBirthDay[2] ? 'selected = "selected"' : ''}
												value="${day}">${day}</option>
										</c:forEach>
								</select>日</td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font> メールアドレス:</td>
								<td align="left"><input class="txBox" type="text"
									name="email" value="${fn:escapeXml(userInfo.email)}" size="30"
									onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font>電話番号:</td>
								<td align="left"><input class="txBox" type="text"
									name="tel" value="${fn:escapeXml(userInfo.tel)}" size="30"
									onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
							<c:if test="${!edit.equals(edit)}">
								<tr>
									<td class="lbl_left"><font color="red">*</font> パスワード:</td>
									<td align="left"><input class="txBox" type="password"
										name="passWord" value="${fn:escapeXml(userInfo.passWord)}"
										size="30" onfocus="this.style.borderColor='#0066ff';"
										onblur="this.style.borderColor='#aaaaaa';" /></td>
								</tr>

								<tr>
									<td class="lbl_left">パスワード（確認）:</td>
									<td align="left"><input class="txBox" type="password"
										name="confirmPassWord"
										value="${fn:escapeXml(userInfo.confirmPassWord)}" size="30"
										onfocus="this.style.borderColor='#0066ff';"
										onblur="this.style.borderColor='#aaaaaa';" /></td>
								</tr>
							</c:if>

							<tr>
								<th align="left" colspan="2"><a href="#"
									onclick="clickLinkLevelJapanese()">日本語能力</a></th>
							</tr>
						</table>

						<table class="tbl_input" border="0" width="100%" cellpadding="4"
							cellspacing="0" style="display: ${display}" id="tableLvelJapan">

							<tr>
								<td class="lbl_left" width="30%">資格:</td>
								<td align="left"><select name="codeLevel">
										<option value="">選択してください</option>
										<c:forEach items="${listJapan}" var="listJapan">
											<option
												${listJapan.codeLevel == userInfo.codeLevel ? 'selected = "selected"' : ''}
												value="${listJapan.codeLevel}">${listJapan.nameLevel}</option>
										</c:forEach>
								</select></td>
							</tr>
							<tr>
								<td class="lbl_left">資格交付日:</td>
								<td align="left"><select name="yearStartDate">
										<c:forEach items="${listYear}" var="year">
											<option
												${ year == userInfo.arrStartDate[0]  ? 'selected = "selected"' : ''}
												value="${year}">${year}</option>
										</c:forEach>
								</select> 年 <select name="monthStartDate">
										<c:forEach items="${listMonth}" var="month">
											<option
												${month == userInfo.arrStartDate[1] ? 'selected = "selected"' : ''}
												value="${month}">${month}</option>
										</c:forEach>
								</select>月 <select name="dayStartDate">
										<c:forEach items="${listDay}" var="day">
											<option
												${day == userInfo.arrStartDate[2] ? 'selected = "selected"' : ''}
												value="${day}">${day}</option>
										</c:forEach>
								</select>日</td>
							</tr>
							<tr>
								<td class="lbl_left">失効日:</td>
								<td align="left"><select name="yearEndDate">
										<c:forEach items="${listYear}" var="year">
											<option
												${ year == userInfo.arrEndDate[0]  ? 'selected = "selected"' : ''}
												value="${year}">${year}</option>
										</c:forEach>
								</select> 年 <select name="monthEndDate">
										<c:forEach items="${listMonth}" var="month">
											<option
												${month == userInfo.arrEndDate[1] ? 'selected = "selected"' : ''}
												value="${month}">${month}</option>
										</c:forEach>
								</select>月 <select name="dayEndDate">
										<c:forEach items="${listDay}" var="day">
											<option
												${day == userInfo.arrEndDate[2] ? 'selected = "selected"' : ''}
												value="${day}">${day}</option>
										</c:forEach>
								</select>日</td>
							</tr>
							<tr>
								<td class="lbl_left">点数:</td>
								<td align="left"><input class="txBox" type="text"
									name="total" value="${fn:escapeXml(userInfo.total)}" size="5"
									onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
		</table>
		<div style="padding-left: 100px;">&nbsp;</div>
		<!-- Begin vung button -->
		<div style="padding-left: 45px;">
			<table border="0" cellpadding="4" cellspacing="0" width="300px">
				<tr>
					<th width="200px" align="center">&nbsp;</th>
					<td><input class="btn" type="submit" value="確認" /></td>
					<td><input class="btn" type="button" value="戻る"
						onclick="javascript:window.location.href='listuser.do?type=back'" /></td>
				</tr>
			</table>
		</div>
		<!-- End vung button -->
	</form>
	<!-- End vung input -->

	<!-- Begin vung footer -->
	<%@ include file="Footer.jsp"%>
	<!-- End vung footer -->
</body>
</html>