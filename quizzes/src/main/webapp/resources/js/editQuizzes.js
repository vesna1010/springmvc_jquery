$(document).ready(function() {
	
	getAllQuizzes();
	
	function getAllQuizzes() {
		$.ajax({
			url : 'quizzes',
			type : 'get',
			dataType : 'json',
			success : function(quizzes){
				appendQuizzesInTable(quizzes);
			}
		});
	}
	
	function appendQuizzesInTable(quizzes) {
		quizzes.forEach(function(quiz) {
			appendQuizRowInTable(quiz);
		});
	}
	
	function saveQuiz() {
		$.ajax({
			url : 'quizzes',
			type : 'post',
			data : JSON.stringify(getQuizFromDialog()),
			contentType : 'application/json',
			dataType : 'json',
			success : function(quiz) {
				appendQuizRowInTable(quiz);
				closeDialog();
			},
			error : function(response) {
				alert(response.responseText);
			}
		});
	}
	
	function appendQuizRowInTable(quiz) {
		$(".table-responsive tbody").append(getTableRowWithQuiz(quiz));
		setButtonFunctions(quiz);
	}
	
	function updateQuiz() {
		var quiz = getQuizFromDialog();
		
		$.ajax({
			url : 'quizzes/' + quiz.id,
			type : 'put',
			data : JSON.stringify(quiz),
			contentType : 'application/json',
			dataType : 'json',
			success : function(quiz) {
				updateQuizRowInTable(quiz);
				closeDialog();
			},
			error : function(response) {
				alert(response.responseText);
			}
		});
	}
	
	function getQuizFromDialog(){
		var quiz = {};
		
		$("input").each(function() {
			quiz[$(this).attr('name')] = $(this).val();
		});
			
		return quiz;
	}

	function updateQuizRowInTable(quiz) {
		$("tbody tr#" + quiz.id).replaceWith(getTableRowWithQuiz(quiz));
		setButtonFunctions(quiz);
	}
	
	function getTableRowWithQuiz(quiz) {
		return (`<tr id="${quiz.id}"><td>${quiz.id}</td><td>${quiz.name}</td><td>
		        <button id="${quiz.id}" class="btn btn-primary">Edit</button>
		        <button id="${quiz.id}" class="btn btn-danger">Delete</button></td></tr>`);
	}
	
	function setButtonFunctions(quiz) {
		$('.btn-primary[id=' + quiz.id + ']').bind('click', function() {
			appendQuizInDialog(quiz);
		});
		
		$('.btn-danger[id=' + quiz.id + ']').bind('click', function() {
			deleteQuizById(quiz.id);
		});
	}
	
	function appendQuizInDialog(quiz) {
		for(let prop in quiz) {
			$('input[name=' + prop + ']').val(quiz[prop]);
		}
		
		openDialog('Edit Quiz', { 'Update' : updateQuiz, 'Cancel' : closeDialog });
	}
	
	function deleteQuizById(id) {
		$.ajax({
			url : 'quizzes/' + id,
			type : 'delete',
			success : function(data) {
				deleteQuizRowFromTable(id);
			},
			error : function(response) {
				alert(response.responseText);
			}
		});
	}

	function deleteQuizRowFromTable(id) {
		$("tbody tr#" + id).remove();
	}
	
	$("#quizDialog").dialog({
		minWidth : 500,
		autoOpen : false
	});
	
	$("#openQuizDialog").click(function() {
		openDialog('Add Quiz', { 'Save' : saveQuiz, 'Cancel' : closeDialog });
	});
	
	function openDialog(title, buttons) {
		$('#quizDialog').dialog({
			title : title,
			buttons : buttons
		}).dialog('open');
	}
	
	function closeDialog(){
		$('input').val('');
		$("#quizDialog").dialog('close');
	}
	
});
