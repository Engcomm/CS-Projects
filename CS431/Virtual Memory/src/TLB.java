
public class TLB {

	public static TLBEntry[] tlb_entries;
	private final int TLB_SIZE = 8;
	private static int head, last_modified;
	private int len;
	
	public TLB() {
		tlb_entries = new TLBEntry[TLB_SIZE];
		head = 0;
		len = 0;
		last_modified = 0;
	}
	
	public void add(TLBEntry newEntry) {
		if(len < TLB_SIZE) {
			for(int i = 0; i < TLB_SIZE; i++) {
				if(tlb_entries[i] == null) {
					tlb_entries[i] = newEntry;
					len++;
					last_modified = i;
					break;
				}
			}
		} else {
			last_modified = head;
			if(tlb_entries[head].dirty == 1)
				OS.write_to_disk(tlb_entries[head]);
			tlb_entries[head] = newEntry;
			if(head == TLB_SIZE - 1)
				head = 0;
			else
				head++;
		}
	}
	
	public String search(String address, int read_or_write) {
		for(int i = 0; i < TLB_SIZE; i++) {
			if(tlb_entries[i] != null && tlb_entries[i].page_num.compareTo(address.substring(0, 2)) == 0)
				return MMU.mapping(String.valueOf(tlb_entries[i].frame), address.substring(2, 4)) + "\t0\t0\t1\t-1\t-1\n";
		}
		if(read_or_write == 0)
			add(new TLBEntry(1, 1, 0, -2, address.substring(0, 2)));
		else
			add(new TLBEntry(1, 1, 1, -2, address.substring(0, 2)));
		return "fail";
	}
	public static void update_frame(int frame) {
		tlb_entries[last_modified].frame = frame;
	}
}
