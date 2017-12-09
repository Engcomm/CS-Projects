import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

// The edge[][] cuts the matrix along diagonal, and it also discards the diagonal, so only[V-1][V-1]

public class Graph {
	
	private int[][] edge;
	private int numEdge = 0;
	
	public Graph(int numVertices, String option) {
		String connectedness = "";
		int numCol = 1;
		edge = new int[numVertices - 1][];
		for(int i = 0; i < numVertices - 1; i++) {
			edge[i] = new int[numCol];
			numCol++;
		}
		if(option.compareToIgnoreCase("sparse") == 0)
			connectedness = "sparse";
		else if(option.compareToIgnoreCase("dense") == 0)
			connectedness = "dense";
		randomGenerateGraph(this, connectedness);
	}
	
	public int getSize() {
		return edge.length + 1;
	}
	
	public int getWeight(int source, int target) {
		if(source > target)
			return edge[source - 1][target];
		else
			return edge[target - 1][source];
	}
	
	public Pair[] getEdgeSorted() {   
		Pair[] result = new Pair[numEdge];
		int index = 0;
		for(int i = 0; i < edge.length; i++) {
			for(int j = 0; j < edge[i].length; j++) {
				if(edge[i][j] != Integer.MAX_VALUE) {
					result[index] = new Pair(i + 1, edge[i][j], j);
					index++;
				}
			}
		}	
		Arrays.sort(result, new MyComparator());
		return result;
	}
	
	private void randomGenerateGraph(Graph graph, String connectedness) {   
		Random random = new Random();
		if(connectedness.compareTo("sparse") == 0) {
			// define sparse as only V-1 edges that simply connects every node
			numEdge = edge.length;
			Set<Integer> connected = new HashSet<Integer>();
			int loop = 0;
			while (loop < edge.length) {
				int row = random.nextInt(edge.length);
				int col = random.nextInt(edge[row].length);
				if(connected.contains(row + 1) && connected.contains(col)) {
					if(this.getWeight(row + 1, col) == 0)
						edge[row][col] = Math.abs(random.nextInt());
					else {
						row = random.nextInt(edge.length);
						col = random.nextInt(edge[row].length);
						loop--;
					}
				} else {
					edge[row][col] = Math.abs(random.nextInt());
					connected.add(row + 1);
					connected.add(col);
				}
				loop++;
			}
			for(int i = 0; i < edge.length; i++) {
				for(int j = 0; j < edge[i].length; j++) {
					if(edge[i][j] == 0)
						edge[i][j] = Integer.MAX_VALUE;
				}
			}
			
		} else {
			// define dense as maximum number of edges
			numEdge = ((edge.length + 1) * (edge.length + 1) - (edge.length + 1)) / 2;
			for(int i = 0; i < edge.length; i++) {
				for(int j = 0; j < edge[i].length; j++) {
					edge[i][j] = Math.abs(random.nextInt());
				}
			}
		}
	}
	
}

class MyComparator implements Comparator<Pair> {  
	// custom comparator on the sorting edges, that sorts the Pair based on the Pair.weight attribute
	@Override
	public int compare(Pair o1, Pair o2) {
		return (o1.weight).compareTo(o2.weight);
	}
}

