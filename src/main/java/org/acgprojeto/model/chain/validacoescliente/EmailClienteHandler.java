package org.acgprojeto.model.chain.validacoescliente;

import javafx.scene.control.Alert;
import org.acgprojeto.dto.ClienteDTO;
import org.acgprojeto.dto.ServicoDTO;
import org.acgprojeto.model.chain.ClienteHandler;
import org.acgprojeto.model.chain.ServicoHandler;
import org.acgprojeto.model.chain.exceptions.ValidacaoException;
import org.acgprojeto.util.Alertas;
import org.acgprojeto.util.Restricoes;

public class EmailClienteHandler extends ClienteHandler {


    @Override
    public ClienteDTO handle(ClienteDTO clienteDTO)  {
        if (clienteDTO.getEmail() == null || clienteDTO.getEmail().isEmpty()) {
            return super.handle(clienteDTO);
        }
        if(Restricoes.validarEmail(clienteDTO.getEmail())) {
            throw new ValidacaoException("Erro ao inserir e-mail");
        }
        return super.handle(clienteDTO);
        }
    }

