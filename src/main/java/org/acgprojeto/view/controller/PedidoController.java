package org.acgprojeto.view.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
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
import org.acgprojeto.db.exceptions.DBException;
import org.acgprojeto.dto.TabelaPedidoDTO;
import org.acgprojeto.model.entities.Pedido;
import org.acgprojeto.model.enums.Estado;
import org.acgprojeto.model.enums.Tipo;
import org.acgprojeto.view.App;
import org.acgprojeto.util.Alertas;
import org.acgprojeto.util.AtualizarVisaoTabelas;
import org.acgprojeto.view.observer.PedidoObserver;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class PedidoController implements Initializable, PedidoObserver {

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
    private ComboBox<String> comboBoxFiltroData;

    @FXML
    private ComboBox<String> comboBoxFiltroEstado;


    private ObservableList<String> listaOpcoes;

    @FXML
    private TableView<TabelaPedidoDTO> tableViewPedido;

    @FXML
    private TableColumn<TabelaPedidoDTO, String> colNomeCliente;

    @FXML
    private TableColumn<TabelaPedidoDTO, String> colDescricao;

    @FXML
    private TableColumn<TabelaPedidoDTO, BigDecimal> colValor;

    @FXML
    private TableColumn<TabelaPedidoDTO, Tipo> colTipo;

    @FXML
    private TableColumn<TabelaPedidoDTO, Estado> colEstado;

    @FXML
    private TableColumn<TabelaPedidoDTO, TabelaPedidoDTO> colMudarEstado;

    @FXML
    private TableColumn<TabelaPedidoDTO, TabelaPedidoDTO> colDetalhe;

    @FXML
    private TableColumn<TabelaPedidoDTO, TabelaPedidoDTO> colAtualizar;

    @FXML
    private TableColumn<TabelaPedidoDTO, TabelaPedidoDTO> colExcluir;

    @FXML
    private TableColumn<TabelaPedidoDTO, LocalDate> colData;

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
        String filtroSelecionadoData = comboBoxFiltroData.getValue();
        String filtroSelecionadoEstado = comboBoxFiltroEstado.getValue();

        if (filtroSelecionadoData == null) {
            filtroSelecionadoData = "";
        }

        if (filtroSelecionadoEstado == null) {
            filtroSelecionadoEstado = "";
        }

        tabelaFiltrada(filtroSelecionadoData, filtroSelecionadoEstado);
    }



    public void atualizarTabelaPedidos(){
        List<TabelaPedidoDTO> listaPedidos = controller.buscarPedidosParaTabelaPedidos();
        pedidos = FXCollections.observableList(listaPedidos);
        tableViewPedido.setItems(pedidos);
        iniciarBotoesDeMudarEstado();
        iniciarBotoesDeDetalhe();
        iniciarBotoesDeAtualizar();
        iniciarBotoesDeRemover();

    }

    private void tabelaFiltrada(String filtroData, String filtroEstado) {
        AtualizarVisaoTabelas.tabelaFiltradaPedido(filtroData, filtroEstado,
                pedidos, tableViewPedido);
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
            Alertas.mostrarAlerta("Erro", "Erro ao carregar tela!", Alert.AlertType.ERROR);
        }
    }

    private void loadCadastroView(String caminho){
        Stage telaBase = App.getMainStage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
            Parent novaTela = loader.load();

            CadastroPedidoController cadastroPedidoController = loader.getController();
            cadastroPedidoController.adicionarObserver(this);

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

    public void mudarEstado(TabelaPedidoDTO tabelaPedidoDTO) {
        try {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Atualizar estado pedido");
            dialog.setHeaderText("Digite o estado:");
            dialog.setContentText("Por favor, insira o novo estado de pedido:");

            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {
                String estadoDigitado = result.get().toUpperCase();
                Pedido pedido = new Pedido(tabelaPedidoDTO.getPedidoDTO());
                try {
                    switch( estadoDigitado) {
                    case "PRONTO":
                        pedido.concluir();
                        atualizarTabelaPedidos();
                        return;
                    case "FINALIZADO":
                        pedido.finalizar();
                        atualizarTabelaPedidos();
                        return;
                    case "CANCELADO":
                        pedido.cancelar();
                        atualizarTabelaPedidos();
                        return;
                        default:
                            Alertas.mostrarAlerta("Erro", "Estado inválido! Tente novamente.", Alert.AlertType.ERROR);
                }

                } catch (IllegalArgumentException e) {
                }
            }
        } catch (DBException e) {
            Alertas.mostrarAlerta("Erro", "Erro ao atualizar estado de pedido", Alert.AlertType.ERROR);
        }
    }


    public void detalharPedido(TabelaPedidoDTO tabelaPedidoDTO){
        try {
            Stage loginStage = App.getMainStage();
            ScrollPane telaAtual = (ScrollPane) loginStage.getScene().getRoot();
            AnchorPane telaAtualContent = (AnchorPane) telaAtual.getContent();

            Node menuBar = telaAtualContent.getChildren().get(0);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/acgprojeto/view/RelatorioPedido.fxml"));
            ScrollPane telaNova = loader.load();
            AnchorPane telaNovaContent = (AnchorPane) telaNova.getContent();

            telaAtualContent.getChildren().clear();
            telaAtualContent.getChildren().add(menuBar);
            telaAtualContent.getChildren().addAll(telaNovaContent.getChildren());

            RelatorioPedidoController relatorioPedidoController = loader.getController();
            List<TabelaPedidoDTO> tabelaPedidoDTOS = controller.buscarTabelaPedidoPorId(tabelaPedidoDTO.getIdPedido());
            relatorioPedidoController.setPedidos(FXCollections.observableArrayList(tabelaPedidoDTOS));

        } catch (IOException e) {
            Alertas.mostrarAlerta("Erro", "Erro ao detalhar pedido!", Alert.AlertType.ERROR);
        }

    }

    public void removerPedido(TabelaPedidoDTO tabelaPedidoDTO){
        Optional<ButtonType> escolha = Alertas.showConfirmation("Confirmação", "Tem certeza que quer deletar esse " +
                "pedido?");

        if(escolha.get() == ButtonType.OK){
            try{
                controller.excluirPedido(tabelaPedidoDTO.getPedidoDTO().getIdPedido());
                atualizarTabelaPedidos();
            }catch (DBException e){
                Alertas.mostrarAlerta("Erro", "Erro ao excluir pedido!", Alert.AlertType.ERROR);
            }
        }

    }

    private void iniciarBotoesDeMudarEstado() {
        colMudarEstado.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        colMudarEstado.setCellFactory(param -> new TableCell<TabelaPedidoDTO, TabelaPedidoDTO>() {
            private final Button button = new Button("Mudar Estado");
            protected void updateItem(TabelaPedidoDTO obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event -> mudarEstado(obj));
            }
        });
    }

    private void iniciarBotoesDeDetalhe() {
        colDetalhe.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        colDetalhe.setCellFactory(param -> new TableCell<TabelaPedidoDTO, TabelaPedidoDTO>() {
            private final Button button = new Button("Detalhe");
            protected void updateItem(TabelaPedidoDTO obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event -> detalharPedido(obj));
            }
        });
    }


    private void iniciarBotoesDeAtualizar() {
        colAtualizar.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        colAtualizar.setCellFactory(param -> new TableCell<TabelaPedidoDTO, TabelaPedidoDTO>() {

            private final Button button = new Button("Atualizar");
            protected void updateItem(TabelaPedidoDTO obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null || empty) {
                    setGraphic(null);
                    return;
                }

                setGraphic(button);
                button.setOnAction(event -> {
                    onBtnNovo();
                });
            }
        });
    }

    private void iniciarBotoesDeRemover() {
        colExcluir.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        colExcluir.setCellFactory(param -> new TableCell<TabelaPedidoDTO, TabelaPedidoDTO>() {
            private final Button button = new Button("Remover");
            protected void updateItem(TabelaPedidoDTO obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event -> removerPedido(obj));
            }
        });
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
        comboBoxFiltroData.setItems(listaOpcoes);

        List<String> opcoesEstado = new ArrayList<>();
        opcoesEstado.add(Estado.ANDAMENTO.toString());
        opcoesEstado.add(Estado.CANCELADO.toString());
        opcoesEstado.add(Estado.FINALIZADO.toString());
        opcoesEstado.add(Estado.PRONTO.toString());
        opcoesEstado.add("TODOS");

        listaOpcoes = FXCollections.observableList(opcoesEstado);
        comboBoxFiltroEstado.setItems(listaOpcoes);

        colNomeCliente.setCellValueFactory(new PropertyValueFactory<>("NomeCliente"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("DescricaoServico"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("ValorServico"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("TipoServico"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("Estado"));
        colData.setCellValueFactory(new PropertyValueFactory<>("Data"));

        atualizarTabelaPedidos();

    }

    @Override
    public void atualizarPedidos() {
        atualizarTabelaPedidos();
    }
}
