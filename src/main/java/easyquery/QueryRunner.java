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
    private final BuildsCriteriaQuery criteriaQueryBuilder;

    public QueryRunner(EntityManager entityManagerProvider, BuildsCriteriaQuery criteriaQueryBuilder) {
        this.entityManagerProvider = entityManagerProvider;
        this.criteriaQueryBuilder = criteriaQueryBuilder;
    }

    public <S> ImmutableList<S> getResultList(EasyQueryBuilder<S> easyQuery) {
        
        try {
            
            return ImmutableList.copyOf(createQuery(easyQuery).getResultList());
            
        } catch (PersistenceException e) {
            
            throw new PersistenceException("Error executing query: " + easyQuery, e);
        }
    }

    public <S> S getSingleResult(EasyQueryBuilder<S> easyQuery) {
        
        try {
            
            return createQuery(easyQuery).getSingleResult();
            
        } catch (NoResultException e) {
            
            throw new NoResultException(e.getMessage() + ": " + easyQuery);
            
        } catch (NonUniqueResultException e) {
            
            throw new NonUniqueResultException(e.getMessage() + ": " + easyQuery);
        }
    }

    public <S> S getFirstResult(EasyQueryBuilder<S> easyQuery) {
        
        List<S> resultList = createQuery(easyQuery).getResultList();
        
        if (resultList.isEmpty()) {
            
            throw new NoResultException("No entity found for query: " + easyQuery);
        }
        
        return resultList.get(0);
    }
    
    public <S> boolean exists(EasyQueryBuilder<S> easyQuery) {
        
        return getResultList(easyQuery).size() > 0;
    }
    
    private <S> TypedQuery<S> createQuery(EasyQueryBuilder<S> easyQuery) {
        
        return entityManagerProvider.createQuery(criteriaQueryBuilder.get(easyQuery));
    }
}
