package ro.zizicu.mservice.order.exceptions;

import java.io.Serial;

public class NotEnoughQuantity extends RuntimeException {

	@Serial
    private static final long serialVersionUID = 7107411095629841964L;

    public NotEnoughQuantity(String s) {
        super(s);
    }
}
