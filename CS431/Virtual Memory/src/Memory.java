import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Memory {

	public static int[][] ram;
	public static VirtualPageTable v_page_table;
	private static CLinkedList clock;
	public static int next_frame;
	private final static int NUM_PAGES = 16;
	private final static int PAGE_SIZE = 256;
	
	public Memory() {
		ram = new int[NUM_PAGES][PAGE_SIZE];
		v_page_table = new VirtualPageTable();
		clock = new CLinkedList();
		next_frame = 0;
	}
	
	public static int get_content(int frame, String offset) {
		return ram[frame][Integer.parseInt(offset, 16)];
	}
	
	public static void write_to_ram(String address, int value) {
		ram[v_page_table.get_frame(address)][Integer.parseInt(address.substring(2, 4), 16)] = value;
		VirtualPageTable.set_bit(address.substring(0, 2), 1, 1);
	}
	
	public static int get_next_frame() {
		return clock.next_frame();
	}
	
	private static String load_page_file(int frame, String file_name) {
		int[] page = new int[PAGE_SIZE];
		String result = "";
		try {
			int offset = 0;
			Scanner page_file = new Scanner(new File("/Users/jundalou/Downloads/Project2_test_and_page_files/page_files/" + file_name + ".pg"));
			while(page_file.hasNext()) {
				page[offset] = Integer.valueOf(page_file.nextLine());
				offset++;
			}
			result = Memory.load_in(file_name, frame, page);
			page_file.close();
		} catch (IOException e) { System.out.println("Page File Not Found"); }
		return result;
	}
	
	public static String get_hard_miss_content(int frame, String address) {
		String page_info = "\t0\t1\t0\t" + load_page_file(frame, address.substring(0, 2));
		return String.valueOf(ram[frame][Integer.parseInt(address.substring(2, 4), 16)]) + page_info;
	}
	
	public static String load_in(String page_num, int frame, int[] page) {
		String result = clock.load_in(page, page_num);
		ram = clock.toArray();
		return result;
	}
	
	public static int load_out(int frame, int offset) {
		return ram[frame][offset];
	}
	
	public void reset() {
		clock.reset();
		ram = clock.toArray();
	}
	
}
