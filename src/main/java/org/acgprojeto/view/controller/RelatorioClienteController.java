package org.acgprojeto.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.acgprojeto.controller.ClienteController;

public class RelatorioClienteController {

    @FXML
    private Button btnRelatorioCliente;

    private final ClienteController clienteController;

    public RelatorioClienteController(){
        clienteController = new ClienteController();
    }

    @FXML
    private void onGerarRelatorioCliente(){
        Stage stage = (Stage) btnRelatorioCliente.getScene().getWindow();
        clienteController.gerarRelatorioCliente(stage);
    }
}