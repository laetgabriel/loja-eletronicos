package org.acgprojeto.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) {

        try {

            Parent parent = FXMLLoader.load(getClass().getResource("/org/acgprojeto/view/Main.fxml"));
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.setTitle("App Cyber Tigre");
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch();
    }
}
