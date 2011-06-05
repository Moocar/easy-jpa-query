package easyquery;

import com.google.common.collect.ImmutableList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class EasyJpaQueryTest {
    
    @Test
    public void test() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("easyjpaquery");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        
        entityManager.getTransaction().begin();
        Bicycle bicycle = new Bicycle();
        bicycle.setModel("Peugot");
        entityManager.persist(bicycle);
        entityManager.getTransaction().commit();
        
        EasyQueryProvider easyQueryProvider = new EasyQueryProvider(entityManager);
        
        
        ImmutableList<Bicycle> bicycles = easyQueryProvider
                .select(Bicycle.class)
                .where(Bicycle_.model).equals("Peugot")
                .getResultList();
        
        assertEquals(1, bicycles.size());
    }
}
