package server;

import shared.ClientResponse;
import shared.MyPair;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

/**
 * Created by Tobias on 2016-12-06.
 */

/**
 * ClientThread is responsible for receiving data from the client amd sending it to the simulation through a vector.
 */
public class ClientThread extends Thread {

    private ObjectInputStream in;
    private ObjectOutputStream out;
    shared.NetworkParser parser;


    /**
     * The Constructor will create the input and output stream objects,
     * the constructor will also start it's own thread.
     * @param clientConnection the socket used for network communication.
     */
    public ClientThread(Socket clientConnection) {
        try {
            this.in = new ObjectInputStream(clientConnection.getInputStream());
            this.out = new ObjectOutputStream(clientConnection.getOutputStream());
            parser = new shared.NetworkParser(in,out);
            start();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {

        //This will add a new agent to the simulation.
        parser.registerCallback("Connecting",(String header,Object payload)->{
            System.out.println(header);
            //Client sends the connection event
            Random r = new Random();
            float angle = r.nextInt(360);
            MainServer.mainSimulation.addAgent(this,new Agent(50,50, Math.cos(angle), Math.sin(angle),60));
        });

        //send the data through a vector(inputData) to the simulation.
        parser.registerCallback("basic",(String header,Object payload)->{
            MainServer.mainSimulation.getInputData().add(new MyPair<>(this, (ClientResponse)payload));
        });

        try {
            while (true)
            {
                parser.parse();
            }
        }
        catch (Exception ex)
        {
            System.out.println("Client probably disconnected!");
            MainServer.mainSimulation.removeAgent(this);
            //ex.printStackTrace();
        }
    }
}
