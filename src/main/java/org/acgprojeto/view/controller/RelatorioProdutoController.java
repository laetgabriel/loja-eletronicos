package org.acgprojeto.view.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.acgprojeto.dto.ProdutoDTO;
import org.acgprojeto.model.enums.Categoria;
import org.acgprojeto.view.util.Alertas;
import org.acgprojeto.view.util.AtualizarVisaoTabelas;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RelatorioProdutoController implements Initializable {

    private org.acgprojeto.controller.ProdutoController controller;

    @FXML
    private Button btnGerarRelatorio;

    @FXML
    private TableView<ProdutoDTO> tableRelProduto;

    @FXML
    private TableColumn<ProdutoDTO, Integer> colIdProduto;

    @FXML
    private TableColumn<ProdutoDTO, String> colNomeProduto;

    @FXML
    private TableColumn<ProdutoDTO, Categoria> colCategoria;

    @FXML
    private TableColumn<ProdutoDTO, BigDecimal> colPreco;

    @FXML
    private TableColumn<ProdutoDTO, Integer> colQuantidadeEstoque;

    private ObservableList<ProdutoDTO> produtos;

    @FXML
    private ComboBox<String> comboBoxFiltro;

    private ObservableList<String> listaOpcoes;

    @FXML
    public void onBtnGerarRelatorioOnAction( ) {
    }

    @FXML
    public void onComboBoxFiltroChanged() {
        String filtroSelecionado = comboBoxFiltro.getValue();
        tabelaFiltrada(filtroSelecionado);
    }


    public void atualizarTabelaRelProduto() {
        List<ProdutoDTO> listaProdutos = controller.listarTodosOsProdutos();
        produtos = FXCollections.observableList(listaProdutos);
        tableRelProduto.setItems(produtos);

    }

    private void tabelaFiltrada(String filtro) {
        AtualizarVisaoTabelas.tabelaFiltradaProduto(filtro, produtos, tableRelProduto);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new org.acgprojeto.controller.ProdutoController();
        List<String> opcoes = controller.listarTodasAsCategorias();
        opcoes.add("SEM FILTRO");

        listaOpcoes = FXCollections.observableList(opcoes);
        comboBoxFiltro.setItems(listaOpcoes);

        colIdProduto.setCellValueFactory(new PropertyValueFactory<>("idProduto"));
        colNomeProduto.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colQuantidadeEstoque.setCellValueFactory(new PropertyValueFactory<>("quantidadeEstoque"));

        atualizarTabelaRelProduto();
    }
}
