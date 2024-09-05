package org.acgprojeto.view.controller.chain.validacoescliente;

import org.acgprojeto.dto.ClienteDTO;
import org.acgprojeto.view.controller.chain.ClienteHandler;

public class ClienteValidator {


    private final ClienteHandler firstHandler;

    public ClienteValidator() {
        ClienteHandler nomeClienteHandler = new NomeClienteHandler();
        ClienteHandler telefoneClienteHandler = new TelefoneClienteHandler();
        ClienteHandler emailClienteHandler = new EmailClienteHandler();

        nomeClienteHandler
                .setNextHandler(emailClienteHandler)
                .setNextHandler(telefoneClienteHandler);

        this.firstHandler = nomeClienteHandler;
    }

    public void validarCliente(ClienteDTO cliente) {
        if (firstHandler != null) {
            firstHandler.handle(cliente);
        }
    }

}
