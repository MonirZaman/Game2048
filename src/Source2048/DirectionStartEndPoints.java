package Source2048;

import java.util.HashMap;
/*
 * class DirectionStartEndPoints: stores information such as start and end position for 
   different move directions. For example, for right direction, we start at
   cell 0,3 and end at 3,0. 
 */

public class DirectionStartEndPoints implements BoardConstants {
	
		private HashMap<String, Position> startPos;
		private HashMap<String, Position> lastPos;
		
		public DirectionStartEndPoints(){
		
			//----initialize start points for different directions
			startPos=new HashMap<String, Position>();
			//lR is the last Row's index of the board and fR is the first Row's index
			//Note that board is a square
			startPos.put("left", new Position(fR,fR));
			startPos.put("right", new Position(fR,lR));
			startPos.put("up",new Position(fR,fR));
			startPos.put("down",new Position(lR,fR));
			
			//----initialize last points for different directions
			lastPos=new HashMap<String, Position>();
			lastPos.put("left", new Position(lR,lR));
			lastPos.put("right", new Position(lR,fR));
			lastPos.put("up",new Position(lR,lR));
			lastPos.put("down",new Position(fR,lR));
		}
		
		public Position getStartPos(String direction){
			//Returns the start position for the move direction
			return startPos.get(direction);
		}
		
		public Position getEndPos(String direction){
			//Returns the end position for the move direction
			return lastPos.get(direction);
		}
		
	
}
