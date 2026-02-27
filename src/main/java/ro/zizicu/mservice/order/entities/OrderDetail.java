package ro.zizicu.mservice.order.entities;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "order_details")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
	@EmbeddedId
	private OrderDetailId id;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("orderId")
	@JoinColumn(name = "order_id")
	private Order order;

	@Column(name="unit_price")
	@NotNull
	private Double unitPrice;
	@NotNull
	private Integer quantity;
	private Double discount;


}
