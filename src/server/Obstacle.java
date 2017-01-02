package server;

import java.io.Serializable;

/**
 * Created by Tobias on 2016-12-20.
 */

/**
 * <p>Obstacle class has the necessary data fo an obstacle</p>
 */
public class Obstacle implements Serializable{
    public double xPos;
    public double yPos;

    public double radius;

    public Obstacle(double xPos, double yPos, double radius) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.radius = radius;
    }
}
