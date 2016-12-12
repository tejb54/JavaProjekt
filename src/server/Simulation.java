package server;

import shared.ClientResponse;
import shared.CommunicationObj;
import shared.MyPair;
import shared.ServerResponse;

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

    private float neighborRadius = 60f;

    //TODO this needs to be synced.
    //check if it's working with the ConcurrentHashMap.
    public void addAgent(ClientThread ct, Agent a)
    {
        synchronized (agentsMap){
            agentsMap.put(ct,a);
        }

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


        //TODO should not use sleep
        Thread.sleep(50);

        ui.ServerUI.clearScreen();

        //TODO check that the iterators are thread safe.


        synchronized (agentsMap)
        {
            //send getAction command to all the clients.
            Iterator it = agentsMap.entrySet().iterator();
            while (it.hasNext())
            {
                Map.Entry pair = (Map.Entry) it.next();

                Agent currentAgent = (Agent)pair.getValue();
                currentAgent.neighbors.clear();

                Iterator it2 = agentsMap.entrySet().iterator();
                ServerResponse serverResponse = new ServerResponse();
                //check for neighbors
                while (it2.hasNext())
                {
                    Map.Entry pair2 = (Map.Entry) it2.next();
                    Agent agentToCheck = (Agent) pair2.getValue();
                    if(agentToCheck != currentAgent)
                    {
                        if(Math.abs(agentToCheck.xPos - currentAgent.xPos) < neighborRadius && Math.abs(agentToCheck.yPos - currentAgent.yPos) < neighborRadius)
                        {
                            System.out.println("neighbor added");
                            currentAgent.neighbors.add(agentToCheck.getSimplification());
                        }
                    }
                }

                serverResponse.neighbors = currentAgent.neighbors.toArray(new SimpleAgent[currentAgent.neighbors.size()]);
                serverResponse.xPos = (float) currentAgent.xPos;
                serverResponse.yPos = (float) currentAgent.yPos;
                serverResponse.xVelocity = (float) currentAgent.xVelocity;
                serverResponse.yVelocity = (float) currentAgent.yVelocity;


                ((ClientThread)pair.getKey()).parser.sendData(new CommunicationObj("getAction",serverResponse));
                ui.ServerUI.drawTriangle(currentAgent.xPos,currentAgent.yPos,currentAgent.angle);
                ui.ServerUI.drawCircle(currentAgent.xPos,currentAgent.yPos,neighborRadius);
            }
        }


        //TODO this is more of a hack.
        //wait for the queue/vector to get all the commands/responses from clients.
        while (inputData.size() != agentsMap.size()){}

        for (MyPair<ClientThread,ClientResponse> p: inputData)
        {
            Agent currentAgent = agentsMap.get(p.getL());
            currentAgent.xPos += p.getR().xVelocity;
            currentAgent.yPos += p.getR().yVelocity;
            currentAgent.xVelocity = p.getR().xVelocity;
            currentAgent.yVelocity = p.getR().yVelocity;

            currentAgent.angle = Math.toDegrees(Math.atan2(p.getR().yVelocity,p.getR().xVelocity));

            if(currentAgent.xPos > 500)
            {
                currentAgent.xPos = 0;
            }

            if(currentAgent.xPos < 0)
            {
                currentAgent.xPos = 500;
            }
        }

        inputData.clear();


        //do simulation or something?
        //calculate the neighbors for each client.
    }
}
