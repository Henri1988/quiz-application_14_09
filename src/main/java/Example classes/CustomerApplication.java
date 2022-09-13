//package backup;
//
//import spi.Dao;
//
//import java.util.Collection;
//import java.util.Optional;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//public class CustomerApplication {
//
//    private static final Logger LOGGER =
//            Logger.getLogger(CustomerApplication.class.getName());
//    private static final Dao<Customer, Integer> CUSTOMER_DAO = new PostgreSqlDao();
//
//    public static void main(String[] args) {
//        // Test whether an exception is thrown when
//        // the database is queried for a non-existent customer.
//        // But, if the customer does exist, the details will be printed
//        // on the console
//        try {
//            Customer customer = getCustomer(1);
//        } catch (infrastructure.NonExistentEntityException ex) {
//            LOGGER.log(Level.WARNING, ex.getMessage());
//        }
//
//        // Test whether a customer can be added to the database
//        Customer firstCustomer =
//                new Customer("Manuel", "Kelley", "ManuelMKelley@jourrapide.com");
//        Customer secondCustomer =
//                new Customer("Joshua", "Daulton", "JoshuaMDaulton@teleworm.us");
//        Customer thirdCustomer =
//                new Customer("April", "Ellis", "AprilMellis@jourrapide.com");
//        addCustomer(firstCustomer).ifPresent(firstCustomer::setId);
//        addCustomer(secondCustomer).ifPresent(secondCustomer::setId);
//        addCustomer(thirdCustomer).ifPresent(thirdCustomer::setId);
//
//
//    }
//
//    // Static helper methods referenced above
//    public static Customer getCustomer(int id) throws infrastructure.NonExistentEntityException {
//        Optional<Customer> customer = CUSTOMER_DAO.get(id);
//        return customer.orElseThrow(infrastructure.NonExistentCustomerException::new);
//    }
//
//    public static Collection<Customer> getAllCustomers() {
//        return CUSTOMER_DAO.getAll();
//    }
//
//    public static void updateCustomer(Customer customer) {
//        CUSTOMER_DAO.update(customer);
//    }
//
//    public static Optional<Integer> addCustomer(Customer customer) {
//        return CUSTOMER_DAO.save(customer);
//    }
//
//    public static void deleteCustomer(Customer customer) {
//        CUSTOMER_DAO.delete(customer);
//    }
//}