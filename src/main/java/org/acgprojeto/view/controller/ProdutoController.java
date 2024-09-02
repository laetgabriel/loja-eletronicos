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
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.acgprojeto.dto.ProdutoDTO;
import org.acgprojeto.model.enums.Categoria;
import org.acgprojeto.util.Alertas;
import org.acgprojeto.util.AtualizarVisaoTabelas;
import org.acgprojeto.view.App;
import org.acgprojeto.view.observer.ProdutoObserver;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProdutoController implements Initializable, ProdutoObserver {

    private org.acgprojeto.controller.ProdutoController controller;

    @FXML
    private Button btnNovo;

    @FXML
    private TableView<ProdutoDTO> tableProduto;

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
    public void onBtnNovo( ) {
        loadCadastroView("/org/acgprojeto/view/CadastroProduto.fxml");
    }
    
    private void loadCadastroView(String caminho){
        Stage telaBase = App.getMainStage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
            Parent novaTela = loader.load();

            CadastroProdutoController cadastroProdutoController = loader.getController();
            cadastroProdutoController.adicionarObserver(this);

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
            Alertas.mostrarAlerta("Erro", "Erro ao carregar tela de cadastro produto", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onComboBoxFiltroChanged() {
        String filtroSelecionado = comboBoxFiltro.getValue();
        tabelaFiltrada(filtroSelecionado);
    }

    public void atualizarTabelaProduto(){
        List<ProdutoDTO> listaProdutos = controller.listarTodosOsProdutos();
        produtos = FXCollections.observableList(listaProdutos);
        tableProduto.setItems(produtos);

    }

    private void tabelaFiltrada(String filtro) {
        AtualizarVisaoTabelas.tabelaFiltradaProduto(filtro, produtos, tableProduto);
    }

    @Override
    public void atualizarProdutos() {
        atualizarTabelaProduto();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new org.acgprojeto.controller.ProdutoController();
        List<String> opcoes = controller.listarTodasAsCategorias();
        opcoes.add("SEM CATEGORIA");

        listaOpcoes = FXCollections.observableList(opcoes);
        comboBoxFiltro.setItems(listaOpcoes);

        colIdProduto.setCellValueFactory(new PropertyValueFactory<>("idProduto"));
        colNomeProduto.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colQuantidadeEstoque.setCellValueFactory(new PropertyValueFactory<>("quantidadeEstoque"));

        atualizarTabelaProduto();
    }

}
