package com.example.booking.controller;

import com.example.booking.dto.ErrorResponse;
import com.example.booking.exception.RequestParameterException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;
import java.util.List;

@RestControllerAdvice
public class ExceptionHandlerController {

    @Operation(summary = "Data not found")
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFound(EntityNotFoundException ex) {
        return new ErrorResponse(ex.getLocalizedMessage());
    }

    @Operation(summary = "Not valid parameter")
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse notValid(MethodArgumentNotValidException ex){
        BindingResult bindingResult = ex.getBindingResult();
        List<String> errorMessages = bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        String errorMessage = String.join("; ", errorMessages);
        return new ErrorResponse(errorMessage);
    }

    @Operation(summary = "Wrong input parameter")
    @ExceptionHandler(RequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse wrongParameter(RequestParameterException ex) {
        return new ErrorResponse(ex.getLocalizedMessage());
    }

    @Operation(summary = "Wrong date format of request parameter")
    @ExceptionHandler(DateTimeParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse wrongDateFormat() {
        return new ErrorResponse("Дату необходимо ввести в формате: dd/MM/yyyy");
    }
}
