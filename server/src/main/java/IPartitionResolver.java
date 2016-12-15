/**
 * Created by mohil.chandra on 13/12/16.
 */
public interface IPartitionResolver {

    int resolve(String key, int numOfPartitions);
}
