package ro.zizicu.mservice.order.entities;

public class ProductValueObject {
	public final Integer quantity;
	public final Double discount;
	public final Double unitPrice;
	public final Integer id;
	
	public ProductValueObject(Integer id, 
							  Integer quantity, 
							  Double discount, 
							  Double unitPrice) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.discount = discount;
		this.unitPrice = unitPrice;
	}
	
}
