import java.awt.Point;
import java.util.List;

public class Move implements Comparable {
	public Integer value = null;
	int fx, fy, tx, ty;

	public Move(int fx, int fy, int tx, int ty) {
		this.fx = fx;
		this.fy = fy;
		this.tx = tx;
		this.ty = ty;
	}

	public String toString() {
		return String.format("Move from %d:%d  to %d:%d", fx, fy, tx, ty);
	}

	@Override
	public int compareTo(Object arg0) {
		Move comp = (Move) arg0;

		return this.value.compareTo(comp.value);

		// TODO Auto-generated method stub
	}
}
