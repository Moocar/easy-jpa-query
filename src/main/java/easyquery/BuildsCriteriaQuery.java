/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package easyquery;

import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author anthony
 */
public interface BuildsCriteriaQuery {
    <S> CriteriaQuery<S> get(EasyQueryBuilder<S> easyQuery);
}
