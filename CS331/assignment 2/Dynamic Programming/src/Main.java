import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Main {

	public static void main(String[] args) throws IOException {
		// use an output file to record all 100 run time
		
		FileWriter output = new FileWriter("output.txt");
		int[] denom = {1, 5, 10, 25};
		
		long startTime = System.nanoTime();
		int[][] change = MakingChange.makeChangeDynProg(100, denom);
		
//		for(int i = 0; i < change.length; i++) {
//			for(int j = 0; j < change[0].length; j++) {
//				System.out.print(change[i][j] + " ");
//			}
//			System.out.println();
//		}
		
		for(int i = 0; i < 100; i++) {
			Random random = new Random();
			int input = random.nextInt(100);
			System.out.println("Change: " + input);
			
			normal(input, denom);
			
			dynamic(input, denom, change);
			
			long endTime = System.nanoTime();
			output.write((int) ((endTime - startTime) / 1000000) + "\n"); 
		}
		output.close();
	}

	public static void dynamic(int input, int[] denom, int[][] change) {
		int[] denomCount = {0, 0, 0, 0};
		int min = Integer.MAX_VALUE, maxDenom = 0;
		
		while(input != 0) {
			for(int i = 0; i < change.length; i++) {
				if(change[i][input] < min) {
					min = change[i][input];
					maxDenom = i;
				}
			}
			input -= denom[maxDenom];
			denomCount[maxDenom]++;
		}
		
		System.out.println("Quarter: " + denomCount[3]);
		System.out.println("Dime: " + denomCount[2]);
		System.out.println("Nickel: " + denomCount[1]);
		System.out.println("Penny: " + denomCount[0]);
	}
	
	public static void normal(int input, int[] denom) {
		int[] change = MakingChange.makeChange(input, denom);
		if(change[0] == -1)
			System.out.println("No Solution");
		else {
			System.out.println("Quarter: " + change[3]);
			System.out.println("Dime: " + change[2]);
			System.out.println("Nickel: " + change[1]);
			System.out.println("Penny: " + change[0]);
		}
	}
	
	
}
