package com.teamrocket.naasp.service.commons.doa.mongo;

import com.mongodb.DuplicateKeyException;
import com.teamrocket.naasp.service.commons.doa.IGenericDoa;
import com.teamrocket.naasp.service.commons.doa.exception.*;
import com.teamrocket.naasp.service.commons.mongo.MongoCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

/**
 * A mongo repository implementation of generic DOA.
 */
@Conditional(MongoCondition.class)
public class GenericMongoDoa <T, ID extends Serializable> implements IGenericDoa<T, ID> {
    protected MongoTemplate mongoTemplate;

    protected final Class<T> persistentClass;

    private final String collectionName;

    public GenericMongoDoa (final String collectionName) {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        this. collectionName = collectionName;
    }

    @Override
    public T get (ID id) {
        try {
            return mongoTemplate.findById(id, persistentClass, collectionName);
        } catch (Exception e) {
            throw new GetObjectException(persistentClass, id, e);
        }
    }

    @Override
    public T update(ID id, T object) {
        try {
            T obj = get(id);

            if (obj == null) {
                throw new ObjectNotFoundException(persistentClass, id);
            }

            mongoTemplate.save(object, collectionName);

            return object;
        } catch (Exception e) {
            throw new UpdateObjectException(persistentClass, id, e);
        }
    }

    @Override
    public T delete (ID id) {
        try {
            T obj = get(id);

            if (obj != null) {
                this.mongoTemplate.remove(obj);
            }

            return obj;
        } catch (Exception e) {
            throw new DeleteObjectException(persistentClass, id, e);
        }
    }

    @Override
    public T create (T obj) {
        try {
            mongoTemplate.insert(obj, collectionName);
            return obj;
        } catch (DuplicateKeyException e) {
            throw new DuplicateObjectException(persistentClass, obj, e);
        } catch (Exception e) {
            throw new CreateObjectException(persistentClass, obj, e);
        }
    }

    @Autowired
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

}
