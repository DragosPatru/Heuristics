package knapsack.neighborhood;

import java.util.BitSet;

public class Selection {
    private int value ;
    private int weight;
    private BitSet selectedItems;
    
    public Selection(BitSet selected) {
    	this.selectedItems = selected;
    }
    
    public Selection(Selection basedOn) {
    	this.value = basedOn.getValue();
    	this.weight = basedOn.getWeight();
    	this.selectedItems = (BitSet) basedOn.getSelectedItems().clone();
    }
    
    public Selection () {
    	this.selectedItems = new BitSet();
    }
    
    public BitSet getSelectedItems() {
		return selectedItems;
	}

	public void setSelectedItems(BitSet selectedItems) {
		this.selectedItems = selectedItems;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public boolean equals(Selection s) {
		BitSet otherSelection = s.getSelectedItems();
		for(int i=0; i< selectedItems.size(); i++) {
			if(otherSelection.get(i) != selectedItems.get(i)) {
				return false;
			}
		}
		
		return true;
	}
}
