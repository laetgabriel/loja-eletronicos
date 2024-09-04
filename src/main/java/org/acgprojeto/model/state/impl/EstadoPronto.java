package org.acgprojeto.model.state.impl;

import javafx.scene.control.Alert;
import org.acgprojeto.model.enums.Estado;
import org.acgprojeto.util.Alertas;

public class EstadoPronto extends AbstractEstadoPedido {

    public EstadoPronto() {
    }

    @Override
    public void finalizar() {
        Alertas.mostrarAlerta("Sucesso", "Estado do pedido atualizado com sucesso", Alert.AlertType.INFORMATION);
        pedidoDTO.setEstado(Estado.FINALIZADO);
        pedidoController.atualizarEstadoPedido(pedidoDTO);

    }

    @Override
    public String getNomeEstado() {
        return "PRONTO";
    }
}
