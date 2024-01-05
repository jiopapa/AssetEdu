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
	  <div class="col-6 tr12">
	    <table class="table table-sm table-hover jnl12TrCdTable" style="font-size:small">
		  <thead class="table-light">
		    <tr class="text-center align-middle">
			  <th scope="col" style="width:50px">No</th>
		      <th scope="col" style="width:70px">거래코드</th>
		      <th scope="col">거래명</th>
		      <th scope="col" style="width:100px">입출고여부</th>
		      <th scope="col" style="width:95px">사용화면구분</th>
		      <th scope="col" style="width:80px">사용여부</th>
		      <th scope="col" style="width:80px">
		          <button class="btn btn-success btn-sm btnCategoryInsert"  data-bs-target="#newJnl12TrCd"><span><i class="fa-regular fa-pen-to-square"></i> 추가</span></button>
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
		      <td scope="col" class="text-center">${item.jnl12UsePageType}</td>
		      <td scope="col" class="text-center">${item.jnl12UseYn}</td>
		      
		      <td scope="col" class="text-center">		      	
               <button class="btn btn-primary btn-sm btnCategoryModify"   data-bs-target="#newJnl12TrCd"><span><i class="fa-regular fa-pen-to-square"></i></span></button>
		       <button class="btn btn-danger btn-sm btnCategoryDelete" data-cd="${item.jnl12TrCd}" data-nm="${item.jnl12TrNm}"><span><i class="fa-regular fa-trash-can"></i></span></button>
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
	            <select class="form-select" name="jnl12InOutType"    required>
	            <option value="" disabled></option>
				<option value="1" >입고</option>
				<option value="2" >출고</option>
				<option value="3" >평가</option>
				</select>
	          </div>
	            <div class="mb-3">
	            <label for="jnl12UsePageType" class="form-label fw-bold" >화면구분</label>
	            <select class="form-select" name="jnl12UsePageType"  required  >
	            <option value="" disabled></option>
				<option value="BUY">BUY</option>
				<option value="SELL">SELL</option>
				<option value="EVAL">EVAL</option>
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
<div class="modal fade" id="newJnl13Cd"  data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable" role="document">
	<div class="modal-content">
		<form  method="POST" id="formNewJnl13" class="validcheck" >
			<input type="hidden" id="formNewJnl13_mode"/>
			<div class="modal-header ">
				<h5 class="modal-title"><b>[</b><span id="formNewJnl13-comcd"></span><b>]분개매핑코드 추가</b></h5>
		        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body">
	          <div class="mb-3">
	            <input type="hidden" id="jnl13TrCd" name="jnl13TrCd"/>
	          </div>	
	          <div class="mb-3">
	            <label for="jnl13Seq" class="form-label fw-bold" >순번</label>
	            <input type="text" class="form-control" name="jnl13Seq" required  pattern="[0-9]+"/>
	          </div>
	          <div class="mb-3">
	            <label for="jnl13ReprAcntCd" class="form-label fw-bold">대표계정코드</label>
     			<button class="btn btn-outline-secondary btn-sm text-warning bg-secondary" type="button" id="jnl13ReprAcntCdPopup"><i class="fa-solid fa-search"></i></button>
	            <input type="hidden" id="jnl13ReprAcntCd" name="jnl13ReprAcntCd" value="" />
	            <input type="text" id="jnl13ReprAcntNm" name="jnl13ReprAcntNm" value=""  readonly="true" style="background-color:#F5F5F5"/>
	          </div>
	           <div class="mb-3">
	            <label for="jnl13DrcrType" class="form-label align-top fw-bold">차대구분</label>
	            <select name="jnl13DrcrType" class="form-select form-select-sm" required>
				<option value="" selected disabled></option>
				<option value="D" >차변</option>
				<option value="C" >대변</option>
    			</select>
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
		      <th scope="col" style="width:80px">차대구분</th>
		      <th scope="col" style="width:180px">계산식</th>
			  <th scope="col" style="width:75px">
              <button class="btn btn-success btn-sm btnDetailAdd" data-bs-target="#newJnl13Cd"><span><i class="fa-regular fa-pen-to-square"></i> 추가</span></button>
		    </tr>
		  </thead>
		  <tbody class="table-group-divider align-middle" >
		    <tr class="" >
		    {{#each list}}
		      <td scope="row" class="text-center fw-bold">{{inc @index}}</td>
			  <td class="text-center clickableRow  jnl13TrCd">{{jnl13TrCd}}</td>
		      <td class="text-center  jnl13Seq">{{jnl13Seq}}</td>
		      <td class="text-center clickableRow jnl13ReprAcntCd">{{jnl13ReprAcntCd}}</td>
		      <td class="text-center jnl13DrcrType">{{jnl13DrcrType}}</td>
		      <td class="text-center jnl13Formula">{{jnl13Formula}}</td>
		      <td>
               <button class="btn btn-primary btn-sm btnDetailModify" data-com-cd="{{jnl12TrCd}}"><span><i class="fa-regular fa-pen-to-square"></i></span></button>
               <button class="btn btn-danger btn-sm btnDetailDelete" data-dtl-cd="{{jnl13TrCd}}" ><span><i class="fa-regular fa-trash-can"></i></span></button>
		      </td>
		    </tr>
		    {{/each}}
		  </tbody>
		  </tbody>
		</table>	
</script>

<!-- =================================================== -->
<jsp:include page="../../common/footer.jsp" flush="false" />
<!-- -================================================== -->
<script>
$(document).ready(function () {
	console.log('ready...');
	
	$('#formNewJnl13').on('click','#jnl13ReprAcntCdPopup', function(){
	    var url = '/popup/jnl/repr-acnt?openerCdId=jnl13ReprAcntCd&openerNmId=jnl13ReprAcntNm';
	    var prop = {};
	    var width = 720;
	    var height = 518;
	    var win = AssetUtil.popupWindow(url, '대표계정코드', {}, width, height);
	    return false;
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
	
    let validateChecker = $('form.validcheck').jbvalidator({
        language: '/js/validation/lang/ko.json'
    });
	Handlebars.registerHelper("inc", function(value, options){
			    return parseInt(value) + 1;
	});
	//Jnl12 추가버튼 클릭 show modal
	$('.btnCategoryInsert').on('click', function(){
		console.log('거래 등록버튼 shown');
		var $modal = $('#newJnl12TrCd');
		$modal.find('input[name=jnl12TrCd]').attr('readonly', false);
		$modal.find('#formNewCode_Mode').val('insert');
		$modal.find('.modal-title').text('거래코드 추가');

		$modal.find('input[name=jnl12TrCd]').val('');
		$modal.find('input[name=jnl12TrNm]').val('');
		$modal.find('input[name=jnl12InOutTypeNm]').val('');
		$modal.find('input[name=jnl12UsePageType]').val('');
		$modal.find('input[name=jnl12UseYn]').first().attr('checked', true);
		//valid class remove
		
	
		$('#formNewCode').find('div.invalid-feedback').hide()
		$('#formNewCode').find('input.is-invalid').removeClass('is-invalid');
		
		$('#newJnl12TrCd').modal('show');
	})
	
	//Jnl12TrCd 수정버튼 클릭시 화면
	$('.btnCategoryModify').on('click', function(){
		console.log('거래코드 수정');
		var $modal = $('#newJnl12TrCd');
		$modal.find('#formNewCode_Mode').val('update');
		$modal.find('.modal-title').text('거래코드 수정');
		$tr = $(this).closest('tr');
		
		var val = $tr.find('.jnl12TrCd').text();
		$modal.find('input[name=jnl12TrCd]').val(val).attr('readonly', true)
		
		val =$tr.find('.jnl12TrNm').text(); 
		$modal.find('input[name=jnl12TrNm]').val(val);
		
		val =$tr.find('.jnl12InOutTypeNm').text(); 
		$modal.find('input[name=jnl12InOutTypeNm]').val(val);
		
		val =$tr.find('.jnl12UsePageType').text(); 
		$modal.find('input[name=jnl12UsePageType]').val(val);
		
		var useYn = $tr.find('input[name=jnl12UseYn]').val();		
		$modal.find('input[name=jnl12UseYn][value=' + useYn + ']').prop('checked',true);
		
		
		//valid class remove
		$('#formNewCode').find('div.invalid-feedback').hide()
		$('#formNewCode').find('input.is-invalid').removeClass('is-invalid');
		
		$('#newJnl12TrCd').modal('show');
	});
	
	//조회버튼
	$('.btnRetrieve').on('click', function(){
		var searchText = $('#searchText').val();
// 		if(searchText.length < 1) return;
		AssetUtil.submitGet('/jnl/trmap/list', {searchText: searchText});
	});
	//초기화
	$('.btnInit').on('click', function(){
		AssetUtil.submitGet('/jnl/trmap/list', {searchText: null});
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
 		var code = $(this).closest('tr').find('.jnl12TrCd').text();
 		AssetUtil.ajax('/jnl/trmap/find', { jnl12TrCd : code},{ success : makeTable})
 		
 	});
 	//거래코드 삭제
 	$('.btnCategoryDelete').on('click', function(e){
 		e.stopPropagation();
 		var cd = $(this).data('cd');
 		var nm = $(this).data('nm');
 		var msg = nm + "(" + cd + ")";
 		if (confirm(msg + ' 를 삭제하시겠습니까?')){
			AssetUtil.submitGet('/jnl/trmap/jnl12delete',{jnl12TrCd:cd});
 		}
 	});	
 	//거래코드 추가 또는 수정 submit
 	$('#btnInsertJnl12TrCd').on('click', function(){
		var $modal = $('#newJnl12TrCd');
		var $form = $('#formNewCode');
		//valid check
		var validateChecker = $form.jbvalidator({
        	language: '/js/validation/lang/ko.json'
  	  	});
		var errorCount = validateChecker.checkAll($form);
		if(errorCount > 0)return;
	
		var mode = $modal.find('#formNewCode_Mode').val();
		var url = '/jnl/trmap/jnl12' + mode ; //mode = insert or update
		var json = AssetUtil.formToJson($('#formNewCode'));
 		json = JSON.stringify(json);
 		AssetUtil.ajax(url, json, {method:'POST', success:(response)=>{
 			if(response.result == 'OK'){
 				alert(response.msg);
 				AssetUtil.submitGet('/jnl/trmap/list');
 			}else{
 				alert("실패하였습니다 " + response.result);
 			}
 		}});
 	});

    //Jnl13Tr 추가 btnDetailAdd
    $('#detail-code-area').on('click', '.btnDetailAdd', function(e){
        e.stopPropagation();
        var comCd = $('#detail-code-area').find('#selectedJnl12TrCd').val();
        console.log('분개매핑 추가', comCd);

        var $modal = $('#newJnl13Cd');
        $modal.find('#formNewJnl13_mode').val('insert');
        $modal.find('.modal-title').html('<b>[</b>' + comCd + ': 거래코드 <b>] 분개매핑 추가</b>');
        $('#formNewJnl13 input[name=jnl13TrCd]').val(jnl13TrCd);

        //각 필드 초기화
        var $tr = $(this).closest('tr');
        $modal.find('input[name=jnl13TrCd]').val('').attr('readonly', false);
        $modal.find('input[name=jnl13Seq]').val('');
        $modal.find('input[name=jnl13ReprAcntCd]').val('');
        $modal.find('input[name=jnl13DrcrType]').val('');
        $modal.find('input[name=jnl13Formula]').val('');
        
		//valid class remove
		$('#formNewJnl13').find('div.invalid-feedback').hide()
		$('#formNewJnl13').find('input.is-invalid').removeClass('is-invalid');
        
        $modal.modal('show');
    });

 	//Jnl13Tr 수정 btnDetailModify
 	$('#detail-code-area').on('click', '.btnDetailModify', function(e){
        e.stopPropagation();
        var comCd = $('#detail-code-area').find('#selectedJnl12TrCd').val();
        console.log('상세코드 수정', comCd);

        var $modal = $('#newDtlCd');
		$modal.find('#formNewJnl13_mode').val('update');
        $modal.find('.modal-title').html('<b>[</b>' + comCd + ': <b>] 상세코드 수정</b>');
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
 		var dtlCd = $(this).data('dtl-cd');
 		var dtlNm = $(this).data('dtl-nm');
 		console.log('detail delete', comCd, dtlCd, dtlNm);
 		var msg = '공통코드'+comCd +'의 상세코드+'+dtlCd+'('+dtlNm+') 을[를] 삭제하시겠습니까?';
 		if(confirm(msg)){
 			AssetUtil.submitGet('/code/delete',{comCd:comCd, dtlCd:dtlCd});
 		}
 	});

 	//상세코드 추가 또는 수정 submit
 	$('#btnInsertJnl13TrCd').on('click', function(){
 		alert("제출");
 		console.log("제출");
		var $modal = $('#newJnl13Cd');
		var $form = $('#formNewJnl13');
		//valid check
		var validateChecker = $form.jbvalidator({
        	language: '/js/validation/lang/ko.json'
  	  	});
		var errorCount = validateChecker.checkAll($form);
		if(errorCount > 0)return;
		
		var mode = $modal.find('#formNewJnl13_mode').val(); 
		var url = '/jnl/trmap/jnl13' + mode;
 		var json = AssetUtil.formToJson($('#formNewJnl13'));
 		var comCd = $('#formNewJnl13 input[name=jnl13TrCd]').val();
 		json = JSON.stringify(json);
 		console.log(json);
 		AssetUtil.ajax(url, json, 
 			{
 				method:'POST', 
 				success:(response)=>{
		 			if(response.result == 'OK'){
		 				alert(response.msg);
		 				var comCd = response.Jnl12Tr.jnl12TrCd;
		 				AssetUtil.submitGet('/jnl/trmap/list', {lastComCd : comCd});
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