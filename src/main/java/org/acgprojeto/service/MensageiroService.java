package org.acgprojeto.service;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class MensageiroService extends SimpleEmail {

    public MensageiroService() {
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
    public void enviarEmail(String destinatario, String titulo, String conteudo){
        try {
            setSubject(titulo);
            setMsg(conteudo);
            addTo(destinatario);
            send();
        } catch (EmailException e) {
            throw new RuntimeException(e);
        }
    }


}
