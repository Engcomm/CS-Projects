
public class SuperMedian {

	public static int getMedian(int[] array) {
		return select(array, array.length % 2 == 0 ? (array.length / 2) - 1 : (array.length - 1) /2);
		// odd and even different in "middle"
	}
	
	private static int select(int[] array, int selection) {
		if(array.length <= 5)
			return adhocSelect(array, selection);
		
		int medSize = array.length / 5;
		if(array.length % 5 != 0)
			medSize++;
		int[] medians = new int[medSize];
		
		for(int i = 0; i < medSize; i++) {
			int[] partialArray;
			int index = 0;
			if(5 * i + 4 < array.length)
				partialArray = new int[5];
			else
				partialArray = new int[array.length - 5 * i];
			while(index < partialArray.length) {
				partialArray[index] = array[5 * i + index];
				index++;
			}
			medians[i] = adhocSelect(partialArray, partialArray.length % 2 == 0 ? (partialArray.length / 2) - 1 : (partialArray.length - 1) /2);
		}
		
		int pivotPosition = 0;
		int pivot = select(medians, medSize % 2 == 0 ? (medSize / 2 - 1) : (medSize - 1) / 2);
		array = partition(array, pivot);
		for(int i = 0; i < array.length; i++) {
			if(array[i] == pivot) {
				pivotPosition = i;
				break;
			}
		}
		
		if(selection < pivotPosition) {
			int[] tmpArray = new int[pivotPosition];
			for(int i = 0; i < pivotPosition; i++)
				tmpArray[i] = array[i];
			return select(tmpArray, selection);
		} else if(selection > pivotPosition) {
			int[] tmpArray = new int[array.length - pivotPosition - 1];
			int index = 0;
			for(int i = pivotPosition + 1; i < array.length; i++) {
				tmpArray[index] = array[i];
				index++;
			}
			return select(tmpArray, selection - pivotPosition - 1);
		} else
			return pivot;
	}
	
	private static int[] partition(int[] array, int pivot) {
		int[] result = new int[array.length];
		int index = 0;
		for(int i = 0; i < array.length; i++) {
			if(array[i] < pivot) {
				result[index] = array[i];
				index++;
			}
		}
		for(int i = 0; i < array.length; i++) {
			if(array[i] == pivot) {
				result[index] = array[i];
				index++;
			}
		}
		for(int i = 0; i < array.length; i++) {
			if(array[i] > pivot) {
				result[index] = array[i];
				index++;
			}
		}
		return result;
	}
	
	private static int adhocSelect(int[] array, int selection) {
		int[] sortedArray = adhocSort(array);
		return sortedArray[selection];
	}
	
	private static int[] adhocSort(int[] array) {
		if(array.length == 1)
			return array;
		else if (array.length == 2)
			return adhocSort2(array);
		else if (array.length == 3)
			return adhocSort3(array);
		else if (array.length == 4)
			return adhocSort4(array);
		else 
			return adhocSort5(array);
	}
	
	private static int[] adhocSort2(int[] array) {
		if(array[0] > array[1])
			return swap(array, 0, 1);
		return array;
	}
	
	private static int[] adhocSort3(int[] array) {
		if(array[0] > array[1])
			array = swap(array, 0, 1);
		int[] tmpArray = {array[0], array[1]};
		return insert(tmpArray, array[2]);
	}
	
	private static int[] adhocSort4(int[] array) {
		if(array[0] > array[1])
			array = swap(array, 0, 1);
		if(array[2] > array[3])
			array = swap(array, 2, 3);
		if(array[0] > array[2]) {
			array = swap(array, 0, 2);
			array = swap(array, 1, 3);
		}
		int[] tmpArray = {array[2], array[3]};
		tmpArray = insert(tmpArray, array[1]);
		for(int i = 1; i < 4; i++)
			array[i] = tmpArray[i - 1];
		return array;
	}
	
	private static int[] adhocSort5(int[] array) {
		if(array[0] > array[1])
			array = swap(array, 0, 1);
		if(array[2] > array[3])
			array = swap(array, 2, 3);
		if(array[0] > array[2]) {
			array = swap(array, 0, 2);
			array = swap(array, 1, 3);
		}
		int[] xArray = {array[0], array[2], array[3]};
		xArray = insert(xArray, array[4]);
		array[0] = xArray[0];
		int[] tmpArray = {xArray[1], xArray[2], xArray[3]};
		xArray = insert(tmpArray, array[1]);
		for(int i = 1; i < 5; i++)
			array[i] = xArray[i - 1];
		return array;
	}
	
	private static int[] swap(int[] array, int i1, int i2) {
		int tmp = 0;
		tmp = array[i1];
		array[i1] = array[i2];
		array[i2] = tmp;
		return array;
	}
	
	private static int[] insert(int[] array, int x) {
		int[] result = new int[array.length + 1];
		if(x < array[1]) {
			if(x < array[0]) {
				result[0] = x;
				result[1] = array[0];
				result[2] = array[1];
				if(array.length == 3)
					result[3] = array[2];
				return result;
			} else {
				result[0] = array[0];
				result[1] = x;
				result[2] = array[1];
				if(array.length == 3)   // check later
					result[3] = array[2];
				return result;
			}
		} else {
			if(array.length == 2 || x > array[2]) {
				result[0] = array[0];
				result[1] = array[1];
				if(array.length == 2)
					result[2] = x;
				else {
					result[2] = array[2];
					result[3] = x;
				}
				return result;
			} else {
				result[0] = array[0];
				result[1] = array[1];
				result[2] = x;
				result[3] = array[2];
				return result;
			}
		}
	}
	
}
