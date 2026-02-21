package ro.zizicu.mservice.order.entities;




import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@ToString
@Data
@NoArgsConstructor
public class ProductValueObject {
	@Min(1)
	private Integer quantity;
	private Double discount;
	@NotEmpty
	private Double unitPrice;
	@NotEmpty
	private Integer id;

	private Integer unitsInStock;
	@NotEmpty
	private Integer unitsOnOrder;
	private Boolean enoughStock = Boolean.TRUE;

}