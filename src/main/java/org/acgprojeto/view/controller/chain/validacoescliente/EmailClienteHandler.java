package org.acgprojeto.view.controller.chain.validacoescliente;

import org.acgprojeto.dto.ClienteDTO;
import org.acgprojeto.view.controller.chain.ClienteHandler;
import org.acgprojeto.view.controller.chain.exceptions.ValidacaoException;

public class EmailClienteHandler extends ClienteHandler {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    @Override
    public ClienteDTO handle(ClienteDTO clienteDTO)  {
        if (clienteDTO.getEmail() == null || clienteDTO.getEmail().isEmpty()) {
            return super.handle(clienteDTO);
        }
        if(!clienteDTO.getEmail().matches(EMAIL_REGEX)){
            throw new ValidacaoException("Erro ao inserir email");
        }
        return super.handle(clienteDTO);
        }
    }

