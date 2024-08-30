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
import org.acgprojeto.controller.ServicoController;
import org.acgprojeto.dto.ServicoDTO;
import org.acgprojeto.util.AtualizarVisaoTabelas;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RelatorioServicoController implements Initializable {

    private ServicoController servicoController;

    @FXML
    private Button btnRelatorioServico;

    @FXML
    private TableView<ServicoDTO> tblRelServicos;

    @FXML
    private TableColumn<ServicoDTO, String> colIdServico;

    @FXML
    private TableColumn<ServicoDTO, String> colDescricao;

    @FXML
    private TableColumn<ServicoDTO, String> colPreco;

    @FXML
    private TableColumn<ServicoDTO, Integer> colTipo;

    private ObservableList<ServicoDTO> servicos;

    private ObservableList<ServicoDTO> servicosFiltro;

    @FXML
    private ComboBox<String> comboBoxFiltro;

    private ObservableList<String> listaFiltro;


    @FXML
    private void onGerarRelatorioServico(){
        Stage stage = (Stage) btnRelatorioServico.getScene().getWindow();
        servicoController.gerarRelatorioServico(stage, servicosFiltro);
    }

    @FXML
    public void onComboBoxFiltroChanged() {
        String filtroSelecionado = comboBoxFiltro.getValue();
        tabelaFiltrada(filtroSelecionado);
    }

    public void atualizarTabelaServico(){
        List<ServicoDTO> listaServicos = servicoController.listarTodosOsServicos();
        servicos = FXCollections.observableList(listaServicos);
        servicosFiltro = FXCollections.observableArrayList(listaServicos);
        tblRelServicos.setItems(servicos);
    }

    private void tabelaFiltrada(String filtro) {
        servicosFiltro = AtualizarVisaoTabelas.tabelaFiltradaServico(filtro, servicos, tblRelServicos);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        servicoController = new ServicoController();
        List<String> opcoes = servicoController.listarTodosOsTiposDeServico();
        opcoes.add("SEM TIPO");
        listaFiltro = FXCollections.observableList(opcoes);

        comboBoxFiltro.setItems(listaFiltro);

        colIdServico.setCellValueFactory(new PropertyValueFactory<>("idServico"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        atualizarTabelaServico();
    }
}
