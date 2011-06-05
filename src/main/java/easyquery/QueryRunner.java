package easyquery;

import com.google.common.collect.ImmutableList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

public class QueryRunner {

    private final EntityManager entityManagerProvider;
    private final CriteriaQueryBuilder entityManager;

    public QueryRunner(EntityManager entityManagerProvider, CriteriaQueryBuilder criteriaQueryBuilder) {
        this.entityManagerProvider = entityManagerProvider;
        this.entityManager = criteriaQueryBuilder;
    }

    public <S> ImmutableList<S> getResultList(EasyQuery<S> easyQuery) {
        
        try {
            
            return ImmutableList.copyOf(createQuery(easyQuery).getResultList());
            
        } catch (PersistenceException e) {
            
            throw new PersistenceException("Error executing query: " + easyQuery, e);
        }
    }

    public <S> S getSingleResult(EasyQuery<S> easyQuery) {
        
        try {
            
            return createQuery(easyQuery).getSingleResult();
            
        } catch (NoResultException e) {
            
            throw new NoResultException(e.getMessage() + ": " + easyQuery);
            
        } catch (NonUniqueResultException e) {
            
            throw new NonUniqueResultException(e.getMessage() + ": " + easyQuery);
        }
    }

    public <S> S getFirstResult(EasyQuery<S> easyQuery) {
        
        List<S> resultList = createQuery(easyQuery).getResultList();
        
        if (resultList.isEmpty()) {
            
            throw new NoResultException("No entity found for query: " + easyQuery);
        }
        
        return resultList.get(0);
    }
    
    public <S> boolean exists(EasyQuery<S> easyQuery) {
        
        return !getResultList(easyQuery).isEmpty();
    }
    
    private <S> TypedQuery<S> createQuery(EasyQuery<S> easyQuery) {
        
        return entityManagerProvider.createQuery(entityManager.get(easyQuery));
    }
}
