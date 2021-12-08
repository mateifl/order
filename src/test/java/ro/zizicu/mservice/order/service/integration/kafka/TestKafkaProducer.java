package ro.zizicu.mservice.order.service.integration.kafka;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestKafkaProducer {

	@Autowired
	private KafkaTemplate kafkaTemplate;
	
	@Test
	void testProducer() {
		log.info("test producer");
		
//		kafkaTemplate.
		
	}
	
}
