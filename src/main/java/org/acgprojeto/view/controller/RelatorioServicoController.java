package org.acgprojeto.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.acgprojeto.controller.ServicoController;

public class RelatorioServicoController {

    @FXML
    private Button btnRelatorioServico;

    private final ServicoController servicoController;

    public RelatorioServicoController(){
        servicoController = new ServicoController();
    }

    @FXML
    private void onGerarRelatorioServico(){
        Stage stage = (Stage) btnRelatorioServico.getScene().getWindow();
        servicoController.gerarRelatorioServico(stage);
    }
}