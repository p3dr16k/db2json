package org.github.p3dr16k.db2json.core;

import org.github.p3dr16k.db2json.properties.PropertiesManager;
import it.csp.json.JSONArray;
import it.csp.json.JSONObject;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


/*=======================================================================

*FILE: GenericSqlManager.java
*
*DESCRIPTION:  DBManager implementation for MySQL DB
*REQUIREMENTS: JSONlib
*AUTHOR:       Patrick Facco
*VERSION:      1.0
*CREATED:      23-apr-2015
*LICENSE:      GNU/GPLv3
*========================================================================
* 
*/

public class GenericSqlManager extends DBManager
{   
    public GenericSqlManager(PropertiesManager propertiesManager) throws IOException, 
            ClassNotFoundException, SQLException, IllegalArgumentException, 
            SecurityException, InstantiationException, IllegalAccessException, 
            InvocationTargetException, NoSuchMethodException
    {
        super(propertiesManager);
    }

    /**
     * MySQL implementation.
     * {@inheritDoc} 
     * @throws SQLException 
     */
    @Override
    public void disconnectDB() throws SQLException
    {
        if(this.getConnection() != null && !this.getConnection().isClosed())        
        {
            if(this.getStatement() != null)
            {
                this.getStatement().close();
            }
            if(this.getResultset() != null)
            {
                this.getResultset().close();
            }
            
            this.getConnection().close();
        }
    }

    /**
     * MySQL implementation.
     * {@inheritDoc} 
     * @return 
     * @throws SQLException 
     */
    @Override
    public ResultSet executeQueryDB(String sql) throws SQLException
    {
        this.setStatement(this.getConnection().createStatement());
        this.setResultset(this.getStatement().executeQuery(sql));
        return this.getResultset();
    }

    /**
     * MySQL implementation.
     * {@inheritDoc} 
     * @return 
     * @throws SQLException 
     */
    @Override
    public int executeUpdateDB(String sql) throws SQLException
    {
        this.setStatement(this.getConnection().createStatement());
        int res = this.getStatement().executeUpdate(sql);
        return res;
    }

    /**
     * MySQL implementation.
     * {@inheritDoc} 
     * @return 
     * @throws SQLException 
     */
    @Override
    public JSONArray jsonize() throws SQLException
    {
        ResultSetMetaData md = this.getResultset().getMetaData();
        int nCol = md.getColumnCount();
        JSONArray result = new JSONArray();
        JSONObject tmp = new JSONObject();
        
        while(this.getResultset().next())
        {
            for(int i = 1; i < nCol+1; i++)
            {             
                tmp.put(md.getColumnLabel(i), this.getResultset().getString(md.getColumnLabel(i)));                
            }
            result.put(tmp);
            tmp = new JSONObject();
        }
        return result;
    }
}
