package org.acgprojeto.view.controller;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.acgprojeto.controller.AdminController;
import org.acgprojeto.controller.MensageiroController;
import org.acgprojeto.dto.AdminDTO;
import org.acgprojeto.dto.MensagemDTO;
import org.acgprojeto.application.App;
import org.acgprojeto.util.Alertas;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private AdminController adminController;
    private AdminDTO adminDTO;
    private MensageiroController mensageiro;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

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
        try{
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
            }
        }catch (Exception e){
            Alertas.mostrarAlerta("Cadastre Admin", "Cadastre um admin primeiro!", Alert.AlertType.ERROR);
        }

    }

    @FXML
    public void onBtnEsqueceuSenha() {
        Alert alertaProgresso = Alertas.retornaAlerta("ENVIANDO SENHA PARA O EMAIL", "Senha de login sendo enviada para o email!", Alert.AlertType.INFORMATION);
        alertaProgresso.show();

        Task<Void> tarefaEnvioEmail = new Task<>() {
            @Override
            protected Void call() throws Exception {
                adminController = new AdminController();
                adminDTO = adminController.buscarAdminPorId(1);
                mensageiro = new MensageiroController();

                mensageiro.enviarEmail(new MensagemDTO("Senha de login", "Sua senha é: " + adminDTO.getSenha(), adminDTO.getEmail()));
                return null;
            }
        };

        tarefaEnvioEmail.setOnSucceeded(e -> {
            alertaProgresso.close();
            Alertas.mostrarAlerta("Envio de senha", "Senha de login enviada para o e-mail!", Alert.AlertType.INFORMATION);
        });

        tarefaEnvioEmail.setOnFailed(e -> {
            alertaProgresso.close();
            Alertas.mostrarAlerta("Erro no envio de senha", "Ocorreu um erro ao enviar a senha de login!", Alert.AlertType.ERROR);
        });

        new Thread(tarefaEnvioEmail).start();
    }

    private void loadView(String caminho) {
        try {
            Stage loginStage = App.getMainStage();

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
            Alertas.mostrarAlerta("Erro", "Erro ao carregar tela de pedido", Alert.AlertType.ERROR);

        }
    }

    private void onPressionaKeyEnter(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            onBtnLogin();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtPassword.setOnKeyPressed(this::onPressionaKeyEnter);
        txtUsername.setOnKeyPressed(this::onPressionaKeyEnter);
    }
}
