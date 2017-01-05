# connect4 Game Implementation using Spring Boot and Rest

#1. How to build the application
   
   	mvn clean package

#2. How the run the application
   
   	java -jar target/connect4-0.0.1-SNAPSHOT.jar
	
#3. How to test the REST Services
   
# i) Create New Game
      
      This call creates a new game with one player and his disc color of choice.
   
   		Method: POST
   
   		http://localhost:8080/connect4/games
   
   		JSON Request body:
   		
   		{
			"userId":"Player1",
			"color":"RED",
			"column":1
   		}
   
   
# ii) Get Game
       
       This call returns the game associated with gameId
   
   		Method: GET
   
   		http://localhost:8080/connect4/games/{gameId}
   		
   		Here gameId is the request parameter, for testing this gameId can be captured from the above JSON response
   
# iii) Join Game
	
	     This call allows the player2 to join the game created by player1 using the gameId
	
		 Method: PUT
	
		 http://localhost:8080/connect4/games/{gameId}
   
   		JSON Request body:
   		
   		{
			"userId":"Player2",
			"color":"YELLOW",
			"column":1
   		}
   
# iv) Play Game
     
       This call allows the Players in the game to drop their disc in each turn, No player can drop two discs consiqutively and
       this result 409 HTTP error code. And also the method can result in WINNER during the play.
       
       Method: PUT
       
       http://localhost:8080/connect4/games/{gameId}/discs
   
   	   JSON Request body:
   	   
       {
			"userId":"Player1",
			"color":"RED",
			"column":1
   		}
   	
	# v) Game Outcome
    
    	This call will fetch the game outcome
    	
    	Method: GET
    	
    	http://localhost:8080/connect4/games/{gameId}/outcome
    	
   		
   	
   