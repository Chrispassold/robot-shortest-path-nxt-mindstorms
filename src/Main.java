import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import lejos.nxt.ColorSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;


public class Main {
	
	private static Node A = new Node(0,0);
	private static Node B = new Node(0,2);
	private static Node C = new Node(0,4);
	private static Node D = new Node(2,2);
	private static Node E = new Node(2,4);
	private static Node F = new Node(4,4);
	private static Node G = new Node(4,2);
	private static Node H = new Node(4,0);
	private static Node I = new Node(2,0);
	
	//Map<Node, List<Node>>
	private static Hashtable getTestGraph() {
		Hashtable testeMap = new Hashtable();
		
		List neighboursA = new ArrayList();
		neighboursA.add(B);
		
		List neighboursB = new ArrayList();
		neighboursB.add(A);
		neighboursB.add(C);
		neighboursB.add(D);
		
		List neighboursC = new ArrayList();
		neighboursC.add(B);
		
		List neighboursD = new ArrayList();
		neighboursD.add(B);
		neighboursD.add(E);
		neighboursD.add(I);
		
		List neighboursE = new ArrayList();
		neighboursE.add(D);
		neighboursE.add(F);

		List neighboursF = new ArrayList();
		neighboursF.add(E);
		neighboursF.add(G);
		
		List neighboursG = new ArrayList();
		neighboursG.add(F);
		neighboursG.add(H);
		
		List neighboursH = new ArrayList();
		neighboursH.add(G);
		neighboursH.add(I);
		
		List neighboursI = new ArrayList();
		neighboursI.add(D);
		neighboursI.add(H);
		
		testeMap.put(A, neighboursA);
		testeMap.put(B, neighboursB);
		testeMap.put(C, neighboursC);
		testeMap.put(D, neighboursD);
		testeMap.put(E, neighboursE);
		testeMap.put(F, neighboursF);
		testeMap.put(G, neighboursG);
		testeMap.put(H, neighboursH);
		testeMap.put(I, neighboursI);
		
		return testeMap;
	}
	
	public static void main(String[] args) throws InterruptedException {
		MechanicalArm mechanicalArm = new MechanicalArm(Motor.B, SensorPort.S1);
		EndPathDetector colorDetector = new EndPathDetector(SensorPort.S2, 30, 5);
		Robot robot = new Robot(Motor.A, Motor.C, mechanicalArm, colorDetector);
		
		
		System.out.println("Prepare yourselves");
		Thread.sleep(10000);
		
		// Exucutar o mapeamento
		Mapping mapping = new Mapping(robot);
		Hashtable listaAdjacencia = mapping.map();
		
		
		Thread.sleep(2000);
		
		Graph graph = new Graph(listaAdjacencia);
		
		//Edges
		List nodes = graph.findShortestPath(mapping.getStartNode(), mapping.getEndNode());
		
		PathExecutor pathExecutor = new PathExecutor(robot);
		pathExecutor.executePathSequence(nodes);
		
		
		
//		while(true){
//			colorDetector.checkEndPath();
//		}
	}

}

