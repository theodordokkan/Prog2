import java.awt.Point;
import java.util.List;

public class Move {
	int fx,fy,tx,ty;

    public Move(int fx, int fy, int tx, int ty) {
        this.fx = fx;
        this.fy = fy;
        this.tx = tx;
        this.ty = ty;
    }
    public String toString(){
        return String.format("Move from %d:%d  to %d:%d",fx,fy,tx,ty);
    } 
}
