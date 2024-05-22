package model;

public class Customer {
	
	private String name;
	private String customerID;
	
	
	public Customer(String name, String id) {
		this.name = name;
		this.customerID = id;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	public String getId() {
		return customerID;
	}
	public void setId(String id) {
		this.customerID = id;
	}


	@Override
	public String toString() {
		return name + ", ID: " + customerID;
	}
	
	
	
	

}
