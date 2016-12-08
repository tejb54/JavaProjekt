package server;

import java.util.List;

/**
 * Created by Tobias on 2016-12-07.
 */
public class Agent {

    double xPos; //this is used by the GUI.
    double yPos; //this is used by the GUI.

    double angle; //angle for the agent.

    List<Agent> neighbors; //list off all the neighbors

    public Agent(double xPos, double yPos, double angle)
    {
        this.xPos = xPos;
        this.yPos = yPos;
        this.angle = angle;
    }
}
