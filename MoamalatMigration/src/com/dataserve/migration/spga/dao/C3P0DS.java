package com.dataserve.migration.spga.dao;

import com.dataserve.migration.spga.util.Utils;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

/************************
 *
 * Created By Mohammad Awwad 24-August-2020
 *
 ************************/
public class C3P0DS {
    static ComboPooledDataSource ds = null;

    private static void init() throws PropertyVetoException {

        ds = new ComboPooledDataSource();
        ds.setDriverClass(Utils.getConfigs("db.driverClass")); //loads the jdbc driver
        ds.setJdbcUrl(Utils.getConfigs("db.uri"));
        ds.setUser(Utils.getConfigs("db.username"));
        ds.setPassword(Utils.getConfigs("db.password"));

        ds.setMinPoolSize(Integer.valueOf(Utils.getConfigs("db.MinPoolSize")));
        ds.setAcquireIncrement(Integer.valueOf(Utils.getConfigs("db.AcquireIncrement")));
        ds.setMaxPoolSize(Integer.valueOf(Utils.getConfigs("db.MaxPoolSize")));

    }

    public static Connection getCon() throws SQLException, PropertyVetoException {
        if (ds == null)
            init();
        return ds.getConnection();
    }

    public static void closeDS() {
        if (ds != null)
            ds.close();
    }

}
