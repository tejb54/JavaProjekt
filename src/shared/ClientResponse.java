package shared;

import java.io.Serializable;

/**
 * Created by Tobias on 2016-12-08.
 */
public class ClientResponse implements Serializable
{
    public ClientResponse(float speed,float angleMove)
    {
        this.speed = speed;
        this.angleMove = angleMove;
    }


    public float angleMove;
    public float speed;
}
