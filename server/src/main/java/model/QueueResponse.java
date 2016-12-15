package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * Created by mohil.chandra on 10/12/16.
 *
 * To send the data and offset info from the Queue.
 */
@AllArgsConstructor
@Getter
public class QueueResponse<T> {
    T data;
    long offset;
}
