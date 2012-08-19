<!DOCTYPE html>
<html>
<head>

	<title>Apache TomCat WebSocket Examples</title>
	
	<jsp:include page="../includes/includes.jsp"></jsp:include>
	
	<script type="text/javascript">
	    	
	    var roomName = '<%=request.getParameter("roomName")%>';
		var emailId = '${user.emailId}';
	
		var ws = null;
	
		var chat_room_url = 'ws://localhost:8080/SocketImpl/chat/room?roomName=' + roomName + '&emailId=' + emailId;
// 		var chat_room_url = 'ws://10.42.43.1:8080/SocketImpl/chat/room?roomName=' + roomName + '&emailId=' + emailId;
		
		function setConnected(connected) {
// 			document.getElementById('connect').disabled = connected;
// 			document.getElementById('disconnect').disabled = !connected;
			document.getElementById('echo').disabled = !connected;
		}
	
		function connect() {
			if ('WebSocket' in window) {
				ws = new WebSocket(chat_room_url);
			} else if ('MozWebSocket' in window) {
				ws = new MozWebSocket(chat_room_url);
			} else {
				alert('WebSocket is not supported by this browser.');
				return;
			}
			ws.onopen = function() {
// 				setConnected(true);
// 				log('Info: WebSocket connection opened.');
			};
			ws.onmessage = function(event) {
// 				console.log(event);
// 				console.log(event.data);
				log(event.data);
			};
			ws.onclose = function() {
// 				setConnected(false);
// 				log('Info: WebSocket connection closed.');
			};
		}
	
		function disconnect() {
			if (ws != null) {
				ws.close();
				ws = null;
			}
			setConnected(false);
		}
	
		function echo() {
			if (ws != null) {
				var message = document.getElementById('my-message').value;
// 				log('Sent: ' + message);
				ws.send(message);
			} else {
				alert('WebSocket connection not established, please connect.');
			}
		}
	
		function log(message) {
			var console = document.getElementById('chat-console');
			var p = document.createElement('p');
			p.appendChild(document.createTextNode(message));
			console.appendChild(p);
			while (console.childNodes.length > 25) {
				console.removeChild(console.firstChild);
			}
			console.scrollTop = console.scrollHeight;
		}
		
		$(document).ready(function(){
			connect();
		});
		
	</script>
</head>
<body>
	<div class="wrapper">

		<div id="header-cont" class="float-fix">
			<div class="float-left">
				<h1 class="page-title"> Chat Room - <%= request.getParameter("roomName") %>  </h1>
			</div>
			
			<div class="float-right">
				<label> welcome <span class="bold"> ${user.fullName} </span> </label>
			</div>
		</div>
			
		<div id="main-cont" class="float-fix">
			
			<div id="chat-cont">
				<div id="chat-console">
					<!-- Chat is appended here -->
				</div>
				
				<div id="">
					<div>
						<textarea id="my-message" placeholder="Say Something !!!"> </textarea>
					</div>
					<div class="btn-cont">
<!-- 						<button id="connect" class="btn" onclick="connect();">Connect</button> -->
<!-- 						<button id="disconnect" class="btn" onclick="disconnect();">Disconnect</button> -->
						<button id="echo" class="btn" onclick="echo();" >Echo message</button>
					</div>
				</div>
				
			</div>
			
		</div>
	</div>
		
</body>
</html>