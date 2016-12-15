import consumersupport.InMemoryConsumerOffsetTracker;
import consumersupport.IConsumerOffsetTracker;

/**
 * Created by mohil.chandra on 14/12/16.
 */
public abstract class AbstractConsumerOffsetTrackerFactory {

    static IConsumerOffsetTracker getConsumerOffsetTracker() {
        return InMemoryConsumerOffsetTracker.getInstance();
    }
}
