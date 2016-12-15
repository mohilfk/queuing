import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by mohil.chandra on 13/12/16.
 *
 * This is the class that maintains the repository of Topics.
 */
public class TopicRepository {

    private static TopicRepository instance = null;
    ConcurrentHashMap<String, Topic> topics;
    ITopicFactory factory = new InMemoryTopicFactory();
    ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();

    /*
     * Will be injected ideally.
     */
    public static synchronized TopicRepository getInstance() {
        if (instance == null) {
            instance = new TopicRepository();
        }

        return instance;
    }

    public Topic getTopic(String topicName) {
        rwlock.readLock().lock();
        try {
            return topics.get(topicName);
        } finally {
            rwlock.readLock().unlock();
        }
    }

    public Topic createTopic(String topicName, Topic.TopicConfig config) {
        rwlock.writeLock().lock();
        try {
            Topic topic = factory.createTopic(config);
            topics.put(topicName, topic);
            return topic;
        } finally {
            rwlock.writeLock().unlock();
        }
    }
}
