import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.postgresql.util.PSQLException;
import spi.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class QuestionQuizApplicationTest {
    private static final Dao<Question, Integer> QUESTION_DAO = new QuestionDao();

    @Before
    public void beforeEachTestMethod() {
        System.out.println("Delete all");
        QUESTION_DAO.deleteAll();
    }

    @Test
    // each test follows the AAA principle
    public void addEmptyQuestion() {
        // Arrange
        var q = new Question();
        assertEquals(0, q.getId());
        assertNull(q.getName());

        // Act
        Exception exception = assertThrows(PSQLException.class, () -> {
            QUESTION_DAO.save(q);
        });

        // Assert
        assertTrue(exception.getMessage().contains("ERROR: null value in column \"name\" of relation \"question\" violates not-null constraint"));
    }

    @Test
    public void addEQuestionWithName() {
        var q = new Question("q1", null, 0);
        assertEquals(0, q.getId());
        assertEquals("q1", q.getName());
        assertNull(q.getTopic());

        Exception exception = assertThrows(PSQLException.class, () -> {
            QUESTION_DAO.save(q);
        });

        assertTrue(exception.getMessage().contains("ERROR: null value in column \"topic\" of relation \"question\" violates not-null constraint"));
    }

    @Test
    public void addEQuestionWithNameAndTopic() throws SQLException {
        var q = new Question("q1", "t1", 0);
        assertEquals(0, q.getId());
        assertEquals("q1", q.getName());
        assertEquals("t1", q.getTopic());

        QUESTION_DAO.save(q);
        assertNotEquals(0, q.getId());

        var freshQ = QUESTION_DAO.get(q.getId());
        assertNotNull(freshQ);
        assertEquals(q.toString(), freshQ.get().toString());
    }

    @Test
    public void addEQuestionWithNameTopicAndDifficulty() throws SQLException {
        var q = new Question("q1", "t1", 1);
        assertEquals(0, q.getId());
        assertEquals("q1", q.getName());
        assertEquals("t1", q.getTopic());
        assertEquals(1, q.getDifficulty());

        QUESTION_DAO.save(q);
        assertNotEquals(0, q.getId());

        var freshQ = QUESTION_DAO.get(q.getId());
        assertNotNull(freshQ);
        assertEquals(q.toString(), freshQ.get().toString());
    }

    @Test
    public void addSameQuestionTwice() throws SQLException {
        List<Question> savedQuestions = new ArrayList<>();
        for (int i = 0; i<2; i++){
            var q = new Question("q1", "t1", 1);
            assertEquals(0, q.getId());
            assertEquals("q1", q.getName());
            assertEquals("t1", q.getTopic());
            assertEquals(1, q.getDifficulty());

            QUESTION_DAO.save(q);
            assertNotEquals(0, q.getId());
            savedQuestions.add(q);

            var freshQ = QUESTION_DAO.get(q.getId());
            assertNotNull(freshQ);
            assertEquals(q.toString(), freshQ.get().toString());
        }

        assertEquals(2, savedQuestions.size());
        assertNotEquals(savedQuestions.get(0).getId(), savedQuestions.get(1).getId());
    }

    @Test
    public void getQuestion() {
    }

    @Test
    public void updateQuestion() {
    }

    @Test
    public void deleteQuestion() {
    }

    @Test
    public void searchQuestionsByTopicInEmptyDatabase() throws Exception {
        var questions = QUESTION_DAO.searchQuestionsByTopic("");

        assertEquals(0, questions.size());
    }

    @Test
    public void searchQuestionsByNotFoundTopic() throws Exception {
        QUESTION_DAO.save(new Question("q1", "t1", 1));

        var questions = QUESTION_DAO.searchQuestionsByTopic("t2");

        assertEquals(0, questions.size());
    }

    @Test
    public void search1QuestionByFoundTopic() throws Exception {
        var q1 = new Question("q1", "t1", 1);
        QUESTION_DAO.save(q1);

        var questions = QUESTION_DAO.searchQuestionsByTopic("t1");
        assertEquals(1, questions.size());

        var foundQ1 = questions.iterator().next();
        assertEquals(q1.toString(), foundQ1.toString());
    }

    @Test
    public void search2QuestionsByFoundTopic() throws Exception {
        var q1 = new Question("q1", "t1", 1);
        QUESTION_DAO.save(q1);
        var q2 = new Question("q2", "t1", 1);
        QUESTION_DAO.save(q2);

        var questions = QUESTION_DAO.searchQuestionsByTopic("t1").toArray();
        assertEquals(2, questions.length);

        assertEquals(q1.toString(), questions[0].toString());
        assertEquals(q2.toString(), questions[1].toString());
    }

    @Test
    public void searchQuestionsByPartiallyFoundTopic() throws Exception {
        var q1 = new Question("q1", "t1", 1);
        QUESTION_DAO.save(q1);
        var q2 = new Question("q2", "t2", 1);
        QUESTION_DAO.save(q2);

        var questions = QUESTION_DAO.searchQuestionsByTopic("t1").toArray();
        assertEquals(1, questions.length);

        assertEquals(q1.toString(), questions[0].toString());
    }

    @Test
    public void searchQuestionsByTopicThatIncludesApostrophe() throws Exception {
        var q1 = new Question("q1", "t1", 1);
        QUESTION_DAO.save(q1);
        var q2 = new Question("q2", "t2", 1);
        QUESTION_DAO.save(q2);

        var questions = QUESTION_DAO.searchQuestionsByTopic("'");
        assertEquals(0, questions.size());
    }

    @Test
    public void searchQuestionsBySqlInjectionTopic() throws Exception {
        var q1 = new Question("q1", "t1", 1);
        QUESTION_DAO.save(q1);
        var q2 = new Question("q2", "t2", 1);
        QUESTION_DAO.save(q2);

        var questions = QUESTION_DAO.searchQuestionsByTopic("'; insert into question(name, topic, difficulty) values ('q10', 't10', 10) --");
        assertEquals(0, questions.size());
    }
}