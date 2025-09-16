package com.thomazllr.algafood.core.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "algafood.email")
public class EmailProperties {

    private String remetente;
    private Implementacao impl = Implementacao.FAKE;
    private String sandboxDestinatario;

    enum Implementacao {
        FAKE, SMTP, SANDBOX
    }
}
