import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class state {

	List<Point> whitepawns = new ArrayList<Point>(); // Do not really need the last part
	List<Point> blackpawns = new ArrayList<Point>(); // Do not really need the last part
	
	public String activerole;
	
	public state(String activerole, List<Point> whitepawns, List<Point> blackpawns) {
		this.activerole = activerole;
		this.whitepawns = whitepawns;
		this.blackpawns = blackpawns;
		
	}
	
	public void getLegalMoves(String role) {
		List<Point> rolepawns;
		List<Point> opponentpawns;
		
		int direction;
		
		if (role.equals("white")) {
			rolepawns = whitepawns;
			direction = 1;
			opponentpawns = blackpawns;
			
		}
		else {
			rolepawns = blackpawns;
			direction = -1;
			opponentpawns = whitepawns;
		}
		for(Point position:rolepawns) {
			Point portside = new Point(position.x-1, position.y+(direction));
			Point middle = new Point(position.x, position.y+(direction));
			Point starboard = new Point(position.x+1, position.y+(direction));
			opponentpawns.contains(portside)
		}
	}
	
}
