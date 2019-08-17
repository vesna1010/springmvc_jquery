package com.vesna1010.quizzes.service;

import java.util.List;
import com.vesna1010.quizzes.model.Quiz;

public interface QuizService {

	List<Quiz> getAllQuizzes();

	Quiz getQuizById(Long id);

	Quiz updateQuiz(Long id, Quiz quiz);

	void saveQuiz(Quiz quiz);

	void deleteQuizById(Long id);

}
