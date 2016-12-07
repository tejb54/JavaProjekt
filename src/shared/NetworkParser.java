package shared;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * Created by Tobias on 2016-12-06.
 */

public class NetworkParser {

    ObjectOutputStream out;
    ObjectInputStream in;

    public NetworkParser(ObjectInputStream in,ObjectOutputStream out) {
        this.out = out;
        this.in = in;
    }

    HashMap<String, NetworkParserCallback> callMap = new HashMap<>();

    //this will register the callbacks
    public void registerCallback(String header, NetworkParserCallback call)
    {
        System.out.println("Callback registered");
        callMap.put(header,call);
    }

    //this function will look for the matching callback for the header.
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


    public synchronized void sendData(CommunicationObj obj) throws Exception
    {
        out.writeObject(obj);
    }

}
