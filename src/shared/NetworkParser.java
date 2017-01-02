package shared;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * Created by Tobias on 2016-12-06.
 */


/**
 * <p>NetworkParser is responsible for receiving and sending data over the network.</p>
 */
public class NetworkParser {

    private ObjectOutputStream out;
    private ObjectInputStream in;

    private HashMap<String, NetworkParserCallback> callMap = new HashMap<>();

    public NetworkParser(ObjectInputStream in,ObjectOutputStream out) {
        this.out = out;
        this.in = in;
    }


    /**
     * <p>registerCallback will register a callback function to a network message</p>
     * @param header header/key that will call the proper callback.
     * @param call the function to be called.
     */
    public void registerCallback(String header, NetworkParserCallback call)
    {
        System.out.println("Callback registered");
        callMap.put(header,call);
    }

    /**
     * <p>parse method is the method that reads the socket stream and calls the right callback. It should run in it's own thread.</p>
     * @throws Exception Network errors
     */
    public void parse() throws Exception
    {
        CommunicationObj inObj = (CommunicationObj) in.readObject();

        if(callMap.size() >= 1 && callMap.containsKey(inObj.header))
        {
            //call the callback
            callMap.get(inObj.header).callback(inObj.header,inObj.payload);
        }
        else{
            System.out.println("no callbacks have been registered.");
        }
    }


    /**
     * sendData will let you send data over the network.
     * @param obj
     * @throws Exception Network errors
     */
    public synchronized void sendData(CommunicationObj obj) throws Exception
    {
        out.writeObject(obj);
    }

}
