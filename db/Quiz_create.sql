-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2022-09-12 15:40:14.343

-- tables
-- Table: answer
CREATE TABLE answer (
    id serial  NOT NULL,
    name varchar(100)  NOT NULL,
    correct boolean  NOT NULL,
    question_id int  NOT NULL,
    CONSTRAINT answer_pk PRIMARY KEY (id)
);

-- Table: question
CREATE TABLE question (
    id serial  NOT NULL,
    name varchar(255)  NOT NULL,
    topic varchar(50)  NOT NULL,
    difficulty int  NOT NULL,
    CONSTRAINT question_pk PRIMARY KEY (id)
);

-- foreign keys
-- Reference: question_answer_question (table: answer)
ALTER TABLE answer ADD CONSTRAINT question_answer_question
    FOREIGN KEY (question_id)
    REFERENCES question (id)
    ON DELETE  CASCADE  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- End of file.

