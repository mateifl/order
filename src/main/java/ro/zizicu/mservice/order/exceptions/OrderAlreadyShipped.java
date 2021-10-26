package ro.zizicu.mservice.order.exceptions;

public class OrderAlreadyShipped extends Exception {

	private final Integer orderId;
	
	public OrderAlreadyShipped(Integer id) {
		orderId = id;
	}

	private static final long serialVersionUID = 9004099707138257008L;

}
