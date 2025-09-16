package com.thomazllr.algafood.infrastructure.service.email;

import com.thomazllr.algafood.core.email.EmailProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;

@Slf4j
public class SandBoxEmailService extends SmtpEmailService {

    @Autowired
    private EmailProperties emailProperties;

    @Override
    public void enviar(Mensagem mensagem) {
        var destinatarios = new HashSet<String>();
        destinatarios.add(emailProperties.getSandboxDestinatario());
        mensagem.setDestinatarios(destinatarios);
        log.info("[SANDBOX E-MAIL] Para: {}\n{}", mensagem.getDestinatarios(), mensagem.getCorpo());
        super.enviar(mensagem);
    }
}
