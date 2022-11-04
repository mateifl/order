package ro.zizicu.mservice.order.restclient;

import ro.zizicu.mservice.order.entities.ProductValueObject;

import java.util.Optional;

public interface RestClient {

    void updateProductQuantity(ProductValueObject product, Long transactionId);
    Optional<ProductValueObject> checkStock(Integer productId, Integer quantity);

}
