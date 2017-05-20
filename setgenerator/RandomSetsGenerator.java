package setgenerator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class RandomSetsGenerator {

	public static final String baseName = "DS";
	public RandomSetsGenerator()
	{
	}
	public static void generateKnapsack(int numberOfItems, int weightRange, int profitRange)
	{
		
		Random generator = new Random();
		 try {
	            FileWriter writer = new FileWriter(baseName + numberOfItems + ".txt", false);
	            writer.write(String.valueOf(numberOfItems));
	            writer.write("\r\n");   // write new line
	            writer.write(String.valueOf(weightRange));
	          
	            for(int i = 1; i <= numberOfItems; i++)
	            {	
	            	writer.write("\r\n"); 
	            	int itemId = i;
	            	int weight = generator.nextInt((int)(weightRange/2))+1;
	            	int profit = generator.nextInt(profitRange)+1;
	            	writer.write(itemId +"," +weight+","+profit); 
	            }
	            writer.write("\r\n"); 
	            writer.close();
	       
		 } catch (IOException e) {
	            e.printStackTrace();
	     }
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int [] files = {8, 10, 50, 100};
		
		for (int i = 0; i < files.length; i++) {
			RandomSetsGenerator.generateKnapsack(files[i], (int) (files[i] * 1.5), 200);
		}
	}

}
