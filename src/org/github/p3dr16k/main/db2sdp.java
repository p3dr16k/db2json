package org.github.p3dr16k.main;

import org.github.p3dr16k.db2json.core.GenericSqlManager;
import org.github.p3dr16k.db2json.properties.PropertiesManager;
import it.csp.json.JSONArray;
import org.github.p3dr16k.db2json.core.DBManager;



/*=======================================================================

*FILE: db2sdp.java
*
*DESCRIPTION:  
*REQUIREMENTS: 
*AUTHOR:       Patrick Facco
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
        if(args.length == 0)
        {
            System.err.println("Missing config file");
            System.exit(-1);
        }
        String conf = args[0];
        PropertiesManager propertiesManager = new PropertiesManager(conf);
        
        DBManager mysqlmanager = new GenericSqlManager(propertiesManager);
        mysqlmanager.executeQueryDB(propertiesManager.getExtractQuery());
        JSONArray res = mysqlmanager.jsonize();
        mysqlmanager.disconnectDB();
        System.out.println(res);
    }    
}
