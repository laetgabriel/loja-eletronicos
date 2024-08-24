package org.acgprojeto.view.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.acgprojeto.view.App;
import org.acgprojeto.view.util.Alertas;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PedidoController implements Initializable {
    @FXML
    private MenuItem menuItemCadProduto;

    @FXML
    private MenuItem menuItemRelCliente;

    @FXML
    private MenuItem menuItemRelProduto;

    @FXML
    private MenuItem menuItemRelPedido;

    @FXML
    private MenuItem menuItemRelServico;

    @FXML
    private MenuItem menuItemLogin;

    @FXML
    private MenuItem menuItemSobre;

    @FXML
    private Button btnNovo;

    @FXML
    private ComboBox<String> comboBoxFiltro;

    private ObservableList<String> listaOpcoes;



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
            FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));

            ScrollPane proximaTela = loader.load();

            Scene cenaAtual = App.getMainScene();
            AnchorPane telaPrincipal = (AnchorPane) cenaAtual.getRoot();

            ScrollPane telaAtual = (ScrollPane) telaPrincipal.getParent();
            AnchorPane filhoTelaAtual = (AnchorPane) telaAtual.getContent();

            Node mainMenu = filhoTelaAtual.getChildren().get(0);

            telaPrincipal.getChildren().clear();
            telaPrincipal.getChildren().add(mainMenu);
            telaPrincipal.getChildren().add(proximaTela);

        } catch (IOException e) {
            Alertas.mostrarAlerta("Erro", null, "Erro ao carregar tela de pedido", Alert.AlertType.ERROR);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> opcoes = new ArrayList<>();
        opcoes.add("Hoje");
        opcoes.add("Semanal");
        opcoes.add("Todos");
        listaOpcoes = FXCollections.observableList(opcoes);
        comboBoxFiltro.setItems(listaOpcoes);
    }
}
