package ro.zizicu.mservice.order.entities;




import jakarta.validation.constraints.Min;
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
	private Boolean enoughStock = Boolean.TRUE;
}