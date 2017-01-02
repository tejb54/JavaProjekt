package shared;

/**
 * Created by Tobias on 2016-12-06.
 */

/**
 * interface for the callbacks in the NetworkParser.
 */
public interface NetworkParserCallback
{
    void callback(String header, Object payload) throws Exception;
}
