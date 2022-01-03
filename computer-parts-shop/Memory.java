import java.util.*;

class Memory extends ComputerPart{

	final private String SOCKET;
	final private String SIZE;

	public Memory(String SOCKET, String SIZE, String BRAND, String MODEL, double price){

		super("MEM", BRAND, MODEL, price);
		this.SOCKET = SOCKET;
		this.SIZE = SIZE;
	}	
	public Memory(Memory memory){
		super(memory);
		this.SOCKET=memory.getSocket();
		this.SIZE=memory.getSize();
	}
	public Memory(){
		this("NA","NA","NA","NA",0);
	}

	public String getSocket(){
		return SOCKET;
	}
	public String getSize(){
		return SIZE;
	}

	@Override
	public String toString(){

		String string = String.format("%s,\t%s,\t%s,\t%s,\t%s,\t$%.2f", getID(), getBrand(), getModel(), getSocket(), getSize(), getPrice());
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
		infoList.add("Size: " + getSize());
		infoList.add("Socket: " + getSocket());

		return infoList;
	}
}
