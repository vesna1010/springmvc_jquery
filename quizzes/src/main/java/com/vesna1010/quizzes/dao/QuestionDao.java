package com.vesna1010.quizzes.dao;

import java.util.List;
import com.vesna1010.quizzes.model.Question;
import com.vesna1010.quizzes.model.Quiz;

public interface QuestionDao {

	List<Question> getAllQuestions();

	List<Question> getAllQuestionsByQuiz(Quiz quiz);

	Question getQuestionById(Long id);

	Question updateQuestion(Question question);

	void saveQuestion(Question question);

	void deleteQuestionById(Long id);

	boolean existsQuestionById(Long id);

}
