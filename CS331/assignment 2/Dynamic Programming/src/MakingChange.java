
public class MakingChange {

	public static int[][] makeChangeDynProg (int n, int[] denom) {
		int[][] change = new int[denom.length][n + 1];  // n + 1 here for all results 0 to n instead of 0 to n-1
		
		for(int i = 0; i < denom.length; i++) {
			for(int j = 0; j < n + 1; j++) {
				if(i == 0 && j < denom[i])  // can't find change use 0
					change[i][j] = 0;
				else if (i == 0)
					change[i][j] = change[0][j - denom[0]] + 1;
				else if (j < denom[i])
					change[i][j] = change[i - 1][j];
				else
					change[i][j] = Math.min(change[i - 1][j], change[i][j - denom[i]] + 1);
			}
		}
		return change;
	}
	
	//--------------------------------------
	
	public static int[] makeChange (int n, int[] denom) {
		int[] change = new int[denom.length];
		int s = 0;
		while(s < n) {
			int i = findNextCoin(denom, n, s);
			if(i == -1) {
				change[0] = -1;
				return change;
			}
			change[i]++;
			s += denom[i];
		}
		return change;
	}
	
	private static int findNextCoin(int[] denom, int n, int s) {
		int denomIndex = denom.length - 1;
		while(true) {
			if(denomIndex < 0)
				return -1;
			if(s + denom[denomIndex] <= n)
				return denomIndex;
			denomIndex--;
		}
	}
}
