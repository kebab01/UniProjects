import java.util.*;

class AMDGraphicsCard extends GraphicsCard{
	
	final private String SOCKET;

	public AMDGraphicsCard(String BRAND, String MODEL, double price){
		super("AMD", BRAND, MODEL, price);
		this.SOCKET = "AMD";
	}
	public AMDGraphicsCard(AMDGraphicsCard graphicsCard){
		super(graphicsCard);
		this.SOCKET=graphicsCard.getSocket();
	}
	public AMDGraphicsCard(){
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
        if(cp instanceof IntelMotherboard || cp instanceof IntelCPU)
            return false;
        else{
            return true;
        }
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