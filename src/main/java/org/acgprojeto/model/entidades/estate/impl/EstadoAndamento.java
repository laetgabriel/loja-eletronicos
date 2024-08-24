package org.acgprojeto.model.entidades.estate.impl;

import org.acgprojeto.dto.PedidoDTO;
import org.acgprojeto.model.enums.Estado;

public class EstadoAndamento extends AbstractEstadoPedido {
    public EstadoAndamento() {

    }

    @Override
    public void finalizar() {
        System.out.println("Pedido em andamento finalizado.");
        pedidoDTO.setEstado(Estado.FINALIZADO);
        pedidoController.atualizarEstadoPedido(pedidoDTO);

    }

    @Override
    public void cancelar() {
        System.out.println("Pedido em andamento cancelado.");
        pedidoDTO.setEstado(Estado.CANCELADO);
        pedidoController.atualizarEstadoPedido(pedidoDTO);
    }

    @Override
    public void concluir() {
        System.out.println("Pedido em andamento concluído.");
        pedidoDTO.setEstado(Estado.PRONTO);
        pedidoController.atualizarEstadoPedido(pedidoDTO);
    }
//    @Override
//    public void gerarRelatorio() {
//        super.gerarRelatorio();
//    }
    @Override
    public String getNomeEstado() {
        return "ANDAMENTO";
    }
}