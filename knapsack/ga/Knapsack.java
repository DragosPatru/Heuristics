package knapsack.ga;
import java.io.*;

public class Knapsack {
	// N - the number of available objects
    private int numberOfItems = 0;
    // G - maximum weight that the knapsack can carry
    private int capacity = 0;
    
    private String[] itemsId;
    private int[] weights;
    private int[] values;


    Knapsack( int numberOfItems, int capacity ) {
        this.numberOfItems = numberOfItems;
        this.itemsId = new String[numberOfItems + 1];
        this.weights = new int[numberOfItems + 1];
        this.values = new int[numberOfItems + 1];
        this.capacity = capacity;

        //System.out.println( "numItems=" + numItems + ", capacity=" + capacity );
    }

    /**
     * File format :
     * - knapsack capacity
     * - total number of items
     * - multiple lines: (itemId, itemWeight, itemValue) ...
     * */
    public static Knapsack parseInputFile( String file ) {
        Knapsack knapsack = null;

		try {
			BufferedReader in = new BufferedReader( new FileReader( file ) );
			int numItems = Integer.parseInt( in.readLine() );
			int capacity = Integer.parseInt( in.readLine() );

			knapsack = new Knapsack( numItems, capacity );

			int i = 0;
			String str;
	    	while ( (str = in.readLine() )!= null ) {
                String[] item = str.split( "," );
                knapsack.itemsId[i] = item[0];
                knapsack.weights[i] = Integer.parseInt( item[1] );
                knapsack.values[i] = Integer.parseInt( item[2] );

                i++;
			}
			in.close();
		} catch ( IOException ex ) {
			System.err.println( "Error: Can't open the file " + file + " for reading." );
		}

        return knapsack;
    }    
    
   

    
	public int getNumberOfItems() {
		return numberOfItems;
	}

	public void setNumberOfItems(int numberOfItems) {
		this.numberOfItems = numberOfItems;
	}

	public double getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String[] getItemsId() {
		return itemsId;
	}

	public void setItemsId(String[] itemsId) {
		this.itemsId = itemsId;
	}

	public int[] getWeights() {
		return weights;
	}

	public void setWeights(int[] weights) {
		this.weights = weights;
	}

	public int[] getValues() {
		return values;
	}

	public void setValues(int[] values) {
		this.values = values;
	}
    

}