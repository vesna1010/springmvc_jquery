package com.vesna1010.quizzes.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
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
import com.vesna1010.quizzes.model.Question;
import com.vesna1010.quizzes.model.Quiz;
import com.vesna1010.quizzes.service.QuestionService;

@RestController
@RequestMapping("/questions")
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	@GetMapping
	public ResponseEntity<List<Question>> getAllQuestions() {
		List<Question> questions = questionService.getAllQuestions();

		return ResponseEntity.ok(questions);
	}

	@GetMapping("/three")
	public ResponseEntity<List<Question>> getThreeQuestionsByQuiz(HttpSession httpSession) {
		Quiz quiz = (Quiz) httpSession.getAttribute("quiz");
		List<Question> questions = questionService.getThreeQuestionsByQuiz(quiz);

		return ResponseEntity.ok(questions);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Question> getQuestionById(@PathVariable final Long id) {
		Question question = questionService.getQuestionById(id);

		return ResponseEntity.ok(question);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Question> saveQuestion(@Valid @RequestBody Question question) {
		questionService.saveQuestion(question);

		return ResponseEntity.status(HttpStatus.CREATED).body(question);
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Question> updateQuestion(@PathVariable final Long id, @Valid @RequestBody Question question) {
		question = questionService.updateQuestion(id, question);

		return ResponseEntity.ok(question);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteQuestionById(@PathVariable final Long id) {
		questionService.deleteQuestionById(id);

		return ResponseEntity.noContent().build();
	}

}
