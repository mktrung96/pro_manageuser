function clickLinkLevelJapanese() {
	var str = document.getElementById('tableLvelJapan').style;
	if (str.display == 'block') {
		str.display = 'none';
	} else if (str.display == 'none') {
		str.display = 'block';
	}
}
function alertDelete(userID) {
	var check = window.confirm("削除しますが、よろしいでしょうか。");
	if (check) {
		window.location = "DeleteUser.do?userID=" + userID;
	}
}