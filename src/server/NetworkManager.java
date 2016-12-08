package server;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Tobias on 2016-12-06.
 */
public class NetworkManager extends Thread{
    private int port;

    public NetworkManager(int port) {
        this.port = port;
    }

    @Override
    public void run() {


        try {
            ServerSocket serverConnection = new ServerSocket(port);

            while (true)
            {
                //Wait for connections
                Socket newSocket = serverConnection.accept();
                System.out.println("new connection established!");

                //hand the connection over to it's own thread.
                ClientThread ct = new ClientThread(newSocket);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }
}
