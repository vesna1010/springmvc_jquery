package com.vesna1010.quizzes.service;

import java.util.List;
import com.vesna1010.quizzes.model.Question;
import com.vesna1010.quizzes.model.Quiz;

public interface QuestionService {

	List<Question> getAllQuestions();

	List<Question> getThreeQuestionsByQuiz(Quiz quiz);

	Question getQuestionById(Long id);

	Question updateQuestion(Long id, Question question);

	void saveQuestion(Question question);

	void deleteQuestionById(Long id);

}
