package org.acgprojeto.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.acgprojeto.dto.*;
import org.acgprojeto.model.enums.Estado;

import java.time.LocalDate;
import java.util.List;

public class AtualizarVisaoTabelas {

    public static ObservableList<TabelaPedidoDTO> tabelaFiltradaPedido(String filtroData, String filtroEstado, List<TabelaPedidoDTO> pedidos, TableView<TabelaPedidoDTO> table) {
        ObservableList<TabelaPedidoDTO> listaFiltrada = FXCollections.observableArrayList();

        for (TabelaPedidoDTO pedido : pedidos) {
            if (filtroPorData(pedido.getPedidoDTO(), filtroData) && filtroPorEstado(pedido.getPedidoDTO(), filtroEstado)) {
                listaFiltrada.add(pedido);
            }
        }
        table.setItems(listaFiltrada);
        return listaFiltrada;
    }

    private static boolean filtroPorData(PedidoDTO pedidoDTO, String filtroData) {
        LocalDate hoje = LocalDate.now();

        switch (filtroData) {
            case "Hoje":
                return pedidoDTO.getData().isEqual(hoje);
            case "Semanal":
                return pedidoDTO.getData().isAfter(hoje.minusDays(7)) && pedidoDTO.getData().isBefore(hoje.plusDays(1));
            case "Mês":
                return pedidoDTO.getData().getMonth().equals(hoje.getMonth());
            case "Todos":
            default:
                return true;
        }
    }

    private static boolean filtroPorEstado(PedidoDTO pedidoDTO, String filtroEstado) {
        switch (filtroEstado) {
            case "ANDAMENTO":
                return pedidoDTO.getEstado().equals(Estado.ANDAMENTO);
            case "PRONTO":
                return pedidoDTO.getEstado().equals(Estado.PRONTO);
            case "CANCELADO":
                return pedidoDTO.getEstado().equals(Estado.CANCELADO);
            case "FINALIZADO":
                return pedidoDTO.getEstado().equals(Estado.FINALIZADO);
            case "Todos":
            default:
                return true;
        }
    }



    public static ObservableList<ProdutoDTO> tabelaFiltradaProduto(String filtro, List<ProdutoDTO> produtos, TableView<ProdutoDTO> table) {
        ObservableList<ProdutoDTO> listaFiltrada = FXCollections.observableArrayList();
        for (ProdutoDTO produto : produtos) {
            if (filtroProduto(produto, filtro)) {
                listaFiltrada.add(produto);
            }
        }

        table.setItems(listaFiltrada);
        return listaFiltrada;
    }

    private static boolean filtroProduto(ProdutoDTO produtoDTO, String filtro) {
        if (produtoDTO.getCategoria().toString().equalsIgnoreCase(filtro) || filtro.equals("SEM CATEGORIA")) {
            return true;
        }

        return false;
    }


    public static ObservableList<ClienteDTO> tabelaFiltradaCliente(String filtro, List<ClienteDTO> clientes, TableView<ClienteDTO> table) {
        ObservableList<ClienteDTO> listaFiltrada = FXCollections.observableArrayList();

        for (ClienteDTO cliente : clientes) {
            if (cliente.getNome().toLowerCase().contains(filtro.toLowerCase())) {
                listaFiltrada.add(cliente);
            }
        }

        table.setItems(listaFiltrada);
        return listaFiltrada;
    }

    public static ObservableList<ServicoDTO> tabelaFiltradaServico(String filtro, List<ServicoDTO> servicos, TableView<ServicoDTO> table) {
        ObservableList<ServicoDTO> listaFiltrada = FXCollections.observableArrayList();
        for (ServicoDTO servico : servicos) {
            if (filtroServico(servico, filtro)) {
                listaFiltrada.add(servico);
            }
        }

        table.setItems(listaFiltrada);
        return listaFiltrada;
    }

    private static boolean filtroServico(ServicoDTO servicoDTO, String filtro) {
        if (servicoDTO.getTipo().toString().equalsIgnoreCase(filtro) || filtro.equals("SEM TIPO")) {
            return true;
        }

        return false;
    }
}
