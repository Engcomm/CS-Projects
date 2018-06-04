import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CPU {
	
	public MMU mmu;
	
	public CPU() {
		mmu = new MMU();
	}
	
	public void read_instructions(String file_path) throws IOException {
		Scanner input = new Scanner(new File(file_path));
		FileWriter output = new FileWriter("output.csv");
		output.write("Address\tR/W\tValue\tSoft\tHard\tHit\tEvicted Page#\tDirty Evicted Page\n");
		int count = 0;
		while(input.hasNext()) {
			if(count == 5) {
				count = 0;
				OS.reset();
			}
			int read_or_write = Integer.valueOf(input.nextLine());
			String address = input.nextLine();
			if(read_or_write == 0) 
				output.write(address + "\t0\t" + mmu.read(address));
			else {
				int value = Integer.valueOf(input.nextLine());
				output.write(address + "\t1\t" + mmu.write(address, value));
			}
			count++;
		}
		input.close();
		output.close();
	}
	

}
