package com.thomazllr.algafood.api.exceptionhandler;

import com.thomazllr.algafood.domain.exception.EntidadeEmUsoException;
import com.thomazllr.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.thomazllr.algafood.domain.exception.NegocioException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorType errorType = ErrorType.ENTIDADE_NAO_ENCONTRADA;
        var detail = ex.getMessage();

        var error = criarErro(errorType, status, detail).build();

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorType errorType = ErrorType.ERRO_DE_NEGOCIO;
        var detail = ex.getMessage();

        var error = criarErro(errorType, status, detail).build();

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;
        ErrorType errorType = ErrorType.ENTIDADE_EM_USO;
        var detail = ex.getMessage();

        var error = criarErro(errorType, status, detail).build();

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, Object body, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {

        String mensagem;
        if (status instanceof HttpStatus httpStatus) {
            mensagem = httpStatus.getReasonPhrase();
        } else {
            mensagem = status.toString();
        }

        if (body == null) {
            body = Error.builder()
                    .title(mensagem)
                    .status(status.value())
                    .build();
        } else if (body instanceof String) {
            body = Error.builder()
                    .title((String) body)
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    public Error.ErrorBuilder criarErro(ErrorType errorType, HttpStatus status, String detail) {
        return Error.builder()
                .title(errorType.getTitle())
                .type(errorType.getUri())
                .detail(detail)
                .status(status.value());
    }

}
