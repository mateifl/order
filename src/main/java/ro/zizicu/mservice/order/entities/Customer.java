package ro.zizicu.mservice.order.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ro.zizicu.nwbase.entity.IdentityOwner;

@Entity
@Table(name = "Customers")
@Getter
@Setter
public class Customer implements IdentityOwner<String>{
	
	@Id
	@Column(name="customer_id")
	private String id;
	@Column(name="company_name", nullable=false)
	private String companyName;
	@Column(name="contact_name", nullable=false)
	private String contactName;
	@Column(name="contact_title", nullable=false)
	private String contactTitle;
	private String address;
	private String city;
	private String region;
	@Column(name="postal_code", nullable=false)
	private String postalCode;
	private String country;
	private String phone;
	private String fax;

	@Override
	public String toString() {
		return "Customer [customerId=" + id + ", companyName=" + companyName + ", contactName=" + contactName
				+ ", contactTitle=" + contactTitle + ", address=" + address + ", city=" + city + ", region=" + region
				+ ", postalCode=" + postalCode + ", country=" + country + ", phone=" + phone + ", fax=" + fax + "]";
	}

	@Override
	public String getEntityName() {
		return "Customer";
	}
}
