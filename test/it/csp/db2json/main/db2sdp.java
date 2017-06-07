package it.csp.db2json.main;

import org.github.p3dr16k.db2json.core.GenericSqlManager;
import org.github.p3dr16k.db2json.properties.PropertiesManager;
import it.csp.json.JSONArray;
import org.github.p3dr16k.db2json.core.DBManager;



/*=======================================================================

*FILE: db2sdp.java
*
*DESCRIPTION:  
*REQUIREMENTS: 
*AUTHOR:       patrick
*COMPANY:      CSP s.c. a r.l. "Innovazione nelle ICT"
*VERSION:      1.0
*CREATED:      23-apr-2015
*LICENSE:      GNU/GPLv3
*========================================================================
* 
*/

public class db2sdp 
{
    public static void main(String[] args) throws Exception
    {
        String conf = "/home/patrick/Sviluppo/db2sdp/config.properties"; //questo diventa args[0]
        PropertiesManager propertiesManager = new PropertiesManager(conf);
        
        DBManager mysqlmanager = new GenericSqlManager(propertiesManager);
        mysqlmanager.executeQueryDB(propertiesManager.getExtractQuery());
        JSONArray res = mysqlmanager.jsonize();
        mysqlmanager.disconnectDB();
        for(int i = 0; i < res.length(); i++)
        { 
            System.out.println(res.get(i));            
        }
    }    
}