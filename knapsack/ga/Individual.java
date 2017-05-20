package knapsack.ga;

import java.util.Random;

/**
 * Resepresents a posible solution
 * */
public class Individual {
	// a chromosome - is represented as a binary string like '10100001...'
	// The zeros and ones are genes; 1 - tell us that the item is selected
	// into the knapsack and 0 - otherwise
	private boolean[] chromosome;
	// the quality of each chromosome
	private double fitnessValue;
	private double totalValue;
	private double totalWeight;
	
	public Individual() {
		chromosome = null;
		fitnessValue = 0.0;
	}
	
	public Individual(int chromosomeLength) {
		generateRandomChromosome(chromosomeLength);
	}
	
	private void generateRandomChromosome(int length) {
		chromosome = new boolean[length];
		Random random = new Random();
		double thresholdValue = random.nextDouble();
		
		for (int cnt = 0; cnt < chromosome.length; cnt ++){
			chromosome[cnt] = (random.nextDouble()  < thresholdValue) ? true : false;
		}
	}
	
	void mutateGene() { 
		Random rand = new Random();
		// gene to mutate
		int pos = rand.nextInt(chromosome.length);
		chromosome[pos] = chromosome[pos] ? false : true;
	}
	
	/**
	 * Cross over chromosomes of this and the other parent
	 * @return the other modified parent or null if no changes
	 * */
	public void crossover (Individual otherParent) {
		Random rand = new Random();
		
		boolean[] chromosomeChild1 = new boolean[chromosome.length];
		boolean[] chromosomeChild2 = new boolean[chromosome.length];
		
		// Generate a random position
		int crossPos = rand.nextInt(chromosome.length);
		
		// exchange bits after crossover point => 2 children
		// child one & two
		for (int i = 0; i < crossPos; i++) {
			chromosomeChild1[i] = (this.getChromosome())[i];
			chromosomeChild2[i] = (otherParent.getChromosome())[i];
		}
		
		for (int i = crossPos; i < chromosome.length; i++) {
			chromosomeChild2[i] = (this.getChromosome())[i];
			chromosomeChild1[i] = (otherParent.getChromosome())[i];
		}

		// new generation children
		this.setChromosome(chromosomeChild1);
		otherParent.setChromosome(chromosomeChild2);
	}	
	
	// Getters and setters
	public boolean[] getChromosome() {
		return chromosome;
	}
	
	public void setChromosome(boolean[] chromosome) {
		this.chromosome = chromosome;
	}

	public double getFitnessValue() {
		return fitnessValue;
	}

	public void setFitnessValue(double fitnessValue) {
		this.fitnessValue = fitnessValue;
	}

	public double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(double totalValue) {
		this.totalValue = totalValue;
	}

	public double getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
	}
	
	/*
	void crossover (Individual firstParent, Individual secondParent) {
		Random random = new Random();
		int crossPos = random.nextInt(firstParent.getChromosome().length);
		
		for (int i = 0; i < crossPos; i++) {
			chromosome[i] = (firstParent.getChromosome())[i];
		}
		for (int i = crossPos; i < chromosome.length; i++) {
			chromosome[i] = (secondParent.getChromosome())[i];
		}
	}
	*/

	public void print() {
		 for (int i = 0; i < chromosome.length; i++ ) {
	            if (chromosome[i])
	                System.out.print( "1" );
	            else
	                System.out.print( "0" );
	        }
	        System.out.println( ", totalValue = " + totalValue + ", totalWeight = " + totalWeight );
		
	}
}
