package org.acgprojeto.model.chain.validacoes;

import javafx.scene.control.Alert;
import org.acgprojeto.dto.ServicoDTO;
import org.acgprojeto.model.chain.ServicoHandler;
import org.acgprojeto.model.chain.exceptions.ValidacaoException;
import org.acgprojeto.util.Alertas;

import java.math.BigDecimal;

public class PrecoServicoHandler extends ServicoHandler {

    @Override
    public ServicoDTO handle(ServicoDTO servicoDTO) {
        if(servicoDTO.getPreco() == null || servicoDTO.getPreco().compareTo(BigDecimal.ZERO) <= 0){
            Alertas.mostrarAlerta("Erro", "Erro no preço do serviço", Alert.AlertType.ERROR);
            throw new ValidacaoException("Erro ao inserir preço do serviço");
        }else
            return super.handle(servicoDTO);
    }
}
