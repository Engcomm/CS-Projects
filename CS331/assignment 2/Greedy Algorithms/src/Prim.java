import java.util.HashSet;
import java.util.Set;

public class Prim {
	
	public static Set<Pair> prim(Graph graph) {  
	
		Set<Pair> result = new HashSet<Pair>();
		int[] nearest = new int[graph.getSize() - 1];
		int[] minDist = new int[graph.getSize() - 1];
		for(int i = 1; i < nearest.length; i++)
			minDist[i] = graph.getWeight(i, 0);
		int k = 0;
		for(int i = 0; i < nearest.length; i++) {
			int min = Integer.MAX_VALUE;
			for(int j = 1; j < nearest.length; j++) {
				if(minDist[j] >= 0 && minDist[j] < min) {
					min = minDist[j];
					k = j;
				}
			}
			result.add(new Pair(nearest[k], 0, k + 1));  // just use 0, not important here
			minDist[k] = -1;
			boolean minDistChanged = false;
			for(int j = 1; j < nearest.length; j++) {
				if(j != k && graph.getWeight(j, k) < minDist[j]) {   // edge[][] discards diagonal, no j == k
					minDistChanged = true;
					minDist[j] = graph.getWeight(j, k);
					nearest[j] = k;
				}
			}
			if(!minDistChanged) {  // otherwise in the next iteration nearest[k] = 0 by default, not true
				for(int j = 0; j < nearest.length; j++)
					nearest[k] = k;
			}
			
		}
		return result;
	}
	
}
