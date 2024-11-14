package de.comparus.testtask.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.joining;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    private static final DateTimeFormatter FORMAT_HTTP = DateTimeFormatter.ofPattern("yyyy'-'MM'-'dd'T'HH':'mm':'ss");

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ValidationMessage> validationExceptionHandler2(HttpServletRequest request,
                                                                         ConstraintViolationException e) {
        var violations = e.getConstraintViolations();
        var errors = violations.stream()
                .map(violation -> new ValidationParamError(getParamName(violation), violation.getMessage()))
                .toList();
        var validationMessage = createMessage(request, errors);

        log.error("Validation error = '{}', path = '{}', errors: \n{}",
                e.getClass().getSimpleName(),
                request.getServletPath(),
                errors.stream()
                        .map(ValidationParamError::toString)
                        .collect(joining(",\n")));
        return ResponseEntity.badRequest().body(validationMessage);
    }

    private String getParamName(ConstraintViolation<?> constraintViolation) {
        return StreamSupport.stream(constraintViolation.getPropertyPath().spliterator(), false)
                .reduce((first, second) -> second)
                .map(Path.Node::getName)
                .orElseThrow();
    }

    public record ValidationMessage(
            String timestamp,
            int status,
            String error,
            List<ValidationParamError> messages,
            String path
    ) {
    }

    private ValidationMessage createMessage(HttpServletRequest request, List<ValidationParamError> errors) {
        return new ValidationMessage(
                LocalDateTime.now().format(FORMAT_HTTP),
                BAD_REQUEST.value(),
                BAD_REQUEST.getReasonPhrase(),
                errors,
                request.getServletPath());
    }

    private record ValidationParamError(
            String param,
            String message
    ) {
    }
}

