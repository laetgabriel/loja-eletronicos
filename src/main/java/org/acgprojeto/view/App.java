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

            Parent parent = FXMLLoader.load(getClass().getResource("/org/acgprojeto/view/Login.fxml"));
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.setTitle("Cyber Tigre Inforcel");
            stage.setResizable(false);
            stage.show();

            Stage stage2 = new Stage();
            Parent parent2 = FXMLLoader.load(getClass().getResource("/org/acgprojeto/view/CadastroAdmin.fxml"));
            Scene scene2 = new Scene(parent2);
            stage2.setScene(scene2);
            stage2.setTitle("Cyber Tigre Inforcel");
            stage2.setResizable(false);
            stage2.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch();
    }
}