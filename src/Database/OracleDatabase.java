package Database;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

// Class used to manage and update an Oracle database
// Created by: Ethan Andrews, Tanner Davis, and Michael Rosenwinkel
// Created on: October 8, 2024
public class OracleDatabase implements DatabaseHandler{

    // set up class variables
    private Connection connection;
    private static final String DB_URL = "jdbc:oracle:thin:sys/IT326RoutineHelperApp!@localhost:1521/FREE as SYSDBA";
    private static final String DB_USER = "SYS";
    private static final String DB_PASS = "IT326RoutineHelperApp!";

    /*
     *  Constructor for OracleDatabase
     */
    public OracleDatabase()
    {
        // no connection to begin with
        this.connection = null;
    }

    /*
     *  Method to connect to a database
     * 
     *  @return Boolean variable dependent on whether or not connection was successful
     */
    @Override
    public boolean connect()
    {
        // try connecting to database
        try
        {
            if(connection == null || connection.isClosed())
            {
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                System.out.println("Connected to the Oracle database successfully.");
                return true;
            }
            else
            {
                System.out.println("Already connected to the Oracle database.");
                return false;
            }
        }
        catch(SQLException e)
        {
            System.out.println("Failed to connect to the Oracle database: " + e.getMessage());
            return false;
        }
    }

    /*
     *  Method to disconnect from a database
     *  @return Boolean variable dependent on whether or not disconnection was successful
     */
    @Override
    public boolean disconnect()
    {
        // try disconnecting from the database
        try
        {
            if(connection != null && !connection.isClosed())
            {
                connection.close();
                return true;
            }
            else
            {
                return false;
            }
        }
        catch(Exception e)
        {
            return false;
        }
    }

    /*
     *  Method to get current connection
     * 
     *  @return Current connection
     */
    public Connection getConnection()
    {
        return this.connection;
    }
}
