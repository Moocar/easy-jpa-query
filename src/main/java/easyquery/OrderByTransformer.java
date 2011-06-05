package easyquery;

import easyquery.clause.OrderBy;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

public class OrderByTransformer<E> {
    
    private final CriteriaBuilder criteriaBuilder;
    private final Root<E> root;

    public OrderByTransformer(CriteriaBuilder criteriaBuilder, Root<E> root) {
        this.criteriaBuilder = criteriaBuilder;
        this.root = root;
    }
    
    public <E> Order transform(OrderBy orderBy) {
        
        return orderBy.build(criteriaBuilder, root);
    }
}
