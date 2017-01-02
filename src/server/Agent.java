package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tobias on 2016-12-07.
 */

/**
 * Agent class is the main component that is simulated in the simulation.
 * Each client has control over it's own agent.
 */
public class Agent implements Serializable {

    public double xPos; //this is used by the GUI.
    public double yPos; //this is used by the GUI.
    public double xVelocity;
    public double yVelocity;

    public double neighborRadius;

    double angle; //this is used by the GUI.

    public List<SimpleAgent> neighbors; //list of all the neighbors

    public Agent(double xPos, double yPos, double xVelocity, double yVelocity, double neighborRadius) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        this.neighborRadius = neighborRadius;
        this.neighbors = new ArrayList<>();
    }

    /**
     * getSimplification will return a SimpleAgent with the necessary information to be sent to the client.
     * @return SimpleAgent of this agent.
     */
    SimpleAgent getSimplification()
    {
        return new SimpleAgent(xPos,yPos,xVelocity,yVelocity);
    }
}
