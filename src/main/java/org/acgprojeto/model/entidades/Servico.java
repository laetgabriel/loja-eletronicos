package org.acgprojeto.model.entidades;

import org.acgprojeto.dto.PedidoDTO;
import org.acgprojeto.dto.ServicoDTO;
import org.acgprojeto.model.enums.Tipo;

import java.math.BigDecimal;

public class Servico {

    private Integer idServico;
    private String descricao;
    private BigDecimal preco;
    private Tipo tipo;

    public Servico() {}

    public Servico(ServicoDTO servicoDTO) {
        this.idServico = servicoDTO.getIdServico();
        this.descricao = servicoDTO.getDescricao();
        this.preco = servicoDTO.getPreco();
        this.tipo = servicoDTO.getTipo();
    }

    public Integer getIdServico() {
        return idServico;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setIdServico(Integer idServico) {
        this.idServico = idServico;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
}
