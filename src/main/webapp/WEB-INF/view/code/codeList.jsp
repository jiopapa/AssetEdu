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
<jsp:include page="../common/meta_css.jsp" flush="false" />
<!-- =================================================== -->
<title><c:out value="${pageTitle}" default="공통코드관리" /></title>
</head>
<style>
.table tbody tr.highlight td {
  background-color: #EAF0F7;
}
</style>
<body>
<!-- =================================================== -->
<jsp:include page="../common/header.jsp" flush="false" />
<!-- =================================================== -->
<main class="container mx-3 my-3">

	<h2><i class="fa-solid fa-cube my-3"></i> 공통코드관리</h2>
 	<div class="search-area">
 		<!-- 검색부분 조회,초기화 -->
		<input type="text" class="form-control w-25 d-inline align-middle" placeholder="검색어를 입력하세요" id="searchText" name="searchText" value="${param.searchText}">
		<a class="btn d-inline align-middle btn-primary btnRetrieve"><i class="fa-solid fa-search"></i> 조회</a>
		<a class="btn d-inline align-middle btn-secondary btnInit"><i class="fa-solid fa-backspace"></i> 초기화</a> 		
 	</div>
 	<div class="row">
 		<div class="col-6">
 			<!-- 공통코드리스트 -->
		    <table class="table table-sm table-hover comCdTable" style="font-size:small">
			  <thead class="table-light">
			    <tr class="text-center align-middle">
			      <th scope="col" style="width:30px">No</th>
			      <th scope="col">공통코드</th>
			      <th scope="col">공통코드명</th>
			      <th scope="col" style="width:40px">순서</th>
			      <th scope="col" style="width:40px">사용</th>
			      <th scope="col">비고</th>
			      <th scope="col" style="width:75px">
			          <button class="btn btn-success btn-sm btnCategoryInsert"  data-bs-target="#newComCd"><span><i class="fa-regular fa-pen-to-square"></i> 추가</span></button>
		          </th>
			    </tr>
			  </thead>
			  <tbody class="table-group-divider" >
			  	<c:forEach var="code" items="${CodeList}" varStatus="status">    
			    <tr class="align-middle" >
			      <td scope="row" class="text-center fw-bold">${status.count }</td>
			      <td class="clickableRow com02ComCd">${code.com02ComCd}</td>
			      <td class="clickableRow com02CodeNm">${code.com02CodeNm}</td>
			      <td class="text-center com02Seq">${code.com02Seq}</td>
			      <td class="text-center">${code.com02UseYn}</td>
			      <td class="com02Note">${code.com02Note}</td>
			      <td>		      	
			       <input type="hidden" name="com02UseYn" value="${code.com02UseYn }"/>
	               <button class="btn btn-primary btn-sm btnCategoryModify"   data-bs-target="#newComCd"><span><i class="fa-regular fa-pen-to-square"></i></span></button>
			       <button class="btn btn-danger btn-sm btnCategoryDelete" data-comcd="${code.com02ComCd}" data-comnm="${code.com02CodeNm}"><span><i class="fa-regular fa-trash-can"></i></span></button>
			      </td>
			    </tr>
				</c:forEach>
			  </tbody>
			</table> 			 
 		</div>
 		<div class="col-6" id="detail-code-area">
	 			<!-- 상세코드리스트 -->           
 		</div> 		
 	</div>
</main>
<!-- =================================================== -->
<jsp:include page="../common/footer.jsp" flush="false" />
<!-- -================================================== -->
<script>
$(document).ready(function () {
	console.log('ready...');
 	//-----------------------------------------------------부가적인 기능코드
  //화면뜨면 검색창에 포커스 
    $("#searchText").focus();

    //검색창에서 enter이면 조회
    $("#searchText").on("keyup",function(key){
        if(key.keyCode==13) { //enter
            $('.btnRetrieve').trigger('click');
        }else if(key.keyCode == 46){ //DEL
            $(this).val('');
        }
    }); 
	   
    let validateChecker = $('form.validcheck').jbvalidator({
        language: '/js/validation/lang/ko.json'
    });
	
	Handlebars.registerHelper("inc", function(value, options){
			    return parseInt(value) + 1;
	});
  //테이블 클릭시 하이라이트 표시
	$('.comCdTable').on('click', 'tbody tr', function(e) {
		  e.stopPropagation();
		  $(this).addClass('highlight').siblings().removeClass('highlight');
	});	
	$('#detail-code-area').on('click', '.dtlCdTable tbody tr', function(e){
		    e.stopPropagation();
        $(this).addClass('highlight').siblings().removeClass('highlight');
    });
});
});
</script>
<script>
$(document).ready(function () {
	console.log('ready...');
	//-----------------------------------------------------
  //handlebar 헬퍼함수
	Handlebars.registerHelper("inc", function(value, options){
			    return parseInt(value) + 1;
	});
	//화면뜨면 검색창에 포커스 
    $("#searchText").focus();

    //검색창에서 enter이면 조회
    $("#searchText").on("keyup",function(key){
        if(key.keyCode==13) { //enter
            $('.btnRetrieve').trigger('click');
        }else if(key.keyCode == 46){ //DEL
            $(this).val('');
        }
    }); 
	//테이블 클릭시 하이라이트 표시
	$('.comCdTable').on('click', 'tbody tr', function(e) {
		  e.stopPropagation();
		  $(this).addClass('highlight').siblings().removeClass('highlight');
	});	
	$('#detail-code-area').on('click', '.dtlCdTable tbody tr', function(e){
		e.stopPropagation();
        $(this).addClass('highlight').siblings().removeClass('highlight');
    });	
	//---------------공통코드 부분 Events
	//조회버튼 
 	$('.btnRetrieve').on('click', function(){
 	   //1. 검색어가 비어 있으면 alert로 검색어가 비어있다고 경고를 띄우고 리턴(아무동작을 하지 않는다)
 	   //2. 서버에서 검색어로 조회 (서버호출)
 	   alert('조회');
 	});
	//초기화버튼
	$('.btnInit').on('click', function(){
		//1. 검색어를  빈문자열로 만들고 조회 (서버호출) 
		alert('초기화');
	});
	//공통코드 추가버튼
	$('.btnCategoryInsert').on('click', function(){
		//1.공통코드추가 팝업을 띄운다. (참조문서: 모달 띄우는 방법)
		var $modal = $('#newComCd');
		$modal.modal('show');
	});
	// 공통코드 리스트의 수정버튼 
	$('.btnCategoryModify').on('click', function(){
		//1. 선택된 공통코드 수정 팝업을 띄운다. (참조문서 : 모달 띄우는 방법)
		//2. 현재 값들로 띄운 모달 안의 입력창에 값을 채운다.
		alert('공통코드 수정');
	});
	// 공통코드 리스트의  삭제버튼
	$('.btnCategoryDelete').on('click', function(){
		//1.삭제하시겠는가? confirm 메세지 
		//2.yes이면 선택된 공통코드 삭제 (서버호출) -> 서버에서는 리스트로 redirect
		alert('공통코드 삭제');
	});
	// 공통코드의 cd,nm (clickAble class)가 클릭시 
	$('.clickableRow').on('click', function(){
		//1. 현재 클릭된 comCd 값을 구한다.
		//2. ajax로 서버 호출 이때 comCd값을 보낸다.
		//3. ajax success에서 서버에서 보내온 detail code list와 handlebar template를 이용해서
		//   html을 만든다음에 오른쪽에 div에 넣는다.
		alert('comCd가 클릭됨 ')
	});
	// 공통코드 추가 수정 모달의 저장버튼 
	$('#btnInsertOrComCd').on('click', function(){
		//1. 모달의 내용을 가져온다.
		//2. 수정이라면 update쪽 path를 추가라면 insert쪽 path를 호출
		//3. Ajax로 insert / update 호출 성공시 다시 list호출 
		//(참조소스)
	});
	//------오른쪽 즉 DtlCd부분 쪽 event임
	// 각자구현
	
});

</script>
<script>
//예를 들어 공통코드 테이블의 row가 클릭시
$('.clickableRow').on('click', function(){
    //클릭된 tr을 찾음
 		var code = $(this).closest('tr').find('.com02ComCd').text();
 		AssetUtil.ajax('★★★[수정 서버단 받는 url]★★★', { comCd: code, codeType : 'D'},{ success : makeTable}) 		
    //★★★ AssetUti.ajax 코드를 확인하십시오. default가 GET인지 POST인지 확인
 	});

//공통코드가 선택시 상세코드리스트를 테이블로 만들어서 보여준다.
 	function makeTable(response){
 		console.log(response);
    //1 template 를 가져온다.
 		var rawTemplate = $('#detail-table-template').html();
    //2. template 를 Handlebars.compile 해서 함수를 얻는다.
 		var compiledTemplate =  Handlebars.compile(rawTemplate);
 		//3. 얻은 함수에 data를 인자로 넣고 수행 html을 얻는다.	
 		var html = compiledTemplate(response);
    //4. 얻은 html을 오른쪽 div에 넣는다
 		$('#detail-code-area').html(html);
 	}

</script>
<div class="modal fade" id="newComCd" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable" role="document">
	<div class="modal-content">
		<form  method="POST" id="formNewCode" class="validcheck" >
          <!-- hidden변수 formNewCode_Mode 로 추가/수정을 구분하려고 함 -->
			    <input type="hidden" id="formNewCode_Mode"/>
	        <input type="hidden" name="com02DtlCd" value="NONE" />
	        <input type="hidden" name="com02CodeType" value="C" />
	
	        <div class="modal-header text-center"> 
				<h5 class="modal-title fw-bold">공통코드 추가</h5>
		        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			
			<div class="modal-body">
	          <div class="mb-3">
	            <label for="com02ComCd" class="form-label fw-bold">공통코드</label>
	            <input class="form-control is-valid" name="com02ComCd" placeholder="코드(알파벳만)" required pattern="[a-z|A-Z]+" data-v-min-length="3" data-v-max-length="100" />
	          </div>	
	          <div class="mb-3">
	            <label for="com02ComNm" class="form-label fw-bold" >공통코드명</label>
	            <input type="text" class="form-control is-valid" name="com02CodeNm" placeholder="공통코드명" required />
	          </div>
	          <div class="mb-3">
	            <label for="com02Seq" class="form-label fw-bold">순서</label>
	            <input type="text" class="form-control" name="com02Seq"  required pattern="[0-9]+"  />
	          </div>
	          <div class="mb-3 align-middle">
	            <label for="com02UseYn" class="form-label fw-bold">사용여부</label>&nbsp;
	            <input type="radio" name="com02UseYn" id="com02UseYn11" value="true" checked/> <label for="com02UseYn11"> 사용함</label>&nbsp;
				<input type="radio" name="com02UseYn" id="com02UseYn22" value="false"/> <label for="com02UseYn22"> 사용안함</label>
	          </div>
	          <div class="mb-3">
	            <label for="com02Note" class="form-label align-top fw-bold">코드설명</label>
	            <textarea class="form-control" name="com02Note" rows="3" cols="50"></textarea>
	          </div>
			</div>
			<div class="modal-footer d-flex justify-content-center">
	        	<button type="button" class="btn btn-primary" id="btnInsertComCd">저장</button>
	            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
			</div>
		</form>
	</div>
  </div>
</div>

</body>
</html>