package client;

import server.SimpleAgent;
import shared.ClientResponse;
import shared.CommunicationObj;
import shared.ServerResponse;
import shared.Vector2D;
import ui.ClientUI;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;

/**
 * Created by Tobias on 2016-12-06.
 */
public class Client extends Thread{


    public Vector2D calcAlignment(ServerResponse response)
    {

        Vector2D v = new Vector2D(0,0);

        if(response.neighbors.length == 0)
        {
            return v;
        }

        for(int i = 0; i < response.neighbors.length; i++)
        {
            SimpleAgent currentAgent = response.neighbors[i];
            v.dX += currentAgent.xVelocity;
            v.dY += currentAgent.yVelocity;
        }

        //take the average
        v.dX/=response.neighbors.length;
        v.dY/=response.neighbors.length;

        return v.normalize();
    }

    public Vector2D calcCohesion(ServerResponse response)
    {

        Vector2D v = new Vector2D(0,0);

        if(response.neighbors.length == 0)
        {
            return v;
        }

        for(int i = 0; i < response.neighbors.length; i++)
        {
            SimpleAgent currentAgent = response.neighbors[i];
            v.dX += currentAgent.xPos;
            v.dY += currentAgent.yPos;
        }

        v.dX /= response.neighbors.length;
        v.dY /= response.neighbors.length;

        v.dX = v.dX - response.xPos;
        v.dY = v.dY - response.yPos;

        return v.normalize();
    }

    public Vector2D calcSeparation(ServerResponse response)
    {

        Vector2D v = new Vector2D(0,0);

        if(response.neighbors.length == 0)
        {
            return v;
        }

        for(int i = 0; i < response.neighbors.length; i++)
        {
            SimpleAgent currentAgent = response.neighbors[i];
            v.dX += currentAgent.xPos - response.xPos;
            v.dY += currentAgent.yPos - response.yPos;
        }

        v.dX /= response.neighbors.length;
        v.dY /= response.neighbors.length;

        v.dX *= -1;
        v.dY *= -1;

        return v.normalize();
    }

    public void run()
    {
        try {
            Socket clientSocket = new Socket(InetAddress.getByName("localhost"),1234);
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

            shared.NetworkParser clientParser = new shared.NetworkParser(in,out);

            float speed = 7f;
            float separationWeight = 1.0f;
            float alignmentWeight = 1.5f;
            float cohesionWeight = 1.0f;


            Random r = new Random();

            clientParser.registerCallback("getAction",(String header, Object payload)->{
                ServerResponse response = (ServerResponse)payload;

                try {
                    ClientUI.clearScreen();
                    ClientUI.drawClient(250,250, Math.toDegrees(Math.atan2(response.yVelocity,response.xVelocity)));
                    ClientUI.drawNeighbors(response,2);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }



                Vector2D vA = calcAlignment(response);
                Vector2D vC = calcCohesion(response);
                Vector2D vS = calcSeparation(response);

                vA = vA.scale(alignmentWeight);
                vC = vC.scale(cohesionWeight);
                vS = vS.scale(separationWeight);


                Vector2D vR = new Vector2D(response.xVelocity,response.yVelocity);

                vR = vR.add(vA).add(vC).add(vS);
                vR.dX += (1 - r.nextFloat()*2)/2;
                vR.dY += (1 - r.nextFloat()*2)/2;
                vR = vR.normalize();

                clientParser.sendData(new CommunicationObj("basic",new ClientResponse( (float)vR.dX * speed, (float) vR.dY * speed)));

            });


            out.writeObject(new CommunicationObj("Connecting",123));
            while (true)
            {
                clientParser.parse();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }


    }
}
