package org.acgprojeto.model.chain.validacoes;

import javafx.scene.control.Alert;
import org.acgprojeto.dto.ServicoDTO;
import org.acgprojeto.model.chain.ServicoHandler;
import org.acgprojeto.util.Alertas;

public class DescricaoServicoHandler extends ServicoHandler {

    @Override
    public ServicoDTO handle(ServicoDTO servicoDTO) {
        if(servicoDTO.getDescricao() == null || servicoDTO.getDescricao().isEmpty()){
            Alertas.mostrarAlerta("Erro", "Erro na descrição do serviço", Alert.AlertType.ERROR);
            return servicoDTO;
        }else
            return super.handle(servicoDTO);
    }
}
