package client;

import ui.ClientUI;

/**
 * Created by Tobias on 2016-12-13.
 */

/**
 * MainClient will start the client and the ui for the client.
 */
public class MainClient {

    public static void main(String[] args)
    {
        try {
            Client c = new Client();
            c.start();

            if(args[1].equals("true"))
            {
                ClientUI.startClientUI(args);
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
