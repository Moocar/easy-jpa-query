package easyquery.clause;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

public class OrderBy<T, K> {
    
    private final SingularAttribute<T, K> attribute;
    private final OrderDirection order;
    
    public enum OrderDirection {
        ASCENDING, DESCINDING;
    }

    public OrderBy(SingularAttribute<T, K> attribute, OrderDirection order) {
        this.attribute = attribute;
        this.order = order;
    }
    
    public Order build(CriteriaBuilder builder, Root<T> from) {
        
        return order == OrderDirection.ASCENDING ? builder.asc(from.get(attribute)) : builder.desc(from.get(attribute));
    }
    
}
