package org.acgprojeto.view.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.acgprojeto.view.util.Alertas;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProdutoController implements Initializable {

    private org.acgprojeto.controller.ProdutoController controller;

    @FXML
    private Button btnNovo;

    @FXML
    private ComboBox<String> comboBoxFiltro;

    private ObservableList<String> listaOpcoes;

    @FXML
    public void onBtnNovo( ) {
        loadCadastroView("/org/acgprojeto/view/CadastroProduto.fxml");
    }
    
    private void loadCadastroView(String caminho){
        Parent novaTela = null;
        try {
            novaTela = FXMLLoader.load(getClass().getResource(caminho));
            Stage palco = new Stage();
            Scene scene = new Scene(novaTela);
            palco.setScene(scene);
            palco.setTitle("Cyber Tigre Inforcell");
            palco.setResizable(true);
            palco.centerOnScreen();
            palco.show();
        } catch (IOException e) {
            Alertas.mostrarAlerta("Erro", null, "Erro ao carregar tela de cadastro produto", Alert.AlertType.ERROR);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new org.acgprojeto.controller.ProdutoController();
        List<String> opcoes = controller.listarTodasAsCategorias();

        listaOpcoes = FXCollections.observableList(opcoes);
        comboBoxFiltro.setItems(listaOpcoes);
    }
}
