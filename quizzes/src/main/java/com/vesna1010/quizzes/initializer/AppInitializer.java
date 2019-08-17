package com.vesna1010.quizzes.initializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import com.vesna1010.quizzes.configuration.ConfigurationForDatabase;
import com.vesna1010.quizzes.configuration.ConfigurationForWeb;

public class AppInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		DispatcherServlet servlet = new DispatcherServlet(servletContext());
		ServletRegistration.Dynamic registration = servletContext.addServlet("quizzes", servlet);

		registration.addMapping("/");
		registration.setLoadOnStartup(1);
		servletContext.addListener(new ContextLoaderListener(rootContext()));
	}

	private WebApplicationContext servletContext() {
		AnnotationConfigWebApplicationContext servletContext = new AnnotationConfigWebApplicationContext();

		servletContext.register(new Class[] { ConfigurationForWeb.class });

		return servletContext;
	}

	private WebApplicationContext rootContext() {
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();

		rootContext.register(new Class[] { ConfigurationForDatabase.class });

		return rootContext;
	}

}
