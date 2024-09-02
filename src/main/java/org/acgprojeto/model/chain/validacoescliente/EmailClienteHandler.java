package org.acgprojeto.model.chain.validacoescliente;

import javafx.scene.control.Alert;
import org.acgprojeto.dto.ClienteDTO;
import org.acgprojeto.dto.ServicoDTO;
import org.acgprojeto.model.chain.ClienteHandler;
import org.acgprojeto.model.chain.ServicoHandler;
import org.acgprojeto.util.Alertas;

public class EmailClienteHandler extends ClienteHandler {

    @Override
    public ClienteDTO handle(ClienteDTO clienteDTO) {
        if(clienteDTO.getEmail() == null || clienteDTO.getEmail().isEmpty()){
            Alertas.mostrarAlerta("Erro", "Erro no email do cliente", Alert.AlertType.ERROR);
            return clienteDTO;
        } else {
            return super.handle(clienteDTO);
        }
    }
}
