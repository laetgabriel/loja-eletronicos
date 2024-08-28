package org.acgprojeto.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.acgprojeto.view.App;
import org.acgprojeto.view.util.Alertas;

import javax.swing.plaf.nimbus.State;
import java.io.IOException;

public class LoginController {

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnEsqueceuSenha;

    @FXML
    public void onBtnLogin() {
        loadView("/org/acgprojeto/view/Pedido.fxml");
    }

    @FXML
    public void onBtnEsqueceuSenha() {
        Alertas.mostrarAlerta("Envio senha", null, "Senha de login enviada para o email!", Alert.AlertType.INFORMATION);

    }

    private void loadView(String caminho) {
        try {
            Stage loginStage = App.getMainState();

            FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
            ScrollPane telaPedido = loader.load();

            telaPedido.setFitToHeight(true);
            telaPedido.setFitToWidth(true);

            Scene cenaPrincipal = new Scene(telaPedido);
            loginStage.setScene(cenaPrincipal);
            loginStage.setTitle("Cyber Tigre Inforcell");
            loginStage.setResizable(true);
            loginStage.centerOnScreen();

        } catch (IOException e) {
            Alertas.mostrarAlerta("Erro", null, "Erro ao carregar tela de pedido", Alert.AlertType.ERROR);
        }
    }
}
