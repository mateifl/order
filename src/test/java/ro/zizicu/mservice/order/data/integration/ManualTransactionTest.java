package ro.zizicu.mservice.order.data.integration;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import ro.zizicu.mservice.order.data.CustomerRepository;
import ro.zizicu.mservice.order.data.EmployeeRepository;
import ro.zizicu.mservice.order.entities.Customer;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class ManualTransactionTest {

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
        entityManager.persist(customer);
        transactionManager.commit(status);
        assertEquals( entityManager.createQuery(" from Customer c where c.companyName = 'Test 123' ").getResultList().size(), 1);
        status = transactionManager.getTransaction(definition);
        entityManager.createQuery("delete from Customer c where c.id = 'Test'").executeUpdate();
        transactionManager.rollback(status);
        assertEquals( entityManager.createQuery(" from Customer c where c.companyName = 'Test 123' ").getResultList().size(), 1);
        status = transactionManager.getTransaction(definition);
        entityManager.createQuery("delete from Customer c where c.id = 'Test'").executeUpdate();
        transactionManager.commit(status);
        assertEquals( entityManager.createQuery(" from Customer c where c.companyName = 'Test 123' ").getResultList().size(), 0);

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

        customerRepository.save(customer);
        transactionManager.commit(status);
        assertEquals( customerRepository.findByCompanyName(customerName).getCompanyName(), customerName);

        status = transactionManager.getTransaction(definition);
        customerRepository.delete(customer);
        transactionManager.rollback(status);
        assertEquals( customerRepository.findByCompanyName(customerName).getCompanyName(), customerName);

        status = transactionManager.getTransaction(definition);
        customerRepository.delete(customer);
        transactionManager.commit(status);
        assertNull(customerRepository.findByCompanyName(customerName));

    }
}
