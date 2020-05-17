import lejos.nxt.NXTRegulatedMotor;

public class Robot {

	private final NXTRegulatedMotor leftMotor;
	private final NXTRegulatedMotor rightMotor;
	private final MechanicalArm mechanicalArm;
	private final EndPathDetector endDetector;
	private Compass virtualCompass = new Compass();
	
	public Robot(NXTRegulatedMotor leftMotor, NXTRegulatedMotor rightMotor, MechanicalArm mechanicalArm, EndPathDetector endDetector) {
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
		this.mechanicalArm = mechanicalArm;
		this.endDetector = endDetector;
	}
	
	public void move(String direction) {
		if (direction.equals(Direction.Move.FRONT)) {
			leftMotor.rotate(RobotSpecs.SPEED, true);
			rightMotor.rotate(RobotSpecs.SPEED);
		} else if (direction.equals(Direction.Move.BACK)) {
			leftMotor.rotate(-RobotSpecs.SPEED, true);
			rightMotor.rotate(-RobotSpecs.SPEED);
		}
	}
	
	public void rotate(String direction) {
		if (direction.equals(Direction.Rotate.LEFT)) {
			leftMotor.rotate(-RobotSpecs.ROTATE_SPEED, true);
			rightMotor.rotate(RobotSpecs.ROTATE_SPEED);
		} else if (direction.equals(Direction.Rotate.RIGHT)) {
			leftMotor.rotate(RobotSpecs.ROTATE_SPEED, true);
			rightMotor.rotate(-RobotSpecs.ROTATE_SPEED);
		}
		virtualCompass.updateOrientation(direction);
	}
	
	public void moveOrientation(String orientation){
		String current = virtualCompass.getCurrentOrientation();
		
		if(Orientation.NORTH.equals(orientation)) {
			if(Orientation.EAST.equals(current)){
				rotate(Direction.LEFT);
			}else if(Orientation.WEST.equals(current)){
				rotate(Direction.RIGHT);
			}else if(Orientation.SOUTH.equals(current)){
				rotate(Direction.RIGHT);
				rotate(Direction.RIGHT);
			}
		} else if(Orientation.SOUTH.equals(orientation)) {
			
			if(Orientation.EAST.equals(current)){
				rotate(Direction.RIGHT);
			}else if(Orientation.WEST.equals(current)){
				rotate(Direction.LEFT);
			}else if(Orientation.NORTH.equals(current)){
				rotate(Direction.RIGHT);
				rotate(Direction.RIGHT);
			}
		} else if(Orientation.EAST.equals(orientation)) {
			if(Orientation.SOUTH.equals(current)){
				rotate(Direction.LEFT);
			}else if(Orientation.WEST.equals(current)){
				rotate(Direction.RIGHT);
				rotate(Direction.RIGHT);
			}else if(Orientation.NORTH.equals(current)){
				rotate(Direction.RIGHT);
			}
		} else if(Orientation.WEST.equals(orientation)) {
			if(Orientation.SOUTH.equals(current)){
				rotate(Direction.RIGHT);
			}else if(Orientation.EAST.equals(current)){
				rotate(Direction.LEFT);
				rotate(Direction.LEFT);
			}else if(Orientation.NORTH.equals(current)){
				rotate(Direction.LEFT);
			}
		}
		
		move(Direction.Move.FRONT);
	}
	
	public MechanicalArm getMechanicalArm() {
		return this.mechanicalArm;
	}
	
	public EndPathDetector getEndDetector() {
		return endDetector;
	}

	public Compass getVirtualCompass() {
		return this.virtualCompass;
	}
	
}
