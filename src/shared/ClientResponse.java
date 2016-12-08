package shared;

import java.io.Serializable;

/**
 * Created by Tobias on 2016-12-08.
 */
public class ClientResponse implements Serializable
{
    public ClientResponse(float xMove, float yMove)
    {
        this.xMove = xMove;
        this.yMove = yMove;
    }

    public float xMove;
    public float yMove;
}
