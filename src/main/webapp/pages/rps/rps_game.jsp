<!DOCTYPE html>
<html>
<head>

	<title>Apache TomCat WebSocket Examples</title>
	
	<jsp:include page="../includes/includes.jsp"></jsp:include>
	
	<script type="text/javascript">
	
		var contextPath = '<%= request.getContextPath() %>';
	    	
		var gameId = '<%= request.getParameter("gameId") %>';
		var emailId = '${user.emailId}';
	
	    var ws = null;
	
		var rps_game_url = 'ws://localhost:8080/SocketImpl/game/rps/play?gameId=' + gameId + '&emailId=' + emailId;
// 		var rps_game_url = 'ws://10.42.43.1:8080/SocketImpl/game/rps/play?gameId=' + gameId + '&emailId=' + emailId;

		/* Game realated Variables  */
		
		var countDownTime = 5000;
		var isPlayerReady = false;
		var playerChoice;
		
		var count = 5;
		var counter;
		
		function connect() {
			if ('WebSocket' in window) {
				ws = new WebSocket(rps_game_url);
			} else if ('MozWebSocket' in window) {
				ws = new MozWebSocket(rps_game_url);
			} else {
				alert('WebSocket is not supported by this browser.');
				return;
			}
			ws.onopen = function() {

			};
			ws.onclose = function() {

			};
			ws.onmessage = function(event) {
// 				console.log(event.data);
				
				var msg = event.data;
				if(msg && msg == "GAME_IS_READY" ) {
					// Show Game Controls	
					$('#pre-start-cont').hide();
					$('#rps-table').show();
					
				} else if(msg == "GAME_IS_NOT_READY" ){
					// Hide Game Controls	
					$('#rps-table').hide();
					$('#pre-start-cont').show();
					
				} else if(msg == "START" ){
					// Start CountDown 
					count = 5;
					counter = setInterval(function(){
							countDownTimer();
						}, 1000);

					function countDownTimer() {
						count = count - 1;
						if (count <= 0) {
							clearInterval(counter);
							return;
						}
						$("#count-down-secs").html('<span class="count">' + count + '</span>');
					}
					
					$('#status-cont').html('<label > Pick Your Symbol !!! </label>').show();
					
				} else if(msg.indexOf("RESULT:") == 0){
					// Publish Result and wait for new round 
					
					var result = msg.substring(7);
					clearInterval(counter);
					showResult(result);
					
					$('#status-cont').hide();
					$('#start-btn').show();
					
					
				} else if(msg.indexOf("MSG:") == 0) {
					// Log the message in the console 
					
					msg = msg.substring(4);
					log(msg);
				} 
				
			};
		}
		
		function showResult(result) {
			if(result) {
				var resultCont = $("#count-down-secs");
				switch (result) {
					case "TIE":
						resultCont.html("TIE");
						setOpponentChoiceToTie();
						break;
					case "WON":
						resultCont.html("You Won");
						setOpponentChoiceToWin();
						break;
					case "LOST":
						resultCont.html("You Lost");
						setOpponentChoiceToLoose();
						break;
					default:
						break;
				}
				
			}
		};
		
		function setOpponentChoiceToTie(){
			var imgSrc = $('#left-player-choice img').attr('src');
			$('#right-player-choice img').attr('src', imgSrc);
		};
		
		function setOpponentChoiceToLoose(){
			var img = $('#right-player-choice img');
			switch (playerChoice) {
				case "ROCK":
					img.attr('src', contextPath + '/themes/images/rps/left_paper.png');
					break;
				case "PAPER":
					img.attr('src', contextPath + '/themes/images/rps/left_scissors.png');
					break;
				case "SCISSORS":
					img.attr('src', contextPath + '/themes/images/rps/left_rock.png');
					break;
				default:
					break;
			}
		};
		
		function setOpponentChoiceToWin(){
			var img = $('#right-player-choice img');
			switch (playerChoice) {
				case "ROCK":
					img.attr('src', contextPath + '/themes/images/rps/left_scissors.png');
					break;
				case "PAPER":
					img.attr('src', contextPath + '/themes/images/rps/left_rock.png');
					break;
				case "SCISSORS":
					img.attr('src', contextPath + '/themes/images/rps/left_paper.png');
					break;
				default:
					break;
			}
		};
		
		
		function log(message) {
			var console = document.getElementById('chat-console');
			var p = document.createElement('p');
			p.appendChild(document.createTextNode(message));
			console.appendChild(p);
			while (console.childNodes.length > 25) {
				console.removeChild(console.firstChild);
			}
			console.scrollTop = console.scrollHeight;
		};
		
		
		
		function sendMessage() {
			if (ws != null) {
				var message = document.getElementById('my-message').value;
				ws.send("MSG:" + message);
			} 
		};
		
		function sendReady() {
			if (ws != null) {
				ws.send("READY:");
				
				$('#start-btn').hide();
				$('#status-cont').html('<label > Waiting for the other player !!! </label>').show();
				$("#count-down-secs").html('');
				$('.player-choice img').attr('src', '');
			} 
		};
		
		function sendChoice(choice) {
			if (ws != null) {
				ws.send("CHOICE:" + choice);
				playerChoice = choice;
				
				var img = $('#left-player-choice').find('img');
				
				switch (choice) {
					case "ROCK":
						img.attr('src', contextPath + '/themes/images/rps/left_rock.png');
						break;
					case "PAPER":
						img.attr('src', contextPath + '/themes/images/rps/left_paper.png');
						break;
					case "SCISSORS":
						img.attr('src', contextPath + '/themes/images/rps/left_scissors.png');
						break;
					default:
						break;
				}
				
			} 
		};
		
		$(document).ready(function(){
			connect();
		});
		
	</script>
</head>
<body>
	<div class="wrapper">

		<div id="header-cont" class="float-fix">
			<div class="float-left">
				<h1 class="page-title"> RPS - <%= request.getParameter("gameId") %>  </h1>
			</div>
			
			<div class="float-right">
				<label> welcome <span class="bold"> ${user.fullName} </span> </label>
			</div>
		</div>
			
		<div id="main-cont" class="float-fix">
			
			<div id="pre-start-cont">
				<p>Waiting for one more player to join</p>
			</div>
			
			<table id="rps-table" style="display: none;">
				<tbody>
					<tr>
						<td width="70%">
						
							<div id="rps-cont" class="float-fix">
								
								<div class="float-left" style="width: 25%;">
									<ul class="rps-choice-list">
										<li id="left-rock" title="Rock">
											<a href="javascript:void(0);" onclick="sendChoice('ROCK');">
												<img alt="Rock" src="<%= request.getContextPath() %>/themes/images/rps/left_rock.png">
											</a>
										</li>
										<li id="left-paper" title="Paper" >
											<a href="javascript:void(0);" onclick="sendChoice('PAPER');">
												<img alt="Paper" src="<%= request.getContextPath() %>/themes/images/rps/left_paper.png">
											</a>
										</li>
										<li id="left-scissors" title="Scissors">
											<a href="javascript:void(0);" onclick="sendChoice('SCISSORS');">
												<img alt="Scissors" src="<%= request.getContextPath() %>/themes/images/rps/left_scissors.png">
											</a>
										</li>
									</ul>
								</div>
								
								<div class="float-right" style="width: 70%;">
									<div id="rps-info-cont" class="float-fix">
										<div id="start-btn" >
											<input type="button" class="btn" onclick="sendReady();" value="Start">
										</div>
										<div id="status-cont" style="display: none;">
											<label id="status-msg">Waiting for Other Player</label>
										</div>
									</div>
								
									<div id="rps-game-cont" class="float-fix">
										<div id="left-player-choice" class="player-choice">
											<img alt="" src="">
										</div>
										<div id="count-down-cont">
											<label id="count-down-secs"></label>
										</div>
										<div id="right-player-choice" class="player-choice">
											<img alt="" src="">
										</div>
									</div>
									
									<div id="rps-scores-cont" class="float-fix">
										<div class="float-left">
											<label class="clear bold margin-5px">Player 1</label>
											<label class="clear bold margin-5px">5 wins</label>
										</div>
										<div class="float-right">
											<label class="clear bold margin-5px">Player 2</label>
											<label class="clear bold margin-5px">4 wins</label>									
										</div>									
									</div>
								
								</div>
								
							</div>
							
						</td>
						<td width="*">
						
							<div id="chat-cont" class="float-right">
								<div id="chat-console">
									<!-- Chat is appended here -->
								</div>
								
								<div id="">
									<div>
										<textarea id="my-message" placeholder="Say Something !!!"></textarea>
									</div>
									<div class="btn-cont">
										<input type="button" class="btn" onclick="sendMessage();" value="send">
<!-- 										<button id="echo" class="btn" onclick="echo();" >Echo message</button> -->
									</div>
								</div>
							</div>
							
						</td>
					</tr>
				</tbody>
			</table>
			
		</div>
	</div>
		
</body>
</html>