package ui;/**
 * Created by Tobias on 2016-12-06.
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.awt.event.MouseEvent;

public class ServerUI extends Application {


    public static void startUI(String[] args)
    {
        launch(args);
    }

    private static Group root;
    public static int mouseX = 0;
    public static int mouseY = 0;

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

    public static void drawCircle(double x, double y,double radius)
    {
        Platform.runLater(()->{
            if(root != null)
            {
                Circle c = new Circle(x,y,radius,new Color(0,0,1,0.1));

                root.getChildren().add(c);
            }
        });

    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Server simulation");
        root = new Group();

        root.setOnMouseMoved(event -> {
            System.out.println("Mouse moved");
            mouseX = (int) event.getX();
            mouseY = (int) event.getY();
        });

        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
    }
}
