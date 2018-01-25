package com.teamrocket.naasp.service.auth.user.doa.mongo;

import com.teamrocket.naasp.service.auth.user.AuthUser;
import com.teamrocket.naasp.service.auth.user.doa.AuthUserDao;
import com.teamrocket.naasp.service.commons.doa.mongo.GenericMongoDoa;
import com.teamrocket.naasp.service.commons.mongo.MongoCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

@Component
@Conditional(MongoCondition.class)
public class MongoAuthUserDoa extends GenericMongoDoa<AuthUser, String> implements AuthUserDao {
    private static final String COLLECTION_NAME = "naaspAuth";

    public MongoAuthUserDoa() {
        super(COLLECTION_NAME);
    }
}
