package org.acgprojeto.model.chain;

import org.acgprojeto.dto.ClienteDTO;

public abstract class ClienteHandler {

    protected ClienteHandler nextHandler;

    public ClienteDTO handle(ClienteDTO clienteDTO) {
        if (nextHandler != null) {
            return nextHandler.handle(clienteDTO);
        }
        return clienteDTO;
    }

    public ClienteHandler setNextHandler(ClienteHandler nextHandler) {
        this.nextHandler = nextHandler;
        return nextHandler;
    }
}
