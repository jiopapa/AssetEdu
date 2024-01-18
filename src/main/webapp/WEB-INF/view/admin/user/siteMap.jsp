<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="kfs" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="asset" uri="/WEB-INF/asset-tags/asset.tld"%>

<!DOCTYPE html>
<html>
<head>
<!-- =================================================== -->
<jsp:include page="../../common/meta_css.jsp" flush="false" />
<!-- =================================================== -->
<title><c:out value="${pageTitle}" default="User List" /></title>
</head>
<style>
.table tbody tr.highlight td {
	background-color: #EAF0F7;
}
</style>
<body>
	<!-- =================================================== -->
	<jsp:include page="../../common/header.jsp" flush="false" />
	<!-- =================================================== -->
	<main class="container mx-3 my-3">

		<h2>
			<i class="fa-solid fa-cube my-3"></i> 메뉴 구성
		</h2>
		<div class="container-lg p-3 border border-2 rounded-1" >
		<div class="row">
		<div class="col-2 "  ><h2>기본정보</h2>
			<ul>
          <li>펀드정보</li>
          <li>주식종목정보</li>
          <li>기관정보</li>
          <li>공통코드</li>
        </ul>
		</div>
		<div class="col-2"><h2>운용지시</h2>
			<ul>
          <li>주식매수</li>
          <li>주식매도</li>
        </ul>
		</div>
		<div class="col-2"><h2>평가처리</h2>
			<ul>
          <li>주식보유원장</li>
          <li>시세입력</li>
          <li>평가처리</li>
        </ul>
		</div>
		<div class="col-2"><h2>회계관리</h2>
			<ul>
          <li>계정과목</li>
          <li>대표계정코드</li>
          <li>분개매핑</li>
          <li>실계정매핑</li>
          <li>분개장</li>
        </ul>
		</div>
		<div class="col-2"><h2>시스템관리</h2>
			<ul>
          <li>사용자관리</li>
          <li>용어사전</li>
          <li>사이트맵</li>
        </ul>
		</div>
		</div>
		</div>
		

	</main>
	<!-- =================================================== -->
	<jsp:include page="../../common/footer.jsp" flush="false" />
	<!-- -================================================== -->
