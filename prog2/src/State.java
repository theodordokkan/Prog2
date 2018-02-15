
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class State {

    List<Point> whitepawns = new ArrayList<Point>(); // Do not really need the last part
    List<Point> blackpawns = new ArrayList<Point>(); // Do not really need the last part

    public String activerole;
    public int width, height;

    public State(String activerole, List<Point> whitepawns, List<Point> blackpawns) {
        this.activerole = activerole;
        this.whitepawns = whitepawns;
        this.blackpawns = blackpawns;

    }

    public List<Move> getLegalMoves(String role) {
        List<Point> rolepawns;
        List<Point> opponentpawns;
        List<Move> legal = new ArrayList<>();

        int direction;

        if (role.equals("white")) {
            rolepawns = whitepawns;
            direction = 1;
            opponentpawns = blackpawns;

        } else {
            rolepawns = blackpawns;
            direction = -1;
            opponentpawns = whitepawns;
        }
        for (Point position : rolepawns) {
            Point portside = new Point(position.x - 1, position.y + (direction)); //left
            Point middle = new Point(position.x, position.y + (direction)); //move infront
            Point starboard = new Point(position.x + 1, position.y + (direction)); //right from direction

            if (opponentpawns.contains(portside)) { //move there and capture
                legal.add(new Move(position.x, position.y, portside.x, portside.y));
            }

            if (opponentpawns.contains(starboard)) { //move there and capture
                legal.add(new Move(position.x, position.y, starboard.x, starboard.y));
            }
            if (!opponentpawns.contains(middle)) { //we can't move there and capture

                if (!rolepawns.contains(middle)) { //if contains one of your pawns

                    legal.add(new Move(position.x, position.y, middle.x, middle.y));
                }

            }

        }
        return legal;
    }

    public boolean isTerminal() {

        List<Move> black = getLegalMoves("black");
        List<Move> white = getLegalMoves("white");

        return black.isEmpty() && white.isEmpty();

    }

    public int evalState() {
        if (isTerminal()) {
            if (blackpawns.isEmpty()) //if theres no blackpawns, white wins
            {
                return 100;
            }
            if (whitepawns.isEmpty()) //no whitepawns, black wins
            {
                return -100;
            }

            //need to solve the draw logic
            for (Point position : whitepawns) { //if whitepawns reach top
                if (position.y == height) {
                    return 100;
                }

            }
            for (Point position : blackpawns) { //if blackpanws reach bottom
                if (position.y == 1) {
                    return -100;
                }

            }
            return 0; // draw return
        } else {
            int max = 0;
            int min = height;
            for (Point position : whitepawns) { //if whitepawns reach top
                if (position.y > max)//highest y
                {
                    max = position.y;
                }

            }
            for (Point position : blackpawns) { //if blackpanws reach bottom
                if (position.y < min) { //lowest y
                    min = position.y;
                }

            }
            return (min - 1) - (height - max);
        }
    }

    
    public void update (String role, int fx, int fy, int tx, int ty) {
    	if (role.equals("black")) {
    		int index = blackpawns.indexOf(new Point(fx, fy));
    		blackpawns.remove(index);
    		blackpawns.add(new Point (tx, ty));
    	}
    	else {
    		int index = whitepawns.indexOf(new Point(fx, fy));
    		whitepawns.remove(index);
    		whitepawns.add(new Point (tx, ty));
    	}
    }
    
    public State clone () {
    	return new State(activerole, new ArrayList<Point>(whitepawns)
    			, new ArrayList<Point>(blackpawns));
    }

	
    
}
