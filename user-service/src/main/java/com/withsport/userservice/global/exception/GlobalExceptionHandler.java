package com.withsport.userservice.global.exception;


import com.withsport.userservice.domain.jwt.exception.RefreshTokenNotExistException;
import com.withsport.userservice.global.dto.Result;
import com.withsport.userservice.global.utils.CookieProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    private final CookieProvider cookieProvider;

    @ExceptionHandler(UserException.class)
    public ResponseEntity userExceptionHandler(UserException ue) {
        HttpStatus status = ue.getStatus();
        Result errorResult = ue.getErrorResult();

        log.warn("[UserException] {}, {}", status, errorResult);

        return ResponseEntity.status(status)
                .body(errorResult);
    }

    @ExceptionHandler(RefreshTokenNotExistException.class)
    public ResponseEntity userJwtExceptionHandler(RefreshTokenNotExistException e) {
        // 쿠키 삭제
        ResponseCookie responseCookie = cookieProvider.deleteRefreshTokenCookie();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(e.getResult());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        return getValidationErrorBody(exception);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity bindExceptionHandler(BindException exception){
        return getValidationErrorBody(exception);
    }

    private ResponseEntity getValidationErrorBody(BindException e){
        BindingResult bindingResult = e.getBindingResult();

        StringBuilder builder = new StringBuilder();
        bindingResult.getFieldErrors()
                .forEach(fieldError -> {
                    builder.append("[");
                    builder.append(fieldError.getField());
                    builder.append("](은)는 ");
                    builder.append(fieldError.getDefaultMessage());
                    builder.append(" 입력된 값: [");
                    builder.append(fieldError.getRejectedValue());
                    builder.append("]");
                });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Result.createErrorResult(builder.toString()));
    }
}
