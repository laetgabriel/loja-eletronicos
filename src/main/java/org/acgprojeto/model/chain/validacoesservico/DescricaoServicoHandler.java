package org.acgprojeto.model.chain.validacoesservico;

import org.acgprojeto.dto.ServicoDTO;
import org.acgprojeto.model.chain.ServicoHandler;
import org.acgprojeto.model.chain.exceptions.ValidacaoException;

public class DescricaoServicoHandler extends ServicoHandler {

    @Override
    public ServicoDTO handle(ServicoDTO servicoDTO) {
        if(servicoDTO.getDescricao() == null || servicoDTO.getDescricao().isEmpty()){
            throw new ValidacaoException("Erro ao inserir descrição do serviço");
        }else
            return super.handle(servicoDTO);
    }
}
