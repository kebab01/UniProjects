import java.util.*;

abstract class CPU extends ComputerPart{

	final private String CORE;
	final private String PREFIX;

	public CPU(String PREFIX, String CORE, String BRAND, String MODEL, double price){

		super(PREFIX + "CPU", BRAND, MODEL, price);
		this.PREFIX = PREFIX;
		this.CORE = CORE;
	}
	public CPU(String CORE, String BRAND, String MODEL, double price){
		this("NA", CORE, BRAND, MODEL, price);
	}
	public CPU(String CORE, String MODEL, double price){
		this("NA", CORE, "NA", MODEL, price);
	}

	public CPU(CPU cpu){
		super(cpu);
		this.CORE=cpu.getCore();
		this.PREFIX=cpu.getPrefix();
	}

	public CPU(){
		this("NA","NA","NA","NA", 0);
	}

	//getters
	public String getCore(){
		return CORE;
	}
	public String getPrefix(){
		return PREFIX;
	}
	@Override
	public String toString(){

		String string = String.format("%s,\t%s,\t%s,\t%s,\t$%.2f", getID(), getBrand(), getCore(), getModel(), getPrice());

		return string;

	}
}
