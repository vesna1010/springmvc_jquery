$(document).ready(function() {

	var threeQuestions = [];
	var numberOfQuestion = 1;
	var score = 0;

	getThreeeQuestions();

	function getThreeeQuestions() {
		$.ajax({
			url : 'questions/three',
			type : 'get',
			dataType : 'json',
			success : function(data) {
				threeQuestions = data;
				showCurrentQuestion();
			}
		});
	}
	
	function showCurrentQuestion() {
		var currentQuestion = threeQuestions[numberOfQuestion - 1];
		
		$('#quizName').text(currentQuestion.quiz.name);
		$('#numberOfQuestion').text(numberOfQuestion);
		$('#score').text(score);
		$('#questionText').text(`Answer question for ${currentQuestion.points} points.
		                    \n${currentQuestion.questionText}`);
		
		$('.wraper span').each(function() {
			 $(this).next().text(currentQuestion['answer' + $(this).attr('id')]);
		 });
			 
		$('.wraper').bind('click', showResult);
	}
	
	function showResult() {
		var playerAnswer = $(this).children('span').text();
		
		if(isCorrectAnswer(playerAnswer)) {
			showCorrectResult(playerAnswer);
		} else {
			showIncorrectResult(playerAnswer);
		}
		
		$('.wraper').unbind();
    }
    
	function isCorrectAnswer(playerAnswer) {
		return (playerAnswer === threeQuestions[numberOfQuestion-1].correctAnswer);
	}
	
	function showCorrectResult(playerAnswer) {
		score = score + threeQuestions[numberOfQuestion-1].points;
		
		$('span#' + playerAnswer).parent().addClass('bg-success');
		$('#questionText').addClass('text-success').text('CORRECT');
		$('#score').text(score);
	}
	
	function showIncorrectResult(playerAnswer) {
		var correctAnswer = threeQuestions[numberOfQuestion-1].correctAnswer;
		
		$('span#' + playerAnswer).parent().addClass('bg-danger');
		$('span#' + correctAnswer).parent().addClass('bg-success');
		$('#questionText').addClass('text-danger').text('INCORRECT');
	}
	
    $('#nextQuestion').click(function() {
	    removeClasses();
	
	    if(++ numberOfQuestion === 3) {
		    setVisibilityButtons();
	    }
	
	    showCurrentQuestion();
    });
	
    function removeClasses() {
	    $('.wraper').removeClass('bg-success bg-danger');
	    $('h3').removeClass('text-success text-danger');
    }
	
    function setVisibilityButtons() {
	    $('#nextQuestion').addClass('invisible');
	    $('#changeQuiz').removeClass('invisible');
    }

});