package org.acgprojeto.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) {

        try {


/*
            Scene scene = new Scene(scrollPane);
            stage.setScene(scene);
            stage.setTitle("Cyber Tigre Inforcell");
            stage.setResizable(true);
            stage.show();
*/

            Parent p = FXMLLoader.load(getClass().getResource("/org/acgprojeto/view/CadastroAdmin.fxml"));
            stage.setScene(new Scene(p));
            stage.setTitle("Cyber Tigre Inforcell");
            stage.setResizable(true);
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