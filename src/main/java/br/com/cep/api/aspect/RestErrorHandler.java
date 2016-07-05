package br.com.cep.api.aspect;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.cep.api.exception.AddressNotFoundException;

/**
 * The type Rest error handler.
 * @author Diego Costa (diegotonzi@gmail.com)
 */
@ControllerAdvice
public class RestErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Message> handlerValidationException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        Message message = new Message(MessageType.Parameter_Error, "Validation error!");
        for (FieldError fieldError : result.getFieldErrors()) {
            message.addNotification(fieldError.getDefaultMessage());
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=utf-8");

        return new ResponseEntity<>(message, responseHeaders, HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings("rawtypes")
	@ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Message> handlerValidationException(ConstraintViolationException ex) {
        Message message = new Message(MessageType.Parameter_Error, "Validation error!");
        for (ConstraintViolation violation : ex.getConstraintViolations()) {
            message.addNotification(violation.getMessage());
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=utf-8");

        return new ResponseEntity<>(message, responseHeaders, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<Message> handlerAddressNotFoundException(AddressNotFoundException ex) {
        Message message = new Message(MessageType.Business_Logic_Error, ex.getMessage());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=utf-8");
        return new ResponseEntity<>(message, responseHeaders, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<Message> handlerAddressNotFoundException(Exception ex) {
        Message message = new Message(MessageType.Internal_Architecture_Error, MessageType.Internal_Architecture_Error.getDescription());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=utf-8");
        return new ResponseEntity<>(message, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}