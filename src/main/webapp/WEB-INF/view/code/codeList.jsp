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
<jsp:include page="../common/meta_css.jsp" flush="false" />
<!-- =================================================== -->
<title><c:out value="${pageTitle }" default="공통코드관리" /></title>
</head>
<body>
	<!-- =================================================== -->
	<jsp:include page="../common/header.jsp" flush="false" />
	<!-- =================================================== -->
	<main class="container mx-3 my-3">

		<h2>
			<i class="fa-solid fa-cube my-3"></i> 공통코드관리
		</h2>
		<div class="container-lg p-3 border border-2 rounded-1">
			<input type="text" class="form-control w-25 d-inline align-middle"
				placeholder="검색어를 입력하세요" id="searchText" name="searchText"
				value="${param.searchText}"> <a
				class="btn d-inline align-middle btn-primary btnRetrieve"><i
				class="fa-solid fa-search"></i> 조회</a> <a
				class="btn d-inline align-middle btn-secondary btnInit"><i
				class="fa-solid fa-backspace"></i> 초기화</a>
		</div>

		<div class="row">
			<div class="col-6">
				<!-- 공통코드 메뉴 -->
				<table class="table table-sm table-hover comCdTable"
					style="font-size: small">
					<thead class="table-light">
						<tr class="text-center align-middle">
							<th scope="col" style="width: 30px">No</th>
							<th scope="col">공통코드</th>
							<th scope="col">공통코드명</th>
							<th scope="col">순번</th>
							<th scope="col">사용여부</th>
							<th scope="col">비고</th>
							<th scope="col">
								<button class="btn btn-success btn-sm btnCategoryInsert"
									data-bs-target="#newComCd">
									<span><i class="fa-regular fa-pen-to-square"></i> 추가</span>
								</button>
							</th>
						</tr>
					</thead>
					<tbody class="table-group-divider">
						<c:forEach var="code" items="${CodeList }" varStatus="status">
							<tr class="align-middle">
								<td scope="row" class="text-center fw-bold">${status.count}</td>
								<td class="com02Comcd clickableRow">${code.com02ComCd}</td>
								<td class="clickableRow com02CodeNm">${code.com02CodeNm}</td>
								<td class="text-center com02Seq">${code.com02Seq}</td>
								<td class="text-center com02UseYn">${code.com02UseYn}</td>
								<td class="com02Note">${code.com02Note}</td>
								 <td>		      	
			       <input type="hidden" name="com02UseYn" value="${code.com02UseYn }"/>
	               <button class="btn btn-primary btn-sm btnCategoryModify"   data-bs-target="#newComCd"><span><i class="fa-regular fa-pen-to-square"></i></span></button>
			       <button class="btn btn-danger btn-sm btnCategoryDelete" data-comcd="${code.com02ComCd}" data-comnm="${code.com02CodeNm}"><span><i class="fa-regular fa-trash-can"></i></span></button>
			      </td>
									</tr>
						</c:forEach>
					</tbody>
					</div>
					<div class="col-6" id="detail-code-area">
	 				<!-- 상세코드리스트 -->           
 					</div> 		
					</div>
					<main class="container-flud"></main>
					<!-- =================================================== -->
					<jsp:include page="../common/footer.jsp" flush="false" />
					<!-- -================================================== -->
					<script>
						$(document).ready(function() {
							console.log('ready...');
						});
					</script>
</body>
</html>