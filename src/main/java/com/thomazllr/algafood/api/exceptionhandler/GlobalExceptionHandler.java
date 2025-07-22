package com.thomazllr.algafood.api.exceptionhandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.thomazllr.algafood.domain.exception.EntidadeEmUsoException;
import com.thomazllr.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.thomazllr.algafood.domain.exception.NegocioException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String MSG_GENERICA_ERROR_INTERNO = "Ocorreu um erro inesperado no sistema. " +
                                                            "Tente novamente mais tarde ou contate o administrador do sistema.";

    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorType errorType = ErrorType.ERROR_DE_SISTEMA;

        ex.printStackTrace();

        var error = criarErro(errorType, status, MSG_GENERICA_ERROR_INTERNO).build();

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }


    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<Object> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorType errorType = ErrorType.RECURSO_NAO_ENCONTRADO;
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

        Error error = criarErro(errorType, (HttpStatus) status, detail)
                .userMessage(MSG_GENERICA_ERROR_INTERNO)
                .build();

        return handleExceptionInternal(ex, error, headers, status, request);

    }

    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorType errorType = ErrorType.RECURSO_NAO_ENCONTRADO;

        String path = ex.getResourcePath();
        var detail = String.format("Recurso %s não encontrado. Verifique o caminho e tente novamente.", path);

        var error = criarErro(errorType, (HttpStatus) status, detail)
                .userMessage(MSG_GENERICA_ERROR_INTERNO)
                .build();

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders
            headers, HttpStatusCode status, WebRequest request) {

        var path = ex.getPath().stream()
                .map(ref -> ref.getFieldName()).collect(Collectors.joining("."));

        ErrorType errorType = ErrorType.MENSAGEM_INCOMPREENSIVEL;
        String detail = String.format("A propriedade '%s' não existe. Corrija ou remova essa propriedade e tente novamente.", path);

        Error error = criarErro(errorType, (HttpStatus) status, detail)
                .userMessage(detail)
                .build();

        return handleExceptionInternal(ex, error, headers, status, request);

    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Object> handleNegocioException(NegocioException ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorType errorType = ErrorType.ERRO_DE_NEGOCIO;
        var detail = ex.getMessage();

        var error = criarErro(errorType, status, detail)
                .userMessage(MSG_GENERICA_ERROR_INTERNO)
                .build();

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<Object> handleEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;
        ErrorType errorType = ErrorType.ENTIDADE_EM_USO;
        var detail = ex.getMessage();

        var error = criarErro(errorType, status, detail)
                .userMessage(detail)
                .build();

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
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ErrorType errorType = ErrorType.DADOS_INVALIDOS;

        var detail = "Um ou mais campos estão inválidos. Corrija e informe os valores corretos e tente novamente.";

        var bindingResult = ex.getBindingResult();

        List<Error.FieldError> fieldErrors = bindingResult.getFieldErrors().stream()
                .map(fieldError -> {

                    String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());

                    return Error.FieldError.builder()
                            .name(fieldError.getField())
                            .message(message)
                            .build();
                }).toList();

        var error = criarErro(errorType, (HttpStatus) status, detail)
                .userMessage(detail)
                .fields(fieldErrors)
                .build();

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
                    .timestamp(LocalDateTime.now())
                    .userMessage(MSG_GENERICA_ERROR_INTERNO)
                    .build();
        } else if (body instanceof String corpo) {
            body = Error.builder()
                    .title(corpo)
                    .timestamp(LocalDateTime.now())
                    .userMessage(MSG_GENERICA_ERROR_INTERNO)
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    public Error.ErrorBuilder criarErro(ErrorType errorType, HttpStatus status, String detail) {
        return Error.builder()
                .title(errorType.getTitle())
                .type(errorType.getUri())
                .detail(detail)
                .status(status.value())
                .timestamp(LocalDateTime.now());
    }

}
