import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Board {

	private int n;
	private int[] board;
	
	public Board(int n) {
		// given n-queens
		this.n = n;
		board = new int[n];
		random_initialize();
	}
	
	public Board(int[] input) {
		// given the existing board
		n = input.length;
		board = new int[n];
		for(int i = 0; i < n; i++)
			board[i] = input[i];
	}
	
	private void random_initialize() {
		Random random = new Random();
		for(int i = 0; i < n; i++) 
			board[i] = random.nextInt(n);
	}
	
	public Integer num_attacks() {
		// no need to check vertically because the initial state guarantees it
		int count = 0;
		for(int i = 0; i < n; i++) {
			for(int j = i + 1; j < n; j++) {
				if(board[i] == board[j])
					count++;
				if(j - i == Math.abs(board[j] - board[i]))
					count++;
			}
		}
		return count;
	}
	
	public List<Board> get_neighbors(List<Board> visited) {
		// 7 * 8 = 56 successors
		List<Board> result = new ArrayList<Board>();
		for(int i = 0; i < n; i++) {
			int j = 0;
			while(j < n) {
				if(j == i)
					j++;
				if(j == n)
					break;
				int[] newBoard = Arrays.copyOf(board, n);
				newBoard[i] = j;
				Board neighbor = new Board(newBoard);
				boolean ifadd = true;
				for(Board x : visited) {
					if(x.equals(neighbor))
						ifadd = false;
				}
				if(ifadd)
					result.add(neighbor);
				j++;
			}
		}
		return result;
	}
	
	public String toString() {
		String result = "";
		int[][] output = new int[n][n];
		for(int i = 0; i < n; i++) 
			output[n - board[i] - 1][i] = 1;
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				if(output[i][j] == 1)
					result += "Q ";
				else
					result += "X ";
			}
			result += "\n";
		}
		return result;
	}
	
	public boolean isequal(Board b) {
		boolean equal = true;
		for(int i = 0; i < n; i++) {
			if(board[i] != b.get_board()[i]) {
				equal = false;
				break;
			}
		}
		return equal;
	}
	
	public int[] get_board() {
		return board;
	}
	
	public void write_board(int index, int value) {
		board[index] = value;
	}
}
