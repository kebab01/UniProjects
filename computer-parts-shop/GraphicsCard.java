abstract class GraphicsCard extends ComputerPart{

	final private String PREFIX;

	public GraphicsCard(String PREFIX, String BRAND, String MODEL, double price){

		super(PREFIX + "GRA", BRAND, MODEL, price);
		this.PREFIX = PREFIX;
	}
	public GraphicsCard(String BRAND, String MODEL, double price){
		this("NA", BRAND, MODEL, price);
	}
	public GraphicsCard(GraphicsCard graphicsCard){
		super(graphicsCard);
		this.PREFIX=getPrefix();
	}
	public GraphicsCard(){
		this("NA","NA","NA",0);
	}

	@Override
	public String toString(){
		return super.toString();
	}
}
