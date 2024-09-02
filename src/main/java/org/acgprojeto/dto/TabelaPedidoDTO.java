package org.acgprojeto.dto;

import org.acgprojeto.model.entities.Pedido;
import org.acgprojeto.model.enums.Estado;
import org.acgprojeto.model.enums.Tipo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TabelaPedidoDTO {

    private Integer idPedido;
    private ClienteDTO cliente;
    private Estado estado;
    private LocalDate data;
    private Tipo tipoServico;
    private BigDecimal valorServico;
    private String descricaoServico;
    private String nomeProduto;
    private BigDecimal precoPedidoProduto;
    private Integer quantidadePedidoProduto;

    public TabelaPedidoDTO(Integer idPedido, ClienteDTO cliente, Estado estado, LocalDate data) {
        this.idPedido = idPedido;
        this.cliente = cliente;
        this.estado = estado;
        this.data = data;
    }

    public TabelaPedidoDTO(Pedido pedido) {
        this.idPedido = pedido.getIdPedido();
        this.cliente = new ClienteDTO(pedido.getCliente());
        this.estado = Estado.valueOf(pedido.getEstado().getNomeEstado());// Se estado for uma instância de EstadoPedido
        this.data = pedido.getData();
    }

    public TabelaPedidoDTO() {

    }

    // Getters e Setters
    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public String getNomeCliente(){
        return cliente.getNome();
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    //Preencher tabelas de pedidos na view

    public void setTipoServico(Tipo tipoServico) {
        this.tipoServico = tipoServico;
    }

    public void setValorServico(BigDecimal valorServico) {
        this.valorServico = valorServico;
    }

    public void setDescricaoServico(String descricaoServico) {
        this.descricaoServico = descricaoServico;
    }

    public BigDecimal getValorServico(){
        return valorServico;
    }

    public Tipo getTipoServico(){
        return tipoServico;
    }

    public String getDescricaoServico(){
        return descricaoServico;
    }

    public String getNomeProduto(){
        return nomeProduto;
    }

    public BigDecimal getPrecoPedidoProduto(){
        return precoPedidoProduto;
    }

    public Integer getQuantidadePedidoProduto(){
        return quantidadePedidoProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public void setPrecoPedidoProduto(BigDecimal precoPedidoProduto) {
        this.precoPedidoProduto = precoPedidoProduto;
    }

    public void setQuantidadePedidoProduto(Integer quantidadePedidoProduto) {
        this.quantidadePedidoProduto = quantidadePedidoProduto;
    }
}
