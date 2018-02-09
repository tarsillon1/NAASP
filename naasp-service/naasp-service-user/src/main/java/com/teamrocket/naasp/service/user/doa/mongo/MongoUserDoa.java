package com.teamrocket.naasp.service.user.doa.mongo;

import com.teamrocket.naasp.service.commons.doa.mongo.GenericMongoDoa;
import com.teamrocket.naasp.service.commons.mongo.MongoCondition;
import com.teamrocket.naasp.service.user.doa.UserDoa;
import com.teamrocket.naasp.service.user.model.User;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

/**
 * Mongo implementation of a doa for user data.
 */
@Component
@Conditional(MongoCondition.class)
public class MongoUserDoa extends GenericMongoDoa<User, String> implements UserDoa {
    private static final String COLLECTION_NAME = "naaspUser";
    public MongoUserDoa() {
        super(COLLECTION_NAME);
    }
}
