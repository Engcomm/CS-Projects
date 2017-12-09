
public class Pair {

	public int first;
	public Integer weight;
	public int second;
	
	public Pair(int first, int weight, int second) {
		this.first = first;
		this.weight = weight;
		this.second = second;
	}
	
	public boolean equalTo(Pair p) {
		return this.first == p.first && this.second == p.second;
	}
	
}
