package ro.zizicu.mservice.order.entities;

public class ProductValueObject {
	public final Integer id;
	public final Integer quantity;
	public final Double discount;
	
	public ProductValueObject(Integer id, Integer quantity, Double discount) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.discount = discount;
	}
	
}
