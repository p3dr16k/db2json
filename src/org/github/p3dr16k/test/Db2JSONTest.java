package org.github.p3dr16k.test;

import org.github.p3dr16k.db2json.core.DBManager;
import org.github.p3dr16k.db2json.core.GenericSqlManager;
import org.github.p3dr16k.db2json.properties.PropertiesManager;
import it.csp.json.JSONArray;
import java.sql.ResultSet;

/*=======================================================================

*FILE: DriverWrapper.java
*
*DESCRIPTION:  
*REQUIREMENTS: 
*AUTHOR:      Patrick Facco
*VERSION:      1.0
*CREATED:      23-apr-2015
*LICENSE:      GNU/GPLv3
*========================================================================
* 
*/
public class Db2JSONTest
{
    public static void main(String[] args) throws Exception
    {
        PropertiesManager propertiesManager = new PropertiesManager("config.properties");
        DBManager dbmanager = new GenericSqlManager(propertiesManager);
        ResultSet rs = dbmanager.executeQueryDB(propertiesManager.getExtractQuery());
        JSONArray json = dbmanager.jsonize();
        System.out.println(json);
    }
}
