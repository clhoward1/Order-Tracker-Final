package model;

public class Item {

	private String name;
	private int amountWanted;
	
	public Item(String name, int amountWanted) {
		this.name = name;
		this.amountWanted = amountWanted;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
	public int getAmountWanted() {
		return amountWanted;
	}
	public void setStock(int amountWanted) {
		this.amountWanted = amountWanted;
	}

	@Override
	public String toString() {
		return "Item: " + name + ", Amount Wanted: " + amountWanted;
	}
}
