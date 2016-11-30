package br.com.ead.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class RelatorioDao {
	
	public Connection getConnection() {
		try {
			return DriverManager.getConnection("jdbc:postgresql://localhost:5432/saescola", "cond", "cond");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
