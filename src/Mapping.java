import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Stack;

public class Mapping {
	
	private final Robot robot; 
	private final Stack stack = new Stack();
	private Hashtable mappingResult;
	private CustomSet knownNodes;
	
	private Node start = new Node(0, 0);
	private Node end = null;
	
	public Mapping(Robot robot) {
		this.robot = robot;
	}

	/***
	 * @return The mapping result with the signature: Map<Node, List<Node>> 
	 */
	public Hashtable map() {
		this.mappingResult = new Hashtable();
		this.knownNodes = new CustomSet();
		
		stack.push(analyseAction(start));
		while (!stack.isEmpty()) {
			MappingAction currentAction = (MappingAction) stack.pop();
			currentAction.execute();
		}
		
		return mappingResult;
	}
	
	public Node getEndNode(){
		return end;
	}
	
	public Node getStartNode(){
		return start;
	}
	
	private MappingAction analyseAction(final Node node) {
		knownNodes.add(node);
		if (mappingResult.get(node) == null) {
			mappingResult.put(node, new CustomSet());
		}
		
		return new MappingAction() {
			public void execute() {
				Node currentNode = node;
				
				if(robot.getEndDetector().checkEndPath()){
					System.out.println("LAST:" + node.toString());
					end = currentNode;
				}
				
				List siblings = getSiblings(currentNode);
				
				Node leftSibling = (Node) siblings.get(0);
				if (!robot.getMechanicalArm().hasObstacle(Direction.LEFT)) {
					addRelation(currentNode, leftSibling);
					if (!knownNodes.contains(leftSibling)) {
						addToStackOnLogicalOrder(new MappingAction[] {
							rotateAction(Direction.Rotate.LEFT),
							moveAction(Direction.Move.FRONT),
							analyseAction(leftSibling),
							moveAction(Direction.Move.BACK),
							rotateAction(Direction.Rotate.RIGHT)
						});
					}
				}
				
				Node rightSibling = (Node) siblings.get(2);
				if (!robot.getMechanicalArm().hasObstacle(Direction.RIGHT)) {
					addRelation(currentNode, rightSibling);
					if (!knownNodes.contains(rightSibling)) {
						addToStackOnLogicalOrder(new MappingAction[] {
							rotateAction(Direction.Rotate.RIGHT),
							moveAction(Direction.Move.FRONT),
							analyseAction(rightSibling),
							moveAction(Direction.Move.BACK),
							rotateAction(Direction.Rotate.LEFT)
						});
					}
				}
				
				Node frontSibling = (Node) siblings.get(1);
				if (!robot.getMechanicalArm().hasObstacle(Direction.FRONT)) {
					addRelation(currentNode, frontSibling);
					if (!knownNodes.contains(frontSibling)) {
						addToStackOnLogicalOrder(new MappingAction[] {
							moveAction(Direction.Move.FRONT),
							analyseAction(frontSibling),
							moveAction(Direction.Move.BACK)
						});
					}
				}
				
			}
		};
	}
	
	private MappingAction moveAction(final String direction) {
		return new MappingAction() {
			public void execute() {
				robot.move(direction);
			}
		};
	}
	
	private MappingAction rotateAction(final String direction) {
		return new MappingAction() {
			public void execute() {
				robot.rotate(direction);
			}
		};
	}
	
	private List getSiblings(Node node) {
		String currentOrientation = robot.getVirtualCompass().getCurrentOrientation();
		ArrayList siblings = new ArrayList(3);
		if (currentOrientation.equals(Orientation.NORTH)) {
			siblings.add(new Node(node.getX() - 1, node.getY()));
			siblings.add(new Node(node.getX(), node.getY() + 1));
			siblings.add(new Node(node.getX() + 1, node.getY()));
			
		} else if (currentOrientation.equals(Orientation.EAST)) {
			siblings.add(new Node(node.getX(), node.getY() + 1));
			siblings.add(new Node(node.getX() + 1, node.getY()));
			siblings.add(new Node(node.getX(), node.getY() - 1));
			
		} else if (currentOrientation.equals(Orientation.SOUTH)) {
			siblings.add(new Node(node.getX() + 1, node.getY()));
			siblings.add(new Node(node.getX(), node.getY() - 1));
			siblings.add(new Node(node.getX() - 1, node.getY()));
			
		} else if (currentOrientation.equals(Orientation.WEST)) {
			siblings.add(new Node(node.getX(), node.getY() - 1));
			siblings.add(new Node(node.getX() - 1, node.getY()));
			siblings.add(new Node(node.getX(), node.getY() + 1));
			
		} else {
			throw new IllegalStateException("No valid orientation found.");
		}
		return siblings;
	}
	
	private void addRelation(Node node1, Node node2) {
		CustomSet childs1 = (CustomSet) mappingResult.get(node1);
		if (childs1 == null) {
			childs1 = new CustomSet();
			mappingResult.put(node1, childs1);
		}
		CustomSet childs2 = (CustomSet) mappingResult.get(node2);
		if (childs2 == null) {
			childs2 = new CustomSet();
			mappingResult.put(node2, childs2);
		}
		
		childs1.add(node2);
		childs2.add(node1);
	}
	
	private void addToStackOnLogicalOrder(MappingAction[] actions) {
		for (int i = (actions.length - 1); i >= 0; i--) {
			stack.push(actions[i]);
		}
	}
	
}
