package Database;

import java.sql.*;

// Class used to manage and update an Oracle database
// Created by: Ethan Andrews, Tanner Davis, and Michael Rosenwinkel
// Created on: October 8, 2024
public class OracleDatabase implements DatabaseHandler{

    // set up class variables
    private Connection connection;
    private static final String DB_URL = "";
    private static final String DB_USERNAME = "";
    private static final String DB_PASSWORD = "";

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
                connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
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
