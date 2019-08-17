package com.vesna1010.quizzes.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.vesna1010.quizzes.model.Quiz;
import com.vesna1010.quizzes.service.QuizService;

@Controller
public class HomeController {
	
	@Autowired
	private QuizService quizService;

	@GetMapping("/")
	public String renderHomePageWithQuizzes(Model model, HttpSession httpSession) {
		List<Quiz> quizzes = quizService.getAllQuizzes();

		model.addAttribute("quizzes", quizzes);
		httpSession.removeAttribute("quiz");

		return "home";
	}

	@GetMapping("/setQuiz")
	public String setQuizForPlay(@RequestParam Long id, HttpSession httpSession) {
		Quiz quiz = quizService.getQuizById(id);

		httpSession.setAttribute("quiz", quiz);

		return "playQuiz";
	}

	@GetMapping("/playQuiz")
	public String renderPlayQuizPage(HttpSession httpSession) {
		Quiz quiz = (Quiz) httpSession.getAttribute("quiz");

		return (quiz != null ? "playQuiz" : "redirect:/");
	}

	@GetMapping("/editQuizzes")
	public String renderEditQuizzesPage() {
		return "editQuizzes";
	}

	@GetMapping("/editQuestions")
	public String renderEditQuestionsPage() {
		return "editQuestions";
	}
}
