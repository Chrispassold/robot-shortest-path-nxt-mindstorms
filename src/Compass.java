import java.util.ArrayList;
import java.util.List;


public class Compass {

	private List orientations = new ArrayList(4);
	private byte currentOrientation = 0;
	
	public Compass() {
		this.orientations.add(Orientation.NORTH);
		this.orientations.add(Orientation.EAST);
		this.orientations.add(Orientation.SOUTH);
		this.orientations.add(Orientation.WEST);
	}
	
	public void updateOrientation(String directionRotated) {
		if (directionRotated.equals(Direction.Rotate.LEFT)) {
			currentOrientation--;
			if (this.currentOrientation < 0) this.currentOrientation = 3;
		} else if (directionRotated.equals(Direction.Rotate.RIGHT)) {
			currentOrientation++;
			if (this.currentOrientation > 3) this.currentOrientation = 0;
		}
	}
	
	public String getCurrentOrientation() {
		return (String) orientations.get(this.currentOrientation);
	}
	
}
