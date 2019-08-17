package com.vesna1010.quizzes.service.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import com.vesna1010.quizzes.dao.QuizDao;
import com.vesna1010.quizzes.exception.ResourceNotFoundException;
import com.vesna1010.quizzes.model.Quiz;
import com.vesna1010.quizzes.service.QuizService;
import com.vesna1010.quizzes.service.impl.QuizServiceImpl;

public class QuizServiceTest extends BaseServiceTest {

	@InjectMocks
	private QuizService quizService = new QuizServiceImpl();
	@Mock
	private QuizDao quizDao;

	@Test
	public void getAllQuizzesTest() {
		when(quizDao.getAllQuizzes()).thenReturn(Arrays.asList(quiz1, quiz2));

		List<Quiz> quizzes = quizService.getAllQuizzes();

		assertThat(quizzes, hasSize(2));
		verify(quizDao, times(1)).getAllQuizzes();
	}

	@Test
	public void getQuizByIdTest() {
		when(quizDao.existsQuizById(1L)).thenReturn(true);
		when(quizDao.getQuizById(1L)).thenReturn(quiz1);

		Quiz quiz = quizService.getQuizById(1L);

		assertThat(quiz.getName(), equalTo("Quiz A"));
		verify(quizDao, times(1)).existsQuizById(1L);
		verify(quizDao, times(1)).getQuizById(1L);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void getQuizByIdNotFoundTest() {
		when(quizDao.existsQuizById(1L)).thenReturn(false);

		quizService.getQuizById(1L);
	}

	@Test
	public void updateQuizTest() {
		when(quizDao.existsQuizById(1L)).thenReturn(true);
		when(quizDao.updateQuiz(quiz1)).thenReturn(quiz1);

		Quiz quiz = quizService.updateQuiz(1L, quiz1);

		assertThat(quiz.getName(), equalTo("Quiz A"));
		verify(quizDao, times(1)).existsQuizById(1L);
		verify(quizDao, times(1)).updateQuiz(quiz1);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void updateQuizNotFoundTest() {
		when(quizDao.existsQuizById(1L)).thenReturn(false);

		quizService.updateQuiz(1L, quiz1);
	}

	@Test
	public void saveQuizTest() {
		final Quiz quiz = new Quiz("Quiz C");

		doNothing().when(quizDao).saveQuiz(quiz);

		quizService.saveQuiz(quiz);

		verify(quizDao, times(1)).saveQuiz(quiz);
	}

	@Test
	public void deleteQuizByIdTest() {
		when(quizDao.existsQuizById(1L)).thenReturn(true);
		doNothing().when(quizDao).deleteQuizById(1L);

		quizService.deleteQuizById(1L);

		verify(quizDao, times(1)).existsQuizById(1L);
		verify(quizDao, times(1)).deleteQuizById(1L);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void deleteQuizByIdNotFoundTest() {
		when(quizDao.existsQuizById(1L)).thenReturn(false);

		quizService.deleteQuizById(1L);
	}

}
