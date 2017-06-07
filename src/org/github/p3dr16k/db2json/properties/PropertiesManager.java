package org.github.p3dr16k.db2json.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


/*=======================================================================

*FILE: PropertiesManager.java
*
*DESCRIPTION:  Read configuration from properties file
*REQUIREMENTS: 
*AUTHOR:       Patrick Facco
*VERSION:      1.0
*CREATED:      22-apr-2015
*LICENSE:      GNU/GPLv3
*========================================================================
* 
*/

public class PropertiesManager
{    
    public static final int DEFAULT_PORT = 3306;
    public Properties props;

    public PropertiesManager(String filepath)
    {
        InputStream in = null;
        Logger.getLogger(PropertiesManager.class.getName()).log(Level.INFO, "Loading config...");
        try
        {
            in = new FileInputStream(filepath);
            props = new Properties();
            props.load(in);
            in.close();
        } 
        catch(FileNotFoundException ex)
        {
            Logger.getLogger(PropertiesManager.class.getName()).log(Level.SEVERE, "File " + filepath + " not exists", ex);
        } 
        catch(IOException ex)
        {
            Logger.getLogger(PropertiesManager.class.getName()).log(Level.SEVERE, "Unable to read file " + filepath, ex);
        }        
        Logger.getLogger(PropertiesManager.class.getName()).log(Level.INFO, "Config loaded...");
    }
    
    public String getDbName()
    {
        return props.getProperty("db_name");
    }
    
    public String getDbHost()
    {
        return props.getProperty("db_host");
    }
    
    public String getDbType()
    {
        return props.getProperty("db_type");
    }
    
    public String getDbClass()
    {
        return props.getProperty("db_class");
    }
    
    public int getDbPort()
    {
        int ris;
        try
        {
            ris = Integer.parseInt(props.getProperty("db_port"));
        }
        catch(NumberFormatException ex)
        {
            String errmsg = "Unable to read db_port, using defaul port " + DEFAULT_PORT;
            Logger.getLogger(PropertiesManager.class.getName()).log(Level.INFO, errmsg);
            ris = DEFAULT_PORT;
        }        
        return ris;        
    }
    
    public String getDbUsername()
    {
        return props.getProperty("db_username");
    }
    
    public String getDbPassword()
    {
        return props.getProperty("db_password");
    }
    
    public String getExtractQuery()
    {
        return props.getProperty("extract_query");
    }
    
    public String getGenericField(String name)
    {
        return props.getProperty(name);        
    }
}
