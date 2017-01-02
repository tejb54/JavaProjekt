package ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.scene.Scene;
import server.MainServer;

/**
 * Created by Tobias Gillström on 2016-12-06.
 */

public class ServerUI extends Application {

    public static void startUI(String[] args)
    {
        launch(args);
    }

    private static Group root;
    public static int width = 1000;
    public static int height = 1000;

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
            System.out.println("Platform not ready");
        }


    }

    public static void drawTriangle(double x, double y,double angle)
    {
        Platform.runLater(()->{
            if(root != null)
            {
                Polygon polygon = new Polygon();
                polygon.getPoints().addAll(
                        x+12, y,
                        x-7, y+7,
                        x-3, y,
                        x-7, y-7);
                polygon.setRotate(angle);
                polygon.setFill(Color.RED);

                root.getChildren().add(polygon);
            }
        });

    }

    public static void drawCircle(double x, double y,double radius, Color color)
    {
        Platform.runLater(()->{
            if(root != null)
            {
                Circle c = new Circle(x,y,radius,color);

                root.getChildren().add(c);
            }
        });

    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Server simulation");
        root = new Group();

        primaryStage.setOnCloseRequest(event -> {
            System.exit(0);
        });

        Scene s = new Scene(root, width, height);

        s.setOnMousePressed((event)->{
            System.out.println("Mouse clicked");
            MainServer.mainSimulation.addObstacle(event.getX(),event.getY());
        });



        primaryStage.setScene(s);
        primaryStage.show();
    }
}
