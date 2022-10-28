package com.example.demo.KeyGenerator;

import org.hibernate.cache.internal.NaturalIdCacheKey;
import org.hibernate.cache.spi.CacheKeysFactory;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.persister.collection.CollectionPersister;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.stereotype.Component;

//extends DefaultCacheKeysFactory
@Component
public class CustomCacheKeysFactory implements CacheKeysFactory {

    public static final String SHORT_NAME = "custom";
    public static final CustomCacheKeysFactory INSTANCE = new CustomCacheKeysFactory();

    public static Object staticCreateCollectionKey(Object id, CollectionPersister persister, SessionFactoryImplementor factory, String tenantIdentifier) {
        return new CustomCacheKeyImplementation(id,persister.getRole().toString());
    }

    public static Object staticCreateEntityKey(Object id, EntityPersister persister, SessionFactoryImplementor factory, String tenantIdentifier) {
        return  "employee_"+id.toString();
    }

    public static Object staticCreateNaturalIdKey(Object[] naturalIdValues, EntityPersister persister, SharedSessionContractImplementor session) {
        return new NaturalIdCacheKey( naturalIdValues,  persister.getPropertyTypes(), persister.getNaturalIdentifierProperties(), persister.getRootEntityName(), session );
    }

    public static Object staticGetEntityId(Object cacheKey) {
        return cacheKey;
    }

    public static Object staticGetCollectionId(Object cacheKey) {
        return ((CustomCacheKeyImplementation) cacheKey).getId();
    }

    public static Object[] staticGetNaturalIdValues(Object cacheKey) {
        return ((NaturalIdCacheKey) cacheKey).getNaturalIdValues();
    }

    @Override
    public Object createCollectionKey(Object id, CollectionPersister persister, SessionFactoryImplementor factory, String tenantIdentifier) {
        return staticCreateCollectionKey(id, persister, factory, tenantIdentifier);
    }

    @Override
    public Object createEntityKey(Object id, EntityPersister persister, SessionFactoryImplementor factory, String tenantIdentifier) {
        return staticCreateEntityKey(id, persister, factory, tenantIdentifier);
    }

    @Override
    public Object createNaturalIdKey(Object[] naturalIdValues, EntityPersister persister, SharedSessionContractImplementor session) {
        return staticCreateNaturalIdKey(naturalIdValues, persister, session);
    }

    @Override
    public Object getEntityId(Object cacheKey) {
        return staticGetEntityId(cacheKey);
    }

    @Override
    public Object getCollectionId(Object cacheKey) {
        return staticGetCollectionId(cacheKey);
    }

    @Override
    public Object[] getNaturalIdValues(Object cacheKey) {
        return staticGetNaturalIdValues(cacheKey);
    }
}





