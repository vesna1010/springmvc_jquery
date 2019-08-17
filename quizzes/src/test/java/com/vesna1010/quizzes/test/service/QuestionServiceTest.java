package com.vesna1010.quizzes.test.service;

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
import com.vesna1010.quizzes.dao.QuestionDao;
import com.vesna1010.quizzes.exception.ResourceNotFoundException;
import com.vesna1010.quizzes.model.Question;
import com.vesna1010.quizzes.service.QuestionService;
import com.vesna1010.quizzes.service.impl.QuestionServiceImpl;

public class QuestionServiceTest extends BaseServiceTest {

	@InjectMocks
	private QuestionService questionService = new QuestionServiceImpl();
	@Mock
	private QuestionDao questionDao;

	@Test
	public void getAllQuestionsTest() {
		when(questionDao.getAllQuestions()).thenReturn(Arrays.asList(question1, question2, question3));

		List<Question> questions = questionService.getAllQuestions();

		assertThat(questions, hasSize(3));
		verify(questionDao, times(1)).getAllQuestions();
	}

	@Test
	public void getAllQuestionsByQuizTest() {
		when(questionDao.getAllQuestionsByQuiz(quiz1)).thenReturn(Arrays.asList(question1, question2, question3));

		List<Question> questions = questionService.getThreeQuestionsByQuiz(quiz1);

		assertThat(questions, hasSize(3));
		verify(questionDao, times(1)).getAllQuestionsByQuiz(quiz1);
	}

	@Test
	public void getQuestionByIdTest() {
		when(questionDao.existsQuestionById(1L)).thenReturn(true);
		when(questionDao.getQuestionById(1L)).thenReturn(question1);

		Question question = questionService.getQuestionById(1L);

		assertThat(question.getQuestionText(), equalTo("Question A"));
		verify(questionDao, times(1)).existsQuestionById(1L);
		verify(questionDao, times(1)).getQuestionById(1L);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void getQuestionByIdNotFoundTest() {
		when(questionDao.existsQuestionById(1L)).thenReturn(false);

		questionService.getQuestionById(1L);
	}

	@Test
	public void updateQuestionTest() {
		when(questionDao.existsQuestionById(1L)).thenReturn(true);
		when(questionDao.updateQuestion(question1)).thenReturn(question1);

		Question question = questionService.updateQuestion(1L, question1);

		assertThat(question.getQuestionText(), equalTo("Question A"));
		verify(questionDao, times(1)).existsQuestionById(1L);
		verify(questionDao, times(1)).updateQuestion(question1);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void updateQuestionNotFoundTest() {
		when(questionDao.existsQuestionById(1L)).thenReturn(false);

		questionService.updateQuestion(1L, question1);
	}

	@Test
	public void saveQuestionTest() {
		final Question question = new Question("Question E", "Answer A", "Answer B", "Answer C", "Answer D",
				com.vesna1010.quizzes.enums.Answer.C, quiz1, 10);

		doNothing().when(questionDao).saveQuestion(question);

		questionService.saveQuestion(question);

		verify(questionDao, times(1)).saveQuestion(question);
	}

	@Test
	public void deleteQuestionsByIdTest() {
		when(questionDao.existsQuestionById(1L)).thenReturn(true);
		doNothing().when(questionDao).deleteQuestionById(1L);

		questionService.deleteQuestionById(1L);

		verify(questionDao, times(1)).existsQuestionById(1L);
		verify(questionDao, times(1)).deleteQuestionById(1L);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void deleteQuestionsByIdNotFoundTest() {
		when(questionDao.existsQuestionById(1L)).thenReturn(false);

		questionService.deleteQuestionById(1L);
	}

}
