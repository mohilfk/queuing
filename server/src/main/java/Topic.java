import exceptions.OffsetNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import model.QueueResponse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mohil.chandra on 12/12/16.
 *
 * represents a topic and maintains the partitions (1 queue per partition).
 * Further implementations will also handle the distributed partition scenario interacting with the
 * metadata store to interact with its partitions.
 */
public class Topic implements ITopic {

    TopicConfig config;
    Map<Integer, TheQueue> partitions;
    IPartitionResolver partitionResolver;

    public Topic(TopicConfig config) {
        this.config = config;
        initialize();
    }

    private void initialize() {
        partitions = new ConcurrentHashMap<Integer, TheQueue>(config.getNumOfPartitions());
        for (int i = 0; i < config.getNumOfPartitions(); i++) {
            TheQueue queue = new TheQueue();
            partitions.put(i, queue);
        }

        partitionResolver = this.config.getPartitionResolverType().resolver;
    }

    public void add(String key, String value) {
        int partition = this.partitionResolver.resolve(key, this.config.getNumOfPartitions());
        TheQueue queue = partitions.get(partition);
        queue.add(new QueueData(key, value));
    }

    public QueueResponse<QueueData> read(int partition, long offset) {
        TheQueue queue = partitions.get(partition);
        try {
            return queue.peek(offset);
        } catch (OffsetNotFoundException e) {
            return null;
        }

    }

    public long getNextOffset(int partition, long offset) {
        TheQueue queue = partitions.get(partition);
        return queue.getNextOffset(offset);
    }

    @Data
    @AllArgsConstructor
    public static class QueueData {
        String key;
        String data;
    }

    @Data
    public static class TopicConfig {
        int numOfPartitions;
        ResolverType partitionResolverType;
    }

    public enum ResolverType {
        RANDOM(new RandomResolver());

        IPartitionResolver resolver;

        ResolverType(IPartitionResolver resolver) {
            this.resolver = resolver;
        }
    }
}
