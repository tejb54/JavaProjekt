package server;

import sun.applet.Main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Tobias on 2016-12-06.
 */
public class ClientThread extends Thread {

    private ObjectInputStream in;
    private ObjectOutputStream out;
    public shared.NetworkParser parser;

    private Socket clientConnection;

    public ClientThread(Socket clientConnection) {
        try {
            this.clientConnection = clientConnection;
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


        parser.registerCallback("Connecting",(String header,Object payload)->{
            System.out.println(header);
            //Client sends the connection event
            MainServer.mainSimulation.addAgent(this,new Agent());

        });

        parser.registerCallback("basic",(String header,Object payload)->{
            System.out.println(header);
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
            ex.printStackTrace();
        }
    }
}
