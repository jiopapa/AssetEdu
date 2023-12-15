<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<main class="container-flud mx-3 my-3">
<h2>test2</h2>
<h3>client로부터 받은 데이터</h3>
<p> name : ${student1.name }</p>
<p> age : ${student1.age }</p>
<p> asset : ${student1.asset }</p>
<p> birth : <fmt:formatDate pattern="yyyy-MM-dd" value= "${student1.birth }" /></p>
			
</main>

</body>
</html>