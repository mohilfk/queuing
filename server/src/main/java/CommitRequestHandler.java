import consumersupport.IConsumerOffsetTracker;
import lombok.AllArgsConstructor;

/**
 * Created by mohil.chandra on 14/12/16.
 */
@AllArgsConstructor
public class CommitRequestHandler extends IHandler {

    String topicName;
    int partition;
    String consumerGroupName;
    long offset;

    public HandlerResponse handle() {

        IConsumerOffsetTracker consumerOffsetTracker = AbstractConsumerOffsetTrackerFactory.getConsumerOffsetTracker();
        consumerOffsetTracker.commitOffset(topicName,partition, consumerGroupName, offset);

        return new HandlerResponse(SUCCESS_RESPONSE, "");
    }
}
