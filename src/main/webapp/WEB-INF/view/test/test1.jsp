<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>test1</h1>
<form action="/test1" method="post"> <!-- method에  get, delete, patch, put 등을 기술가능-->
	<p>이름 : <input type="text" name="name"/></p>
	<p>나이: <input type="text" name="age" /></p>
	<p>재산 : <input type="text" name="asset" /></p>
	<p>생년월일 : <input type="text" name="birth" /></p>
	<p><input type="submit" value="저장" /></p>
</form>
</body>
</html>