package com.vesna1010.quizzes.test.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.vesna1010.quizzes.controller.HomeController;
import com.vesna1010.quizzes.service.QuizService;

public class HomeControllerTest extends BaseControllerTest {

	@InjectMocks
	private HomeController homeController;
	@Mock
	private QuizService quizService;
	private MockMvc mockMvc;
	
	@Before
	public void init() {
		this.mockMvc = MockMvcBuilders
				.standaloneSetup(homeController)
				.setViewResolvers(viewResolver())
				.build();
	}
	
	@Test
	public void renderHomePageWithQuizzesTest() throws Exception {	
		when(quizService.getAllQuizzes()).thenReturn(Arrays.asList(quiz1, quiz2));
		
		mockMvc.perform(
				get("/")
				.sessionAttr("quiz", quiz1)
				)
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("quizzes", hasSize(2)))
		       .andExpect(request().sessionAttribute("quiz", is(nullValue())))
		       .andExpect(view().name("home"));
		
		verify(quizService, times(1)).getAllQuizzes();
	}
	
	@Test
	public void setQuizForPlayTest() throws Exception {
		when(quizService.getQuizById(1L)).thenReturn(quiz1);
		
		mockMvc.perform(
				get("/setQuiz")
				.param("id", "1")
				.sessionAttr("quiz", quiz1)
				)
		       .andExpect(status().isOk())
		       .andExpect(request().sessionAttribute("quiz", is(quiz1)))
		       .andExpect(view().name("playQuiz"));
		
		verify(quizService, times(1)).getQuizById(1L);
	}
	
	@Test
	public void renderPlayQuizPageTest() throws Exception {
		mockMvc.perform(
				get("/playQuiz")
				.sessionAttr("quiz", quiz1)
				)
		       .andExpect(status().isOk())
	               .andExpect(view().name("playQuiz"));     
	}
	
	@Test
	public void tryRenderPlayQuizPageTest() throws Exception {
		mockMvc.perform(get("/playQuiz"))
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrl("/"));  
	}
	
	@Test
	public void renderEditQuizzesPageTest() throws Exception {
		mockMvc.perform(get("/editQuizzes"))
		       .andExpect(status().isOk())
		       .andExpect(view().name("editQuizzes"));
	}
	
	@Test
	public void renderEditQuestionsPageTest() throws Exception {
		mockMvc.perform(get("/editQuestions"))
		       .andExpect(status().isOk())
		       .andExpect(view().name("editQuestions"));
	}
	
}
