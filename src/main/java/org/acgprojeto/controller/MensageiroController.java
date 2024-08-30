package org.acgprojeto.controller;

import org.acgprojeto.dto.MensagemDTO;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class MensageiroController extends SimpleEmail {

    public MensageiroController() {
        setHostName("smtp.gmail.com");
        setSmtpPort(587);
        setAuthenticator(new DefaultAuthenticator("cybertigre85@gmail.com", "znvb phtq qjxw vraz"));
        setSSLOnConnect(true);
        try {
            setFrom("cybertigre85@gmail.com");
        } catch (EmailException e) {
            throw new RuntimeException(e);
        }
    }
    public void enviarEmail(MensagemDTO mensagem) throws EmailException {
        setSubject(mensagem.titulo());
        setMsg(mensagem.conteudo());
        addTo(mensagem.destinatario());
        send();
    }
}
