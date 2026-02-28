package ro.zizicu.mservice.order.data.integration;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.zizicu.mservice.order.restclient.ProductServiceClient;
import ro.zizicu.nwbase.controller.request.UpdateStockRequest;

@SpringBootTest
public class FeignClientTest {

    @Autowired
    private ProductServiceClient productServiceClient;

    @Test
    public void testFeignClient() {

        UpdateStockRequest updateStockRequest = new UpdateStockRequest();
        updateStockRequest.setId(6);
        updateStockRequest.setUnitsOnOrder(2);
        productServiceClient.updateProductStock(updateStockRequest);
    }
}
