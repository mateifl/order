package ro.zizicu.mservice.order.entities;


import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public class ProductValueObject {
	public final  Integer quantity;
	public final  Double discount;
	public final  Double unitPrice;
	public final  Integer productId;

}