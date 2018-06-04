import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		OS os = new OS(args[1]);
		//os.cpu.read_instructions("/Users/jundalou/Downloads/Project2_test_and_page_files/test_files/test_4.txt");
		os.cpu.read_instructions(args[0]);
	}

}
