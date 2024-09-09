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
import org.acgprojeto.dao.DAOFactory;
import org.acgprojeto.dao.ServicoDAO;
import org.acgprojeto.dto.PedidoDTO;
import org.acgprojeto.dto.ServicoDTO;
import org.acgprojeto.dto.TabelaPedidoDTO;
import org.acgprojeto.model.enums.Tipo;
import org.acgprojeto.util.FileChooserUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServicoController {

    ServicoDAO servicoDAO = DAOFactory.criarServicoDAO();

    public void inserirServico(ServicoDTO servicoDTO) {
        servicoDAO.inserirServico(servicoDTO);
    }

    public void atualizarServico(ServicoDTO servicoDTO) {
        servicoDAO.atualizarServico(servicoDTO);
    }

    public void excluirServico(Integer id) {
        servicoDAO.excluirServico(id);
    }

    public List<ServicoDTO> listarTodosOsServicos() {
        return servicoDAO.listarTodosOsServicos();
    }

    public ServicoDTO buscarServicoPorId(Integer id) {
        return servicoDAO.buscarServicoPorId(id);
    }

    public List<ServicoDTO> listarServicosPorPedido(PedidoDTO pedidoDTO) {
        return servicoDAO.listarServicosPorPedido(pedidoDTO);
    }
    public List<ServicoDTO> listarServicosPorPedido(TabelaPedidoDTO pedidoDTO) {
        return servicoDAO.listarServicosPorPedido(pedidoDTO);
    }


    public List<String> listarTodosOsTiposDeServico() {
        List<Tipo> tipos = servicoDAO.listarTodosOsTiposDeServicos();
        List<String> tiposDeServicos = new ArrayList<>();

        for (Tipo tipo : tipos) {
            tiposDeServicos.add(tipo.toString());
        }
        return tiposDeServicos;
    }

    public void gerarRelatorioServico(Stage stage, ObservableList<ServicoDTO> servicos) {
        File file = FileChooserUtil.gerarFileChooser("Relatório_Servico").showSaveDialog(stage);
        if (file != null) {

            try (PdfWriter writer = new PdfWriter(file);
                 PdfDocument pdfDoc = new PdfDocument(writer);
                 Document document = new Document(pdfDoc)) {

                PdfFont font = PdfFontFactory.createRegisteredFont("Helvetica-Bold");
                document.add(new Paragraph("Relatório de Serviços")
                        .setFont(font)
                        .setFontSize(20)
                        .setTextAlignment(TextAlignment.CENTER));

                Table tableServicos = new Table(UnitValue.createPercentArray(new float[]{1, 3, 3, 2, 2}))
                        .setWidth(UnitValue.createPercentValue(100));

                tableServicos.addHeaderCell(new Cell()
                        .add(new Paragraph("ID"))
                        .setBackgroundColor(new DeviceRgb(0, 128, 0)) // Verde
                        .setFontColor(ColorConstants.WHITE));
                tableServicos.addHeaderCell(new Cell().add(new Paragraph("Descrição")));
                tableServicos.addHeaderCell(new Cell().add(new Paragraph("Preço")));
                tableServicos.addHeaderCell(new Cell().add(new Paragraph("Tipo")));
                tableServicos.addHeaderCell(new Cell().add(new Paragraph("Pedido ID")));

                for (ServicoDTO servicoDTO : servicos) {
                    tableServicos.addCell(new Paragraph(
                            servicoDTO.getIdServico() != null ? servicoDTO.getIdServico().toString() : "N/A"
                    ));
                    tableServicos.addCell(new Paragraph(
                            servicoDTO.getDescricao() != null ? servicoDTO.getDescricao() : "N/A"
                    ));
                    tableServicos.addCell(new Paragraph(
                            servicoDTO.getPreco() != null ? servicoDTO.getPreco().toString() : "N/A"
                    ));
                    tableServicos.addCell(new Paragraph(
                            servicoDTO.getTipo() != null ? servicoDTO.getTipo().toString() : "N/A"
                    ));
                    tableServicos.addCell(new Paragraph(
                            servicoDTO.getPedido() != null && servicoDTO.getPedido().getIdPedido() != null
                                    ? servicoDTO.getPedido().getIdPedido().toString()
                                    : "N/A"
                    ));
                }

                document.add(new Paragraph("Serviços:")
                        .setFont(font)
                        .setFontSize(14));
                document.add(tableServicos);

            } catch (IOException e) {
                throw new RuntimeException("Erro ao gerar relatório de serviços", e);
            }
        }
    }
}
