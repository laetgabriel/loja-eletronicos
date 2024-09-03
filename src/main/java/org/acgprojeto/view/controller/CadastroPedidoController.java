package org.acgprojeto.view.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.acgprojeto.controller.*;
import org.acgprojeto.controller.PedidoController;
import org.acgprojeto.controller.ProdutoController;
import org.acgprojeto.dto.*;
import org.acgprojeto.model.chain.exceptions.ValidacaoException;
import org.acgprojeto.model.chain.validacoes.ServicoValidator;
import org.acgprojeto.model.chain.validacoescliente.ClienteValidator;
import org.acgprojeto.model.entities.Cliente;
import org.acgprojeto.model.entities.Pedido;
import org.acgprojeto.model.enums.Tipo;
import org.acgprojeto.util.Alertas;
import org.acgprojeto.util.Restricoes;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class CadastroPedidoController implements Initializable {

    @FXML
    private ChoiceBox<String> choiceBoxTipoServico;
    @FXML
    private ComboBox<ClienteDTO> comboBoxClientes;
    @FXML
    private ComboBox<ProdutoDTO> comboBoxProduto;
    @FXML
    private TextField txtNomeCliente;
    @FXML
    private TextField txtEmailCliente;
    @FXML
    private TextField txtDescricaoServico;
    @FXML
    private TextField txtPrecoServico;
    @FXML
    private TextField txtTelefoneCliente;
    @FXML
    private TextField txtQuantidadeProduto;
    @FXML
    private Label lblErroNomeCliente;
    @FXML
    private Label lblErroEmailCliente;
    @FXML
    private Label lblErroTelefoneCliente;
    @FXML
    private Label lblErroDescricaoServico;
    @FXML
    private Label lblErroPrecoServico;
    @FXML
    private Label lblErroQuantidadeProduto;
    @FXML
    private DatePicker data;
    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnAdicionarProduto;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnAdicionarServico;

    private final ProdutoController produtoController = new ProdutoController();
    private final ClienteController clienteController = new ClienteController();
    private final PedidoController pedidoController = new PedidoController();
    private final ServicoController servicoController = new ServicoController();
    private final PedidoProdutoController pedidoProdutoController = new PedidoProdutoController();
    private ObservableList<ProdutoDTO> produtos;
    private ObservableList<ClienteDTO> clientes;
    private final ServicoValidator chainServico = new ServicoValidator();
    private final ClienteValidator clienteValidator = new ClienteValidator();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Restricoes.setTextFieldString(txtNomeCliente);
        data.setValue(LocalDate.now());
        Restricoes.setTextFieldDouble(txtPrecoServico);

        choiceBoxTipoServico.getItems().addAll("COMPRA", "VENDA", "CONSERTO");
        choiceBoxTipoServico.setValue("VENDA");

        btnAdicionarServico.setDisable(true);
        btnAdicionarProduto.setDisable(true);

        try {
            List<ClienteDTO> listaClientes = clienteController.listarTodosOsClientes();
            clientes = FXCollections.observableArrayList(listaClientes);
            comboBoxClientes.setItems(clientes);

            comboBoxClientes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    txtNomeCliente.setText(newValue.getNome());
                    txtEmailCliente.setText(newValue.getEmail());
                    txtTelefoneCliente.setText(newValue.getTelefone());
                    txtNomeCliente.setDisable(true);
                    txtEmailCliente.setDisable(true);
                    txtTelefoneCliente.setDisable(true);
                }else {
                    txtNomeCliente.setDisable(false);
                    txtEmailCliente.setDisable(false);
                    txtTelefoneCliente.setDisable(false);
                }

            });

            atualizarComboBoxProduto();

            choiceBoxTipoServico.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if("COMPRA".equals(newValue)) {
                    comboBoxProduto.setDisable(true);
                    btnAdicionarProduto.setDisable(true);
                }else {
                    comboBoxProduto.setDisable(false);
                    btnAdicionarProduto.setDisable(false);
                }
            });

        } catch (Exception ignored) {
            Alertas.mostrarAlerta("Erro", "Não foi possível carregar os dados.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void salvarPedido() {

        String quantidadeString = txtQuantidadeProduto.getText();
        Tipo tipo = TipoStringParaEnum(choiceBoxTipoServico.getValue());
        ProdutoDTO produto = comboBoxProduto.getValue();
        atualizarComboBoxProduto();
        int quantidadeInt;
        try {
            quantidadeInt = Integer.parseInt(quantidadeString);
        } catch (NumberFormatException e) {
            Alertas.mostrarAlerta("Erro", "Quantidade inválida", Alert.AlertType.ERROR);
            return;
        }

        if (produto == null) {
            Alertas.mostrarAlerta("Erro", "Produto não selecionado", Alert.AlertType.ERROR);
            return;
        }

        if (quantidadeString.isEmpty() || quantidadeInt > produto.getQuantidadeEstoque()) {
            Alertas.mostrarAlerta("Erro", "Quantidade para esse produto indisponível no estoque", Alert.AlertType.ERROR);
            return;
        } else {
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidadeInt);
            produtoController.atualizarProduto(produto);
        }

        ClienteDTO clienteDTO =
                comboBoxClientes.getValue() != null ?
                        comboBoxClientes.getValue() : new ClienteDTO(null,
                        txtNomeCliente.getText(),
                        txtEmailCliente.getText(),
                        txtTelefoneCliente.getText());

        Cliente cliente = null;

        try {
            if (comboBoxClientes.getValue() != null) {
                clienteDTO = comboBoxClientes.getValue();
            } else {
                clienteValidator.validarCliente(clienteDTO);
                clienteController.inserirCliente(clienteDTO);
                clienteDTO = clienteController.obterUltimoCliente();
            }
        }catch (ValidacaoException _){
            return;
        }
        cliente = new Cliente(clienteDTO);


        Pedido pedido = new Pedido(null, cliente, data.getValue());
        PedidoDTO pedidoDTO = new PedidoDTO(pedido);
        try {
            pedidoController.inserirPedido(pedidoDTO);

            ServicoDTO servicoDTO = new ServicoDTO(
                    null,
                    pedidoController.obterUltimoPedido(),
                    txtDescricaoServico.getText(),
                    new BigDecimal(String.valueOf
                            (!Objects.equals(txtPrecoServico.getText(), "") ? txtPrecoServico.getText() : 0)),
                    tipo
            );

            chainServico.validateServico(servicoDTO);
            servicoController.inserirServico(servicoDTO);
            pedidoProdutoController.inserirPedidoProduto
                    (new PedidoProdutoDTO(
                            pedidoController.obterUltimoPedido(),
                            comboBoxProduto.getValue(),
                            comboBoxProduto.getValue().getPreco(),
                            quantidadeInt));
            btnAdicionarServico.setDisable(false);
            btnAdicionarProduto.setDisable(false);
            txtDescricaoServico.setDisable(true);
            txtPrecoServico.setDisable(true);
            btnSalvar.setDisable(true);
        } catch (ValidacaoException e) {
            PedidoDTO ultimoPedido = pedidoController.obterUltimoPedido();
            if (ultimoPedido != null) {
                pedidoController.excluirPedido(ultimoPedido.getIdPedido());
            }

        }

    }

    @FXML
    private void abrirDialogoAdicionarServico() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Adicionar Serviço");
        dialog.setHeaderText("Informe os detalhes do serviço");

        ButtonType buttonTypeOk = new ButtonType("ADICIONAR", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeOk, ButtonType.CANCEL);

        TextField txtPreco = new TextField();
        txtPreco.setPromptText("Preço");

        TextField txtDescricao = new TextField();
        txtDescricao.setPromptText("Descrição");

        ChoiceBox<String> choiceBoxTipoServico = new ChoiceBox<>();
        choiceBoxTipoServico.getItems().addAll("COMPRA", "VENDA", "CONSERTO");
        choiceBoxTipoServico.setValue("COMPRA");

        VBox vbox = new VBox();
        vbox.setSpacing(12);
        vbox.getChildren().addAll(new Label("Servico:"), txtDescricao, new Label("Descrição:"), txtPreco, new Label("Tipo de Serviço:"), choiceBoxTipoServico);

        dialog.getDialogPane().setContent(vbox);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == buttonTypeOk) {
            String preco = txtPreco.getText();
            String descricao = txtDescricao.getText();
            String tipoServico = choiceBoxTipoServico.getValue();
            Tipo tipo = TipoStringParaEnum(tipoServico);

            try {
                ServicoDTO servicoDTO = new ServicoDTO(
                        null,
                        pedidoController.obterUltimoPedido(),
                        descricao,
                        new BigDecimal(String.valueOf
                                (!Objects.equals(preco, "") ? preco : 0)),
                        tipo
                );

                chainServico.validateServico(servicoDTO);
                servicoController.inserirServico(servicoDTO);
            }catch (ValidacaoException _) {

            }
        }
    }

    @FXML
    public void abrirDialogProduto(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Adicionar Produto");
        dialog.setHeaderText("Selecione um produto");

        ComboBox<ProdutoDTO> comboBoxProduto = new ComboBox<>();
        comboBoxProduto.getItems().addAll(produtoController.listarTodosOsProdutos());

        ButtonType buttonTypeOk = new ButtonType("ADICIONAR", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeOk, ButtonType.CANCEL);

        VBox vBox = new VBox();
        vBox.setSpacing(12);
        vBox.getChildren().addAll(new Label("Produto: "), comboBoxProduto);

        dialog.getDialogPane().setContent(vBox);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == buttonTypeOk) {
            System.out.println(comboBoxProduto.getValue());
        }

    }

    private Tipo TipoStringParaEnum(String tipoString) {
        return switch (tipoString) {
            case "COMPRA" -> Tipo.COMPRA;
            case "VENDA" -> Tipo.VENDA;
            case "CONSERTO" -> Tipo.CONSERTO;
            default -> throw new IllegalArgumentException("Tipo inválido: " + tipoString);
        };
    }

    public void atualizarComboBoxProduto() {
        List<ProdutoDTO> listaProdutos = produtoController.listarTodosOsProdutos();
        listaProdutos.removeIf(produtoDTO -> produtoDTO.getQuantidadeEstoque() == 0);
        ObservableList<ProdutoDTO> produtos = FXCollections.observableArrayList(listaProdutos);
        comboBoxProduto.setItems(produtos);
    }

}
