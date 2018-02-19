
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class State {

	Set<Point> whitepawns;// Do not really need the last part
	Set<Point> blackpawns; // Do not really need the last part

	public String activerole;
	public int width, height;

	public State(String activerole, Set<Point> whitepawns, Set<Point> blackpawns, int width, int height) {

		this.activerole = activerole;
		this.whitepawns = whitepawns;
		this.blackpawns = blackpawns;
		this.width = width;
		this.height = height;

	}

	public List<Move> getLegalMoves(String role) {
		Set<Point> rolepawns;
		Set<Point> opponentpawns;
		List<Move> legal = new ArrayList<>();

		int direction;

		if (role.equals("white")) {
			rolepawns = whitepawns;
			direction = 1;
			opponentpawns = blackpawns;

			for (Point position : whitepawns) { // if whitepawns reach top
				if (position.y == height) {
					return new ArrayList<>();
				}

			}

		} else {
			rolepawns = blackpawns;
			direction = -1;
			opponentpawns = whitepawns;

			for (Point position : blackpawns) { // if blackpanws reach bottom
				if (position.y == 1) {
					return new ArrayList<>();
				}

			}
		}

		for (Point position : rolepawns) {
			Point portside = new Point(position.x - 1, position.y + (direction)); // left
			Point middle = new Point(position.x, position.y + (direction)); // move infront
			Point starboard = new Point(position.x + 1, position.y + (direction)); // right from direction

			if (opponentpawns.contains(portside)) { // move there and capture
				legal.add(new Move(position.x, position.y, portside.x, portside.y));
			}

			if (opponentpawns.contains(starboard)) { // move there and capture
				legal.add(new Move(position.x, position.y, starboard.x, starboard.y));
			}
			if (!opponentpawns.contains(middle)) { // we can't move there and capture

				if (!rolepawns.contains(middle)) { // if contains one of your pawns

					legal.add(new Move(position.x, position.y, middle.x, middle.y));
				}

			}

		}
		return legal;
	}

	public boolean isTerminal() {

		List<Move> black = getLegalMoves("black");
		List<Move> white = getLegalMoves("white");

		return black.isEmpty() || white.isEmpty();

	}

	public int evalState(String role) {
		int whiteMulti = 1, blackMulti = 1;
		if (role.equals("black")) {
			whiteMulti = -1;
		} else {
			blackMulti = -1;
		}
		if (isTerminal()) {

			for (Point position : whitepawns) { // if whitepawns reach top
				if (position.y == height) {
					return 100*whiteMulti;
				}

			}
			for (Point position : blackpawns) { // if blackpanws reach bottom
				if (position.y == 1) {
					return 100*blackMulti;
				}

			}
			return 0; // draw return
		} else {
			int nBlack, nWhite, factor;
			nBlack = blackpawns.size();
			nWhite = whitepawns.size();
			factor = 90 / height;

			int max = 0;
			int min = height;
			for (Point position : whitepawns) { // if whitepawns reach top
				if (position.y > max)// highest y
				{
					max = position.y;
				}

			}
			for (Point position : blackpawns) { // if blackpanws reach bottom
				if (position.y < min) { // lowest y
					min = position.y;
				}

			}
			int bdist = (min - 1);
			int wdist = (height - max);

			if (role.equals("black")) {
				return (nBlack - nWhite) + (((height - bdist) * factor) - ((height - wdist) * factor));
			} else {
				return (nWhite - nBlack) + (((height - wdist) * factor) - ((height - bdist) * factor));
			}

		}
	}

	public void update(String role, Move move) {

		if (role.equals("black")) {
			blackpawns.remove(new Point(move.fx, move.fy));
			blackpawns.add(new Point(move.tx, move.ty));
			activerole = "white";
		} else {
			whitepawns.remove(new Point(move.fx, move.fy));
			whitepawns.add(new Point(move.tx, move.ty));
			activerole = "black";
		}

	}

	public State clone() {
		return new State(activerole, new HashSet<Point>(whitepawns), new HashSet<Point>(blackpawns), width, height);
	}

}
