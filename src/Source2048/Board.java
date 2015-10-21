package Source2048;

/*
 * Author: Monir Zaman
 * Email: mznDOTmunnaATgmailDOTcom
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

/*
 * class Board contains data structure and simple helper 
 * methods to store and maintain the board
 */
public class Board implements BoardConstants{
	private long[][] grid;
	public static final long EMPTY_VALUE=0;
	public static final int WIN_VALUE=2048;
	
	/*
	 * A HashMap valueToCellIndex is used to store what values are in which cells;
	 * Key in the hash map is value and value is a HashSet containing
	 * the cells that contain the value.
	 * Each cell reference is converted to a single number using
	 * (10*row num+ column num) formula.
	 */
	private HashMap<Long, ArrayList<Integer>> valueToCellIndex;
	
	public Board(){
		grid=new long[N][N];
		valueToCellIndex=new HashMap<Long, ArrayList<Integer>>();
		
		//initialize the grid and its valueToCell index
		ArrayList<Integer> hs=new ArrayList<Integer>();
		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++){
				grid[i][j]=EMPTY_VALUE;
				hs.add(compressCellReference(i,j));
			}
		}
		//At this point, all the cells contain 0 or empty
		valueToCellIndex.put(EMPTY_VALUE,hs);
		
		//random assignment at two positions
		placeAValue();
		placeAValue();
	}
	
	
	
	public int compressCellReference(int row, int col){
		/*
		 * Converts and returns a cell reference to a single number using
		 * (10*row num+ column num) formula.
		 */
		return (10*row)+col;
	}
	
	public Position decompressCellReference(int compressedCellRef){
		/*
		 * Separates out the row and column number from compressedCellRef
		 * and creates a Position object with the row and column number 
		 * and finally returns the Position object.
		 */
		return new Position(compressedCellRef/10, compressedCellRef%10);
	}
	
	public boolean emptyCell(Position p){
		/*
		 * Returns true if board contains 0 at position p
		 */
		return grid[p.getR()][p.getC()]==EMPTY_VALUE;
	}

	public boolean sameValue(Position s, Position e){
		/*
		 * Returns true if grid has the same value at s and e
		 */
		return grid[s.getR()][s.getC()]==grid[e.getR()][e.getC()];
	}

	public void combine(Position s, Position e){
		/*
		 * Adds the value of the cell at s to the value of the cell at e.
		 * Make the cell at s empty.
		 */
		long oldValueAtS=grid[s.getR()][s.getC()];
		long oldValueAtE=grid[e.getR()][e.getC()];
		
		grid[e.getR()][e.getC()]=grid[e.getR()][e.getC()]+grid[s.getR()][s.getC()];
		grid[s.getR()][s.getC()]=EMPTY_VALUE;
		
		//check for the winning score
		if(grid[e.getR()][e.getC()]==WIN_VALUE){
			System.out.println("You won!");
			System.exit(0);
		}
			
		//update the valueToCellIndex to reflect the changes
		long newValueAtE=grid[e.getR()][e.getC()];
		updateIndex(s,oldValueAtS, e, oldValueAtE, newValueAtE);
	}
	
	
	public void updateIndex(Position s, long oldValueAtS, Position e, long oldValueAtE, long newValueAtE){
		/*
		 * Updates the valueToCellIndex as values in the grid changes 
		 * s is added to empty cell list
		 * s should also be removed from oldValueAtS list
		 * e is added to the list of cells that contain newValueAtE
		 * e should also be removed from oldValueAtE list
		 */
		int sc=compressCellReference(s.getR(),s.getC());
		int ec=compressCellReference(e.getR(),e.getC());
		
		removeFromIndex(oldValueAtS,sc);
		addToIndex(EMPTY_VALUE,sc);
		
		removeFromIndex(oldValueAtE,ec);
		addToIndex(newValueAtE,ec);
	}
	
	public void removeFromIndex(long value, int compressedCellRef){
		/*
		 * Remove compressedCellRef from the list for the key value
		 */
		ArrayList<Integer> lst=valueToCellIndex.get(value);
		lst.remove(lst.indexOf(compressedCellRef));
		
	}
	
	public void addToIndex(long value, int compressedCellRef){
		/*
		 * Checks if compressedCellRef is in the list for the key value;
		 * If not, then add it to the list
		 */
		if(valueToCellIndex.containsKey(value))
			valueToCellIndex.get(value).add(compressedCellRef);
		else
			valueToCellIndex.put(value, new ArrayList<Integer>(Arrays.asList(compressedCellRef)));
	}

	public int probabilisticValueSelection(){
		/* 
		 * Randomly select either 2 or 4. Probability distribution is .9 and .1
		 * and return the selection. 
		 */
		int[] numbers={2,4};
		int MAX_NUM=10;
		
		//generate a random number between 1 (inclusive) and 10 (inclusive)
		int rn=1+new Random().nextInt(MAX_NUM);
		
		return numbers[rn/MAX_NUM]; //integer division
	}
	
	public void placeAValue(){
		/*
		 * Generate a value using probabilisticValueSelection and 
		 * place the value at a randomly selected empty cell;
		 * Also updates valueToCellIndex with the newly added value 
		 */
		
		ArrayList<Integer> emptyCells=valueToCellIndex.get(EMPTY_VALUE);
		if(emptyCells.size()==0){
			System.out.println("No empty cell to place the value");
			return;
		}
		
		//---randomly select a cell
		//(a) randomly select an index
		int indx=new Random().nextInt(emptyCells.size());
		//(b) retrieve the compressed cell reference and convert it to Position
		int compressedCellRef=emptyCells.get(indx);
		Position p=decompressCellReference(compressedCellRef);
		
		//---place value
		long value=probabilisticValueSelection();
		grid[p.getR()][p.getC()]=value;
		
		//---update the index
		//(1/2)Remove the selected cell from empty cell list
		emptyCells.remove(indx);
		
		//(2/2)Add the selected cell to the list of cells that contain value
		if(valueToCellIndex.containsKey(value))
			valueToCellIndex.get(value).add(compressedCellRef);
		else
			valueToCellIndex.put(value, new ArrayList<Integer>(Arrays.asList(compressedCellRef)));
		
		
	}

	
	public void display(){
		System.out.println(" Board");
		//display the board
		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++){
				if(grid[i][j]==EMPTY_VALUE)
					System.out.print("_");//empty cell
				else
					System.out.print(grid[i][j]);
				System.out.print(" ");//separate each cell by space
			}
			System.out.println();//insert new line between rows
		}
	}

	public void displayIndex(){
		//used for debugging purpose
		System.out.println(valueToCellIndex);
	}
}
