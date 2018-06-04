
public class MMU {
	
	public TLB tlb;
	
	public MMU() {
		tlb = new TLB();
	}
	
	public String read(String address) {
		String result = tlb.search(address, 0);
		if(result.compareTo("fail") == 0)
			result = Memory.v_page_table.search(address, 0);
		return result;
	}
	
	public String write(String address, int value) {
		String result = tlb.search(address, 1);
		if(result.compareTo("fail") == 0)
			result = Memory.v_page_table.search(address, 1);
		Memory.write_to_ram(address, value);
		return result;	
	}

	public static String mapping(String frame, String offset) {
		return String.valueOf(Memory.get_content(Integer.valueOf(frame), offset));
	}
	
}
