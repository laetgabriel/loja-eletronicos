package org.acgprojeto.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.acgprojeto.controller.AdminController;
import org.acgprojeto.controller.MensageiroController;
import org.acgprojeto.dto.AdminDTO;
import org.acgprojeto.view.App;
import org.acgprojeto.view.util.Alertas;
import org.apache.commons.mail.EmailException;

import java.io.IOException;

public class LoginController {
    private AdminController adminController;
    private AdminDTO adminDTO;
    private MensageiroController mensageiro;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    private Label txtErroUsername;

    @FXML
    private Label txtErroSenha;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnEsqueceuSenha;

    @FXML
    public void onBtnLogin() {
        /*
        adminController = new AdminController();
        adminDTO = adminController.buscarAdminPorId(1);

        if (!adminDTO.getNome().equals(txtUsername.getText())) {
            txtErroUsername.setText("Erro no nome do usuário!");
            txtErroSenha.setText(null);

        }else if (!adminDTO.getSenha().equals(txtPassword.getText())) {
            txtErroUsername.setText(null);
            txtErroSenha.setText("Erro na senha!");
        }else {
            loadView("/org/acgprojeto/view/Pedido.fxml");
        }*/
        loadView("/org/acgprojeto/view/Pedido.fxml");
    }

    @FXML
    public void onBtnEsqueceuSenha() {
        try {

            adminController = new AdminController();
            adminDTO = adminController.buscarAdminPorId(1);
            mensageiro = new MensageiroController();

            Alert alertaSenha = Alertas.retornaAlerta("ENVIANDO SENHA PARA O EMAIL!","Senha de login sendo enviada para o email!", Alert.AlertType.INFORMATION);
            alertaSenha.show();

            mensageiro.enviarEmail(adminDTO.getEmail(), "Senha de login", "Sua senha é: " + adminDTO.getSenha());

            alertaSenha.close();

            Alertas.mostrarAlerta("Envio senha", null, "Senha de login enviada para o email!", Alert.AlertType.INFORMATION);

        }
        catch (EmailException e){
            Alertas.mostrarAlerta("Erro envio de senha", null, "Erro no envio da senha de login!", Alert.AlertType.ERROR);
        }
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
