package knapsack.ga;

import java.util.*;

import knapsack.ga.Individual;

public class Population {

	private Vector<Individual> individuals;
	private double avrgFitness;
	

	private double bestFitness;
	private double worstFitness;
	private Individual bestIndividual ;

	public Population() {
		individuals = new Vector<Individual>();
	}

	public Population(int numIndividuals, int chromosomeLength) {
		individuals = new Vector<Individual>();
		bestIndividual = new Individual();
		bestIndividual.setFitnessValue(0.0);
		
		bestFitness = 0.0;
		worstFitness = Double.MAX_VALUE;
		
		for (int i = 0; i < numIndividuals; i++)
			individuals.add(new Individual(chromosomeLength));
	}

	// Roulette selection
	// [Sum] Calculate sum of all chromosome fitnesses in population - sum S.
	// [Select] Generate random number from interval (0,S) - r.
	// [Loop] Go through the population and sum fitnesses from 0 - sum s. When
	// the sum s is greater then r,
	// stop and return the chromosome where you are.

	public Individual rouletteSelection() {
		int i;
		double sumFitness = 0.0;

		// Sum
		for (i = 0; i < individuals.size(); i++) {
			sumFitness = sumFitness + individuals.get(i).getFitnessValue();
		}
		// Select
		Random rand = new Random();
		double rouletteFitness = rand.nextDouble() * sumFitness;

		// Loop
		double partialSumFitness = 0.0;
		for (i = 0; i < individuals.size(); i++) {
			partialSumFitness = partialSumFitness + individuals.get(i).getFitnessValue();
			if (rouletteFitness < partialSumFitness) {
				return individuals.get(i);
			}
		}

		return individuals.get(--i);
	}

	/**
	 * Calculate statistics of population best/worst fitness, average fitness,
	 * best individual
	 */
	public void calculatePopulationStatistics() {
		double sumFitness = 0.0;
		//bestFitness = 0.0;
		//worstFitness = Double.MAX_VALUE;

		for (int i = 0; i < individuals.size(); i++) {
			double fitness = individuals.get(i).getFitnessValue();
			sumFitness += fitness;

			if (bestFitness < fitness) {
				bestFitness = fitness;
				// set the best solution
				bestIndividual.setChromosome((individuals.get(i)).getChromosome());
				bestIndividual.setFitnessValue((individuals.get(i)).getFitnessValue());
				bestIndividual.setTotalValue((individuals.get(i)).getTotalValue());
				bestIndividual.setTotalWeight((individuals.get(i)).getTotalWeight());
			}

			if (fitness < worstFitness )
				worstFitness = fitness;
		}
		avrgFitness = sumFitness / individuals.size();
	}

	
	/**
	 * Create next generation
	 */
	public void generateOffsprings(double crossOverProb, double mutationProb, double eliteRate) {
		
		Vector<Individual> nextGeneration = new Vector<Individual>();
		// sort individuals
		sortDescendingByFitness();
		// select elite individuals
		selectElite(eliteRate, nextGeneration);

		Random rand = new Random();
		for (int i = nextGeneration.size(); i < individuals.size(); i = i+2) {
			Individual parent1 = rouletteSelection();
			Individual parent2 = rouletteSelection();

			// Should we cross over?
			if (rand.nextDouble() < crossOverProb)
				parent1.crossover(parent2);
			
			Individual offspring1 = parent1;
			Individual offspring2 = parent2;
			
			// Should we mutate genes ?
			if (rand.nextDouble() < mutationProb) {
				offspring1.mutateGene();
				offspring2.mutateGene();
			}

			nextGeneration.add(offspring1);
			nextGeneration.add(offspring2);
		}

		// overwrite individuals with next
		individuals = nextGeneration;

	}

	private void selectElite(double eliteRate, Vector<Individual> elites) {
		// select elite individuals
		for (int i = 0; i < individuals.size() * eliteRate; i++) {
			elites.add(individuals.get(i));
		}
	}

	/**
	 * Sort individuals in descending order of fitness
	 */
	private void sortDescendingByFitness() {
		// sort individuals descending by fitness value
		Collections.sort(individuals, new Comparator<Individual>() {
			public int compare(Individual ind1, Individual ind2) {
				double fitnessVal1 = ind1.getFitnessValue();
				double fitnessVal2 = ind2.getFitnessValue();
				return Double.compare(fitnessVal2, fitnessVal1);
			}
		});
	}
	
	
	
	/// Getters and setters
	public Vector<Individual> getIndividuals() {
		return individuals;
	}

	public void setIndividuals(Vector<Individual> individuals) {
		this.individuals = individuals;
	}

	public double getAvrgFitness() {
		return avrgFitness;
	}

	public void setAvrgFitness(double avrgFitness) {
		this.avrgFitness = avrgFitness;
	}

	public double getBestFitness() {
		return bestFitness;
	}

	public void setBestFitness(double bestFitness) {
		this.bestFitness = bestFitness;
	}

	public double getWorstFitness() {
		return worstFitness;
	}

	public void setWorstFitness(double worstFitness) {
		this.worstFitness = worstFitness;
	}

	public Individual getBestIndividual() {
		return bestIndividual;
	}

	public void setBestIndividual(Individual bestIndividual) {
		this.bestIndividual = bestIndividual;
	}
}
