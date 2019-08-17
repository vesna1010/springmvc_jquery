package com.vesna1010.quizzes.dao;

import java.util.List;
import com.vesna1010.quizzes.model.Quiz;

public interface QuizDao {

	List<Quiz> getAllQuizzes();

	Quiz getQuizById(Long id);

	Quiz updateQuiz(Quiz quiz);

	void saveQuiz(Quiz quiz);

	void deleteQuizById(Long id);

	boolean existsQuizById(Long id);

}
