import lejos.nxt.ColorSensor;
import lejos.nxt.SensorPort;


public class EndPathDetector {
	
	private final ColorSensor sensor;
	private final int lightValueEnd;
	private final int tolerance;
	
	public EndPathDetector(SensorPort sensor, int lightValueEnd, int tolerance){
		this.sensor = new ColorSensor(sensor);
		this.lightValueEnd = lightValueEnd;
		this.tolerance = tolerance;
	}
	
	public boolean checkEndPath(){
		int light = sensor.getLightValue();
		int lightValueMax = lightValueEnd + tolerance;
		int lightValueMin = lightValueEnd - tolerance;
		
		System.out.println(light + " -> "+ (light >= lightValueMin || light <= lightValueMax));
		
		return  light >= lightValueMin || light <= lightValueMax ;
	}
	
}
