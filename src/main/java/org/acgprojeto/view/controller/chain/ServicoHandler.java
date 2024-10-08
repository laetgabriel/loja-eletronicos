package org.acgprojeto.view.controller.chain;

import org.acgprojeto.dto.ServicoDTO;

public abstract class ServicoHandler {

    protected ServicoHandler nextHandler;

    public ServicoDTO handle(ServicoDTO servicoDTO) {
        if (nextHandler != null) {
            return nextHandler.handle(servicoDTO);
        }
        return servicoDTO;
    }

    public void setNextHandler(ServicoHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

}
