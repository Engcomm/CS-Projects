

public class Equation {
	
	public static double fx(int control, double x) {
		double result = 0;
		if(control == 1) 
			result = (2 * Math.pow(x, 3)) - (11.7 * Math.pow(x, 2)) + 17.7 * x - 5;
		else if(control == 2)	
			result = x + 10 - x * Math.cosh(50/x);
		else
			result = -999;
		return result;
	}
	
	public static double dx(int control, double x) {
		double result = 0;
		if(control == 1) 
			result = (6 * Math.pow(x, 2)) - (23.4 * x) + 17.7 ;
		else if(control == 2)	
			result = 1 - Math.cosh(50 / x) + 50 * x * Math.sinh(50 * Math.pow(x, -2));
		else
			result = -999;
		return result;
	}
	
	
}
