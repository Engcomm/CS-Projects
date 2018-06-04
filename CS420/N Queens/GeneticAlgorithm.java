import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Random;

public class GeneticAlgorithm {

	private int n;
	PriorityQueue<Board> population;
	private final int INITIAL_STATES = 90;
	
	public GeneticAlgorithm(int n) {
		this.n = n;
		population = new PriorityQueue<Board>(new HeuristicComparator()); 
		for(int i = 0; i < INITIAL_STATES; i++) {
			Board board = new Board(n);
			population.add(board);
		}	
	}
	
	public boolean solve() {
		boolean solved = false;
		Random random = new Random();
		long start_time = System.nanoTime(), end_time = 0;
		while(true) {
			end_time = System.nanoTime();
			// time limit = 1 sec
			if((double)(end_time - start_time) / 1e9 > 1) {
				//System.out.println("Time Limit Exceeded");
				break;
			}
			PriorityQueue<Board> new_population = new PriorityQueue<Board>(new HeuristicComparator()); 
			//System.out.print(population.peek() + "" + population.peek().num_attacks() + "\n\n");
			if(population.peek().num_attacks() == 0) {
				solved = true;
				//System.out.print(population.peek());
				break;
			}
			for(int i = 0; i < population.size(); i++) {
				// random select 2 parents
				Board x = random_selection(population);
				Board y = random_selection(population);
				// produce the child
				Board child = produce(x, y);
				// mutate the child given 10% probability
				if(random.nextDouble() < 0.1)
					child = mutate(child);
				new_population.add(child);
			}
			population = new_population;
		}
		return solved;
	}
	
	private Board random_selection(PriorityQueue<Board> population) {
		Random random = new Random();
		Board result = null;
		int index = population.size();
		double sum = 0;
		for(Board x : population)
			sum += x.num_attacks();
		double ran = random.nextDouble();
		Iterator<Board> iter = population.iterator();
		while(iter.hasNext()) {
			result = iter.next();
			index--;
			if(ran - ((double)result.num_attacks() / sum) < 0)
				break;
			ran -= (double)result.num_attacks() / sum;
		}
		// smaller the number, better the fit, this is different in the book, so I reversed it
		iter = population.iterator();
		for(int i = 0; i <= index; i++)
			result = iter.next();
		return result;
	}
	
	private Board produce(Board x, Board y) {
		Random random = new Random();
		int c = random.nextInt(n);
		int[] newBoard = new int[n];
		for(int i = 0; i < c; i++)
			newBoard[i] = x.get_board()[i];
		for(int i = c; i < n; i++)
			newBoard[i] = y.get_board()[i];
		return new Board(newBoard);
	}
	
	private Board mutate(Board child) {
		Random random = new Random();
		int index = random.nextInt(n);
		child.write_board(index, random.nextInt(n));
		return child;
	}
	
	class HeuristicComparator implements Comparator<Board> {
		public int compare(Board b1, Board b2) {
			return b1.num_attacks().compareTo(b2.num_attacks());
		}
	}
}
