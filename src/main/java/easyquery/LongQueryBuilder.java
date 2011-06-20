package easyquery;

import com.google.common.collect.ImmutableList;
import easyquery.clause.WhereClause;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.SingularAttribute;

public class LongQueryBuilder {

    private final ImmutableList.Builder<WhereClause> whereClauses;
    private final CriteriaQuery<Long> criteriaQuery;
    private final QueryRunner queryRunner;
    private final WhereTransformer whereTransformer;
    
    public LongQueryBuilder(CriteriaQuery<Long> criteriaQuery, QueryRunner queryRunner, WhereTransformer whereTransformer) {
        whereClauses = ImmutableList.builder();
        this.criteriaQuery = criteriaQuery;
        this.queryRunner = queryRunner;
        this.whereTransformer = whereTransformer;
    }
    
    public <E, A> LongWhereBuilder<E, A> where(SingularAttribute<E, A> attribute) {
        
        return new LongWhereBuilder<E, A>(this, attribute);
    }

    LongQueryBuilder addWhereClause(WhereClause whereClause) {
        
        whereClauses.add(whereClause);
        return this;
    }

    private ImmutableList<WhereClause> getWhereClauses() {
        
        return whereClauses.build();
    }
    
    public Long getResult() {

        return queryRunner.getSingleResult(getCriteriaQuery());
    }
    
    public boolean exists() {
        return queryRunner.getSingleResult(getCriteriaQuery()) > 0;
    }
    
    @Override
    public String toString() {
        
        return "Where " + getWhereClauses();
    }

    private CriteriaQuery<Long> getCriteriaQuery() {
        return criteriaQuery.where(whereTransformer.transformWheres(getWhereClauses()));
    }
}
