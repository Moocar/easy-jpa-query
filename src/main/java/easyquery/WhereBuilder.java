package easyquery;

import com.google.common.collect.ImmutableCollection;
import easyquery.clause.EqualsClause;
import easyquery.clause.InClause;
import easyquery.clause.LikeClause;
import easyquery.clause.NotEqualsClause;
import javax.persistence.metamodel.SingularAttribute;

public class WhereBuilder<E, A, S> {
    
    private final EasyQueryBuilder<S> queryBuilder;
    private final SingularAttribute<E, A> attribute;
    
    public WhereBuilder(EasyQueryBuilder<S> queryBuilder, SingularAttribute<E, A> attribute) {
        this.queryBuilder = queryBuilder;
        this.attribute = attribute;
    }
    
    public EasyQueryBuilder<S> equals(A object) {
        
        queryBuilder.addWhereClause(new EqualsClause(attribute, object));
        
        return queryBuilder;
    }
    
    public EasyQueryBuilder<S> in(ImmutableCollection<A> collection) {
        
        queryBuilder.addWhereClause(new InClause(attribute, collection));
        
        return queryBuilder;
    }

    public EasyQueryBuilder<S> notEquals(A object) {
        
        queryBuilder.addWhereClause(new NotEqualsClause(attribute, object));
        
        return queryBuilder;
    }

    public EasyQueryBuilder<S> like(String likeString) {
        
        queryBuilder.addWhereClause(new LikeClause(attribute, likeString));
        
        return queryBuilder;
    }
}
