import model.IUserMessage;
import model.QueueReference;
import model.QueueResponse;

import java.util.List;
import java.util.Queue;

/**
 * Created by mohil.chandra on 09/12/16.
 *
 * The guy who accepts requests.
 *
 * Have to rework on the request and response. Have neglected the correctness for
 * completion.
 *
 */
public interface IBroker<T> {
    void submit(IUserMessage<T> message, String topic, Topic.TopicConfig config);
    Object read(String topicName, int partition, String consumerGroupName);
}
