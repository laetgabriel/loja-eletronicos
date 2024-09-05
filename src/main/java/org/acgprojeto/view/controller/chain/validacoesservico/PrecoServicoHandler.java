package org.acgprojeto.view.controller.chain.validacoesservico;

import org.acgprojeto.dto.ServicoDTO;
import org.acgprojeto.view.controller.chain.ServicoHandler;
import org.acgprojeto.view.controller.chain.exceptions.ValidacaoException;

import java.math.BigDecimal;

public class PrecoServicoHandler extends ServicoHandler {

    @Override
    public ServicoDTO handle(ServicoDTO servicoDTO) {
        if(servicoDTO.getPreco() == null || servicoDTO.getPreco().compareTo(BigDecimal.ZERO) <= 0){
            throw new ValidacaoException("Erro ao inserir preço do serviço");
        }else
            return super.handle(servicoDTO);
    }
}
