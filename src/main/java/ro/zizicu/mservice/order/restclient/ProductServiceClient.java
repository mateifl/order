package ro.zizicu.mservice.order.restclient;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "product-service")
public class ProductServiceClient {

}
