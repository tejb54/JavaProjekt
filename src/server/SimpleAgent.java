package server;

import java.io.Serializable;

/**
 * Created by Tobias on 2016-12-12.
 */

/**
 * <P>This class is used to send data over the network.</P>
 */
public class SimpleAgent implements Serializable {
    public double xPos;
    public double yPos;
    public double xVelocity;
    public double yVelocity;

    public SimpleAgent(double xPos, double yPos, double xVelocity, double yVelocity) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
    }
}
