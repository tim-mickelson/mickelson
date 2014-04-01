package com.thirdbase.challenge.maze.tim;

import java.util.ArrayList;
import java.util.List;

import com.thirdbase.challenge.maze.Cursor;
import com.thirdbase.challenge.maze.Move;
import com.thirdbase.challenge.maze.Solver;

/**
 * The main idea of this class is to try to keep track of the coordinates passed and so try to choose the next
 * step in the most optimal way as to not cross if not strictly necessary. Surely it could improve more about
 * the crossing of already crossed coordinates but the time frame is not sufficient.
 * 
 * @author Tim Mickelson
 * @since 27/02/2014
 */
public class SolverImpl implements Solver {
	Coord coord = new Coord(0, 0);
	List<Coord> coords = new ArrayList<Coord>();
	List<Move> moves = new ArrayList<Move>();
	boolean startLeft = true;
	enum COMPASS{
		NORTH, SOUTH, EAST, WEST
	}

	/**
	 * Assume starting in a relative coordinate (0, 0) and assume facing EAST. All is relative so it is not really important.
	 * @author Tim Mickelson
	 * @since 27/02/2014
	 */
	@Override
	public List<Move> solve(Cursor cursor) {
		coords.add(coord);
		// Start at relative coords (0,0) with now wall behind
		COMPASS compass = COMPASS.EAST;
		// For each cycle choose the optimal direction to move and return the chose in an relative compass.
		while(!cursor.isFinished()){
			compass = optimalDirection(cursor, compass);
		} // end loop
		return moves;
	}  // end function solve
	

	/**
	 * Utility function to turn compass 90 degrees.
	 * @author Tim Mickelson
	 * @since 27/02/2014
	 * @param c Compass with current direction.
	 * @param left 90 degrees left, else right.
	 * @return New direction.
	 */
	private COMPASS turn(COMPASS c, boolean left){
		switch(c){
			case NORTH: c = left==true?COMPASS.WEST:COMPASS.EAST;
						break;
			case WEST: 	c = left==true?COMPASS.SOUTH:COMPASS.NORTH;
						break;
			case SOUTH:	c = left==true?COMPASS.EAST:COMPASS.WEST;
						break;
			case EAST:	c = left==true?COMPASS.NORTH:COMPASS.SOUTH;
						break;
		}
		return c;
	}  // end function turn
	
	/**
	 * Take one step in the compass direction and return the new coordinate.
	 * @author Tim Mickelson
	 * @since 27/02/2014
	 * @param direction Direction on compass to step.
	 * @param coord Current coordinate
	 * @return New coordinate after effectuating the step.
	 */
	private Coord move(COMPASS direction, Coord coord){
		Coord newCoord = null;
		switch(direction){
			case NORTH:	newCoord = new Coord(coord.getX(), coord.getY()+1);
						break; 
			case SOUTH: newCoord = new Coord(coord.getX(), coord.getY()-1);
						break;
			case EAST:	newCoord = new Coord(coord.getX()+1, coord.getY());
						break;
			case WEST:	newCoord = new Coord(coord.getX()-1, coord.getY());
						break;
		} // end switch
		
		return newCoord;
	}  // end private function move
	
	/**
	 * Assure if the requested coordinate has been visited in a previous step.
	 * @author Tim Mickelson
	 * @since 27/02/2014
	 * @param coord Test coordinate.
	 * @return <code>true</code> if previously visited
	 */
	private boolean visited(Coord coord){
		for(Coord c : coords){
			if(c.equals(coord))
				return true;
		}
		return false;
	}  // end function visited
	
	/**
	 * Just get the number of times a coordinate has been visited.
	 * @author Tim Mickelson
	 * @since 27/02/2014
	 * @param coord Test coordinate.
	 * @return Ammount of visits.
	 */
	private int weight(Coord coord){
		int weight = 0;
		for(Coord c : coords){
			if(c.equals(coord))
				weight = weight +1;
		}
		return weight;
	}  // end function weight

	private COMPASS move(boolean left, Cursor cursor, List<Weight> weights, Move move, COMPASS compass, COMPASS currentDirection){
		if(left){
			if(!cursor.wallToLeft()){
				compass = turn(currentDirection, true);
				Coord tmp = move(compass, coord);
				move = Move.TURN_LEFT;
				if(!visited(tmp)){
					coord = tmp;
					cursor.turnLeft();
					moves.add(Move.TURN_LEFT);
					cursor.step();
					moves.add(Move.STEP);
					coords.add(tmp);
					return compass;
				}else{
					Weight w = new Weight();
					w.coordinate = tmp;
					w.weight = weight(tmp);
					w.compass = compass;
					w.move = move;
					weights.add(w);
				}
			}
		}else{
			if(!cursor.wallToRight()){
				compass = turn(currentDirection, false);
				Coord tmp = move(compass, coord);
				move = Move.TURN_RIGHT;
				if(!visited(tmp)){
					coord = tmp;
					cursor.turnRight();
					moves.add(Move.TURN_RIGHT);
					cursor.step();
					moves.add(Move.STEP);
					coords.add(tmp);
					return compass;
				}else{
					Weight w = new Weight();
					w.coordinate = tmp;
					w.weight = weight(tmp);
					w.compass = compass;
					w.move = move;
					weights.add(w);
				}
			}
			
		}
			
		
		return compass;
	}

	/**
	 * Choose where to move and save the new coordinate, move and return the new direction.
	 * @author Tim Mickelson
	 * @since 27/02/2014
	 * @param cursor Maze cursor.
	 * @param coord Actual coordinate on maze.
	 * @param currentDirection The current direction the cursor is facing.
	 * @return The direction the cursor is facing after taking optimal step.
	 */
	private COMPASS optimalDirection(Cursor cursor, COMPASS currentDirection){
		COMPASS compass = null;
		Move move = null;
		List<Weight> weights = new ArrayList<Weight>();
		if(!cursor.wallInFront()){
			compass = currentDirection;
			move = Move.STEP;
			Coord tmp = move(compass, coord);
			if(!visited(tmp)){
				coord = tmp;
				coords.add(tmp);
				moves.add(move);
				cursor.step();
				return compass;
			}else{
				Weight w = new Weight();
				w.coordinate = tmp;
				w.weight = weight(tmp);
				w.compass = compass;
				w.move = move;
				weights.add(w);
			}
		}
		// To not always try first left, alternate
		compass = move(startLeft, cursor, weights, move, compass, currentDirection);
		startLeft = !startLeft;
/*		
		if(!cursor.wallToLeft()){
			compass = turn(currentDirection, true);
			Coord tmp = move(compass, coord);
			move = Move.TURN_LEFT;
			if(!visited(tmp)){
				coord = tmp;
				cursor.turnLeft();
				moves.add(Move.TURN_LEFT);
				cursor.step();
				moves.add(Move.STEP);
				coords.add(tmp);
				return compass;
			}else{
				Weight w = new Weight();
				w.coordinate = tmp;
				w.weight = weight(tmp);
				w.compass = compass;
				w.move = move;
				weights.add(w);
			}
		}
		if(!cursor.wallToRight()){
			compass = turn(currentDirection, false);
			Coord tmp = move(compass, coord);
			move = Move.TURN_RIGHT;
			if(!visited(tmp)){
				coord = tmp;
				cursor.turnRight();
				moves.add(Move.TURN_RIGHT);
				cursor.step();
				moves.add(Move.STEP);
				coords.add(tmp);
				return compass;
			}else{
				Weight w = new Weight();
				w.coordinate = tmp;
				w.weight = weight(tmp);
				w.compass = compass;
				w.move = move;
				weights.add(w);
			}
		}
*/		
		// Get max weight
		if(weights.size()==0){
			// Dead end return! turn twice left
			compass = turn(currentDirection, true);
			compass = turn(compass, true);
			cursor.turnLeft();
			moves.add(Move.TURN_LEFT);
			cursor.turnLeft();
			moves.add(Move.TURN_LEFT);
			cursor.step();
			moves.add(Move.STEP);
			return compass;
		}
		
		Weight min = weights.get(0);
		for(Weight w : weights){
			min = w.weight<min.weight?w:min;
		}
		coord = min.coordinate;
		compass = min.compass;
		coords.add(coord);
		if(min.move.equals(Move.STEP)){
			cursor.step();
			moves.add(Move.STEP);
		}else if(min.move.equals(Move.TURN_LEFT)){
			cursor.turnLeft();
			moves.add(Move.TURN_LEFT);
			cursor.step();
			moves.add(Move.STEP);
		}else{
			cursor.turnRight();
			moves.add(Move.TURN_RIGHT);
			cursor.step();
			moves.add(Move.STEP);
		}
		return compass;
	}
	
	/**
	 * Utility class
	 * @author Tim Mickelson
	 * @since 27/02/2014
	 */
	private class Weight{
		int weight;
		Coord coordinate;
		COMPASS compass;
		Move move;
	}
	
	

/**
 * Represents visited square.
 * @author Tim Mickelson
 *
 */
public class Coord {
	private int x;
	private int y;
	
	public Coord(int x, int y){
		this.x = x;
		this.y = y;
	}
	
    public boolean equals(Object o) {
        Coord c = (Coord) o;
        return c.x == x && c.y == y;
    }
    
    public int hashCode() {
        return new Integer(x + "0" + y);
    }

	
    public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}    

}	
}  // end class SolverImpl