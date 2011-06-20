package easyquery;

import com.google.common.collect.ImmutableCollection;
import easyquery.clause.EqualsClause;
import easyquery.clause.InClause;
import easyquery.clause.LikeClause;
import easyquery.clause.NotEqualsClause;
import javax.persistence.metamodel.SingularAttribute;

public class LongWhereBuilder<E, A> {
    
    private final LongQueryBuilder queryBuilder;
    private final SingularAttribute<E, A> attribute;
    
    public LongWhereBuilder(LongQueryBuilder queryBuilder, SingularAttribute<E, A> attribute) {
        this.queryBuilder = queryBuilder;
        this.attribute = attribute;
    }
    
    public LongQueryBuilder equals(A object) {
        
        queryBuilder.addWhereClause(new EqualsClause(attribute, object));
        
        return queryBuilder;
    }
    
    public LongQueryBuilder in(ImmutableCollection<A> collection) {
        
        queryBuilder.addWhereClause(new InClause(attribute, collection));
        
        return queryBuilder;
    }

    public LongQueryBuilder notEquals(A object) {
        
        queryBuilder.addWhereClause(new NotEqualsClause(attribute, object));
        
        return queryBuilder;
    }

    public LongQueryBuilder like(String likeString) {
        
        queryBuilder.addWhereClause(new LikeClause(attribute, likeString));
        
        return queryBuilder;
    }
    
}
