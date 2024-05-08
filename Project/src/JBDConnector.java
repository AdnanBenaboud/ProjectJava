
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.sql.*;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;

import java.util.ArrayList;

public class JBDConnector {

	// les colonnes des tables dans la BDD
	private String[] etudiant = { "id_etudiant", "nom_etudiant", "prenom_etudiant", "id_semestre", "id_filiere" };
	private String[] filiere = { "id_filiere", "nom_filiere", "objectif_filiere" };
	private String[] matiere = { "id_matiere", "nom_matiere", "description_matiere", "volume_horaire_matiere",
			"coefficient_matiere", "id_module" };
	private String[] module = { "id_module", "nom_module", "description_module", "id_semestre", "id_filiere" };
	private String[] semestre = { "id_semestre", "nom_semestre", "id_filiere", "annee_semestre" };
	private String[] notes = { "id_etudiant", "id_matiere", "nom_etudiant", "prenom_etudiant", "nom_matiere",
			"coefficient_matiere", "note_finale" };
	private String[] note = { "id_etudiant, id_matiere, note_finale" };

	public JBDConnector() {

	}

	// read columns of a table
	public String[][] read(String TableName) {
		Properties props = new Properties();
		try (FileInputStream fis = new FileInputStream("conf.properties")) {
			props.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Start creating script to be executed
		String script = "SELECT * FROM " + TableName;
		try {
			// Extract data from conf.proprities file where informations concerning our DB
			// are stored
			Class.forName(props.getProperty("jdbc.driver.class"));
			String url = props.getProperty("jdbc.url");
			String login = props.getProperty("jdbc.login");
			String password = props.getProperty("jdbc.password");

			// System.out.println("Database connection...");
			try (Connection con = DriverManager.getConnection(url, login, password)) {

				try (Statement statem = con.createStatement()) {
					System.out.println("Excecuting: " + script);
					// Execution to get a ResultSet
					ResultSet result = statem.executeQuery(script);

					// System.out.println("Excecution done.");
					// Extract results from ResultSet
					return extractResults(result, TableName);
				}
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String[][] extractResults(ResultSet result, String TableName) {
		// turn Result set to String[][]
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		String columns[] = getColumnsOfTable(TableName);
		try {
			while (result.next()) {
				ArrayList<String> row = new ArrayList<String>();

				for (int i = 0; i < columns.length; i++) {
					// Get object in each column, them turn it to string.
					row.add(result.getObject(columns[i]).toString());

				}
				data.add(row);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return TwoDArrayToList(data);

	}

	private String[][] extractResults(ResultSet result, String[] columns) {
		// get values only for specific columns
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		try {
			while (result.next()) {
				ArrayList<String> row = new ArrayList<String>();

				for (int i = 0; i < columns.length; i++) {
					row.add(result.getObject(columns[i]).toString());

				}
				data.add(row);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return TwoDArrayToList(data);
	}

	// modify a table
	public void update(String TableName, String[] newRow, String[] oldRow) {
		Properties props = new Properties();

		String[] columns = getColumnsOfTable(TableName);

		try (FileInputStream fis = new FileInputStream("conf.properties")) {
			props.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Start creating script to be executed
		try {
			// Extract data from conf.proprities file where informations concerning our DB
			// are stored
			Class.forName(props.getProperty("jdbc.driver.class"));
			String url = props.getProperty("jdbc.url");
			String login = props.getProperty("jdbc.login");
			String password = props.getProperty("jdbc.password");

			// System.out.println("Database connection...");
			// Make new connection
			try (Connection con = DriverManager.getConnection(url, login, password)) {

				// Create script to be executed for the updates
				String script = String.format("UPDATE `%s` SET", TableName);

				try {
					for (int i = 0; i < columns.length; i++) {
						if (i == columns.length - 1) {
							script += String.format(" `%s` = '%s'", columns[i], newRow[i]);
						} else {
							script += String.format(" `%s` = '%s',", columns[i], newRow[i]);

						}
					}
				} catch (Exception error) {
					error.printStackTrace();
				}

				// to get for example: "WHERE ID=2 AND NAME="CRISTIANO"" etc etc
				script += String.format(" WHERE `%s` = '%s'", columns[0], oldRow[0]);

				try {
					for (int i = 1; i < columns.length; i++) {
						script += String.format("AND `%s` = '%s'", columns[i], oldRow[i]);
					}
				}

				catch (Exception error) {
					error.printStackTrace();
				}
				try (Statement statem = con.createStatement()) {
					System.out.println("Excecuting: " + script);

					statem.executeUpdate(script);

					// System.out.println("Excecution done.");

				} catch (SQLIntegrityConstraintViolationException e) {
					System.out.println("**Update invalide**");
					System.out.println("**Excecution Failed**");
				} catch (MysqlDataTruncation e) {
					System.out.println("**Valeur de type invalide**");
					System.out.println("**Excecution Failed**");

				}
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public void add(String TableName, String[] values) {

		String[] columns = getColumnsOfTable(TableName);

		Properties props = new Properties();
		try (FileInputStream fis = new FileInputStream("conf.properties")) {
			props.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Start creating script to be executed
		try {
			// Extract data from conf.proprities file where informations concerning our DB
			// are stored
			Class.forName(props.getProperty("jdbc.driver.class"));
			String url = props.getProperty("jdbc.url");
			String login = props.getProperty("jdbc.login");
			String password = props.getProperty("jdbc.password");

			// System.out.println("Database connection...");
			// Make new connection
			try (Connection con = DriverManager.getConnection(url, login, password)) {
				String columnNames = "";
				String valuesScript = "";

				// for "etudiant" we don't need to mention id_etudiant in script because it is
				// AUTO-INCREMENT
				int startColumn = TableName == "etudiant" ? 1 : 0;
				for (int i = startColumn; i <= columns.length - 1; i++) {
					if (i != columns.length - 1) {
						columnNames += String.format("`%s`, ", columns[i]);

					} else {
						columnNames += String.format("`%s`", columns[i]);
					}
				}

				for (int i = 0; i <= values.length - 1; i++) {
					if (i == values.length - 1) {
						valuesScript += String.format("'%s'", values[i]);

					} else {
						System.out.println(i);
						valuesScript += String.format("'%s', ", values[i]);
					}
				}
				// Create script to be executed for the updates
				String script = String.format("INSERT INTO `%s`(%s) VALUES (%s)", TableName, columnNames, valuesScript);

				try {

				} catch (Exception error) {
					error.printStackTrace();
				}

				try (Statement statem = con.createStatement()) {
					System.out.println("Excecuting: " + script);

					statem.executeUpdate(script);

					// System.out.println("Excecution done.");

				}
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete(String TableName, String[] columns, String[] values) {
		Properties props = new Properties();

		try (FileInputStream fis = new FileInputStream("conf.properties")) {
			props.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Start creating script to be executed
		try {
			// Extract data from conf.proprities file where informations concerning our DB
			// are stored
			Class.forName(props.getProperty("jdbc.driver.class"));
			String url = props.getProperty("jdbc.url");
			String login = props.getProperty("jdbc.login");
			String password = props.getProperty("jdbc.password");

			// System.out.println("Database connection...");
			// Make new connection
			try (Connection con = DriverManager.getConnection(url, login, password)) {

				// Create script to be executed for the delete
				String script = String.format("DELETE FROM `%s`", TableName);

				script += String.format(" WHERE `%s` = '%s'", columns[0], values[0]);

				try {
					for (int i = 1; i < columns.length; i++) {
						script += String.format("AND `%s` = '%s'", columns[i], values[i]);
					}
				}

				catch (Exception error) {
					error.printStackTrace();
				}
				try (Statement statem = con.createStatement()) {
					System.out.println("Excecuting: " + script);

					statem.executeUpdate(script);

					// System.out.println("Excecution done.");

				} catch (SQLIntegrityConstraintViolationException e) {
					System.out.println("**Update invalide**");
					System.out.println("**Excecution Failed**");
				} catch (MysqlDataTruncation e) {
					System.out.println("**Valeur de type invalide**");
					System.out.println("**Excecution Failed**");

				}
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	// to get only one column of a table
	public String[] getOneColumn(String TableName, String column) {
		Properties props = new Properties();

		try (FileInputStream fis = new FileInputStream("conf.properties")) {
			props.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String script = "SELECT ";
		script += column;
		script += String.format(" FROM %s ;", TableName);
		try {
			Class.forName(props.getProperty("jdbc.driver.class"));
			String url = props.getProperty("jdbc.url");
			String login = props.getProperty("jdbc.login");
			String password = props.getProperty("jdbc.password");

			// System.out.println("Database connection...");
			try (Connection con = DriverManager.getConnection(url, login, password)) {

				try (Statement statem = con.createStatement()) {
					System.out.println("Excecuting: " + script);

					ResultSet result = statem.executeQuery(script);

					// System.out.println("Excecution done.");

					ArrayList<String> values = new ArrayList<String>();
					while (result.next()) {
						values.add(result.getObject(column).toString());
					}

					return OneDArrayToList(values);

				}
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	// get multiple columns from a table
	public String[][] getMultipleColumns(String TableName, String[] columns) {
		Properties props = new Properties();

		try (FileInputStream fis = new FileInputStream("conf.properties")) {
			props.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String script = "SELECT ";
		for (int i = 0; i <= columns.length - 1; i++) {
			if (i != columns.length - 1) {
				script += columns[i] + ", ";
			} else {
				script += columns[i] + " ";
			}

		}
		script += String.format("FROM %s ;", TableName);
		try {
			Class.forName(props.getProperty("jdbc.driver.class"));
			String url = props.getProperty("jdbc.url");
			String login = props.getProperty("jdbc.login");
			String password = props.getProperty("jdbc.password");

			// System.out.println("Database connection...");
			try (Connection con = DriverManager.getConnection(url, login, password)) {

				try (Statement statem = con.createStatement()) {
					System.out.println("Excecuting: " + script);

					ResultSet result = statem.executeQuery(script);

					// System.out.println("Excecution done.");

					return extractResults(result, columns);

				}
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public String[] getColumnsOfTable(String TableName) {
		String columns[] = new String[] {};
		if (TableName == "note") {
			columns = note;
		} else if (TableName == "etudiant") {
			columns = etudiant;
		} else if (TableName == "filiere") {
			columns = filiere;
		} else if (TableName == "matiere") {
			columns = matiere;
		} else if (TableName == "module") {
			columns = module;
		} else if (TableName == "semestre") {
			columns = semestre;
		} else if (TableName == "notes") {
			columns = notes;
		}
		return columns;
	}

	// ArrayList<ArrayList<String>> to String[][]
	public String[][] TwoDArrayToList(ArrayList<ArrayList<String>> arrayList) {
		int numRows = arrayList.size();
		int numCols = arrayList.get(0).size();
		String[][] stringArray = new String[numRows][numCols];

		// Populate the 2D array
		try {
			for (int i = 0; i < numRows; i++) {
				for (int j = 0; j < numCols; j++) {
					stringArray[i][j] = arrayList.get(i).get(j);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return stringArray;

	}

	// to get only one column of a table with certain conditions that will be
	// provided
	// example of condtions : id=10 and name="cristiano"
	// ==> it should be provided to this function as
	// (name of the table, column we want, {id, name}, {10, "cristiano"}
	public String[] getOneColumnWithConditions(String TableName, String columnPrincipale, String[] columns,
			String[] values) {
		Properties props = new Properties();

		try (FileInputStream fis = new FileInputStream("conf.properties")) {
			props.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String script = "SELECT ";
		script += columnPrincipale;
		script += String.format(" FROM %s", TableName);
		script += String.format(" WHERE `%s` = '%s'", columns[0], values[0]);

		// add conditions to the script
		for (int i = 1; i < columns.length; i++) {
			script += String.format("AND `%s` = '%s'", columns[i], values[i]);
		}
		script += ";";

		try {
			Class.forName(props.getProperty("jdbc.driver.class"));
			String url = props.getProperty("jdbc.url");
			String login = props.getProperty("jdbc.login");
			String password = props.getProperty("jdbc.password");

			// System.out.println("Database connection...");
			try (Connection con = DriverManager.getConnection(url, login, password)) {

				try (Statement statem = con.createStatement()) {
					System.out.println("Excecuting: " + script);
					// execute
					ResultSet result = statem.executeQuery(script);

					// System.out.println("Excecution done.");

					// extract results
					ArrayList<String> extractedValues = new ArrayList<String>();
					while (result.next()) {
						extractedValues.add(result.getObject(columnPrincipale).toString());
					}
					// return them in the form of String[]
					return OneDArrayToList(extractedValues);

				}
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	// change ArrayList<String> to String[]
	public String[] OneDArrayToList(ArrayList<String> arrayList) {
		int numRows = arrayList.size();
		String[] stringArray = new String[numRows];

		// Populate the 1D array
		try {
			for (int i = 0; i < numRows; i++) {
				stringArray[i] = arrayList.get(i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return stringArray;
	}

	public String[][] TwoDArrayWithScript(String[] columns, String script) {

		Properties props = new Properties();

		try (FileInputStream fis = new FileInputStream("conf.properties")) {
			props.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			Class.forName(props.getProperty("jdbc.driver.class"));
			String url = props.getProperty("jdbc.url");
			String login = props.getProperty("jdbc.login");
			String password = props.getProperty("jdbc.password");

			// System.out.println("Database connection...");
			try (Connection con = DriverManager.getConnection(url, login, password)) {

				try (Statement statem = con.createStatement()) {
					System.out.println("Excecuting: " + script);

					ResultSet result = statem.executeQuery(script);

					// System.out.println("Excecution done.");

					return extractResults(result, columns);

				}
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public String[] OneDArrayWithScript(String column, String script) {

		Properties props = new Properties();

		try (FileInputStream fis = new FileInputStream("conf.properties")) {
			props.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			Class.forName(props.getProperty("jdbc.driver.class"));
			String url = props.getProperty("jdbc.url");
			String login = props.getProperty("jdbc.login");
			String password = props.getProperty("jdbc.password");

			// System.out.println("Database connection...");
			try (Connection con = DriverManager.getConnection(url, login, password)) {

				try (Statement statem = con.createStatement()) {
					System.out.println("Excecuting: " + script);

					if (column == "0") {
						statem.executeUpdate(script);
					} else {
						ResultSet result = statem.executeQuery(script);
						// System.out.println("Excecution done.");

						ArrayList<String> values = new ArrayList<String>();
						while (result.next()) {
							values.add(result.getObject(column).toString());
						}

						return OneDArrayToList(values);
					}

				}
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

}
