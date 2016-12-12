package client;

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
            clientParser.sendData(new CommunicationObj("basic",new ClientResponse( (float)Math.cos(angle),(float)Math.sin(angle))));
        });


        out.writeObject(new CommunicationObj("Connecting",123));
        while (true)
        {
            clientParser.parse();
        }

    }
}
