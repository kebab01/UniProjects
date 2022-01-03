import java.util.*;
import java.io.*;

abstract class ComputerPart implements Compatible, Serializable{
	
	final private String PREFIX;
	final private String ID;
	final private String BRAND;
	final private String MODEL;
	private double price;
	
	public ComputerPart(String PREFIX, String BRAND, String MODEL, double price){

		this.PREFIX = PREFIX;
		this.ID = generateID(PREFIX);
		this.BRAND = BRAND;
		this.MODEL = MODEL;
		this.price = price;
	}

	public ComputerPart(ComputerPart computerPart){
		this.PREFIX=computerPart.getPrefix();
		this.ID=computerPart.getID();
		this.BRAND=computerPart.getBrand();
		this.MODEL=computerPart.getModel();
		this.price=computerPart.getPrice();
	}

	public ComputerPart(){
		this("NA", "NA","NA",0);
	}

	private String generateID(String PREFIX){
		String id;
		id = PREFIX + generateRanNum();
		return id;
	}

	private int generateRanNum(){

		// Generate random number
		int min = 11111;
		int max = 99999;

		int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);

		return random_int;
	}

	//getters and setters
	public String getBrand(){
		return BRAND;
	}
	public String getModel(){
		return MODEL;
	}
	public double getPrice(){
		return price;
	}
	public void setPrice(double newPrice){
		price = newPrice;
	}
	public String getID(){
		return ID;
	}
	public String getPrefix(){
		return PREFIX;
	}
	public ArrayList<String> infoList(){
		ArrayList<String> infoList = new ArrayList<String>();
		infoList.add(getID());
		infoList.add("Brand: " + getBrand());
		infoList.add("Model: " + getModel());
		return infoList;
	}

	@Override
	public String toString(){

		String string = String.format("%s,\t%s,\t%s,\t$%.2f", getID(), getBrand(), getModel(), getPrice());

		return string;
	}
}