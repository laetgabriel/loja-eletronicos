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
import org.acgprojeto.controller.PedidoProdutoController;
import org.acgprojeto.controller.ServicoController;import org.acgprojeto.dto.PedidoDTO;
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

    public abstract String getNomeEstado();

    public void setPedidoDTO(PedidoDTO pedidoDTO){
        this.pedidoDTO = pedidoDTO;
    }
}

