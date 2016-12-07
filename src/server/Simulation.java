package server;

import shared.CommunicationObj;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Tobias on 2016-12-06.
 */
public class Simulation extends Thread{

    //Simulation will use a data structure to keep track of all the connected clients
    //this data structure will ensure that there are only one agent per client
    //I use a hashMap for this.
    private ConcurrentHashMap<ClientThread, Agent> agentsMap;


    //add a response queue from all the clients

    public Simulation() {
        agentsMap = new ConcurrentHashMap<>();
    }


    //TODO this needs to be synced.
    //check if it's working with the ConcurrentHashMap.
    public void addAgent(ClientThread ct, Agent a)
    {
        agentsMap.put(ct,a);
    }

    @Override
    public void run() {
        try {
            while (true)
            {
                update();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    private void update() throws Exception
    {
        Thread.sleep(1000);
        Iterator it = agentsMap.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry pair = (Map.Entry) it.next();
            ((ClientThread)pair.getKey()).parser.sendData(new CommunicationObj("getAction",123));
        }
    }
}
