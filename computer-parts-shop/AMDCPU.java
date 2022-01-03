import java.util.*;

class AMDCPU extends CPU{

	final private String SOCKET;

	public AMDCPU(String CORE, String MODEL, double price){
		super("AMD", CORE, "AMD", MODEL, price);
		this.SOCKET = "AMD";
	}
	public AMDCPU(AMDCPU amdCPU){
		super(amdCPU);
		this.SOCKET=amdCPU.getSocket();
	}
	public AMDCPU(){
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
        if(cp instanceof IntelMotherboard || cp instanceof IntelGraphicsCard)
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