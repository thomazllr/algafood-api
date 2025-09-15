package com.thomazllr.algafood.infrastructure.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.thomazllr.algafood.core.storage.StorageProperties;
import com.thomazllr.algafood.domain.exception.StorageException;
import com.thomazllr.algafood.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;

public class S3FotoStorageService implements FotoStorageService {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private StorageProperties properties;

    @Override
    public void armazenar(NovaFoto foto) {

        try {
            var bucket = properties.getS3().getBucket();
            String caminhoArquivo = getCaminhoArquivo(foto.getNomeArquivo());
            var objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(foto.getContentType());

            var putObjectRequest = new PutObjectRequest(bucket, caminhoArquivo, foto.getInputStream(), objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);

            amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Erro ao armazenar a foto no S3", e);
        }
    }


    @Override
    public void remover(String nomeArquivo) {
        try {
            var bucket = properties.getS3().getBucket();
            String caminhoArquivo = getCaminhoArquivo(nomeArquivo);

            var putObjectRequest = new DeleteObjectRequest(bucket, caminhoArquivo);

            amazonS3.deleteObject(putObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Erro ao remover a foto no S3", e);
        }

    }

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        String caminhoArquivo = getCaminhoArquivo(nomeArquivo);
        URL url = amazonS3.getUrl(properties.getS3().getBucket(), caminhoArquivo);
        return FotoRecuperada.builder()
                .url(url.toString())
                .build();
    }

    private String getCaminhoArquivo(String nomeArquivo) {
        return String.format("%s/%s", properties.getS3().getDiretorioFotos(), nomeArquivo);
    }


}
