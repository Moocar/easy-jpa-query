package easyquery.clause;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

public class GreaterThanClause<E> implements WhereClause<E>{
    
    private final SingularAttribute<E, Number> attribute;
    private final Number value;

    public GreaterThanClause(SingularAttribute<E, Number> attribute, Number value) {
        this.attribute = attribute;
        this.value = value;
    }

    @Override
    public Predicate build(CriteriaBuilder builder, Root<E> from) {
        
        return builder.gt(from.get(attribute), value);
    }
    
    @Override
    public String toString() {
        
        return attribute.getName() + " == " + value;
    }
}
