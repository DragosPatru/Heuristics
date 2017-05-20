package knapsack.ga;

import java.util.Vector;

public class KnapsackGA {
	   // GA parameters
    final static double GA_ELITE_RATE = 0.10;
    final static double GA_MUTATION_PROB = 0.10;
    final static double GA_CROSSOVER_PROB = 0.8;

    private Knapsack knapsack;
    
    public KnapsackGA() {
    	knapsack = Knapsack.parseInputFile( "DS10.txt" );
    }
    
    /**
     * Calculate fitness of Individual
     * Based on capacity, total weight & total value
     * */
    public double calculateFitness( Individual individual ) {
    	
        double totalValue = 0.0, totalWeight = 0.0;
        double fitness = 0.0;
        boolean overweight = false;
        boolean[] solution = individual.getChromosome();

        int[] weights = this.knapsack.getWeights();
        int[] values = this.knapsack.getValues();
        double capacity = this.knapsack.getCapacity();
        
        for (int i = 0; i < solution.length; i++) {
            if (solution[i]) {
                totalValue += values[i];
                totalWeight += weights[i];
            }
            if (capacity < totalWeight) {
                overweight = true;
                break;
            }
        }

        fitness = (overweight) ? 0.0 : totalValue;

        // System.out.println( "fitness=" + fitness + ", totalValue=" + totalValue + ", totalWeight=" + totalWeight );

        // set results on individual
        individual.setFitnessValue( fitness );
        individual.setTotalWeight( totalWeight );
        individual.setTotalValue( (int)fitness );

        return fitness;
    }
    
    public Knapsack getKnapsack() {
    	return this.knapsack;
    }
    
    public static void main( String[] args ) {
		if ( args.length < 2 ) {
			System.out.println( "Usage: java knapsack.KnapsackGaSolver <problem file> <# iterations> <# individuals>" );
			return;
		}

        // parse the problem file
        ///Knapsack knapsack = Knapsack.parseInputFile( "DS10.txt" );
        KnapsackGA knapsackGA = new KnapsackGA();
		
        int numberOfIterations = Integer.parseInt( args[0] );
        //  Good population size is about 20-30, however sometimes sizes 50-100 are reported as best
        int numberOfIndividuals = Integer.parseInt( args[1] );

        // create a population consists of individuals
        // Requires: number of individuals and the chromosome length
        // Generate initial population
        Population population = new Population( numberOfIndividuals, (knapsackGA.getKnapsack()).getNumberOfItems());
        
        // start evaluating the individuals
        for (int i = 0; i < numberOfIterations; i++) {
            System.out.println("__________Iteration number = "+ i);
            
        	Vector<Individual> individuals = population.getIndividuals();
        	System.out.println("__________Calculate fitness of Individuals ");
            for (int j = 0; j < individuals.size(); j++) {
                Individual individual = (Individual)individuals.get( j );
                knapsackGA.calculateFitness( individual );
            }
            
            System.out.println("__________Calculate Population's statistics");
            // Calculate population statistics
            population.calculatePopulationStatistics();
            System.out.println("_________Result :");
            System.out.println( "__________Iteration = " + i +
                                ", Fitness (best = " + population.getBestFitness() +
                                ", avrg = " + population.getAvrgFitness() +
                                ", worst = " + population.getWorstFitness() + ")" );
            
            System.out.println("_________Create a new generation :");
            // new generation
            population.generateOffsprings(GA_CROSSOVER_PROB, GA_MUTATION_PROB, GA_ELITE_RATE);
        }

        // finally, show results
        Individual bestSolution = population.getBestIndividual();
        System.out.println();
        System.out.println("_________Final Result :");
        System.out.println( "Maximum Knapsack Weight: " +  (knapsackGA.getKnapsack()).getCapacity() );
        System.out.println( "Total Item Weight: " + bestSolution.getTotalWeight() );
        System.out.println( "Total Item Value: " + bestSolution.getTotalValue() );
        System.out.println( "Selected Items (item ID, weight, value):" );

        String[] ids = (knapsackGA.getKnapsack()).getItemsId();
        int[] weights = (knapsackGA.getKnapsack()).getWeights();
        int[] values = (knapsackGA.getKnapsack()).getValues();
        boolean[] chromosome = bestSolution.getChromosome();
        
        for (int i = 0; i < chromosome.length; i++) {
            if (chromosome[i]) 
                System.out.println( ids[i] + ", " + weights[i] + ", " + values[i] );
        }
    }
}
