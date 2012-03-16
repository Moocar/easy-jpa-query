package easyquery;

import com.google.common.collect.ImmutableList;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class QueryRunner {

    private final EntityManager entityManagerProvider;

    public QueryRunner(EntityManager entityManagerProvider) {
        this.entityManagerProvider = entityManagerProvider;
    }

    public <S> ImmutableList<S> getResultList(CriteriaQuery<S> easyQuery) {
        
        try {
            
            return ImmutableList.copyOf(createQuery(easyQuery).getResultList());
            
        } catch (PersistenceException e) {
            
            throw new PersistenceException("Error executing query: " + easyQuery, e);
        }
    }

    public <S> S getSingleResult(CriteriaQuery<S> easyQuery) {
        
        try {
            
            return createQuery(easyQuery).getSingleResult();
            
        } catch (NonUniqueResultException e) {
            
            throw new NonUniqueResultException(e.getMessage() + ": " + easyQuery);
        }
    }

    public <S> S getFirstResult(CriteriaQuery<S> easyQuery) {
        
        List<S> resultList = createQuery(easyQuery).getResultList();
        
        if (resultList.isEmpty())
            throw new NoResultException("No entity found for query: " + easyQuery);

        return resultList.get(0);
    }
    
    private <S> TypedQuery<S> createQuery(CriteriaQuery<S> easyQuery) {
        
        return entityManagerProvider.createQuery(easyQuery);
    }
}
