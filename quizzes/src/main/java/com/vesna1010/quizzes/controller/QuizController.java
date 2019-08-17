package com.vesna1010.quizzes.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vesna1010.quizzes.model.Quiz;
import com.vesna1010.quizzes.service.QuizService;

@RestController
@RequestMapping("/quizzes")
public class QuizController {

	@Autowired
	private QuizService quizService;

	@GetMapping
	public ResponseEntity<List<Quiz>> getAllQuizzes() {
		List<Quiz> quizzes = quizService.getAllQuizzes();

		return ResponseEntity.ok(quizzes);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Quiz> getQuizById(@PathVariable final Long id) {
		Quiz quiz = quizService.getQuizById(id);

		return ResponseEntity.ok(quiz);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Quiz> saveQuiz(@Valid @RequestBody Quiz quiz) {
		quizService.saveQuiz(quiz);

		return ResponseEntity.status(HttpStatus.CREATED).body(quiz);
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Quiz> updateQuiz(@PathVariable final Long id, @Valid @RequestBody Quiz quiz) {
		quiz = quizService.updateQuiz(id, quiz);

		return ResponseEntity.ok(quiz);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteQuizById(@PathVariable final Long id) {
		quizService.deleteQuizById(id);

		return ResponseEntity.noContent().build();
	}

}
