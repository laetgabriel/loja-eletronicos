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
import javafx.scene.control.Alert;
import org.acgprojeto.controller.PedidoController;
import org.acgprojeto.controller.PedidoProdutoController;
import org.acgprojeto.controller.ServicoController;import org.acgprojeto.dto.PedidoDTO;
import org.acgprojeto.dto.PedidoProdutoDTO;
import org.acgprojeto.dto.ServicoDTO;
import org.acgprojeto.model.entities.Produto;
import org.acgprojeto.model.state.EstadoPedido;
import org.acgprojeto.util.Alertas;

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
        Alertas.mostrarAlerta("Erro", "O pedido não pode ser finalizado no estado atual!", Alert.AlertType.ERROR);
    }

    @Override
    public void cancelar() {
        Alertas.mostrarAlerta("Erro", "O pedido não pode ser cancelado no estado atual!", Alert.AlertType.ERROR);
    }

    @Override
    public void concluir() {
        Alertas.mostrarAlerta("Erro", "O pedido não pode ser concluido no estado atual!", Alert.AlertType.ERROR);
    }

    public abstract String getNomeEstado();

    public void setPedidoDTO(PedidoDTO pedidoDTO){
        this.pedidoDTO = pedidoDTO;
    }
}

