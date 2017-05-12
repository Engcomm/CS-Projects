package rootsMethods;

public class Equation {
	
	public static double fx(String[][] equaData, double x) {
		double result = 0;
		double[] coeff = new double[equaData[0].length];
		double[] power = new double[equaData[0].length];
		for(int i = 0; i < equaData[0].length; i++) {
			for(int j = 0; j < equaData[0][i].length(); j++)
				if(equaData[0][i].charAt(j) == 'x') 
					coeff[i] = x;
				else
					coeff[i] = Double.parseDouble(equaData[0][i]);
			power[i] = Double.parseDouble(equaData[1][i]);
		}
		for(int i = 0; i < coeff.length; i++) {
			if(equaData[2][i].compareTo("cosh") == 0)
				result  = result + coeff[i] * Math.cosh(50/x);
			else 
				result = result + coeff[i] * Math.pow(x, power[i]);
		}
		return result;				
		
	}
}
