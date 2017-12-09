import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Main {

	public static void main(String[] args) throws IOException {
		int[] array = new int[100000];
		Random random = new Random();
		FileWriter output = new FileWriter("output.txt");
		for(int i = 0; i < array.length; i++)
			array[i] = random.nextInt(100000);
		
		long startTime = System.nanoTime();
		
		System.out.println(NormalMedian.getMedian(array));
		//System.out.println(SuperMedian.getMedian(array));
		
		long endTime = System.nanoTime();
		System.out.println((endTime - startTime) / 1000000 + "us\n");
		output.write((endTime - startTime) / 1000000 + "us\n"); 
		output.close();
	}

}
