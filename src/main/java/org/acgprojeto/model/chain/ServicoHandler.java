package org.acgprojeto.model.chain;

import org.acgprojeto.dto.PedidoDTO;
import org.acgprojeto.dto.ServicoDTO;

public abstract class ServicoHandler {

    protected ServicoHandler nextHandler;

    public ServicoDTO handle(ServicoDTO servicoDTO) {
        if (nextHandler != null) {
            return nextHandler.handle(servicoDTO);
        }
        return servicoDTO;
    }

    public ServicoHandler setNextHandler(ServicoHandler nextHandler) {
        this.nextHandler = nextHandler;
        return nextHandler;
    }

}
