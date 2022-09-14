import spi.Dao;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuestionDao implements Dao<Question, Integer> {
    private static final Logger LOGGER =
            Logger.getLogger(QuestionDao.class.getName());
    private final Optional<Connection> connection;


    public QuestionDao() {
        this.connection = JdbcConnection.getConnection();
    }

    @Override
    public Optional<Question> get(int id) {
        return connection.flatMap(conn -> {
            Optional<Question> question = Optional.empty();
            // TODO: use prepareStatemnt to aviod sql injection, like searchByTopicMethod
            String sql = "SELECT * FROM question WHERE id = " + id;

            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String topic = resultSet.getString("topic");
                    Integer difficulty = resultSet.getInt("difficulty");

                    question = Optional.of(new Question(id, name, topic, difficulty));

                    LOGGER.log(Level.INFO, "Found {0} in database", question.get());
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }

            return question;
        });
    }

    @Override
    public Collection<Question> getAll() {
        return null;
    }

    @Override
    public void save(Question question) throws SQLException {
        String message = "The question to be added should not be null";
        Question nonNullQuestion = Objects.requireNonNull(question, message);
        String sql = "INSERT INTO "
                + "question(name, topic, difficulty) "
                + "VALUES(?, ?, ?)";

        if (connection.isEmpty()) {
            return;
        }
        var conn = connection.get();

        PreparedStatement statement =
                conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, nonNullQuestion.getName());
        statement.setString(2, nonNullQuestion.getTopic());
        statement.setInt(3, nonNullQuestion.getDifficulty());

        int numberOfInsertedRows = statement.executeUpdate();

        // Retrieve the auto-generated id
        if (numberOfInsertedRows > 0) {
            // TODO Remove try-catches and throw exception instead from here and all other methods.
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    question.setId(resultSet.getInt(1));
                }
            }
        }

        LOGGER.log(
                Level.INFO,
                "{0} created successfully? {1}",
                new Object[]{nonNullQuestion,
                        (numberOfInsertedRows > 0)});
    }

    @Override
    public void update(Question question) {
        String message = "The question to be updated should not be null";
        Question nonNullQuestion = Objects.requireNonNull(question, message);
        String sql = "UPDATE question "
                + "SET "
                + "name = ?, "
                + "topic = ?, "
                + "difficulty = ? "
                + "WHERE "
                + "id = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setString(1, nonNullQuestion.getName());
                statement.setString(2, nonNullQuestion.getTopic());
                statement.setInt(3, nonNullQuestion.getDifficulty());
                statement.setInt(4, nonNullQuestion.getId());

                int numberOfUpdatedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the question updated successfully? {0}",
                        numberOfUpdatedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });

    }

    @Override
    public void delete(Question question) {
        String message = "The question to be deleted should not be null";
        Question nonNullQuestion = Objects.requireNonNull(question, message);
        String sql = "DELETE FROM question WHERE id = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setInt(1, nonNullQuestion.getId());

                int numberOfDeletedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the question deleted successfully? {0}",
                        numberOfDeletedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public Collection<Question> searchQuestionsByTopic(String searchTopic) throws SQLException {
        List<Question> questions = new ArrayList<>();

        if (connection.isEmpty()) {
            return questions;
        }
        var conn = connection.get();

        String sql = "SELECT * FROM question WHERE topic = ?";
        System.out.println(sql);

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, searchTopic);

        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String topic = resultSet.getString("topic");
            Integer difficulty = resultSet.getInt("difficulty");

            Question question = new Question(id, name, topic, difficulty);
            questions.add(question);

            LOGGER.log(Level.INFO, "Found {0} in database", question);
        }

        return questions;
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM question";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.executeUpdate();
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }
}