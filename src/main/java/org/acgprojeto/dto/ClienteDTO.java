package org.acgprojeto.dto;

import org.acgprojeto.model.entidades.Cliente;

import java.util.Objects;

public class ClienteDTO {

    private Cliente cliente;
    private String nome;
    private String email;
    private String telefone;


    public Cliente getCliente() {
        return cliente;
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

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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
                "idCliente=" + cliente +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClienteDTO cliente = (ClienteDTO) o;
        return Objects.equals(cliente, cliente.cliente) && Objects.equals(nome, cliente.nome) && Objects.equals(email, cliente.email) && Objects.equals(telefone, cliente.telefone);
    }

}
