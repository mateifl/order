package ro.zizicu.mservice.order.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import ro.zizicu.nwbase.entity.IdentityOwner;

@Entity
@Table(name = "Orders")
@Data
public class Order implements IdentityOwner<Integer> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="order_id")
	private Integer id;
	@Column(name="order_date")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date orderDate;
	@Column(name="required_date")
	private Date requiredDate;
	@Column(name="shipped_date")
	private Date shippedDate;
	@Column(name="freight")
	private Double freight;
	@Column(name="ship_name")
	private String shipName;
	@Column(name="ship_address")
	private String shipAddress;
	@Column(name="ship_city")
	private String shipCity;
	@Column(name="ship_region")
	private String shipRegion;
	@Column(name="ship_postal_code")
	private String shipPostalCode;
	@Column(name="ship_country")
	private String shipCountry;
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id", nullable=false)
	@JsonIgnore
	private Employee employee;
	@ManyToOne(cascade = CascadeType.PERSIST, optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	@JsonIgnore
	private Customer customer;
	@Column(name = "ship_via", nullable=false)
	private Integer shipperId;
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	@JsonIgnore
	private List<OrderDetail> orderDetails = new ArrayList<>();
	
	public List<OrderDetail> getOrderDetails() {
		if(orderDetails == null)
			orderDetails = new ArrayList<>();
		return orderDetails;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + id + ", orderDate=" + orderDate + ", requiredDate=" + requiredDate
				+ ", shippedDate=" + shippedDate +  ", freight=" + freight + ", shipName="
				+ shipName + ", shipAddress=" + shipAddress + ", shipCity=" + shipCity + ", shipRegion=" + shipRegion
				+ ", shipPostalCode=" + shipPostalCode + ", shipCountry=" + shipCountry + ", employee=" + employee
				+ ", customer=" + customer + ", orderDetails=" + orderDetails + "]";
	}
	
	public void resetDetails() {
		orderDetails = new ArrayList<>();
	}
	@Override
	public String getEntityName() {
		return "Order";
	}
}
