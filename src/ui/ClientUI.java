package ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import server.SimpleAgent;
import shared.ServerResponse;

/**
 * Created by Tobias on 2016-12-13.
 */


/**
 * ClientUI handles the ui for the client.
 */
public class ClientUI extends Application{


    /**
     * startClientUI will start the javafx ui in its own thread.
     * @param args array of strings.
     */
    public static void startClientUI(String[] args){
        launch(args);
    }

    private static Group root;


    public static void clearScreen()
    {
        try
        {
            Platform.runLater(()->{
                if(root != null)
                {
                    root.getChildren().clear();
                }
            });
        }
        catch (Exception ex)
        {
            //System.out.println("Platform not ready");
        }


    }

    public static void drawClient(double x, double y,double angle)
    {
        Platform.runLater(()->{
            if(root != null)
            {
                root.getChildren().add(drawArrow(x,y,angle,2));

                Circle c = new Circle(x,y,100*2, new Color(0,0,1,0.1));
                root.getChildren().add(c);
            }
        });
    }

    private static Polygon drawArrow(double x, double y, double angle,float scaleFactor){
        Polygon polygon = new Polygon();
        polygon.getPoints().addAll(
                x+12*scaleFactor, y,
                x-7*scaleFactor, y+7*scaleFactor,
                x-3*scaleFactor, y,
                x-7*scaleFactor, y-7*scaleFactor);
        polygon.setRotate(angle);
        polygon.setFill(Color.RED);
        return polygon;
    }

    public static void drawNeighbors(ServerResponse response,float scaleFactor)
    {
        Platform.runLater(()->{
            if(root != null)
            {
                for (SimpleAgent s: response.neighbors)
                {
                    root.getChildren().add(drawArrow(250 +(s.xPos - response.xPos)*scaleFactor,250 +(s.yPos - response.yPos)*scaleFactor,Math.toDegrees(Math.atan2(s.yVelocity,s.xVelocity)),scaleFactor));
                }
            }
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Client view");
        root = new Group();

        primaryStage.setOnCloseRequest(event -> {
            System.exit(0); //exit the client
        });


        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
    }
}
