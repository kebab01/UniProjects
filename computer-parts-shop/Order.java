
import java.util.ArrayList;

class Order{
	
	final private String ID;
	private double totalPrice;
	private ArrayList<ComputerPart> parts;

	public Order(){
		this.parts = new ArrayList<ComputerPart>();
		this.ID = generateID();
	}

	private String generateID(){

		// Generate random number
		int min = 11111;
		int max = 99999;

		int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);

		String ID = "O" + random_int;

		return ID;
	}
	public String getID(){
		return ID;
	}

	public double getTotalPrice(){

		//calculate and return the totalPrice of the oder
		totalPrice = 0;

		for (ComputerPart part : parts){
			totalPrice += part.getPrice();
		}

		return totalPrice;
	}
	public void addComputerPart(ComputerPart part){

		//Add cloned version of computer part to order
		
		if (part instanceof AMDMotherboard){

			parts.add(new AMDMotherboard((AMDMotherboard) part));
		}
		else if (part instanceof IntelMotherboard){

			parts.add(new IntelMotherboard((IntelMotherboard) part));
		}
		else if (part instanceof AMDCPU){

			parts.add(new AMDCPU((AMDCPU) part));
		}
		else if (part instanceof IntelCPU){

			parts.add(new IntelCPU((IntelCPU) part));
		}
		else if (part instanceof AMDGraphicsCard){

			parts.add(new AMDGraphicsCard((AMDGraphicsCard) part));
		}
		else if (part instanceof IntelGraphicsCard){

			parts.add(new IntelGraphicsCard((IntelGraphicsCard) part));
		}
		else if (part instanceof Memory){

			parts.add(new Memory((Memory)part));
		}
		else if (part instanceof Monitor){

			parts.add(new Monitor((Monitor) part));
		}
	}
	public void removeComputerPart(int index){
		parts.remove(index);
	}
	public ArrayList<ComputerPart> getComputerParts(){
		return parts;
	}
	public ComputerPart getComputerPart(int index){
		return parts.get(index);
	}
	private ArrayList<ComputerPart>checkCompatibility(ComputerPart part){

		ArrayList<ComputerPart> inCompatibleParts = new ArrayList<ComputerPart>();

		for (ComputerPart partInOrder: parts){

			if (part.isCompatible(partInOrder) == false){
				inCompatibleParts.add(partInOrder);
			}
		}

		return inCompatibleParts;
	}
	public ArrayList<String> getDetails(){

		ArrayList<String> details = new ArrayList<String>();
		details.add(getID());

		for (ComputerPart part:getComputerParts()){
			details.add(part.getID());
		}

		details.add(String.format("Total: $%.2f",getTotalPrice()));

		if (this instanceof DiscountOrder){
			DiscountOrder discOrder = (DiscountOrder) this;
			details.add(String.format("Total incl. discounts: $%.2f",getTotalPrice() * (1 - discOrder.getDiscount())));
		}

		return details;
	}
	@Override
	public String toString(){

		String string = "";

		for (int i = 0; i<parts.size(); i ++){
			string += String.format("(%d)\t%s\t", i, parts.get(i).toString());

			//Get list of incompatible parts with current part
			ArrayList<ComputerPart> inCompatibleParts = checkCompatibility(parts.get(i));

			//If part has incompatibilities
			if (inCompatibleParts.size() > 0){

				for (int a =0; a<inCompatibleParts.size(); a++){

					if (a==0){
						string += "incompatible with: ";
						string += inCompatibleParts.get(a).getID();
					}
					else{
						string += inCompatibleParts.get(a).getID();
					}
					//If part is not the last one in the list add a comma
					if (a+1 != inCompatibleParts.size()){
						string += ", ";
					}
				}
			}
			string += "\n";
		}
		//Adds other order info
		string += "Order ID: " + getID() + "\n";
		string += "Total: $" + getTotalPrice();
		return string;
	}
}
