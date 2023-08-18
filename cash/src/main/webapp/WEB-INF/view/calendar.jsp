<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--문자열 자르기 위한 import -->
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>calendar.jsp</title>
<!-- Latest compiled and minified CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">

<!-- Latest compiled JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<div class="container">
	<div align="center">
		<h1>
			<!-- 자바코드(제어문) : JSTL 사용 -->
			<a href="/cash/calendar?targetYear=${targetYear}&targetMonth=${targetMonth-1}">	&#60;&#60;</a>
			${targetYear}년 ${targetMonth+1}월
			<a href="/cash/calendar?targetYear=${targetYear}&targetMonth=${targetMonth+1}">&#62;&#62;</a>
		</h1>	
	</div>	

	<table class="table table-bordered ">
		<thead>
			<tr>
				<th>일</th>
				<th>월</th>
				<th>화</th>
				<th>수</th>
				<th>목</th>
				<th>금</th>
				<th>토</th>
			</tr>
		</thead>
		<tr>
			<!-- 총 날짜까지 반북문 -->
			<c:forEach var="i" begin="0" end="${totalTd - 1}" step="1">
				<!--dateNum에 시작 날짜 입력 (시작 공백만큼 빼주고 1을 더해야 시작일) -->
				<c:set var="dateNum" value="${i - beginBlank + 1}" />
				
				<!--7일마다 행바꿈 -->
				<c:if test="${i % 7 == 0 && i !=0}">
					</tr><tr>
				</c:if>
					
				<!--1일부터 마지막 날까지 출력 -->
				<c:if test="${dateNum > 0 && dateNum <= lastDate}">
				
					<!--7일마다 빨간색으로 -->
					<c:if test="${i % 7 == 0}">
						<td>
							<p class="text-danger">${dateNum}</p>
							
							<!-- cashbook 내용 출력 -->
							<c:forEach var="cashInfo" items="${dbCashbook}">
								<!-- 날짜와 cashInfo가 맞는 경우만 츌력 -->
								<c:if test="${dateNum == fn:substring(cashInfo.cashbookDate, 8,10) }">
									<div>
										<!-- 수입인 경우 + -->
										<c:if test="${cashInfo.category=='수입'}">										
											<span style="color:blue">+${cashInfo.price}	</span>
										</c:if>
										<!-- 지출인 경우 - -->
										<c:if test="${cashInfo.category=='지출'}">										
											<span style="color:red">-${cashInfo.price}</span>
										</c:if>
									</div>
								</c:if>
							</c:forEach>
												
						</td>
					</c:if>					
					<!--빨간색이 아닌 날 -->
					<c:if test="${i % 7 != 0}">
						<td>
							<p class="text-body">${dateNum}</p>
							
							<!-- cashbook 내용 출력 -->
							<c:forEach var="cashInfo" items="${dbCashbook}">
								<!-- 날짜와 cashInfo가 맞는 경우만 츌력 -->
								<c:if test="${dateNum == fn:substring(cashInfo.cashbookDate, 8,10) }">
									<div>
										<!-- 수입인 경우 + -->
										<c:if test="${cashInfo.category=='수입'}">										
											<span style="color:blue">+${cashInfo.price}	</span>
										</c:if>
										<!-- 지출인 경우 - -->
										<c:if test="${cashInfo.category=='지출'}">										
											<span style="color:red">-${cashInfo.price}</span>
										</c:if>
									</div>
								</c:if>
							</c:forEach>
							
						</td>						
					</c:if>					
				</c:if>
				<!-- 날짜 범위 밖은 빈 칸 -->
				<c:if test="${!(dateNum > 0 && dateNum <= lastDate)}">
					<td></td>
				</c:if>
			</c:forEach>
		</tr>
	</table>
</div>
</body>
</html>