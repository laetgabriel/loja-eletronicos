package org.acgprojeto.dto;

import org.acgprojeto.model.entities.Pedido;
import org.acgprojeto.model.enums.Categoria;
import org.acgprojeto.model.enums.Estado;
import org.acgprojeto.model.enums.Tipo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TabelaPedidoDTO {

    private PedidoProdutoDTO pedidoProdutoDTO;
    private ServicoDTO servicoDTO;
    private PedidoDTO pedidoDTO;
    private ProdutoDTO produtoDTO;


    public PedidoProdutoDTO getPedidoProdutoDTO() {
        return pedidoProdutoDTO;
    }

    public void setPedidoDTO(PedidoDTO pedidoDTO) {
        this.pedidoDTO = pedidoDTO;
    }

    public void setProdutoDTO(ProdutoDTO produtoDTO) {
        this.produtoDTO = produtoDTO;
    }

    public void setPedidoProdutoDTO(PedidoProdutoDTO pedidoProdutoDTO) {
        this.pedidoProdutoDTO = pedidoProdutoDTO;
    }

    public ServicoDTO getServicoDTO() {
        return servicoDTO;
    }

    public void setServicoDTO(ServicoDTO servicoDTO) {
        this.servicoDTO = servicoDTO;
    }

    public PedidoDTO getPedidoDTO() {
        return pedidoProdutoDTO.getPedido();
    }



    public ProdutoDTO getProdutoDTO() {
        return pedidoProdutoDTO.getProduto();
    }

    public Integer getIdServico() {
        return this.servicoDTO.getIdServico();
    }

    public PedidoDTO getPedido() {
        return this.servicoDTO.getPedido();
    }

    public String getDescricaoServico() {
        return servicoDTO.getDescricao();
    }

    public BigDecimal getValorServico() {
        return servicoDTO.getPreco();
    }

    public Tipo getTipoServico() {
        return servicoDTO.getTipo();
    }

    public Integer getIdProduto() {
        return produtoDTO.getIdProduto();
    }

    public String getNomeProduto() {
        return produtoDTO.getNomeProduto();
    }

    public Categoria getCategoria() {
        return produtoDTO.getCategoria();
    }

    public Integer getIdPedido() {
        return pedidoDTO.getIdPedido();
    }

    public BigDecimal getPrecoPedidoProduto() {
        return pedidoProdutoDTO.getPreco();
    }

    public Integer getQuantidadePedidoProduto() {
        return pedidoProdutoDTO.getQuantidade();
    }
    public ClienteDTO getCliente() {
        return pedidoDTO.getCliente();
    }

    public String getNomeCliente(){
        return pedidoDTO.getCliente().getNome();
    }

    public Estado getEstado() {
        return pedidoDTO.getEstado();
    }


    public LocalDate getData() {
        return pedidoDTO.getData();
    }


    public TabelaPedidoDTO() {
    }
}