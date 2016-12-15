import exceptions.NoMoreDataException;
import exceptions.OffsetNotFoundException;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import model.QueueResponse;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by mohil.chandra on 10/12/16.
 *
 * Adds the offset logic to the underlying store.
 */
public class TheQueue implements IOffsetBasedQueue<Topic.QueueData> {

    ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();
    IStore<Long, LinkedQueueElement> store = new InMemoryMapStore();
    OffsetState offsetState = new OffsetState();

    public long add(Topic.QueueData data) {

        if (data == null) {
            throw new RuntimeException("Null not allowed.");
        }

        LinkedQueueElement currentElement = new LinkedQueueElement(data);
        long currentOffset;

        rwlock.writeLock().lock();
        try {
            LinkedQueueElement lastElement = null;
            if (offsetState.getLastOffset() != -1)
                lastElement = store.get(offsetState.getLastOffset());

            lastElement.setNextElement(currentElement);

            offsetState.increment();
            currentOffset = offsetState.getLastOffset();
            if (!store.add(currentOffset, currentElement)){
                offsetState.decrement();
            }

        } finally {
            rwlock.writeLock().unlock();
        }

        return currentOffset;
    }

    public QueueResponse<Topic.QueueData> peek(long offset) throws OffsetNotFoundException {
        if (offset > offsetState.getLastOffset()) {
            throw new NoMoreDataException();
        }
        LinkedQueueElement queueElement = readFromQueue(offset);
        return new QueueResponse<Topic.QueueData>(queueElement.getData(), offset);

    }

    public List<QueueResponse<Topic.QueueData>> peek(long startOffset, int batchSize) throws OffsetNotFoundException {
        if (startOffset > offsetState.getLastOffset()) {
            throw new NoMoreDataException();
        }
        LinkedList<QueueResponse<Topic.QueueData>> returnList = new LinkedList<QueueResponse<Topic.QueueData>>();
        LinkedQueueElement queueElement = readFromQueue(startOffset);
        long offset = startOffset;
        for (int i = 0; i < batchSize; i++) {
            returnList.add(new QueueResponse<Topic.QueueData>(queueElement.getData(), offset));
            queueElement = queueElement.getNextElement();
            if (queueElement == null) {
                break;
            }
            offset++;
        }

        return returnList;
    }

    private LinkedQueueElement readFromQueue(long offset) throws OffsetNotFoundException {
        rwlock.readLock().lock();
        try {
            if (!store.containsKey(offset)) {
                throw new OffsetNotFoundException();
            }
            return store.get(offset);
        } finally {
            rwlock.readLock().unlock();
        }
    }

    public long getHeadOffset() {
        return offsetState.getHeadOffset();
    }

    public long getNextOffset(long offset) {
        return ++offset;
    }

    @Getter
    @Setter
    public static class LinkedQueueElement {
        public LinkedQueueElement(Topic.QueueData data) {
            this.data = data;
            this.nextElement = null;
        }

        Topic.QueueData data;
        LinkedQueueElement nextElement;
    }

    @Data
    private class OffsetState {
        long headOffset = 0;
        long lastOffset = -1;

        public void increment() {
            ++lastOffset;
        }

        public void decrement() {
            --lastOffset;
        }
    }

}
