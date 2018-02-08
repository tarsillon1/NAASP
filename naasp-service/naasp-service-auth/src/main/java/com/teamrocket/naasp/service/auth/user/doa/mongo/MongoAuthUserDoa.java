package com.teamrocket.naasp.service.auth.user.doa.mongo;

import com.teamrocket.naasp.service.auth.oauth2.model.AuthApproval;
import com.teamrocket.naasp.service.auth.user.AuthUser;
import com.teamrocket.naasp.service.auth.user.doa.AuthUserDao;
import com.teamrocket.naasp.service.commons.doa.exception.FindObjectException;
import com.teamrocket.naasp.service.commons.doa.mongo.GenericMongoDoa;
import com.teamrocket.naasp.service.commons.mongo.MongoCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Conditional(MongoCondition.class)
public class MongoAuthUserDoa extends GenericMongoDoa<AuthUser, String> implements AuthUserDao {
    private static final String COLLECTION_NAME = "naaspAuth";

    public MongoAuthUserDoa() {
        super(COLLECTION_NAME);
    }

    @Override
    public AuthUser findAuthUserByUserId(String userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        List<AuthUser> authUsers = find(query);
        return !authUsers.isEmpty() ? authUsers.get(0) : null;
    }

    private List<AuthUser> find (Query query) {
        try {
            List<AuthUser> authUsers = mongoTemplate.find(query, persistentClass, COLLECTION_NAME);
            return authUsers;
        } catch (Exception e) {
            throw new FindObjectException(AuthApproval.class, query.toString(), e);
        }
    }
}
