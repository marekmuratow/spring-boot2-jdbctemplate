package pl.entito.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class Application implements CommandLineRunner {

	private static Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public void run(String... args) throws Exception {

		// execute an SQL statements
		jdbcTemplate.execute(
				"CREATE TABLE CUSTOMER(id long auto_increment, first_name VARCHAR(255), last_name VARCHAR(255))");

		jdbcTemplate.execute("INSERT INTO CUSTOMER(first_name, last_name) VALUES('Crazy', 'Bob')");

		// execute an SQL statement providing array of binding arguments
		jdbcTemplate.update("INSERT INTO CUSTOMER(first_name, last_name) VALUES(?,?)",
				new Object[] { "Mark", "Twain" });

		// execute batch update providing with a batch of supplied arguments
		jdbcTemplate.batchUpdate("INSERT INTO CUSTOMER(first_name, last_name) VALUES (?,?)", createCustomers());

		// execute a query with a rowMapper, it doesn't throw exception if no result
		// TIP for a single result check if (!list.isEmpty()) and return list.get(0)
		List<Customer> queryCustomers = jdbcTemplate.query("SELECT * FROM CUSTOMER",
				(rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name")));
		log.info("customers: " + queryCustomers);

		// query for an Object providing a custom RowMapper
		Customer firstCustomer;
		try {
			firstCustomer = jdbcTemplate.queryForObject("Select * from CUSTOMER where id = 1", new CustomerRowMapper());
		} catch (EmptyResultDataAccessException e) {
			firstCustomer = null;
		}
		log.info("query for object  with id=1: " + firstCustomer);

		// query for a List of Objects providing a custom RowMapper
		List<Customer> customers;
		try {
			customers = jdbcTemplate.queryForObject("Select * from CUSTOMER", new CustomerListRowMapper());
		} catch (EmptyResultDataAccessException e) {
			customers = Collections.emptyList();
		}
		log.info("customers: " + customers);
	}

	private List<Object[]> createCustomers() {
		List<Object[]> names = new ArrayList<>();
		names.add(new String[] { "Bill", "Jones" });
		names.add(new String[] { "Mark", "Holl" });
		names.add(new String[] { "Fred", "Go" });
		return names;
	}

}