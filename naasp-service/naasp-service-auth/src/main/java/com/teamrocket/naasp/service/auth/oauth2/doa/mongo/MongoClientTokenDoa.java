package com.teamrocket.naasp.service.auth.oauth2.doa.mongo;

import com.teamrocket.naasp.service.auth.oauth2.doa.ClientTokenDoa;
import com.teamrocket.naasp.service.auth.oauth2.model.ClientToken;
import com.teamrocket.naasp.service.commons.doa.exception.FindObjectException;
import com.teamrocket.naasp.service.commons.doa.mongo.GenericMongoDoa;
import com.teamrocket.naasp.service.commons.mongo.MongoCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Mongo repository implementation for a Client Token DOA.
 */
@Component
@Conditional(MongoCondition.class)
public class MongoClientTokenDoa extends GenericMongoDoa<ClientToken, String>implements ClientTokenDoa {
    private static final String COLLECTION_NAME = "naaspClientToken";

    public MongoClientTokenDoa() {
        super(COLLECTION_NAME);
    }

    @Override
    public ClientToken deleteByAuthenticationId(String authenticationId) {
        Query query = new Query(Criteria.where("authenticationId").is(authenticationId));

        List<ClientToken> tokens = find(query);
        for (ClientToken clientToken : tokens) {
            delete(clientToken.getId());
        }

        return tokens.get(0);
    }

    @Override
    public ClientToken findByAuthenticationId(String authenticationId) {
        Query query = new Query(Criteria.where("authenticationId").is(authenticationId));
        return find(query).get(0);
    }

    private List<ClientToken> find (Query query) {
        try {
            List<ClientToken> tokens = mongoTemplate.find(query, persistentClass, COLLECTION_NAME);
            return tokens;
        } catch (Exception e) {
            throw new FindObjectException(ClientToken.class, query.toString(), e);
        }
    }
}
