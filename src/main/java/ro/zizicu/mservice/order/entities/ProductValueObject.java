package ro.zizicu.mservice.order.entities;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public class ProductValueObject {
	@Min(1)
	public final  Integer quantity;
	public final  Double discount;
	@NotEmpty
	public final  Double unitPrice;
	@NotEmpty
	public final  Integer productId;

}