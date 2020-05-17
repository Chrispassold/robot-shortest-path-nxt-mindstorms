import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;


public class Graph {

	public static Integer DEFAULT_COST = Integer.valueOf(1);
	private Hashtable listaAdjacencia = new Hashtable();
	private Integer infinity = Integer.valueOf(Integer.MAX_VALUE);
	/*
	 * The mapping result with the signature: Map<Node, List<Node>> 
	 * */
	public Graph(Hashtable listaAdjacencia){
		this.listaAdjacencia = listaAdjacencia;
	}
	
	private CustomSet getVertices(){
		CustomSet vertices = new CustomSet();
		Enumeration vertexes = this.listaAdjacencia.keys();
		while (vertexes.hasMoreElements()) {
			Node vertex = (Node) vertexes.nextElement();
			Iterator children = ((CustomSet) this.listaAdjacencia.get(vertex)).iterator();
			while (children.hasNext()) {
				vertices.add(children.next());
			}
		}		
		return vertices;
	}

	private Node shortestDistance(Queue vertices, Hashtable distances){
		Enumeration it = vertices.elements();
		Integer minDist = this.infinity;
		Node minNode = null;
		while(it.hasMoreElements()){
			Node vertex = (Node)it.nextElement();
			Integer dist = (Integer)distances.get(vertex);
			if(minNode == null || minDist.intValue() > dist.intValue()){
				minNode = vertex;
			}
		}
		
		return minNode;
	}
	
	private CustomSet getNeighbours(Node vertex){
		return (CustomSet) this.listaAdjacencia.get(vertex);
	}
	
	public List findShortestPath(Node start, Node end){
		
		if(!this.listaAdjacencia.keys().hasMoreElements()){
			return null;
		}
		
		CustomSet vertices = getVertices();
		
		//1. Inicializar os vertices como não visitados
		Hashtable previousVertices = new Hashtable();
		Iterator nodesIterator = vertices.iterator();
		while(nodesIterator.hasNext()){
			Node vertex = (Node) nodesIterator.next();
			previousVertices.put(vertex, new EmptyNode());
		}
		
		//2. Setar zero para o `source` e infinito para os demais vertices
		Hashtable distances = new Hashtable();
		Iterator nodesIterator2 = vertices.iterator();
		while(nodesIterator2.hasNext()){
			Node vertex = (Node) nodesIterator2.next();
			distances.put(vertex, this.infinity);
		}
		
		//3. Cria uma fila com os vertices
		Queue filaVertices = new Queue();
		Iterator nodesIterator3 = vertices.iterator();
		while(nodesIterator3.hasNext()){
			Node vertex = (Node) nodesIterator3.next();
			filaVertices.push(vertex);
		}
		
		//4. Popula o primeiro nó com distancia 0
		distances.put(start, Integer.valueOf(0));
		
		while(!filaVertices.isEmpty()){
			//3. Seleciona o vertice com menor distancia
			Node currentVertex = shortestDistance(filaVertices, distances);

	        //4. Verifica se a menor distancia entre os outros 
	        //   vertices não visitados é infinita
			Integer currentDistance = (Integer)distances.get(currentVertex);
			if(currentDistance.equals(this.infinity)){
				System.out.println("O vertice "+currentVertex+" esta isolado");
				return null;
			}
			
	        //5. Procura os vizinhos não visitados pelo vertice atual
			//	 e calcula a distancia entre eles
			CustomSet neigbours = getNeighbours(currentVertex);
			Iterator neigboursIt = neigbours.iterator();
			while(neigboursIt.hasNext()){
				Node neighbourVertex = (Node)neigboursIt.next();
				int alternativeRouteCost = currentDistance.intValue() + DEFAULT_COST.intValue();
				
				Integer neighbourDist = (Integer)distances.get(neighbourVertex);
				// Compara a distancia com o mais novo vertice e salva o menor
				if(alternativeRouteCost < neighbourDist.intValue()){
					distances.put(neighbourVertex, Integer.valueOf(alternativeRouteCost));
					previousVertices.put(neighbourVertex, currentVertex);
				}
			}
			
	        // Remove o vertice atual da lista de vertices não visitados
			filaVertices.removeElement(currentVertex);
		}
		
		Stack path = new Stack();
		Node currentVertex = end;
		
	    //Gera o caminho, de tras pra frente
		while(!(previousVertices.get(currentVertex) instanceof EmptyNode)){
			path.push(currentVertex);
			currentVertex = (Node)previousVertices.get(currentVertex);
		}
		
		if(!path.empty()){
			path.push(currentVertex);
		}
		
		List pathReversed = new LinkedList();
		while(!path.empty()){
			pathReversed.add(path.pop());
		}
		
		return pathReversed;
	}
	
}
