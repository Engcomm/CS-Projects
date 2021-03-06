// Lou, Junda
// CS 141 01
// Project #2: Failure IS an Option
//

import java.util.Scanner;
import java.io.*;
public class project2
{
    public static void main(String[] args) throws IOException
    {
	String fileName = args[0];
	int numRow = getDataRows(fileName);
	String[][] data = new String[numRow][];
	data = getDataInSquareArray(fileName, data, numRow);
	printGraph(data);
    }    
    public static int getDataRows(String fileName) throws IOException
    {
	int numRow = 0;
	File fileReadLine = new File(fileName);
	Scanner inputFileReadLine = new Scanner(fileReadLine);
	try {
	    while (true){ 	
		inputFileReadLine.nextLine();
		numRow++;                       
	    }
	}
	catch (Exception e){
	}

	inputFileReadLine.close();
	return numRow;
    }

    public static String[][] getDataInSquareArray(String fileName, String data[][], int numRow) throws IOException
	{
	    int row = 0, col = 0, y = 0;	
	    File file = new File(fileName);
	    Scanner inputFile = new Scanner(file);
	    try {
		while (true) {
			data[row] = inputFile.nextLine().split(",");
			row++;
		}
	    }
	    catch (Exception e) {
	    }
		int rectLength = row;
		int rectWidth = data[0].length;
		try {		 
		    y = 1 / ((rectLength - rectWidth) + Math.abs(rectLength - rectWidth));
		    rectWidth = rectLength;
		}
		catch (ArithmeticException e) {
		    rectLength = rectWidth;
		}

		int n = rectLength;
		String[][] squareData = new String[n][n];
		row = 0;
		col = 0;
		try {
		    while (true) {
			try {
			    while (true) {
				y = 1 / (row - n); 	
				squareData[row][col] = null;
				col++;
			    }
			}
			catch (ArrayIndexOutOfBoundsException e) {
			row++;
			col = 0;
			}
		    }
		}
		catch (ArithmeticException e) {
		}

		row = 0;
	
		try {
		    while (true) {
			try {
			    while (true) {
				y = 1 / (row - numRow);
				squareData[row][col] = data[row][col];
				col++;
			    }
			}
			catch (ArrayIndexOutOfBoundsException e) {
			    row++;
			    col = 0;
			}
		    }
		}
		catch (ArithmeticException e) {
		}

	    return squareData;
	}

    public static void printGraph(String[][] data)
    {
	int row = 0, col = 0, y = 0;
	ANSI.clearScreen();
	ANSI.cursorHome();
	try {
	    while (true) {
		try {
		    while (true) {
			y = 1 / (row - data.length);
			try {
			    ANSI.setTextColor(data[row][col].charAt(1) - '0');
			    ANSI.setBackgroundColor(data[row][col].charAt(2) - '0');
			    System.out.print(data[row][col].charAt(0));
			    ANSI.resetChars();
			}
			catch (NullPointerException e) {
			}
			col++;
		    }
		}
		catch (ArrayIndexOutOfBoundsException e) {
		    row++;
		    col = 0;
		    System.out.println();
		}
	    }
	}
	catch (ArithmeticException e) {
	}
	
	try {
	Thread.sleep(250);
	}
	catch (Exception e) {
	}

	data = rotateGraph(data);
	printGraph(data);
    }

    public static String[][] rotateGraph(String[][] data)
    {
	String tmp;
	int n = data.length, row = 0, col = 0, y = 0;
	String[][] newData = new String[n][n];
	try {
	    while (true) {
		try {
		    while (true) {
			y = 1 / (row - n);
			newData[row][col] = data[n-1-col][row];
			col++;
		    }
		}
		catch (ArrayIndexOutOfBoundsException e) {
		    row++;
		    col = 0;
		}
	    }
	}
	catch (ArithmeticException e) {
	}

	return newData;
    }
}
    