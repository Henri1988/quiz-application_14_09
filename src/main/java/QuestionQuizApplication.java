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
    }


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