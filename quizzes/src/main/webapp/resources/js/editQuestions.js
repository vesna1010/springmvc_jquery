$(document).ready(function() {
	
	var quizzes = [];
	
	getAllQuizzes();
	
	function getAllQuizzes() {
		$.ajax({
			url : 'quizzes',
			type : 'get',
			dataType : 'json',
			success : function(data){
				quizzes = data;
				appendQuizzesInDialog(data);
			}
		});
	}
	
	function appendQuizzesInDialog(quizzes) {
		quizzes.forEach(function(quiz) {
			appendQuizInDialog(quiz);
		});
	}
	
	function appendQuizInDialog(quiz) {
		$('[name=quiz]').append('<option value=' + quiz.id + '>' + quiz.name + '</option>');
	}
	
	getAllQuestions();
	
	function getAllQuestions() {
		$.ajax({
			url : 'questions',
			type : 'get',
			dataType : 'json',
			success : function(questions) {
				appendQuestionsInTable(questions);
			}
		});
	}
	
	function appendQuestionsInTable(questions) {
		questions.forEach(function(question) {
			appendQuestionRowInTable(question);
		});
	}
	
	function saveQuestion() {
		$.ajax({
			url : 'questions',
			type : 'post',
			data : JSON.stringify(getQuestionFromDialog()),
			contentType : 'application/json',
			dataType : 'json',
			success : function(question) {
				appendQuestionRowInTable(question);
				closeDialog();
			},
			error : function(response) {
				alert(response.responseText);
			}
		});
	}
	
	function appendQuestionRowInTable(question) {
		$('.table-responsive tbody').append(getTableRowWithQuestion(question));
		setButtonFunctions(question);
	}
	
	function updateQuestion() {
		var question = getQuestionFromDialog();
		
		$.ajax({
			url : 'questions/' + question.id,
			type : 'put',
			data : JSON.stringify(question),
			contentType : 'application/json',
			dataType : 'json',
			success : function(question) {
				updateQuestionRowInTable(question);
				closeDialog();
			},
			error : function(response) {
				alert(response.responseText);
			}
		});
	}
	
	function getQuestionFromDialog() {
		var question = {};
		
		$('[type=hidden], [type=text], [type=radio]:checked, select').each(function() {
			question[$(this).attr('name')] = $(this).val();
		});
		
		question.quiz = getQuizById(question.quiz);
			
		return question;
	}
	
	function getQuizById(id) {
		return quizzes.filter(function(quiz) {
			return (quiz.id == id);
		})[0];
	}
	
	function updateQuestionRowInTable(question) {
		$('tbody tr#' + question.id).replaceWith(getTableRowWithQuestion(question));
		setButtonFunctions(question);
	}
	
	function getTableRowWithQuestion(question) {
		return (`<tr id="${question.id}"><td>${question.id}</td><td>${question.questionText}</td>
		         <td>${question.answerA}</td><td>${question.answerB}</td><td>${question.answerC}</td>
		         <td>${question.answerD}</td><td>${question.points}</td><td>${question.quiz.name}</td>
		         <td>${question.correctAnswer}</td><td><button id="${question.id}" class="btn btn-primary">Edit</button>
		         <button id="${question.id}" class="btn btn-danger">Delete</button></td></tr>`);
	}
	
	function setButtonFunctions(question) {
		$('.btn-primary[id=' + question.id + ']').bind('click', function() {
			appendQuestionInDialog(question);
		});
		
		$('.btn-danger[id=' + question.id + ']').bind('click', function() {
			deleteQuestionById(question.id);
		});
	}
	
	function appendQuestionInDialog(question) {
		$(':input').not('[type=radio], [name=quiz]').each(function() {
			$('[name=' + $(this).attr('name') + ']').val(question[$(this).attr('name')]);
		});
			
		$(':radio[value=' + question.correctAnswer + ']').prop('checked', true);
		$('[name=quiz]').val(question.quiz.id);
		
		openDialog('Edit Question', { 'Update' : updateQuestion, 'Cancel' : closeDialog });
	}
	
	function deleteQuestionById(id) {
		$.ajax({
			url : 'questions/' + id,
			type : 'delete',
			success : function(data) {
				deleteQuestionRowFromTable(id);
			},
			error : function(response) {
				alert(response.responseText);
			}
		});
	}

	function deleteQuestionRowFromTable(id) {
		$('tbody tr#' + id).remove();
	}
	
	$('#questionDialog').dialog({
		minWidth : 500,
		autoOpen : false
	});
	
	$('#openQuestionDialog').click(function() {
		openDialog('Add Question', { 'Save' : saveQuestion, 'Cancel' : closeDialog });
	});
	
	function openDialog(title, buttons) {
		$('#questionDialog').dialog({
			title : title,
			buttons : buttons
		}).dialog('open');
	}
	
	function closeDialog(){
		$(':input').not(':radio').val('');
		$(':radio').attr('checked', false);
		$('#questionDialog').dialog('close');
	}
	
});