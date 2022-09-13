//package backup;
//
//import spi.Dao;
//
//import java.sql.*;
//import java.util.Collection;
//import java.util.Objects;
//import java.util.Optional;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//public class PostgreSqlDao implements Dao<Customer, Integer> {
//
//    private static final Logger LOGGER =
//            Logger.getLogger(PostgreSqlDao.class.getName());
//    private final Optional<Connection> connection;
//
//    public PostgreSqlDao() {
//        this.connection = JdbcConnection.getConnection();
//    }
//
//    @Override
//    public Optional<Customer> get(int id) {
//        return Optional.empty();
//    }
//
//    @Override
//    public Collection<Customer> getAll() {
//        return null;
//    }
//
//    @Override
//    public Optional<Integer> save(Customer customer) {
//        String message = "The customer to be added should not be null";
//        Customer nonNullCustomer = Objects.requireNonNull(customer, message);
//        String sql = "INSERT INTO "
//                + "customer(first_name, last_name, email) "
//                + "VALUES(?, ?, ?)";
//
//        return connection.flatMap(conn -> {
//            Optional<Integer> generatedId = Optional.empty();
//
//            try (PreparedStatement statement =
//                         conn.prepareStatement(
//                                 sql,
//                                 Statement.RETURN_GENERATED_KEYS)) {
//
//                statement.setString(1, nonNullCustomer.getFirstName());
//                statement.setString(2, nonNullCustomer.getLastName());
//                statement.setString(3, nonNullCustomer.getEmail());
//
//                int numberOfInsertedRows = statement.executeUpdate();
//
//                // Retrieve the auto-generated id
//                if (numberOfInsertedRows > 0) {
//                    try (ResultSet resultSet = statement.getGeneratedKeys()) {
//                        if (resultSet.next()) {
//                            generatedId = Optional.of(resultSet.getInt(1));
//                        }
//                    }
//                }
//
//                LOGGER.log(
//                        Level.INFO,
//                        "{0} created successfully? {1}",
//                        new Object[]{nonNullCustomer,
//                                (numberOfInsertedRows > 0)});
//            } catch (SQLException ex) {
//                LOGGER.log(Level.SEVERE, null, ex);
//            }
//
//            return generatedId;
//        });
//    }
//
//    @Override
//    public void update(Customer customer) {
//
//    }
//
//    @Override
//    public void delete(Customer customer) {
//
//    }
//
//
//
//    // Other methods of the interface which currently aren't implemented yet
//}