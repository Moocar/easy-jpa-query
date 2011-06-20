package easyquery;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import easyquery.clause.OrderBy;
import easyquery.clause.WhereClause;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;

public class CriteriaQueryBuilder implements BuildsCriteriaQuery {

    private final WhereTransformer whereTransformer;
    private final OrderByTransformer orderByTransformer;

    public CriteriaQueryBuilder(WhereTransformer whereTransformer, OrderByTransformer orderByTransformer) {
        this.whereTransformer = whereTransformer;
        this.orderByTransformer = orderByTransformer;
    }
    
    @Override
    public <S> CriteriaQuery<S> get(EasyQueryBuilder<S> easyQuery) {
        
        return easyQuery.getCriteriaQuery()
                .where(transformWheres(easyQuery.getWhereClauses()))
                .orderBy(transformOrders(easyQuery.getOrderClauses()));
    }

    private Predicate[] transformWheres(ImmutableList<WhereClause> whereClauses) {
        
        return Collections2.transform(whereClauses, new Function<WhereClause, Predicate>() {

            @Override
            public Predicate apply(WhereClause f) {
                
                return whereTransformer.transform(f);
            }
            
        }).toArray(new Predicate[whereClauses.size()]);
    }

    private Order[] transformOrders(ImmutableList<OrderBy> orderBys) {
        
        return Collections2.transform(orderBys, new Function<OrderBy, Order>() {

            @Override
            public Order apply(OrderBy f) {
                
                return orderByTransformer.transform(f);
            }
            
        }).toArray(new Order[orderBys.size()]);
    }
}
