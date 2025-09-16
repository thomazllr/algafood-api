package com.thomazllr.algafood.core.email;

import com.thomazllr.algafood.domain.service.EnvioEmailService;
import com.thomazllr.algafood.infrastructure.service.email.MockEmailService;
import com.thomazllr.algafood.infrastructure.service.email.SandBoxEmailService;
import com.thomazllr.algafood.infrastructure.service.email.SmtpEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class EmailConfig {

    private final EmailProperties emailProperties;

    @Bean
    public EnvioEmailService envioEmailService() {
        return switch (emailProperties.getImpl()) {
            case FAKE -> new MockEmailService();
            case SMTP -> new SmtpEmailService();
            case SANDBOX -> new SandBoxEmailService();
            default -> null;
        };
    }
}
