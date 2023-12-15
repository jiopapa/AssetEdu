<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="kfs" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="asset"  uri="/WEB-INF/asset-tags/asset.tld"%>
    
<!DOCTYPE html>
<html>
<head>
<!-- =================================================== -->
<jsp:include page="../../common/meta_css.jsp" flush="false" />
<!-- =================================================== -->
<title><c:out value="${pageTitle}" default="사용자관리" /></title>
<style>
.table tbody tr.highlight td {
  background-color: #EAF0F7;
}
</style>
</head>
<body>
<!-- =================================================== -->
<jsp:include page="../../common/header.jsp" flush="false" />
<!-- =================================================== -->
<main class="container mx-3 my-3">
	<h2><i class="fa-solid fa-cube my-3"></i> 사용자관리</h2>
	<div class="container-lg p-3 border border-2 rounded-1">
		<input type="text" class="form-control w-50 d-inline align-middle" placeholder="검색어를 입력하세요" id="searchText" name="searchText" value="${param.searchText}">
		<a class="btn d-inline align-middle btn-primary btnRetrieve"><i class="fa-solid fa-search"></i> 조회</a>
        <a class="btn d-inline align-middle btn-success btnInsert" href="/admin/user/insert"><i class="fa-solid fa-pencil-alt"></i> 등록</a>
	</div>
	<table class="mt-3 table table-hover table-sm dictTable" style="font-size:small">
	  <thead class="table-light">
	    <tr class="text-center">
	      <th scope="col" style="width:50px">No</th>
          <th scope="col" style="width:150px">유저ID</th>
	      <th scope="col" style="width:250px">유저이름</th>
	       <th scope="col" style="width:250px">유저전화번호</th>
	        <th scope="col" style="width:250px">유저이메일</th>
	    </tr>
	  </thead>
	  <tbody class="table-group-divider" >
	  	<c:forEach var="list" items="${list}" varStatus="status">
		    <tr class="align-middle">
		      <td scope="row" class="text-center fw-bold">${status.count}</td>
              <td class="sys01UserId text-center">${list.sys01UserId}</td>
		      <td class="sys01UserNm">${list.sys01UserNm}</td>
		      <td class="sys01UserTel">${list.sys01UserTel}</td>
		      <td class="sys01UserEmail">${list.sys01UserEmail}</td>
              <td align="right">
                  <button class="btn btn-primary btn-sm btnModify" data-bs-target="#modalDict"><span><i class="fa-regular fa-pen-to-square"></i></span> 수정</button>
                  <button class="btn btn-danger btn-sm btnDelete" data-user-id="${list.sys01UserId }" ><span><i class="fa-regular fa-trash-can"></i></span> 삭제</button>
              </td>
		    </tr>
	    </c:forEach>
	  </tbody>
	</table>
	
</main>

<!-- 등록/수정 Modal -->
<div class="modal fade" id="modalDict" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable" role="document">
    <div class="modal-content">
      <form method="POST" id="formModalDict" class="validcheck">
        <input type="hidden" id="formMode"/>
        <div class="modal-header">
            <h5 class="modal-title"><b>용어등록</b></h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
            <div class="mb-3">
                <label for="sys01UserId" class="form-label fw-bold">유저ID</label>
                <input type="text" class="form-control" name="sys01UserId" readonly />
            </div>
            <div class="mb-3">
                <label for="sys02KorNm" class="form-label fw-bold" >용어명</label>
                <input type="text" class="form-control is-valid" name="sys02KorNm" />
            </div>
            <div class="mb-3">
                <label for="sys02Short" class="form-label fw-bold" >영문약명</label>
                <input type="text" class="form-control is-valid" name="sys02Short" placeholder="알파벳만" required pattern="[a-z|A-Z]+"/>
            </div>
            <div class="mb-3">
                <label for="sys02Full" class="form-label fw-bold" >영문Full명</label>
                <input type="text" class="form-control is-valid" name="sys02Full" placeholder="알파벳만" required pattern="[a-z|A-Z]+"/>
            </div>
            <div class="mb-3">
                <label for="sys02Note" class="form-label align-top fw-bold">비고</label>
                <textarea class="form-control" name="sys02Note" rows="3" cols="50"/></textarea>
            </div>
        </div>
        <div class="modal-footer d-flex justify-content-center">
            <button type="button" class="btn btn-primary" id="btnUpdate">저장</button>
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
        </div>
      </form>
    </div>
  </div>
</div>

<!-- =================================================== -->
<jsp:include page="../../common/footer.jsp" flush="false" />
<!-- -================================================== -->
<script type="text/javascript" src="/js/input-format.js"></script>


</body>
</html>