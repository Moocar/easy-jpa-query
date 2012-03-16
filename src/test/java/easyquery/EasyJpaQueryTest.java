package easyquery;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;

import static org.junit.Assert.*;

public class EasyJpaQueryTest {
    
    private EntityManager entityManager;
    private EasyQuery easyQuery;
    
    @Before
    public void setup() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("easyjpaquery");
        
        entityManager = entityManagerFactory.createEntityManager();
        
        easyQuery = new EasyQuery(entityManager);
    }
    
    @Test
    public void testGetAll() {
        createBicycle("Peugot");
        
        ImmutableList<Bicycle> bicycles = easyQuery
                .select(Bicycle.class)
                .getResultList();

        assertEquals(1, bicycles.size());
    }

    @Test
    public void testGetOneAttribute() {
        createBicycle("Peugot");
        
        String model = easyQuery
                  .select(Bicycle_.model)
                  .getSingleResult();

        assertEquals("Peugot", model);
    }

    @Test
    public void testIn() {
        createBicycle("Peugot1");
        createBicycle("Peugot2");
        createBicycle("Peugot3");
        
        ImmutableList<Bicycle> bicycles = easyQuery
                .select(Bicycle.class)
                .where(Bicycle_.model).in(ImmutableSet.of("Peugot1", "Peugot3"))
                .getResultList();

        assertEquals(2, bicycles.size());
    }

    @Test
    public void testLike() {
        createBicycle("Peugot1");
        createBicycle("Barft2");
        createBicycle("Peugot3");
        
        ImmutableList<Bicycle> bicycles = easyQuery
                .select(Bicycle.class)
                .where(Bicycle_.model).like("Peugot%")
                .getResultList();

        assertEquals(2, bicycles.size());
    }

    @Test
    public void testSelectAttribute() {
        createBicycle("i1");
        createBicycle("i2");
        createBicycle("i3");
        
        ImmutableList<String> bicycles = easyQuery
                .select(Bicycle_.model)
                .getResultList();

        assertTrue(ImmutableSet.of("i1", "i2", "i3").containsAll(bicycles));
    }

    @Test
    public void testSingle() {
        createBicycle("desc1");
        createBicycle("desc2");
        createBicycle("desc3");
        
        Bicycle bicycle = easyQuery
                .select(Bicycle.class)
                .where(Bicycle_.model).equals("desc3")
                .getSingleResult();

        assertEquals("desc3", bicycle.getModel());

        try {
            easyQuery.select(Bicycle.class)
                    .getSingleResult();
            fail();

        } catch (NonUniqueResultException e) {
            System.out.println(e.getClass() + ": " + e.getMessage());
            // it's ok
        }
    }

    @Test
    public void testOrder() {
        
        long time = System.currentTimeMillis();
        Bicycle bicycle1 = createBicycle("desc", time + 3);
        Bicycle bicycle2 = createBicycle("desc", time + 1);
        Bicycle bicycle3 = createBicycle("desc2", time + 2);
        
        ImmutableList<Bicycle> bicycles = easyQuery
                .select(Bicycle.class)
                .orderBy(Bicycle_.timestamp).asc()
                .getResultList();
        
        assertTrue(ImmutableList.of(bicycle2, bicycle3, bicycle1).containsAll(bicycles));
        assertTrue(bicycles.equals(ImmutableList.of(bicycle2, bicycle3, bicycle1).asList()));
        assertFalse(bicycles.equals(ImmutableList.of(bicycle1, bicycle2, bicycle3)));
        
        bicycles = easyQuery
                .select(Bicycle.class)
                .where(Bicycle_.model).equals("desc")
                .orderBy(Bicycle_.timestamp).asc()
                .getResultList();

        assertTrue(ImmutableList.of(bicycle2, bicycle1).containsAll(bicycles));
        assertTrue(bicycles.equals(ImmutableList.of(bicycle2, bicycle1).asList()));
        assertFalse(bicycles.equals(ImmutableList.of(bicycle1, bicycle2)));
    }

    @Test
    public void testCount() {
        
        long time = System.currentTimeMillis();
        createBicycle("desc", time + 3);
        createBicycle("desc", time + 1);
        createBicycle("desc2", time + 2);
        
        assertEquals(Long.valueOf(3), easyQuery
                .count(Bicycle.class)
                .getResult());
        
        assertEquals(Long.valueOf(2), easyQuery
                .count(Bicycle.class)
                .where(Bicycle_.model).equals("desc")
                .getResult());
        
        assertFalse(easyQuery
                .count(Bicycle.class)
                .where(Bicycle_.model).equals("dessssc")
                .exists());
        
        assertTrue(easyQuery
                .count(Bicycle.class)
                .exists());
    }

    @Test
    public void testSum() {
        
        createBicycle("desc", 1);
        createBicycle("desc", 2);
        createBicycle("desc2", 3);
        
        assertEquals(Long.valueOf(6), easyQuery
                .sum(Bicycle_.timestamp)
                .getResult());
        
        assertNull(easyQuery
                .sum(Bicycle_.timestamp)
                .where(Bicycle_.model).equals("moo")
                .getResult());
    }

    @Test
    public void testNotFound() {
        try {
        easyQuery.select(Bicycle.class)
                .where(Bicycle_.id).equals(4323455)
                .getSingleResult();
        } catch (NoResultException e) {
            // good
            return;
        }
        fail("Should have thrown exception");
    }

    private Bicycle createBicycle(String model) {
        return createBicycle(model, System.currentTimeMillis());
    }

    private Bicycle createBicycle(String model, long timestamp) {
        beginTx();
        Bicycle bicycle = new Bicycle();
        bicycle.setModel(model);
        bicycle.setTimestamp(timestamp);
        entityManager.persist(bicycle);
        commitTx();
        return bicycle;
    }

    private void beginTx() {
        entityManager.getTransaction().begin();
    }

    private void commitTx() {
        entityManager.getTransaction().commit();
    }
}
