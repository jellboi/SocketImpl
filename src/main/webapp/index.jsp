<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE HTML>
<html>
<head>

	<title>SocketImpl</title>
	
	<jsp:include page="pages/includes/includes.jsp"></jsp:include>

</head>

<body>
	<div class="" style="margin: 0 auto; width: 300px; padding-top: 100px;">
		<form method="post" action="<%= request.getContextPath()%>/home">
			<ul>
				 <li class="margin-5px">
				 	<input type="text" name="emailId" placeholder="Your Email-ID" class="inp-box" />
				 </li>
				 <li class="margin-5px">
				 	<input type="text" name="fullName" placeholder="Your full Name" />
				 </li>
				 <li class="margin-5px">
				 	<input type="text" name="displayName" placeholder="Your Role Name" />
				 </li>
				 <li class="margin-5px">
				 	<input type="submit" value="Enter" class="margin-5px btn btn-yellow" style="width: 280px;"/>
				 </li>
			</ul>
		</form>
	</div>
</body>
</html>