INSERT INTO roles(id, name) VALUES (3, 'ROLE_USER');
INSERT INTO roles(id, name) VALUES (4, 'ROLE_ADMIN');

INSERT INTO users(id, email, name, password, provider) VALUES (1, 'test@gmail.com', 'test', 'test', 'local');

INSERT INTO users_roles(user_id, role_id) VALUES (1, 4);

INSERT INTO subjects(id, name) VALUES (1, 'C');
INSERT INTO subjects(id, name) VALUES (2, 'Java');
INSERT INTO subjects(id, name) VALUES (3, 'HTML');
INSERT INTO subjects(id, name) VALUES (4, 'CSS');
INSERT INTO courses(id, name, description, locked, subject_id, AUTHOR_OF_COURSE_ID) VALUES (1, 'C for beginner', 'Introduction in C', false, 1, 1);
INSERT INTO courses(id, name, description, locked, subject_id, AUTHOR_OF_COURSE_ID) VALUES (2, 'Java in action', 'Course about java', false, 2, 1);
INSERT INTO courses(id, name, description, locked, subject_id, AUTHOR_OF_COURSE_ID) VALUES (3, 'Java in use', 'Course about java', false, 2, 1);
INSERT INTO courses(id, name, description, locked, subject_id, AUTHOR_OF_COURSE_ID) VALUES (4, 'Clean code', 'Course about everything', false, 2, 1);
INSERT INTO courses(id, name, description, locked, subject_id, AUTHOR_OF_COURSE_ID) VALUES (5, 'HTML basics', 'For beginner', false, 3, 1);
INSERT INTO courses(id, name, description, locked, subject_id, AUTHOR_OF_COURSE_ID) VALUES (6, 'CSS in examples', 'Foundation of all', false, 3, 1);

INSERT INTO users_courses(user_id, course_id) VALUES (1, 2);
INSERT INTO users_courses(user_id, course_id) VALUES (1, 4);
INSERT INTO users_courses(user_id, course_id) VALUES (1, 6);

INSERT INTO lessons(id, num, name, description, course_id) VALUES (1, 1, 'First C lesson', 'it`s the first C lesson', 1);
INSERT INTO lessons(id, num, name, description, course_id) VALUES (2, 2, 'Second C lesson', 'it`s the second C lesson', 1);
INSERT INTO lessons(id, num, name, description, course_id) VALUES (3, 1, 'First java lesson', 'it`s the first java lesson', 2);
INSERT INTO lessons(id, num, name, description, course_id) VALUES (4, 2, 'Second java lesson', 'it`s the second java lesson', 2);
INSERT INTO lessons(id, num, name, description, course_id) VALUES (5, 3, 'Third java lesson', 'it`s the third java lesson', 2);

INSERT INTO pages(id, num, name, content, lesson_id) VALUES (1, 1, 'First Page', 'it`s the first page of the first C lesson', 1);
INSERT INTO pages(id, num, name, content, lesson_id) VALUES (2, 2, 'Second Page', 'it`s the Second page of the first C lesson', 1);
INSERT INTO pages(id, num, name, content, lesson_id) VALUES (3, 3, 'Third Page', 'it`s the Third page of the first C lesson', 1);
INSERT INTO pages(id, num, name, content, lesson_id) VALUES (4, 4, 'Fourth Page', 'it`s the fourth page of the first C lesson', 1);
INSERT INTO pages(id, num, name, content, lesson_id) VALUES (5, 5, 'Fifth Page', 'it`s the fifth page of the first C lesson', 1);

INSERT INTO questions(id, content, course_id) VALUES (1, 'Do you like C?', 1);
INSERT INTO questions(id, content, course_id) VALUES (2, 'What language are you learning now?', 1);

INSERT INTO answers(id, content, is_right, question_id) VALUES (1, 'Yes', TRUE, 1);
INSERT INTO answers(id, content, is_right, question_id) VALUES (2, 'NO', FALSE, 1);
INSERT INTO answers(id, content, is_right, question_id) VALUES (3, 'C', TRUE, 2);
INSERT INTO answers(id, content, is_right, question_id) VALUES (4, 'C#', FALSE, 2);










