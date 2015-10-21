package Source2048;

import java.util.HashMap;


/*
 * class Position represents a position in the board in the
 * form of row and column number. It also contains method to
 * calculate next position based on the move direction.
 */

public class Position {
	private int r,c;
	
	public Position(int r, int c){
		this.r=r;
		this.c=c;
	}
	
	public boolean equal(Position p){
		/*
		 * Returns true if the row and column of the current position matches
		 * the same of the argument position p
		 */
		return this.r==p.getR() && this.c==p.getC();
	}
	
	public int getR() {
		return r;
	}

	public int getC() {
		return c;
	}

	public void setR(int r) {
		this.r = r;
	}

	public void setC(int c) {
		this.c = c;
	}
	public String toString(){
		return this.r+","+this.c;
	}
}
