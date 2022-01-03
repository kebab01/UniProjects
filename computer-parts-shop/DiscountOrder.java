import java.util.*;

class DiscountOrder extends Order{

	private double discount;

	public DiscountOrder(double discount){
		this.discount = discount;
	}
	public DiscountOrder(){
		this(0);
	}
	public double getDiscount(){
		return discount;
	}

	@Override
	public String toString(){
		String string = super.toString() + "\n";
		string += String.format("Member discount: $%.2f\n", getDiscount() * getTotalPrice());
		string += String.format("Total including discount: $%.2f", (getTotalPrice() * (1 - getDiscount())));
		return string;
	}


}