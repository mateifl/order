package ro.zizicu.mservice.order.entities;

import java.util.Date;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import ro.zizicu.nwbase.entity.IdentityOwner;

@Entity
@Table(name = "Employees")
@Getter
@Setter
public class Employee implements IdentityOwner<Integer> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="employee_id")
	private Integer id; 
	@Column(name="birth_date")
	private Date birthDate;
	@Column(name="first_name")
	private String firstName;
	@Column(name="last_name")
	private String lastName;
	@Column(name="hire_date")
	private Date hireDate;
	private String title;
	@Column(name="title_of_courtesy")
	private String titleOfCourtesy;
	private String address;
	private String city;
	private String region;
	@Column(name="postal_code")
	private String postalCode;
	private String country;
	@Column(name="home_phone")
	private String homePhone;
	private String extension;
	private String photo;
	private String notes;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="reports_to")
	@JsonIgnore
	private Employee reportsTo;
	
	@Override
	public String getEntityName() {
		return "Employee";
	}
}
