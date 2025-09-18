package com.thomazllr.algafood.infrastructure.service.storage;

import com.thomazllr.algafood.core.storage.StorageProperties;
import com.thomazllr.algafood.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

public class S3FotoStorageService implements FotoStorageService {

    @Autowired
    private S3Client s3Client;

    @Autowired
    private StorageProperties properties;

    @Override
    public void armazenar(NovaFoto foto) {
        try {
            var bucket = properties.getS3().getBucket();
            String caminhoArquivo = getCaminhoArquivo(foto.getNomeArquivo());

            var putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(caminhoArquivo)
                    .contentType(foto.getContentType())
                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .build();

            s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(foto.getInputStream(), foto.getSize()));

        } catch (Exception e) {
            throw new StorageException("Erro ao armazenar a foto no S3", e);
        }
    }

    @Override
    public void remover(String nomeArquivo) {
        try {
            var bucket = properties.getS3().getBucket();
            String caminhoArquivo = getCaminhoArquivo(nomeArquivo);

            var deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(caminhoArquivo)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);

        } catch (Exception e) {
            throw new StorageException("Erro ao remover a foto no S3", e);
        }
    }

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        String caminhoArquivo = getCaminhoArquivo(nomeArquivo);

        var getUrlRequest = GetUrlRequest.builder()
                .bucket(properties.getS3().getBucket())
                .key(caminhoArquivo)
                .build();

        var url = s3Client.utilities().getUrl(getUrlRequest);

        return FotoRecuperada.builder()
                .url(url.toString())
                .build();
    }

    private String getCaminhoArquivo(String nomeArquivo) {
        return String.format("%s/%s", properties.getS3().getDiretorioFotos(), nomeArquivo);
    }
}