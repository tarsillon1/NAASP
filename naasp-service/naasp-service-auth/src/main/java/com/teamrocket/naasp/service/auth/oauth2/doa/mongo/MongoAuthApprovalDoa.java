package com.teamrocket.naasp.service.auth.oauth2.doa.mongo;

import com.teamrocket.naasp.service.auth.oauth2.doa.AuthApprovalDoa;
import com.teamrocket.naasp.service.auth.oauth2.model.AuthApproval;
import com.teamrocket.naasp.service.commons.doa.exception.FindObjectException;
import com.teamrocket.naasp.service.commons.doa.mongo.GenericMongoDoa;
import com.teamrocket.naasp.service.commons.mongo.MongoCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Mongo repository implementation for a Auth Approval DOA.
 */
@Component
@Conditional(MongoCondition.class)
public class MongoAuthApprovalDoa extends GenericMongoDoa<AuthApproval, String> implements AuthApprovalDoa {
    private static final String COLLECTION_NAME = "naaspApproval";

    public MongoAuthApprovalDoa() {
        super(COLLECTION_NAME);
    }

    @Override
    public List<AuthApproval> findByUserIdAndClientId(String userId, String clientId) {
        Query query = new Query(Criteria.where("userId").is(userId)
                .and("clientId").is(clientId));
        return find(query);
    }

    @Override
    public Collection<AuthApproval> updateOrCreate(Collection<AuthApproval> authApprovals) {
        List<AuthApproval> insert = new ArrayList<>();
        for (AuthApproval authApproval : authApprovals) {
            if (get (authApproval.getId()) == null) {
                insert.add(authApproval);
            } else {
                mongoTemplate.save(authApproval);
            }
        }
        mongoTemplate.insertAll(insert);

        return authApprovals;
    }

    private List<AuthApproval> find (Query query) {
        try {
            List<AuthApproval> tokens = mongoTemplate.find(query, persistentClass, COLLECTION_NAME);
            return tokens;
        } catch (Exception e) {
            throw new FindObjectException(AuthApproval.class, query.toString(), e);
        }
    }
}
