package com.vesna1010.quizzes.service.impl;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vesna1010.quizzes.dao.QuizDao;
import com.vesna1010.quizzes.exception.ResourceNotFoundException;
import com.vesna1010.quizzes.model.Quiz;
import com.vesna1010.quizzes.service.QuizService;

@Service
@Transactional
public class QuizServiceImpl implements QuizService {

	@Autowired
	private QuizDao quizDao;

	@Override
	public List<Quiz> getAllQuizzes() {
		return quizDao.getAllQuizzes();
	}

	@Override
	public Quiz getQuizById(Long id) {
		if (!quizDao.existsQuizById(id)) {
			throw new ResourceNotFoundException("No quiz with id " + id);
		}

		return quizDao.getQuizById(id);
	}

	@Override
	public Quiz updateQuiz(Long id, Quiz quiz) {
		if (!quizDao.existsQuizById(id)) {
			throw new ResourceNotFoundException("No quiz with id " + id);
		}

		return quizDao.updateQuiz(quiz);
	}

	@Override
	public void saveQuiz(Quiz quiz) {
		quizDao.saveQuiz(quiz);
	}

	@Override
	public void deleteQuizById(Long id) {
		if (!quizDao.existsQuizById(id)) {
			throw new ResourceNotFoundException("No quiz with id " + id);
		}

		quizDao.deleteQuizById(id);
	}

}
