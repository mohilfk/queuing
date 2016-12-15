import model.QueueResponse;

/**
 * Created by mohil.chandra on 13/12/16.
 *
 * The key value can be made generic.
 *
 * This class will act as the interface to a topic. In a distributed scenario the implementation
 * will have the logic of connecting to the correct node to perform the add and read operations.
 */
public interface ITopic {
    void add(String key, String value);

    QueueResponse<Topic.QueueData> read(int partition, long offset);

    long getNextOffset(int partition, long offset);
}
