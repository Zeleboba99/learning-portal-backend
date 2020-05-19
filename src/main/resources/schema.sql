drop table if exists SUBJECTS cascade;
drop table if exists COURSES cascade;
drop table if exists LESSONS cascade;
drop table if exists PAGE_TYPES cascade;
drop table if exists PAGES cascade;
drop table if exists USERS cascade;
drop table if exists PRIVILEGES cascade;
drop table if exists ROLES cascade;
drop table if exists QUESTIONS cascade;
drop table if exists ANSWERS cascade;
drop table if exists USERS_ROLES cascade;
drop table if exists ROLES_PRIVILEGES cascade;
drop table if exists USERS_COURSES cascade;
drop table if exists TEST_RESULTS cascade;
drop sequence if exists HIBERNATE_SEQUENCE ;
drop sequence if exists SUBJECT_SEQUENCE ;
drop sequence if exists COURSE_SEQUENCE ;
drop sequence if exists LESSON_SEQUENCE ;
drop sequence if exists PAGE_TYPE_SEQUENCE ;
drop sequence if exists PAGE_SEQUENCE ;
drop sequence if exists USER_SEQUENCE ;
drop sequence if exists ROLE_SEQUENCE ;
drop sequence if exists QUESTION_SEQUENCE ;
drop sequence if exists ANSWER_SEQUENCE ;
drop sequence if exists TEST_RESULT_SEQUENCE ;

CREATE SEQUENCE IF NOT EXISTS SUBJECT_SEQUENCE START WITH 10 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS COURSE_SEQUENCE START WITH 10 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS LESSON_SEQUENCE START WITH 10 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS PAGE_TYPE_SEQUENCE START WITH 10 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS PAGE_SEQUENCE START WITH 10 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS USER_SEQUENCE START WITH 10 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS ROLE_SEQUENCE START WITH 10 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS QUESTION_SEQUENCE START WITH 10 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS ANSWER_SEQUENCE START WITH 10 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS TEST_RESULT_SEQUENCE START WITH 10 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS USERS(
    ID SERIAL  PRIMARY KEY,
    EMAIL VARCHAR(200) NOT NULL,
    EMAIL_VERIFIED BOOLEAN,
    IMAGE_URL VARCHAR(255),
    IMAGE BYTEA,
    NAME VARCHAR(200),
    PASSWORD VARCHAR(200),
    PROVIDER VARCHAR(200),
    PROVIDER_ID VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS SUBJECTS
(
    ID SERIAL  PRIMARY KEY,
    NAME VARCHAR(200) NOT NULL
);

CREATE TABLE IF NOT EXISTS COURSES
(
    ID SERIAL  PRIMARY KEY,
    NAME VARCHAR(200) NOT NULL,
    DESCRIPTION VARCHAR(500),
    LOCKED BOOLEAN,
    THRESHOLD INTEGER,
    SUBJECT_ID INTEGER REFERENCES SUBJECTS(ID),
    AUTHOR_OF_COURSE_ID INTEGER REFERENCES USERS(ID)
);

CREATE TABLE IF NOT EXISTS LESSONS
(
    ID SERIAL  PRIMARY KEY,
    NUM INTEGER NOT NULL,
    NAME VARCHAR(200) NOT NULL,
    DESCRIPTION VARCHAR(500),
    COURSE_ID INTEGER REFERENCES COURSES(ID)
);

CREATE TABLE IF NOT EXISTS PAGE_TYPES
(
    ID SERIAL PRIMARY KEY,
    TYPE_NAME VARCHAR(200) NOT NULL
);

CREATE TABLE IF NOT EXISTS PAGES
(
    ID SERIAL  PRIMARY KEY,
    NUM INTEGER NOT NULL,
    NAME VARCHAR(200) NOT NULL,
    CONTENT VARCHAR(10000),
    IMAGE BYTEA,
    LESSON_ID INTEGER REFERENCES LESSONS(ID),
    PAGE_TYPE_ID INTEGER REFERENCES PAGE_TYPES(ID)
);

CREATE TABLE IF NOT EXISTS ROLES(
    ID SERIAL  PRIMARY KEY,
    NAME VARCHAR(200) NOT NULL
);

CREATE TABLE IF NOT EXISTS QUESTIONS
(
    ID SERIAL  PRIMARY KEY,
    CONTENT VARCHAR(500),
    COURSE_ID INTEGER REFERENCES COURSES(ID)
);

CREATE TABLE IF NOT EXISTS ANSWERS
(
    ID SERIAL  PRIMARY KEY,
    IS_RIGHT BOOLEAN,
    CONTENT VARCHAR(500),
    QUESTION_ID INTEGER REFERENCES QUESTIONS(ID)
);

CREATE TABLE IF NOT EXISTS USERS_ROLES(
    USER_ID INTEGER REFERENCES USERS(ID),
    ROLE_ID INTEGER REFERENCES ROLES(ID)
);

CREATE TABLE IF NOT EXISTS USERS_COURSES
(
    USER_ID INTEGER REFERENCES USERS(ID),
    COURSE_ID INTEGER REFERENCES COURSES(ID)
);

CREATE TABLE IF NOT EXISTS TEST_RESULTS
(
    ID SERIAL  PRIMARY KEY,
    NUM INTEGER NOT NULL,
    PASSED_AT TIMESTAMP,
    ANSWERS_NUM INTEGER,
    RIGHT_ANSWERS_NUM INTEGER,
    USER_ID INTEGER REFERENCES USERS(ID),
    COURSE_ID INTEGER REFERENCES COURSES(ID)
);



