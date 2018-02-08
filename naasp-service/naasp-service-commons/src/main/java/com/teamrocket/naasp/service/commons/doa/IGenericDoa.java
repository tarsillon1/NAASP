package com.teamrocket.naasp.service.commons.doa;

import java.io.Serializable;

/**
 * A generic DOA for deleting, updating, creating and retrieving objects.
 * @param <T> the type of object
 * @param <ID> the unique id of the object
 */
public interface IGenericDoa<T, ID extends Serializable> {
    T delete (ID id);
    T update (T object);
    T create (T object);
    T get (ID id);
}
