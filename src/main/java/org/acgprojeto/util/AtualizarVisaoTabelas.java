package org.acgprojeto.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.acgprojeto.dto.ClienteDTO;
import org.acgprojeto.dto.PedidoDTO;
import org.acgprojeto.dto.ProdutoDTO;
import org.acgprojeto.dto.ServicoDTO;

import java.time.LocalDate;
import java.util.List;

public class AtualizarVisaoTabelas {

    public static ObservableList<PedidoDTO> tabelaFiltradaPedido(String filtro, List<PedidoDTO> pedidos, TableView<PedidoDTO> table) {
        ObservableList<PedidoDTO> listaFiltrada = FXCollections.observableArrayList();
        for (PedidoDTO pedido : pedidos) {
            if (filtroPedido(pedido, filtro)) {
                listaFiltrada.add(pedido);
            }
        }
        table.setItems(listaFiltrada);
        return listaFiltrada;
    }

    private static boolean filtroPedido(PedidoDTO pedidoDTO, String filtro) {
        LocalDate hoje = LocalDate.now();
        switch (filtro) {
            case "Hoje":
                return pedidoDTO.getData().isEqual(hoje);
            case "Semanal":
                LocalDate umaSemanaAtras = hoje.minusWeeks(1);
                return !pedidoDTO.getData().isBefore(umaSemanaAtras) && !pedidoDTO.getData().isAfter(hoje);
            case "Mês":
                LocalDate umMesAtras = hoje.minusMonths(1);
                return !pedidoDTO.getData().isBefore(umMesAtras) && !pedidoDTO.getData().isAfter(hoje);
            case "Todos":
                return true;
            default:
                return false;
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
