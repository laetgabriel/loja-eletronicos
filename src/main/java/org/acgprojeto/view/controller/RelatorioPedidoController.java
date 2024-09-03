package org.acgprojeto.view.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.acgprojeto.controller.PedidoController;
import org.acgprojeto.dto.TabelaPedidoDTO;
import org.acgprojeto.model.enums.Estado;
import org.acgprojeto.model.enums.Tipo;
import org.acgprojeto.util.AtualizarVisaoTabelas;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RelatorioPedidoController implements Initializable {
    private PedidoController pedidoController;

    @FXML
    private Button btnGerarRelatorio;

    @FXML
    private TableView<TabelaPedidoDTO> tableRelPedido;

    @FXML
    private TableColumn<TabelaPedidoDTO, String> colIdPedido;

    @FXML
    private TableColumn<TabelaPedidoDTO, String> colNomeCliente;

    @FXML
    private TableColumn<TabelaPedidoDTO, String> colNomeProduto;

    @FXML
    private TableColumn<TabelaPedidoDTO, BigDecimal> colPrecoPedidoProduto;

    @FXML
    private TableColumn<TabelaPedidoDTO, Integer> colQuantidadePedidoProduto;

    @FXML
    private TableColumn<TabelaPedidoDTO, Estado> colEstado;

    @FXML
    private TableColumn<TabelaPedidoDTO, Tipo> colTipo;

    @FXML
    private TableColumn<TabelaPedidoDTO, BigDecimal> colTotalServico;

    @FXML
    private TableColumn<TabelaPedidoDTO, LocalDate> colData;

    private ObservableList<TabelaPedidoDTO> pedidos;

    private ObservableList<TabelaPedidoDTO> pedidosFiltro;

    @FXML
    private ComboBox<String> comboBoxFiltro;

    private ObservableList<String> listaOpcoes;

    @FXML
    public void onBtnGerarRelatorio() {
        Stage stage = (Stage) btnGerarRelatorio.getScene().getWindow();
        pedidoController.gerarRelatorioPedido(stage, pedidosFiltro);
    }

    @FXML
    public void onComboBoxFiltroChanged() {
        String filtroSelecionado = comboBoxFiltro.getValue();
        tabelaFiltrada(filtroSelecionado);
    }


    public void atualizarTabelaRelPedidos(){
        List<TabelaPedidoDTO> listaPedidos = pedidoController.buscarPedidosParaTabelaRelPedidos();
        pedidos = FXCollections.observableList(listaPedidos);
        pedidosFiltro = FXCollections.observableList(listaPedidos);
        tableRelPedido.setItems(pedidos);

    }

    private void tabelaFiltrada(String filtro) {
        pedidosFiltro = AtualizarVisaoTabelas.tabelaFiltradaPedido(filtro, pedidos, tableRelPedido);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pedidoController = new org.acgprojeto.controller.PedidoController();

        List<String> opcoes = new ArrayList<>();
        opcoes.add("Hoje");
        opcoes.add("Semanal");
        opcoes.add("Mês");
        opcoes.add("Todos");
        listaOpcoes = FXCollections.observableList(opcoes);
        comboBoxFiltro.setItems(listaOpcoes);

        colIdPedido.setCellValueFactory(new PropertyValueFactory<>("idPedido"));
        colNomeCliente.setCellValueFactory(new PropertyValueFactory<>("NomeCliente"));
        colNomeProduto.setCellValueFactory(new PropertyValueFactory<>("NomeProduto"));
        colPrecoPedidoProduto.setCellValueFactory(new PropertyValueFactory<>("PrecoPedidoProduto"));
        colQuantidadePedidoProduto.setCellValueFactory(new PropertyValueFactory<>("QuantidadePedidoProduto"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("Estado"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("TipoServico"));
        colTotalServico.setCellValueFactory(new PropertyValueFactory<>("ValorServico"));
        colData.setCellValueFactory(new PropertyValueFactory<>("Data"));

        atualizarTabelaRelPedidos();

    }

    public void setPedidos(ObservableList<TabelaPedidoDTO> pedidos){
        this.pedidos = pedidos;
        tableRelPedido.setItems(pedidos);
    }
}
