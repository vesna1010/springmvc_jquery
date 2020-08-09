package com.vesna1010.quizzes.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.vesna1010.quizzes.exception.ResourceNotFoundException;

@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler
	public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}

	@ExceptionHandler
	public ResponseEntity<StringBuilder> handleQuestionNotFoundException(MethodArgumentNotValidException e) {
		StringBuilder errors = getValidationMessages(e.getBindingResult());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}

	private StringBuilder getValidationMessages(BindingResult result) {
		StringBuilder validationMessages = new StringBuilder();
		List<ObjectError> errors = result.getAllErrors();

		for (ObjectError error : errors) {
			validationMessages.append(error.getDefaultMessage() + "\n");
		}

		return validationMessages;
	}

}
