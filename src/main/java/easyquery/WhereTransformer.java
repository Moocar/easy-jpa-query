package easyquery;

import easyquery.clause.WhereClause;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class WhereTransformer<E> {
    
    private final CriteriaBuilder criteriaBuilder;
    private final Root<E> root;

    public WhereTransformer(CriteriaBuilder criteriaBuilder, Root<E> root) {
        this.criteriaBuilder = criteriaBuilder;
        this.root = root;
    }
    
    public <E> Predicate transform(WhereClause whereClause) {
        
        return whereClause.build(criteriaBuilder, root);
    }
}
