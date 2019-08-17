package com.vesna1010.quizzes.dao.impl;

import java.util.List;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import com.vesna1010.quizzes.dao.QuizDao;
import com.vesna1010.quizzes.model.Quiz;

@Repository
public class QuizDaoImpl extends DaoImpl implements QuizDao {

	@Override
	public List<Quiz> getAllQuizzes() {
		TypedQuery<Quiz> query = session().createQuery("select q from Quiz q order by id asc", Quiz.class);

		return (List<Quiz>) query.getResultList();
	}

	@Override
	public void deleteQuizById(Long id) {
		Quiz quiz = getQuizById(id);

		session().delete(quiz);
	}

	@Override
	public Quiz getQuizById(Long id) {
		return (Quiz) session().get(Quiz.class, id);
	}

	@Override
	public Quiz updateQuiz(Quiz quiz) {
		return (Quiz) session().merge(quiz);
	}

	@Override
	public void saveQuiz(Quiz quiz) {
		session().persist(quiz);
	}

	@Override
	public boolean existsQuizById(Long id) {
		TypedQuery<Long> query = session().createQuery("select count(q) from Quiz q where q.id=:id", Long.class);

		query.setParameter("id", id);

		return (query.getSingleResult() == 1);
	}

}
