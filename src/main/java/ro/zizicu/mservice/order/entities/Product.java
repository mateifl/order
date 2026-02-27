package ro.zizicu.mservice.order.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {

    private Integer id;
    private String name;
    private String quantityPerUnit;
    private Double unitPrice;
    private Integer unitsInStock;
    private Integer unitsOnOrder;

}
