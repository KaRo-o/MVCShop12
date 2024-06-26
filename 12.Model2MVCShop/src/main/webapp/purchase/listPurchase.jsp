
<%@page import="java.util.List"%>
<%@page import="com.model2.mvc.common.Page"%>
<%@page import="com.model2.mvc.service.domain.Purchase"%>
<%@page import="com.model2.mvc.common.Search"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="c"	uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<%-- 

<%

//HashMap<String, Object	> map = (HashMap<String, Object>)request.getAttribute("map");
List<Purchase> list = (List<Purchase>)request.getAttribute("list");
Search search = (Search)request.getAttribute("searchVO");
Page resultPage=(Page)request.getAttribute("resultPage");



%> --%>




<html>
<head>
<title>구매 목록조회</title>

<!-- 참조 : http://getbootstrap.com/css/   참조 -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	
	<!--  ///////////////////////// Bootstrap, jQuery CDN ////////////////////////// -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" >
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" >
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" ></script>
	
	
	<!-- Bootstrap Dropdown Hover CSS -->
   <link href="/css/animate.min.css" rel="stylesheet">
   <link href="/css/bootstrap-dropdownhover.min.css" rel="stylesheet">
    <!-- Bootstrap Dropdown Hover JS -->
   <script src="/javascript/bootstrap-dropdownhover.min.js"></script>
   
   
   <!-- jQuery UI toolTip 사용 CSS-->
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <!-- jQuery UI toolTip 사용 JS-->
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<!-- <link rel="stylesheet" href="/css/admin.css" type="text/css"> -->

<script type="text/javascript">
function fncGetUserList(currentPage){
	document.getElementById("currentPage").value = currentPage;
	document.detailForm.submit();
}
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width: 98%; margin-left: 10px;">

<form name="detailForm" action="/listPurchase" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"width="15" height="37"></td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">구매 목록조회</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"	width="12" height="37"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
	<tr>
		<td colspan="11">전체 ${resultPage.totalCount} 건수, 현재  ${resultPage.currentPage } 페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">회원ID</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">회원명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">전화번호</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">배송현황</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">정보수정</td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>

		<%-- <%
			//int no=list.size();
			for(int i=0; i<list.size(); i++) {
				Purchase vo = (Purchase)list.get(i);
		%> --%>
	<c:set var="i" value="0"></c:set>
	<c:forEach var="list" items="${list}">
	<c:set var="i" value="${i+1}"/>
	<tr class="ct_list_pop">
		<td align="center">
			<a href="/purchase/getPurchase?tranNo=${list.tranNo}">${i}</a>
		</td>
		<td></td>
		<td align="left">
			<a href="/user/getUser?userId=${list.buyer.userId}">${list.buyer.userId}</a>
		</td>
		<td></td>
		<td align="left">${list.receiverName}</td>
		<td></td>
		<td align="left">
		<c:if test="${list.receiverPhone != 'null' }">${list.receiverPhone}</c:if>
		<c:if test="${list.receiverPhone == 'null' }">없음</c:if>
		</td>
		<td></td>
		<td>
			<c:choose>
				<c:when test='${list.tranCode.trim().equals("1")}'>판매중</c:when>
				<c:when test='${list.tranCode.trim().equals("2")}'>구매완료</c:when>
				<c:when test='${list.tranCode.trim().equals("3")}'>배송중</c:when>
				<c:when test='${list.tranCode.trim().equals("4")}'>배송완료</c:when>
			</c:choose>
		</td>
		<td></td>
		
		<td>
			<c:if test='${list.tranCode.trim().equals("2")} && ${param.menu.equals("manage")} '>
				<a href="/purchase/updateTranCode?tranNo=${list.tranNo}&tranCode=3">배송시작</a>
			</c:if>
		 	<c:if test='${list.tranCode.trim().equals("3")}' >
				<a href="/purchase/updateTranCode?tranNo=${list.tranNo}&tranCode=4">물건도착</a>
			</c:if>
		</td>
		<td></td>
	</tr>
	</c:forEach>
	<%-- <%} %> --%>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>
	
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
			<tr>
					<td align="center">
						<input type="hidden" id="currentPage" name="currentPage" value=""/>
						
						<%-- <% if( resultPage.getCurrentPage() <= resultPage.getPageUnit() ){ %>
						◀ 이전
						<% }else{ %>
								<a href="javascript:fncGetPurchaseList('<%=resultPage.getCurrentPage()-1%>')">◀ 이전</a>
						<% } %>
								
						<%	for(int i=resultPage.getBeginUnitPage();i<= resultPage.getEndUnitPage() ;i++){	%>
						<a href="javascript:fncGetPurchaseList('<%=i%>')"><%=i %></a> 
						<% } %>
						
						<% if( resultPage.getEndUnitPage() >= resultPage.getMaxPage() ){ %>
						이후 ▶
						<% }else{ %>
								<a href="javascript:fncGetPurchaseList('<%=resultPage.getEndUnitPage()+1%>')">이후 ▶</a>
						<% } %> --%>
						<jsp:include page="../common/pageNavigator.jsp"/>
					</td>
				</tr>
</table>

<!--  페이지 Navigator 끝 -->
</form>

</div>

</body>
