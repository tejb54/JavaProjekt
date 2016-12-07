package shared;

/**
 * Created by Tobias on 2016-12-06.
 */
public interface NetworkParserCallback
{
    void callback(String header, Object payload) throws Exception;
}
