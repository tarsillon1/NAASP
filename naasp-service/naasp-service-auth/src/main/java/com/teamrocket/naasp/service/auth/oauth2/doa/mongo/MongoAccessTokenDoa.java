package com.teamrocket.naasp.service.auth.oauth2.doa.mongo;

import com.teamrocket.naasp.service.auth.oauth2.doa.AccessTokenDoa;
import com.teamrocket.naasp.service.auth.oauth2.model.AccessToken;
import com.teamrocket.naasp.service.commons.doa.exception.FindObjectException;
import com.teamrocket.naasp.service.commons.doa.mongo.GenericMongoDoa;
import com.teamrocket.naasp.service.commons.mongo.MongoCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Mongo repository implementation for a Access Token DOA.
 */
@Component
@Conditional(MongoCondition.class)
public class MongoAccessTokenDoa extends GenericMongoDoa<AccessToken, String> implements AccessTokenDoa {
    private static final String COLLECTION_NAME = "naaspAccessToken";

    public MongoAccessTokenDoa() {
        super(COLLECTION_NAME);
    }

    @Override
    public AccessToken findByRefreshToken(String refreshToken) {
        Query query = new Query(Criteria.where("refreshToken").is(refreshToken));
        List<AccessToken> accessTokens = find(query);
        return !accessTokens.isEmpty() ? accessTokens.get(0) : null;
    }

    @Override
    public AccessToken findByAuthenticationId(String authenticationId) {
        Query query = new Query(Criteria.where("authenticationId").is(authenticationId));
        List<AccessToken> accessTokens = find(query);
        return !accessTokens.isEmpty() ? accessTokens.get(0) : null;
    }

    @Override
    public List<AccessToken> findByClientIdAndUserName(String clientId, String userName) {
        Query query = new Query(Criteria.where("clientId").is(clientId).and("entity.userName").is(userName));
        return find(query);
    }

    @Override
    public List<AccessToken> findByClientId(String clientId) {
        Query query = new Query(Criteria.where("clientId").is(clientId));
        return find(query);
    }

    private List<AccessToken> find (Query query) {
        try {
            List<AccessToken> tokens = mongoTemplate.find(query, persistentClass, COLLECTION_NAME);
            return tokens;
        } catch (Exception e) {
            throw new FindObjectException(AccessToken.class, query.toString(), e);
        }
    }
}
