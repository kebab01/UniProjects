
import java.lang.Math;
import java.util.ArrayList;
import java.io.*;

class Customer implements Serializable{

	final private String ID;
	private String name;
	private String gender;
	private String mobileNum;
	private String address;
	private ArrayList<Order> orders;

	public Customer(String ID, String name, String gender, String mobileNum, String address){

		this.name = name;
		this.gender = gender;
		this.mobileNum = mobileNum;
		this.address = address;
		this.orders = new ArrayList<Order>();

		if (ID != null){
			this.ID = ID;
		}
		else{
			this.ID = generateID();
		}
	}
	public Customer(String name, String gender, String mobileNum, String address){

		this(null, name, gender, mobileNum, address);
	}

	public Customer(){
		this("NA","NA","NA","NA");
	}

	private String generateID(){

		// Generate random number
		int min = 11111;
		int max = 99999;

		int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);

		String ID = "C" + random_int;

		return ID;

	}
	// Getters and setters
	public String getID(){
		return ID;
	}
	public String getName(){
		return name;
	}
	public String getGender(){
		return gender;
	}
	public String getMobile(){
		return mobileNum;
	}
	public String getAddress(){
		return address;
	}
	public void setName(String name){
		this.name = name;
	}
	public void setGender(String gender){
		this.gender = gender;
	}
	public void setMobile(String number){
		this.mobileNum = number;
	}
	public void setAddress(String address){
		this.address = address;
	}
	public ArrayList<Order> getOrders(){
		return orders;
	}
	public void addOrder(Order order){
		orders.add(order);
	}
	public void removeOrder(int index){
		orders.remove(index);
	}
	@Override
	public String toString(){

		String string = String.format("Customer: %s,\t%s,\t%s,\t%s,\t%s", getID(), getName(), getGender(), getMobile(), getAddress());

		return string;
	}
}
