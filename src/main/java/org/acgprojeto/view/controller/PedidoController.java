package org.acgprojeto.view.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.acgprojeto.view.App;
import org.acgprojeto.view.util.Alertas;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PedidoController implements Initializable {

    @FXML
    private MenuItem menuItemPedido;

    @FXML
    private MenuItem menuItemProduto;

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
    public void onMenuItemPedido() {
        loadView("/org/acgprojeto/view/Pedido.fxml");
    }

    @FXML
    public void onMenuItemProduto() {
        loadView("/org/acgprojeto/view/Produto.fxml");
    }

    @FXML
    public void onMenuItemRelCliente() {
        loadView("/org/acgprojeto/view/RelatorioCliente.fxml");
    }

    @FXML
    public void onMenuItemRelProduto() {
        loadView("/org/acgprojeto/view/RelatorioProduto.fxml");
    }

    @FXML
    public void onMenuItemRelPedido() {
        loadView("/org/acgprojeto/view/RelatorioPedido.fxml");
    }

    @FXML
    public void onMenuItemRelServico() {
        loadView("/org/acgprojeto/view/RelatorioServico.fxml");
    }

    @FXML
    public void onMenuItemLogin() {
        loadCadastroView("/org/acgprojeto/view/CadastroAdmin.fxml");
    }

    @FXML
    public void onMenuItemSobre() {
        loadView("/org/acgprojeto/view/Sobre.fxml");
    }

    @FXML
    public void onBtnNovo() {
        loadCadastroView("/org/acgprojeto/view/CadastroPedido.fxml");
    }


    private void loadView(String caminho) {
        try {
            Stage loginStage = App.getMainState();
            ScrollPane telaAtual = (ScrollPane) loginStage.getScene().getRoot();
            AnchorPane telaAtualContent = (AnchorPane) telaAtual.getContent();

            Node menuBar = telaAtualContent.getChildren().get(0);

            FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
            ScrollPane telaProduto = loader.load();
            AnchorPane telaProdutoContent = (AnchorPane) telaProduto.getContent();

            telaAtualContent.getChildren().clear();
            telaAtualContent.getChildren().add(menuBar);
            telaAtualContent.getChildren().addAll(telaProdutoContent.getChildren());

        } catch (IOException e) {
            Alertas.mostrarAlerta("Erro", null, "Erro ao carregar tela de pedido", Alert.AlertType.ERROR);
        }
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
            Alertas.mostrarAlerta("Erro", null, "Erro ao carregar tela de cadastro admin", Alert.AlertType.ERROR);
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
