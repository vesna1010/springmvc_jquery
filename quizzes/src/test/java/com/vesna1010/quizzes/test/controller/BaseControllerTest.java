package com.vesna1010.quizzes.test.controller;

import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import com.vesna1010.quizzes.test.BaseTest;

@RunWith(MockitoJUnitRunner.class)
public abstract class BaseControllerTest extends BaseTest {

	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".html");

		return viewResolver;
	}
}

