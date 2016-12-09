package shared;

import java.io.Serializable;

/**
 * Created by Tobias on 2016-12-08.
 */
public class ClientResponse implements Serializable
{
    public ClientResponse(float xMove, float yMove, float angleMove)
    {
        this.xMove = xMove;
        this.yMove = yMove;
        this.angleMove = angleMove;
    }

    public float xMove;
    public float yMove;
    public float angleMove;
}
