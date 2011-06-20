package easyquery;

import easyquery.clause.OrderBy;
import javax.persistence.metamodel.SingularAttribute;

public class OrderByBuilder<E, A, S> {
    
    private final EasyQueryBuilder<S> queryBuilder;
    private final SingularAttribute<E, A> attribute;

    public OrderByBuilder(EasyQueryBuilder<S> queryBuilder, SingularAttribute<E, A> attribute) {
        this.queryBuilder = queryBuilder;
        this.attribute = attribute;
    }

    public EasyQueryBuilder<S> asc() {
        
        queryBuilder.addOrderBy(new OrderBy(attribute, OrderBy.OrderDirection.ASCENDING));
        
        return queryBuilder;
    }

    public EasyQueryBuilder<S> desc() {
        
        queryBuilder.addOrderBy(new OrderBy(attribute, OrderBy.OrderDirection.DESCINDING));
        
        return queryBuilder;
    }
    
}
