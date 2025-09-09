package com.thomazllr.algafood.core.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class FileContentValidator implements ConstraintValidator<FileContentType, MultipartFile> {

    private Set<String> allowed;

    @Override
    public void initialize(FileContentType constraintAnnotation) {
        this.allowed = Arrays.stream(constraintAnnotation.allowed())
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toSet());
    }


    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {

        if (multipartFile == null || multipartFile.isEmpty()) {
            return true;
        }

        String contentType = multipartFile.getContentType();
        if (contentType == null) {
            return false;
        }

        return allowed.stream().anyMatch(a -> a.equalsIgnoreCase(contentType));
    }
}
