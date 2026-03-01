package ro.zizicu.mservice.order;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.junit.jupiter.Container;

import java.nio.file.Path;

public abstract class BaseIntegrationTest {

    static Network network = Network.newNetwork();

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16")
                    .withInitScript("schema.sql")
                    .withNetwork(network)
                    .withNetworkAliases("postgres");


    @Container
    static GenericContainer<?> productService = new GenericContainer<>(
            new ImageFromDockerfile()
                    .withDockerfileFromBuilder(builder -> builder
                            .from("eclipse-temurin:21")
                            .copy("app.jar", "/app.jar")
                            .entryPoint("java", "-jar", "/app.jar")
                            .build())
                    // Path to the product-service JAR, relative to the order-service project root
                    .withFileFromPath("app.jar", Path.of("../product/target/product-0.0.1.jar"))
    )
            .withNetwork(network)
            .withExposedPorts(8081)
            .withEnv("SPRING_DATASOURCE_URL", "jdbc:postgresql://postgres:5432/test") // uses network alias
            .withEnv("SPRING_DATASOURCE_USERNAME", postgres.getUsername())
            .withEnv("SPRING_DATASOURCE_PASSWORD", postgres.getPassword())
            .waitingFor(Wait.forHttp("/actuator/health")
                    .forPort(8081)
                    .forStatusCode(200))
            .dependsOn(postgres);

    static {
        postgres.start();
        productService.start();
        System.out.println(productService.getLogs());
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("product.service.url", () ->
                "http://" + productService.getHost() + ":" + productService.getMappedPort(8081)
        );
    }

}
