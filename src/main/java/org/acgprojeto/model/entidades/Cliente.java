package org.acgprojeto.model.entidades;

import java.util.Objects;

public class Cliente {

    private Cliente cliente;
    private String nome;
    private String email;
    private String telefone;

    public Cliente() {}

    public Cliente getIdCliente() {
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

    public void setIdCliente(Cliente cliente) {
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
        Cliente cliente = (Cliente) o;
        return Objects.equals(cliente, cliente.cliente) && Objects.equals(nome, cliente.nome) && Objects.equals(email, cliente.email) && Objects.equals(telefone, cliente.telefone);
    }

}
