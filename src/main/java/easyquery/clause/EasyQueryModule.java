package easyquery.clause;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import easyquery.EasyQuery;

import javax.inject.Provider;
import javax.persistence.EntityManager;

public class EasyQueryModule extends AbstractModule{

    /**
     * Provides an instance of EasyQuery for making super awesome easy to understand sql statements
     */
    @Provides
    public EasyQuery getEasyQuery(Provider<EntityManager> entityManagerProvider) {
        return new EasyQuery(entityManagerProvider.get());
    }

    @Override
    protected void configure() {

    }
}
