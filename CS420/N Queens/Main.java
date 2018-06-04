
public class Main {

	public static void main(String[] args) {
		int count_rrhc = 0, count_ga = 0;
		RandomRestartHillClimbing rrhc = new RandomRestartHillClimbing(21);
		GeneticAlgorithm ga = new GeneticAlgorithm(21);
		for(int i = 0; i < 200; i++) {
			if(rrhc.solve())
				count_rrhc++;
			if(ga.solve())
				count_ga++;
		}
		System.out.println("Random Restart solved: " + count_rrhc + "\nPercentage: " + (double)count_rrhc / 200);
		System.out.println("Genetic solved: " + count_ga + "\nPercentage: " + (double)count_ga / 200);
	}

}
