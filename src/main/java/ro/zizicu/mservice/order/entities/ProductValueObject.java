package ro.zizicu.mservice.order.entities;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@ToString
@Data
@NoArgsConstructor
public class ProductValueObject {
	@NotEmpty
	private Integer id;
	@NotEmpty
	private Integer unitsOnOrder;
	private Double unitPrice;
	private Double discount;
	private Boolean enoughStock = Boolean.TRUE;

}