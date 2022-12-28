package ro.zizicu.mservice.order.config;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import ro.zizicu.nwbase.rest.TransactionCoordinatorRestClient;
import ro.zizicu.nwbase.service.DistributedTransactionService;
import ro.zizicu.nwbase.service.impl.DistributedTransactionServiceImpl;

@RequiredArgsConstructor
@Configuration
public class DistributedTransactionServiceConfig {

    private final TransactionCoordinatorRestClient restClient;
    @PersistenceUnit
    private final EntityManagerFactory entityManagerFactory;
    
	@Bean
	public DistributedTransactionService distributedTransactionService() {
		return new DistributedTransactionServiceImpl(entityManagerFactory, restClient);  
	}
		
}
