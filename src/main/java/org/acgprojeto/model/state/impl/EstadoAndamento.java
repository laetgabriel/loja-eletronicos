package org.acgprojeto.model.state.impl;

import javafx.scene.control.Alert;
import org.acgprojeto.model.enums.Estado;
import org.acgprojeto.util.Alertas;

public class EstadoAndamento extends AbstractEstadoPedido {
    public EstadoAndamento() {

    }

    @Override
    public void finalizar() {
        Alertas.mostrarAlerta("Sucesso", "Estado do pedido atualizado com sucesso", Alert.AlertType.INFORMATION);
        pedidoDTO.setEstado(Estado.FINALIZADO);
        pedidoController.atualizarEstadoPedido(pedidoDTO);

    }

    @Override
    public void cancelar() {
        Alertas.mostrarAlerta("Sucesso", "Estado do pedido atualizado com sucesso", Alert.AlertType.INFORMATION);
        pedidoDTO.setEstado(Estado.CANCELADO);
        pedidoController.atualizarEstadoPedido(pedidoDTO);
    }

    @Override
    public void concluir() {
        Alertas.mostrarAlerta("Sucesso", "Estado do pedido atualizado com sucesso", Alert.AlertType.INFORMATION);
        pedidoDTO.setEstado(Estado.PRONTO);
        pedidoController.atualizarEstadoPedido(pedidoDTO);
    }
    @Override
    public String getNomeEstado() {
        return "ANDAMENTO";
    }
}
