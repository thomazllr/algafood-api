package com.thomazllr.algafood.core.storage;

import com.thomazllr.algafood.domain.service.FotoStorageService;
import com.thomazllr.algafood.infrastructure.service.storage.LocalFotoStorageService;
import com.thomazllr.algafood.infrastructure.service.storage.S3FotoStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@RequiredArgsConstructor
public class StorageConfig {

    private final StorageProperties storageProperties;

    @Bean
    public S3Client s3Client() {
        var chaveAcesso = storageProperties.getS3().getIdChaveAcesso();
        var chaveAcessoSecreta = storageProperties.getS3().getChaveAcessoSecreta();
        var regiao = storageProperties.getS3().getRegiao();

        var credentials = AwsBasicCredentials.create(chaveAcesso, chaveAcessoSecreta);

        return S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(regiao)
                .build();
    }

    @Bean
    public FotoStorageService fotoStorageService() {
        return switch (storageProperties.getTipo()) {
            case S3 -> new S3FotoStorageService();
            case LOCAL -> new LocalFotoStorageService();
            default -> null;
        };
    }
}