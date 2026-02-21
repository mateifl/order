package ro.zizicu.mservice.order.entities;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.zizicu.nwbase.entity.IdentityOwner;


@Entity
@Table(name = "order_details")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail implements IdentityOwner<Integer> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JsonIgnore
	@JoinColumn(name = "orderid")
	private Order order;
	@Column(name="productid")
	@NotNull
	private Integer productId;
	@Column(name="unitprice")
	@NotNull
	private Double unitPrice;
	@NotNull
	private Integer quantity;
	private Double discount;
	@Override
	public String getEntityName() {
		return "OrderDetail";
	}
}
