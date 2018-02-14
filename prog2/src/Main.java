
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;


public class Main {
	
	/**
	 * starts the game player and waits for messages from the game master <br>
	 * Command line options: [port]
	 */
	public static void main(String[] args){
		/* try{
			// TODO: put in your agent here
			Agent agent = new Agentv1();

			int port=4001;
			if(args.length>=1){
				port=Integer.parseInt(args[0]);
			}
			GamePlayer gp=new GamePlayer(port, agent);
			gp.waitForExit();
		}catch(Exception ex){
			ex.printStackTrace();
			System.exit(-1);
		}
                */
                State currentState;
                
                List<Point> whitepawns = new ArrayList<Point>(); 
		List<Point> blackpawns = new ArrayList<Point>();
		
		for (int i = 0; i < 2; i++ ) {
			whitepawns.add(new Point(i, 1));
			whitepawns.add(new Point(i, 2));
			blackpawns.add(new Point(i, 4));
			blackpawns.add(new Point(i, 4-1));
		}
		
		currentState = new State ("white", whitepawns, blackpawns);
		
		List<Move> whiteLegal = currentState.getLegalMoves("white");
		
                
                 for (Move position : whiteLegal) { //if blackpanws reach bottom
               System.out.println(position.toString());
                }

                
                
	}
}
