package Source2048;

/*
 * Author: Monir Zaman
 * Email: mznDOTmunnaATgmailDOTcom
 */
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

/*
 * class Play contains the workflow of the game.
 * In addition, it calculates the immediate next position of the current position (nextPos method) 
 * It slides the tile of the entire board based on input direction (slideTiles method)
 * It also contains several other methods.
 */
public class Play implements BoardConstants {
	
	private DirectionStartEndPoints posConstraint;
	private Board board;
	
	public Play(){
		//Constructor
		posConstraint=new DirectionStartEndPoints();
		board=new Board();
	}
	
	public void start(){
		/*
		 * It is the Workflow method of the game. It displays the board,
		 * takes user input and then slide the board. Process continues until
		 * user enters quit
		 * 
		 */
		System.out.println("Let's play 2048 Game");
		while(true){
			displayBoard();
			String dir=userInput();
			if(dir.equalsIgnoreCase("quit"))
				return;
			slideTiles(dir);
			
		}
	}
	
	public String userInput(){
		/* Asks and returns user's input*/
		Scanner keyb=new Scanner(System.in);
		System.out.println("Type your move. Valid moves are left, right,up, down, quit.");
		String move= keyb.nextLine().trim().toLowerCase();
		//Time permitted, I would compare if the move is valid. if not, an exception would be thrown.
		return move;
	}
	
	public Position nextPos(Position p, String dir)
			throws IllegalArgumentException{
		/*
		 * Returns the immediate next position from the current position p
		 * based on the movement direction dir
		 */
		
		//return null when p is null or p is already the last position for direction dir
		if(p==null || posConstraint.getEndPos(dir).equal(p))
			return null;

		//row and column
		int r=p.getR(); 
		int c=p.getC();
		
		//find the next position based on direction
		if(dir.equalsIgnoreCase("left")){
			/*
			 * When moving left, we start at the left most cell of a row and move to 
			 * the right next cell one at a time. Traversal is row major meaning that 
			 * once a row is traversed, we move on the next row.
			 * Based on this workflow, we calculate the next cell position.
			 */
			r=r+((c+1)/N); //integer division
			c=(c+1)%N;
		}
		else if(dir.equalsIgnoreCase("right")){
			//Traversal is row major
			r=r+((N-c)/N);
			c=(N+c-1)%N;
		}
		else if(dir.equalsIgnoreCase("up")){
			//Traversal is column major
			c=c+((r+1)/N);
			r=(r+1)%N;
		}
		else if(dir.equalsIgnoreCase("down")){
			//Traversal is column major
			c=c+((N-r)/N);
			r=(N+r-1)%N;
		}
		else{
			throw new IllegalArgumentException("invalid direction");
		}
		
		return new Position(r,c);
	}

	public Position prevPos(Position p, String dir){
		/*
		 * prevPos is used by merger method to find out the next position 
		 * to recurse on. Returns a Position object. returns null 
		 * when boundary exceeds.
		 *  
		 */
			
		//row and column
		int r=p.getR(); 
		int c=p.getC();
		
		//find the next position based on direction
		if(dir.equalsIgnoreCase("left")){
			if(c==0)
				//whether boundary exceeds
				return null;
			c--;
		}
		else if(dir.equalsIgnoreCase("right")){
			if(c==N-1)
				return null;
			c++;
		}
		else if(dir.equalsIgnoreCase("up")){
			if(r==0)
				return null;
			r--;
		}
		else if(dir.equalsIgnoreCase("down")){
			if(r==N-1)
				return null;
			r++;
		}
		return new Position(r,c);
	}
	
	public void slideTiles(String dir){
		/*
		 * Slide the entire board to the argument direction dir.
		 * Merge the tiles if numbers are same. Traversal overview:
		 * 
	     * When moving left, we start at the left most cell of a row and move to 
		 * the right next cell one at a time. Traversal is row major meaning that 
		 * once a row is traversed, we move on the next row.
	     * Based on this workflow, we calculate the next cell position.
			 
		 * When moving down, we start at the bottom most cell of the first column
		 * and gradually go up the column. Once a column is traversed, we start
		 * the bottom most cell of the next column. This traversal is column major.
		 * 
		 *  Similar process for right and up.
		 */
		
		/*Although a merged value stops sliding, a non-merged value 
		 * can slide to a merged value and get merged with it. HashSet is used
		 * to keep track of what cell contains a merged value.
		 */
		HashSet<Integer> hs=new HashSet<Integer>();
		
		Position toCell=posConstraint.getStartPos(dir);
		Position fromCell;
		int count=1;
		while((fromCell=nextPos(toCell,dir))!=null){
			//counting how many fromCell has been calculated
			++count; 
			
			merge(fromCell,toCell,dir,hs);
			
			//update toCell
			toCell=fromCell;
			
			//after calculating fromCell N times, do a jump to the next row/column
			//depending on the direction
			if(count%N==0){
				toCell=nextPos(toCell,dir);
				count=1;
			}
		}
		
		//at the end of the move, place a value on the grid
		board.placeAValue();
		
	}

	public void merge(Position source, Position dest, String dir, HashSet<Integer> hs){
		/*
		 * Recursive method. Attempt to move tile at source to dest. Then call itself
		 * for dest and next position of dest.
		 * Argument dir is used to find the next position of dest.
		 */
		
		if(source!=null && dest!=null){
			int destCompressedCellRef=board.compressCellReference(dest.getR(),dest.getC());
			
			if(this.board.emptyCell(dest)){
				/*combine method moves the value from source to dest; 
				  source becomes empty*/
				this.board.combine(source,dest);
				
				//recursive call
				merge(dest,prevPos(dest,dir),dir,hs);
				
			}
			else if(this.board.sameValue(source,dest) && !hs.contains(destCompressedCellRef)){
				//when source and dest both have the same value then combine.
				this.board.combine(source,dest);
				//mark dest as it now contains merged value
				hs.add(destCompressedCellRef);
				
				/*recursive call is not necessary as a combined value cannot get
				 * further combined*/ 
				 
			}
		}
	}

	public void displayBoard(){
		this.board.display();
	}
}
