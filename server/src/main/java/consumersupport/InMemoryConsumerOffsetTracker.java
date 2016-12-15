package consumersupport;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mohil.chandra on 13/12/16.
 */
public class InMemoryConsumerOffsetTracker implements IConsumerOffsetTracker {
    private static IConsumerOffsetTracker instance = null;

    Map<String, Long> offsetsState;

    public static synchronized IConsumerOffsetTracker getInstance() {
        if (instance == null) {
            instance = new InMemoryConsumerOffsetTracker();
        }

        return instance;
    }

    private InMemoryConsumerOffsetTracker() {
        offsetsState = new ConcurrentHashMap<String, Long>();

        // load saved states here
    }

    public long getOffset(String topicName, int partition, String consumerGroupName) {
        String key = createKey(topicName, partition, consumerGroupName);
        return offsetsState.get(key);
    }

    public void commitOffset(String topicName, int partition, String consumerGroupName, long offset) {
        String key = createKey(topicName, partition, consumerGroupName);
        offsetsState.put(key, offset);
    }

    private String createKey (String topicName, int partition, String consumerGroupName) {
        return topicName + ":" + partition + ":" + consumerGroupName;
    }


}
