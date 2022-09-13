import infrastructure.NonExistentQuestionException;
import infrastructure.NonExistentEntityException;
import spi.Dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuestionQuizApplication {

    private static final Logger LOGGER =
            Logger.getLogger(QuestionQuizApplication.class.getName());
    private static final Dao<Question, Integer> QUESTION_DAO = new QuestionDao();

    public static void main(String[] args) throws SQLException {
        // Test whether an exception is thrown when
        // the database is queried for a non-existent question.
        // But, if the question does exist, the details will be printed
        // on the console
        try {
            Question question = getQuestion(1);
        } catch (NonExistentEntityException ex) {
            LOGGER.log(Level.WARNING, ex.getMessage());
        }

        // ADDING QUESTION TO DATABASE
        Question firstQuestion =
                new Question("What is the capital of France?", "Geography", 1);
        Question secondQuestion =
                new Question("What is 2 + 2?", "Mathematics", 1);
        Question thirdQuestion =
                new Question("Who won the 2018 FIFA World Cup?", "Sports", 3);
        Question fourthQuestion =
                new Question("What is the most Northern Capital City?", "Geography", 2);

        addQuestion(firstQuestion);
        addQuestion(secondQuestion);
        addQuestion(thirdQuestion);
        addQuestion(fourthQuestion);

        //UPDATE QUESTION IN THE DATABASE
        secondQuestion.setName("What is 100 + 1?");
        updateQuestion(secondQuestion);

        //DELETE QUESTION FROM THE DATABASE
        deleteQuestion(fourthQuestion);


    }

    // Static helper methods referenced above

    public static Question getQuestion(int id) throws NonExistentEntityException {
        Optional<Question> question = QUESTION_DAO.get(id);
        return question.orElseThrow(NonExistentQuestionException::new);
    }
    public static void updateQuestion(Question question) {
        QUESTION_DAO.update(question);
    }
    public static void addQuestion(Question question) throws SQLException {
        QUESTION_DAO.save(question);
    }
    public static void deleteQuestion(Question question) {
        QUESTION_DAO.delete(question);
    }
}