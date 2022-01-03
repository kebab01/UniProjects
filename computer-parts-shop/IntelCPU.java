import java.util.*;

class IntelCPU extends CPU{
	
	final private String SOCKET;

	public IntelCPU(String CORE, String MODEL, double price){
		super("INT", CORE, "Intel", MODEL, price);
		this.SOCKET = "Intel";
	}
	public IntelCPU(IntelCPU intelCPU){
		super(intelCPU);
		this.SOCKET=intelCPU.getSocket();
	}
	public IntelCPU(){
		this("NA","NA",0);
	}
	public String getSocket(){
		return SOCKET;
	}

	@Override
	public String toString(){

		String string = String.format("%s,\t%s,\t%s,\t%s,\t%s,\t$%.2f", getID(), getBrand(), getCore(), getSocket(), getModel(), getPrice());

		return string;

	}
	public boolean isCompatible(ComputerPart cp) {
        if(cp instanceof AMDMotherboard || cp instanceof AMDGraphicsCard)
            return false;
        else
            return true;
    }
    public ArrayList<String> infoList(){
		ArrayList<String> infoList = new ArrayList<String>();

		for (String item:super.infoList()){
			infoList.add(item);
		}
		infoList.add("Core: " + getCore());
		infoList.add("Socket: " + getSocket());

		return infoList;
	}
}