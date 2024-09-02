package org.acgprojeto.view.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.acgprojeto.controller.ClienteController;
import org.acgprojeto.controller.PedidoController;
import org.acgprojeto.controller.ProdutoController;
import org.acgprojeto.controller.ServicoController;
import org.acgprojeto.dto.ClienteDTO;
import org.acgprojeto.dto.PedidoDTO;
import org.acgprojeto.dto.ProdutoDTO;
import org.acgprojeto.dto.ServicoDTO;
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
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class CadastroPedidoController implements Initializable {

    @FXML
    private ChoiceBox<String> choiceBoxTipoServico;
    @FXML
    private ChoiceBox<ClienteDTO> choiceBoxClientes;
    @FXML
    private ChoiceBox<ProdutoDTO> choiceBoxProduto;
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
    private ObservableList<ProdutoDTO> produtos;
    private ObservableList<ClienteDTO> clientes;
    private final ServicoValidator chainServico = new ServicoValidator();
    private final ClienteValidator clienteValidator = new ClienteValidator();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        data.setValue(LocalDate.now());
        Restricoes.setTextFieldDouble(txtPrecoServico);

        choiceBoxTipoServico.getItems().addAll("COMPRA", "VENDA", "CONSERTO");
        choiceBoxTipoServico.setValue("COMPRA");

        try {
            List<ClienteDTO> listaClientes = clienteController.listarTodosOsClientes();
            clientes = FXCollections.observableArrayList(listaClientes);
            choiceBoxClientes.setItems(clientes);

            choiceBoxClientes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    txtNomeCliente.setText(newValue.getNome());
                    txtEmailCliente.setText(newValue.getEmail());
                    txtTelefoneCliente.setText(newValue.getTelefone());
                }
            });

            List<ProdutoDTO> listaProdutos = produtoController.listarTodosOsProdutos();
            produtos = FXCollections.observableArrayList(listaProdutos);
            choiceBoxProduto.setItems(produtos);

        } catch (Exception e) {
            e.printStackTrace();
            Alertas.mostrarAlerta("Erro", "Não foi possível carregar os dados.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void salvarPedido() {
        Tipo tipo = TipoStringParaEnum(choiceBoxTipoServico.getValue());
        ClienteDTO clienteDTO = choiceBoxClientes.getValue() != null ?
                choiceBoxClientes.getValue() : clienteController.inserirCliente(new ClienteDTO(null, txtNomeCliente.getText(), txtEmailCliente.getText(), txtTelefoneCliente.getText()));
        clienteValidator.validarCliente(clienteDTO);

        ClienteDTO clienteAdicionado = clienteController.obterUltimoCliente();
        Cliente cliente = new Cliente(clienteAdicionado);


        Pedido pedido = new Pedido(null, cliente, data.getValue());
        PedidoDTO pedidoDTO = new PedidoDTO(pedido);

        pedidoController.inserirPedido(pedidoDTO);
        ServicoDTO servicoDTO = new ServicoDTO(
                null,
                pedidoController.obterUltimoPedido(),
                txtDescricaoServico.getText(),
                new BigDecimal(String.valueOf(!Objects.equals(txtPrecoServico.getText(), "") ? txtPrecoServico.getText():0)),
                tipo
        );
        chainServico.validateServico(servicoDTO);
        servicoController.inserirServico(servicoDTO);

    }

    private Tipo TipoStringParaEnum(String tipoString) {
        return switch (tipoString) {
            case "COMPRA" -> Tipo.COMPRA;
            case "VENDA" -> Tipo.VENDA;
            case "CONSERTO" -> Tipo.CONSERTO;
            default -> throw new IllegalArgumentException("Tipo inválido: " + tipoString);
        };
    }
}
