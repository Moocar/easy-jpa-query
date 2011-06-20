package easyquery;

import com.google.common.collect.ImmutableList;
import easyquery.clause.OrderBy;
import easyquery.clause.WhereClause;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.SingularAttribute;

public class EasyQueryBuilder<S> {

    private final ImmutableList.Builder<WhereClause> whereClauses;
    private final ImmutableList.Builder<OrderBy> orderClauses;
    private final CriteriaQuery<S> criteriaQuery;
    private final QueryRunner queryRunner;
    private final WhereTransformer whereTransformer;
    private final OrderByTransformer orderByTransformer;
    
    public EasyQueryBuilder(CriteriaQuery<S> criteriaQuery, QueryRunner queryRunner, WhereTransformer whereTransformer, OrderByTransformer orderByTransformer) {
        whereClauses = ImmutableList.builder();
        orderClauses = ImmutableList.builder();
        this.criteriaQuery = criteriaQuery;
        this.queryRunner = queryRunner;
        this.whereTransformer = whereTransformer;
        this.orderByTransformer = orderByTransformer;
    }
    
    public <E, A> WhereBuilder<E, A, S> where(SingularAttribute<E, A> attribute) {
        
        return new WhereBuilder<E, A, S>(this, attribute);
    }

    public <E, A> OrderByBuilder<E, A, S> orderBy(SingularAttribute<E, A> attribute) {
        
        return new OrderByBuilder<E, A, S>(this, attribute);
    }

    void addWhereClause(WhereClause whereClause) {
        
        whereClauses.add(whereClause);
    }

    void addOrderBy(OrderBy orderBy) {
        
        orderClauses.add(orderBy);
    }

    private ImmutableList<OrderBy> getOrderClauses() {
        
        return orderClauses.build();
    }

    private ImmutableList<WhereClause> getWhereClauses() {
        
        return whereClauses.build();
    }

    public ImmutableList<S> getResultList() {
        
        return queryRunner.getResultList(getCriteriaQuery());
    }

    private CriteriaQuery<S> getCriteriaQuery() {
        return criteriaQuery.where(whereTransformer.transformWheres(getWhereClauses())).orderBy(orderByTransformer.transform(getOrderClauses()));
    }
    
    public S getSingleResult() {

        return queryRunner.getSingleResult(getCriteriaQuery());
    }

    public S getFirstResult() {
        
        return queryRunner.getFirstResult(getCriteriaQuery());
    }
    
    @Override
    public String toString() {
        
        return "Where " + getWhereClauses() + ", OrderBy " + getOrderClauses();
    }
}
