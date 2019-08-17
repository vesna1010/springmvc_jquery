package com.vesna1010.quizzes.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DaoImpl {

	@Autowired
	private SessionFactory sessionFactory;

	protected Session session() {
		return sessionFactory.getCurrentSession();
	}

}
