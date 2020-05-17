import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;


public class MechanicalArm {
	
	private static final int MOTOR_ROTATION = 95;
	
	private final NXTRegulatedMotor rotationMotor;
	private final UltrasonicSensor obstacleSensor;
	
	public MechanicalArm(NXTRegulatedMotor rotationMotor, SensorPort obstacleSensor) {
		this.rotationMotor = rotationMotor;
		this.obstacleSensor = new UltrasonicSensor(obstacleSensor);
	}
	
	public boolean hasObstacle(String direction) {
		boolean hasObtacle;
		if (direction.equals(Direction.LEFT)) {
			rotationMotor.rotate(-MOTOR_ROTATION);
			hasObtacle = hasObstacle();
			rotationMotor.rotate(MOTOR_ROTATION);
		} else if (direction.equals(Direction.RIGHT)) {
			rotationMotor.rotate(MOTOR_ROTATION);
			hasObtacle = hasObstacle();
			rotationMotor.rotate(-MOTOR_ROTATION);
		} else if (direction.equals(Direction.BACK)) {
			rotationMotor.rotate(MOTOR_ROTATION * 2);
			hasObtacle = hasObstacle();
			rotationMotor.rotate(-MOTOR_ROTATION * 2);
		} else {
			hasObtacle = hasObstacle();
		}
		return hasObtacle;
	}
	
	private boolean hasObstacle() {
		return this.obstacleSensor.getDistance() <= RobotSpecs.OBSTACLE_TRESHOLD;
	}

}
