import java.util.*;
import java.io.*;

public class test {
   
    public static void main(String[] args) throws IOException {
	FileWriter output = new FileWriter("input.txt");
	Integer[][] array = new Integer[10][10];
	for(int i = 0; i < 10; i++) 
	    for(int j = 0; j < 10; j++) 
		array[i][j] = 10 * i + j; 
	for(int i = 0; i < 10; i++) {
	    for(int j = 0; j < 10; j++) { 
		output.write(array[i][j].toString());
		output.write("\t");
	    }
	    output.write("\n");
	}
	output.close();
    }


}
