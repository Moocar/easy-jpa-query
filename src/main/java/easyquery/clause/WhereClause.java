package easyquery.clause;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface WhereClause<T> {
    
    Predicate build(CriteriaBuilder builder, Root<T> from);
}
