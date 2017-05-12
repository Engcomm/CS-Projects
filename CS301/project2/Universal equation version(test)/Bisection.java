package rootsMethods;

public class Bisection {
	
	private String[][] equaData;
	private double ea;
	
	public Bisection(String[][] equaData) {
		this.equaData = equaData;
	}
	
	public double solveEquation(double a, double b, int nmax) {	
			double fa = Equation.fx(equaData, a);
			double fb = Equation.fx(equaData, b);
			double error = 0, c = 0, fc = 0, prevC= 0;
			if(fa*fb > 0) {
				System.out.println("Error: fa and fb have same sign");
				return -999;
			}
			error = b - a;
			for(int n = 0; n < nmax; n++) {
				error = error / 2;
				c = a + error;
				if(prevC != 0)
					ea = Math.abs(prevC - c) / Math.abs(c); 
				prevC = c;
				fc = Equation.fx(equaData, c);
				if(ea < 0.01) {   // ea < 0.01   Math.abs(error) < ea
					System.out.println("Convergence");   // add output
					break;
				}
				if(fa*fc < 0)
					b = c;
				else
					a = c;
			}
			return c;
	}
	
	
	
	
	
}
