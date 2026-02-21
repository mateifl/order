package ro.zizicu.mservice.order.data.integration;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import ro.zizicu.mservice.order.BaseIntegrationTest;
import ro.zizicu.mservice.order.data.CustomerRepository;
import ro.zizicu.mservice.order.entities.Customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ManualTransactionTest extends BaseIntegrationTest {

    @Autowired
    private PlatformTransactionManager transactionManager;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setup() {

    }

    @Test
    public void testCommit() {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
        definition.setTimeout(3);
        TransactionStatus status  = transactionManager.getTransaction(definition);
        Customer customer = new Customer();
        customer.setId("Test");
        customer.setCompanyName("Test 123");
        customer.setCity("Buhus");
        customer.setCountry("Romania");
        customer.setContactName("Test");
        customer.setContactTitle("Test contact title");
        customer.setPostalCode("12345");
        customer.setPhone("04424123");
        entityManager.persist(customer);
        transactionManager.commit(status);
        assertEquals( entityManager.createQuery(" from Customer c where c.companyName = 'Test 123' ").getResultList().size(), 1);
        status = transactionManager.getTransaction(definition);
        entityManager.createQuery("delete from Customer c where c.id = 'Test'").executeUpdate();
        transactionManager.commit(status);
        assertEquals( entityManager.createQuery(" from Customer c where c.companyName = 'Test 123' ").getResultList().size(), 0);
    }

    @Test
    public void testRollback() {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
        definition.setTimeout(3);
        TransactionStatus status  = transactionManager.getTransaction(definition);

        Customer customer = new Customer();
        customer.setId("Test");
        customer.setCompanyName("Test 123");
        customer.setCity("Buhus");
        customer.setCountry("Romania");
        customer.setContactName("Test");
        customer.setContactTitle("Test contact title");
        customer.setPostalCode("12345");
        customer.setPhone("04424123");
        entityManager.persist(customer);
        transactionManager.commit(status);
        assertEquals( entityManager.createQuery(" from Customer c where c.companyName = 'Test 123' ").getResultList().size(), 1);
        status = transactionManager.getTransaction(definition);
        entityManager.createQuery("delete from Customer c where c.id = 'Test'").executeUpdate();
        transactionManager.rollback(status);
        assertEquals(1, entityManager.createQuery(" from Customer c where c.companyName = 'Test 123' ").getResultList().size());
        status = transactionManager.getTransaction(definition);
        entityManager.createQuery("delete from Customer c where c.id = 'Test'").executeUpdate();
        transactionManager.commit(status);
        assertEquals(0, entityManager.createQuery(" from Customer c where c.companyName = 'Test 123' ").getResultList().size());

    }

    @Test
    public void testWithRepository() {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
        definition.setTimeout(3);
        TransactionStatus status  = transactionManager.getTransaction(definition);
        String customerName = "test124";
        Customer customer = new Customer();
        customer.setId("Test");
        customer.setCompanyName(customerName);
        customer.setCity("Buhus");
        customer.setCountry("Romania");
        customer.setContactName("Test");
        customer.setContactTitle("Test contact title");
        customer.setPostalCode("12345");
        customer.setPhone("04424123");
        customerRepository.save(customer);
        transactionManager.commit(status);
        assertEquals(customerName, customerRepository.findByCompanyName(customerName).getCompanyName());

        status = transactionManager.getTransaction(definition);
        customerRepository.delete(customer);
        transactionManager.rollback(status);
        assertEquals(customerName, customerRepository.findByCompanyName(customerName).getCompanyName());

        status = transactionManager.getTransaction(definition);
        customerRepository.delete(customer);
        transactionManager.commit(status);
        assertNull(customerRepository.findByCompanyName(customerName));

    }
}
