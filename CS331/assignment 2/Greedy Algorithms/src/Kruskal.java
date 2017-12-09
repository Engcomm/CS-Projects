import java.util.HashSet;
import java.util.Set;

public class Kruskal {

	public static Set<Pair> kruskal(Graph graph) {
		Pair[] sortedEdges = graph.getEdgeSorted();
		Set<Pair> result = new HashSet<Pair>();
		DisjointSet sets = new DisjointSet(graph.getSize());
		int index = 0;
		
		while(result.size() != graph.getSize() - 1) {
			if(index == sortedEdges.length)
				break;
			Pair nextEdge = sortedEdges[index];
			int uset = sets.find(nextEdge.first);
			int vset = sets.find(nextEdge.second);
			
			if(uset != vset) {
				sets.merge(uset, vset);
				result.add(nextEdge);
			}
			index++;
		}
		return result;
	}
	
}
