import java.util.Arrays;

public class NormalMedian {
	
	public static int getMedian(int[] list) {
		Arrays.sort(list);
		if(list.length % 2 == 0)
			return list[list.length / 2 - 1];
		else 
			return list[(list.length - 1) / 2];
	}
	
}
