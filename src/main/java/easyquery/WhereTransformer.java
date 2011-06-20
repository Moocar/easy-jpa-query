package easyquery;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import easyquery.clause.WhereClause;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class WhereTransformer<E> {
    
    private final CriteriaBuilder criteriaBuilder;
    private final Root<E> root;

    public WhereTransformer(CriteriaBuilder criteriaBuilder, Root<E> root) {
        this.criteriaBuilder = criteriaBuilder;
        this.root = root;
    }
    
    public <E> Predicate transform(WhereClause whereClause) {
        
        return whereClause.build(criteriaBuilder, root);
    }

    public Predicate[] transformWheres(ImmutableList<WhereClause> whereClauses) {
        
        return Collections2.transform(whereClauses, new Function<WhereClause, Predicate>() {

            @Override
            public Predicate apply(WhereClause f) {
                
                return transform(f);
            }
            
        }).toArray(new Predicate[whereClauses.size()]);
    }
}
