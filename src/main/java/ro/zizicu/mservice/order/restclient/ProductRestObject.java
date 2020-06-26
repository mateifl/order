package ro.zizicu.mservice.order.restclient;

public class ProductRestObject {
	private Integer id;
	private Integer unitsInStock;
	private Integer unitsOnOrder; 
	public ProductRestObject() {

	}
	
	public ProductRestObject(Integer id, Integer unitsInStock, Integer unitsOnOrder) {
		super();
		this.id = id;
		this.unitsInStock = unitsInStock;
		this.unitsOnOrder = unitsOnOrder;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUnitsInStock() {
		return unitsInStock;
	}
	public void setUnitsInStock(Integer unitsInStock) {
		this.unitsInStock = unitsInStock;
	}
	public Integer getUnitsOnOrder() {
		return unitsOnOrder;
	}
	public void setUnitsOnOrder(Integer unitsOnOrder) {
		this.unitsOnOrder = unitsOnOrder;
	}
	
}
