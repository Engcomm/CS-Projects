import java.util.*;
import java.io.*;

public class Interpolation {
	public static void main(String[] args) throws FileNotFoundException {
	    File inputFile = new File("input.txt");
		//File inputFile = new File("E:/CS-Projects/CS301/project3/sample.txt");
		Scanner input = new Scanner(inputFile);
		String[] tmp = input.nextLine().split(" ");
		double[][] data = new double[2][tmp.length];
		for(int i = 0; i < tmp.length; i++) {
			data[0][i] = Double.parseDouble(tmp[i]);
			data[1][i] = input.nextDouble();
		}
		DividedDifference dTable = new DividedDifference(data);
		dTable.getDDifference();
	}

}
