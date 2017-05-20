package knapsack.neighborhood;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;

import knapsack.ga.Knapsack;

public class KnapsackNSearch {
	private Knapsack knapsack;
	public static int value = 0;
	
	public KnapsackNSearch(Knapsack knapsack) {
		this.knapsack = knapsack;
		//this.current = new Selection(knapsack.getNumberOfItems());
		//this.next = new Selection(knapsack.getNumberOfItems());
	}

	public Knapsack getKnapsack() {
		return this.knapsack;
	}

	public int getNumberOfItems() {
		return this.knapsack.getNumberOfItems();
	}
	
	public int[] getWeights() {
		return knapsack.getWeights();
	}
	
	public int[] getValues() {
		return knapsack.getValues();
	}
	
	public int getCapacity() {
		return (int)knapsack.getCapacity();
	}
	
	/*public double evaluate(Selection selection) {
		int weight = 0;
		int currentValue = 0;
		int[] selected = selection.getItemsInBag();
		int[] weights = this.getWeights();
		int[] values = this.getValues();
		
		for (int i = 0; i < getNumberOfItems(); i++) {
			weight = weight + selected[i] * weights[i];
			currentValue = currentValue + selected[i] * values[i];
		}
		if (weight > getCapacity())
			return 0;
		return (double)currentValue/weight;
	}
*/
	public Selection initialSelection(int numberOfItems) {
		Random rand = new Random();
		int x = rand.nextInt(numberOfItems);
		
		BitSet selectedItems = new BitSet(numberOfItems);
		for (int i=0; i < numberOfItems; i++) {
			if (x == i) {
				selectedItems.set(i);
				System.out.print("true ");
			}
		}
		
		Selection selection = new Selection(selectedItems);
		this.calculatePriceAndWeight(selection);
		
		return selection;
	}
	
	/*private Selection getNeighbour() {
		Random rand = new Random();
		int x = rand.nextInt(knapsack.getNumberOfItems());
		
		(next.getItemsInBag())[x] = ((next.getItemsInBag())[x] == 1) ? 0:1;
		calculatePriceAndWeight(next);
		
		return next;
	}
	*/
	public Selection getBestNeighbour(Selection selection) {
		
			ArrayList<Selection> neighbours = new ArrayList<Selection>();
			Selection best = new Selection(selection.getSelectedItems());
			best.setValue(selection.getValue());
			best.setWeight(selection.getWeight());
			
			for( int i=0; i < knapsack.getNumberOfItems(); i++) {
				System.out.println("STEP :" + i);
				if (i == 0) {
					BitSet rightNeighbour = (BitSet) (selection.getSelectedItems()).clone();
					rightNeighbour.flip(i+1);
					Selection right = new Selection(rightNeighbour);
					this.calculatePriceAndWeight(right);
					System.out.println("Price and weight : " + right.getValue() + " --- " + right.getWeight());
					
					if (right.getWeight() <= knapsack.getCapacity()) {
						// is a valid solution
						/*if (((double)right.value/ right.weight) > (best.getValue()/ best.getWeight())) {
							best = right;
						}
						*/
						if(right.getValue() > best.getValue()) {
							best.setSelectedItems(right.getSelectedItems());
							best.setWeight(right.getWeight());
							best.setValue(right.getValue());
							System.out.println("RIGHT ACCEPTED");
							
						} else if (right.getValue() == best.getValue() && (((double)right.getValue()/ right.getWeight()) > (best.getValue()/ best.getWeight()))) {
							best.setSelectedItems(right.getSelectedItems());
							best.setWeight(right.getWeight());
							best.setValue(right.getValue());
							System.out.println("RIGHT ACCEPTED 2");
						}
						
					}
					
					neighbours.add(right);
					
				} else if (i == knapsack.getNumberOfItems() - 1) {
					BitSet leftNeighbour = (BitSet) (selection.getSelectedItems()).clone();
					leftNeighbour.flip(i-1);
					Selection left = new Selection(leftNeighbour);
					this.calculatePriceAndWeight(left);
					System.out.println("Left Price and weight : " + left.getValue() + " --- " + left.getWeight());
					
					if (left.getWeight() <= knapsack.getCapacity()) {
						// is a valid solution
						/*if (((double)left.value/ left.weight) > (best.getValue()/ best.getWeight())) {
							best = left;
						}
						*/
						if(left.getValue() > best.getValue()) {
							System.out.println("LEFT ACCEPTED");
							best.setSelectedItems(left.getSelectedItems());
							best.setWeight(left.getWeight());
							best.setValue(left.getValue());
							
						} else if (left.getValue() == best.getValue() && (((double)left.getValue()/ left.getWeight()) > (best.getValue()/ best.getWeight()))) {
							best.setSelectedItems(left.getSelectedItems());
							best.setWeight(left.getWeight());
							best.setValue(left.getValue());
							System.out.println("lEFT ACCEPTED");
						}
					}
					
					neighbours.add(left);
				} else {
					BitSet leftNeighbour = (BitSet) (selection.getSelectedItems()).clone();
					leftNeighbour.flip(i-1);
					
					BitSet rightNeighbour = (BitSet)(selection.getSelectedItems()).clone();
					rightNeighbour.flip(i+1);
					
					System.out.println("TEST __ L:"+leftNeighbour.get(i-1)+"____"+ (selection.getSelectedItems()).get(i) + "____" +"___R:" +rightNeighbour.get(i+1));
					
					Selection right = new Selection(rightNeighbour);
					this.calculatePriceAndWeight(right);
					
					Selection left = new Selection(leftNeighbour);
					this.calculatePriceAndWeight(left);
					
					System.out.println("Left Price and weight : " + left.getValue() + " --- " + left.getWeight());
					System.out.println("RIght Price and weight : " + right.getValue() + " --- " + right.getWeight());

					
					if (left.getWeight() <= knapsack.getCapacity()) {
						// is a valid solution
						/*if (((double)left.value/ left.weight) > (best.getValue()/ best.getWeight())) {
							best = left;
						}*/
						if(left.getValue() > best.getValue()) {
							best.setSelectedItems(left.getSelectedItems());
							best.setWeight(left.getWeight());
							best.setValue(left.getValue());
							System.out.println("left ACCEPTED");
							
						} else if (left.getValue() == best.getValue() && (((double)left.getValue()/ left.getWeight()) > (best.getValue()/ best.getWeight()))) {
							best.setSelectedItems(left.getSelectedItems());
							best.setWeight(left.getWeight());
							best.setValue(left.getValue());
							System.out.println("left ACCEPTED");
						}
						
					}

					if (right.getWeight() <= knapsack.getCapacity()) {
						// is a valid solution
						/*if (((double)right.value/ right.weight) > (best.getValue()/ best.getWeight())) {
							best = right;
						}*/
						if(right.getValue() > best.getValue()) {
							best.setSelectedItems(right.getSelectedItems());
							best.setWeight(right.getWeight());
							best.setValue(right.getValue());
					
							System.out.println("RIGHT ACCEPTED");
						} else if (right.getValue() == best.getValue() && (((double)right.getValue()/ right.getWeight()) > (best.getValue()/ best.getWeight()))) {
							best.setSelectedItems(right.getSelectedItems());
							best.setWeight(right.getWeight());
							best.setValue(right.getValue());
							System.out.println("RIGHT ACCEPTED");
						}
						
					}
					
					neighbours.add(left);
					neighbours.add(right);
				}
				
				calculatePriceAndWeight(best);
				System.out.println("Solution maximum value/ weight = " + best.getValue() + "/" + best.getWeight());
			}
			
			if (selection.equals(best)) {
				System.out.println(" null null null ");
				return null;
			}
			/*recalculate values*/
			return best;
	}
	
	private void calculatePriceAndWeight(Selection next) {
		int newPrice = 0, newWeight = 0;
		for (int i=0; i<knapsack.getNumberOfItems(); i++) {
			newPrice = newPrice + (((next.getSelectedItems()).get(i)) ? (knapsack.getValues())[i] : 0);
			newWeight = newWeight + (((next.getSelectedItems()).get(i)) ? (knapsack.getWeights())[i] : 0);
		}
		next.setValue(newPrice);
		next.setWeight(newWeight);
	}
	
	
	public static void main(String[] args) {
		Knapsack knapsack = Knapsack.parseInputFile("DS8.txt");
		KnapsackNSearch neighborhoodSearch = new KnapsackNSearch(knapsack); 
		
		Selection initial =  neighborhoodSearch.initialSelection(knapsack.getNumberOfItems());
		
		System.out.println("Solution initial value/ weight = " + initial.getValue() + "/" + initial.getWeight());
		
		Selection next = null;
		Selection best = initial;
		
		next = neighborhoodSearch.getBestNeighbour(initial);
		
		while(next != null ) {
			best = new Selection((BitSet)next.getSelectedItems().clone());
			best.setValue(next.getValue());
			best.setWeight(next.getWeight());
			
			next = neighborhoodSearch.getBestNeighbour(next);
		}
		
		System.out.println("Solution maximum value/ weight = " + best.getValue() + "/" + best.getWeight());
		for(int i=0; i< knapsack.getCapacity(); i++)
		System.out.print("_"+(best.getSelectedItems()).get(i));
	}

}
