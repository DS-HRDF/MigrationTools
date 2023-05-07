package com.dataserve.dao;



import java.util.Hashtable;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Administrator
 */

public class JDBCHelper
{
    public static final int TOMCAT = 1;
    public static final int WEB_LOGIC = 2;
    public static final int WEB_SPHERE = 3;

    public static final int HR_DB_TYPE_SQL_SERVER = 1;
    public static final int HR_DB_TYPE_ORACLE = 2;

    public static Connection getConnection() throws SQLException, NamingException
    {
        String dataSource = getDataSourceName();
        return getConnection(dataSource);
    }

    public static Connection getConnection(String dataSource) throws SQLException, NamingException
    {
        Context context;
        InitialContext initContext;
        javax.sql.DataSource source = null;
        int appServer = 3;
        
        switch(appServer)
        {
            case TOMCAT:
                context = new InitialContext();
                javax.naming.Context env = (javax.naming.Context)context.lookup("java:comp/env");
                source = (javax.sql.DataSource)env.lookup(dataSource);
                break;
            case WEB_LOGIC:
                context = new InitialContext();
                source = (javax.sql.DataSource)context.lookup(dataSource);
                break;
            case WEB_SPHERE:
                Hashtable<String, String> hashtable = new Hashtable<String, String>();
                hashtable.put("java.naming.factory.initial", "com.ibm.websphere.naming.WsnInitialContextFactory");
                initContext = new InitialContext(hashtable);
                source = (javax.sql.DataSource)initContext.lookup(dataSource);
                break;
        }
        return source.getConnection();
    }

    private static String getDataSourceName()
    {
        String dataSource = "FNMOAMALATDS "; //default value...
//        try
//        {
//            javax.naming.Context ctx = new javax.naming.InitialContext();
//            javax.naming.Context env = (javax.naming.Context)ctx.lookup("java:comp/env");
//            dataSource = (String)env.lookup("IOAPPDataSource");
//        }
//        catch(Exception e){}
        return dataSource;
    }

}
