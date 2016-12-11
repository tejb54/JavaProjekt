package client;

import shared.ClientResponse;
import shared.CommunicationObj;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;

/**
 * Created by Tobias on 2016-12-06.
 */
public class Client {

    public static void main(String[] args) throws Exception
    {
        runClient();
    }

    private static void runClient() throws Exception
    {
        Socket clientSocket = new Socket(InetAddress.getByName("localhost"),1234);
        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

        shared.NetworkParser clientParser = new shared.NetworkParser(in,out);

        clientParser.registerCallback("getAction",(String header, Object payload)->{
            System.out.println(header);
            Random r = new Random();
            clientParser.sendData(new CommunicationObj("basic",new ClientResponse(3f,3f)));
        });


        out.writeObject(new CommunicationObj("Connecting",123));
        while (true)
        {
            clientParser.parse();
        }

    }
}
