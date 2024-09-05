package org.acgprojeto.view.controller.chain.validacoesservico;

import org.acgprojeto.dto.ServicoDTO;
import org.acgprojeto.view.controller.chain.ServicoHandler;


public class ServicoValidator {

    private ServicoHandler firstHandler;

    public ServicoValidator(){
        ServicoHandler descricaoServicoHandler = new DescricaoServicoHandler();
        ServicoHandler PrecoServicoHandler = new PrecoServicoHandler();

        descricaoServicoHandler.setNextHandler(PrecoServicoHandler);
        this.firstHandler = descricaoServicoHandler;
    }

    public void validateServico(ServicoDTO servicoDTO) {
        if (firstHandler != null) {
            firstHandler.handle(servicoDTO);
        }
    }
}
