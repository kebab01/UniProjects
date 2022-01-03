import java.util.*;

class Monitor extends ComputerPart{

	final private String SIZE;

	public Monitor(String SIZE, String BRAND, String MODEL, double price){

		super("MON", BRAND, MODEL, price);
		this.SIZE = SIZE;
	}

	public Monitor(Monitor monitor){
		super(monitor);
		this.SIZE=monitor.getSize();
	}

	public Monitor(){
		this("NA","NA","NA",0);
	}
	public String getSize(){
		return SIZE;
	}
	
	@Override
	public String toString(){

		String string = String.format("%s,\t%s,\t%s,\t%s,\t$%.2f", getID(), getBrand(), getModel(), getSize(), getPrice());

		return string;
	}
	public boolean isCompatible(ComputerPart cp) {
        return true;
    }
    public ArrayList<String> infoList(){
		ArrayList<String> infoList = new ArrayList<String>();

		for (String item:super.infoList()){
			infoList.add(item);
		}
		infoList.add("Size: " + getSize() + "\"");

		return infoList;
	}
}
