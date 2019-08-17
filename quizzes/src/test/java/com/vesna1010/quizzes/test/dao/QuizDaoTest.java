package com.vesna1010.quizzes.test.dao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.vesna1010.quizzes.dao.QuizDao;
import com.vesna1010.quizzes.model.Quiz;

public class QuizDaoTest extends BaseDaoTest {

	@Autowired
	private QuizDao quizDao;

	@Test
	public void getAllQuizzesTest() {
		List<Quiz> quizzes = quizDao.getAllQuizzes();

		assertThat(quizzes, hasSize(2));
	}

	@Test
	public void getQuizByIdTest() {
		Quiz quiz = quizDao.getQuizById(1L);

		assertThat(quiz.getName(), equalTo("Quiz A"));
		assertThat(quiz.getQuestions(), hasSize(3));
	}

	@Test
	public void getQuizByIdNotFoundTest() {
		Quiz quiz = quizDao.getQuizById(3L);

		assertNull(quiz);
	}

	@Test
	public void saveQuizTest() {
		Quiz quiz = new Quiz("New Quiz");

		quizDao.saveQuiz(quiz);

		assertNotNull(quiz.getId());
	}

	@Test
	public void updateQuizTest() {
		Quiz quiz = quizDao.getQuizById(1L);

		quiz.setName("Quiz");
		quiz = quizDao.updateQuiz(quiz);

		assertThat(quiz.getName(), equalTo("Quiz"));
		assertThat(quiz.getQuestions(), hasSize(3));
	}

	@Test
	public void deleteQuizByIdTest() {
		quizDao.deleteQuizById(1L);

		assertFalse(quizDao.existsQuizById(1L));
	}

	@Test
	public void existsQuizById() {
		assertTrue(quizDao.existsQuizById(1L));
		assertFalse(quizDao.existsQuizById(3L));
	}

}
