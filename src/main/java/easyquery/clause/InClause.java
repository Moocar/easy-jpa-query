package easyquery.clause;

import com.google.common.collect.ImmutableCollection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

public class InClause<T, K> implements WhereClause<T> {

    private final SingularAttribute<T, K> attribute;
    private final ImmutableCollection<K> args;

    public InClause(SingularAttribute<T, K> attribute, ImmutableCollection<K> args) {
        this.attribute = attribute;
        this.args = args;
    }

    @Override
    public Predicate build(CriteriaBuilder builder, Root<T> from) {
        
        return from.get(attribute).in(args);
    }
    
    @Override
    public String toString() {
        
        return attribute.getName() + " in " + args;
    }
}
