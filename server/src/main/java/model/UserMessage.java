package model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by mohil.chandra on 09/12/16.
 */
@AllArgsConstructor
@Data
public class UserMessage implements IUserMessage<String> {

    String key;
    String message;

    public byte[] getBytes() {
        return message.getBytes();
    }


}
