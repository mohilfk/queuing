/**
 * Created by mohil.chandra on 13/12/16.
 */
public class RandomResolver implements IPartitionResolver {

    public int resolve(String key, int numOfPartitions) {
        return (int) (Math.random() * numOfPartitions);
    }
}
