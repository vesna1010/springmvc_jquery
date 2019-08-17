package com.vesna1010.quizzes.service.impl;

import java.util.Collections;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vesna1010.quizzes.dao.QuestionDao;
import com.vesna1010.quizzes.exception.ResourceNotFoundException;
import com.vesna1010.quizzes.model.Question;
import com.vesna1010.quizzes.model.Quiz;
import com.vesna1010.quizzes.service.QuestionService;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionDao questionDao;

	@Override
	public List<Question> getAllQuestions() {
		return questionDao.getAllQuestions();
	}

	@Override
	public List<Question> getThreeQuestionsByQuiz(Quiz quiz) {
		List<Question> questions = questionDao.getAllQuestionsByQuiz(quiz);

		Collections.shuffle(questions);

		return questions.subList(0, 3);
	}

	@Override
	public Question getQuestionById(Long id) {
		if (!questionDao.existsQuestionById(id)) {
			throw new ResourceNotFoundException("No question with id " + id);
		}

		return questionDao.getQuestionById(id);
	}

	@Override
	public Question updateQuestion(Long id, Question question) {
		if (!questionDao.existsQuestionById(id)) {
			throw new ResourceNotFoundException("No question with id " + id);
		}

		return questionDao.updateQuestion(question);
	}

	@Override
	public void saveQuestion(Question question) {
		questionDao.saveQuestion(question);
	}

	@Override
	public void deleteQuestionById(Long id) {
		
		if (!questionDao.existsQuestionById(id)) {
			throw new ResourceNotFoundException("No question with id " + id);
		}

		questionDao.deleteQuestionById(id);
	}

}
