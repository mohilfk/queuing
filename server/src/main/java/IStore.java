import exceptions.KeyNotFoundException;

import java.util.List;
import java.util.Map;

/**
 * Created by mohil.chandra on 12/12/16.
 *
 * This is a an abstraction for the low level storage that might be used in the queuing system.
 * Has to support thread safe concurrent access to the underlying store.
 */
public interface IStore<K,V> {
    boolean add(K key, V value);

    V get(K key) throws KeyNotFoundException;

    Map<K,V> getInBatch(K... keys);

    boolean containsKey(K key);

}
