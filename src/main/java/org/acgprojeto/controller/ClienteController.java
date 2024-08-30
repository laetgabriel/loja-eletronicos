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
import org.acgprojeto.dao.ClienteDAO;
import org.acgprojeto.dao.impl.ClienteDAOImpl;
import org.acgprojeto.db.DB;
import org.acgprojeto.dto.ClienteDTO;
import org.acgprojeto.util.FileChooserUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ClienteController {

    ClienteDAO clienteDAO = new ClienteDAOImpl(DB.getConexao());

    public void inserirCliente(ClienteDTO clienteDTO) {
        if (isEmailOrTelefoneCadastrado(clienteDTO.getEmail(), clienteDTO.getTelefone())) {
            System.out.println("Email ou telefone já cadastrado.");
        } else {
            clienteDAO.inserirCliente(clienteDTO);
        }
    }

    public void atualizarCliente(ClienteDTO clienteDTO) {
        clienteDAO.atualizarCliente(clienteDTO);
    }

    public void excluirCliente(Integer id) {
        clienteDAO.excluirCliente(id);
    }

    public ClienteDTO buscarClientePorId(Integer id) {
        return clienteDAO.buscarClientePorId(id);
    }

    public List<ClienteDTO> listarTodosOsClientes() {
        return clienteDAO.listarTodosOsClientes();
    }

    private boolean isEmailOrTelefoneCadastrado(String email, String telefone) {
        List<ClienteDTO> clientes = listarTodosOsClientes();
        for (ClienteDTO cliente : clientes) {
            if (cliente.getEmail().equalsIgnoreCase(email) || cliente.getTelefone().equals(telefone)) {
                return true;
            }
        }
        return false;
    }

    public void gerarRelatorioCliente(Stage stage) {
        File file = FileChooserUtil.gerarFileChooser("Relatório_Cliente").showSaveDialog(stage);
        if(file != null) {
            try (PdfWriter writer = new PdfWriter(file);
                 PdfDocument pdfDoc = new PdfDocument(writer);
                 Document document = new Document(pdfDoc)) {

                PdfFont font = PdfFontFactory.createRegisteredFont("Helvetica-Bold");
                document.add(new Paragraph("Relatório de Clientes")
                        .setFont(font)
                        .setFontSize(20)
                        .setTextAlignment(TextAlignment.CENTER));

                List<ClienteDTO> clientes = listarTodosOsClientes();

                Table tableClientes = new Table(UnitValue.createPercentArray(new float[]{1, 3, 3, 3}))
                        .setWidth(UnitValue.createPercentValue(100));
                tableClientes.addHeaderCell(new Cell()
                        .add(new Paragraph("Cliente ID"))
                        .setBackgroundColor(new DeviceRgb(0, 0, 255))
                        .setFontColor(ColorConstants.WHITE));
                tableClientes.addHeaderCell(new Cell().add(new Paragraph("Nome")));
                tableClientes.addHeaderCell(new Cell().add(new Paragraph("Email")));
                tableClientes.addHeaderCell(new Cell().add(new Paragraph("Telefone")));

                for (ClienteDTO clienteDTO : clientes) {
                    tableClientes.addCell(new Paragraph(
                            clienteDTO.getIdCliente() != null ? clienteDTO.getIdCliente().toString() : "N/A"
                    ));
                    tableClientes.addCell(new Paragraph(
                            clienteDTO.getNome() != null ? clienteDTO.getNome() : "N/A"
                    ));
                    tableClientes.addCell(new Paragraph(
                            clienteDTO.getEmail() != null ? clienteDTO.getEmail() : "N/A"
                    ));
                    tableClientes.addCell(new Paragraph(
                            clienteDTO.getTelefone() != null ? clienteDTO.getTelefone() : "N/A"
                    ));
                }

                document.add(new Paragraph("Clientes:")
                        .setFont(font)
                        .setFontSize(14));
                document.add(tableClientes);

            } catch (IOException e) {
                throw new RuntimeException("Erro ao gerar relatório de clientes", e);
            }
        }
    }
}
