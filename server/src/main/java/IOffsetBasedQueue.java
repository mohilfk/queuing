import exceptions.OffsetNotFoundException;
import model.QueueResponse;

import java.util.List;

/**
 * Created by mohil.chandra on 10/12/16.
 *
 * The guy who stores the stuff.
 *
 *
 */
public interface IOffsetBasedQueue<T> {
    /*
     * To add to tail. return offset.
     */
    long add(T data) throws Exception;

    /*
     * To view from offset.
     */
    QueueResponse<T> peek(long offset) throws OffsetNotFoundException;

    /*
    To support batched retrieval.
     */
    List<QueueResponse<T>> peek(long startOffset, int batchSize) throws OffsetNotFoundException;

    long getHeadOffset();

    long getNextOffset(long offset);
}
