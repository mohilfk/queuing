import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by mohil.chandra on 13/12/16.
 */
public class SubmitRequestHandler extends IHandler {

    SubmitData requestdata;
    String topicName;
    Topic.TopicConfig topicConfig;

    // Inject Ideally
    TopicManager topicManager = new TopicManager();

    public SubmitRequestHandler(SubmitData data, String topicName, Topic.TopicConfig topicConfig) {
        this.topicName = topicName;
        this.topicConfig = topicConfig;
        this.requestdata = data;
    }

    public HandlerResponse handle() {

        Topic topic = topicManager.getOrCreateTopic(topicName, topicConfig);

        topic.add(requestdata.getKey(), requestdata.getValue());

        return new HandlerResponse(SUCCESS_RESPONSE, "");
    }

    @Data
    @AllArgsConstructor
    public static class SubmitData {
        String key;
        String value;
    }
}
