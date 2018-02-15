import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Agentv1 implements Agent {

	private String role; // the name of this agent's role (white or black)
	private int playclock; // this is how much time (in seconds) we have before nextAction needs to return a move
	private boolean myTurn; // whether it is this agent's turn or not
	private int width, height; // dimensions of the board
	State currentState;
	/*
		init(String role, int playclock) is called once before you have to select the first action. Use it to initialize the agent. role is either "white" or "black" and playclock is the number of seconds after which nextAction must return.
	*/
	
	
	
    public void init(String role, int width, int height, int playclock) {
		this.role = role;
		this.playclock = playclock;
		myTurn = !role.equals("white");
		this.width = width;
		this.height = height;
		// TODO: add your own initialization code here
		List<Point> whitepawns = new ArrayList<Point>(); 
		List<Point> blackpawns = new ArrayList<Point>();
		
		for (int i = 0; i < width; i++ ) {
			whitepawns.add(new Point(i, 1));
			whitepawns.add(new Point(i, 2));
			blackpawns.add(new Point(i, height));
			blackpawns.add(new Point(i, height-1));
		}
		
		currentState = new State ("white", whitepawns, blackpawns);
		
		
		
		
    }

	// lastMove is null the first time nextAction gets called (in the initial state)
    // otherwise it contains the coordinates x1,y1,x2,y2 of the move that the last player did
    public String nextAction(int[] lastMove) {
    	if (lastMove != null) {
    		int x1 = lastMove[0], y1 = lastMove[1], x2 = lastMove[2], y2 = lastMove[3];
    		String roleOfLastPlayer;
    		if (myTurn && role.equals("white") || !myTurn && role.equals("black")) {
    			roleOfLastPlayer = "white";
    		} else {
    			roleOfLastPlayer = "black";
    		}
   			System.out.println(roleOfLastPlayer + " moved from " + x1 + "," + y1 + " to " + x2 + "," + y2);
    		// TODO: 1. update your internal world model according to the action that was just executed
    		currentState.update(roleOfLastPlayer, x1, y1, x2, y2);
    	}
		
    	// update turn (above that line it myTurn is still for the previous state)
		myTurn = !myTurn;
		if (myTurn) {
			// TODO: 2. run alpha-beta search to determine the best move

			// Here we just construct a random move (that will most likely not even be possible),
			// this needs to be replaced with the actual best move.
			
			
			int x1 = 1, y1 = 1, x2 = 1, y2= 1;
			return "(move " + x1 + " " + y1 + " " + x2 + " " + y2 + ")";
		} else {
			return "noop";
		}
	}

    public Move alfaBetaSearch (State state , int alfa, int beta) {
    	int v = Integer.MIN_VALUE;
    	int value;
    	Move bestMove = null;
    	for (Move move : state.getLegalMoves(role)) {
    		value = minValue(getNext(state, move), Integer.MIN_VALUE, Integer.MAX_VALUE);
    		if (value > v) {
    			v = value;}
    			bestMove = move;
    			if ( v >= beta) {
    				alfa = Math.max(alfa, v);
    				
    			}
    			
    		}
    		return bestMove;
    }
    
	private int minValue(State state, int alfa, int beta) {
    	int v = Integer.MAX_VALUE;
    	int value;
    	for (Move move : state.getLegalMoves(role)) {
    		value = maxValue(getNext(state, move), Integer.MIN_VALUE, Integer.MAX_VALUE);
    		if (value < v) {
    			v = value;}
    		
    			if ( v >= alfa) {
    				beta = Math.max(beta, v);
    				
    			}
    			
    		}
    		return v;
		
	}

	private int maxValue(State state, int alfa, int beta) {
		int v = Integer.MIN_VALUE;
    	int value;
    	for (Move move : state.getLegalMoves(role)) {
    		value = minValue(getNext(state, move), Integer.MIN_VALUE, Integer.MAX_VALUE);
    		if (value > v) {
    			v = value;}
    			if ( v >= beta) {
    				alfa = Math.max(alfa, v);
    				
    			}
    			
    		}
    		return v;
	}

	// is called when the game is over or the match is aborted
	@Override
	public void cleanup() {
		// TODO: cleanup so that the agent is ready for the next match
	}
	
	public State getNext(State state, Move move) {
		State newState = (State) state.clone();
		newState.update(state.activerole, move.fx, move.fy, move.tx, move.ty);
		return newState;
	}
}
