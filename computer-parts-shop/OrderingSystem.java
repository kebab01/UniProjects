
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

class OrderingSystem implements Serializable{

	private ArrayList<Customer> customers;
	private ArrayList<ComputerPart> parts;
	private Order order;
	private Customer currentCustomer;

	public static void main(String[] args){

		OrderingSystem ors = new OrderingSystem();
		ors.export();

	}
	public OrderingSystem(){
		customers = new ArrayList<Customer>();
		parts = new ArrayList<ComputerPart>();
		this.order = null;
		this.currentCustomer = null;
	}
	
	public void export(){

		String filename = "compshop.ser";
		OrderingSystem object = this;

			try{
				FileOutputStream fileout = new FileOutputStream(filename);
				ObjectOutputStream objectOut = new ObjectOutputStream(fileout);

				objectOut.writeObject(object);

				objectOut.close();
				fileout.close();

			}
			catch(IOException e){
				e.printStackTrace();
			}
	}
	public Customer getCustomer(String id){

		for (Customer customer : customers){
			if (customer.getID().equals(id)){
				return customer;
			}
		}
		return null;
	}
	public void setCurrentCustomer(Customer customer){
		currentCustomer = customer;
	}
	public Customer getCurrentCustomer(){
		return currentCustomer;
	}
	public void setOrder(Order order){
		this.order = order;
	}
	public Order getOrder(){
		return order;
	}
	public ComputerPart getPart(int index){
		return parts.get(index);
	}
	public ArrayList<Customer> getCustomers(){
		return customers;
	}
	public ArrayList<ComputerPart> getComputerParts(){
		return parts;
	}
	public void addCustomer(Customer customer){
		customers.add(customer);
	}
	public void addComputerPart(ComputerPart part){
		parts.add(part);
	}
}