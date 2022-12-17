package ro.zizicu.mservice.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import lombok.AllArgsConstructor;
import ro.zizicu.nwbase.rest.DefaultTransactionCoordinatorRestClient;
import ro.zizicu.nwbase.rest.TransactionCoordinatorRestClient;

@Configuration
@AllArgsConstructor
public class TransactionCoordinatorClientConfig {

	private final Environment environment;
	private final RestTemplate restTemplate;
	
	@Bean
	public TransactionCoordinatorRestClient transactionCoordinatorRestClient() {
		return new DefaultTransactionCoordinatorRestClient(environment, restTemplate);
	}
}
