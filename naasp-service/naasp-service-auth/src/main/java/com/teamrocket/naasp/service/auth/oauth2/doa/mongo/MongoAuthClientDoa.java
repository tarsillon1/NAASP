package com.teamrocket.naasp.service.auth.oauth2.doa.mongo;

import com.teamrocket.naasp.service.auth.oauth2.doa.AuthClientDoa;
import com.teamrocket.naasp.service.auth.oauth2.model.AuthClient;
import com.teamrocket.naasp.service.commons.doa.mongo.GenericMongoDoa;
import com.teamrocket.naasp.service.commons.mongo.MongoCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Mongo repository implementation for a Auth Client DOA.
 */
@Component
@Conditional(MongoCondition.class)
public class MongoAuthClientDoa extends GenericMongoDoa<AuthClient, String> implements AuthClientDoa {
    private static final String COLLECTION_NAME = "naaspClient";

    public MongoAuthClientDoa() {
        super(COLLECTION_NAME);
    }

    @Override
    public Collection<AuthClient> getAll() {
        return mongoTemplate.findAll(persistentClass, COLLECTION_NAME);
    }
}
