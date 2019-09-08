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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vesna1010.quizzes.controller.ExceptionController;
import com.vesna1010.quizzes.controller.QuestionController;
import com.vesna1010.quizzes.exception.ResourceNotFoundException;
import com.vesna1010.quizzes.enums.Answer;
import com.vesna1010.quizzes.model.Question;
import com.vesna1010.quizzes.service.QuestionService;

public class QuestionControllerTest extends BaseControllerTest {

	@InjectMocks
	private QuestionController questionController;
	private ExceptionController exceptionController = new ExceptionController();
	@Mock
	private QuestionService questionService;
	private MockMvc mockMvc;
	
	@Before
	public void init() {
		this.mockMvc = MockMvcBuilders
				.standaloneSetup(questionController, exceptionController)
				.setViewResolvers(viewResolver())
				.setValidator(new QuestionValidator())
				.build();
	}

	@Test
	public void getAllQuestionsTest() throws Exception {
		when(questionService.getAllQuestions()).thenReturn(Arrays.asList(question1, question2, question3));
	
		mockMvc.perform(get("/questions"))
		       .andExpect(status().isOk())
		       .andExpect(content().contentType("application/json;charset=UTF-8"))
		       .andExpect(jsonPath("$", hasSize(3)))
		       .andExpect(jsonPath("$[0].questionText", is("Question A")))
		       .andExpect(jsonPath("$[1].questionText", is("Question B")))
		       .andExpect(jsonPath("$[2].questionText", is("Question C")));
	
		verify(questionService, times(1)).getAllQuestions();
	}
	
	@Test
	public void getThreeQuestionsByQuizTest() throws Exception {
		when(questionService.getThreeQuestionsByQuiz(quiz1)).thenReturn(Arrays.asList(question1, question2, question3));
	
		mockMvc.perform(get("/questions/three").sessionAttr("quiz", quiz1))
		       .andExpect(status().isOk())
		       .andExpect(content().contentType("application/json;charset=UTF-8"))
		       .andExpect(jsonPath("$", hasSize(3)))
		       .andExpect(jsonPath("$[0].questionText", is("Question A")))
		       .andExpect(jsonPath("$[1].questionText", is("Question B")))
		       .andExpect(jsonPath("$[2].questionText", is("Question C")));
	
		verify(questionService, times(1)).getThreeQuestionsByQuiz(quiz1);
	}
	
	@Test
	public void getQuestionByIdTest() throws Exception {
		when(questionService.getQuestionById(1L)).thenReturn(question1);
		
		mockMvc.perform(get("/questions/1"))
		       .andExpect(status().isOk())
		       .andExpect(content().contentType("application/json;charset=UTF-8"))
		       .andExpect(jsonPath("$.questionText", is("Question A")));
		
		verify(questionService, times(1)).getQuestionById(1L);
	}
	
	@Test
	public void getQuestionByIdNotFoundTest() throws Exception {
		when(questionService.getQuestionById(1L)).thenThrow(new ResourceNotFoundException("No question with id 1"));
		
		mockMvc.perform(get("/questions/1"))
		       .andExpect(status().isNotFound())
		       .andExpect(content().contentType("text/plain;charset=ISO-8859-1"))
		       .andExpect(jsonPath("$", is("No question with id 1")));
		
		verify(questionService, times(1)).getQuestionById(1L);
	}
	
	@Test
	public void saveQuestionTest() throws Exception {
		Question question = new Question("Question D", "Answer A", "Answer B", "Answer C", "Answer D",
				Answer.A, quiz1, 10);
		
		doNothing().when(questionService).saveQuestion(question);
		
		mockMvc.perform(
				post("/questions")
				.contentType("application/json;charset=UTF-8")
				.content(new ObjectMapper().writeValueAsString(question))
				)
		       .andExpect(status().isCreated())
		       .andExpect(content().contentType("application/json;charset=UTF-8"))
		       .andExpect(content().string(new ObjectMapper().writeValueAsString(question)));
		
		verify(questionService, times(1)).saveQuestion(question);
	}
	
	@Test
	public void saveQuiestionBadRequestTest() throws Exception {
		Question question = new Question("New Question", null, "", "Answer C", "Answer D", Answer.C, null, 20);
	
		doNothing().when(questionService).saveQuestion(question);
		
		mockMvc.perform(
				post("/questions")
				.contentType("application/json;charset=UTF-8")
				.content(new ObjectMapper().writeValueAsString(question))
				)
		       .andExpect(status().isBadRequest())
		       .andExpect(content().contentType("text/plain;charset=ISO-8859-1"))
		       .andExpect(jsonPath("$", containsString("Enter the answer a")))
		       .andExpect(jsonPath("$", containsString("Enter the answer b")))
		       .andExpect(jsonPath("$", containsString("Please select a quiz")));
		
		verify(questionService, never()).saveQuestion(question);
	}
	
	@Test
	public void updateQuestionTest() throws Exception {
		when(questionService.updateQuestion(1L, question1)).thenReturn(question1);
		
		mockMvc.perform(
				put("/questions/1")
				.contentType("application/json;charset=UTF-8")
				.content(new ObjectMapper().writeValueAsString(question1))
				)
		       .andExpect(status().isOk())
		       .andExpect(content().contentType("application/json;charset=UTF-8"))
		       .andExpect(content().string(new ObjectMapper().writeValueAsString(question1)));
		
		verify(questionService, times(1)).updateQuestion(1L, question1);
	}
	
	@Test
	public void updateQuiestionNotFoundTest() throws Exception {
		when(questionService.updateQuestion(1L, question1)).thenThrow(new ResourceNotFoundException("No question with id 1"));
	
		mockMvc.perform(
				put("/questions/1")
				.contentType("application/json;charset=UTF-8")
				.content(new ObjectMapper().writeValueAsString(question1))
				)
		       .andExpect(status().isNotFound())
		       .andExpect(content().contentType("text/plain;charset=ISO-8859-1"))
		       .andExpect(jsonPath("$", is("No question with id 1")));
		
		verify(questionService, times(1)).updateQuestion(1L, question1);
	}
	
	@Test
	public void updateQuiestionBadRequestTest() throws Exception {
		Question question = new Question(1L, "New Question", null, "", "Answer C", "Answer D", Answer.C, null, 20);
		
		when(questionService.updateQuestion(1L, question)).thenReturn(question);
		
		mockMvc.perform(
				put("/questions/1")
				.contentType("application/json;charset=UTF-8")
				.content(new ObjectMapper().writeValueAsString(question))
				)
		       .andExpect(status().isBadRequest())
		       .andExpect(content().contentType("text/plain;charset=ISO-8859-1"))
		       .andExpect(jsonPath("$", containsString("Enter the answer a")))
		       .andExpect(jsonPath("$", containsString("Enter the answer b")))
		       .andExpect(jsonPath("$", containsString("Please select a quiz")));
		
		verify(questionService, never()).updateQuestion(1L, question);
	}
	
	@Test
	public void deleteQuestionByIdTest() throws Exception {
		doNothing().when(questionService).deleteQuestionById(1L);
		
		mockMvc.perform(delete("/questions/1"))
		       .andExpect(status().isNoContent());
		
		verify(questionService, times(1)).deleteQuestionById(1L);
	}
	
	@Test
	public void deleteQuestionByIdNotFoundTest() throws Exception {
		doThrow(new ResourceNotFoundException("No question with id 1")).when(questionService).deleteQuestionById(1L);
		
		mockMvc.perform(delete("/questions/1"))
	               .andExpect(status().isNotFound())
	               .andExpect(content().contentType("text/plain;charset=ISO-8859-1"))
		       .andExpect(jsonPath("$", is("No question with id 1")));
		
		verify(questionService, times(1)).deleteQuestionById(1L);
	}
	
	private class QuestionValidator implements Validator {

		private final String nameRegex = "^[\\w\\s]{5,}$";

		@Override
		public boolean supports(Class<?> cls) {
			return cls.equals(Question.class);
		}

		@Override
		public void validate(Object obj, Errors errors) {
			Question question = (Question) obj;

			if (!question.getQuestionText().matches(nameRegex)) {
				errors.reject("quiz", null, "Enter a valid question");
			}

			if (question.getAnswerA() == null || question.getAnswerA().isEmpty()) {
				errors.reject("answerA", null, "Enter the answer a");
			}

			if (question.getAnswerB() == null || question.getAnswerB().isEmpty()) {
				errors.reject("answerB", null, "Enter the answer b");
			}

			if (question.getAnswerC() == null || question.getAnswerC().isEmpty()) {
				errors.reject("answerC", null, "Enter the answer c");
			}

			if (question.getAnswerD() == null || question.getAnswerD().isEmpty()) {
				errors.reject("answerD", null, "Enter the answer d");
			}

			if (question.getCorrectAnswer() == null) {
				errors.reject("answer", null, "Please select the correct answer");
			}

			if (question.getQuiz() == null) {
				errors.reject("quiz", null, "Please select a quiz");
			}

			if (question.getPoints() == null) {
				errors.reject("quiz", null, "Please select a points");
			}
		}

	}
	
}
