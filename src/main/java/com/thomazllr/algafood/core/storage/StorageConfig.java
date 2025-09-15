package com.thomazllr.algafood.core.storage;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.thomazllr.algafood.domain.service.FotoStorageService;
import com.thomazllr.algafood.infrastructure.service.LocalFotoStorageService;
import com.thomazllr.algafood.infrastructure.service.S3FotoStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.thomazllr.algafood.core.storage.StorageProperties.*;

@Configuration
@RequiredArgsConstructor
public class StorageConfig {

    private final StorageProperties storageProperties;

    @Bean
    public AmazonS3 amazonS3() {

        var chaveAcesso = storageProperties.getS3().getIdChaveAcesso();
        var chaveAcessoSecreta = storageProperties.getS3().getChaveAcessoSecreta();
        var regiao = storageProperties.getS3().getRegiao();

        var credentials = new BasicAWSCredentials(chaveAcesso, chaveAcessoSecreta);
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(regiao)
                .build();
    }

    @Bean
    public FotoStorageService fotoStorageService() {
        if(TipoStorage.S3.equals(storageProperties.getTipo())) {
            return new S3FotoStorageService();
        }
        else {
            return new LocalFotoStorageService();
        }
    }
}
