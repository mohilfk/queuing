import consumersupport.IConsumerOffsetTracker;
import exceptions.NoTopicFoundException;
import model.QueueResponse;

/**
 * Created by mohil.chandra on 13/12/16.
 *
 * Not coded to support batch read currently. Although supported at the queue level.
 */

public class ReadRequestHandler extends IHandler {

    String topicName;
    int partition;
    String consumerGroupName;

    public ReadRequestHandler(String topicName, int partition, String consumerGroupName) {
        this.topicName = topicName;
        this.partition = partition;
        this.consumerGroupName = consumerGroupName;
    }

    // inject ideally
    private TopicManager topicManager = new TopicManager();

    public HandlerResponse handle() {
        IConsumerOffsetTracker consumerOffsetTracker = AbstractConsumerOffsetTrackerFactory.getConsumerOffsetTracker();
        long offset = consumerOffsetTracker.getOffset(topicName, partition, consumerGroupName);

        try {
            Topic topic = topicManager.getTopic(topicName);

            long nextOffset = topic.getNextOffset(partition, offset);
            QueueResponse<Topic.QueueData> queueResponse = topic.read(partition, nextOffset);

            return new HandlerResponse(SUCCESS_RESPONSE, queueResponse.getData());

        } catch (NoTopicFoundException e) {
            e.printStackTrace();
            // handle with appropriate response
            return new HandlerResponse(FAILURE_RESPONSE, e.getMessage());
        }
    }
}
