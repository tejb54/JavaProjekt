package server;

/**
 * Created by Tobias on 2016-12-06.
 */
public class MainServer {
    static Simulation mainSimulation;
    public static boolean runnigSimulation = true;

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
