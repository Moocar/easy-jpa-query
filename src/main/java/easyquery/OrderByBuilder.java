package easyquery;

import easyquery.clause.OrderBy;
import javax.persistence.metamodel.SingularAttribute;

public class OrderByBuilder<S, E> {
    
    private final EasyQuery<S> queryBuilder;
    private final SingularAttribute<S, E> attribute;

    public OrderByBuilder(EasyQuery<S> queryBuilder, SingularAttribute<S, E> attribute) {
        this.queryBuilder = queryBuilder;
        this.attribute = attribute;
    }

    public EasyQuery<S> asc() {
        
        queryBuilder.addOrderBy(new OrderBy(attribute, OrderBy.OrderDirection.ASCENDING));
        
        return queryBuilder;
    }

    public EasyQuery<S> desc() {
        
        queryBuilder.addOrderBy(new OrderBy(attribute, OrderBy.OrderDirection.DESCINDING));
        
        return queryBuilder;
    }
    
}
