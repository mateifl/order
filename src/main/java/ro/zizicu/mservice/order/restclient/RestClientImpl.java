package ro.zizicu.mservice.order.restclient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import ro.zizicu.mservice.order.entities.ProductValueObject;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class RestClientImpl implements RestClient {
	private final Environment environment;
	private final RestTemplate restTemplate;

	public void updateProductQuantity(ProductValueObject product) {
		String productUrl = environment.getProperty("product.url");
		HttpEntity<ProductValueObject> updatedEntity = new HttpEntity<ProductValueObject>(product);
		log.debug("product stock update url: {}", productUrl + "/" + product.getId());
		restTemplate.patchForObject(productUrl + "/" + product.getId(),
										updatedEntity, ProductValueObject.class);
	}

	public Optional<ProductValueObject> checkStock(Integer productId, Integer quantity) {
		log.debug("Checking product with id: {}", productId);
		String productUrl = environment.getProperty("product.url");
		ProductValueObject product = restTemplate.getForObject(productUrl + "/" + productId, ProductValueObject.class);
		assert product != null;
		product.setQuantity(quantity);
		log.debug("Units in stock for product {}: {}", product.getId(), product.getUnitsInStock());
		if(quantity > product.getUnitsInStock()) {
			log.warn("Not enough stock for product {}", productId);
			product.setEnoughStock(Boolean.FALSE);
		}
		return Optional.of(product);
	}


}
