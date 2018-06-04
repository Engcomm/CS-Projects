
public class TLBEntry {

	public String page_num;
	public int valid;
	public int referenced;
	public int dirty;
	public int frame;
	
	public TLBEntry(int v, int r, int d, int f, String p) {
		valid = v;
		referenced = r;
		dirty = d;
		frame = f;
		page_num = p;
	}
	
	
}
