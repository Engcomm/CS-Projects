
import java.io.*;

public class Bisection {
	
	private int control;
	private double ea;
	private Double[] Ea;
	private Double[] Et;
	
	public Bisection(int control) {
		this.control = control;
	}
	
	public double solveEquation(double a, double b, int nmax) throws IOException {	
			double fa = Equation.fx(control, a);
			double fb = Equation.fx(control, b);
			double error = 0, c = 0, fc = 0, prevC= 0;
			Ea = new Double[nmax];
			Et = new Double[nmax];
			if(fa*fb > 0) {
				System.out.println("Error: fa and fb have same sign");
				return -999;
			}
			error = b - a;
			System.out.println("n    cn             f(cn)          ea");
			for(int n = 0; n < nmax; n++) {
				error = error / 2;
				c = a + error;
				if(prevC != 0) 
					ea = Math.abs(prevC - c) / Math.abs(c); 
				prevC = c;
				fc = Equation.fx(control, c);
				outputFormat(n, c, fc, ea);
				if(ea != 0 && ea < 0.00001) {   // ea < 0.0001   Math.abs(error) < ea
					System.out.println("Root found");   // add output
					FileWriter output = new FileWriter("bisection.txt");
					for(int i = 0; i < n; i++) {
						output.write(Ea[i].toString());
						output.write("\t");
					}
					output.write("\n");
					for(int i = 0; i < n; i++) {
						output.write(Et[i].toString());
						output.write("\t");
					}
					output.close();
					return c;
				}
				Ea[n] = Math.log10(ea);
				Et[n] = Math.log10((Math.abs(c - 126.63351346432101)) / 126.63351346432101);
				if(fa*fc < 0)
					b = c;
				else
					a = c;
			}
			return -998;
	}
	
	private void outputFormat(int n, double c, double fc, double ea) {
		System.out.printf("%-5d", n);
		System.out.printf("%.11f", c);
		System.out.print("  ");
		System.out.printf("%.11f", fc);
		System.out.print("  ");
		System.out.printf("%.11f", ea);
		System.out.println();
	}
}
