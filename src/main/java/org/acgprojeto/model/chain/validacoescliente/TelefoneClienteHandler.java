package org.acgprojeto.model.chain.validacoescliente;

import javafx.scene.control.Alert;
import org.acgprojeto.dto.ClienteDTO;
import org.acgprojeto.dto.ServicoDTO;
import org.acgprojeto.model.chain.ClienteHandler;
import org.acgprojeto.model.chain.ServicoHandler;
import org.acgprojeto.model.chain.exceptions.ValidacaoException;
import org.acgprojeto.util.Alertas;

public class TelefoneClienteHandler extends ClienteHandler {

    @Override
    public ClienteDTO handle(ClienteDTO clienteDTO) {
        if (clienteDTO.getTelefone() == null || clienteDTO.getTelefone().isEmpty()) {
            return super.handle(clienteDTO);
        }
        if(clienteDTO.getTelefone().length() == 9){
            return clienteDTO;
        }else
            return super.handle(clienteDTO);
    }

}
