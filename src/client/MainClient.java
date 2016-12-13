package client;

import ui.ClientUI;

/**
 * Created by Tobias on 2016-12-13.
 */
public class MainClient {

    public static void main(String[] args)
    {
        try {
            Client c = new Client();
            c.start();

            ClientUI.startClientUI(args);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
