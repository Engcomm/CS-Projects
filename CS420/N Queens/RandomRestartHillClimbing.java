import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class RandomRestartHillClimbing {
	
	private int n;
	private Board board;
	private final int LIMIT = 25;
	
	public RandomRestartHillClimbing(int n) {
		this.n = n;
		board = new Board(n);
	}

	public boolean solve() {
		boolean solved = false;
		for(int i = 0; i < LIMIT; i++) {
			board = new Board(n);   // restart
			board = local_minimum();  
			if(board.num_attacks() == 0) {
				solved = true;
				//System.out.print("Solved\n" + board);
				break;
			}
		}
		//if(!solved)
			//System.out.print("Not Solved, Reached max 25 restarts\nLast Result Attacking Pairs = " + board.num_attacks() + "\n" + board);
		return solved;
	}
	
	public Board local_minimum() {
		// put every successor into the priority queue, and get the head
		PriorityQueue<Board> state_queue = new PriorityQueue<Board>(new HeuristicComparator());
		List<Board> visited = new ArrayList<Board>();
		visited.add(board);
		while(true) {
			for(Board x : board.get_neighbors(visited))
				state_queue.add(x);
			Board neighbor = state_queue.poll();
			state_queue.clear();
			if(neighbor.num_attacks() >= board.num_attacks())
				return board;
			board = neighbor;
			visited.add(board);
		}
	}
	
	class HeuristicComparator implements Comparator<Board> {
		public int compare(Board b1, Board b2) {
			return b1.num_attacks().compareTo(b2.num_attacks());
		}
	}

}
