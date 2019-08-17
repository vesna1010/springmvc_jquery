package com.vesna1010.quizzes.test.dao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.vesna1010.quizzes.dao.QuestionDao;
import com.vesna1010.quizzes.enums.Answer;
import com.vesna1010.quizzes.model.Question;
import com.vesna1010.quizzes.model.Quiz;

public class QuestionDaoTest extends BaseDaoTest {

	@Autowired
	private QuestionDao questionDao;

	@Test
	public void getAllQuestionsTest() {
		List<Question> questions = questionDao.getAllQuestions();

		assertThat(questions, hasSize(4));
	}

	@Test
	public void getAllQuestionsByQuizTest() {
		List<Question> questions = questionDao.getAllQuestionsByQuiz(new Quiz(1L, "Quiz A"));

		assertThat(questions, hasSize(3));
	}

	@Test
	public void getQuestionByIdTest() {
		Question question = questionDao.getQuestionById(1L);

		assertThat(question.getQuestionText(), equalTo("Question A"));
	}

	@Test
	public void getQuestionByIdNotFoundTest() {
		Question question = questionDao.getQuestionById(5L);

		assertNull(question);
	}

	@Test
	public void saveQuestionTest() {
		Question question = new Question("Question D", "Answer A", "Answer B", "Answer C", "Answer D", Answer.D,
				new Quiz(1L, "Quiz A"), 10);

		questionDao.saveQuestion(question);

		assertNotNull(question.getId());
	}

	@Test
	public void updateQuestionTest() {
		Question question = questionDao.getQuestionById(1L);

		question.setQuestionText("Question");
		question = questionDao.updateQuestion(question);

		assertThat(question.getQuestionText(), equalTo("Question"));
	}

	@Test
	public void deleteQuestionsByIdTest() {
		questionDao.deleteQuestionById(1L);

		assertFalse(questionDao.existsQuestionById(1L));
	}

	@Test
	public void existsQuestionById() {
		assertTrue(questionDao.existsQuestionById(1L));
		assertFalse(questionDao.existsQuestionById(5L));
	}

}
