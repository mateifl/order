package ro.zizicu.mservice.order.restclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import ro.zizicu.mservice.order.exceptions.NotEnoughQuantity;

@Component
public class RestClient {
	private static Logger logger = LoggerFactory.getLogger(RestClient.class);
	
	@Autowired
	private Environment environment; 
	
	public ProductRestObject loadAndUpdateProduct(Integer productId, Integer quantity) {
		logger.debug("Checking product with id {}", productId);
		RestTemplate restTemplate = new RestTemplate();
		String productUrl = environment.getProperty("product.url");
		ProductRestObject product = restTemplate.getForObject(productUrl + "/" + productId, ProductRestObject.class);
		if(quantity > product.getUnitsInStock()) {
			logger.error("not enough products in stock, product id {}", productId);
			throw new NotEnoughQuantity();
		}
		logger.debug("Enough stock, updating");
		ProductRestObject updatedProduct = new ProductRestObject(productId, product.getUnitsInStock() - quantity, product.getUnitsOnOrder() + quantity);
		HttpEntity<ProductRestObject> updatedEntity = new HttpEntity<ProductRestObject>(updatedProduct);
		restTemplate.exchange(productUrl + "/" + productId, HttpMethod.PATCH, updatedEntity, Void.class);
		return product;
	}
}
