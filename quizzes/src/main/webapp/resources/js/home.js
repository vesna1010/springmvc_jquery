$(document).ready(function() {

	getAllQuizzes();

	function getAllQuizzes() {
		$.ajax({
			url : 'quizzes',
			type : 'get',
			dataType : 'json',
			success : function(quizzes) {
				appendQuizzesInForm(quizzes);
			}
		});
	}

	function appendQuizzesInForm(quizzes) {
		quizzes.forEach(function(quiz) {
			appendQuizInForm(quiz);
		});
	}

	function appendQuizInForm(quiz) {
		$('select').append(`<option value="${quiz.id}">${quiz.name}</option>`);
	}

});