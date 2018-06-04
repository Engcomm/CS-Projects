public class CLinkedList {
	
	public Node hand, tail;
	private int len;
	private final int SIZE = 16;
	
	public CLinkedList() {	
		hand = null;
		tail = null;
		len = 0;
	}
	
	public String load_in(int[] newPage, String page_num) {
		String result = "-1\t-1\n";
		if(len == 0) {
			Node newNode = new Node(newPage, len, page_num);
			hand = newNode;
			tail = newNode;
			len++;
		} else if(len < SIZE) {
			Node newNode = new Node(newPage, len, page_num);
			newNode.next = hand;
			tail.next = newNode;
			tail = newNode;
			len++;
		} else {
			Node newNode = new Node(newPage, hand.frame, page_num);
			if(VirtualPageTable.get_page(page_num).dirty == 1) {
				result = hand.frame + "\t1\n";
				OS.write_to_disk(VirtualPageTable.get_page(page_num));
			} else
				result = hand.frame + "\t0\n";
			newNode.next = hand.next;
			tail.next = newNode;
			hand = newNode;
		}
		return result;
	}
	
	public int next_frame() {
		if(len < SIZE)
			return len;
		else {
			while(true) {
				if(VirtualPageTable.get_page(hand.page_num).referenced == 0)
					return hand.frame;
				else
					VirtualPageTable.set_bit(hand.page_num, 0, 0);
				hand = hand.next;
				tail = tail.next;
			}
		}
	}
	
	public int[][] toArray() {
		Node current = hand;
		int[][] result = new int[SIZE][];
		for(int i = 0; i < len; i++) {
			result[i] = hand.data;
			current = current.next;
		}
		return result;
	}
	
	public void reset() {
		Node current = hand;
		for(int i = 0; i < SIZE; i++) {
			if(VirtualPageTable.get_page(current.page_num).dirty == 1) {
				OS.write_to_disk(VirtualPageTable.get_page(current.page_num));
				VirtualPageTable.set_bit(current.page_num, 0, 1);
			}
			VirtualPageTable.set_bit(current.page_num, 0, 0);
			current = current.next;
		}
	}
	
	private class Node {
		
		private int[] data;
		private int frame;
		private String page_num;
		private Node next;
		
		private Node(int[] data, int frame, String page_num) {
			this.data = data;
			this.frame = frame;
			this.page_num = page_num;
		}
		
	}
}





