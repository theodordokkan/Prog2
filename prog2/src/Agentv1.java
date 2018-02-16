import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Agentv1 implements Agent {

	private String role; // the name of this agent's role (white or black)
	private int playclock; // this is how much time (in seconds) we have before nextAction needs to return
							// a move
	private boolean myTurn; // whether it is this agent's turn or not
	private int width, height; // dimensions of the board
	State currentState;
	private boolean bottomed;
	private int nStates;
	/*
	 * init(String role, int playclock) is called once before you have to select the
	 * first action. Use it to initialize the agent. role is either "white" or
	 * "black" and playclock is the number of seconds after which nextAction must
	 * return.
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

		for (int i = 1; i <= width; i++) {
			whitepawns.add(new Point(i, 1));
			whitepawns.add(new Point(i, 2));
			blackpawns.add(new Point(i, height));
			blackpawns.add(new Point(i, height - 1));
		}

		currentState = new State("white", whitepawns, blackpawns);

	}

	// lastMove is null the first time nextAction gets called (in the initial state)
	// otherwise it contains the coordinates x1,y1,x2,y2 of the move that the last
	// player did
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
			// TODO: 1. update your internal world model according to the action that was
			// just executed
			currentState.update(roleOfLastPlayer, new Move(x1, y1, x2, y2));
		}

		// update turn (above that line it myTurn is still for the previous state)
		myTurn = !myTurn;
		if (myTurn) {
			Move bestMove = null;
			// TODO: 2. run alpha-beta search to determine the best move
			long timeout = System.currentTimeMillis() + (playclock * 1000);
			try {

				System.out.println("starting the search at depth 1");
				for (int d = 1; d < 100; d++) {
					nStates=0;
					System.out.println("search at depth " + d);
					Long startLoopTime = System.currentTimeMillis();
					bestMove = alfaBetaSearch(currentState, timeout, d);
					Long endLoopTime = System.currentTimeMillis();
					System.out.println("Depth searched done in: " + (endLoopTime - startLoopTime) + " ms and " + nStates
							+ " nodes");
					if (!bottomed)
						break;
				}
			} catch (TimeOutException e) {
				System.out.println("no more time, Get out, out, out .... ");
			}
			
			
			return "(move " + bestMove.fx + " " + bestMove.fy + " " + bestMove.tx + " " + bestMove.ty + ")";
		} else {
			return "noop";
		}
	}

	public Move alfaBetaSearch(State state, long timeout, int d) throws TimeOutException {
		int bestValue = Integer.MIN_VALUE;
		int value;
		Move bestMove = null;
		for (Move move : state.getLegalMoves(state.activerole)) {
		//	System.out.println(move);
			value = minValue(getNext(state, move), 0, 100, timeout, d);
			if (value > bestValue) {
				bestValue = value;
				bestMove = move;
			}

		}
		return bestMove;
	}

	private int minValue(State state, int alfa, int beta, long timeout, int d) throws TimeOutException {
		int bestValue = Integer.MAX_VALUE;
		int value;
		nStates++;
		if (System.currentTimeMillis() + 100 >= timeout)
			throw new TimeOutException();

		if (state.isTerminal())
			return state.evalState();

		if (d == 0) {
			bottomed = true;
			return 42;
		}

		for (Move move : state.getLegalMoves(state.activerole)) {
		//	System.out.println(move);
			value = maxValue(getNext(state, move), alfa, beta, timeout, d - 1);
			bestValue = Math.min(value, bestValue);

			if (bestValue <= alfa)
				return bestValue;
			beta = Math.min(beta, bestValue);

		}
		return bestValue;

	}

	private int maxValue(State state, int alfa, int beta, long timeout, int d) throws TimeOutException {
		int bestValue = Integer.MIN_VALUE;
		int value;
		nStates++;
		if (System.currentTimeMillis() + 100 >= timeout)
			throw new TimeOutException();

		if (state.isTerminal())
			return state.evalState();

		if (d == 0) {
			bottomed = true;
			return 42;
		}

		for (Move move : state.getLegalMoves(state.activerole)) {
			//System.out.println(move);
			value = minValue(getNext(state, move), alfa, beta, timeout, d - 1);
			bestValue = Math.max(value, bestValue);

			if (bestValue >= beta)
				return bestValue;
			alfa = Math.max(alfa, bestValue);

		}
		return bestValue;
	}

	// is called when the game is over or the match is aborted
	@Override
	public void cleanup() {
		// TODO: cleanup so that the agent is ready for the next match
	}

	public State getNext(State state, Move move) {
		State newState = (State) state.clone();
		newState.update(state.activerole, move);
		return newState;
	}
}
