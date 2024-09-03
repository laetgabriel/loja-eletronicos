package org.acgprojeto.view.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.acgprojeto.db.exceptions.DBException;
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
import java.util.Optional;
import java.util.ResourceBundle;

public class ProdutoController implements Initializable, ProdutoObserver {

    private org.acgprojeto.controller.ProdutoController controller;
    public static ProdutoDTO produtoSelecionado;

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

    @FXML
    private TableColumn<ProdutoDTO, ProdutoDTO> colAtualizar;

    @FXML
    private TableColumn<ProdutoDTO, ProdutoDTO> colExcluir;

    private ObservableList<ProdutoDTO> produtos;

    @FXML
    private ComboBox<String> comboBoxFiltro;

    private ObservableList<String> listaOpcoes;


    @FXML
    public void onBtnNovo() {
        loadCadastroView("/org/acgprojeto/view/CadastroProduto.fxml");
    }

    private void loadCadastroView(String caminho){
        Stage telaBase = App.getMainStage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
            Parent novaTela = loader.load();

            CadastroProdutoController cadastroProdutoController = loader.getController();
            cadastroProdutoController.adicionarObserver(this);

            cadastroProdutoController.setProdutoDTO(getProdutoSelecionado());

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
        iniciarBotoesDeAtualizar();
        iniciarBotoesDeRemover();
    }

    private void tabelaFiltrada(String filtro) {
        AtualizarVisaoTabelas.tabelaFiltradaProduto(filtro, produtos, tableProduto);
    }

    @Override
    public void atualizarProdutos() {
        atualizarTabelaProduto();
    }

    public void removerProduto(ProdutoDTO produto){
        Optional<ButtonType> escolha = Alertas.showConfirmation("Confirmação", "Tem certeza que quer deletar esse " +
                "produto?");

        if(escolha.get() == ButtonType.OK){
            try{
                controller.excluirProduto(produto.getIdProduto());
                atualizarProdutos();
            }catch (DBException e){
                Alertas.mostrarAlerta("Erro", "Esse produto contem pedidos", Alert.AlertType.ERROR);
            }
        }

    }

    private void iniciarBotoesDeAtualizar() {
        colAtualizar.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        colAtualizar.setCellFactory(param -> new TableCell<ProdutoDTO, ProdutoDTO>() {

            private final Button button = new Button("Atualizar");

            @Override
            protected void updateItem(ProdutoDTO obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null || empty) {
                    setGraphic(null);
                    return;
                }

                setGraphic(button);
                button.setOnAction(event -> {

                    ProdutoController.produtoSelecionado = obj;
                    onBtnNovo();
                });
            }
        });
    }

    private void iniciarBotoesDeRemover() {
        colExcluir.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        colExcluir.setCellFactory(param -> new TableCell<ProdutoDTO, ProdutoDTO>() {
            private final Button button = new Button("Remover");
            protected void updateItem(ProdutoDTO obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event -> removerProduto(obj));
            }
        });
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

    public ProdutoDTO getProdutoSelecionado() {
        return produtoSelecionado;
    }

}
