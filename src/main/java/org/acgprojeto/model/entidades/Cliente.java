package org.acgprojeto.model.entidades;

import org.acgprojeto.dto.ClienteDTO;

import java.util.Objects;

public class Cliente {

    private Integer idCliente;
    private String nome;
    private String email;
    private String telefone;

    public Cliente(){}

    public Cliente(ClienteDTO clienteDTO) {
        idCliente = clienteDTO.getIdCliente();
        nome = clienteDTO.getNome();
        email = clienteDTO.getEmail();
        telefone = clienteDTO.getTelefone();
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "idCliente=" + idCliente +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(cliente, cliente.idCliente) && Objects.equals(nome, cliente.nome) && Objects.equals(email, cliente.email) && Objects.equals(telefone, cliente.telefone);
    }

}
