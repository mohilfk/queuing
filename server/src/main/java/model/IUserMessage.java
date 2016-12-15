package model;

/**
 * Created by mohil.chandra on 09/12/16.
 */
public interface IUserMessage<I> {
    I getMessage();
    byte[] getBytes();
    String getKey();
}
