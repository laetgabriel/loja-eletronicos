package org.acgprojeto.view.controller.chain.validacoescliente;

import org.acgprojeto.dto.ClienteDTO;
import org.acgprojeto.view.controller.chain.ClienteHandler;
import org.acgprojeto.view.controller.chain.exceptions.ValidacaoException;

public class NomeClienteHandler extends ClienteHandler {

    @Override
    public ClienteDTO handle(ClienteDTO clienteDTO) {
        if(clienteDTO == null || clienteDTO.getNome() == null || clienteDTO.getNome().isEmpty()){
            throw new ValidacaoException("Erro ao inserir nome do cliente");
        }else
            return super.handle(clienteDTO);
    }
}
