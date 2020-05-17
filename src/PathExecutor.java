import java.util.Iterator;
import java.util.List;

public class PathExecutor {

	private final Robot robot;
	
	public PathExecutor(Robot robot){
		this.robot = robot;
	}
	
	
	public void executePathSequence(List path){
		//ignorar primeiro
		path.remove(0);
		
		int refX = 0;
		int refY = 0;
		
		Iterator it = path.iterator();
		
		while(it.hasNext()){
			Node currentNode = (Node)it.next();
			int x = currentNode.getX();
			int y = currentNode.getY();
			
			if(x == refX && y != refY){
				if(y > refY){
					robot.moveOrientation(Orientation.NORTH);
				}else{
					robot.moveOrientation(Orientation.SOUTH);
				}
			}
			
			if(x != refX && y == refY){
				if(x > refX){
					robot.moveOrientation(Orientation.EAST);
				}else{
					robot.moveOrientation(Orientation.WEST);
				}
			}
			
			refX = x;
			refY = y;
		}
				
	}
	
	
}
