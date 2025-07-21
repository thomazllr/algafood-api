package com.thomazllr.algafood.api.exceptionhandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.thomazllr.algafood.domain.exception.EntidadeEmUsoException;
import com.thomazllr.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.thomazllr.algafood.domain.exception.NegocioException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<Object> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorType errorType = ErrorType.ENTIDADE_NAO_ENCONTRADA;
        var detail = ex.getMessage();

        var error = criarErro(errorType, status, detail).build();

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders
            headers, HttpStatusCode status, WebRequest request) {

        var path = ex.getPath().stream()
                .map(ref -> ref.getFieldName()).collect(Collectors.joining("."));

        ErrorType errorType = ErrorType.MENSAGEM_INCOMPREENSIVEL;
        String detail = String.format("A propriedade '%s' recebeu um valor '%s' que é inválido. Corrija e informe o valor com o tipo %s.",
                path,
                ex.getValue(),
                ex.getTargetType().getSimpleName());

        Error error = criarErro(errorType, (HttpStatus) status, detail).build();

        return handleExceptionInternal(ex, error, headers, status, request);

    }

    private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders
            headers, HttpStatusCode status, WebRequest request) {

        var path = ex.getPath().stream()
                .map(ref -> ref.getFieldName()).collect(Collectors.joining("."));

        ErrorType errorType = ErrorType.MENSAGEM_INCOMPREENSIVEL;
        String detail = String.format("A propriedade '%s' não existe. Corrija ou remova essa propriedade e tente novamente.", path);

        Error error = criarErro(errorType, (HttpStatus) status, detail).build();

        return handleExceptionInternal(ex, error, headers, status, request);

    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Object> handleNegocioException(NegocioException ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorType errorType = ErrorType.ERRO_DE_NEGOCIO;
        var detail = ex.getMessage();

        var error = criarErro(errorType, status, detail).build();

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<Object> handleEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;
        ErrorType errorType = ErrorType.ENTIDADE_EM_USO;
        var detail = ex.getMessage();

        var error = criarErro(errorType, status, detail).build();

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders
            headers, HttpStatusCode status, WebRequest request) {

        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException invalidFormatException) {
            return handleInvalidFormatException(invalidFormatException, headers, status, request);
        }

        if (rootCause instanceof PropertyBindingException propertyBindingException) {
            return handlePropertyBindingException(propertyBindingException, headers, status, request);
        }


        ErrorType errorType = ErrorType.MENSAGEM_INCOMPREENSIVEL;
        var detail = "O corpo da requisição está inválido. Verifique a sintaxe.";

        var error = criarErro(errorType, (HttpStatus) status, detail).build();

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        if (ex instanceof MethodArgumentTypeMismatchException methodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatchException(methodArgumentTypeMismatchException, headers, status, request);
        }

        ErrorType errorType = ErrorType.PARAMETRO_INVALIDO;
        var detail = "Parâmetro na URL está errado. Corrija e tente novamente";

        var error = criarErro(errorType, (HttpStatus) status, detail).build();

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpHeaders
            headers, HttpStatusCode status, WebRequest request) {

        ErrorType errorType = ErrorType.PARAMETRO_INVALIDO;

        String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
                                      + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

        Error error = criarErro(errorType, (HttpStatus) status, detail).build();

        return handleExceptionInternal(ex, error, headers, status, request);

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
        } else if (body instanceof String corpo) {
            body = Error.builder()
                    .title(corpo)
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
