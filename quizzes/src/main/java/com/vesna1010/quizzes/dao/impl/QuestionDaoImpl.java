package com.vesna1010.quizzes.dao.impl;

import java.util.List;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import com.vesna1010.quizzes.dao.QuestionDao;
import com.vesna1010.quizzes.model.Question;
import com.vesna1010.quizzes.model.Quiz;

@Repository
public class QuestionDaoImpl extends DaoImpl implements QuestionDao {

	@Override
	public List<Question> getAllQuestions() {
		TypedQuery<Question> query = session().createQuery("select q from Question q order by id asc", Question.class);

		return (List<Question>) query.getResultList();
	}

	@Override
	public List<Question> getAllQuestionsByQuiz(Quiz quiz) {
		TypedQuery<Question> query = session().createQuery("select q from Question q where quiz=:quiz order by id asc",
				Question.class);

		query.setParameter("quiz", quiz);

		return (List<Question>) query.getResultList();
	}

	@Override
	public void deleteQuestionById(Long id) {
		Question question = getQuestionById(id);

		session().delete(question);
	}

	@Override
	public Question getQuestionById(Long id) {
		return (Question) session().get(Question.class, id);
	}

	@Override
	public Question updateQuestion(Question question) {
		return (Question) session().merge(question);
	}

	@Override
	public void saveQuestion(Question question) {
		session().persist(question);
	}

	@Override
	public boolean existsQuestionById(Long id) {
		TypedQuery<Long> query = session().createQuery("select count(q) from Question q where q.id=:id", Long.class);

		query.setParameter("id", id);

		return (query.getSingleResult() == 1);
	}

}
