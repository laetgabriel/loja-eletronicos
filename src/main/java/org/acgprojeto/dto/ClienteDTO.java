package org.acgprojeto.dto;


import org.acgprojeto.model.entities.Cliente;

public class ClienteDTO {

    private Integer idCliente;
    private String nome;
    private String email;
    private String telefone;

    public ClienteDTO() {}

    public ClienteDTO(Integer idCliente, String nome, String email, String telefone) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    public ClienteDTO(Cliente cliente){
        idCliente = cliente.getIdCliente();
        nome = cliente.getNome();
        email = cliente.getEmail();
        telefone = cliente.getTelefone();
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
        return nome;
    }

}