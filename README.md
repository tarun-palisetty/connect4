# Connect4 Game - Implementation Using Spring Boot and Rest
   This is a Spring boot application which exposes REST services for connect4 game play.


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
			"color":"RED"
   		}
   
   
# ii) Get Game
       
       This call returns the game associated with gameId
   
   		Method: GET
   
   		http://localhost:8080/connect4/games/{gameId}
   		
   		Here gameId is the path parameter, for testing this gameId can be captured from the above JSON response
   
# iii) Join Game
	
	     This call allows the player2 to join the game created by player1 using the gameId
	
		 Method: PUT
	
		 http://localhost:8080/connect4/games/{gameId}
   
   		JSON Request body:
   		
   		{
			"userId":"Player2"
   		}
   		
   		** The player2 disc color is automatically assigned based on the player1 ones selection.
   
# iv) Play Game
     
       This call allows the Players in the game to drop their disc in each turn, No player can drop two discs consecutively and
       this result 409 HTTP error code. And also the method can result in WINNER during the play with Player details in the response.
       
       Method: PUT
       
       http://localhost:8080/connect4/games/{gameId}/discs
   
   	   JSON Request body:
   	   
       {
			"userId":"Player1",
			"column":1
   		}
   		
   	
   	
# v) Game Outcome
    
    	This call will fetch the game outcome
    	
    	Method: GET
    	
    	http://localhost:8080/connect4/games/{gameId}/outcome

#4. Validations
    There are different validations put in place with user friendly messages and HTTP response code.
    	
   	
#5. JUnits
    Test cases have been implemented for different edge cases scenarios of Connect4 game play.
   	
   