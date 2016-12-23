package server;

import javafx.scene.paint.Color;
import shared.ClientResponse;
import shared.CommunicationObj;
import shared.MyPair;
import shared.ServerResponse;
import ui.ServerUI;

import java.util.*;
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

    private List<Obstacle> obstacleList = new ArrayList<>();

    private int failedConnections = 0;

    public Vector<MyPair<ClientThread, ClientResponse>> getInputData()
    {
        return inputData;
    }

    //add a response queue from all the clients
    public Simulation() {
        agentsMap = new ConcurrentHashMap<>();
    }


    //check if it's working with the ConcurrentHashMap.
    public void addAgent(ClientThread ct, Agent a)
    {
        synchronized (agentsMap){
            agentsMap.put(ct,a);
        }
    }

    public void removeAgent(ClientThread ct)
    {
        synchronized (agentsMap){
            agentsMap.remove(ct);
        }
    }

    public void addObstacle(double x, double y)
    {
        synchronized (obstacleList){
            obstacleList.add(new Obstacle(x,y,10));
        }

    }

    @Override
    public void run() {
        try {
            while (MainServer.runnigSimulation)
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



                //check for neighbors
                while (it2.hasNext())
                {
                    Map.Entry pair2 = (Map.Entry) it2.next();
                    Agent agentToCheck = (Agent) pair2.getValue();
                    if(agentToCheck != currentAgent)
                    {
                        if(Math.abs(agentToCheck.xPos - currentAgent.xPos) < currentAgent.neighborRadius && Math.abs(agentToCheck.yPos - currentAgent.yPos) < currentAgent.neighborRadius)
                        {
                            //System.out.println("neighbor added");
                            currentAgent.neighbors.add(agentToCheck.getSimplification());
                        }
                    }
                }

                ServerResponse serverResponse = new ServerResponse();
                serverResponse.neighbors = currentAgent.neighbors.toArray(new SimpleAgent[currentAgent.neighbors.size()]);
                serverResponse.xPos = (float) currentAgent.xPos;
                serverResponse.yPos = (float) currentAgent.yPos;
                serverResponse.xVelocity = (float) currentAgent.xVelocity;
                serverResponse.yVelocity = (float) currentAgent.yVelocity;


                //calculate obstacle for current agent
                synchronized (obstacleList)
                {
                    double smallestDistance = 0.0f;
                    for (Obstacle o: obstacleList)
                    {
                        double distance = Math.sqrt(Math.pow(o.xPos - currentAgent.xPos,2) + Math.pow(o.yPos-currentAgent.yPos,2));
                        if(distance < currentAgent.neighborRadius && ((distance < smallestDistance) || (distance != 0.0)))
                        {
                            smallestDistance = distance;
                            serverResponse.obstacle = o;
                        }
                    }
                }


                try {
                    ((ClientThread)pair.getKey()).parser.sendData(new CommunicationObj("getAction",serverResponse));
                }
                catch (Exception ex)
                {
                    System.out.println("Could not send data to that client.");
                }
            }




            //TODO this is more of a hack.
            //wait for the queue/vector to get all the commands/responses from clients.
            while (inputData.size() != agentsMap.size()){
                System.out.println("waiting for all the commands");
            }

            ui.ServerUI.clearScreen();

            synchronized (obstacleList)
            {
                for (Obstacle o: obstacleList)
                {
                    ui.ServerUI.drawCircle(o.xPos,o.yPos,10,new Color(0,0,1,1));
                }
            }



            for (MyPair<ClientThread,ClientResponse> p: inputData)
            {
                Agent currentAgent = agentsMap.get(p.getL());
                currentAgent.xPos += p.getR().xVelocity;
                currentAgent.yPos += p.getR().yVelocity;
                currentAgent.xVelocity = p.getR().xVelocity;
                currentAgent.yVelocity = p.getR().yVelocity;

                currentAgent.angle = Math.toDegrees(Math.atan2(p.getR().yVelocity,p.getR().xVelocity));


                if(currentAgent.xPos > ServerUI.width)
                {
                    currentAgent.xPos = 0;
                }

                if(currentAgent.xPos < 0)
                {
                    currentAgent.xPos = ServerUI.width;
                }

                if(currentAgent.yPos > ServerUI.height)
                {
                    currentAgent.yPos = 0;
                }

                if(currentAgent.yPos < 0)
                {
                    currentAgent.yPos = ServerUI.height;
                }
                ui.ServerUI.drawTriangle(currentAgent.xPos,currentAgent.yPos,currentAgent.angle);
                //ui.ServerUI.drawCircle(currentAgent.xPos,currentAgent.yPos,neighborRadius);
            }

            inputData.clear();
        }
    }
}
