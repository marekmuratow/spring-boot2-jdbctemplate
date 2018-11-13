package pl.entito.demo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

public class CustomerListRowMapper implements RowMapper<List<Customer>> {

	@Override
	public List<Customer> mapRow(ResultSet rs, int rowNum) throws SQLException {
		List<Customer> customers = new ArrayList<>();
		do {
			customers.add(new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name")));
		} while (rs.next());
		return customers;
	}

}
