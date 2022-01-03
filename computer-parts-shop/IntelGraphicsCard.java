import java.util.*;

class IntelGraphicsCard extends GraphicsCard{
	
	final private String SOCKET;

	public IntelGraphicsCard(String BRAND, String MODEL, double price){
		super("INT", BRAND, MODEL, price);
		this.SOCKET = "INT";
	}
	public IntelGraphicsCard(IntelGraphicsCard graphicsCard){
		super(graphicsCard);
		this.SOCKET=graphicsCard.getSocket();
	}
	public IntelGraphicsCard(){
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
        if(cp instanceof AMDCPU || cp instanceof AMDMotherboard)
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