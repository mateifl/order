//package ro.zizicu.mservice.order.config;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.kafka.clients.admin.AdminClientConfig;
//import org.apache.kafka.clients.admin.NewTopic;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.kafka.config.TopicBuilder;
//import org.springframework.kafka.core.*;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.kafka.support.serializer.JsonSerializer;
//import ro.zizicu.nwbase.transaction.TransactionMessage;
//
//
//@Configuration
//@Slf4j
//public class OrderKafkaConfiguration {
//	private final Environment environment;
//	
//	public OrderKafkaConfiguration(Environment environment) {
//		this.environment = environment;
//	}
//	
//	@Bean
//	public KafkaAdmin admin() {
//		log.info("Kafka url: {}", environment.getProperty("kafka.url"));
//	    Map<String, Object> configs = new HashMap<>();
//	    configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getProperty("kafka.url"));
//	    return new KafkaAdmin(configs);
//	}
//
//	@Bean
//	public ProducerFactory<String, TransactionMessage> producerFactory() {
//		return new DefaultKafkaProducerFactory<>(producerConfigs());
//	}
//
//	@Bean
//	public Map<String, Object> producerConfigs() {
//		Map<String, Object> props = new HashMap<>();
//		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getProperty("kafka.url"));
//		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//		return props;
//	}
//
//	@Bean
//	public KafkaTemplate<String, TransactionMessage> kafkaTemplate()
//	{
//		return new KafkaTemplate<String, TransactionMessage>(producerFactory());
//	}
//
//
//
//}
