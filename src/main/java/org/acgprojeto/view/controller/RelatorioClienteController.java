package org.acgprojeto.view.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.acgprojeto.controller.ClienteController;
import org.acgprojeto.dto.ClienteDTO;
import org.acgprojeto.util.AtualizarVisaoTabelas;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RelatorioClienteController implements Initializable {

    private ClienteController clienteController;

    @FXML
    private Button btnRelatorioCliente;

    @FXML
    private TableView<ClienteDTO> tblRelatorioCliente;

    @FXML
    private TableColumn<ClienteDTO, Integer> colIdCliente;

    @FXML
    private TableColumn<ClienteDTO, String> colNome;

    @FXML
    private TableColumn<ClienteDTO, String> colEmail;

    @FXML
    private TableColumn<ClienteDTO, String> colTelefone;

    @FXML
    private ComboBox<String> comboBoxFiltro;

    private ObservableList<ClienteDTO> clientes;

    private ObservableList<ClienteDTO> clientesFiltro;

    @FXML
    private void onGerarRelatorioCliente(){
        Stage stage = (Stage) btnRelatorioCliente.getScene().getWindow();
        clienteController.gerarRelatorioCliente(stage, clientesFiltro);
    }

    @FXML
    public void onComboBoxFiltroChanged() {
        String filtroSelecionado = comboBoxFiltro.getEditor().getText();
        tabelaFiltrada(filtroSelecionado);
    }

    public void atualizarTabelaCliente(){
        List<ClienteDTO> listaClientes = clienteController.listarTodosOsClientes();
        clientes = FXCollections.observableList(listaClientes);
        clientesFiltro = FXCollections.observableArrayList(listaClientes);
        tblRelatorioCliente.setItems(clientes);
    }

    private void tabelaFiltrada(String filtro) {
        clientesFiltro = AtualizarVisaoTabelas.tabelaFiltradaCliente(filtro, clientes, tblRelatorioCliente);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clienteController = new ClienteController();
        comboBoxFiltro.setEditable(true);

        colIdCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        atualizarTabelaCliente();

        comboBoxFiltro.getEditor().textProperty().addListener((obs, oldVal, newVal) -> tabelaFiltrada(newVal));
    }
}