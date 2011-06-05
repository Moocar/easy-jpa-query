package easyquery.clause;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

public class NotEqualsClause<T, K> implements WhereClause<T>{
    
    private final SingularAttribute<T, K> attribute;
    private final K value;

    public NotEqualsClause(SingularAttribute<T, K> attribute, K value) {
        this.attribute = attribute;
        this.value = value;
    }

    @Override
    public Predicate build(CriteriaBuilder builder, Root<T> from) {
        
        return builder.notEqual(from.get(attribute), value);
    }
    
    @Override
    public String toString() {
        
        return attribute.getName() + " != " + value;
    }
    
    
}
