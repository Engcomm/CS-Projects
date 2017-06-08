public class DividedDifference {
	
	double[][] data;
	
	public DividedDifference(double[][] data) {
		this.data = data;
	}

	public void getDDifference() {
		int tableLen = 0, startI = data[0].length;
		for(int i = 0; i < data[0].length; i++)
			tableLen = tableLen + (data[0].length - i);	
		double[] dTable = new double[tableLen];
		for(int i = 0; i < data[0].length; i++) 
			dTable[i] = data[1][i]; 
		for(int i = 0; i < data[0].length; i++) {
			dTable = getNextDiff(dTable, startI, data[0].length - i - 1);
			startI = startI + data[0].length - i - 1;
		}
		printTable(dTable);
		getPolynomial(dTable);
		getSimplified(dTable);
	}
	
	private void getPolynomial(double[] dTable) {
		int len = data[0].length, index = len;
		String x = "";
		System.out.print("\nInterpolation Polynomial is: ");   // solve this
		for(int i = 0; i < data[0].length; i++) {
			if(i >= 1) {
				if(data[0][i - 1] > 0)
					x = x + "(x - " + data[0][i - 1] + ")";
				else if(data[0][i - 1] < 0)
					x = x + "(x + " + Math.abs(data[0][i - 1]) + ")";
				else
					x = x + "x";
				if(dTable[index] > 0)
					System.out.print("+ " + dTable[index] + x + " ");
				else if(dTable[index] < 0)
					System.out.print("- " + Math.abs(dTable[index]) + x + " ");
				len--;
				index += len;
			}
			else
				System.out.print(dTable[0] + " ");
		}
	}
	
	private void getSimplified(double[] dTable) {
		double[] divDif = new double[data[0].length];
		double[] coeff = new double[data[0].length];
		int index = 0, len = data[0].length, combLen = 0, tmp = data[0].length - 1;
		for(int i = 0; i < divDif.length; i++) {
			divDif[i] = dTable[index];
			index += len;
			len--;
		}
		for(int i = 1; i <= tmp; i++) 
			combLen += combination(tmp, i);
		String[] comb = new String[combLen];
		for(Integer i = 0; i < data[0].length - 1; i++)
			comb[i] = i.toString();
		comb = recursiveComb(comb, 0, data[0].length - 2, 0, data[0].length - 1);
		/*
		System.out.println();
		for(Integer i = 0; i < comb.length; i++)
			System.out.println(comb[i]);
		*/	
		for(int pow = 0; pow < coeff.length; pow++) {
			double result = 0;
			if(pow == 0)
				coeff[pow] = divDif[pow];
			else {
				result = divDif[pow];
				int i = pow;
					for(int j = 0; j < comb.length; j++) {
						if(comb[j].length() < data[0].length - pow) {
							if(j == 0 || comb[j].length() > comb[j - 1].length())
								i++;
							if(comb[j].length() == 1 && (int)(comb[j].charAt(0) - '0') < data[0].length - pow) 
								result += divDif[i] * (-data[0][(int)(comb[j].charAt(0) - '0')]);
							else if(comb[j].length() > 1 && (int)(comb[j].charAt(comb[j].length() - 1) - '0') < data[0].length - pow) {
								double temp = 1;						
								for(int k = 0; k < comb[j].length(); k++) 							
									temp *= -data[0][(int)(comb[j].charAt(k) - '0')];							
								result += divDif[i] * temp;
							}
						}
					}
				
				coeff[pow] = result;
			}
		}
		System.out.print("\n\nSimplified polynomial is: ");
		for(int i = coeff.length - 1; i >= 0; i--) {
			coeff[i] = (double)Math.round(coeff[i] * 1000) / 1000;
			if(coeff[i] != 0) {
				if(i == coeff.length - 1)
					System.out.print(coeff[i]);
				else if(coeff[i] < 0)
					System.out.print("- " + Math.abs(coeff[i]));
				else
					System.out.print("+ " + Math.abs(coeff[i]));
				if(i == 1) 
					System.out.print("x ");
				else if(i > 1)
					System.out.print("x^" + i + " ");
			}
		}
		System.out.println();
	}
	
	private String[] recursiveComb(String[] comb, int low, int high, int current, int index) {
		for(Integer i = low + 1; i <= high; i++) {
			comb[index] = comb[current] + i.toString();
			recursiveComb(comb, i, high, index, index + combination(high + 1, comb[index].length()));
			recursiveComb(comb, i, high, current++, index + high - low);					
			index++;
		}
		return comb;
	}
	
	private double[] getNextDiff(double[] dTable, int startI, int len) {
		int xIndex = 0, yIndex = startI;
		for(int i = startI; i < startI + len; i++) {
			if(xIndex + data[0].length - len < data[0].length)
				dTable[i] = (dTable[yIndex - len] - dTable[yIndex - len - 1]) / (data[0][xIndex + data[0].length - len] - data[0][xIndex]);
			xIndex++;
			yIndex++;
		}
		for(int i = 0; i < dTable.length; i++)
			dTable[i] = (double)Math.round(dTable[i] * 1000) / 1000;
		return dTable;
	}
	
	private void printTable(double[] dTable) {
		int row = data[0].length + data[0].length - 1, col = data[0].length + 1, index = 0, dIndex = 0, len = 1;
		double[][] table = new double[row][col];
		for(int i = 0; i < row; i++) 
			for(int j = 0; j < col; j++) 
				table[i][j] = -999;
		for(int i = 0; i < data[0].length; i++) {
			if(index >= table.length)
				index = table.length - 1;
			table[index][0] = data[0][i];
			table[index][1] = dTable[dIndex];
			index += 2;
			dIndex++;
		}
		index = len;
		for(int i = 2; i < table[0].length; i++) {
			for(int j = len; j < data[0].length; j++) {
 				table[index][i] = dTable[dIndex];
 				index += 2;
 				dIndex++;
			}
			len++;
			index = len;
		}
		String fx = "f[", deli = "]\t";
		System.out.print("x\t");
		for(int i = 0; i < data[0].length; i++) {
			System.out.print(fx + deli);
			fx += ",";
		}
		System.out.println();
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				if(table[i][j] == -999)
					System.out.print("\t");
				else {
					//System.out.print(table[i][j] + "\t");
					System.out.print(table[i][j] + "\t");
				}	
			}
			System.out.println();
		}
	}
	
	private int combination(int n, int r) {
		return factorial(n) / (factorial(r) * factorial(n - r));
	}
	
	private int factorial(int n) {
		int fac = 1;
		for(int i = 1; i <= n; i++)
			fac *= i;
		return fac;
	}
	
}
