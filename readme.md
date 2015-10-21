Game 2048:
2048 is a single-player puzzle game in which the objective is to slide numbered tiles on a grid 
to combine them and create a tile with the number 2048. This project is an implemention of the game 
mechanics in Java. 

Rules of 2048
�The game board is a 4�4 grid of tiles, each tile is either occupied with a number, or empty.
�The game board is initialized with two randomly placed tiles, each of which can be either 2 (90% probability) or 4 (10% probability).
�You may move in one of four directions: up, left, down, right.
�You may only move in a given direction if the move would change the state of the board.
�While moving, the tiles will slide as far as possible in the chosen direction until they are stopped by another tile or the edge of the grid. If two tiles of the same number collide while moving, they will merge into a tile with the total value of the two tiles that collided. The resulting tile cannot merge with another tile again in the same move.
�After moving, a new tile is set to either 2 (90% probability) or 4 (10% probability) at a random empty location.
�If a 2048 tile appears anywhere on the board, the game is won.
�If all tiles have numbers, and there is no valid move, the game is lost.

Author of the solution: Monir Zaman
Email: mznDOTmunnaATgmailDOTcom

Overview of the solution:
Some important classes and their methods are described as the following: 

Class Play: Contains the workflow of the game. It displays the board,
		    takes user input and then slide the board. Process continues until
		    user enters quit.
		    Method slideTiles slide all the tiles of the entire board to the user's
		    specified direction. It uses the merge method which is a recursive method.
		    merge method merges value s to e and call itself for e and the previous position of e.
            nextPos and prevPos return next position of the current cell in opposite 
            direction from each other.
  

class Board: contains data structure and methods to store and maintain the board.
			 An NbyN array is used as the board. A hash map is used to map values to
			 the cells where they apprear. 
			 Any cell address in the board is represented in two ways: using Position class
			 instance that contains row and column info. The second way combines the row and
			 column number using arithmetic. compressCellReference method performs this operation 
			 while decompressCellReference converts a single number to a Position object.
			 
class DirectionStartEndPoints: stores information such as start and end position for 
             different move directions. For example, for right direction, we start at
             cell 0,3 and end at 3,0.