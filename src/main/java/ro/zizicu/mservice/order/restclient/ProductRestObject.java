package ro.zizicu.mservice.order.restclient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductRestObject {
	private Integer id;
	private Integer unitsInStock;
	private Integer unitsOnOrder; 
}
