package ro.zizicu.mservice.order.exceptions;

public class OrderNotFoundException extends RuntimeException {

	public OrderNotFoundException() {
		
	}
	
	public OrderNotFoundException(String string) {
		super(string);
	}
	private static final long serialVersionUID = -3965991515990053659L;

}
