package server;

import shared.MyPair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tobias on 2016-12-17.
 */


public class UniformMap {
    int width;
    int height;
    int cellSize;

    private ArrayList<ArrayList<ArrayList<Agent>>> buckets;

    public UniformMap(int width, int height, int cellSize) {
        this.width = width;
        this.height = height;
        this.cellSize = cellSize;

        buckets = new ArrayList<ArrayList<ArrayList<Agent>>>();
    }

    public void addObject(Agent a)
    {
        int[] leftUpperCorner = getObjectHash((int)a.xPos + (int)a.neighborRadius,(int)a.yPos + (int)a.neighborRadius);
        if(!buckets.get(leftUpperCorner[0]).get(leftUpperCorner[1]).contains(a))
        {
            buckets.get(leftUpperCorner[0]).get(leftUpperCorner[1]).add(a);
        }

        int[] rightUpperCorner = getObjectHash((int)a.xPos - (int)a.neighborRadius,(int)a.yPos + (int)a.neighborRadius);
        if(!buckets.get(rightUpperCorner[0]).get(rightUpperCorner[1]).contains(a))
        {
            buckets.get(rightUpperCorner[0]).get(rightUpperCorner[1]).add(a);
        }
    }

    public List<Agent> getObjects(Agent a)
    {
        int[] array = getObjectHash((int)a.xPos,(int) a.yPos);
        return buckets.get(array[0]).get(array[1]);
    }

    private int[] getObjectHash(int xPos, int yPos)
    {
        int x = xPos/cellSize;
        int y = yPos/cellSize;

        return new int[]{x,y};
    }
}
