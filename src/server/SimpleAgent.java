package server;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Tobias on 2016-12-12.
 */
public class SimpleAgent implements Serializable {
    double xPos; //this is used by the GUI.
    double yPos; //this is used by the GUI.
    double xVelocity;
    double yVelocity;

    public SimpleAgent(double xPos, double yPos, double xVelocity, double yVelocity) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
    }
}
