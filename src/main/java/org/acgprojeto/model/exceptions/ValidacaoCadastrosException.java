package org.acgprojeto.model.exceptions;

import java.util.HashMap;
import java.util.Map;

public class ValidacaoCadastrosException extends RuntimeException {

    private static final long serialVersionUID = 1L;


    private Map<String, String> errors = new HashMap<String, String>();

    public Map<String, String> getErrors() {
        return errors;
    }

    public void addErro(String campo, String mensagem) {
        errors.put(campo, mensagem);
    }

}
