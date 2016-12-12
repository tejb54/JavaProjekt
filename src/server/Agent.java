package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tobias on 2016-12-07.
 */
public class Agent implements Serializable {

    public double xPos; //this is used by the GUI.
    public double yPos; //this is used by the GUI.
    public double xVelocity;
    public double yVelocity;

    double angle; //this is used by the GUI.

    public List<SimpleAgent> neighbors; //list off all the neighbors

    public Agent(double xPos, double yPos)
    {
        neighbors = new ArrayList<>();
        this.xPos = xPos;
        this.yPos = yPos;
    }

    SimpleAgent getSimplification()
    {
        return new SimpleAgent(xPos,yPos,xVelocity,yVelocity);
    }
}
