package easyquery;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
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
    
    public Order transform(OrderBy orderBy) {
        
        return orderBy.build(criteriaBuilder, root);
    }

    public Order[] transform(ImmutableList<OrderBy> orderBys) {
        
        return Collections2.transform(orderBys, new Function<OrderBy, Order>() {

            @Override
            public Order apply(OrderBy f) {
                
                return transform(f);
            }
            
        }).toArray(new Order[orderBys.size()]);
    }
}
