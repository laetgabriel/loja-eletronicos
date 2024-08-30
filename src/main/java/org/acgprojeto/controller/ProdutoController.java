package org.acgprojeto.controller;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import javafx.stage.Stage;
import org.acgprojeto.dao.ProdutoDAO;
import org.acgprojeto.dao.impl.ProdutoDAOImpl;
import org.acgprojeto.db.DB;
import org.acgprojeto.dto.ProdutoDTO;
import org.acgprojeto.model.enums.Categoria;
import org.acgprojeto.util.FileChooserUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ProdutoController {

    ProdutoDAO produtoDAO = new ProdutoDAOImpl(DB.getConexao());

    public void inserirProduto(ProdutoDTO produtoDTO) {
        if (isProdutoCadastrado(produtoDTO.getNomeProduto(), produtoDTO.getCategoria())) {
            System.out.println("Produto com o mesmo nome e tipo já cadastrado.");
        } else {
            produtoDAO.inserirProduto(produtoDTO);
        }
    }

    public void atualizarProduto(ProdutoDTO produtoDTO) {
        produtoDAO.atualizarProduto(produtoDTO);
    }

    public void excluirProduto(Integer id) {
        produtoDAO.excluirProduto(id);
    }

    public List<ProdutoDTO> listarTodosOsProdutos() {
        return produtoDAO.listarTodosOsProdutos();
    }

    public List<String> listarTodasAsCategorias() {return produtoDAO.listarTodasAsCategorias();
    }

    public ProdutoDTO buscarProdutoPorId(Integer id) {
        return produtoDAO.buscarProdutoPorId(id);
    }

    private boolean isProdutoCadastrado(String nome, Categoria tipo) {
        List<ProdutoDTO> produtos = listarTodosOsProdutos();
        for (ProdutoDTO produto : produtos) {
            if (produto.getNomeProduto().equalsIgnoreCase(nome) && produto.getCategoria().equals(tipo)) {
                return true;
            }
        }
        return false;
    }

    public void gerarRelatorioProduto(Stage stage) {
        File file = FileChooserUtil.gerarFileChooser("Relatório_Produto").showSaveDialog(stage);

        if (file != null) {
            try (PdfWriter writer = new PdfWriter(file);
                 PdfDocument pdfDoc = new PdfDocument(writer);
                 Document document = new Document(pdfDoc)) {

                PdfFont font = PdfFontFactory.createRegisteredFont("Helvetica-Bold");
                document.add(new Paragraph("Relatório de Produtos")
                        .setFont(font)
                        .setFontSize(20)
                        .setTextAlignment(TextAlignment.CENTER));

                List<ProdutoDTO> produtos = listarTodosOsProdutos();

                Table tableProdutos = new Table(UnitValue.createPercentArray(new float[]{1, 3, 2, 2, 2}))
                        .setWidth(UnitValue.createPercentValue(100));

                tableProdutos.addHeaderCell(new Cell()
                        .add(new Paragraph("ID"))
                        .setBackgroundColor(new DeviceRgb(0, 128, 0))
                        .setFontColor(ColorConstants.WHITE));
                tableProdutos.addHeaderCell(new Cell().add(new Paragraph("Nome")));
                tableProdutos.addHeaderCell(new Cell().add(new Paragraph("Categoria")));
                tableProdutos.addHeaderCell(new Cell().add(new Paragraph("Preço")));
                tableProdutos.addHeaderCell(new Cell().add(new Paragraph("Quantidade em Estoque")));

                for (ProdutoDTO produtoDTO : produtos) {
                    tableProdutos.addCell(new Paragraph(produtoDTO.getIdProduto() != null
                            ? produtoDTO.getIdProduto().toString() : "N/A"));
                    tableProdutos.addCell(new Paragraph(produtoDTO.getNomeProduto() != null
                            ? produtoDTO.getNomeProduto() : "N/A"));
                    tableProdutos.addCell(new Paragraph(produtoDTO.getCategoria().toString() != null
                            ? produtoDTO.getCategoria().toString() : "N/A"));
                    tableProdutos.addCell(new Paragraph(produtoDTO.getPreco().toString() != null
                            ? produtoDTO.getPreco().toString() : "N/A"));
                    tableProdutos.addCell(new Paragraph(produtoDTO.getQuantidadeEstoque().toString()));
                }

                document.add(new Paragraph("Produtos:")
                        .setFont(font)
                        .setFontSize(14));
                document.add(tableProdutos);

            } catch (IOException e) {
                throw new RuntimeException("Erro ao gerar relatório de produtos");
            }
        }
    }
}
