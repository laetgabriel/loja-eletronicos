package org.acgprojeto.model.chain.validacoescliente;

import org.acgprojeto.dto.ClienteDTO;
import org.acgprojeto.model.chain.ClienteHandler;
import org.acgprojeto.model.chain.ServicoHandler;

public class ClienteValidator {


    private ClienteHandler firstHandler;

    public ClienteValidator() {
        ClienteHandler nomeClienteHandler = new NomeClienteHandler();
        ClienteHandler telefoneClienteHandler = new TelefoneClienteHandler();
        ClienteHandler emailClienteHandler = new EmailClienteHandler();

        nomeClienteHandler
                .setNextHandler(telefoneClienteHandler)
                .setNextHandler(emailClienteHandler);

        this.firstHandler = nomeClienteHandler;
    }

    public void validarCliente(ClienteDTO cliente) {
        if (firstHandler != null) {
            firstHandler.handle(cliente);
        }
    }
}
