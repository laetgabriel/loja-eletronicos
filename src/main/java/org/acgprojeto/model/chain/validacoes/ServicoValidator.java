package org.acgprojeto.model.chain.validacoes;

import org.acgprojeto.dto.ServicoDTO;
import org.acgprojeto.model.chain.ServicoHandler;


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
