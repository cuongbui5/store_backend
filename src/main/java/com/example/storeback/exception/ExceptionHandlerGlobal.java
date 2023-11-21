package com.example.storeback.exception;



import com.auth0.jwt.exceptions.JWTDecodeException;
import com.example.storeback.dto.response.BaseResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlerGlobal {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handlerRuntimeException(RuntimeException e){
        System.out.println("HEllo");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new BaseResponse("fail",e.getMessage())
        );
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handlerDataIntegrityViolationException(DataIntegrityViolationException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new BaseResponse("fail",e.getMessage())
        );
    }
    @ExceptionHandler(JWTDecodeException.class)
    public ResponseEntity<?> handlerJWTDecodeException(JWTDecodeException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new BaseResponse("fail",e.getMessage())
        );
    }



    @ExceptionHandler(BindException.class)
    public ResponseEntity<BaseResponse> handleBindException(
            BindException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BaseResponse("fail",errors.toString()));
    }

    @ExceptionHandler(NotFound.class)
    public ResponseEntity<?> handlerNotFoundException(NotFound e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new BaseResponse("fail",e.getMessage())
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e){
        String errorMessage = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));

        // Extract specific error message, assuming it's in the format "[Error in object 'fieldName': codes [code1,code2]; arguments [arg1,arg2]; default message [message]]"
        String specificErrorMessage = errorMessage.replaceAll(".*default message \\[([^]]*)\\].*", "$1");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new BaseResponse("fail", specificErrorMessage)
        );
    }
    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<?> handlerAlreadyExistException(AlreadyExistException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new BaseResponse("fail", e.getMessage())
        );
    }
    @ExceptionHandler(WrongPassword.class)
    public ResponseEntity<?> handlerWrongPassword(WrongPassword e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new BaseResponse("fail", e.getMessage())
        );
    }
    @ExceptionHandler(WrongUsernameOrEmail.class)
    public ResponseEntity<?> handlerWrongUsernameOrEmail(WrongUsernameOrEmail e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new BaseResponse("fail", e.getMessage())
        );
    }



}
