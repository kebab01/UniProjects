import java.io.*;

class MemberCustomer extends Customer implements Serializable{

	final private double DISCOUNT = 0.05;

	public MemberCustomer(String ID, String name, String gender, String mobileNum, String address){
		super(ID, name, gender, mobileNum, address);
	}
	public MemberCustomer(){
		this("NA","NA","NA","NA","NA");
	}
	public double getDISCOUNT(){
		return DISCOUNT;
	}
	public String toString(){
		return super.toString();
	}
}