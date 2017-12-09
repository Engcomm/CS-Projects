import java.util.Set;

public class Main {
	
	public static void main(String[] args) {
		Graph graph = new Graph(10000, "sparse");
		long startTime, endTime;
		
		startTime = System.nanoTime();
		Set<Pair> result1 = Prim.prim(graph);
		endTime = System.nanoTime();
		System.out.println((endTime - startTime) / 1000000 + "ms\n");
		startTime = System.nanoTime();
		Set<Pair> result2 = Kruskal.kruskal(graph);
		endTime = System.nanoTime();
		System.out.println((endTime - startTime) / 1000000 + "ms\n");
		
//-------------------test-------------------------
//		for(Pair x : result1) {
//			System.out.print(x.first + ", ");
//			System.out.print(x.second + "  ");
//			System.out.println();
//		}
//		System.out.println();
//		System.out.println();
//		for(Pair x : result2) {
//			System.out.print(x.first + ", ");
//			System.out.print(x.second + "  ");
//			System.out.println();
//		}

		
	}
	
}
