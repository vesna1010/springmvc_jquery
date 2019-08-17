package com.vesna1010.quizzes.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import com.vesna1010.quizzes.enums.Answer;

@Entity
@Table(name = "QUESTIONS")
public class Question implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String questionText;
	private String answerA;
	private String answerB;
	private String answerC;
	private String answerD;
	private Answer correctAnswer;
	private Quiz quiz;
	private Integer points;

	public Question() {
	}

	public Question(String questionText, String answerA, String answerB, String answerC, String answerD,
			Answer correctAnswer, Quiz quiz, Integer points) {
		this(null, questionText, answerA, answerB, answerC, answerD, correctAnswer, quiz, points);
	}

	public Question(Long id, String questionText, String answerA, String answerB, String answerC, String answerD,
			Answer correctAnswer, Quiz quiz, Integer points) {
		this.id = id;
		this.questionText = questionText;
		this.answerA = answerA;
		this.answerB = answerB;
		this.answerC = answerC;
		this.answerD = answerD;
		this.correctAnswer = correctAnswer;
		this.quiz = quiz;
		this.points = points;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Pattern(regexp = "^[\\w\\s]{5,}$", message = "Enter a valid question")
	@Column(name = "QUESTION_TEXT")
	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	@NotEmpty(message = "Enter the answer a")
	@Column(name = "ANSWER_A")
	public String getAnswerA() {
		return answerA;
	}

	public void setAnswerA(String answerA) {
		this.answerA = answerA;
	}

	@NotEmpty(message = "Enter the answer b")
	@Column(name = "ANSWER_B")
	public String getAnswerB() {
		return answerB;
	}

	public void setAnswerB(String answerB) {
		this.answerB = answerB;
	}

	@NotEmpty(message = "Enter the answer c")
	@Column(name = "ANSWER_C")
	public String getAnswerC() {
		return answerC;
	}

	public void setAnswerC(String answerC) {
		this.answerC = answerC;
	}

	@NotEmpty(message = "Enter the answer d")
	@Column(name = "ANSWER_D")
	public String getAnswerD() {
		return answerD;
	}

	public void setAnswerD(String answerD) {
		this.answerD = answerD;
	}

	@NotNull(message = "Please select the correct answer")
	@Enumerated(EnumType.STRING)
	@Column(name = "CORRECT_ANSWER")
	public Answer getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(Answer correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	@NotNull(message = "Please select a quiz")
	@ManyToOne
	@JoinColumn(name = "QUIZ_ID")
	public Quiz getQuiz() {
		return quiz;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	@NotNull(message = "Please select a points")
	@Column(name = "POINTS")
	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
