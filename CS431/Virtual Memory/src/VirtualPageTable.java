
public class VirtualPageTable {

	public static PageTableEntry[] page_table_entries;
	private final int TABLE_SIZE = 256;
	private static int len;
	
	public VirtualPageTable() {
		page_table_entries = new PageTableEntry[TABLE_SIZE];
		len = 0;
	}  
	
	private void add(PageTableEntry newEntry) {
		page_table_entries[len] = newEntry;
		len++;
	}
	
	public String search(String address, int read_or_write) {
		if(len != 0) {
			for(int i = 0; i < len; i++) {
				if(page_table_entries[i].page_num.compareTo(address.substring(0, 2)) == 0) {
					TLB.update_frame(page_table_entries[i].frame);
					return MMU.mapping(String.valueOf(page_table_entries[i].frame), address.substring(2, 4)) + "\t1\t0\t0\t-1\t-1\n";
				}
			}
		}
		int frame = Memory.get_next_frame();
		TLB.update_frame(frame);
		if(read_or_write == 0)
			add(new PageTableEntry(1, 1, 0, frame, address.substring(0, 2)));
		else
			add(new PageTableEntry(1, 1, 1, frame, address.substring(0, 2)));
		String result = Memory.get_hard_miss_content(frame, address);
		return result;
	}
	
	public int get_frame(String address) {
		for(int i = 0; i < len; i++) {
			if(page_table_entries[i].page_num.compareTo(address.substring(0, 2)) == 0)
				return page_table_entries[i].frame;
		}
		return -1;
	}
	
	public static PageTableEntry get_page(String page_num) {
		for(int i = 0; i < len; i++) {
			if(page_table_entries[i].page_num.compareTo(page_num) == 0)
				return page_table_entries[i];
		}
		return null;
	}
	
	public static void set_bit(String page_num, int value, int r_d) {
		for(int i = 0; i < len; i++) {
			if(page_table_entries[i].page_num.compareTo(page_num) == 0) {
				if(r_d == 0)
					page_table_entries[i].referenced = value;
				else
					page_table_entries[i].dirty = value;
			}
		}
	}

	public String toString() {
		String result = "";
		for(int i = 0; i < len; i++) {
			result += page_table_entries[i].referenced + " " + page_table_entries[i].dirty + " " + page_table_entries[i].frame + " " + page_table_entries[i].page_num + "\n";
		}
		return result;
	}
	
	public int getLen() {
		return len;
	}
	
	
}
