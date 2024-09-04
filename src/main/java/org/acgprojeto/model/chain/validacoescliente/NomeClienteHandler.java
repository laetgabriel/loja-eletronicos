package org.acgprojeto.model.chain.validacoescliente;

import javafx.scene.control.Alert;
import org.acgprojeto.dto.ClienteDTO;
import org.acgprojeto.model.chain.ClienteHandler;
import org.acgprojeto.model.chain.exceptions.ValidacaoException;
import org.acgprojeto.util.Alertas;

public class NomeClienteHandler extends ClienteHandler {

    @Override
    public ClienteDTO handle(ClienteDTO clienteDTO) {
        if(clienteDTO == null || clienteDTO.getNome() == null || clienteDTO.getNome().isEmpty()){
            throw new ValidacaoException("Erro ao inserir nome do cliente");
        }else
            return super.handle(clienteDTO);
    }
}
