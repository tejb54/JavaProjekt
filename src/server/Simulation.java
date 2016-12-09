package server;

import shared.ClientResponse;
import shared.CommunicationObj;
import shared.MyPair;

import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Tobias on 2016-12-06.
 */
public class Simulation extends Thread{

    //Simulation will use a data structure to keep track of all the connected clients
    //this data structure will ensure that there are only one agent per client
    //I use a hashMap for this.
    private ConcurrentHashMap<ClientThread, Agent> agentsMap;

    Vector<MyPair<ClientThread, ClientResponse>> inputData = new Vector<>();

    public Vector<MyPair<ClientThread, ClientResponse>> getInputData()
    {
        return inputData;
    }

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
        //TODO check that the iterators are thread safe.

        //TODO should not use sleep
        Thread.sleep(100);

        ui.ServerUI.clearScreen();
        //send getAction command to all the clients.
        Iterator it = agentsMap.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry pair = (Map.Entry) it.next();
            ((ClientThread)pair.getKey()).parser.sendData(new CommunicationObj("getAction",123));
            ui.ServerUI.drawTriangle(((Agent)pair.getValue()).xPos,((Agent)pair.getValue()).yPos,((Agent)pair.getValue()).angle);
        }

        //TODO this is more off a hack.
        //check the queue
        while (inputData.size() != agentsMap.size()){}

        for (MyPair<ClientThread,ClientResponse> p: inputData)
        {
            agentsMap.get(p.getL()).xPos += p.getR().xMove;
            agentsMap.get(p.getL()).yPos += p.getR().yMove;
            agentsMap.get(p.getL()).angle += p.getR().angleMove;
        }

        inputData.clear();


        //do simulation or something?
    }
}
