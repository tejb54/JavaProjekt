package shared;

import java.io.Serializable;

/**
 * Created by Tobias on 2016-12-08.
 */

/**
 * ClientResponse is the response from the client to the server.
 */
public class ClientResponse implements Serializable
{
    public ClientResponse(float xVelocity, float yVelocity) {
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
    }

    public float xVelocity;
    public float yVelocity;
}
