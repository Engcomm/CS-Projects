
public class DisjointSet {

	private int[] sets;
	private int[] rank;
	
	public DisjointSet(int n) {
		sets = new int[n];
		rank = new int[n];
		for(int i = 0; i < n; i++)
			sets[i] = i;
	}
	
	public int find(int x) {
		int r = x;
		while(sets[r] != r)
			r = sets[r];
		int i = x;
		while(i != r) {
			int j = sets[i];
			sets[i] = j;
			i = j;
		}
		return r;
	}
	
	public void merge(int a, int b) {
		if(rank[a] == rank[b]) {
			rank[a] = rank[a] + 1;
			sets[b] = a;
		} else {
			if(rank[a] > rank[b]) 
				sets[b] = a;
			else
				sets[a] = b;
		}
	}
	
}
