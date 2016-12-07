package ui;/**
 * Created by Tobias on 2016-12-06.
 */

import javafx.application.Application;
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

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World!");

        Circle circle = new Circle();
        circle.setCenterX(100.0f);
        circle.setCenterY(100.0f);
        circle.setRadius(50.0f);

        StackPane root = new StackPane();
        root.getChildren().add(circle);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
}
