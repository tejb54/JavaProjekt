package ui;/**
 * Created by Tobias on 2016-12-06.
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class ServerUI extends Application {


    public static void startUI(String[] args)
    {
        launch(args);
    }

    private static Group root;

    public static void clearScreen()
    {
        Platform.runLater(()->{
            root.getChildren().clear();
        });

    }

    public static void drawCircle(double x, double y)
    {
        Platform.runLater(()->{
            Circle circle = new Circle(x,y,5, Color.RED);
            root.getChildren().add(circle);
        });

    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Server simulation");
        root = new Group();

        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
}
