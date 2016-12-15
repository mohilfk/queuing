import exceptions.KeyNotFoundException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mohil.chandra on 12/12/16.
 */
public class InMemoryMapStore implements IStore<Long, TheQueue.LinkedQueueElement> {

    ConcurrentHashMap<Long, TheQueue.LinkedQueueElement> store = new ConcurrentHashMap<Long, TheQueue.LinkedQueueElement>();

    public boolean add(Long key, TheQueue.LinkedQueueElement value) {
        store.put(key, value);
        return true;
    }

    public TheQueue.LinkedQueueElement get(Long key) throws KeyNotFoundException {
        return store.get(key);
    }

    public Map<Long, TheQueue.LinkedQueueElement> getInBatch(Long... keys) throws KeyNotFoundException {
        throw new UnsupportedOperationException();
    }

    public boolean containsKey(Long key) {
        return store.containsKey(key);
    }


}
