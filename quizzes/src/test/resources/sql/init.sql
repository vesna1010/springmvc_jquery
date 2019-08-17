delete from questions;
delete from quizzes;

insert into quizzes(id, name) values(1, 'Quiz A');
insert into quizzes(id, name) values(2, 'Quiz B');

insert into questions(id, question_text, answer_a, answer_b, answer_c, answer_d, correct_answer, quiz_id, points)
values(1, 'Question A', 'Answer A', 'Answer B', 'Answer C', 'Answer D', 'A', 1, 10);

insert into questions(id, question_text, answer_a, answer_b, answer_c, answer_d, correct_answer, quiz_id, points)
values(2, 'Question B', 'Answer A', 'Answer B', 'Answer C', 'Answer D', 'B', 1, 20);

insert into questions(id, question_text, answer_a, answer_b, answer_c, answer_d, correct_answer, quiz_id, points)
values(3, 'Question C', 'Answer A', 'Answer B', 'Answer C', 'Answer D', 'C', 2, 30);

insert into questions(id, question_text, answer_a, answer_b, answer_c, answer_d, correct_answer, quiz_id, points)
values(4, 'Question D', 'Answer A', 'Answer B', 'Answer C', 'Answer D', 'D', 1, 30);

