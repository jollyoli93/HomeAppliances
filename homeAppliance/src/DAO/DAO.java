package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class providing generic database operations for CRUD functionality.
 * 
 * This class provides methods for initialising a database connection, creating tables, 
 * inserting, updating, deleting records, and querying the database.
 *
 * @param <T> The type of object that the DAO is managing (e.g., User, Product).
 */
public abstract class DAO<T> {
    Connector connector;
    protected HashMap<String, String> tables = new HashMap<String, String>();
    protected ArrayList<String> allowedTablesList = new ArrayList<String>();

    /**
     * Default constructor.
     */
    public DAO() {
    }

    /**
     * Changes the connection provider for this DAO.
     * 
     * @param connector the new connection provider
     */
    public void changeConnection(Connector connector) {
        this.connector = connector;
    }

    /**
     * Initializes the database connection using the provided connector.
     */
    public void initializeDBConnection() {
        connector.initializeDBConnection();
    }

    /**
     * Adds a table name to the list of allowed tables.
     * 
     * @param table the table name to be added
     */
    public void setAllowedTables(String table) {
        allowedTablesList.add(table);
    }

    /**
     * Creates a new table in the database using the provided schema.
     * 
     * @param name   the name of the table
     * @param schema the schema definition for the table
     * @return true if the table was created successfully, false if it already exists
     */
    public boolean createTable(String name, String schema) {
        if (checkTableExists(name)) {
            return false; // Exit early if the table exists
        }

        try (Connection connect = connector.initializeDBConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(schema)) {

            int updated = preparedStatement.executeUpdate();

            return updated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error connecting to the database");
            System.out.println("SQL Exception: " + e.getMessage());
            return false;
        }
    }

    /**
     * Checks if a table exists in the database.
     * 
     * @param name the name of the table to check
     * @return true if the table exists, false otherwise
     */
    public boolean checkTableExists(String name) {
        String query = "SELECT name FROM sqlite_master WHERE type='table' AND name=? COLLATE NOCASE";
        try (Connection connect = connector.initializeDBConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(query)) {

            preparedStatement.setString(1, name); // Set the table name dynamically
            ResultSet result = preparedStatement.executeQuery();

            if (result.next()) {
                System.out.println("Table " + name + " exists.");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error checking table existence for: " + name);
            System.out.println("SQL Exception: " + e.getMessage());
            return false;
        }

        return false;
    }

    /**
     * Adds a table schema to the internal table map.
     * 
     * @param tableName the name of the table
     * @param schema    the schema definition for the table
     */
    protected void addTableMap(String tableName, String schema) {
        if (tables.containsKey(tableName)) {
            return;
        }
        tables.put(tableName, schema);
    }

    /**
     * Initialises all tables using the provided schema map.
     * 
     * @param tables the map of table names to schemas
     */
    protected void initAllTables(HashMap<String, String> tables) {
        tables.forEach((tableName, schema) -> {
            if (!checkTableExists(tableName)) {
                createTable(tableName, schema);
            } else {
                // Table exists, do nothing
            }
        });
    }

    /**
     * Adds a new record to the specified table.
     * 
     * @param tableName the name of the table
     * @param fields    a map of column names and their corresponding values
     * @return true if the record was added successfully, false otherwise
     */
    public boolean addNew(String tableName, Map<String, Object> fields) {
        StringBuilder queryBuilder = new StringBuilder("INSERT INTO ").append(tableName).append(" (");
        StringBuilder valuesBuilder = new StringBuilder(" VALUES (");

        // Dynamically construct column names and placeholders
        int fieldCount = fields.size();
        int i = 0;
        for (String field : fields.keySet()) {
            queryBuilder.append(field);
            valuesBuilder.append("?");
            if (i < fieldCount - 1) {
                queryBuilder.append(", ");
                valuesBuilder.append(", ");
            }
            i++;
        }
        queryBuilder.append(")").append(valuesBuilder).append(")");

        String query = queryBuilder.toString();

        try (Connection connect = connector.initializeDBConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(query)) {

            // Set the values dynamically
            int index = 1;
            for (Object value : fields.values()) {
                preparedStatement.setObject(index++, value);
            }

            // Execute the query
            int executeRows = preparedStatement.executeUpdate();
            return executeRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Exception: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves a record by its ID.
     * 
     * @param query the SQL query to execute
     * @param id    the ID of the record to retrieve
     * @return the ResultSet containing the record data
     * @throws SQLException if a database error occurs
     */
    protected ResultSet getById(String query, int id) throws SQLException {
        Connection connection = connector.initializeDBConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement.executeQuery();
    }

    /**
     * Deletes a record by its ID from the specified table.
     * 
     * @param tableName the name of the table
     * @param conditions a map of column names and their corresponding values for deletion criteria
     * @return the number of affected rows
     * @throws IllegalArgumentException if the table is not in the allowed list or the conditions are invalid
     */
    public int deleteById(String tableName, Map<String, Object> conditions) {
        System.out.println(allowedTablesList);

        if (!allowedTablesList.contains(tableName)) {
            throw new IllegalArgumentException("Invalid table.");
        }

        if (conditions == null || conditions.isEmpty()) {
            throw new IllegalArgumentException("Conditions cannot be null or empty.");
        }

        // Start constructing the DELETE query
        StringBuilder queryBuilder = new StringBuilder("DELETE FROM ").append(tableName).append(" WHERE ");

        // Add conditions dynamically
        boolean firstCondition = true;
        for (String column : conditions.keySet()) {
            if (!firstCondition) {
                queryBuilder.append(" AND ");
            }
            queryBuilder.append(column).append(" = ?");
            firstCondition = false;
        }

        String query = queryBuilder.toString();

        try (Connection connect = connector.initializeDBConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(query)) {

            // Bind the condition values
            int paramIndex = 1; // Start from the first parameter
            for (Object value : conditions.values()) {
                preparedStatement.setObject(paramIndex++, value);
            }

            // Execute the query and return the number of affected rows
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error connecting to the database");
            System.out.println("SQL Exception: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Finds all records in the database, optionally filtered by ID and sorted.
     * 
     * @param optionalId an optional ID to filter the records (can be null or 0)
     * @param sortParams a map of sorting parameters (e.g., column name, sort order)
     * @return a list of records
     */
    public abstract ArrayList<T> findAll(int optionalId, HashMap<String, Object> sortParams);

    /**
     * Updates a record in the specified table by its ID.
     * 
     * @param id          the ID of the record to update
     * @param table       the name of the table
     * @param updateFields a map of column names and their updated values
     * @return the number of rows affected by the update
     */
    public abstract int updateById(int id, String table, Map<String, Object> updateFields);
}
