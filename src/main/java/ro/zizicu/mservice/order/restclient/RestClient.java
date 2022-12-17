package ro.zizicu.mservice.order.restclient;

import java.util.Optional;

import ro.zizicu.mservice.order.entities.ProductValueObject;

public interface RestClient {

    void updateProductQuantity(ProductValueObject product, Long transactionId);
    Optional<ProductValueObject> checkStock(Integer productId, Integer quantity);
}
