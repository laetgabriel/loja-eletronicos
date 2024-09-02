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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.acgprojeto.dto.PedidoDTO;
import org.acgprojeto.dto.TabelaPedidoDTO;
import org.acgprojeto.model.enums.Estado;
import org.acgprojeto.model.enums.Tipo;
import org.acgprojeto.view.App;
import org.acgprojeto.util.Alertas;
import org.acgprojeto.util.AtualizarVisaoTabelas;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PedidoController implements Initializable {

    private org.acgprojeto.controller.PedidoController controller;

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
    private TableView<TabelaPedidoDTO> tableViewPedido;

    @FXML
    private TableColumn<PedidoDTO, String> colNomeCliente;

    @FXML
    private TableColumn<PedidoDTO, String> colDescricao;

    @FXML
    private TableColumn<PedidoDTO, BigDecimal> colValor;

    @FXML
    private TableColumn<PedidoDTO, Tipo> colTipo;

    @FXML
    private TableColumn<PedidoDTO, Estado> colEstado;

    @FXML
    private TableColumn<PedidoDTO, LocalDate> colData;

    private ObservableList<TabelaPedidoDTO> pedidos;

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

    @FXML
    public void onComboBoxFiltroChanged() {
        String filtroSelecionado = comboBoxFiltro.getValue();
        tabelaFiltrada(filtroSelecionado);
    }


    public void atualizarTabelaPedidos(){
        List<TabelaPedidoDTO> listaPedidos = controller.buscarPedidosParaTabelaPedidos();
        pedidos = FXCollections.observableList(listaPedidos);
        tableViewPedido.setItems(pedidos);

    }

    private void tabelaFiltrada(String filtro) {
        AtualizarVisaoTabelas.tabelaFiltradaPedido(filtro, pedidos, tableViewPedido);
    }


    private void loadView(String caminho) {
        try {
            Stage loginStage = App.getMainStage();
            ScrollPane telaAtual = (ScrollPane) loginStage.getScene().getRoot();
            AnchorPane telaAtualContent = (AnchorPane) telaAtual.getContent();

            Node menuBar = telaAtualContent.getChildren().get(0);

            FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
            ScrollPane telaNova = loader.load();
            AnchorPane telaNovaContent = (AnchorPane) telaNova.getContent();

            telaAtualContent.getChildren().clear();
            telaAtualContent.getChildren().add(menuBar);
            telaAtualContent.getChildren().addAll(telaNovaContent.getChildren());

        } catch (IOException e) {
            Alertas.mostrarAlerta("Erro", "Erro ao carregar tela de pedido", Alert.AlertType.ERROR);
        }
    }

    private void loadCadastroView(String caminho){
        Parent novaTela = null;
        Stage telaBase = App.getMainStage();
        try {
            novaTela = FXMLLoader.load(getClass().getResource(caminho));
            Stage palco = new Stage();
            Scene scene = new Scene(novaTela);
            palco.setScene(scene);
            palco.setTitle("Cyber Tigre Inforcell");
            palco.setResizable(false);
            palco.centerOnScreen();
            palco.initOwner(telaBase);
            palco.initModality(Modality.WINDOW_MODAL);
            palco.showAndWait();

        } catch (IOException e) {
            Alertas.mostrarAlerta("Erro", "Erro ao carregar tela de cadastro", Alert.AlertType.ERROR);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new org.acgprojeto.controller.PedidoController();

        List<String> opcoes = new ArrayList<>();
        opcoes.add("Hoje");
        opcoes.add("Semanal");
        opcoes.add("Mês");
        opcoes.add("Todos");
        listaOpcoes = FXCollections.observableList(opcoes);
        comboBoxFiltro.setItems(listaOpcoes);

        colNomeCliente.setCellValueFactory(new PropertyValueFactory<>("NomeCliente"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("DescricaoServico"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("ValorServico"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("TipoServico"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("Estado"));
        colData.setCellValueFactory(new PropertyValueFactory<>("Data"));

        atualizarTabelaPedidos();

    }
}
