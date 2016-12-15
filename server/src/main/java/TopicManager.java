import exceptions.NoTopicFoundException;

/**
 * Created by mohil.chandra on 14/12/16.
 */
public class TopicManager {

    public Topic getTopic(String topicName) throws NoTopicFoundException {
        Topic topic = TopicRepository.getInstance().getTopic(topicName);
        if (topic == null) {
            throw new NoTopicFoundException();
        }

        return topic;
    }

    public Topic getOrCreateTopic(String topicName, Topic.TopicConfig config) {
        Topic topic = TopicRepository.getInstance().getTopic(topicName);
        if (topic == null) {
            topic = TopicRepository.getInstance().createTopic(topicName, config);
        }

        return topic;
    }

}
