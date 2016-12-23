package server;

import java.io.Serializable;

/**
 * Created by Tobias on 2016-12-20.
 */
public class Obstacle implements Serializable{
    public double xPos; //this is used by the GUI.
    public double yPos; //this is used by the GUI.

    public double radius;

    public Obstacle(double xPos, double yPos, double radius) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.radius = radius;
    }
}
