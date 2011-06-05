package easyquery.clause;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

public class EqualsClause<T, K> implements WhereClause<T>{
    
    private final SingularAttribute<T, K> attribute;
    private final K value;

    public EqualsClause(SingularAttribute<T, K> attribute, K object) {
        this.attribute = attribute;
        this.value = object;
    }

    @Override
    public Predicate build(CriteriaBuilder builder, Root<T> from) {
        
        return builder.equal(from.get(attribute), value);
    }
    
    @Override
    public String toString() {
        
        return attribute.getName() + " == " + value;
    }
}
