package ro.zizicu.mservice.order.restclient;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ro.zizicu.mservice.order.entities.Product;
import ro.zizicu.nwbase.controller.request.UpdateStockRequest;

@FeignClient(name = "product-service", url = "${product.service.url}")
public interface ProductServiceClient {

    @RequestMapping(method = RequestMethod.GET, value = "/products/{id}", produces = "application/json")
    Product getProductById(@PathVariable("id") Integer id);

    @RequestMapping(method = RequestMethod.PATCH, value = "/products/update-stock", produces = "application/json")
    void updateProductStock(@Valid @RequestBody UpdateStockRequest request);

}
