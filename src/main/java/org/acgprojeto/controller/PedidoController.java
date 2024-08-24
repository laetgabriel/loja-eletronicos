package org.acgprojeto.controller;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.acgprojeto.dao.PedidoDAO;
import org.acgprojeto.dao.impl.PedidoDAOImpl;
import org.acgprojeto.dao.impl.ServicoDAOImpl;
import org.acgprojeto.db.DB;
import org.acgprojeto.dto.PedidoDTO;
import org.acgprojeto.dto.ServicoDTO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

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

    public List<PedidoDTO> buscarPedidos() {
        return pedidoDAO.buscarPedidos();
    }

    public PedidoDTO buscarPedidoPorId(Integer id) {
        return pedidoDAO.buscarPedidoPorId(id);
    }
}