abstract class Motherboard extends ComputerPart{

	final private String PREFIX;

	public Motherboard(String PREFIX, String BRAND, String MODEL, double price){

		super(PREFIX + "MOT", BRAND, MODEL, price);
		this.PREFIX = PREFIX;
	}
	public Motherboard(String BRAND, String MODEL, double price){
		this("NA", BRAND, MODEL, price);
	}
	public Motherboard(Motherboard motherboard){
		super(motherboard);
		this.PREFIX=motherboard.getPrefix();
	}
	public Motherboard(){
		this("NA","NA","NA",0);
	}
	@Override
	public String toString(){
		return super.toString();
	}


}
