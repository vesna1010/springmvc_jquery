package com.vesna1010.quizzes.tests;

import com.vesna1010.quizzes.enums.Answer;
import com.vesna1010.quizzes.model.Question;
import com.vesna1010.quizzes.model.Quiz;

public class BaseTest {

	protected final Quiz quiz1 = new Quiz(1L, "Quiz A");
	protected final Quiz quiz2 = new Quiz(2L, "Quiz B");
	protected final Question question1 = new Question(1L, "Question A", "Answer A", "Answer B", "Answer C", "Answer D",
			Answer.A, quiz1, 10);
	protected final Question question2 = new Question(2L, "Question B", "Answer A", "Answer B", "Answer C", "Answer D",
			Answer.B, quiz1, 10);
	protected final Question question3 = new Question(3L, "Question C", "Answer A", "Answer B", "Answer C", "Answer D",
			Answer.C, quiz1, 10);

}
