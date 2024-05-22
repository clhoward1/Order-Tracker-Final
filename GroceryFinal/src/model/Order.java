package model;
import java.util.LinkedList;

public class Order {
	
	private String orderID;
	private int hourDue;
	private Customer customer;
	private LinkedList<Item> itemOrder = new LinkedList<Item>();
	private boolean isCompleted = false;
	

	public Order(Customer customer) {
		this.customer = customer;
	}
	
	public Order(String orderID, int hourDue, Customer customer) {
		this.orderID = orderID;
		this.hourDue = hourDue;
		this.customer = customer;
	}

	
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	
	
	public int getHourDue() {
		return hourDue;
	}
	public void setHourDue(int orderDue) {
		this.hourDue = orderDue;
	}


	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	
	public LinkedList<Item> getItemOrder() {
		return itemOrder;
	}
	public void setItemOrder(LinkedList<Item> itemOrder) {
		this.itemOrder = itemOrder;
		sortItems();
	}
	
	
	public boolean isCompleted() {
		return isCompleted;
	}
	public void setIsCompleted(boolean completed) {
		this.isCompleted = completed;
	}
	
	
	public String getItemsInOrder() {
		String output = "";
		for (Item item : itemOrder) {
			output += item.getName() + ", ";
		}
		return output;
	}
	
	
	/**
	 * Sorts items by name alphabetically using insertion sort
	 */
	private void sortItems() {
        for (int i = 1; i < itemOrder.size(); i++) {
            Item key = itemOrder.get(i); // current item to get compared to
            int j = i - 1;
            
            /**
             * Moves items to right by one if they are greater than key
             */
            while (j >= 0 && itemOrder.get(j).getName().compareTo(key.getName()) > 0) {
                itemOrder.set(j + 1, itemOrder.get(j)); // swaps j to the right by one
                j = j - 1;
            }
            /**
             * Inserts key item at correct position
             */
            itemOrder.set(j + 1, key);
        }
    }
	

}
