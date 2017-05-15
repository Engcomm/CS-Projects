

import java.io.*;

public class Secant {
	private int control;
	private double ea;
	private Double[] Ea;
	private Double[] Et;
	
	public Secant(int control) {
		this.control = control;
	}
	
	public double solveEquation(double a, double b, int nmax) throws IOException{
		double fa = Equation.fx(control, a);
		double fb = Equation.fx(control, b);
		double tmp = fa, d = 0;
		Ea = new Double[nmax];
		Et = new Double[nmax];
		ea = 0;
		System.out.println("n    xn             f(xn)            ea");
		if(Math.abs(fa) > Math.abs(fb)) {
			fa = fb;
			fb = tmp;
			tmp = a;
			a = b;
			b = tmp;
		}
		Ea[0] = 0.0;
		Ea[1] = 0.0;
		Et[0] = 0.0;
		Et[1] = 0.0;
		System.out.print("0    " + a + "            ");
		System.out.printf("%.11f", fa);
		System.out.println("   NA");
		System.out.print("1    " + b + "            ");
		System.out.printf("%.11f", fb);
		System.out.println("    NA");
		for(int n = 2; n < nmax; n++) {
			if(Math.abs(fa) > Math.abs(fb)) {
				tmp = fa;
				fa = fb;
				fb = tmp;
				tmp = a;
				a = b;
				b = tmp;	
			}
			d = (b - a) / (fb - fa);
			b = a;
			fb = fa;
			d = d * fa;
			if((ea != 0 && ea < 0.00001) || fa == 0) {
				System.out.println("Root found");
				FileWriter output = new FileWriter("secant.txt");
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
				return a;
			}
			a = a - d;
			ea = Math.abs(tmp - a) / Math.abs(a);
			fa = Equation.fx(control, a);
			Ea[n] = Math.log10(ea);
			Et[n] = Math.log10((Math.abs(a - 126.63351346432101)) / 126.63351346432101);
			outputFormat(n, a, fa, ea);
		}
		return -999;
	}
	
	private void outputFormat(int n, double a, double fa, double ea) {
		System.out.printf("%-5d", n);
		System.out.printf("%.11f", a);
		System.out.print("  ");
		System.out.printf("%.11f", fa);
		System.out.print("  ");
		System.out.printf("%.11f", ea);
		System.out.println();
	}	
}
