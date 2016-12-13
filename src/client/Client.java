package client;

import com.sun.javafx.geom.Vec2f;
import server.Agent;
import server.SimpleAgent;
import shared.ClientResponse;
import shared.CommunicationObj;
import shared.ServerResponse;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;
import java.util.Random;

/**
 * Created by Tobias on 2016-12-06.
 */
public class Client {

    public static void main(String[] args)
    {
        try {
            runClient();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

    }

    static float angle = 0;

    public static Vec2f calcAlignment(ServerResponse response)
    {
        double length = 1;
        double x = 0;
        double y = 0;

        for(int i = 0; i < response.neighbors.length; i++)
        {
            SimpleAgent currentAgent = response.neighbors[i];
            x += currentAgent.xVelocity;
            y += currentAgent.yVelocity;
        }

        x/=response.neighbors.length;
        y/=response.neighbors.length;

        length = Math.sqrt(Math.pow(x,2) + Math.pow(y,2));

        return new Vec2f((float) (x/length),(float)(y/length));
    }

    static public Vec2f calcCohesion(ServerResponse response)
    {
        double length = 1;
        double x = 0;
        double y = 0;

        for(int i = 0; i < response.neighbors.length; i++)
        {
            SimpleAgent currentAgent = response.neighbors[i];
            x += currentAgent.xPos;
            y += currentAgent.yPos;
        }

        x/=response.neighbors.length;
        y/=response.neighbors.length;

        x = x - response.xPos;
        y = y - response.yPos;

        length = Math.sqrt(Math.pow(x,2) + Math.pow(y,2));

        return new Vec2f((float) (x/length),(float)(y/length));
    }

    private static void runClient() throws Exception
    {
        Socket clientSocket = new Socket(InetAddress.getByName("localhost"),1234);
        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

        shared.NetworkParser clientParser = new shared.NetworkParser(in,out);

        Random r = new Random();
        angle = r.nextInt(360);

        clientParser.registerCallback("getAction",(String header, Object payload)->{
            ServerResponse response = (ServerResponse)payload;
            System.out.println(response.neighbors.length);

            Vec2f vA = calcAlignment(response);
            Vec2f vC = calcCohesion(response);



                clientParser.sendData(new CommunicationObj("basic",new ClientResponse( (float)Math.cos(angle),(float)Math.sin(angle))));

        });


        out.writeObject(new CommunicationObj("Connecting",123));
        while (true)
        {
            clientParser.parse();
        }

    }
}
