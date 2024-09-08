package org.acgprojeto.view.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.acgprojeto.controller.*;
import org.acgprojeto.controller.PedidoController;
import org.acgprojeto.controller.ProdutoController;
import org.acgprojeto.db.exceptions.DBException;
import org.acgprojeto.dto.*;
import org.acgprojeto.view.controller.chain.exceptions.ValidacaoException;
import org.acgprojeto.view.controller.chain.validacoesservico.ServicoValidator;
import org.acgprojeto.view.controller.chain.validacoescliente.ClienteValidator;
import org.acgprojeto.model.entities.Cliente;
import org.acgprojeto.model.entities.Pedido;
import org.acgprojeto.model.enums.Tipo;
import org.acgprojeto.util.Alertas;
import org.acgprojeto.util.Restricoes;
import org.acgprojeto.view.observer.PedidoObserver;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class CadastroPedidoController implements Initializable {

    @FXML
    private ComboBox<String> comboBoxTipoServico;
    @FXML
    private ComboBox<ClienteDTO> comboBoxClientes;
    @FXML
    private CheckBox checkBoxCliente;
    @FXML
    private CheckBox checkBoxPedido;
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
    private Label lblErroProdutoSelecionado;
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

    private List<PedidoObserver> observers = new ArrayList<>();
    private ObservableList<ClienteDTO> clientes;
    private final ProdutoController produtoController = new ProdutoController();
    private final ClienteController clienteController = new ClienteController();
    private final PedidoController pedidoController = new PedidoController();
    private final ServicoController servicoController = new ServicoController();
    private final PedidoProdutoController pedidoProdutoController = new PedidoProdutoController();
    private final ServicoValidator chainServico = new ServicoValidator();
    private final ClienteValidator clienteValidator = new ClienteValidator();

    public void adicionarObserver(PedidoObserver observer) {
        observers.add(observer);
    }

    public void onCheckBoxCliente() {
        if (checkBoxCliente.isSelected()) {
            txtNomeCliente.clear();
            txtEmailCliente.clear();
            txtTelefoneCliente.clear();
            comboBoxClientes.setValue(null);
            comboBoxClientes.setDisable(true);
            txtNomeCliente.setDisable(true);
            txtEmailCliente.setDisable(true);
            txtTelefoneCliente.setDisable(true);
        } else {
            txtNomeCliente.setDisable(false);
            txtEmailCliente.setDisable(false);
            txtTelefoneCliente.setDisable(false);
            comboBoxClientes.setDisable(false);
        }

    }

    public void onCheckBoxPedido() {
        if (checkBoxPedido.isSelected()) {
            comboBoxProduto.setValue(null);
            comboBoxProduto.setDisable(true);
            txtQuantidadeProduto.clear();
            txtQuantidadeProduto.setDisable(true);
        } else {
            comboBoxProduto.setDisable(false);
            txtQuantidadeProduto.setDisable(false);
        }

    }

    public void onComboBoxTipoServico() {
        if (Tipo.valueOf(comboBoxTipoServico.getValue()).equals(Tipo.CONSERTO) || Tipo.valueOf(comboBoxTipoServico.getValue()).equals(Tipo.COMPRA)) {
            checkBoxCliente.setDisable(true);
            checkBoxCliente.setSelected(false);
            onCheckBoxCliente();

            checkBoxPedido.setSelected(true);
            checkBoxPedido.setDisable(true);
            onCheckBoxPedido();
        } else {
            checkBoxCliente.setDisable(false);
            checkBoxPedido.setSelected(false);
            checkBoxPedido.setDisable(true);
            onCheckBoxPedido();
        }
    }


    @FXML
    private void salvarPedido() {
        String quantidadeString = txtQuantidadeProduto.getText();
        int quantidadeInt = 0;
        Tipo tipo = TipoStringParaEnum(comboBoxTipoServico.getValue());
        ProdutoDTO produto = comboBoxProduto.getValue();
        atualizarComboBoxProduto();
        ClienteDTO clienteDTO = null;

        try {
            if (!checkBoxCliente.isSelected()) {
                if (comboBoxClientes.getValue() != null) {
                    clienteDTO = comboBoxClientes.getValue();
                } else {
                    clienteDTO = new ClienteDTO(null, txtNomeCliente.getText(), txtEmailCliente.getText(), txtTelefoneCliente.getText());
                    validacaoCompletaCliente(clienteDTO);
                    clienteController.inserirCliente(clienteDTO);
                    clienteDTO = clienteController.obterUltimoCliente();
                }

                Cliente cliente = new Cliente(clienteDTO);
                Pedido pedido = new Pedido(null, cliente, data.getValue());
                PedidoDTO pedidoDTO = new PedidoDTO(pedido);

                pedidoController.inserirPedido(pedidoDTO);
            } else {
                Pedido pedido = new Pedido(null, new Cliente(), data.getValue());
                PedidoDTO pedidoDTO = new PedidoDTO(pedido);

                pedidoController.inserirPedidoSemCliente(pedidoDTO);
            }

            ServicoDTO servicoDTO = new ServicoDTO(
                    null,
                    pedidoController.obterUltimoPedido(),
                    txtDescricaoServico.getText(),
                    new BigDecimal(String.valueOf
                            (!Objects.equals(txtPrecoServico.getText(), "") ? txtPrecoServico.getText() : 0)),
                    tipo
            );

            validacoCompletaServico(servicoDTO);

            if (!checkBoxPedido.isSelected()) {
                validacaoProdutoeQuantidadeCompleta(quantidadeString, produto);
                quantidadeInt = Integer.parseInt(quantidadeString);
                produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidadeInt);
                produtoController.atualizarProduto(produto);
                atualizarComboBoxProduto();
                pedidoProdutoController.inserirPedidoProduto
                        (new PedidoProdutoDTO(
                                pedidoController.obterUltimoPedido(),
                                produto,
                                produto.getPreco(),
                                quantidadeInt));
            }


            servicoController.inserirServico(servicoDTO);

            txtNomeCliente.setDisable(true);
            txtEmailCliente.setDisable(true);
            txtTelefoneCliente.setDisable(true);
            txtDescricaoServico.setDisable(true);
            txtPrecoServico.setDisable(true);
            txtQuantidadeProduto.setDisable(true);
            comboBoxProduto.setDisable(true);
            comboBoxClientes.setDisable(true);
            comboBoxTipoServico.setDisable(true);
            data.setDisable(true);
            btnSalvar.setDisable(true);

            lblErroProdutoSelecionado.setText("");
            lblErroQuantidadeProduto.setText("");

            btnAdicionarServico.setDisable(false);
            btnAdicionarProduto.setDisable(false);

            notificarOuvintes();

            if (tipo.equals(Tipo.COMPRA)){
                Optional<ButtonType> opcaoCompra = Alertas.showConfirmation("Pedido de compra criado", "Compra feita! Se for um produto, deseja cadastrar ao estoque?");
                if (opcaoCompra.get() == ButtonType.OK) {
                    abrirTelaCadastroProduto();
                }
            }

            Optional<ButtonType> opcao = Alertas.showConfirmation("Pedido criado", "Pedido adicionado! Adicionar mais produtos ou serviços?");

            if (opcao.get() == ButtonType.CANCEL) {
                Stage stage = (Stage) btnCancelar.getScene().getWindow();
                stage.close();
            }

        } catch (ValidacaoException e) {
            try {
                PedidoDTO ultimoPedido = pedidoController.obterUltimoPedido();
                if (ultimoPedido != null) {
                    pedidoController.excluirPedido(ultimoPedido.getIdPedido());
                    if (comboBoxClientes.getValue() == null) {
                        clienteController.excluirCliente(clienteController.obterUltimoCliente().getIdCliente());
                    }
                }
            } catch (DBException _) {

            }
            lblErroNomeCliente.setText("");
            lblErroEmailCliente.setText("");
            lblErroTelefoneCliente.setText("");
            lblErroDescricaoServico.setText("");
            lblErroPrecoServico.setText("");
            lblErroProdutoSelecionado.setText("");
            lblErroQuantidadeProduto.setText("");
            if (e.getMessage().contains("nome")) {
                lblErroNomeCliente.setText(e.getMessage());
            } else if (e.getMessage().contains("email")) {
                lblErroEmailCliente.setText(e.getMessage());
            } else if (e.getMessage().contains("telefone")) {
                lblErroTelefoneCliente.setText(e.getMessage());
            } else if (e.getMessage().contains("descrição")) {
                lblErroDescricaoServico.setText(e.getMessage());
            } else if (e.getMessage().contains("preço")) {
                lblErroPrecoServico.setText(e.getMessage());
            } else if (e.getMessage().contains("produto")) {
                lblErroProdutoSelecionado.setText(e.getMessage());
            } else if (e.getMessage().contains("indisponível") || e.getMessage().contains("inválida") || e.getMessage().contains("zero")) {
                lblErroQuantidadeProduto.setText(e.getMessage());
            }

        }

    }

    @FXML
    private void onBtnCancelar() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
        notificarOuvintes();
    }

    private void abrirTelaCadastroProduto() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/acgprojeto/view/CadastroProduto.fxml"));
            Parent root = loader.load();

            CadastroProdutoController cadastroProdutoController = loader.getController();

            // Configurar o produto se necessário (você pode passar um ProdutoDTO já criado ou deixá-lo vazio para novo cadastro)
            cadastroProdutoController.setProdutoDTO(null);

            Stage stage = new Stage();
            stage.setTitle("Cadastrar Produto no Estoque");
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            Alertas.mostrarAlerta("Erro", "Erro ao abrir a tela de cadastro de produto!", Alert.AlertType.ERROR);
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
        choiceBoxTipoServico.setValue("VENDA");

        VBox vbox = new VBox();
        vbox.setSpacing(12);
        vbox.getChildren().addAll(new Label("Descrição do servico:"), txtDescricao, new Label("Preço:"), txtPreco, new Label("Tipo de Serviço:"), choiceBoxTipoServico);

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
            } catch (ValidacaoException e) {
                Alertas.mostrarAlerta("ERRO", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void abrirDialogProduto() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Adicionar Produto");
        dialog.setHeaderText("Selecione um produto");

        ComboBox<ProdutoDTO> comboBoxProduto = new ComboBox<>();
        comboBoxProduto.getItems().addAll(produtoController.listarTodosOsProdutos());

        TextField txtQuantidade = new TextField();
        txtQuantidade.setPromptText("Quantidade");

        ButtonType buttonTypeOk = new ButtonType("ADICIONAR", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeOk, ButtonType.CANCEL);

        VBox vBox = new VBox();
        vBox.setSpacing(12);
        vBox.getChildren().addAll(new Label("Digite a quantidade... "), txtQuantidade, new Label("Produto: "), comboBoxProduto);
        atualizarComboBoxProduto();
        dialog.getDialogPane().setContent(vBox);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == buttonTypeOk) {
            String quantidadeString = String.valueOf(txtQuantidade.getText());
            int quantidade;
            ProdutoDTO produto = comboBoxProduto.getValue();
            try {
                validacaoProdutoeQuantidadeCompleta(quantidadeString, produto);

                quantidade = Integer.parseInt(quantidadeString);
                produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidade);
                produtoController.atualizarProduto(produto);

                pedidoProdutoController.inserirPedidoProduto(new PedidoProdutoDTO(
                        pedidoController.obterUltimoPedido(),
                        produto, produto.getPreco(),
                        quantidade));
                atualizarComboBoxProduto();
            } catch (ValidacaoException e) {
                Alertas.mostrarAlerta("ERRO", e.getMessage(), Alert.AlertType.ERROR);
            }
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

    private void atualizarComboBoxProduto() {
        List<ProdutoDTO> listaProdutos = produtoController.listarTodosOsProdutos();
        listaProdutos.removeIf(produtoDTO -> produtoDTO.getQuantidadeEstoque() == 0);
        ObservableList<ProdutoDTO> produtos = FXCollections.observableArrayList(listaProdutos);
        comboBoxProduto.setItems(produtos);
    }


    private void notificarOuvintes() {
        for (PedidoObserver observer : observers) {
            observer.atualizarPedidos();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        data.setValue(LocalDate.now());
        checkBoxPedido.setDisable(true);

        Restricoes.setTextFieldMaxLength(txtTelefoneCliente, 9);
        Restricoes.setTextFieldDouble(txtPrecoServico);
        Restricoes.setTextFieldInteger(txtQuantidadeProduto);

        btnAdicionarProduto.setDisable(true);
        btnAdicionarServico.setDisable(true);

        comboBoxTipoServico.getItems().addAll("COMPRA", "VENDA", "CONSERTO");
        comboBoxTipoServico.setValue("VENDA");

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
                } else {
                    txtNomeCliente.setDisable(false);
                    txtEmailCliente.setDisable(false);
                    txtTelefoneCliente.setDisable(false);
                }

            });

            atualizarComboBoxProduto();

        } catch (Exception ignored) {
            Alertas.mostrarAlerta("Erro", "Não foi possível carregar os dados.", Alert.AlertType.ERROR);
        }
    }

    private void validarProdutoEQuantidade(String quantidadeTxt, ProdutoDTO produtoDTO) {
        if (produtoDTO == null) {
            throw new ValidacaoException("Selecione um produto para adicionar");
        }

        if (quantidadeTxt == null || quantidadeTxt.isEmpty() || !quantidadeTxt.matches("\\d+")) {
            throw new ValidacaoException("Quantidade inválida");
        }

        int quantidade = Integer.parseInt(quantidadeTxt);
        if (quantidade == 0) {
            throw new ValidacaoException("Quantidade não pode ser zero");
        }

        if (quantidade > produtoDTO.getQuantidadeEstoque()) {
            throw new ValidacaoException("Quantidade indisponível");
        }
    }

    private void validacaoProdutoeQuantidadeCompleta(String quantidadeTxt, ProdutoDTO produtoDTO) throws ValidacaoException {
        validarProdutoEQuantidade(quantidadeTxt, produtoDTO);

    }

    private void validacaoCompletaCliente(ClienteDTO clienteDTO) throws ValidacaoException {
        lblErroNomeCliente.setText("");
        lblErroEmailCliente.setText("");
        lblErroTelefoneCliente.setText("");
        clienteValidator.validarCliente(clienteDTO);
    }

    private void validacoCompletaServico(ServicoDTO servicoDTO) throws ValidacaoException {
        lblErroDescricaoServico.setText("");
        lblErroPrecoServico.setText("");
        chainServico.validateServico(servicoDTO);
    }
}

