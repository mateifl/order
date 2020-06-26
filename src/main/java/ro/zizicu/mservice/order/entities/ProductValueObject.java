package ro.zizicu.mservice.order.entities;

public class ProductValueObject {
	public final  Integer quantity;
	public final  Double discount;
	public final  Double unitPrice;
	public final  Integer productId;
	
	public ProductValueObject(Integer quantity, Double discount, Double unitPrice, Integer productId) {
		super();
		this.quantity = quantity;
		this.discount = discount;
		this.unitPrice = unitPrice;
		this.productId = productId;
	}

	@Override
	public String toString() {
		return "ProductValueObject [quantity=" + quantity + ", discount=" + discount + ", unitPrice=" + unitPrice
				+ ", productId=" + productId + "]";
	}
	
	

}