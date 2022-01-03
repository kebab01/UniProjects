import java.util.*;

class IntelMotherboard extends Motherboard{

	final private String SOCKET;

	public IntelMotherboard(String BRAND, String MODEL, double price){
		super("INT", BRAND, MODEL, price);
		this.SOCKET = "Intel";
	}
	public IntelMotherboard(IntelMotherboard motherboard){
		super(motherboard);
		this.SOCKET=motherboard.getSocket();
	}
	public IntelMotherboard(){
		this("NA", "NA", 0);
	}
	public String getSocket(){
		return SOCKET;
	}

	@Override
	public String toString(){
		String string = String.format("%s,\t%s,\t%s,\t%s,\t$%.2f", getID(), getBrand(), getSocket(), getModel(), getPrice());

		return string;
	}
	public boolean isCompatible(ComputerPart cp) {
        if(cp instanceof AMDGraphicsCard || cp instanceof AMDCPU)
            return false;
        else
            return true;
    }
    public ArrayList<String> infoList(){
		ArrayList<String> infoList = new ArrayList<String>();

		for (String item:super.infoList()){
			infoList.add(item);
		}
		infoList.add("Socket: " + getSocket());

		return infoList;
	}
}