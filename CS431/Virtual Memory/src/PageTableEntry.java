
public class PageTableEntry {
	
	public int valid;
	public int referenced;
	public int dirty;
	public String page_num;
	public int frame;
	
	
	public PageTableEntry(int v, int r, int d, int f, String p) {
		valid = v;
		referenced = r;
		dirty = d;
		frame = f;
		page_num = p;
	}

}
