package org.acgprojeto.view.controller.chain.validacoescliente;

import org.acgprojeto.dto.ClienteDTO;
import org.acgprojeto.view.controller.chain.ClienteHandler;
import org.acgprojeto.view.controller.chain.exceptions.ValidacaoException;

public class TelefoneClienteHandler extends ClienteHandler {

    @Override
    public ClienteDTO handle(ClienteDTO clienteDTO) {
        if (clienteDTO.getTelefone() == null || clienteDTO.getTelefone().isEmpty()) {
            return super.handle(clienteDTO);
        }
        if(clienteDTO.getTelefone().length() < 8){
            throw new ValidacaoException("Erro ao inserir telefone");
        }
        if(clienteDTO.getTelefone().length() == 9){
            return clienteDTO;
        }else
            return super.handle(clienteDTO);
    }

}
