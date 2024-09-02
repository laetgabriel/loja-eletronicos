package org.acgprojeto.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.acgprojeto.controller.AdminController;
import org.acgprojeto.dto.AdminDTO;
import org.acgprojeto.dto.ProdutoDTO;
import org.acgprojeto.model.exceptions.ValidacaoCadastrosException;
import org.acgprojeto.util.Alertas;
import org.acgprojeto.util.Restricoes;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Pattern;

public class CadastroAdminController implements Initializable {

    AdminController adminController;
    AdminDTO adminDTO;

    @FXML
    private TextField txtUsuario;
    @FXML
    private Label lblErroUsuario;
    @FXML
    private TextField txtEmail;
    @FXML
    private Label lblErroEmail;
    @FXML
    private TextField txtConfirmacaoEmail;
    @FXML
    private Label lblErroConfirmacaoEmail;
    @FXML
    private PasswordField txtSenha;
    @FXML
    private Label lblErroSenha;
    @FXML
    private PasswordField txtConfirmacaoSenha;
    @FXML
    private Label lblErroConfirmacaoSenha;
    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnCancelar;

    @FXML
    public void onBtnSalvar() {
        limparErros();
        try {
            AdminDTO adminDeInsercao = getDadosCampos();

            if (adminDTO == null) {
                adminController.inserirAdmin(adminDeInsercao);
            } else {
                adminDTO.setNome(adminDeInsercao.getNome());
                adminDTO.setEmail(adminDeInsercao.getEmail());
                adminDTO.setSenha(adminDeInsercao.getSenha());
                System.out.println(adminDTO.getId());
                adminController.atualizarAdmin(adminDTO);
            }

            Stage stage = (Stage) btnSalvar.getScene().getWindow();
            stage.close();
        } catch (ValidacaoCadastrosException e) {
            setLblErros(e.getErrors());
        } catch (Exception e) {
            Alertas.mostrarAlerta("Erro", "Erro ao salvar/editar administrador!", Alert.AlertType.ERROR);
            e.printStackTrace(); // Exibe o stack trace no console para depuração
        }
    }

    public void editarAdmin(AdminDTO adminDTO) {
        txtUsuario.setText(adminDTO.getNome());
        txtEmail.setText(adminDTO.getEmail());
        txtSenha.setText(adminDTO.getSenha());
    }

    @FXML
    public void onBtnCancelar(){
        Stage tela = (Stage) btnCancelar.getScene().getWindow();
        tela.close();
    }

    private AdminDTO getDadosCampos() throws ValidacaoCadastrosException {
        ValidacaoCadastrosException validacaoCampos = new ValidacaoCadastrosException();

        String usuario = txtUsuario.getText();
        String email = txtEmail.getText();
        String confirmacaoEmail = txtConfirmacaoEmail.getText();
        String senha = txtSenha.getText();
        String confirmacaoSenha = txtConfirmacaoSenha.getText();

        if (usuario == null || usuario.trim().isEmpty()) {
            validacaoCampos.addErro("usuario", "Usuário não pode ser vazio!");
        }

        if (email == null || email.trim().isEmpty()) {
            validacaoCampos.addErro("email", "Email não pode ser vazio!");
        } else if (!Restricoes.validarEmail(email)) {
            validacaoCampos.addErro("email", "Email inválido!");
        }

        if (confirmacaoEmail == null || confirmacaoEmail.trim().isEmpty()) {
            validacaoCampos.addErro("confirmacaoEmail", "Confirmação de email não pode ser vazio!");
        } else if (!email.equals(confirmacaoEmail)) {
            validacaoCampos.addErro("confirmacaoEmail", "Emails não coincidem!");
        }

        if (senha == null || senha.trim().isEmpty()) {
            validacaoCampos.addErro("senha", "Senha não pode ser vazia!");
        } else if (senha.length() < 8) {
            validacaoCampos.addErro("senha", "Senha deve ter pelo menos 8 caracteres!");
        }

        if (confirmacaoSenha == null || confirmacaoSenha.trim().isEmpty()) {
            validacaoCampos.addErro("confirmacaoSenha", "Confirmação de senha não pode ser vazia!");
        } else if (!senha.equals(confirmacaoSenha)) {
            validacaoCampos.addErro("confirmacaoSenha", "Senhas não coincidem!");
        }

        if (validacaoCampos.getErrors().size() > 0) {
            throw validacaoCampos;
        }

        return new AdminDTO(null, usuario, email, senha);
    }


    private void setLblErros(Map<String, String> erros) {
        Set<String> campos = erros.keySet();

        if (campos.contains("usuario")) {
            lblErroUsuario.setText(erros.get("usuario"));
        }
        if (campos.contains("email")) {
            lblErroEmail.setText(erros.get("email"));
        }
        if (campos.contains("confirmacaoEmail")) {
            lblErroConfirmacaoEmail.setText(erros.get("confirmacaoEmail"));
        }
        if (campos.contains("senha")) {
            lblErroSenha.setText(erros.get("senha"));
        }
        if (campos.contains("confirmacaoSenha")) {
            lblErroConfirmacaoSenha.setText(erros.get("confirmacaoSenha"));
        }
    }

    private void limparErros() {
        lblErroUsuario.setText("");
        lblErroEmail.setText("");
        lblErroConfirmacaoEmail.setText("");
        lblErroSenha.setText("");
        lblErroConfirmacaoSenha.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        adminController = new AdminController();
        adminDTO = adminController.buscarAdminPorId(1);

        if (adminDTO != null) {
            editarAdmin(adminDTO);
        }

        Restricoes.setTextFieldMaxLength(txtUsuario, 100);
        Restricoes.setTextFieldMaxLength(txtEmail, 100);
        Restricoes.setTextFieldMaxLength(txtConfirmacaoEmail, 100);
        Restricoes.setTextFieldMaxLength(txtSenha, 100);
        Restricoes.setTextFieldMaxLength(txtConfirmacaoSenha, 100);
    }
}
