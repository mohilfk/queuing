import model.IUserMessage;

/**
 * Created by mohil.chandra on 09/12/16.
 */
public class Broker implements IBroker<String> {


    public void submit(IUserMessage<String> message, String topic, Topic.TopicConfig config) {
        SubmitRequestHandler handler = new SubmitRequestHandler(
                new SubmitRequestHandler.SubmitData(message.getKey(), message.getMessage()), topic, config);
        handler.handle();
    }

    public Object read(String topicName, int partition, String consumerGroupName) {

        ReadRequestHandler handler = new ReadRequestHandler(topicName, partition, consumerGroupName);
        IHandler.HandlerResponse handlerResponse = handler.handle();
        return  handlerResponse.<Topic.QueueData>getBody();

    }
}
