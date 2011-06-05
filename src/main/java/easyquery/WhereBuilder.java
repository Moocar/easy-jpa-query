package easyquery;

import com.google.common.collect.ImmutableCollection;
import easyquery.clause.EqualsClause;
import easyquery.clause.InClause;
import easyquery.clause.LikeClause;
import easyquery.clause.NotEqualsClause;
import javax.persistence.metamodel.SingularAttribute;

public class WhereBuilder<S, E> {
    
    private final EasyQueryBuilder<S> queryBuilder;
    private final SingularAttribute<S, E> attribute;
    
    public WhereBuilder(EasyQueryBuilder<S> queryBuilder, SingularAttribute<S, E> attribute) {
        this.queryBuilder = queryBuilder;
        this.attribute = attribute;
    }
    
    public EasyQueryBuilder<S> equals(E object) {
        
        queryBuilder.addWhereClause(new EqualsClause(attribute, object));
        
        return queryBuilder;
    }
    
    public EasyQueryBuilder<S> in(ImmutableCollection<E> collection) {
        
        queryBuilder.addWhereClause(new InClause(attribute, collection));
        
        return queryBuilder;
    }

    public EasyQueryBuilder<S> notEquals(E object) {
        
        queryBuilder.addWhereClause(new NotEqualsClause(attribute, object));
        
        return queryBuilder;
    }

    public EasyQueryBuilder<S> like(String likeString) {
        
        queryBuilder.addWhereClause(new LikeClause(attribute, likeString));
        
        return queryBuilder;
    }
}
