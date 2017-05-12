package rootsMethods;
import java.util.*;


public class LocateRoots {

	public static void main(String[] args) {
		int choice1 = 0, choice2 = 0;
		Scanner kb = new Scanner(System.in);
		System.out.print("1. Test defualt examples from BlackBoard\n2. Test manually");
		choice1 = kb.nextInt();
		if(choice1 == 1) {
			System.out.print("1. 2x3 - 11.7x2 + 17.7x - 5\n2. x + 10 - xcosh(50/x)");
			choice2 = kb.nextInt();
			if(choice2 == 1) {
				String[][] equaData;
			  	equaData = defaultEquation1();
			  	bisection(equaData);
			  	secant(equaData);
			}
			else {
				String[][] equaData;
				equaData = defaultEquation2();
				bisection(equaData);
			  	secant(equaData);
			}
		}
		else {
			System.out.print("Enter the number of terms of the equation: ");
			int term = kb.nextInt();
			String[][] equaData = new String[3][term];
			equaData = askCoeff(equaData, kb);
			equaData = askPower(equaData, kb);
			equaData = askType(equaData, kb);
			bisection(equaData);
		  	secant(equaData);
		}
		
		
	}
	
	public static void bisection(String[][] equaData) {
		Bisection bis = new Bisection(equaData);
		System.out.println(bis.solveEquation(1, 2, 100));
	}
	
	public static void secant(String[][] equaData) {
		
		
		
	}
	
	public static String[][] askCoeff(String[][] equaData, Scanner kb) {
		for(int i = 0; i < equaData[0].length; i++) {
			System.out.print("Enter the number " + (i + 1) + " coefficient: ");
			equaData[0][i] = kb.nextLine();
		}
		return equaData;
	}
	
	public static String[][] askPower(String[][] equaData, Scanner kb) {
		for(int i = 0; i < equaData[1].length; i++) {
			System.out.print("Enter the number " + (i + 1) + " power of that term: ");
			equaData[0][i] = kb.nextLine();
		}
		return equaData;
	}
	
	public static String[][] askType(String[][] equaData, Scanner kb) {
		for(int i = 0; i < equaData[2].length; i++) {
			System.out.print("Enter the number " + (i + 1) + " type of equation(x for default, eg. cosh): ");
			equaData[0][i] = kb.nextLine();
		}
			return equaData;
	}
	
	public static String[][] defaultEquation1() {
		String[][] equaData = new String[3][4];
		equaData[0][0] = "2";
		equaData[0][1] = "-11.7";
		equaData[0][2] = "17.7";
		equaData[0][3] = "-5";
		equaData[1][0] = "3";
		equaData[1][1] = "2";
		equaData[1][2] = "1";
		equaData[1][3] = "0";
		equaData[2][0] = "x";
		equaData[2][1] = "x";
		equaData[2][2] = "x";
		equaData[2][3] = "x";		
		return equaData;
	}
		
	public static String[][] defaultEquation2() {
		String[][] equaData = new String[3][3];
		equaData[0][0] = "1";
		equaData[0][1] = "10";
		equaData[0][2] = "-x";
		equaData[1][0] = "1";
		equaData[1][1] = "0";
		equaData[1][2] = "1";
		equaData[2][0] = "x";
		equaData[2][1] = "x";
		equaData[2][2] = "cosh";
		return equaData;
	}
		
}
