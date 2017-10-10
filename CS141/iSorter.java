// Lou, Junda
// CS 141 03
// Project #3 : Line Sorter
//

public class iSorter extends Sorter{
    
	private String[] input;
	
    public iSorter(String[] input) {
	super(input);
    }

    protected String[] iSort(String[] input, int bottom) {
	input = super.sort(input, bottom);
	return input;
    }
	  
    protected int getMax(String[] input, int bottom, int assumedMax, int current) {
	if (current <= bottom) {
	    if (input[assumedMax].compareToIgnoreCase( input[current] ) < 0)
		return getMax(input, bottom, current, current + 1);
	    else
		return getMax(input, bottom, assumedMax, current + 1);
	}
	return assumedMax;
    }

}