package ro.zizicu.mservice.order.restclient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import ro.zizicu.mservice.order.exceptions.NotEnoughQuantity;

@Component
@RequiredArgsConstructor
@Slf4j
public class RestClient {

	private final Environment environment;
	private final RestTemplate restTemplate;

	public ProductRestObject loadAndUpdateProduct(Integer productId, Integer quantity) {
		log.debug("Checking product with id: {}", productId);

		String productUrl = environment.getProperty("product.url");
		ProductRestObject product = restTemplate.getForObject(productUrl + "/" + productId, ProductRestObject.class);
		log.debug("Units in stock for product {}: {}", product.getId(), product.getUnitsInStock());
		if(quantity > product.getUnitsInStock()) {
			log.error("not enough products in stock, product id {}", productId);
			throw new NotEnoughQuantity();
		}
		log.debug("Enough stock, updating");
		ProductRestObject updatedProduct = new ProductRestObject(productId, product.getUnitsInStock() - quantity, product.getUnitsOnOrder() + quantity);
		HttpEntity<ProductRestObject> updatedEntity = new HttpEntity<ProductRestObject>(updatedProduct);
		log.debug("product update url: {}", productUrl + "/" + productId);
//		restTemplate.exchange(productUrl + "/" + productId, HttpMethod.PATCH, updatedEntity, Void.class);
		restTemplate.patchForObject(productUrl + "/" + productId, updatedEntity, ProductRestObject.class);
		return product;
	}
}
