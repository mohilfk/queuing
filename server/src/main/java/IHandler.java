import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by mohil.chandra on 13/12/16.
 *
 * This might be required to do some common request handler code.
 */
public abstract class IHandler {

    final static int SUCCESS_RESPONSE = 0;
    final static int FAILURE_RESPONSE = 1;

    // Should possibly return a response.
    public abstract <T> HandlerResponse<T> handle();


    // Possibly have different response objects for each kind of request
    // and the interface defined accordingly.
    @AllArgsConstructor
    @Data
    public static class HandlerResponse<T> {
        int repsponseCode;
        T body;
    }
}
