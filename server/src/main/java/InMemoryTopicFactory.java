/**
 * Created by mohil.chandra on 13/12/16.
 */
public class InMemoryTopicFactory implements ITopicFactory {
    public Topic createTopic(Topic.TopicConfig config) {
        return new Topic(config);
    }
}
