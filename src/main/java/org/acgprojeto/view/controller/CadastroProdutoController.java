package org.acgprojeto.view.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.acgprojeto.controller.ProdutoController;
import org.acgprojeto.dto.ProdutoDTO;
import org.acgprojeto.model.enums.Categoria;
import org.acgprojeto.model.exceptions.ValidacaoCadastrosException;
import org.acgprojeto.util.Alertas;
import org.acgprojeto.util.Restricoes;
import org.acgprojeto.view.observer.ProdutoObserver;

import java.math.BigDecimal;
import java.net.URL;
import java.util.*;

public class CadastroProdutoController implements Initializable {

    private ProdutoController controller;

    private ProdutoDTO produtoDTO;

    private Boolean isAtualizarProduto = false;

    @FXML
    private TextField txtNome;
    @FXML
    private Label lblErroNome;
    @FXML
    private TextField txtPreco;
    @FXML
    private Label lblErroPreco;
    @FXML
    private TextField txtQtdEstoque;
    @FXML
    private Label lblErroQtdEstoque;
    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnCancelar;

    @FXML
    private ComboBox<Categoria> comboBoxCategoria;

    @FXML
    private Label lblErroCategoria;

    private ObservableList<Categoria> listaOpcoes;

    private List<ProdutoObserver> observers = new ArrayList<>();

    public void adicionarObserver(ProdutoObserver observer) {
        observers.add(observer);
    }

    @FXML
    public void onBtnSalvar() {
        limparErros();
        try {
            if (!isAtualizarProduto) {
                produtoDTO = getDadosCampos();
                controller.inserirProduto(produtoDTO);
                notificarOuvintes();
                Stage stage = (Stage) btnSalvar.getScene().getWindow();
                stage.close();
            }else{
                produtoDTO = getDadosCampos();
                controller.atualizarProduto(produtoDTO);
                notificarOuvintes();
                Stage stage = (Stage) btnSalvar.getScene().getWindow();
                stage.close();
                org.acgprojeto.view.controller.ProdutoController.produtoSelecionado = null;
            }


        } catch (ValidacaoCadastrosException e) {
            setLblErros(e.getErrors());
        } catch (Exception e) {
            Alertas.mostrarAlerta("Erro", "Erro ao salvar/atualizar produto!", Alert.AlertType.ERROR);
        }
    }



    @FXML
    public void onBtnCancelar() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        org.acgprojeto.view.controller.ProdutoController.produtoSelecionado = null;
        stage.close();
    }

    private ProdutoDTO getDadosCampos() throws ValidacaoCadastrosException {
        ValidacaoCadastrosException validacaoCampos = new ValidacaoCadastrosException();

        String nome = txtNome.getText();
        Categoria categoria = comboBoxCategoria.getSelectionModel().getSelectedItem();
        String precoText = txtPreco.getText();
        String qtdEstoqueText = txtQtdEstoque.getText();

        if (nome == null || nome.trim().isEmpty()) {
            validacaoCampos.addErro("nome", "Nome não pode ser vazio!");
        }
        if (categoria == null) {
            validacaoCampos.addErro("categoria", "Precisa selecionar uma categoria!");
        }
        if (precoText == null || precoText.trim().isEmpty()) {
            validacaoCampos.addErro("preco", "Preço não pode ser vazio!");
        }else if(new BigDecimal(precoText).scale() > 2){
            validacaoCampos.addErro("preco", "Preço não pode ter mais que duas casas decimais!");
        }

        if (qtdEstoqueText == null || qtdEstoqueText.trim().isEmpty()) {
            validacaoCampos.addErro("qtdEstoque", "Quantidade no estoque não pode ser vazio!");
        }

        if(controller.isProdutoCadastrado(nome,categoria) && !isAtualizarProduto){
            validacaoCampos.addErro("nome", "Produto com esse nome e categoria já existe!");
        }

        if (!validacaoCampos.getErrors().isEmpty()) {
            throw validacaoCampos;
        }
        ProdutoDTO produtoDTO;

        if(!isAtualizarProduto){
            produtoDTO = ProdutoDTO.ProdutoDTOBuilder.aProdutoDTO()
                .idProduto(null)
                .nomeProduto(nome)
                .categoria(categoria)
                .preco(new BigDecimal(precoText))
                .quantidadeEstoque(Integer.parseInt(qtdEstoqueText))
                .build();
        }else{
            produtoDTO = ProdutoDTO.ProdutoDTOBuilder.aProdutoDTO()
                .idProduto(this.produtoDTO.getIdProduto())
                .nomeProduto(nome)
                .categoria(categoria)
                .preco(new BigDecimal(precoText))
                .quantidadeEstoque(Integer.parseInt(qtdEstoqueText))
                .build();
    }
        return produtoDTO;
    }

    private void notificarOuvintes(){
        for (ProdutoObserver observer : observers) {
            observer.atualizarProdutos();
        }
    }

    private void setLblErros(Map<String, String> erros){
        Set<String> campos = erros.keySet();

            if(campos.contains("nome")){
                lblErroNome.setText(erros.get("nome"));
            }
            if (campos.contains("categoria")){
                lblErroCategoria.setText(erros.get("categoria"));
            }

            if(campos.contains("preco")){
                lblErroPreco.setText(erros.get("preco"));
            }

            if(campos.contains("qtdEstoque")){
                lblErroQtdEstoque.setText(erros.get("qtdEstoque"));
            }

    }

    private void preencherCampos() {
        if(produtoDTO != null){
            txtNome.setText(produtoDTO.getNomeProduto());
            comboBoxCategoria.setValue(produtoDTO.getCategoria());
            txtPreco.setText(produtoDTO.getPreco().toString());
            txtQtdEstoque.setText(String.valueOf(produtoDTO.getQuantidadeEstoque()));
        }

    }


    private void limparErros() {
        lblErroNome.setText("");
        lblErroCategoria.setText("");
        lblErroPreco.setText("");
        lblErroQtdEstoque.setText("");
    }

    public void setProdutoDTO(ProdutoDTO pd) {
        if(pd != null){
            produtoDTO = pd;
            isAtualizarProduto = true;
            preencherCampos();
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new org.acgprojeto.controller.ProdutoController();
        List<Categoria> opcoes = Arrays.stream(Categoria.values()).toList();

        listaOpcoes = FXCollections.observableList(opcoes);
        comboBoxCategoria.setItems(listaOpcoes);

        Restricoes.setTextFieldDouble(txtPreco);
        Restricoes.setTextFieldInteger(txtQtdEstoque);
        Restricoes.setTextFieldMaxLength(txtNome,100);
    }
}
