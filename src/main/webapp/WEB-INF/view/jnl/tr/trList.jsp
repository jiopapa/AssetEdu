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
<title><c:out value="${pageTitle}" default="거래유형별 분개맵핑-리스트" /></title>
</head>
<style>
.table tbody tr.highlight td {
  background-color: #EAF0F7;
}
.table tbody tr.subSumTr td {
  background-color: #FAF4F4;
}
@media (min-width: 100%) {
    .container{
        max-width: 90%;
    }
}
</style>
<body>
<!-- =================================================== -->
<jsp:include page="../../common/header.jsp" flush="false" />
<!-- =================================================== -->
<c:set var="baseUrl" value="/jnl/trmap"/>
<main class="container mx-3 my-3" >

	<h2><i class="fa-solid fa-cube my-3"></i> 거래유형별 분개맵핑</h2>

	<div class="container-lg p-3 border border-2 rounded-1">
		<input type="text" class="form-control w-50 d-inline align-middle" placeholder="검색어(거래코드/거래명)를 입력하세요" id="searchText" name="searchText" value="${param.searchText}">
		<a class="btn d-inline align-middle btn-primary btnRetrieve"><i class="fa-solid fa-search"></i> 조회</a>
        <a class="btn d-inline align-middle btn-secondary btnInit"><i class="fa-solid fa-backspace"></i> 초기화</a>
	</div>
	
	<div class="row">
	<!-- Tr 리스트 -->
		<div class="col-5 tr12 py-3">
		<table id="table12" class="table table-hover table-bordered table-sm" style="font-size:small">
			  <thead class="table-light">
			    <tr class="text-center align-middle">
			      <th scope="col" style="width:50px">No</th>
			      <th scope="col" style="width:70px">거래코드</th>
			      <th scope="col">거래명</th>
			      <th scope="col" style="width:100px">입출고구분</th>
			      <th scope="col" style="width:85px">사용여부</th>
			      <th scope="col" style="width:80px">
			         <button class="btn btn-success btn-sm btnDisplayEditTr12"><span><i class="fa-regular fa-pen-to-square"></i> 추가</span></button>
<!-- 			      	<a class="d-inline  btn btn-success btnDisplayEditTr12"> -->
<!-- 			      		<span title="거래코드 추가"><i class="fa-sharp fa-solid fa-plus"></i></span> -->
<!-- 			      	</a> -->
			      </th>
			    </tr>
			  </thead>
			  <tbody class="table-group-divider" >
			  	<c:forEach var="item" items="${list12}" varStatus="status">
				    <tr data-tr-cd="${item.jnl12TrCd }" data-tr-nm="${item.jnl12TrNm }" class="align-middle">
				      <td scope="col" class="text-center" >${status.count }</td>
				      <td scope="col" class="text-center clickableRow" ><c:out value="${item.jnl12TrCd }"/></td>
				      <td scope="col" class="text-start clickableRow"  ><c:out value="${item.jnl12TrNm }"/></td>
				      <td scope="col" class="text-center" ><c:out value="${item.jnl12InOutTypeNm }"/></td>
				      <td scope="col" class="text-center" ><c:out value="${item.jnl12UseYn }"/></td>
				      <td scope="col" class="text-center">
					      <button class="btn btn-primary btn-sm btnModify12" data-tr-cd="${item.jnl12TrCd}"><span title="수정"><i class="fa-regular fa-pen-to-square"></i></span></button>
					      <button class="btn btn-danger btn-sm btnDelete12" data-tr-cd="${item.jnl12TrCd}" data-tr-nm="${item.jnl12TrNm}"><span title="삭제"><i class="fa-regular fa-trash-can"></i></span></button>		      
				      </td>
				    </tr>
			    </c:forEach>
			  </tbody>
			</table>
		</div>
		<div class="col-7 tr13  py-3 px-3">
			<table id="table13" class="table table-hover table-bordered table-sm" style="font-size:small">
			  <thead class="table-light">
			    <tr class="text-center align-middle">
 			     
                  <th scope="col" style="width:70px">No</th>
                  <th scope="col" style="width:50px">순번</th>
                  <th scope="col">대표계정명</th>
                  <th scope="col" style="width:100px">차대구분</th>
                  <th scope="col">계산식</th>
			      <th scope="col" style="width:80px">
                      <button class="btn btn-success btn-sm btnDisplayEditTr13"><span><i class="fa-regular fa-pen-to-square"></i> 추가</span></button>
			      </th>
			    </tr>
			  </thead>
			  <tbody class="table-group-divider" >
			  </tbody>
			</table>
		</div>
	</div>
		
		</main>
		<!-- Modal 거래코드 추가와 수정을 같이 사용한다-->
<div class="modal fade" id="newTrCd" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
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
	            <input class="form-control is-valid" name=jnl12TrCd placeholder="코드(숫자만)" required pattern="^[0-9]+$" data-v-min-length="4" maxlength="4" />
	          </div>	
	          <div class="mb-3">
	            <label for="jnl12TrNm" class="form-label fw-bold" >거래명</label>
	            <input type="text" class="form-control is-valid" name="jnl12TrNm" placeholder="거래명" required />
	          </div>
	          <div class="mb-3">
	            <label for="jnl12InOutTypeNm" class="form-label fw-bold">입출고구분</label>
	            <input type="text" class="form-control" name="jnl12InOutType"  required pattern="[0-9]+"  />
	          </div>
	          <div class="mb-3 align-middle">
	            <label for=jnl12UseYn class="form-label fw-bold">사용여부</label>&nbsp;
	            <input type="radio" name="jnl12UseYn" id="com02UseYn11" value="true" checked/> <label for="com02UseYn11"> 사용함</label>&nbsp;
				<input type="radio" name="jnl12UseYn" id="com02UseYn22" value="false"/> <label for="com02UseYn22"> 사용안함</label>
	          </div>
	         
			</div>
			<div class="modal-footer d-flex justify-content-center">
	        	<button type="button" class="btn btn-primary" id="btnInsertTrCd">저장</button>
	            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
			</div>
		</form>
	</div>
  </div>
</div>



  


<script id="table12-tr-list-template" type="text/x-handlebars-template">
<!-- jnl12Tr 리스트 TR -->
{{#each list}}
<tr data-tr-cd="{{jnl12TrCd }}" data-tr-nm="{{jnl12TrNm }}" class="align-middle">
  <td scope="col" class="text-center">{{inc @index}}</td>
  <td scope="col" class="text-center">{{jnl12TrCd}}</td>
  <td scope="col">{{jnl12TrNm}}</td>
  <td scope="col" class="text-center">{{jnl12InOutTypeNm}}</td>
  <td scope="col" class="text-center">{{jnl12UseYn}}</td>
  <td scope="col">
      <button class="btn btn-primary btn-sm btnModify12" data-tr-cd="{{jnl12TrCd}}"><span><i class="fa-regular fa-pen-to-square"></i></span></button>
      <button class="btn btn-danger btn-sm btnDelete12" data-tr-cd="{{jnl12TrCd}}" data-tr-nm="{{jnl12TrNm }}"><span><i class="fa-regular fa-trash-can"></i></span></button>		      
  </td>
</tr>
 {{/each}}
</script>

<script id="table12-tr-form-template" type="text/x-handlebars-template">
<!-- jnl12Tr 편집(수정,추가) TR -->
<tr id="tr12-grid" class="align-middle">
  <td scope="col" class="text-center fw-bolder">{{mode-name}}</td>
  <td scope="col"><input type="text" name="jnl12TrCd" value="{{jnl12TrCd}}"  class="form-control form-control-sm" maxlength="4" /></td>
  <td scope="col"><input type="text" name="jnl12TrNm" value="{{jnl12TrNm}}"  class="form-control form-control-sm"/></td>
  <td scope="col">
	<!--input type="text" name="jnl12InOutType" value="{{jnl12InOutType}}" /-->
	<select name="jnl12InOutType" class="form-select form-select-sm">
		<option value=""></option>
		<option value="1" {{#cond jnl12InOutType '==' '1'}}selected{{/cond}}>입고</option>
		<option value="2" {{#cond jnl12InOutType '==' '2'}}selected{{/cond}}>출고</option>
		<option value="3" {{#cond jnl12InOutType '==' '3'}}selected{{/cond}}>평가</option>
    </select>
  </td>
  <td scope="col">
		<!-- input type="text" name="jnl12UseYn" value="{{jnl12UseYn}}"/ -->
	<div class="form-check">
		<input type="radio" class="form-check-input form-check-input-sm" name="jnl12UseYn" id="jnl12UseYn1" value="true" {{#cond jnl12UseYn '==' 'true'}}checked{{/cond}}/> <label for="jnl12UseYn1" class="form-check-label">사용</label>&nbsp;
	</div>
	<div class="form-check">
		<input type="radio" class="form-check-input" name="jnl12UseYn" id="jnl12UseYn2" value="false" {{#cond jnl12UseYn '==' 'false'}}checked{{/cond}}/> <label for="jnl12UseYn2"  class="form-check-label">사용안함</label>
	</div>
  </td>
  <td scope="col" class="form-control text-center">
      <button class="btn btn-primary btn-sm btnSaveGrid" data-mode="{{mode-name}}"><span><i class="fa-solid fa-database"></i></span></button>
      <button class="btn btn-danger btn-sm btnCancelGrid" data-mode="{{mode-name}}"><span><i class="fa-solid fa-xmark"></i></span></button>		      
  </td>
</tr>
</script>
<script id="table13-tr-list-template" type="text/x-handlebars-template">
<!-- jnl13Tr 리스트 TR -->
{{#each list}}
<tr class="align-middle">
  <td scope="col" class="text-center">{{inc @index}}</td>
  <td scope="col" class="text-center" >{{jnl13Seq }}</td>
  <td scope="col">
     <input type="hidden" value="{{jnl13ReprAcntCd}}" />
     {{jnl11ReprAcntNm }}
  </td>
  <td scope="col" class="text-center" >{{jnl13DrcrTypeNm }}</td>
  <td scope="col">{{jnl13Formula }}</td>
  <td scope="col" class="text-center">
	  <button class="btn btn-primary btn-sm btnModify" data-tr-nm="{{jnl11ReprAcntNm}}" data-tr-cd="{{jnl13TrCd }}" data-seq="{{jnl13Seq }}"><span title="수정"><i class="fa-regular fa-pen-to-square"></i></span></button>
	  <button class="btn btn-danger btn-sm btnDelete" data-tr-cd="{{jnl13TrCd }}" data-seq="{{jnl13Seq }}"><span title="삭제"><i class="fa-regular fa-trash-can"></i></span></button>		      
  </td>
</tr>
 {{/each}}
</script>
<script id="table13-tr-form-template" type="text/x-handlebars-template">
<!-- jnl13Tr 편집(수정,추가) TR -->
<tr id="tr13-grid" class="align-middle">
  <td scope="col" class="text-center">{{jnl13TrCd}}<br/>[{{jnl13TrNm}}]</td>
  {{#cond mode '==' '추가'}}  
  <td scope="col"><input type="text" name="jnl13Seq" value="{{jnl13Seq }}" class="form-control form-control-sm" /></td>
  {{else}}
  <td scope="col" class="text-center">{{jnl13Seq}}</td>
  {{/cond}}
  <td scope="col">
	<div class="d-flex">
     <button class="btn btn-outline-secondary btn-sm text-warning bg-secondary" type="button" id="jnl13ReprAcntCdPopup"><i class="fa-solid fa-search"></i></button>
     <input type="hidden" id="jnl13ReprAcntCd" name="jnl13ReprAcntCd" value="{{jnl13ReprAcntCd}}" />
     <input  type="text" id="jnl13ReprAcntNm" name="jnl13ReprAcntNm" value="{{jnl11ReprAcntNm}}" class="form-control form-control-sm" readonly="true" style="background-color:#F5F5F5"/>
  </div>
  </td>
  <td scope="col">
	<!--input type="text"  name="jnl13DrcrType" value="{{jnl13DrcrType }}" /-->
	<select name="jnl13DrcrType" class="form-select form-select-sm">
		<option value=""selected></option>
		<option value="D" {{#cond jnl13DrcrType '==' 'D'}}selected{{/cond}}>차변</option>
		<option value="C" {{#cond jnl13DrcrType '==' 'C'}}selected{{/cond}}>대변</option>
    </select>

  </td>
  <td scope="col"><input type="text"  name="jnl13Formula" value="{{jnl13Formula }}"  class="form-control form-control-sm" /></td>
  <td scope="col" class="text-end">
	  <button class="btn btn-primary btn-sm btnSave13" data-mode="{{mode}}" data-tr-cd="{{jnl13TrCd }}" data-seq="{{jnl13Seq }}"><span title="저장"><i class="fa-solid fa-database"></i></span></button>
	  <button class="btn btn-danger btn-sm btnCancel13" data-mode="{{mode}}" data-tr-cd="{{jnl13TrCd }}" data-seq="{{jnl13Seq }}"><span title="취소"><i class="fa-solid fa-xmark"></i></span></button>		      
  </td>
</tr>
</script>

<!-- =================================================== -->
<jsp:include page="../../common/footer.jsp" flush="false" />
<!-- -================================================== -->
<script type="text/javascript" src="/js/input-format.js"></script>
<script>
$(document).ready(function () {
	console.log('acnt 거래유형별 분개맵핑 리스트..');
	var highlightCssName = 'highlight';//'table-danger' ;
	function generateHtml(templateId, data){
		var template = $(templateId).html();
		var compiledTemplate =  Handlebars.compile(template);
		return html = compiledTemplate(data);
	}
	const baseUrl = "/jnl/trmap";
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
	$('.clickableRow').on('click', function(){
		var code = $(this).find('c\\:out').text();
 		AssetUtil.ajax('/jnl/trmap/find', { jnl12TrCd: code},{ success : makeTable})
 		
 	});
	function makeTbody13(trCd){
	  var url = baseUrl + "/jnl13/" + trCd;
	  $.get(url, function(response, status){
		  console.log(response);
		  makeTbody13List(response.list)
	  });
	}
	//공통코드가 선택시 상세코드리스트를 테이블로 만들어서 보여준다.
 	function makeTable(response){
 		console.log(response);
 		var rawTemplate = $('#detail-table-template').html();
 		var compiledTemplate =  Handlebars.compile(rawTemplate);

 		var html = compiledTemplate(response);
 		$('#detail-code-area').html(html);
 	}
	function makeTbody13List(list){
		var $table13Body = $('#table13 tbody');
		var html = '';
		if(list.length == 0){
			html = '<tr class="table-warning text-center"><td colspan="6">매핑데이터가 없습니다</td></tr>';
		}else{
			html = generateHtml('#table13-tr-list-template', {list:list});
		}
		console.log(html);
		$table13Body.html(html);			
	}
    
	//table13에서 popup창으로 대표계정코드 구하기
	$('#table13').on('click','#jnl13ReprAcntCdPopup', function(){
        var url = '/popup/jnl/repr-acnt?openerCdId=jnl13ReprAcntCd&openerNmId=jnl13ReprAcntNm';
        var prop = {};
        var width = 720;
        var height = 518;
        var win = AssetUtil.popupWindow(url, '대표계정코드', {}, width, height);
        return false;
	});
	
}); 
</script>
</body>
</html>