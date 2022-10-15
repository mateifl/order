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
	@Column(name="orderid")
	private Integer id;
	@Column(name="orderdate")
	@JsonFormat(pattern="yyyy-MM-dd")
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
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "employeeid", nullable=false)
	@JsonIgnore
	private Employee employee;
	@ManyToOne(cascade = CascadeType.PERSIST, optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "customerid")
	@JsonIgnore
	private Customer customer;
	@Column(name = "shipvia", nullable=false)
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
