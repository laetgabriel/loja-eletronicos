package org.acgprojeto.model.state.impl;

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
import org.acgprojeto.controller.PedidoController;
import org.acgprojeto.dao.impl.PedidoProdutoDAOImpl;
import org.acgprojeto.dao.impl.ServicoDAOImpl;
import org.acgprojeto.db.DB;
import org.acgprojeto.dto.PedidoDTO;
import org.acgprojeto.dto.PedidoProdutoDTO;
import org.acgprojeto.dto.ServicoDTO;
import org.acgprojeto.model.entities.Produto;
import org.acgprojeto.model.state.EstadoPedido;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public abstract class AbstractEstadoPedido implements EstadoPedido {

    protected PedidoDTO pedidoDTO = null;
    protected final PedidoController pedidoController = new PedidoController();

    public AbstractEstadoPedido() {
    }

    @Override
    public void finalizar(){
        throw new UnsupportedOperationException("Operação não permitida no estado atual.");
    }

    @Override
    public void cancelar() {
        throw new UnsupportedOperationException("Operação não permitida no estado atual.");
    }

    @Override
    public void concluir() {
        throw new UnsupportedOperationException("Operação não permitida no estado atual.");
    }

    public void gerarRelatorio(){
        String path = System.getProperty("user.dir") + "\\PDF_Relatório_" + pedidoDTO.getIdPedido() + ".pdf";
        try (PdfWriter writer = new PdfWriter(path);
             PdfDocument pdfDoc = new PdfDocument(writer);
             Document document = new Document(pdfDoc)) {

            // Adiciona o título
            BigDecimal valorTotalPedido = new BigDecimal(0);
            PdfFont font = PdfFontFactory.createRegisteredFont("Helvetica-Bold");
            document.add(new Paragraph("Relatório de Pedido")
                    .setFont(font)
                    .setFontSize(20)
                    .setTextAlignment(TextAlignment.CENTER));

            // Adiciona informações do pedido
            document.add(new Paragraph("ID do Pedido: " + pedidoDTO.getIdPedido()));
            document.add(new Paragraph("Cliente: " + pedidoDTO.getCliente().getNome()));
            document.add(new Paragraph("Data: " + pedidoDTO.getData().toString()));
            document.add(new Paragraph("Estado: " + pedidoDTO.getEstado()));
            document.add(new Paragraph("\n"));

            // Adiciona a tabela de produtos
            PedidoProdutoDAOImpl pedidoProdutoDAO = new PedidoProdutoDAOImpl(DB.getConexao());
            List<PedidoProdutoDTO> pedidoProdutos = pedidoProdutoDAO.listarPedidoProduto();

            Table tableProdutos = new Table(UnitValue.createPercentArray(new float[]{1, 1, 1, 1, 1}))
                    .setWidth(UnitValue.createPercentValue(100));

            // Cabeçalhos da tabela de produtos
            tableProdutos.addHeaderCell(new Cell().add(new Paragraph("Produto ID")).setBackgroundColor(new DeviceRgb(0, 0, 255)).setFontColor(ColorConstants.WHITE));
            tableProdutos.addHeaderCell(new Cell().add(new Paragraph("Nome")));
            tableProdutos.addHeaderCell(new Cell().add(new Paragraph("Categoria")));
            tableProdutos.addHeaderCell(new Cell().add(new Paragraph("Preço")));
            tableProdutos.addHeaderCell(new Cell().add(new Paragraph("Quantidade")));

            for (PedidoProdutoDTO pp : pedidoProdutos) {
                if (pp.getPedido().getIdPedido().equals(pedidoDTO.getIdPedido())) {
                    Produto produto = new Produto(pp.getProduto());
                    tableProdutos.addCell(new Paragraph(produto.getIdProduto().toString()));
                    tableProdutos.addCell(new Paragraph(produto.getNomeProduto()));
                    tableProdutos.addCell(new Paragraph(String.valueOf(produto.getCategoria())));
                    tableProdutos.addCell(new Paragraph(String.valueOf(produto.getPreco())));
                    tableProdutos.addCell(new Paragraph(pp.getQuantidade().toString()));
                    valorTotalPedido = valorTotalPedido.add(pp.getPreco());
                }
            }

            document.add(new Paragraph("Produtos:").setFont(font).setFontSize(14));
            document.add(tableProdutos);
            document.add(new Paragraph("\n"));

            // Adiciona a tabela de serviços
            ServicoDAOImpl servicoDAO = new ServicoDAOImpl(DB.getConexao());
            List<ServicoDTO> servicos = servicoDAO.listarServicosPorPedido(pedidoDTO);

            Table tableServicos = new Table(UnitValue.createPercentArray(new float[]{2, 4, 2}))
                    .setWidth(UnitValue.createPercentValue(100));

            // Cabeçalhos da tabela de serviços
            tableServicos.addHeaderCell(new Cell().add(new Paragraph("Serviço ID")).setBackgroundColor(new DeviceRgb(0, 128, 0))
                    .setFontColor(ColorConstants.WHITE));
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
            document.add(new Paragraph("Valor total do Pedido: "+ "R$" + valorTotalPedido ));

        } catch (IOException e) {
            throw new RuntimeException("Erro ao gerar relatório de pedido", e);
        }
    }

    public abstract String getNomeEstado();

    public void setPedidoDTO(PedidoDTO pedidoDTO){
        this.pedidoDTO = pedidoDTO;
    }
}

