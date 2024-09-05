package org.acgprojeto.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.acgprojeto.controller.AdminController;
import org.acgprojeto.dto.AdminDTO;
import org.acgprojeto.util.Alertas;

import java.io.IOException;

public class App extends Application {

    private static Stage stage;
    private AdminController adminController;
    private AdminDTO adminDTO;
    @Override
    public void start(Stage stage) {
        try {
            adminController = new AdminController();
            adminDTO = adminController.buscarAdminPorId(1);

            this.stage = stage;

            Parent login = FXMLLoader.load(getClass().getResource("/org/acgprojeto/view/login.fxml"));
            Scene cenaLogin = new Scene(login);
            stage.setScene(cenaLogin);
            stage.setTitle("Cyber Tigre Inforcell");
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();

            if(adminDTO == null) {
                Stage telaBase = App.getMainStage();
                Parent cadastroAdmin = FXMLLoader.load(getClass().getResource("/org/acgprojeto/view/CadastroAdmin.fxml"));
                Stage palco = new Stage();
                Scene scene = new Scene(cadastroAdmin);
                palco.setScene(scene);
                palco.setTitle("Cyber Tigre Inforcell");
                palco.setResizable(true);
                palco.initOwner(telaBase);
                palco.initModality(Modality.WINDOW_MODAL);
                palco.centerOnScreen();
                palco.showAndWait();
            }
        }
        catch (Exception e) {
            Alertas.mostrarAlerta("Cadastre Admin", "Cadastre uma admin primeiro!", Alert.AlertType.ERROR);
        }

    }

    public static Stage getMainStage() {
        return stage;
    }

    public static void main(String[] args) {
        launch();
    }
}