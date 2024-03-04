package br.com.validateddocumentservice.application.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionGlobalHandler {
    @ExceptionHandler(ValidationException::class)
    fun handleValidationException(validationException: ValidationException): ResponseEntity<*>? {
        val details = ExceptionDetails(HttpStatus.BAD_REQUEST.value(), validationException.message)
        return ResponseEntity<Any>(details, HttpStatus.BAD_REQUEST)
    }
}