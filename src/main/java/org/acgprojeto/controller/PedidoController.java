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
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import org.acgprojeto.dao.PedidoDAO;
import org.acgprojeto.dao.impl.PedidoDAOImpl;
import org.acgprojeto.db.DB;
import org.acgprojeto.dto.*;
import org.acgprojeto.util.FileChooserUtil;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PedidoController {

    PedidoDAO pedidoDAO = new PedidoDAOImpl(DB.getConexao());

    public void inserirPedido(PedidoDTO pedidoDTO) {
        pedidoDAO.inserirPedido(pedidoDTO);
    }

    public void atualizarPedido(PedidoDTO pedidoDTO) {
        pedidoDAO.atualizarPedido(pedidoDTO);
    }

    public void atualizarEstadoPedido(PedidoDTO pedidoDTO) {
        pedidoDAO.atualizarEstadoPedido(pedidoDTO);
    }

    public void excluirPedido(Integer id) {
        pedidoDAO.excluirPedido(id);
    }

    public List<PedidoDTO> buscarPedidos() { return pedidoDAO.buscarPedidos(); }

    public PedidoDTO buscarPedidoPorId(Integer id) {
        return pedidoDAO.buscarPedidoPorId(id);
    }

    public List<TabelaPedidoDTO> buscarPedidosParaTabelaPedidos(){return pedidoDAO.buscarPedidosParaTabelaPedidos(); }

    public List<TabelaPedidoDTO> buscarTabelaPedidoPorId(Integer id){ return pedidoDAO.buscarTabelaPedidoPorId(id); };

    public List<TabelaPedidoDTO> buscarPedidosParaTabelaRelPedidos(){ return pedidoDAO.buscarPedidosParaTabelaRelPedidos(); }

    public void gerarRelatorioPedido(Stage stage, ObservableList<TabelaPedidoDTO> pedidos) {
        File file = FileChooserUtil.gerarFileChooser("Relatório_Pedido").showSaveDialog(stage);

        if (file != null) {
            try (PdfWriter writer = new PdfWriter(file);
                 PdfDocument pdfDoc = new PdfDocument(writer);
                 Document document = new Document(pdfDoc)) {

                BigDecimal valorTotalGeral = BigDecimal.ZERO;  // Valor total de todos os pedidos
                PdfFont font = PdfFontFactory.createRegisteredFont("Helvetica-Bold");
                document.add(new Paragraph("Relatório de Pedido")
                        .setFont(font)
                        .setFontSize(20)
                        .setTextAlignment(TextAlignment.CENTER));

                Set<Integer> pedidosAdicionados = new HashSet<>();

                for (TabelaPedidoDTO TabelapedidoDTO : pedidos) {
                    if (!pedidosAdicionados.contains(TabelapedidoDTO.getPedidoDTO().getIdPedido())) {
                        pedidosAdicionados.add(TabelapedidoDTO.getPedidoDTO().getIdPedido());  // Marca o pedido como incluído
                        BigDecimal valorTotaPedido = BigDecimal.ZERO;  // Valor total do pedido atual

                        gerarDetalhesPedido(document, TabelapedidoDTO, font);
                        gerarTabelaProdutos(document, TabelapedidoDTO, font);
                        valorTotaPedido = gerarTabelaServicos(document, TabelapedidoDTO, font, valorTotaPedido);

                        // Adiciona o valor total do pedido ao relatório
                        document.add(new Paragraph("Valor total do Pedido: R$" + valorTotaPedido)
                                .setFont(font)
                                .setFontSize(14));
                        document.add(new Paragraph("\n"));

                        // Acumula o valor total de todos os pedidos
                        valorTotalGeral = valorTotalGeral.add(valorTotaPedido);
                    }
                }


                // Adiciona o valor total geral ao final do relatório
                document.add(new Paragraph("Valor total das vendas: R$" + valorTotalGeral)
                        .setFont(font)
                        .setFontSize(16));

            } catch (IOException e) {
                throw new RuntimeException("Erro ao gerar relatório de pedido", e);
            }
        }
    }

    private void gerarDetalhesPedido(Document document, TabelaPedidoDTO tabelaPedidoDTO, PdfFont font) {
        document.add(new Paragraph("ID do Pedido: " + tabelaPedidoDTO.getPedidoDTO().getIdPedido()));
        document.add(new Paragraph("Cliente: " + tabelaPedidoDTO.getPedidoDTO().getCliente().getNome()));
        document.add(new Paragraph("Data: " + tabelaPedidoDTO.getPedidoDTO().getData().toString()));
        document.add(new Paragraph("Estado: " + tabelaPedidoDTO.getPedidoDTO().getEstado()));
        document.add(new Paragraph("\n"));
    }

    private void gerarTabelaProdutos(Document document, TabelaPedidoDTO pedidoDTO, PdfFont font) {
        PedidoProdutoController pedidoProdutoController = new PedidoProdutoController();
        List<PedidoProdutoDTO> pedidoProdutos = pedidoProdutoController.listarPedidoProduto();

        Table tableProdutos = new Table(UnitValue.createPercentArray(new float[]{1, 1, 1, 1, 1}))
                .setWidth(UnitValue.createPercentValue(100));

        tableProdutos.addHeaderCell(new Cell().add(new Paragraph("Produto ID"))
                .setBackgroundColor(new DeviceRgb(0, 0, 255)).setFontColor(ColorConstants.WHITE));
        tableProdutos.addHeaderCell(new Cell().add(new Paragraph("Nome")));
        tableProdutos.addHeaderCell(new Cell().add(new Paragraph("Categoria")));
        tableProdutos.addHeaderCell(new Cell().add(new Paragraph("Preço")));
        tableProdutos.addHeaderCell(new Cell().add(new Paragraph("Quantidade")));

        for (PedidoProdutoDTO pp : pedidoProdutos) {
            if (pp.getPedido().getIdPedido().equals(pedidoDTO.getPedidoDTO().getIdPedido())) {
                ProdutoDTO produtoDTO = pp.getProduto();
                tableProdutos.addCell(new Paragraph(produtoDTO.getIdProduto().toString()));
                tableProdutos.addCell(new Paragraph(produtoDTO.getNomeProduto()));
                tableProdutos.addCell(new Paragraph(String.valueOf(produtoDTO.getCategoria())));
                tableProdutos.addCell(new Paragraph(String.valueOf(produtoDTO.getPreco())));
                tableProdutos.addCell(new Paragraph(pp.getQuantidade().toString()));
            }
        }

        document.add(new Paragraph("Produtos:").setFont(font).setFontSize(14));
        document.add(tableProdutos);
        document.add(new Paragraph("\n"));

    }

    private BigDecimal gerarTabelaServicos(Document document, TabelaPedidoDTO pedidoDTO, PdfFont font, BigDecimal valorTotalPedido) {
        ServicoController servicoController = new ServicoController();

        List<ServicoDTO> servicos = servicoController.listarServicosPorPedido(pedidoDTO);

        Table tableServicos = new Table(UnitValue.createPercentArray(new float[]{2, 4, 2}))
                .setWidth(UnitValue.createPercentValue(100));

        tableServicos.addHeaderCell(new Cell().add(new Paragraph("Serviço ID"))
                .setBackgroundColor(new DeviceRgb(0, 128, 0)).setFontColor(ColorConstants.WHITE));
        tableServicos.addHeaderCell(new Cell().add(new Paragraph("Descrição")));
        tableServicos.addHeaderCell(new Cell().add(new Paragraph("Preço")));

        for (ServicoDTO servico : servicos) {
            tableServicos.addCell(new Paragraph(servico.getIdServico().toString()));
            tableServicos.addCell(new Paragraph(servico.getDescricao()));
            tableServicos.addCell(new Paragraph(servico.getPreco().toString()));
            valorTotalPedido = valorTotalPedido.add(servico.getPreco());
        }

        document.add(new Paragraph("Serviços:").setFont(font).setFontSize(14));
        document.add(tableServicos);
        document.add(new Paragraph("\n"));

        return valorTotalPedido;
    }
}



