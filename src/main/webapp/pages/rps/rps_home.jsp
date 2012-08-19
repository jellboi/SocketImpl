
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE HTML>
<html>

<head>

	<title>Home</title>
	
	<jsp:include page="../includes/includes.jsp"></jsp:include>

	<script type="text/javascript">
	$(document).ready(function() {
		
		
	});
	</script>
	
</head>

<body>

	<div class="float-left margin-5px">
		<label class="page-title"> Rock Papers Scissors </label>
	</div>
		
	<div class="float-left clear margin-5px">
		<label> welcome <span class="bold"> ${user.fullName} </span> </label>
	</div>
	
	<!-- Body Content -->
	
	<div id="top-cont" class="" style="border-top: none;">
	
		<div id="carousel-cont" class="float-fix">
			
		</div>
			
		<div id="search-cont">
			<div id="search-header" class="float-fix">

			</div>
			
			<div id="search-results-cont" style="display: none;">
			
			</div>
		
		</div>
		
	</div>
		
	<div id="main-cont" class="float-fix">
		<div class="float-left clear margin-5px">
			<label class="page-title"> Rooms list</label>
		</div>
		
		<ul class="float-left clear margin-5px">
			<li>
				<a href="<%= request.getContextPath()%>/rps/game?room=1"> Game 1 </a>
			</li>
			<li>
				<a href="<%= request.getContextPath()%>/rps/game?room=2"> Game 2 </a>
			</li>
			<li>
				<a href="<%= request.getContextPath()%>/rps/game?room=3"> Game 3 </a>
			</li>
			<li>
				<a href="<%= request.getContextPath()%>/rps/game?room=4"> Game 4 </a>
			</li>
		</ul>

		
	</div>
	
	<section class="pop-ups-cont" style="display: none;">
		
		
	</section>
		    	
</body>

</html>
