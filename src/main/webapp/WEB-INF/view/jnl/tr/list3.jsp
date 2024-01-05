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
<title><c:out value="${pageTitle}" default="거래코드관리" /></title>
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

	<h2><i class="fa-solid fa-cube my-3"></i> 거래코드관리</h2>
  
	<div class="container-lg p-3 mb-3 border border-2 rounded-1">
		<input type="text" class="form-control w-25 d-inline align-middle" placeholder="검색어를 입력하세요" id="searchText" name="searchText" value="${param.searchText}">
		<a class="btn d-inline align-middle btn-primary btnRetrieve"><i class="fa-solid fa-search"></i> 조회</a>
		<a class="btn d-inline align-middle btn-secondary btnInit"><i class="fa-solid fa-backspace"></i> 초기화</a>
	</div>

	<div class="row">
      <!-- jnl12 코드 리스트 -->
	  <div class="col-5 tr12 py-3">
	    <table class="table table-sm table-hover comCdTable" style="font-size:small">
		  <thead class="table-light">
		    <tr class="text-center align-middle">
			  <th scope="col" style="width:50px">No</th>
		      <th scope="col" style="width:70px">거래코드</th>
		      <th scope="col">거래명</th>
		      <th scope="col" style="width:100px">입출고여부</th>
		      <th scope="col" style="width:85px">사용여부</th>
		      <th scope="col" style="width:80px">
		          <button class="btn btn-success btn-sm btnCategoryInsert"  data-bs-target="#newComCd"><span><i class="fa-regular fa-pen-to-square"></i> 추가</span></button>
	          </th>
		    </tr>
		  </thead>
		  <tbody class="table-group-divider" >
		  	<c:forEach var="item" items="${jnl12List}" varStatus="status">    
		   <tr data-tr-cd="${item.jnl12TrCd }" data-tr-nm="${item.jnl12TrNm }" class="align-middle">

		      <td scope="col" class="text-center" >${status.count }</td>
		      <td scope="col" class="text-center jnl12TrCd clickableRow">${item.jnl12TrCd}</td>
		      <td scope="col" class="text-start jnl12TrNm clickableRow">${item.jnl12TrNm}</td>
		      <td scope="col" class="text-center com02Seq">${item.jnl12InOutTypeNm}</td>
		      <td scope="col" class="text-center">${item.jnl12UseYn}</td>
		      <td scope="col" class="text-center">		      	
		       <input type="hidden" name="com02UseYn" value="${item.jnl12UseYn }"/>
               <button class="btn btn-primary btn-sm btnCategoryModify"   data-bs-target="#newJnl12TrCd"><span><i class="fa-regular fa-pen-to-square"></i></span></button>
		       <button class="btn btn-danger btn-sm btnCategoryDelete" data-comcd="${item.jnl12TrCd}" data-comnm="${item.jnl12TrNm}"><span><i class="fa-regular fa-trash-can"></i></span></button>
		      </td>
		    </tr>
			</c:forEach>
		  </tbody>
		</table>
	  </div>
	  <div class="col" id="detail-code-area">
	  </div>
	</div>
</main>

<!-- Modal 거래코드 추가와 수정을 같이 사용한다-->
<div class="modal fade" id="newJnl12TrCd" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable" role="document">
	<div class="modal-content">
		<form  method="POST" id="formNewCode" class="validcheck" >
			<input type="hidden" id="formNewCode_Mode"/>

	
	        <div class="modal-header text-center"> 
				<h5 class="modal-title fw-bold">거래코드 추가</h5>
		        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			
			<div class="modal-body">
	          <div class="mb-3">
	            <label for="jnl12TrCd" class="form-label fw-bold">거래코드</label>
	            <input class="form-control is-valid" name="jnl12TrCd" placeholder="코드(숫자만)" required pattern="^[0-9]+$" data-v-min-length="4" maxlength="4" />
	          </div>	
	          <div class="mb-3">
	            <label for="jnl12TrNm" class="form-label fw-bold" >거래명</label>
	            <input type="text" class="form-control is-valid" name="jnl12TrNm" placeholder="거래명" required />
	          </div>
	          <div class="mb-3">
	            <label for="jnl12InOutTypeNm" class="form-label fw-bold">입출고구분</label>
	            <select class="form-select" name="jnl12InOutType"    >
	            <option value=""></option>
				<option value="1" >입고</option>
				<option value="2" >출고</option>
				<option value="3" >평가</option>
				</select>
	          </div>
	          <div class="mb-3 align-middle">
	            <label for="jnl12UseYn" class="form-label fw-bold">사용여부</label>&nbsp;
	            <input type="radio" name="jnl12UseYn" id="jnl12UseYn11" value="true" checked/> <label for="jnl12UseYn11"> 사용함</label>&nbsp;
				<input type="radio" name="jnl12UseYn" id="jnl12UseYn22" value="false"/> <label for="jnl12UseYn22"> 사용안함</label>
	          </div>
			</div>
			<div class="modal-footer d-flex justify-content-center">
	        	<button type="button" class="btn btn-primary" id="btnInsertJnl12TrCd">저장</button>
	            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
			</div>
		</form>
	</div>
  </div>
</div>

<!-- 상세코드추가화면 -->
<div class="modal fade" id="newJnl13TrCd"  data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable" role="document">
	<div class="modal-content">
		<form  method="POST" id="formNewJnl13" class="validcheck" >
			<input type="hidden" id="formNewJnl13_mode"/>
			<div class="modal-header ">
				<h5 class="modal-title"><b>[</b><span id="formNewJnl13TrCdNm"></span><b>분개매핑코드 추가</b></h5>
		        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body">
	          <div class="mb-3">
	            <label for="jnl13TrCd" class="form-label fw-bold">거래코드</label>
	            <input class="form-control is-valid" name="jnl13TrCd" required data-v-min-length="4" maxlength="4" />
	            <input type="hidden" name="jnl13TrCd"  />
	          </div>	
	          <div class="mb-3">
	            <label for="jnl13Seq" class="form-label fw-bold" >순번</label>
	            <input type="text" class="form-control" name="jnl13Seq" required  pattern="[0-9]+"/>
	          </div>
	          <div class="mb-3">
	            <label for="jnl13ReprAcntCd" class="form-label fw-bold">대표계정코드</label>
	            <input type="text" class="form-control" name="jnl13ReprAcntCd"/>
	          </div>
	           <div class="mb-3">
	            <label for="jnl13DrcrType" class="form-label align-top fw-bold">차대구분</label>
	          </div>
	          <div class="mb-3 align-middle">
	            <label for="jnl13Fomula" class="form-label fw-bold">계산식</label>&nbsp;
	           	<input type="text" class="form-control" name="jnl13Fomula"   />

	          </div>
	         
			</div>
			<div class="modal-footer d-flex justify-content-center">
	        	<button type="button" class="btn btn-primary" id="btnInsertJnl13TrCd">저장</button>
	            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
			</div>
		</form>
	</div>
  </div>
</div>

<script id="detail-table-template" type="text/x-handlebars-template">
		<input type="hidden" id="selectedJnl12TrCd" value="{{jnl12TrCd}}" />
		<input type="hidden" id="selectedJnl12TrNm" value="{{jnl12TrNm}}" />
	    <table class="table table-sm table-hover dtlCdTable" style="font-size:small">
		  <thead class="table-light">
		    <tr class="align-middle text-center">
		      <th scope="col" style="width:30px">No</th>
		      <th scope="col" style="width:80px">거래코드</th>
		      <th scope="col" style="width:40px">순번</th>
		      <th scope="col" style="width:80px">대표계정코드</th>
		      <th scope="col" style="width:40px">차대구분</th>
		      <th scope="col" >계산식</th>
                <button class="btn btn-success btn-sm btnDetailAdd" data-bs-target="#newDtlCd"><span><i class="fa-regular fa-pen-to-square"></i> 추가</span></button>
		    </tr>
		  </thead>
		  <tbody class="table-group-divider align-middle" >
		    <tr class="" >
		    {{#each list}}
		      <td scope="row" class="text-center fw-bold">{{inc @index}}</td>
			  <td class="text-center clickableRow  jnl13TrCd">{{jnl13TrCd}}</td>
		      <td class="text-center  jnl13Seq">{{jnl13Seq}}</td>
		      <td class="clickableRow jnl13ReprAcntCd">{{jnl13ReprAcntCd}}</td>
		      <td class="text-center jnl13DrcrType">{{jnl13DrcrType}}</td>
		      <td class="text-center jnl13Fomula">{{jnl13Fomula}}</td>
		      <td>
               <button class="btn btn-primary btn-sm btnDetailModify" data-com-cd="{{jnl12TrCd}}"><span><i class="fa-regular fa-pen-to-square"></i></span></button>
               <button class="btn btn-danger btn-sm btnDetailDelete" data-dtl-cd="{{jnl13TrCd}}" ><span><i class="fa-regular fa-trash-can"></i></span></button>
		      </td>
		    </tr>
		    {{/each}}
		  </tbody>
		</table>	
</script>

<!-- =================================================== -->
<jsp:include page="../../common/footer.jsp" flush="false" />
<!-- -================================================== -->
<script>
$(document).ready(function () {
	console.log('ready...');
	
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
	
	//Jnl12 추가버튼 클릭 show modal
	$('.btnCategoryInsert').on('click', function(){
		console.log('거래 수정버튼 shown');
		var $modal = $('#newJnl12TrCd');
		$modal.find('input[name=com02ComCd]').attr('readonly', false)
		$modal.find('#formNewCode_Mode').val('insert');
		$modal.find('.modal-title').text('공통코드 추가');

		$modal.find('input[name=com02ComCd]').val('');
		$modal.find('input[name=com02CodeNm]').val('');
		$modal.find('input[name=com02Seq]').val('');	
		$modal.find('input[name=com02UseYn]').first().attr('checked', true);
		$modal.find('textarea[name=com02Note]').val('');
		//valid class remove
		$('#formNewCode').find('div.invalid-feedback').hide()
		$('#formNewCode').find('input.is-invalid').removeClass('is-invalid');
		
		$('#newComCd').modal('show');
	})
	
	//ComCd수정버튼 클릭 show form
	$('.btnCategoryModify').on('click', function(){
		console.log('공통코드 수정버튼 shown');
		var $modal = $('#newComCd');
		$modal.find('#formNewCode_Mode').val('update');
		$modal.find('.modal-title').text('공통코드 수정');
		$tr = $(this).closest('tr');
		
		var val = $tr.find('.com02ComCd').text();
		$modal.find('input[name=com02ComCd]').val(val).attr('readonly', true)
		
		val =$tr.find('.com02CodeNm').text(); 
		$modal.find('input[name=com02CodeNm]').val(val);
		
		val =$tr.find('.com02Seq').text(); 
		$modal.find('input[name=com02Seq]').val(val);
		
		var useYn = $tr.find('input[name=com02UseYn]').val();		
		$modal.find('input[name=com02UseYn][value=' + useYn + ']').prop('checked',true);
		
		val =$tr.find('.com02Note').text(); 
		$modal.find('textarea[name=com02Note]').val(val);
		
		//valid class remove
		$('#formNewCode').find('div.invalid-feedback').hide()
		$('#formNewCode').find('input.is-invalid').removeClass('is-invalid');
		
		$('#newComCd').modal('show');
	});
	
	//조회버튼
	$('.btnRetrieve').on('click', function(){
		var searchText = $('#searchText').val();
// 		if(searchText.length < 1) return;
		AssetUtil.submitGet('/code/list', {searchText: searchText});
	});
	//초기화
	$('.btnInit').on('click', function(){
		AssetUtil.submitGet('/code/list', {searchText: null});
	});
	//테이블 클릭시 하이라이트 표시
	$('.comCdTable').on('click', 'tbody tr', function(event) {
		  $(this).addClass('highlight').siblings().removeClass('highlight');
	});
	
	$('#detail-code-area').on('click', '.dtlCdTable tbody tr', function(e){
        $(this).addClass('highlight').siblings().removeClass('highlight');
    });

    //공통코드 선택
 	$('.clickableRow').on('click', function(){
 		var code = $(this).closest('tr').find('.com02ComCd').text();
 		AssetUtil.ajax('/code/find', { comCd: code, codeType : 'D'},{ success : makeTable})
 		
 	});
 	//공통코드 삭제
 	$('.btnCategoryDelete').on('click', function(e){
 		e.stopPropagation();
 		var cd = $(this).data('comcd');
 		var nm = $(this).data('comnm');
 		var msg = nm + "(" + cd + ")";
 		if (confirm(msg + ' 를 삭제하시겠습니까?')){
			AssetUtil.submitGet('/code/delete',{comCd:cd});
 		}
 	});	
 	//공통코드 추가 또는 수정 submit
 	$('#btnInsertComCd').on('click', function(){
		var $modal = $('#newComCd');
		var $form = $('#formNewCode');
		//valid check
		var validateChecker = $form.jbvalidator({
        	language: '/js/validation/lang/ko.json'
  	  	});
		var errorCount = validateChecker.checkAll($form);
		if(errorCount > 0)return;
		
		var mode = $modal.find('#formNewCode_Mode').val();
		var url = '/code/' + mode ; //mode = insert or update
		var json = AssetUtil.formToJson($('#formNewCode'));
 		json = JSON.stringify(json);
 		AssetUtil.ajax(url, json, {method:'POST', success:(response)=>{
 			if(response.result == 'OK'){
 				alert(response.msg);
 				AssetUtil.submitGet('/code/list');
 			}else{
 				alert("실패하였습니다 " + response.result);
 			}
 		}});
 	});

    //상세코드 추가 btnDetailAdd
    $('#detail-code-area').on('click', '.btnDetailAdd', function(e){
        e.stopPropagation();
        var comCd = $('#detail-code-area').find('#selectedComCd').val();
        var comNm = $('#detail-code-area').find('#selectedComNm').val();
        console.log('상세코드 추가', comCd, comNm);

        var $modal = $('#newDtlCd');
        $modal.find('#formNewDtl_mode').val('insert');
        $modal.find('.modal-title').html('<b>[</b>' + comCd + ':' + comNm + '<b>] 상세코드 추가</b>');
        $('#formNewDtl input[name=com02ComCd]').val(comCd);

        //각 필드 초기화
        var $tr = $(this).closest('tr');
        $modal.find('input[name=com02DtlCd]').val('').attr('readonly', false);
        $modal.find('input[name=com02CodeNm]').val('');
        $modal.find('input[name=com02Seq]').val('');
        $modal.find('input[name=com02UseYn]').first().prop('checked', true);
        $modal.find('textarea[name=com02Note]').val('');
        
		//valid class remove
		$('#formNewDtl').find('div.invalid-feedback').hide()
		$('#formNewDtl').find('input.is-invalid').removeClass('is-invalid');
        
        $modal.modal('show');
    });

 	//상세코드 수정 btnDetailModify
 	$('#detail-code-area').on('click', '.btnDetailModify', function(e){
        e.stopPropagation();
        var comCd = $('#detail-code-area').find('#selectedComCd').val();
        var comNm = $('#detail-code-area').find('#selectedComNm').val();
        console.log('상세코드 수정', comCd, comNm);

        var $modal = $('#newDtlCd');
		$modal.find('#formNewDtl_mode').val('update');
        $modal.find('.modal-title').html('<b>[</b>' + comCd + ':' + comNm + '<b>] 상세코드 수정</b>');
        $('#formNewDtl input[name=com02ComCd]').val(comCd);

        //각 필드 SET
		var $tr = $(this).closest('tr');

// 		$modal.find('input[name=com02ComCd]').val($(this).data('com-cd'));

		$modal.find('input[name=com02DtlCd]').val($tr.find('.com02DtlCd').text()).attr('readonly', true);
		$modal.find('input[name=com02CodeNm]').val($tr.find('.com02CodeNm').text());
		$modal.find('input[name=com02Seq]').val($tr.find('.com02Seq').text());
		
		var useYn = $tr.find('input[name=com02UseYn]').val();		
		$modal.find('input[name=com02UseYn][value=' + useYn + ']').prop('checked',true);

		$modal.find('textarea[name=com02Note]').val($tr.find('.com02Note').text());
		
		//valid class remove
		$('#formNewDtl').find('div.invalid-feedback').hide()
		$('#formNewDtl').find('input.is-invalid').removeClass('is-invalid');		
		$modal.modal('show');
 	});
 	
 	//상세코드 삭제
 	$('#detail-code-area').on('click', '.btnDetailDelete', function(e){
 		e.stopPropagation();
 		var comCd = $('#detail-code-area').find('#selectedComCd').val();
 		var comNm = $('#detail-code-area').find('#selectedComNm').val();
 		var dtlCd = $(this).data('dtl-cd');
 		var dtlNm = $(this).data('dtl-nm');
 		console.log('detail delete', comCd, comNm, dtlCd, dtlNm);
 		var msg = '공통코드'+comCd+'('+comNm+')의 상세코드'+dtlCd+'('+dtlNm+') 을[를] 삭제하시겠습니까?';
 		if(confirm(msg)){
 			AssetUtil.submitGet('/code/delete',{comCd:comCd, dtlCd:dtlCd});
 		}
 	});

 	//상세코드 추가 또는 수정 submit
 	$('#btnInsertDtlCd').on('click', function(){
		var $modal = $('#newDtlCd');
		var $form = $('#formNewDtl');
		//valid check
		var validateChecker = $form.jbvalidator({
        	language: '/js/validation/lang/ko.json'
  	  	});
		var errorCount = validateChecker.checkAll($form);
		if(errorCount > 0)return;
		
		var mode = $modal.find('#formNewDtl_mode').val(); 
		var url = '/code/' + mode;
 		var json = AssetUtil.formToJson($('#formNewDtl'));
 		var comCd = $('#formNewDtl input[name=com02ComCd]').val();
 		json = JSON.stringify(json);
 		console.log(json);
 		AssetUtil.ajax(url, json, 
 			{
 				method:'POST', 
 				success:(response)=>{
		 			if(response.result == 'OK'){
		 				alert(response.msg);
		 				var comCd = response.com02Code.com02ComCd;
		 				AssetUtil.submitGet('/code/list', {lastComCd : comCd});
		 			}else{
		 				
		 				alert("실패하였습니다 " + response.result);
		 			}
 				}
 			}
 	   );		
 		
 	});
 	
 	//공통코드가 선택시 상세코드리스트를 테이블로 만들어서 보여준다.
 	function makeTable(response){
 		console.log(response);
 		var rawTemplate = $('#detail-table-template').html();
 		var compiledTemplate =  Handlebars.compile(rawTemplate);
 			
 		var html = compiledTemplate(response);
 		$('#detail-code-area').html(html);
 	}
 	
 	//서버로부터받은 comCd로 하이라이트
	var lastComCd= '<%=request.getParameter("lastComCd")%>';
	
 	if(lastComCd && lastComCd != 'null'){
 		$(".comCdTable tr td").filter(function(){ return $(this).text() == lastComCd}).click(); 		
 	}
 	
});


</script>
</body>
</html>