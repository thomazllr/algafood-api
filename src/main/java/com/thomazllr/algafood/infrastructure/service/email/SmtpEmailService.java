package com.thomazllr.algafood.infrastructure.service.email;

import com.thomazllr.algafood.core.email.EmailProperties;
import com.thomazllr.algafood.domain.service.EnvioEmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

public class SmtpEmailService implements EnvioEmailService {

    @Autowired
    private EmailProperties emailProperties;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Configuration freeMakerConfig;

    @Override
    public void enviar(Mensagem mensagem) {
        try {

            String corpo = processarTemplate(mensagem);
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setFrom(emailProperties.getRemetente());
            helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
            helper.setSubject(mensagem.getAssunto());
            helper.setText(corpo, true);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailException("Não foi possível enviar o e-mail." + e);
        }

    }

    protected String processarTemplate(Mensagem mensagem) {
        try {
            Template template = freeMakerConfig.getTemplate(mensagem.getCorpo());
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, mensagem.getVariaveis());
        } catch (Exception e) {
            throw new EmailException("Não foi possível processar o template para enviar o e-mail." + e);
        }
    }
}
