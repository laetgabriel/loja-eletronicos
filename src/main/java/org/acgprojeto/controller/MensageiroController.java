package org.acgprojeto.controller;

import org.acgprojeto.service.MensageiroService;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;

public class MensageiroController {

    private MensageiroService mensageiroService;

    public MensageiroController() {
        mensageiroService = new MensageiroService();
    }

    public void enviarEmail(String destinatario, String titulo, String conteudo) throws EmailException {
        mensageiroService.enviarEmail(destinatario, titulo, conteudo);
    }
}
