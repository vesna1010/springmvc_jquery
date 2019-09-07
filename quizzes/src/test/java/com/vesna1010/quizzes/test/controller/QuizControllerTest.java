package com.vesna1010.quizzes.test.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vesna1010.quizzes.controller.ExceptionController;
import com.vesna1010.quizzes.controller.QuizController;
import com.vesna1010.quizzes.exception.ResourceNotFoundException;
import com.vesna1010.quizzes.model.Quiz;
import com.vesna1010.quizzes.service.QuizService;

public class QuizControllerTest extends BaseControllerTest {

	@Mock
	private QuizService quizService;
	@InjectMocks
	private QuizController quizController;
	private ExceptionController exceptionController = new ExceptionController();
	private MockMvc mockMvc;
	
	@Before
	public void init() {
		this.mockMvc = MockMvcBuilders
				.standaloneSetup(quizController, exceptionController)
				.setViewResolvers(viewResolver())
				.setValidator(new QuizValidator())
				.build();
	}
	
	@Test
	public void getAllQuizzesTest() throws Exception {
		when(quizService.getAllQuizzes()).thenReturn(Arrays.asList(quiz1, quiz2));
		
		mockMvc.perform(get("/quizzes"))
		       .andExpect(status().isOk())
		       .andExpect(content().contentType("application/json;charset=UTF-8"))
		       .andExpect(jsonPath("$", hasSize(2)))
		       .andExpect(jsonPath("$[0].name", is("Quiz A")))
		       .andExpect(jsonPath("$[1].name", is("Quiz B")));

		verify(quizService, times(1)).getAllQuizzes();
	}
	
	@Test
	public void getQuizByIdTest() throws Exception {
		when(quizService.getQuizById(1L)).thenReturn(quiz1);

		mockMvc.perform(get("/quizzes/1"))
	               .andExpect(status().isOk())
	               .andExpect(content().contentType("application/json;charset=UTF-8"))
	               .andExpect(jsonPath("$.name", is("Quiz A")));

	    verify(quizService, times(1)).getQuizById(1L);
	}
	
	@Test
	public void getQuizByIdNotFoundTest() throws Exception {
		when(quizService.getQuizById(1L)).thenThrow(new ResourceNotFoundException("No quiz with id 1"));

		mockMvc.perform(get("/quizzes/1"))
	               .andExpect(status().isNotFound())
	               .andExpect(content().contentType("text/plain;charset=ISO-8859-1"))
	               .andExpect(jsonPath("$", is("No quiz with id 1")));

	    verify(quizService, times(1)).getQuizById(1L);
	}
	
	@Test
	public void saveQuizTest() throws JsonProcessingException, Exception {
		Quiz quiz = new Quiz("New Quiz");
		
		doNothing().when(quizService).saveQuiz(quiz);
		
		mockMvc.perform(
				post("/quizzes")
				.contentType("application/json;charset=UTF-8")
				.content(new ObjectMapper().writeValueAsString(quiz))
				)
		       .andExpect(status().isCreated())
	               .andExpect(content().contentType("application/json;charset=UTF-8"))
	               .andExpect(content().string(new ObjectMapper().writeValueAsString(quiz)));
		
		verify(quizService, times(1)).saveQuiz(quiz);
	}
	
	@Test
	public void saveQuizBadRequestTest() throws JsonProcessingException, Exception {
		Quiz quiz = new Quiz("New + Quiz");
		
		doNothing().when(quizService).saveQuiz(quiz);
		
		mockMvc.perform(
				post("/quizzes")
				.contentType("application/json;charset=UTF-8")
				.content(new ObjectMapper().writeValueAsString(quiz))
				)
		       .andExpect(status().isBadRequest())
	               .andExpect(content().contentType("text/plain;charset=ISO-8859-1"))
	               .andExpect(jsonPath("$", containsString("Enter a valid name")));
		
		verify(quizService, never()).saveQuiz(quiz);
	}
	
	@Test
	public void updateQuizTest() throws JsonProcessingException, Exception {
		when(quizService.updateQuiz(1L, quiz1)).thenReturn(quiz1);
		
		mockMvc.perform(
				put("/quizzes/1")
				.contentType("application/json;charset=UTF-8")
				.content(new ObjectMapper().writeValueAsString(quiz1))
				)
		       .andExpect(status().isOk())
	               .andExpect(content().contentType("application/json;charset=UTF-8"))
	               .andExpect(content().string(new ObjectMapper().writeValueAsString(quiz1)));
		
		verify(quizService, times(1)).updateQuiz(1L, quiz1);
	}
	
	@Test
	public void updateQuizNotFoundTest() throws JsonProcessingException, Exception {
		when(quizService.updateQuiz(1L, quiz1)).thenThrow(new ResourceNotFoundException("No quiz with id 1"));
		
		mockMvc.perform(
				put("/quizzes/1")
				.contentType("application/json;charset=UTF-8")
				.content(new ObjectMapper().writeValueAsString(quiz1))
				)
		       .andExpect(status().isNotFound())
		       .andExpect(content().contentType("text/plain;charset=ISO-8859-1"))
	               .andExpect(jsonPath("$", is("No quiz with id 1")));
		
		verify(quizService, times(1)).updateQuiz(1L, quiz1);
	}
	
	@Test
	public void updateQuizBadRequestTest() throws JsonProcessingException, Exception {
		Quiz quiz = new Quiz(1L, "New + Quiz");
		
		when(quizService.updateQuiz(1L, quiz)).thenReturn(quiz);
		
		mockMvc.perform(
				put("/quizzes/1")
				.contentType("application/json;charset=UTF-8")
				.content(new ObjectMapper().writeValueAsString(quiz))
				)
		       .andExpect(status().isBadRequest())
		       .andExpect(content().contentType("text/plain;charset=ISO-8859-1"))
		       .andExpect(jsonPath("$", containsString("Enter a valid name")));
		
		verify(quizService, never()).updateQuiz(1L, quiz);
	}
	
	@Test
	public void deleteQuizByIdTest() throws Exception {
		doNothing().when(quizService).deleteQuizById(1L);

		mockMvc.perform(delete("/quizzes/1"))
		       .andExpect(status().isNoContent());
		
		verify(quizService, times(1)).deleteQuizById(1L);       
	}
	
	@Test
	public void deleteQuizByIdNotFoundTest() throws Exception {
		doThrow(new ResourceNotFoundException("No quiz with id 1")).when(quizService).deleteQuizById(1L);

		mockMvc.perform(delete("/quizzes/1"))
		       .andExpect(status().isNotFound())
		       .andExpect(content().contentType("text/plain;charset=ISO-8859-1"))
	               .andExpect(jsonPath("$", is("No quiz with id 1")));
		
		verify(quizService, times(1)).deleteQuizById(1L);       
	}
	
	private class QuizValidator implements Validator {

		private final String nameRegex = "^[a-zA-Z\\s]{5,}$";

		@Override
		public boolean supports(Class<?> cls) {
			return cls.equals(Quiz.class);
		}

		@Override
		public void validate(Object obj, Errors errors) {
			Quiz quiz = (Quiz) obj;

			if (!quiz.getName().matches(nameRegex)) {
				errors.rejectValue("name", null, "Enter a valid name");
			}
		}

	}
	
}
