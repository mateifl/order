package ro.zizicu.mservice.order.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "orders")
public class Order implements IdentityOwner<Integer> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="orderid")
	private Integer orderId;
	@Column(name="orderdate")
	private Date orderDate;
	@Column(name="requireddate")
	private Date requiredDate;
	@Column(name="shippeddate")
	private Date shippedDate;
	@Column(name="freight")
	private Double freight;
	@Column(name="shipname")
	private String shipName;
	@Column(name="shipaddress")
	private String shipAddress;
	@Column(name="shipcity")
	private String shipCity;
	@Column(name="shipregion")
	private String shipRegion;
	@Column(name="shippostalcode")
	private String shipPostalCode;
	@Column(name="shipcountry")
	private String shipCountry;
	@ManyToOne
	@JoinColumn(name = "employeeid")
	@JsonIgnore
	private Employee employee;
	@ManyToOne
	@JoinColumn(name = "customerid")
	@JsonIgnore
	private Customer customer;
	@ManyToOne
	@JoinColumn(name = "shipvia")
	@JsonIgnore
	private Shipper shipper;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	@JsonIgnore
	private List<OrderDetail> orderDetails;
	
	public Integer getId() {
		return orderId;
	}
	public void setId(Integer orderId) {
		this.orderId = orderId;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public Date getRequiredDate() {
		return requiredDate;
	}
	public void setRequiredDate(Date requiredDate) {
		this.requiredDate = requiredDate;
	}
	public Date getShippedDate() {
		return shippedDate;
	}
	public void setShippedDate(Date shippedDate) {
		this.shippedDate = shippedDate;
	}
	public Double getFreight() {
		return freight;
	}
	public void setFreight(Double freight) {
		this.freight = freight;
	}
	public String getShipName() {
		return shipName;
	}
	public void setShipName(String shipName) {
		this.shipName = shipName;
	}
	public String getShipAddress() {
		return shipAddress;
	}
	public void setShipAddress(String shipAddress) {
		this.shipAddress = shipAddress;
	}
	public String getShipCity() {
		return shipCity;
	}
	public void setShipCity(String shipCity) {
		this.shipCity = shipCity;
	}
	public String getShipRegion() {
		return shipRegion;
	}
	public void setShipRegion(String shipRegion) {
		this.shipRegion = shipRegion;
	}
	public String getShipPostalCode() {
		return shipPostalCode;
	}
	public void setShipPostalCode(String shipPostalCode) {
		this.shipPostalCode = shipPostalCode;
	}
	public String getShipCountry() {
		return shipCountry;
	}
	public void setShipCountry(String shipCountry) {
		this.shipCountry = shipCountry;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public List<OrderDetail> getOrderDetails() {
		if(orderDetails == null)
			orderDetails = new ArrayList<>();
		return orderDetails;
	}
	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}
	public Shipper getShipper() {
		return shipper;
	}
	public void setShipper(Shipper shipper) {
		this.shipper = shipper;
	}
	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orderDate=" + orderDate + ", requiredDate=" + requiredDate
				+ ", shippedDate=" + shippedDate +  ", freight=" + freight + ", shipName="
				+ shipName + ", shipAddress=" + shipAddress + ", shipCity=" + shipCity + ", shipRegion=" + shipRegion
				+ ", shipPostalCode=" + shipPostalCode + ", shipCountry=" + shipCountry + ", employee=" + employee
				+ ", customer=" + customer + ", orderDetails=" + orderDetails + "]";
	}
	
	public void resetDetails() {
		orderDetails = new ArrayList<>();
	}
	
	
}
