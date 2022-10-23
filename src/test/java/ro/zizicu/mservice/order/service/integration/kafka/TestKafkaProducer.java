package ro.zizicu.mservice.order.service.integration.kafka;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

@Slf4j
@SpringBootTest
public class TestKafkaProducer {

	@Autowired
	private KafkaTemplate kafkaTemplate;
	
	@Test
	void testProducer() {
		log.info("test producer");

		try {
			ListenableFuture<SendResult<String, String>> result = kafkaTemplate.send("stockUpdateTopic", "This is a  message ");
			System.out.println(result.get().getProducerRecord().value());
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}


	}
	
}
