package org.acgprojeto.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene cenaPrincipal;


    @Override
    public void start(Stage stage) {


        try {
            Parent login = FXMLLoader.load(getClass().getResource("/org/acgprojeto/view/Login.fxml"));
            cenaPrincipal = new Scene(login);
            stage.setScene(cenaPrincipal);
            stage.setTitle("Cyber Tigre Inforcell");
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();

            if(1 == 1){
                Parent cadastroAdmin = FXMLLoader.load(getClass().getResource("/org/acgprojeto/view/CadastroAdmin.fxml"));
                Stage palco = new Stage();
                Scene scene = new Scene(cadastroAdmin);
                palco.setScene(scene);
                palco.setTitle("Cyber Tigre Inforcell");
                palco.setResizable(true);
                palco.centerOnScreen();
                palco.show();

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Scene getMainScene() {
        return cenaPrincipal;
    }

    public static void main(String[] args) {
        launch();
    }
}