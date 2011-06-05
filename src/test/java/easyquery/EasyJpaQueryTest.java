package easyquery;

import com.google.common.collect.ImmutableList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class EasyJpaQueryTest {
    
    private EntityManager entityManager;
    private EasyQueryProvider easyQueryProvider;
    
    @Before
    public void setup() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("easyjpaquery");
        
        entityManager = entityManagerFactory.createEntityManager();
        
        easyQueryProvider = new EasyQueryProvider(entityManager);
    }
    
    @Test
    public void testGetAll() {
        createBicycle();
        
        ImmutableList<Bicycle> bicycles = easyQueryProvider
                .select(Bicycle.class)
                .getResultList();

        assertEquals(1, bicycles.size());
    }

    @Test
    public void testGetOneAttribute() {
        createBicycle();
        
        String model = easyQueryProvider
                  .select(Bicycle_.model)
                  .getSingleResult();

        assertEquals("Peugot", model);
    }

    private void createBicycle() {
        beginTx();
        Bicycle bicycle = new Bicycle();
        bicycle.setModel("Peugot");
        entityManager.persist(bicycle);
        commitTx();
    }

    private void beginTx() {
        entityManager.getTransaction().begin();
    }

    private void commitTx() {
        entityManager.getTransaction().commit();
    }
}
