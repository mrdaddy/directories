package com.rw.directories.controllers;

import com.rw.directories.dto.ErrorMessage;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;
import java.net.ConnectException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApiResponses(value = {
        @ApiResponse(code = 503, message = "Service Unavailable", response = ErrorMessage.class, responseContainer = "List")
})
public class BaseController {
    public enum ERROR_PREFIX {validation, system, express}

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleInvalidRequest(ConstraintViolationException e, WebRequest request) {
        List<ErrorMessage> errors = new ArrayList<>();
        ErrorMessage errorMessage = new ErrorMessage(ERROR_PREFIX.validation+".violation_error",e.getLocalizedMessage());
        errors.add(errorMessage);
        return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleInvalidRequest(MethodArgumentNotValidException e, WebRequest request) {
        List<ErrorMessage> errors = new ArrayList<>();
        if(e.getBindingResult().hasErrors()) {
            for(ObjectError oe: e.getBindingResult().getAllErrors()) {
                ErrorMessage errorMessage = new ErrorMessage(ERROR_PREFIX.validation+"."+oe.getCodes()[0],oe.getDefaultMessage());
                errors.add(errorMessage);
            }
        }
        return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ConnectException.class)
    protected ResponseEntity<?> handleConnectException(ConnectException e) {
        List<ErrorMessage> errors = new ArrayList<>();
        errors.add(new ErrorMessage(ERROR_PREFIX.system+".database_error", e.getMessage()));
        return new ResponseEntity(errors, HttpStatus.GATEWAY_TIMEOUT);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    protected ResponseEntity<?> handleDataAccessException(EmptyResultDataAccessException e) {
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SQLException.class)
    protected ResponseEntity<?> handleSQLException(SQLException e) {
        List<ErrorMessage> errors = new ArrayList<>();
        errors.add(new ErrorMessage(ERROR_PREFIX.system + ".database_error", e.getMessage()));
        return new ResponseEntity(errors, HttpStatus.SERVICE_UNAVAILABLE);
    }


}
