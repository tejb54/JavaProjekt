package shared;

import server.Obstacle;
import server.SimpleAgent;

import java.io.Serializable;

/**
 * Created by Tobias on 2016-12-10.
 */

/**
 * ServerResponse is a class used to send encapsulate the data from the server to the client.
 */
public class ServerResponse implements Serializable {

    public SimpleAgent[] neighbors;
    public Obstacle obstacle;
    public float xPos;
    public float yPos;
    public float yVelocity;
    public float xVelocity;

}
