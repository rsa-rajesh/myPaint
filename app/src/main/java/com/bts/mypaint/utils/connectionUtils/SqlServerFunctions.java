package com.bts.mypaint.utils.connectionUtils;

import android.os.StrictMode;

import com.bts.mypaint.data.Prefs;

import java.sql.Connection;

public class SqlServerFunctions {
    public Connection ConnectToSQLServer(Prefs prefs) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
//        try {
////            String ConnectionString = "jdbc:jtds:sqlserver://"+prefs.getServerIp()+":"+prefs.getServerPort()+"/"+prefs.getDatabaseName()+";encrypt=fasle;user="+prefs.getDatabaseUser()+";password="+prefs.getDatabasePassword();
////            Log.i("Android", ConnectionString);
////            Class.forName("net.sourceforge.jtds.jdbc.Driver");
////            connection = DriverManager.getConnection(ConnectionString, prefs.getDatabaseUser(), prefs.getDatabasePassword());
//            Log.i("Android", connection.toString());
//        } catch (SQLException se) {
//            Log.e("error here 1 : ", se.getMessage());
//        } catch (ClassNotFoundException e) {
//            Log.e("error here 2 : ", e.getMessage());
//        } catch (Exception e) {
//            Log.e("error here 3 : ", e.getMessage());
//        }
        return connection;
    }

    public Connection TempConnectToSQLServer(Prefs prefs) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
//        try {
////            String ConnectionString = "jdbc:jtds:sqlserver://"+prefs.getTempServerIp()+":"+prefs.getTempServerPort()+"/"+prefs.getTempDatabaseName()+";encrypt=fasle;user="+prefs.getTempDatabaseUser()+";password="+prefs.getTempDatabasePassword();
////            Log.i("Android", ConnectionString);
////            Class.forName("net.sourceforge.jtds.jdbc.Driver");
////            connection = DriverManager.getConnection(ConnectionString, prefs.getDatabaseUser(), prefs.getDatabasePassword());
//            Log.i("Android", connection.toString());
//        } catch (SQLException se) {
//            Log.e("error here 1 : ", se.getMessage());
//        } catch (ClassNotFoundException e) {
//            Log.e("error here 2 : ", e.getMessage());
//        } catch (Exception e) {
//            Log.e("error here 3 : ", e.getMessage());
//        }
        return connection;
    }
}
