import java.io.FileWriter;
import java.io.IOException;

public class OS {

	public static Memory memory;
	public CPU cpu;
	private static String page_folder_path;

	
	public OS(String pfp) {
		memory = new Memory();
		cpu = new CPU();
		page_folder_path = pfp;
	}
	
	public static void reset() {
		memory.reset();
	}
	
	public static void write_to_disk(PageTableEntry dirty_page) {
		try {
			//FileWriter new_page_file = new FileWriter("/Users/jundalou/Downloads/Project2_test_and_page_files/page_files/" + dirty_page.page_num + ".pg");
			FileWriter new_page_file = new FileWriter(page_folder_path + dirty_page.page_num + ".pg");
			for(int i = 0; i < 256; i++)
				new_page_file.write(Memory.load_out(dirty_page.frame, i) + "\n");
			new_page_file.close();
		} catch (IOException e) { System.out.println("This is impossible"); }
	}
	
	public static void write_to_disk(TLBEntry dirty_page) {
		try {
			//FileWriter new_page_file = new FileWriter("/Users/jundalou/Downloads/Project2_test_and_page_files/page_files/" + dirty_page.page_num + ".pg");
			FileWriter new_page_file = new FileWriter(page_folder_path + dirty_page.page_num + ".pg");
			for(int i = 0; i < 256; i++)
				new_page_file.write(Memory.load_out(dirty_page.frame, i) + "\n");
			new_page_file.close();
		} catch (IOException e) { System.out.println("This is impossible"); }
	}
	
}
