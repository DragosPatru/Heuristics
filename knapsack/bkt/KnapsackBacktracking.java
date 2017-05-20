package knapsack.bkt;

import knapsack.ga.*;

public class KnapsackBacktracking {
	private Knapsack knapsack;

	private static boolean[] selection;
	private static boolean[] best_solution;
	private static int max_value = 0;
	private static int max_weight = 0;
	private static int partial_weight = 0;
	private static int noSolutions = 0;

	public KnapsackBacktracking() {
		this.knapsack = Knapsack.parseInputFile("DS10.txt");
	}

	public Knapsack getKnapsack() {
		return this.knapsack;
	}

	public static void main(String[] args) {
		String file = "DS8.txt";
		KnapsackBacktracking knapsackBK = new KnapsackBacktracking();

		int numberOfItems = (knapsackBK.getKnapsack()).getNumberOfItems();
		// 1 - is into the knapsack
		// 0 - otherwise
		selection = new boolean[numberOfItems];
		best_solution = new boolean[numberOfItems];
		for (int i = 0; i < numberOfItems; i++) {
			selection[i] = false;
			best_solution[i] = false;
		}

		// call backtracking
		knapsackBK.backtracking(0);

		System.out.println("Best solution is for max capacity -> " + (knapsackBK.getKnapsack()).getCapacity());
		System.out.println("max_weight = " + max_weight + " ...max_value = " + max_value);

	}

	public void backtracking(int k) {
		for (int i = 0; i <= 1; i++) {
			boolean selected = (i == 0) ? false : true;
			selection[k] = selected;
			if (isValid(k)) {
				if (k == (this.getKnapsack()).getNumberOfItems() - 1) {
					optim(); // save the best solution
				} else {
					backtracking(k + 1);
				}
			}
		}
	}

	public boolean isValid(int k) {
		int[] weights = (this.getKnapsack()).getWeights();
		int capacity = (int) (this.getKnapsack()).getCapacity();
		int partialWeight = 0;
		for (int j = 0; j < k; j++) {
			if (selection[j]) {
				partialWeight = partialWeight + weights[j];
			}
		}

		if (partialWeight <= capacity) {
			return true;

		} else
			return false;
	}

	public void optim() {
		int numberOfItems = (this.getKnapsack()).getNumberOfItems();
		int[] values = (this.getKnapsack()).getValues();
		int[] weights = (this.getKnapsack()).getWeights();
		int capacity = (int) (this.getKnapsack()).getCapacity();

		int value = 0;
		int weight = 0;
		noSolutions = noSolutions + 1;
		for (int i = 0; i < numberOfItems; i++) {
			if (selection[i]) {
				value = value + values[i];
				weight = weight + weights[i];
			}
		}

		if ((noSolutions == 0) || (weight <= capacity)) {
			if (value > max_value) { // choose the max value
				max_value = value;
				max_weight = weight;
				best_solution = selection;
			}
		}
	}
}
