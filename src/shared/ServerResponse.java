package shared;

import server.SimpleAgent;

import java.io.Serializable;

/**
 * Created by Tobias on 2016-12-10.
 */
public class ServerResponse implements Serializable {
    //TODO this class should contain data about an agents neighbors.

    public SimpleAgent[] neighbors;
    public float xPos;
    public float yPos;
    public float yVelocity;
    public float xVelocity;

}
