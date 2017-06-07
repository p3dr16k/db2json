package org.github.p3dr16k.db2json.core;

import org.github.p3dr16k.db2json.properties.PropertiesManager;
import it.csp.json.JSONArray;
import java.sql.Statement;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import xeus.jcl.JarClassLoader;
import xeus.jcl.JclObjectFactory;

/*=======================================================================

*FILE: DBManager.java
*
*DESCRIPTION:  This class loads dinamically JDBC Driver
*REQUIREMENTS: log4j, jcl, JSONlib
*AUTHOR:       Patrick Facco
*VERSION:      1.0
*CREATED:      23-apr-2015
*LICENSE:      GNU/GPLv3
*========================================================================
* 
*/


public abstract class DBManager 
{
    public final String DRIVER_PATH = "drivers/";
    private PropertiesManager propertiesManager;    
    private Connection connection;
    private Statement statement;
    private ResultSet resultset;
    
    
    public DBManager(PropertiesManager propertiesManager) throws IOException, ClassNotFoundException, 
            SQLException, IllegalArgumentException, 
            SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        this.propertiesManager = propertiesManager;
        String classToLoad = propertiesManager.getDbClass();           
        Object driver = loadClasspath(classToLoad);        
             
        DriverManager.registerDriver(new DriverWrapper((Driver) driver));
        String url = "jdbc:" + propertiesManager.getDbType() + "://" +
                propertiesManager.getDbHost() + ":" + propertiesManager.getDbPort() +
                "/" + propertiesManager.getDbName();        
        
        Logger.getLogger(DBManager.class.getName()).log(Level.INFO, "JDBC url: {0}, getting connection", url);        
        connection = DriverManager.getConnection(url, propertiesManager.getDbUsername(), propertiesManager.getDbPassword());
        Logger.getLogger(DBManager.class.getName()).log(Level.INFO, "Done!");    
    }
    
    /**
     * Loads dinamically all jars in the folder drivers and returns an instance
     * of the class classToLoad
     * @param classToLoad the class to search in drivers
     * @return an instance of the class classToLoad
     * @throws IOException
     * @throws IllegalArgumentException
     * @throws SecurityException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException 
     */
    private Object loadClasspath(String classToLoad) throws IOException, 
            IllegalArgumentException, SecurityException, 
            ClassNotFoundException, InstantiationException, 
            IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        JarClassLoader jcl = new JarClassLoader();  
        Logger.getLogger(DBManager.class.getName()).log(Level.INFO, "Try to load drivers from " + DRIVER_PATH);
        jcl.add(DRIVER_PATH);        
        Logger.getLogger(DBManager.class.getName()).log(Level.INFO, "Done!");    
        JclObjectFactory factory = JclObjectFactory.getInstance();  
        return factory.create(jcl, classToLoad);
    }    

    public PropertiesManager getPropertiesManager()
    {
        return propertiesManager;
    }

    public void setPropertiesManager(PropertiesManager propertiesManager)
    {
        this.propertiesManager = propertiesManager;
    }

    public Connection getConnection()
    {
        return connection;
    }

    public void setConnection(Connection connection)
    {
        this.connection = connection;
    }

    public Statement getStatement()
    {
        return statement;
    }

    public void setStatement(Statement statement)
    {
        this.statement = statement;
    }

    public ResultSet getResultset()
    {
        return resultset;
    }

    public void setResultset(ResultSet resultset)
    {
        this.resultset = resultset;
    }
    
    /**
     * Disconnect from database and deallocate resources
     * @throws SQLException 
     */
    public abstract void disconnectDB() throws SQLException;
    
    /**
     * Execute query on database and returns a ResultSet
     * @param sql the query to execute
     * @return a ResultSet that contains results
     * @throws SQLException 
     */
    public abstract ResultSet executeQueryDB(String sql) throws SQLException;
    
    /**
     * Execute an update query on database and returns the number of columns 
     * affected by the operations
     * @param sql the update query to execute
     * @return the number of columns affected by the operations
     * @throws SQLException 
     */
    public abstract int executeUpdateDB(String sql) throws SQLException; 
    
    /**
     * Converts a ResultSet in json array     
     * @return a json array rapresentation of the ResultSet rs
     * @throws SQLException 
     */
    public abstract JSONArray jsonize() throws SQLException;
}
