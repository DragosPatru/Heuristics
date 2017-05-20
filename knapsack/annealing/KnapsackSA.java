package knapsack.annealing;

import java.util.BitSet;
import java.util.Random;

import knapsack.ga.Knapsack;
import knapsack.neighborhood.Selection;

public class KnapsackSA {

	private Knapsack knapsack;
	private double initialTemperature;
	private double endingTemperature;
	private double temperature;
	private double coolingRatio;// or cooling factor
	
	// number of cycles in a iteration // number of steps
	private int cyclesPerIteration;
	
	private Selection stateSelection;
	private Selection bestSelection;
	
	public KnapsackSA (Knapsack knp, double iTemp, double eTemp, double coolingRatio) {
		this.knapsack = knp;
		this.initialTemperature = iTemp;
		this.endingTemperature = eTemp;
		this.coolingRatio = coolingRatio;
	}

	public KnapsackSA (Knapsack knp, double eTemp, double coolingRatio) {
		this.knapsack = knp;
		this.endingTemperature = eTemp;
		this.coolingRatio = coolingRatio;
		this.initialTemperature = setInitTemperature();
	}
	
	/**
	 * Set the initial set => no items in bag
	 * */
	private void setInitialState() {
		stateSelection.setSelectedItems(BitSet.valueOf(new long[]{0}));
		stateSelection.setValue(0);
		stateSelection.setWeight(0);
	}
	
	/**
	 * Move to next state
	 * */
	private Selection getNextState() {
		
		Selection newStateSelection = new Selection();
		
		do {
			newStateSelection = this.getNeighbour();
		
		} while(newStateSelection.getWeight() > knapsack.getCapacity()) ;
		
		double bestPricePolicy = newStateSelection.getValue() - stateSelection.getValue();
		
		if (bestPricePolicy > 0) {
			// indeed the new state is better 
			return newStateSelection;
		
		} else {
			// from wiki : If P( E(s), E(snew), T) ≥ random(0, 1):
			//				s ← snew
			// \exp(-(e'-e)/T) -> acceptance probability function
			
			double random01 = Math.random(); // value between 0 and 1
			double acceptanceProbability = Math.exp(bestPricePolicy/ temperature);
			
			if (random01 < acceptanceProbability) {
				return newStateSelection;
				
			} else {
				return stateSelection;
			}
		}
		
	}
	
	
	private Selection getNeighbour() {
		Selection newSelection = new Selection(stateSelection);
		Random rand = new Random();
		
		int pos = rand.nextInt(knapsack.getNumberOfItems());
		
		// random select/ remove item from knapsack
		newSelection.getSelectedItems().flip(pos);
		// set total value and weight
		this.calculatePriceAndWeight(newSelection);
		
		return newSelection;
	}
	
	
	private void calculatePriceAndWeight(Selection selection) {
		int newPrice = 0, newWeight = 0;
		for (int i=0; i<knapsack.getNumberOfItems(); i++) {
			newPrice = newPrice + (((selection.getSelectedItems()).get(i)) ? (knapsack.getValues())[i] : 0);
			newWeight = newWeight + (((selection.getSelectedItems()).get(i)) ? (knapsack.getWeights())[i] : 0);
		}
		selection.setValue(newPrice);
		selection.setWeight(newWeight);
	}
	
	private void startCooling () {
		temperature = temperature * this.coolingRatio;
	}
	
	
	public Selection solve() {
		this.stateSelection = new Selection();
		this.bestSelection = new Selection();
		
		// test
		cyclesPerIteration = knapsack.getNumberOfItems() * 5;
		
		this.setInitialState();
		temperature = initialTemperature;
		
		// iterations
		while (temperature > endingTemperature) {
			// n cycles in a iteration
			for (int m = 0; m < cyclesPerIteration; m++) {
				stateSelection = getNextState();
				
				if (stateSelection.getValue() > bestSelection.getValue())
					bestSelection = stateSelection;
			}
			// start cooling when a iteration is finished
			startCooling();
		}
		
		return bestSelection;
	}
	
	// test method to generate a initial temperature
	private double setInitTemperature() {
		int sumCost = 0, sumWeight = 0;
		
		for (int i = 0; i < knapsack.getNumberOfItems(); i++) {
			sumCost += knapsack.getValues()[i];
			sumWeight += knapsack.getWeights()[i];
		}
		
		double temp = (sumCost / knapsack.getNumberOfItems()) / (sumWeight / knapsack.getCapacity());
		System.out.println("Calculated initial temperature : " + temp);
		return temp; 
	}
	
	public Knapsack getKnapsack() {
		return knapsack;
	}
	
	/**
	 * @param strings
	 */
	public static void main(String[] strings) {
		
		Knapsack knapsack = Knapsack.parseInputFile("DS10.txt");
		KnapsackSA simulatedAnnealing = new KnapsackSA(knapsack, 2, 0.96);
		
		Selection bestSelection = simulatedAnnealing.solve();
		System.out.println("____ Best selection____");	
		System.out.println("Value : " +bestSelection.getValue() + "       Weight: " + bestSelection.getWeight());
		
		String[] ids = (simulatedAnnealing.getKnapsack()).getItemsId();
        int[] weights = (simulatedAnnealing.getKnapsack()).getWeights();
        int[] values = (simulatedAnnealing.getKnapsack()).getValues();
        BitSet selected = bestSelection.getSelectedItems();
        System.out.println("Selected items are : ");
        for (int i = 0; i < selected.length(); i++) {
            if (selected.get(i)) 
                System.out.println( ids[i] + ", " + weights[i] + ", " + values[i] );
        }
	}

	
	
}
