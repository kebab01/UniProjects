import java.util.*;

class AMDMotherboard extends Motherboard{
	
	final private String SOCKET;

	public AMDMotherboard(String BRAND, String MODEL, double price){
		super("AMD", BRAND, MODEL, price);
		this.SOCKET = "AMD";
	}
	public AMDMotherboard(AMDMotherboard motherboard){
		super(motherboard);
		this.SOCKET=motherboard.getSocket();
	}
	public AMDMotherboard(){
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
        if(cp instanceof IntelCPU || cp instanceof IntelGraphicsCard)
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