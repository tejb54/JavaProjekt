package server;

/**
 * Created by Tobias on 2016-12-06.
 */

/**
 * Main server is responsible for starting everything.
 * It will start the NetworkManager, simulation and the UI.
 */
public class MainServer {
    public static Simulation mainSimulation;
    public static boolean runningSimulation = true;

    public static void main(String[] args)
    {
        //Start the networkManager
        NetworkManager networkManager = new NetworkManager(1234);
        networkManager.start();

        //start the simulation
        mainSimulation = new Simulation();
        mainSimulation.start();

        //start the ui on it's own thread
        ui.ServerUI.startUI(new String[]{"empty"});
    }
}
