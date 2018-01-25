package com.teamrocket.naasp.service.auth.oauth2.doa.mongo;

import com.teamrocket.naasp.service.auth.oauth2.doa.RefreshTokenDoa;
import com.teamrocket.naasp.service.auth.oauth2.model.RefreshToken;
import com.teamrocket.naasp.service.commons.doa.mongo.GenericMongoDoa;
import com.teamrocket.naasp.service.commons.mongo.MongoCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

/**
 * Mongo repository implementation for a Refresh Token DOA.
 */
@Component
@Conditional(MongoCondition.class)
public class MongoRefreshTokenDoa extends GenericMongoDoa<RefreshToken, String>implements RefreshTokenDoa {
    private static final String COLLECTION_NAME = "naaspRefreshToken";

    public MongoRefreshTokenDoa() {
        super(COLLECTION_NAME);
    }
}
