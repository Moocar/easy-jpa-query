package easyquery.clause;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

public class LikeClause<T, K> implements WhereClause<T>{

    private final SingularAttribute<T, String> attribute;
    private final String likeString;

    public LikeClause(SingularAttribute<T, String> attribute, String likeString) {
        this.attribute = attribute;
        this.likeString = likeString;
    }

    @Override
    public Predicate build(CriteriaBuilder builder, Root<T> from) {
    
        return builder.like(from.get(attribute), likeString);
    }
    
    @Override
    public String toString() {
        
        return attribute.getName() + " like " + likeString;
    }
}
