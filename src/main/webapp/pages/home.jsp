
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE HTML>
<html>

<head>

	<title>Home</title>
	
	<jsp:include page="includes/includes.jsp"></jsp:include>

	<script type="text/javascript">
	$(document).ready(function() {
		
		
	});
	</script>
	
</head>

<body>
	<div class="wrapper">

		<div id="header-cont" class="float-fix">
			<div class="float-left">
				<h1 class="page-title"> Home Page </h1>
			</div>
			
			<div class="float-right">
				<label> welcome <span class="bold"> ${user.fullName} </span> </label>
			</div>
		</div>
			
		<div id="main-cont" class="float-fix">
	
			<div class="item-box">
				
				<label class="title"> Chat Rooms </label>
				
				<ul class="simple-list">
					<c:forEach items="${chatRooms}" var="chatRoom" varStatus="roomIndex">
						<li>
							<c:set var="chatUsers" value="${chatRoom.chatUsers}"></c:set>
							<a href="<%= request.getContextPath()%>/chat?roomName=${chatRoom.name}" class="margin-5px"> ${chatRoom.name} (  ) </a>
						</li>
					</c:forEach>
				</ul>	
			</div>
		
			<div class="item-box">
			
				<label class="title"> Rock-Paper-Scissors </label>
				<ul class="simple-list">
					<c:forEach items="${chatRooms}" var="chatRoom" varStatus="roomIndex">
						<li>
							<a href="<%= request.getContextPath()%>/game/rps?gameId=${roomIndex.count}" class="margin-5px"> Rock Paper Scissors  </a>
						</li>
					</c:forEach>
				</ul>
				
			</div>
			
		</div>
		
	</div>
</body>

</html>
