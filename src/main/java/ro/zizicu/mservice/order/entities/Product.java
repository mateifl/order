package ro.zizicu.mservice.order.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "products")
public class Product {
	@Id 
	@Column(name="productid")
	private Integer productId;
	@Column(name = "productname")
	private String productName;
	@Column(name = "quantityperunit")
	private String quantityPerUnit;
	@Column(name = "unitprice")
	private Double unitPrice;
	@Column(name = "unitsinstock")
	private Integer unitsInStock;
	@Column(name = "unitsonorder")
	private Integer unitsOnOrder;
	@Column(name = "reorderlevel")
	private Integer reorderLevel;
	private String discontinued;
		
	public Integer getProductId() {
		return productId;
	}

	public String getProductName() {
		return productName;
	}

	public String getQuantityPerUnit() {
		return quantityPerUnit;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public Integer getUnitsInStock() {
		return unitsInStock;
	}

	public Integer getUnitsOnOrder() {
		return unitsOnOrder;
	}

	public Integer getReorderLevel() {
		return reorderLevel;
	}

	public String getDiscontinued() {
		return discontinued;
	}

}
