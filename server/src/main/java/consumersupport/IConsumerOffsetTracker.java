package consumersupport;

/**
 * Created by mohil.chandra on 14/12/16.
 */
public interface IConsumerOffsetTracker {
    long getOffset(String topicName, int partition, String consumerGroupName);

    void commitOffset(String topicName, int partition, String consumerGroupName, long offset);
}
